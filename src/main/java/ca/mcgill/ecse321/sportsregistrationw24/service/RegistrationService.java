package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.*;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;
import ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;
import java.util.*;

@Service
public class RegistrationService {
  @Autowired
  private RegistrationRepository registrationRepository;

  @Autowired
  private CustomerAccountRepository customerAccountRepository;

  @Autowired
  private CourseOfferingRepository courseOfferingRepository;

  @Autowired
  private CourseTypeRepository courseTypeRepository;

  @Autowired
  private InstructorAccountRepository instructorAccountRepository;

  @Transactional
  public Registration createRegistration(String courseTypeName, String instructorEmail, Date courseStartDate,
                                         Date courseEndDate, String userAccountEmail, Date registrationDate) {
    Registration newRegistration = new Registration();

    Optional<CustomerAccount> wrappedCustomer = customerAccountRepository.findByEmail(userAccountEmail);
    CustomerAccount customer = wrappedCustomer.orElseThrow(() -> new IllegalArgumentException("No customer account was found with the provided email!"));

    Optional<CourseType> wrappedCourseType = courseTypeRepository.findByCourseName(courseTypeName);
    CourseType courseType = wrappedCourseType.orElseThrow(() -> new IllegalArgumentException("No course type was found with the provided name!"));

    Optional<InstructorAccount> wrappedInstructorAccount = instructorAccountRepository.findByEmail(instructorEmail);
    InstructorAccount instructorAccount = wrappedInstructorAccount.orElseThrow(() -> new IllegalArgumentException("No instructor account was found with the provided email!"));

    CourseOffering courseOffering = null;

    for (CourseOffering c : courseOfferingRepository.findAll()) {
      if (c.getCourseType().getCourseName().equals(courseType.getCourseName())
              && c.getInstructorAccount().getEmail().equals(instructorAccount.getEmail())
              && c.getStartDate().compareTo(courseStartDate) == 0
              && c.getEndDate().compareTo(courseEndDate) == 0) {
        courseOffering = c;
      }
    }

    if (courseOffering == null) {
      throw new IllegalArgumentException("No course offering was found with the provided information!");
    }

    newRegistration.setCustomerAccount(customer);
    newRegistration.setCourseOffering(courseOffering);
    newRegistration.setRegisteredDate(registrationDate);

    registrationRepository.save(newRegistration);

    return newRegistration;
  }

  // NOTE: IMPLEMENT THIS
  @Transactional
  public Registration getRegistration(String courseTypeName, String instructorEmail, Date courseStartDate,
                                      Date courseEndDate, String userAccountEmail, Date registrationDate) {
      Registration registration = null;

      for (Registration r : registrationRepository.findAll()) {
        if (r.getCourseOffering().getCourseType().getCourseName().equals(courseTypeName)
                && r.getCourseOffering().getInstructorAccount().getEmail().equals(instructorEmail)
                && r.getCourseOffering().getStartDate().compareTo(courseStartDate) == 0
                && r.getCourseOffering().getEndDate().compareTo(courseEndDate) == 0
                && r.getCustomerAccount().getEmail().equals(userAccountEmail)
                && r.getRegisteredDate().compareTo(registrationDate) == 0) {
          registration = r;
        }
      }
      // If a registration is matched, then return an object else null
      return registration;
  }

  @Transactional
  public void deleteRegistration(String courseTypeName, String instructorEmail, Date courseStartDate,
                                 Date courseEndDate, String userAccountEmail, Date registrationDate) {
    Registration registration = getRegistration(courseTypeName, instructorEmail, courseStartDate,
            courseEndDate, userAccountEmail, registrationDate);

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
