package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseOfferingRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.PaymentInfoRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;
import ca.mcgill.ecse321.sportsregistrationw24.model.keys.RegistrationId;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.getUserFromToken;

@Service
public class RegistrationService {
  private final RegistrationRepository registrationRepository;
  private final UserAccountRepository userAccountRepository;
  private final CourseOfferingRepository courseOfferingRepository;
  private final PaymentInfoRepository paymentInfoRepository;

  @Autowired
  public RegistrationService(RegistrationRepository registrationRepository, UserAccountRepository userAccountRepository, CourseOfferingRepository courseOfferingRepository, PaymentInfoRepository paymentInfoRepository) {
    this.registrationRepository = registrationRepository;
    this.userAccountRepository = userAccountRepository;
    this.courseOfferingRepository = courseOfferingRepository;
    this.paymentInfoRepository = paymentInfoRepository;
  }

  @Transactional
  public void createRegistration(String userToken, Integer courseOfferingId, Integer paymentInfoId, Integer pricePaid, Date registrationDate) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);
    Validate.isTrue(user.getUserType().equals("CUSTOMER"), "Only customers can register for courses!");

    Registration duplicateRegistration = registrationRepository.findById(new RegistrationId(user.getId(), courseOfferingId)).orElse(null);
    Validate.notNull(duplicateRegistration, "You have already registered for this course offering!");

    CourseOffering courseOffering = courseOfferingRepository.findById(courseOfferingId).orElse(null);
    PaymentInfo paymentInfo = paymentInfoRepository.findById(paymentInfoId).orElse(null);

    Validate.notNull(courseOffering, "No course offering was found with the provided information!");
    Validate.notNull(paymentInfo, "No payment information was found with the provided information!");
    Validate.notNull(registrationDate, "Must register on a date that is not null!");
    Validate.isTrue(pricePaid > 0, "Price paid must be greater than 0!");
    Validate.isTrue(registrationDate.before(courseOffering.getStartDate()), "You must register for a course offering before it starts");

    Registration registration = new Registration();
    registration.setCourseOffering(courseOffering);
    registration.setCustomerAccount((CustomerAccount) user);
    registration.setPaymentInfo(paymentInfo);
    registration.setRegisteredDate(registrationDate);
    registration.setPricePaid(pricePaid);
    registration.setId(new RegistrationId(user.getId(), courseOfferingId));

    registrationRepository.save(registration);
  }

  @Transactional
  public List<Registration> getAllRegistrationsByCustomer(String userToken) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);
    Validate.isTrue(user.getUserType().equals("CUSTOMER"), "Only customers can view their registrations");

    List<Registration> foundRegistrations = registrationRepository.findByCustomerAccount((CustomerAccount) user).orElse(null);

    if (foundRegistrations == null) {
      throw new IllegalArgumentException("No registrations were found for this customer");
    }

    return foundRegistrations;
  }

  @Transactional
  public List<Registration> getAllRegistrations(String userToken, Date lowRegistrationDate, Date highRegistrationDate, List<Integer> courseOfferingIDs, String customerEmail) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);
    Validate.isTrue(user.getUserType().equals("OWNER"), "Only owners can view all registrations");

    List<CourseOffering> courseOfferings = courseOfferingIDs.stream().map(id -> courseOfferingRepository.findById(id).orElse(null)).toList();

    CustomerAccount customerAccount = customerEmail == null ? null : (CustomerAccount) userAccountRepository.findUserByEmail(customerEmail).orElseThrow();

    List<Registration> foundRegistrations = registrationRepository.findByFilters(courseOfferings, customerAccount, lowRegistrationDate, highRegistrationDate).orElse(null);

    if (foundRegistrations == null) {
      throw new IllegalArgumentException("No registrations were found with the provided information!");
    }

    return foundRegistrations;
  }

  @Transactional
  public List<Registration> getAllRegistrationsByCourseOffering(String userToken, Integer courseOfferingId) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (user.getUserType().equals("CUSTOMER")) {
      throw new IllegalArgumentException("Only instructors and owners can view registrations!");
    }

    CourseOffering courseOffering = courseOfferingRepository.findById(courseOfferingId).orElse(null);

    if (courseOffering == null) {
      throw new IllegalArgumentException("No course offering was found with the provided information!");
    }

    List<Registration> foundRegistrations = registrationRepository.findByCourseOffering(courseOffering).orElse(null);

    if (foundRegistrations == null) {
      throw new IllegalArgumentException("No registrations were found for this course offering!");
    }

    return foundRegistrations;
  }

  @Transactional
  public void deleteRegistration(RegistrationId registrationId) {
    Registration registration = registrationRepository.findById(registrationId).orElse(null);

    if (registration == null) {
      throw new IllegalArgumentException("Registration does not exist!");
    }

    registrationRepository.delete(registration);
  }
}
