package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseOfferingRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseTypeRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.Registration;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;
import java.util.*;

@Service
public class RegistrationCreationService {
    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @Transactional
    public Registration createRegistration(Integer courseOfferingId, String userAccountEmail, Date registrationDate) {
        Registration newRegistration = new Registration();

        Optional<CustomerAccount> wrappedCustomer = customerAccountRepository.findByEmail(userAccountEmail);
        CustomerAccount unwrappedCustomer = wrappedCustomer.orElseThrow(() -> new IllegalArgumentException("No customer account was found with the provided email!"));

        Optional<CourseOffering> wrappedCourseOffering = courseOfferingRepository.findById(courseOfferingId);
        CourseOffering unwrappedCourseOffering = wrappedCourseOffering.orElseThrow(() -> new IllegalArgumentException("No course offering was found with the provided ID!"));

        newRegistration.setCustomerAccount(unwrappedCustomer);
        newRegistration.setCourseOffering(unwrappedCourseOffering);
        newRegistration.setRegisteredDate(registrationDate);

        registrationRepository.save(newRegistration);

        return newRegistration;
    }

    // NOTE: IMPLEMENT THIS
    @Transactional
    public Registration getRegistration(Date date) {
        return new Registration();
    }

    @Transactional
    public List<Registration> getAllRegistrations() {
        return toList(registrationRepository.findAll());
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}
