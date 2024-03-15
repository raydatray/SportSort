package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.*;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;
import ca.mcgill.ecse321.sportsregistrationw24.model.keys.RegistrationId;
import ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;
import java.util.logging.Logger;

@Service
public class RegistrationService {
  @Autowired
  private RegistrationRepository registrationRepository;

  @Autowired
  private CustomerAccountRepository customerAccountRepository;

  @Autowired
  private CourseOfferingRepository courseOfferingRepository;

  @Autowired
  private PaymentInfoRepository paymentInfoRepository;

  @Transactional
  public Registration createRegistration(Integer courseOfferingId, Integer customerAccountId,
                                         Integer paymentInfoId, Date registrationDate) {
    Registration newRegistration = new Registration();

    CourseOffering courseOffering = courseOfferingRepository.findById(courseOfferingId).orElse(null);
    CustomerAccount customer = customerAccountRepository.findById(customerAccountId).orElse(null);
    PaymentInfo paymentInfo = paymentInfoRepository.findById(paymentInfoId).orElse(null);

    if (courseOffering == null) {
      throw new IllegalArgumentException("No course offering was found with the provided information!");
    }

    if (customer == null) {
      throw new IllegalArgumentException("No customer account was found with the provided information!");
    }

    if (paymentInfo == null) {
      throw new IllegalArgumentException("No payment information was found with the provided information!");
    }

    newRegistration.setCustomerAccount(customer);
    newRegistration.setCourseOffering(courseOffering);
    newRegistration.setPaymentInfo(paymentInfo);
    newRegistration.setRegisteredDate(registrationDate);

    registrationRepository.save(newRegistration);

    return newRegistration;
  }

  @Transactional
  public Registration getRegistration(RegistrationId registrationId) {
    return registrationRepository.findById(registrationId).orElseThrow(() -> new IllegalArgumentException("The registration does not exist!"));
  }

  @Transactional
  public void deleteRegistration(RegistrationId registrationId) {
    Registration registration = registrationRepository.findById(registrationId).orElse(null);

    if (registration == null) {
      throw new IllegalArgumentException("Registration does not exist!");
    }

    registrationRepository.delete(registration);
  }

  @Transactional
  public List<Registration> getAllRegistrations() {
    Utilities utilities = new Utilities();
    return utilities.iterableToArrayList(registrationRepository.findAll());
  }
}
