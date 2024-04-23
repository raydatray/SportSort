package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseOfferingRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.PaymentInfoRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;
import ca.mcgill.ecse321.sportsregistrationw24.model.keys.RegistrationId;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.getUserFromToken;

@Service
public class RegistrationService {
  @Autowired
  private RegistrationRepository registrationRepository;
  @Autowired
  private UserAccountRepository userAccountRepository;
  @Autowired
  private CourseOfferingRepository courseOfferingRepository;
  @Autowired
  private PaymentInfoRepository paymentInfoRepository;

  @Transactional
  public void createRegistration(String userToken, Integer courseOfferingId, Integer paymentInfoId, Integer pricePaid, Date registrationDate) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (!user.getUserType().equals("CUSTOMER")) {
      throw new IllegalArgumentException("Only customers can register for courses!");
    }

    Registration duplicateRegistration = registrationRepository.findById(new RegistrationId(user.getId(), courseOfferingId)).orElse(null);

    if (duplicateRegistration != null) {
      throw new IllegalArgumentException("You have already registered for this course offering!");
    }

    CourseOffering courseOffering = courseOfferingRepository.findById(courseOfferingId).orElse(null);
    PaymentInfo paymentInfo = paymentInfoRepository.findById(paymentInfoId).orElse(null);

    if (courseOffering == null) {
      throw new IllegalArgumentException("No course offering was found with the provided information!");
    }

    if (paymentInfo == null) {
      throw new IllegalArgumentException("No payment information was found with the provided information!");
    }

    if (registrationDate == null) {
      throw new IllegalArgumentException("Must register on a date that is not null!");
    }

    if (registrationDate.after(courseOffering.getStartDate()) || registrationDate.after(courseOffering.getEndDate()) || registrationDate.equals(courseOffering.getStartDate())) {
      throw new IllegalArgumentException("You must register for a course offering at most one day before it starts!");
    }

    Registration registration = new Registration();
    registration.setCourseOffering(courseOffering);
    registration.setCustomerAccount((CustomerAccount) user);
    registration.setPaymentInfo(paymentInfo);
    registration.setRegisteredDate(registrationDate);
    registration.setPricePaid(pricePaid);

    registration.setId(new RegistrationId(user.getId(), courseOfferingId));

    registrationRepository.save(registration);
  }

  //Do we need this?
  @Deprecated
  @Transactional
  public Registration getRegistration(RegistrationId registrationId) {
    Registration registration = registrationRepository.findById(registrationId).orElse(null);

    if (registration == null) {
      throw new IllegalArgumentException("Registration does not exist!");
    }

    return registration;
  }

  @Transactional
  public List<Registration> getAllRegistrationsByCustomer(String userToken) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (!user.getUserType().equals("CUSTOMER")) {
      throw new IllegalArgumentException("Only customers can send this request");
    }

    List<Registration> foundRegistrations = registrationRepository.findByCustomerAccount((CustomerAccount) user).orElse(null);

    if (foundRegistrations == null) {
      throw new IllegalArgumentException("No registrations were found for this customer!");
    }

    return foundRegistrations;
  }

  @Transactional
  public List<Registration> getAllRegistrations(String userToken, Date lowRegistrationDate, Date highRegistrationDate, List<Integer> courseOfferingIDs, String customerEmail) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (!user.getUserType().equals("OWNER")) {
      throw new IllegalArgumentException("Only owners can view all registrations!");
    }

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
