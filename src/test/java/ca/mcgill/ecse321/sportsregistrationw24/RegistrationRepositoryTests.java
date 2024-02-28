package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.*;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RegistrationRepositoryTests {
    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private InstructorAccountRepository instructorAccountRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        registrationRepository.deleteAll();
        courseOfferingRepository.deleteAll();
        customerAccountRepository.deleteAll();
        courseTypeRepository.deleteAll();
        roomRepository.deleteAll();
        instructorAccountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadRegistration() {
        CourseType courseType = new CourseType("Cardio", true);
        courseTypeRepository.save(courseType);

        Room testRoom = new Room("Pool", 10, 10, 10);
        roomRepository.save(testRoom);

        InstructorAccount testInstructor = new InstructorAccount("raydatray@gmail.com", "password");
        instructorAccountRepository.save(testInstructor);

        Date startDate = Date.valueOf("2024-02-18");
        Date endDate = Date.valueOf("2024-03-15");
        List<DayOfWeek> testDays = List.of(new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.FRIDAY});

        CourseOffering testOffering  = new CourseOffering(startDate, endDate, testDays, testRoom, courseType, testInstructor);
        courseOfferingRepository.save(testOffering);

        String testEmail = "joebama@gmail.com";
        String testPassword = "obama";

        CustomerAccount testCustomer = new CustomerAccount(testEmail, testPassword);
        customerAccountRepository.save(testCustomer);

        Date testDate = Date.valueOf("2024-03-04");

        Registration testRegistration = new Registration(testDate, testOffering, testCustomer);
        registrationRepository.save(testRegistration);

        Optional<List<Registration>> readRegistrations = registrationRepository.findByCustomerAccount(testCustomer);

        List<Registration> testRegistrations;

        assertNotNull(testRegistrations = readRegistrations.orElse(null));

        Registration foundRegistration = null;

        for(Registration registration : testRegistrations) {
            if (registration.getId().equals(testRegistration.getId())) {
                foundRegistration = registration;
                return;
            }
        }

        assertNotNull(foundRegistration);

        assertEquals(foundRegistration.getCourseOffering().getCourseType().getId(), courseType.getId());

        assertEquals(foundRegistration.getCourseOffering().getId(), testOffering.getId());

        assertEquals(foundRegistration.getCourseOffering().getRoom().getId(), testRoom.getId());

        assertEquals(foundRegistration.getCourseOffering().getInstructorAccount().getId(), testInstructor.getId());

        assertEquals(foundRegistration.getCustomerAccount().getId(), testCustomer.getId());

        assertEquals(testDate, foundRegistration.getRegisteredDate());
    }
}
