package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RegistrationRepositoryTests {
    @Autowired
    private RegistrationRepository registrationRepository;

    @AfterEach
    public void clearDatabase() {
        registrationRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadRegistration() {
        CourseType courseType = new CourseType(1, "Cardio", true);
        Room testRoom = new Room("Pool", 10, 10, 10);
        InstructorAccount testInstructor = new InstructorAccount(1, "raydatray@gmail.com", "password");
        Date startDate = Date.valueOf("2024-02-18");
        Date endDate = Date.valueOf("2024-03-15");

        CourseOffering testOffering  = new CourseOffering(1, startDate, endDate, testRoom, courseType, testInstructor);

        String testEmail = "joebama@gmail.com";
        String testPassword = "obama";

        CustomerAccount testCustomer = new CustomerAccount(1, testEmail, testPassword);

        Date testDate = Date.valueOf("2024-03-04");

        Registration testRegistration = new Registration(testDate, testOffering, testCustomer);

        registrationRepository.save(testRegistration);

        List<Registration> readRegistrations = registrationRepository.findByCustomer(testCustomer);

        assertNotNull(readRegistrations);
        assertNotNull(testRegistration = readRegistrations.contains(testRegistration) ? testRegistration : null);
        assertEquals(testDate, testRegistration.getRegisteredDate());
        assertEquals(testOffering, testRegistration.getCourseOffering());
        assertEquals(testCustomer, testRegistration.getCustomerAccount());
    }
}
