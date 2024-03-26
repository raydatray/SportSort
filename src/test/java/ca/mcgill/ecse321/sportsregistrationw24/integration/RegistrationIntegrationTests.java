package ca.mcgill.ecse321.sportsregistrationw24.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.mcgill.ecse321.sportsregistrationw24.dao.*;
import ca.mcgill.ecse321.sportsregistrationw24.dto.RegistrationCO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.RegistrationDto;
import ca.mcgill.ecse321.sportsregistrationw24.dto.RegistrationIdDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.PaymentInfo;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.keys.RegistrationId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.sportsregistrationw24.dao.RegistrationRepository;

import java.sql.Date;
import java.time.DayOfWeek;
import java.util.ArrayList;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RegistrationIntegrationTests {

  @Autowired
  private TestRestTemplate client;

  @Autowired
  private RegistrationRepository registrationRepository;

  @Autowired
  private CourseOfferingRepository courseOfferingRepository;

  @Autowired
  private CustomerAccountRepository customerAccountRepository;

  @Autowired
  private PaymentInfoRepository paymentInfoRepository;


  /* -------- CREATE DEPENDENCIES -------- */
  private int createCustomerAccount(String name, String email, String password) {
    return customerAccountRepository.save(new CustomerAccount(name, email, password)).getId();
  }
  private int createPaymentInfo(PaymentInfo.PaymentType paymentType, Integer cardNumber, Integer cvv, Integer expirationYear, Integer expirationMonth, CustomerAccount customerAccount) {
    return paymentInfoRepository.save(new PaymentInfo(paymentType, cardNumber, cvv, expirationYear, expirationMonth, customerAccount)).getId();
  }
  private int createCourseOffering(Date startDate, Date endDate, ArrayList<DayOfWeek> daysOffered, Room room, CourseType courseType, InstructorAccount instructorAccount) {
    return courseOfferingRepository.save(new CourseOffering(startDate, endDate, daysOffered, room, courseType, instructorAccount)).getId();
  }


  @BeforeEach
  @AfterEach
  public void clearDatabase() {
    customerAccountRepository.deleteAll();
    courseOfferingRepository.deleteAll();
    paymentInfoRepository.deleteAll();
    registrationRepository.deleteAll();
  }
/*
  @Test
  public void testCreateandGetRegistration() {

    CustomerAccount customer = new CustomerAccount("joe", "joe@gmail.com",  "Password7!");
    PaymentInfo paymentInfo = new PaymentInfo(PaymentInfo.PaymentType.Credit, 1234, 729, 2024, 12, customer);
    InstructorAccount instructor = new InstructorAccount("Chris Bumstead", "cbum@yahoo.com", "iLuvCourtney4evr!");
    ArrayList<DayOfWeek> daysOffered = new ArrayList<>();
    daysOffered.add(DayOfWeek.TUESDAY);
    daysOffered.add(DayOfWeek.THURSDAY);
    CourseType courseType = new CourseType("weightlifting");
    Room room = new Room("Weight Room", 2, 3, 40);
    CourseOffering courseOffering = new CourseOffering(Date.valueOf("2024-04-01"), Date.valueOf("2024-05-01"), daysOffered, room, courseType, instructor);

    Integer courseOfferingID = courseOffering.getId();
    Integer customerAccountID = customer.getId();
    Integer paymentInfoID = paymentInfo.getId();
    Date registrationDate = Date.valueOf("2024-03-10");


    RegistrationIdDto registrationID = testCreateRegistration(courseOfferingID, customerAccountID, paymentInfoID, registrationDate);
    testGetRegistration(registrationID);
  }
*/
  private RegistrationIdDto testCreateRegistration(Integer courseOfferingId, Integer customerAccountId, Integer paymentInfoId, Date registrationDate) {
    ResponseEntity<RegistrationCO> response = client.postForEntity("/registrations/create", new RegistrationCO(courseOfferingId, customerAccountId, paymentInfoId, registrationDate), RegistrationCO.class);

    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals(courseOfferingId, response.getBody().getCourseOfferingId(), "Response has correct course offering ID");
    assertEquals(customerAccountId, response.getBody().getCustomerAccountId(), "Response has correct customer account ID");
    assertEquals(paymentInfoId, response.getBody().getCourseOfferingId(), "Response has correct payment info ID");
    assertEquals(registrationDate, response.getBody().getRegistrationDate(), "Response has correct registration date");

    return new RegistrationIdDto(customerAccountId, courseOfferingId);
  }

  private void testGetRegistration(RegistrationIdDto registrationID) {
    ResponseEntity<RegistrationDto> response = client.getForEntity("/registrations/get/", RegistrationDto.class);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals(registrationID.getCourseOfferingId(), response.getBody().getRegistrationId().getCourseOfferingId(), "Response has correct course offering ID");
    assertEquals(registrationID.getCustomerAccountId(), response.getBody().getRegistrationId().getCustomerAccountId(), "Response has correct customer account ID");
  }

/*
  @Test
  public void testCreateInvalidRegistration() {
    Date registrationDate = Date.valueOf("2024-06-30");
    CustomerAccount customer = new CustomerAccount("joe", "joe@gmail.com",  "Password7!");
    PaymentInfo paymentInfo = new PaymentInfo(PaymentInfo.PaymentType.Credit, 1234, 729, 2024, 12, customer);
    InstructorAccount instructor = new InstructorAccount("Chris Bumstead", "cbum@yahoo.com", "iLuvCourtney4evr!");
    ArrayList<DayOfWeek> daysOffered = new ArrayList<>();
    daysOffered.add(DayOfWeek.TUESDAY);
    daysOffered.add(DayOfWeek.THURSDAY);
    CourseType courseType = new CourseType("weightlifting");
    Room room = new Room("Weight Room", 2, 3, 40);
    CourseOffering courseOffering = new CourseOffering(Date.valueOf("2024-04-01"), Date.valueOf("2024-05-01"), daysOffered, room, courseType, instructor);

    Integer courseOfferingID = courseOffering.getId();
    Integer customerAccountID = customer.getId();
    Integer paymentInfoID = paymentInfo.getId();

    ResponseEntity<String> response = client.postForEntity("/registrations/create/", new RegistrationCO(courseOfferingID, customerAccountID, paymentInfoID, registrationDate), String.class);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
  }

  @Test
  public void testGetInvalidRegistration() {

    ResponseEntity<String> response = client.getForEntity("/registrations/get/", new RegistrationIdDto(Integer.MAX_VALUE, Integer.MAX_VALUE), String.class);

    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
    assertEquals("Registration does not exist!", response.getBody(), "Response has correct error message");
  }

 */
}
