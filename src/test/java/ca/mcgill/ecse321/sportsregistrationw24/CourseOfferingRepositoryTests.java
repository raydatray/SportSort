package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.*;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.Date;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CourseOfferingRepositoryTests {
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private InstructorAccountRepository instructorAccountRepository;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        registrationRepository.deleteAll();
        courseOfferingRepository.deleteAll();
        paymentInfoRepository.deleteAll();
        customerAccountRepository.deleteAll();
        courseTypeRepository.deleteAll();
        roomRepository.deleteAll();
        instructorAccountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCourseOffering(){
        //create a new course offering
        CourseType courseType = new CourseType("Cardio");
        courseType.setApproved(true);
        courseTypeRepository.save(courseType);

        Room testRoom = new Room("Pool", 10, 10, 10);
        roomRepository.save(testRoom);

        InstructorAccount testInstructor = new InstructorAccount("ray","raydatray@gmail.com", "password");
        instructorAccountRepository.save(testInstructor);

        ArrayList<DayOfWeek> testDays = new ArrayList<>(List.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));        Date startDate = Date.valueOf("2024-02-18");
        Date endDate = Date.valueOf("2024-03-15");

        CourseOffering test  = new CourseOffering(startDate, endDate, testDays, testRoom, courseType, testInstructor);
        courseOfferingRepository.save(test);
        Integer generatedID = test.getId();

        //Read the event
        Optional<CourseOffering> readOffering = courseOfferingRepository.findById(generatedID);

        assertNotNull(test = readOffering.orElse(null));
        //Verify that the courseType is the same thing
        assertEquals(courseType.getId(), test.getCourseType().getId());

        assertEquals(testRoom.getId(), test.getRoom().getId());

        assertEquals(testInstructor.getId(), test.getInstructorAccount().getId());

        assertEquals(startDate, test.getStartDate());
        assertEquals(endDate, test.getEndDate());
        assertEquals(generatedID, test.getId());
    }

    @Test
    public void testDeleteCourseOfferingWithoutRegistrations() {
        //create a new course offering
        CourseType courseType = new CourseType("Cardio");
        courseType.setApproved(true);
        courseTypeRepository.save(courseType);

        Room testRoom = new Room("Pool", 10, 10, 10);
        roomRepository.save(testRoom);

        InstructorAccount testInstructor = new InstructorAccount("ray","raydatray@gmail.com", "password");
        instructorAccountRepository.save(testInstructor);

        ArrayList<DayOfWeek> testDays = new ArrayList<>(List.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));
        Date startDate = Date.valueOf("2024-02-18");
        Date endDate = Date.valueOf("2024-03-15");

        CourseOffering testCourseOffering = new CourseOffering(startDate, endDate, testDays, testRoom, courseType, testInstructor);
        courseOfferingRepository.save(testCourseOffering);
        Integer generatedCourseOfferingID = testCourseOffering.getId();

        courseOfferingRepository.deleteById(generatedCourseOfferingID);

        // After the exception, verify that the CourseOffering still exists
        Optional<CourseOffering> deletedOffering = courseOfferingRepository.findById(testCourseOffering.getId());
        assertFalse(deletedOffering.isPresent(), CourseOffering.class.getSimpleName() + " was not deleted successfully");

    }

    @Test
    public void testDeleteCourseOfferingWithRegistrations() {
        //create a new course offering
        CourseType courseType = new CourseType("Cardio");
        courseType.setApproved(true);
        courseTypeRepository.save(courseType);

        Room testRoom = new Room("Pool", 10, 10, 10);
        roomRepository.save(testRoom);

        InstructorAccount testInstructor = new InstructorAccount("ray","raydatray@gmail.com", "password");
        instructorAccountRepository.save(testInstructor);

        ArrayList<DayOfWeek> testDays = new ArrayList<>(List.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY)); //fuck u intellij autocomplete
        Date startDate = Date.valueOf("2024-02-18");
        Date endDate = Date.valueOf("2024-03-15");

        CourseOffering testCourseOffering = new CourseOffering(startDate, endDate, testDays, testRoom, courseType, testInstructor);
        courseOfferingRepository.save(testCourseOffering);
        Integer generatedCourseOfferingID = testCourseOffering.getId();

        String testName = "joe";
        String testEmail = "joebama@gmail.com";
        String testPassword = "obama";
        String testEmail2 = "joebama2@gmail.com";
        String testPassword2 = "obama2";
        String testEmail3 = "joebama3@gmail.com";
        String testPassword3 = "obama3";

        CustomerAccount testCustomer = new CustomerAccount(testName,testEmail, testPassword);
        CustomerAccount testCustomer2 = new CustomerAccount(testName, testEmail2, testPassword2);
        CustomerAccount testCustomer3 = new CustomerAccount(testName, testEmail3, testPassword3);
        customerAccountRepository.save(testCustomer);
        customerAccountRepository.save(testCustomer2);
        customerAccountRepository.save(testCustomer3);

        PaymentInfo card1 = new PaymentInfo(PaymentInfo.PaymentType.Credit, 12345, 123, 24, 6, testCustomer);
        PaymentInfo card2 = new PaymentInfo(PaymentInfo.PaymentType.Credit, 12345, 123, 24, 6, testCustomer2);
        PaymentInfo card3 = new PaymentInfo(PaymentInfo.PaymentType.Credit, 12345, 123, 24, 6, testCustomer3);
        paymentInfoRepository.save(card1);
        paymentInfoRepository.save(card2);
        paymentInfoRepository.save(card3);

        Registration registration = new Registration(Date.valueOf("2024-02-18"), testCourseOffering, testCustomer, card1);
        Registration registration2 = new Registration(Date.valueOf("2024-02-18"), testCourseOffering, testCustomer2, card2);
        Registration registration3 = new Registration(Date.valueOf("2024-02-18"), testCourseOffering, testCustomer3, card3);
        registrationRepository.save(registration);
        registrationRepository.save(registration2);
        registrationRepository.save(registration3);

        // Verify that CourseOffering cannot be deleted while Registrations exist
        assertThrows(DataIntegrityViolationException.class, () -> courseOfferingRepository.deleteById(generatedCourseOfferingID));

        // After the exception, verify that the CourseOffering still exists
        Optional<CourseOffering> notDeletedOffering = courseOfferingRepository.findById(generatedCourseOfferingID);
        assertTrue(notDeletedOffering.isPresent(), CourseOffering.class.getSimpleName() + " was deleted when it should not have been");
    }

}
