package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.model.keys.RegistrationId;
import ca.mcgill.ecse321.sportsregistrationw24.service.CustomerAccountService;
import ca.mcgill.ecse321.sportsregistrationw24.service.RegistrationService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.sportsregistrationw24.dao.*;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Array;
import java.sql.Date;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationRepositoryTests {
  @Mock
  private RegistrationRepository registrationDao;

  @Mock
  private CourseOfferingRepository courseOfferingDao;
/*
  //do I need CourseType??
  @Mock
  private CourseTypeRepository courseTypeDao;

  @Mock
  private RoomRepository roomDao;

  @Mock
  private InstructorAccountRepository instructorAccountDao;
*/
  @Mock
  private CustomerAccountRepository customerAccountDao;

  @Mock
  private PaymentInfoRepository paymentInfoDao;



  @InjectMocks
  private RegistrationService registrationService;
  /*
  @InjectMocks
  private CustomerAccountService
  */
  private static final Integer CUSTOMER_ID = 1;
  private static final Integer COURSE_OFFERING_ID = 1;
  private static final Date DATE = Date.valueOf("2024-01-02");
  private static final Integer PAYMENT_INFO_ID = 1;
  private static final Integer INSTRUCTOR_ID = 1;

  @BeforeEach
  public void setMockOutput() {
    lenient().when(customerAccountDao.findByToken(anyString())).thenAnswer((InvocationOnMock invocation) -> {
      if (invocation.getArgument(0).equals(CUSTOMER_ID)) {
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setName("John");
        customerAccount.setEmail("john@gmail.com");
        customerAccount.setPassword("HelloJohn7!");
        return Optional.of(customerAccount);
      } else {
        return Optional.empty();
      }
    });
    lenient().when(courseOfferingDao.findById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
      if (invocation.getArgument(0).equals(COURSE_OFFERING_ID)) {
        CourseOffering courseOffering = new CourseOffering();
        courseOffering.setId(COURSE_OFFERING_ID);
        courseOffering.setStartDate(Date.valueOf("2024-01-01")); // Start date = Jan 1st, 2024
        courseOffering.setEndDate(Date.valueOf("2024-02-01")); // End date = Feb 1st, 2024
        return Optional.of(courseOffering);
      } else {
        return Optional.empty();
      }
    });
    lenient().when(paymentInfoDao.findById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
      if (invocation.getArgument(0).equals(PAYMENT_INFO_ID)) {
        PaymentInfo paymentInfo = new PaymentInfo();
        // SET FIELDS
        paymentInfo.setCustomerAccount(customerAccountDao.findById(CUSTOMER_ID).orElse(null));
        return Optional.of(paymentInfo);
      } else {
        return Optional.empty();
      }
    });
    // Whenever anything is saved, just return the parameter object
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };
    lenient().when(customerAccountDao.save(any(CustomerAccount.class))).thenAnswer(returnParameterAsAnswer);
    lenient().when(courseOfferingDao.save(any(CourseOffering.class))).thenAnswer(returnParameterAsAnswer);
    lenient().when(registrationDao.save(any(Registration.class))).thenAnswer(returnParameterAsAnswer);
    /*
    lenient().when(courseTypeDao.save(any(CourseType.class))).thenAnswer(returnParameterAsAnswer);
    lenient().when(roomDao.save(any(Room.class))).thenAnswer(returnParameterAsAnswer);
    lenient().when(instructorAccountDao.save(any(InstructorAccount.class))).thenAnswer(returnParameterAsAnswer);
     */
    lenient().when(paymentInfoDao.save(any(PaymentInfo.class))).thenAnswer(returnParameterAsAnswer);
  }
  @BeforeEach
  @AfterEach
  public void clearDatabase() {
      registrationDao.deleteAll();
      courseOfferingDao.deleteAll();
      paymentInfoDao.deleteAll();
      customerAccountDao.deleteAll();
      /*
      courseTypeDao.deleteAll();
      roomDao.deleteAll();
      instructorAccountDao.deleteAll();
       */
    }
