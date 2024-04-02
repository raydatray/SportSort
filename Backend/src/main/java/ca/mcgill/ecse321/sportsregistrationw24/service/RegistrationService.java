package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.*;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;
import ca.mcgill.ecse321.sportsregistrationw24.model.keys.RegistrationId;
import ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.aot.RegisteredBeanAotContribution;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.getUserFromToken;
import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.iterableToArrayList;

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
  public void createRegistration(String userToken, Integer courseOfferingId, Integer paymentInfoId, Date registrationDate) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (!user.getUserType().equals("CUSTOMER")) {
      throw new IllegalArgumentException("Only customers can register for courses!");
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

    Registration newRegistration = new Registration(registrationDate, courseOffering, (CustomerAccount) user, paymentInfo);

    registrationRepository.save(newRegistration);
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
  public List<Registration> getAllRegistrations(String userToken) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (!user.getUserType().equals("OWNER")) {
      throw new IllegalArgumentException("Only owners can view all registrations!");
    }

    return iterableToArrayList(registrationRepository.findAll());
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