/*
  @Test
  public void testPersistAndLoadRegistration() {
      CourseType courseType = new CourseType("Cardio");
      courseType.setApproved(true);
      courseTypeDao.save(courseType);

      Room testRoom = new Room("Pool", 10, 10, 10);
      roomDao.save(testRoom);

      InstructorAccount testInstructor = new InstructorAccount("ray","raydatray@gmail.com", "password");
      instructorAccountDao.save(testInstructor);

      Date startDate = Date.valueOf("2024-02-18");
      Date endDate = Date.valueOf("2024-03-15");
      ArrayList<DayOfWeek> testDays = new ArrayList<>(List.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));

      CourseOffering testOffering  = new CourseOffering(startDate, endDate, testDays, testRoom, courseType, testInstructor);
      courseOfferingDao.save(testOffering);

      String testName = "joe";
      String testEmail = "joebama@gmail.com";
      String testPassword = "obama";

      CustomerAccount testCustomer = new CustomerAccount(testName, testEmail, testPassword);
      customerAccountDao.save(testCustomer);

      PaymentInfo testPayment = new PaymentInfo(PaymentInfo.PaymentType.Credit, 12345, 123, 24, 6, testCustomer);
      paymentInfoDao.save(testPayment);

      Date testDate = Date.valueOf("2024-03-04");

      Registration testRegistration = new Registration(testDate, testOffering, testCustomer, testPayment);
      registrationDao.save(testRegistration);

      Optional<List<Registration>> readRegistrations = registrationDao.findByCustomerAccount(testCustomer);

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
 */
  /*-------- Tests for createRegistration() --------*/
  @Test
  public void testCreateRegistration() {
    assertEquals(0, registrationService.getAllRegistrations().size());

    Registration registration = null;
    try {
      registration = registrationService.createRegistration(COURSE_OFFERING_ID, CUSTOMER_ID, PAYMENT_INFO_ID, DATE);
    } catch (IllegalArgumentException e) {
      fail();
    }

    assertNotNull(registration);
    assertEquals(COURSE_OFFERING_ID, registration.getCourseOffering().getId());
    assertEquals(CUSTOMER_ID, registration.getCustomerAccount().getId());
    assertEquals(PAYMENT_INFO_ID, registration.getPaymentInfo().getId());
    assertEquals(DATE, registration.getRegisteredDate());
  }

  // can make this impossible to get wrong in frontend
  @Test
  public void testCreateRegistrationWithNullDate() {
    Date registeredDate = null;

    String error = null;
    Registration registration = null;
    try {
      registration = registrationService.createRegistration(COURSE_OFFERING_ID, CUSTOMER_ID, PAYMENT_INFO_ID, registeredDate);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Must register on a date that is not null!", error);
  }

  public void testCreateRegistrationWithNullCourseOffering() {
    CourseOffering courseOffering = null;

    String error = null;
    Registration registration = null;
    try {
      registration = registrationService.createRegistration(courseOffering.getId(), CUSTOMER_ID, PAYMENT_INFO_ID, DATE);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("No course offering was found with the provided information!", error);
  }

  public void testCreateRegistrationWithNullPaymentInfo() {
    PaymentInfo paymentInfo = null;

    String error = null;
    Registration registration = null;
    try {
      registration = registrationService.createRegistration(COURSE_OFFERING_ID, CUSTOMER_ID, paymentInfo.getId(), DATE);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("No payment info was found with the provided information!", error);
  }

  public void testCreateRegistrationWithNullCustomerAccount() {
    CustomerAccount customerAccount = null;

    String error = null;
    Registration registration = null;
    try {
      registration = registrationService.createRegistration(COURSE_OFFERING_ID, customerAccount.getId(), PAYMENT_INFO_ID, DATE);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("No customer account was found with the provided information!", error);
  }

  public void testCreateRegistrationWithInvalidCourseOffering() {
    Integer courseOfferingID = 5;

    String error = null;
    Registration registration = null;
    try {
      registration = registrationService.createRegistration(courseOfferingID, CUSTOMER_ID, PAYMENT_INFO_ID, DATE);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("No course offering was found with the provided information!", error);
  }

  public void testCreateRegistrationWithInvalidCustomerAccount() {
    Integer customerAccountID = 8;

    String error = null;
    Registration registration = null;
    try {
      registration = registrationService.createRegistration(COURSE_OFFERING_ID, customerAccountID, PAYMENT_INFO_ID, DATE);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("No customer account was found with the provided information!", error);
  }

  public void testCreateRegistrationWithInvalidPaymentInfo() {
    Integer paymentinfoID = 6;

    String error = null;
    Registration registration = null;
    try {
      registration = registrationService.createRegistration(COURSE_OFFERING_ID, CUSTOMER_ID, paymentinfoID, DATE);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("No payment info was found with the provided information!", error);
  }

  // create with date after course offering end date
  public void testCreateRegistrationWithDateAfterCourseOfferingEndDate() {
    Date registeredDate = Date.valueOf("2024-03-01");

    String error = null;
    Registration registration = null;
    try {
      registration = registrationService.createRegistration(COURSE_OFFERING_ID, CUSTOMER_ID, PAYMENT_INFO_ID, registeredDate);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("You must register for a course offering at most one day before it starts!", error);
  }
  // create with date after course offering start date
  public void testCreateRegistrationWithDateAfterCourseOfferingStartDate() {
    Date registeredDate = Date.valueOf("2024-02-10");

    String error = null;
    Registration registration = null;
    try {
      registration = registrationService.createRegistration(COURSE_OFFERING_ID, CUSTOMER_ID, PAYMENT_INFO_ID, registeredDate);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("You must register for a course offering at most one day before it starts!", error);
  }

  public void testCreateRegistrationWithSameDateAsCourseOfferingStartDate() {
    Date registeredDate = Date.valueOf("2024-02-01");

    String error = null;
    Registration registration = null;
    try {
      registration = registrationService.createRegistration(COURSE_OFFERING_ID, CUSTOMER_ID, PAYMENT_INFO_ID, registeredDate);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("You must register for a course offering at most one day before it starts!", error);
  }



  /*-------- Tests for getRegistration() --------*/
  public void testGetRegistration() {
    RegistrationId registrationID = new RegistrationId(CUSTOMER_ID, COURSE_OFFERING_ID);

    Registration registration = null;
    try {
      registration = registrationService.getRegistration(registrationID);
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(registration);
    assertEquals(COURSE_OFFERING_ID, registration.getCourseOffering().getId());
    assertEquals(CUSTOMER_ID, registration.getCustomerAccount().getId());
    assertEquals(PAYMENT_INFO_ID, registration.getPaymentInfo().getId());
    assertEquals(DATE, registration.getRegisteredDate());
  }
  public void testGetRegistrationWithNullRegistrationID() {
    RegistrationId registrationID = null;

    String error = null;
    Registration registration = null;
    try {
      registration = registrationService.getRegistration(registrationID);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Registration does not exist!", error);
  }

  public void testGetRegistrationWithNullCustomerAccountID() {
    Integer customerAccountID = null;
    RegistrationId registrationID = new RegistrationId(customerAccountID, COURSE_OFFERING_ID);

    String error = null;
    Registration registration = null;
    try {
      registration = registrationService.getRegistration(registrationID);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Registration does not exist!", error);
  }

  public void testGetRegistrationWithNullCourseOfferingID() {
    Integer courseOfferingID = null;
    RegistrationId registrationID = new RegistrationId(CUSTOMER_ID, courseOfferingID);


    String error = null;
    Registration registration = null;
    try {
      registration = registrationService.getRegistration(registrationID);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Registration does not exist!", error);
  }

  public void testGetRegistrationWithInvalidCustomerAccountID() {
    Integer customerAccountID = 19;
    RegistrationId registrationID = new RegistrationId(customerAccountID, COURSE_OFFERING_ID);

    String error = null;
    Registration registration = null;
    try {
      registration = registrationService.getRegistration(registrationID);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Registration does not exist!", error);
  }

  public void testGetRegistrationWithInvalidCourseOfferingID() {
    Integer courseOfferingID = 6;
    RegistrationId registrationID = new RegistrationId(CUSTOMER_ID, courseOfferingID);


    String error = null;
    Registration registration = null;
    try {
      registration = registrationService.getRegistration(registrationID);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Registration does not exist!", error);
  }


  /*-------- Tests for deleteRegistration() --------*/
  public void testDeleteRegistration() {
    RegistrationId registrationID = new RegistrationId(CUSTOMER_ID, COURSE_OFFERING_ID);

    Registration registration = registrationService.getRegistration(registrationID);
    try {
      registrationService.deleteRegistration(registrationID);
    } catch (IllegalArgumentException e) {
      fail();
    }
    // i think this is all i need
    assertNull(registration);
  }
  public void testDeleteRegistrationWithNullRegistrationID() {
    RegistrationId registrationID = null;

    String error = null;
    Registration registration = null;
    try {
      registrationService.deleteRegistration(registrationID);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Registration does not exist!", error);
  }

  public void testDeleteRegistrationWithNullCustomerAccountID() {
    Integer customerAccountID = null;
    RegistrationId registrationID = new RegistrationId(customerAccountID, COURSE_OFFERING_ID);

    String error = null;
    Registration registration = null;
    try {
      registrationService.deleteRegistration(registrationID);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Registration does not exist!", error);
  }

  public void testDeleteRegistrationWithNullCourseOfferingID() {
    Integer courseOfferingID = null;
    RegistrationId registrationID = new RegistrationId(CUSTOMER_ID, courseOfferingID);


    String error = null;
    Registration registration = null;
    try {
      registrationService.deleteRegistration(registrationID);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Registration does not exist!", error);
  }

  public void testDeleteRegistrationWithInvalidCustomerAccountID() {
    Integer customerAccountID = 19;
    RegistrationId registrationID = new RegistrationId(customerAccountID, COURSE_OFFERING_ID);

    String error = null;
    Registration registration = null;
    try {
      registrationService.deleteRegistration(registrationID);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Registration does not exist!", error);
  }

  public void testDeleteRegistrationWithInvalidCourseOfferingID() {
    Integer courseOfferingID = 6;
    RegistrationId registrationID = new RegistrationId(CUSTOMER_ID, courseOfferingID);


    String error = null;
    Registration registration = null;
    try {
      registrationService.deleteRegistration(registrationID);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Registration does not exist!", error);
  }
}
