package ca.mcgill.ecse321.sportsregistrationw24.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.mcgill.ecse321.sportsregistrationw24.dao.*;
import ca.mcgill.ecse321.sportsregistrationw24.dto.RegistrationCO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.RegistrationDto;
import ca.mcgill.ecse321.sportsregistrationw24.dto.RegistrationIdDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;
import ca.mcgill.ecse321.sportsregistrationw24.model.keys.RegistrationId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.sportsregistrationw24.dao.RegistrationRepository;

import java.net.URI;
import java.sql.Date;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.TimeZone;

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

  @Autowired
  private InstructorAccountRepository instructorAccountRepository;

  @Autowired
  private CourseTypeRepository courseTypeRepository;

  @Autowired
  private RoomRepository roomRepository;





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
  private int createInstructorAccount(String name, String email, String password) {
    return instructorAccountRepository.save(new InstructorAccount(name, email, password)).getId();
  }
  private int createRoom(String name, Integer floorNumber, Integer roomNumber, Integer capacity) {
    return roomRepository.save(new Room(name, floorNumber, roomNumber, capacity)).getId();
  }
  private int createCourseType(String name) {
    return courseTypeRepository.save(new CourseType(name)).getId();
  }


  @BeforeEach
  public void setUp() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }

  @BeforeEach
  public void clearDatabase() {
    registrationRepository.deleteAll();
    courseOfferingRepository.deleteAll();
    courseTypeRepository.deleteAll();
    roomRepository.deleteAll();
    paymentInfoRepository.deleteAll();
    customerAccountRepository.deleteAll();
    instructorAccountRepository.deleteAll();
  }


  @Test
  public void testCreateAndGetRegistration() {
    Integer instructorID = createInstructorAccount("Chris Bumstead", "cbum@yahoo.com", "iLuvCourtney4evr!");
    ArrayList<DayOfWeek> daysOffered = new ArrayList<>();
    daysOffered.add(DayOfWeek.TUESDAY);
    daysOffered.add(DayOfWeek.THURSDAY);


    Integer roomID = createRoom("Weight Room", 2, 3, 40);

    // DTO stuff
    String courseName = "weightlifting";
    Integer cardNumber = 1234;
    String cardNumStr = cardNumber.toString();
    Integer registrationPrice = 100;


    Integer courseTypeID = createCourseType(courseName);
    // CO stuff
    Integer courseOfferingID = createCourseOffering(Date.valueOf("2024-04-01"), Date.valueOf("2024-05-01"), daysOffered, roomRepository.findById(roomID).get(), courseTypeRepository.findById(courseTypeID).get(), instructorAccountRepository.findById(instructorID).get());
    Integer customerAccountID = createCustomerAccount("joe", "joe@gmail.com",  "Password7!");
    Integer paymentInfoID = createPaymentInfo(PaymentInfo.PaymentType.Credit, cardNumber, 729, 2024, 12, customerAccountRepository.findById(customerAccountID).get());
    Date registrationDate = Date.valueOf("2024-03-10");


    RegistrationIdDto registrationID = testCreateRegistration(courseOfferingID, customerAccountID, paymentInfoID, registrationDate, courseName, cardNumStr, registrationPrice);
    testGetRegistration(registrationID);
  }

  private RegistrationIdDto testCreateRegistration(Integer courseOfferingId, Integer customerAccountId, Integer paymentInfoId, Date registrationDate, String courseTypeName, String paymentInfoCardNumber, Integer registrationPrice) {
    ResponseEntity<RegistrationDto> response = client.postForEntity("/registrations/create", new RegistrationCO(courseOfferingId, customerAccountId, paymentInfoId, registrationDate), RegistrationDto.class);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    //assertEquals("Registration was created successfully!", response.getBody());

    assertEquals(courseOfferingId, response.getBody().getRegistrationId().getCourseOfferingId(), "Response has correct course offering ID (for the registration ID)");
    assertEquals(customerAccountId, response.getBody().getRegistrationId().getCustomerAccountId(), "Response has correct customer account ID (for the registration ID)");
    assertEquals(courseTypeName, response.getBody().getCourseTypeName(), "Response has correct course type name");
    assertEquals(paymentInfoId, response.getBody().getPaymentInfoID(), "Response has correct payment info ID");
    assertEquals(paymentInfoCardNumber, response.getBody().getPaymentInfoCardNumber(), "Response has correct payment info card number");
    assertEquals(registrationDate, response.getBody().getRegistrationDate(), "Response has correct registration date");
    assertEquals(registrationPrice, response.getBody().getRegistrationPrice(), "Response has correct registration price");

    return new RegistrationIdDto(customerAccountId, courseOfferingId);
  }

  private void testGetRegistration(RegistrationIdDto registrationID) {
    Integer customerAccountID = registrationID.getCustomerAccountId();
    Integer courseOfferingID = registrationID.getCourseOfferingId();
    ResponseEntity<RegistrationDto> response = client.getForEntity("/registrations/get?customerAccountID=" + customerAccountID + "&courseOfferingID=" + courseOfferingID, RegistrationDto.class);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals(registrationID.getCourseOfferingId(), response.getBody().getRegistrationId().getCourseOfferingId(), "Response has correct course offering ID");
    assertEquals(registrationID.getCustomerAccountId(), response.getBody().getRegistrationId().getCustomerAccountId(), "Response has correct customer account ID");
  }

  @Test
  public void testCreateDeleteRegistration() {
    Integer instructorID = createInstructorAccount("Michael Phelps", "mphelps@yahoo.com", "swimmingIsEZL0L!");
    ArrayList<DayOfWeek> daysOffered = new ArrayList<>();
    daysOffered.add(DayOfWeek.TUESDAY);
    daysOffered.add(DayOfWeek.THURSDAY);

    Integer roomID = createRoom("Pool 1", 2, 3, 40);

    // DTO stuff
    String courseName = "swimming";
    Integer cardNumber = 5678;
    String cardNumStr = cardNumber.toString();
    Integer registrationPrice = 100;

    Integer courseTypeID = createCourseType(courseName);
    // CO stuff
    Integer courseOfferingID = createCourseOffering(Date.valueOf("2024-04-01"), Date.valueOf("2024-05-01"), daysOffered, roomRepository.findById(roomID).get(), courseTypeRepository.findById(courseTypeID).get(), instructorAccountRepository.findById(instructorID).get());
    Integer customerAccountID = createCustomerAccount("bob", "bob@gmail.com",  "Password_9!");
    Integer paymentInfoID = createPaymentInfo(PaymentInfo.PaymentType.Credit, cardNumber, 813, 2025, 5, customerAccountRepository.findById(customerAccountID).get());
    Date registrationDate = Date.valueOf("2024-03-10");


    RegistrationIdDto registrationID = testCreateRegistration(courseOfferingID, customerAccountID, paymentInfoID, registrationDate, courseName, cardNumStr, registrationPrice);
    testDeleteRegistration(registrationID);
  }

  private void testDeleteRegistration(RegistrationIdDto registrationId) {
    Integer customerAccountID = registrationId.getCustomerAccountId();
    Integer courseOfferingID = registrationId.getCourseOfferingId();
    String endpointURL = "/registrations/delete?customerAccountID=" + customerAccountID + "&courseOfferingID=" + courseOfferingID;
    ResponseEntity<String> response = client.exchange(endpointURL, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);

    assertNotNull(response);
    assertNotNull(response.getBody(), "Response has body");
    assertEquals("Registration has been successfully deleted!", response.getBody());
  }

  @Test
  public void testCreateAndGetAllRegistrations() {
    // CREATE REGISTRATION 1
    Integer instructorID1 = createInstructorAccount("Chris Bumstead", "cbum@yahoo.com", "iLuvCourtney4evr!");
    ArrayList<DayOfWeek> daysOffered1 = new ArrayList<>();
    daysOffered1.add(DayOfWeek.MONDAY);
    daysOffered1.add(DayOfWeek.SATURDAY);
    Integer roomID1 = createRoom("Weight Room", 2, 3, 40);

    // DTO stuff
    String courseName1 = "weightlifting";
    Integer cardNumber1 = 1234;
    String cardNumStr1 = cardNumber1.toString();
    Integer registrationPrice1 = 100;

    Integer courseTypeID1 = createCourseType(courseName1);
    // CO stuff
    Integer courseOfferingID1 = createCourseOffering(Date.valueOf("2024-04-01"), Date.valueOf("2024-05-01"), daysOffered1, roomRepository.findById(roomID1).get(), courseTypeRepository.findById(courseTypeID1).get(), instructorAccountRepository.findById(instructorID1).get());
    Integer customerAccountID1 = createCustomerAccount("joe", "joe@gmail.com",  "Password7!");
    Integer paymentInfoID1 = createPaymentInfo(PaymentInfo.PaymentType.Credit, cardNumber1, 729, 2024, 12, customerAccountRepository.findById(customerAccountID1).get());
    Date registrationDate1 = Date.valueOf("2024-03-10");
    testCreateRegistration(courseOfferingID1, customerAccountID1, paymentInfoID1, registrationDate1, courseName1, cardNumStr1, registrationPrice1);


    // CREATE REGISTRATION 2
    Integer instructorID2 = createInstructorAccount("Michael Phelps", "mphelps@yahoo.com", "swimmingIsEZL0L!");
    ArrayList<DayOfWeek> daysOffered2 = new ArrayList<>();
    daysOffered2.add(DayOfWeek.TUESDAY);
    daysOffered2.add(DayOfWeek.THURSDAY);
    Integer roomID2 = createRoom("Pool 1", 2, 3, 40);

    // DTO stuff
    String courseName2 = "swimming";
    Integer cardNumber2 = 5678;
    String cardNumStr2 = cardNumber2.toString();
    Integer registrationPrice2 = 100;

    Integer courseTypeID2 = createCourseType(courseName2);
    // CO stuff
    Integer courseOfferingID2 = createCourseOffering(Date.valueOf("2024-04-01"), Date.valueOf("2024-05-01"), daysOffered2, roomRepository.findById(roomID2).get(), courseTypeRepository.findById(courseTypeID2).get(), instructorAccountRepository.findById(instructorID2).get());
    Integer customerAccountID2 = createCustomerAccount("bob", "bob@gmail.com",  "Password_9!");
    Integer paymentInfoID2 = createPaymentInfo(PaymentInfo.PaymentType.Credit, cardNumber2, 813, 2025, 5, customerAccountRepository.findById(customerAccountID2).get());
    Date registrationDate2 = Date.valueOf("2024-03-10");
    testCreateRegistration(courseOfferingID2, customerAccountID2, paymentInfoID2, registrationDate2, courseName2, cardNumStr2, registrationPrice2);

    ResponseEntity<ArrayList> response = client.getForEntity("/registrations/getAll", ArrayList.class);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertEquals(2, response.getBody().size(), "Response has correct size");
  }


  /* -------- INVALID TESTS --------*/

  // no payment info
  @Test
  public void testCreateInvalidRegistration() {
    Date registrationDate = Date.valueOf("2024-06-30");
    Integer customerId = createCustomerAccount("joe", "joe@gmail.com",  "Password7!");
    Integer paymentInfoId = createPaymentInfo(PaymentInfo.PaymentType.Credit, 1234, 729, 2024, 12, customerAccountRepository.findById(customerId).get());
    Integer instructorId = createInstructorAccount("Chris Bumstead", "cbum@yahoo.com", "iLuvCourtney4evr!");
    ArrayList<DayOfWeek> daysOffered = new ArrayList<>();
    daysOffered.add(DayOfWeek.TUESDAY);
    daysOffered.add(DayOfWeek.THURSDAY);
    Integer courseTypeId = createCourseType("weightlifting");
    Integer roomId = createRoom("Weight Room", 2, 3, 40);
    Integer courseOfferingId = createCourseOffering(Date.valueOf("2024-04-01"), Date.valueOf("2024-05-01"), daysOffered, roomRepository.findById(roomId).get(), courseTypeRepository.findById(courseTypeId).get(), instructorAccountRepository.findById(instructorId).get());


    ResponseEntity<String> response1 = client.postForEntity("/registrations/create/", new RegistrationCO(courseOfferingId, customerId, null, registrationDate), String.class);
    assertNotNull(response1);
    assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode(), "Response has correct status");

    ResponseEntity<String> response2 = client.postForEntity("/registrations/create/", new RegistrationCO(courseOfferingId, customerId, paymentInfoId, null), String.class);
    assertNotNull(response2);
    assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode(), "Response has correct status");

    ResponseEntity<String> response3 = client.postForEntity("/registrations/create/", new RegistrationCO(courseOfferingId, null, paymentInfoId, registrationDate), String.class);
    assertNotNull(response3);
    assertEquals(HttpStatus.BAD_REQUEST, response3.getStatusCode(), "Response has correct status");

    ResponseEntity<String> response4 = client.postForEntity("/registrations/create/", new RegistrationCO(null, customerId, paymentInfoId, registrationDate), String.class);
    assertNotNull(response4);
    assertEquals(HttpStatus.BAD_REQUEST, response4.getStatusCode(), "Response has correct status");
  }

  @Test
  public void testGetInvalidRegistration() {
    Integer instructorID = createInstructorAccount("Chris Bumstead", "cbum@yahoo.com", "iLuvCourtney4evr!");
    ArrayList<DayOfWeek> daysOffered = new ArrayList<>();
    daysOffered.add(DayOfWeek.TUESDAY);
    daysOffered.add(DayOfWeek.THURSDAY);


    Integer roomID = createRoom("Weight Room", 2, 3, 40);

    // DTO stuff
    String courseName = "weightlifting";
    Integer cardNumber = 1234;
    String cardNumStr = cardNumber.toString();
    Integer registrationPrice = 100;


    Integer courseTypeID = createCourseType(courseName);
    // CO stuff
    Integer courseOfferingID = createCourseOffering(Date.valueOf("2024-04-01"), Date.valueOf("2024-05-01"), daysOffered, roomRepository.findById(roomID).get(), courseTypeRepository.findById(courseTypeID).get(), instructorAccountRepository.findById(instructorID).get());
    Integer customerAccountID = createCustomerAccount("joe", "joe@gmail.com",  "Password7!");
    Integer paymentInfoID = createPaymentInfo(PaymentInfo.PaymentType.Credit, cardNumber, 729, 2024, 12, customerAccountRepository.findById(customerAccountID).get());
    Date registrationDate = Date.valueOf("2024-03-10");


    RegistrationIdDto registrationID = testCreateRegistration(courseOfferingID, customerAccountID, paymentInfoID, registrationDate, courseName, cardNumStr, registrationPrice);

    // 2 bad IDs
    String endpointURL1 = "/registrations/get?customerAccountID="+ Integer.MAX_VALUE + "&courseOfferingID=" + Integer.MAX_VALUE;
    ResponseEntity<String> response1 = client.getForEntity(endpointURL1, String.class);

    assertNotNull(response1);
    assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode(), "Response has correct status");
    assertEquals("Registration does not exist!", response1.getBody(), "Response has correct error message");

    // good customerId, bad courseOfferingId
    String endpointURL2 = "/registrations/get?customerAccountID=" + customerAccountID + "&courseOfferingID=" + Integer.MAX_VALUE;
    ResponseEntity<String> response2 = client.getForEntity(endpointURL2, String.class);

    assertNotNull(response2);
    assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode(), "Response has correct status");
    assertEquals("Registration does not exist!", response2.getBody(), "Response has correct error message");

    // bad customerId, good courseOfferingId
    String endpointURL3 = "/registrations/get?customerAccountID=" + Integer.MAX_VALUE + "&courseOfferingID=" + courseOfferingID;
    ResponseEntity<String> response3 = client.getForEntity(endpointURL3, String.class);

    assertNotNull(response3);
    assertEquals(HttpStatus.BAD_REQUEST, response3.getStatusCode(), "Response has correct status");
    assertEquals("Registration does not exist!", response3.getBody(), "Response has correct error message");

    // null IDs
    String endpointURL4 = "/registrations/get?customerAccountID=&courseOfferingID=";
    ResponseEntity<String> response4 = client.getForEntity(endpointURL4, String.class);

    assertNotNull(response4);
    assertEquals(HttpStatus.BAD_REQUEST, response4.getStatusCode(), "Response has correct status");
    //assertEquals("Registration does not exist!", response5.getBody(), "Response has correct error message");

    // good customerId, null courseOfferingId
    String endpointURL5 = "/registrations/get?customerAccountID=" + customerAccountID + "&courseOfferingID=";
    ResponseEntity<String> response5 = client.getForEntity(endpointURL5, String.class);

    assertNotNull(response5);
    assertEquals(HttpStatus.BAD_REQUEST, response5.getStatusCode(), "Response has correct status");
    //assertEquals("Registration does not exist!", response5.getBody(), "Response has correct error message");

    // null customerId, good courseOfferingId
    String endpointURL6 = "/registrations/get?customerAccountID=&courseOfferingID=" + courseOfferingID;
    ResponseEntity<String> response6 = client.getForEntity(endpointURL6, String.class);

    assertNotNull(response6);
    assertEquals(HttpStatus.BAD_REQUEST, response6.getStatusCode(), "Response has correct status");
    //assertEquals("Registration does not exist!", response5.getBody(), "Response has correct error message");
  }

  //getAll invalid

  //delete invalid
  @Test
  public void testDeleteInvalidRegistration() {
    Integer instructorID = createInstructorAccount("Chris Bumstead", "cbum@yahoo.com", "iLuvCourtney4evr!");
    ArrayList<DayOfWeek> daysOffered = new ArrayList<>();
    daysOffered.add(DayOfWeek.TUESDAY);
    daysOffered.add(DayOfWeek.THURSDAY);
    Integer roomID = createRoom("Weight Room", 2, 3, 40);

    // DTO stuff
    String courseName = "weightlifting";
    Integer cardNumber = 1234;
    String cardNumStr = cardNumber.toString();
    Integer registrationPrice = 100;

    Integer courseTypeID = createCourseType(courseName);
    // CO stuff
    Integer courseOfferingID = createCourseOffering(Date.valueOf("2024-04-01"), Date.valueOf("2024-05-01"), daysOffered, roomRepository.findById(roomID).get(), courseTypeRepository.findById(courseTypeID).get(), instructorAccountRepository.findById(instructorID).get());
    Integer customerAccountID = createCustomerAccount("joe", "joe@gmail.com",  "Password7!");
    Integer paymentInfoID = createPaymentInfo(PaymentInfo.PaymentType.Credit, cardNumber, 729, 2024, 12, customerAccountRepository.findById(customerAccountID).get());
    Date registrationDate = Date.valueOf("2024-03-10");

    testCreateRegistration(courseOfferingID, customerAccountID, paymentInfoID, registrationDate, courseName, cardNumStr, registrationPrice);
    Long registrationListSize = registrationRepository.count();

    // 2 bad IDs
    String endpointURL1 = "/registrations/delete?customerAccountID="+ Integer.MAX_VALUE + "&courseOfferingID=" + Integer.MAX_VALUE;
    ResponseEntity<String> response1 = client.exchange(endpointURL1, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
    assertNotNull(response1);
    assertEquals(1, registrationListSize, "Request has not deleted anything");
    assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode(), "Response has correct status");
    assertEquals("Registration does not exist!", response1.getBody(), "Response has correct error message");

    // good customerId, bad courseOfferingId
    String endpointURL2 = "/registrations/delete?customerAccountID=" + customerAccountID + "&courseOfferingID=" + Integer.MAX_VALUE;
    ResponseEntity<String> response2 = client.exchange(endpointURL2, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
    assertNotNull(response2);
    assertEquals(1, registrationListSize, "Request has not deleted anything");
    assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode(), "Response has correct status");
    assertEquals("Registration does not exist!", response2.getBody(), "Response has correct error message");

    // bad customerId, good courseOfferingId
    String endpointURL3 = "/registrations/delete?customerAccountID=" + Integer.MAX_VALUE + "&courseOfferingID=" + courseOfferingID;
    ResponseEntity<String> response3 = client.exchange(endpointURL3, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
    assertNotNull(response3);
    assertEquals(1, registrationListSize, "Request has not deleted anything");
    assertEquals(HttpStatus.BAD_REQUEST, response3.getStatusCode(), "Response has correct status");
    assertEquals("Registration does not exist!", response3.getBody(), "Response has correct error message");

    // null IDs
    String endpointURL4 = "/registrations/delete?customerAccountID=&courseOfferingID=";
    ResponseEntity<String> response4 = client.exchange(endpointURL4, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
    assertNotNull(response4);
    assertEquals(1, registrationListSize, "Request has not deleted anything");
    assertEquals(HttpStatus.BAD_REQUEST, response4.getStatusCode(), "Response has correct status");
    //assertEquals("Registration does not exist!", response5.getBody(), "Response has correct error message");

    // good customerId, null courseOfferingId
    String endpointURL5 = "/registrations/delete?customerAccountID=" + customerAccountID + "&courseOfferingID=";
    ResponseEntity<String> response5 = client.exchange(endpointURL5, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
    assertNotNull(response5);
    assertEquals(1, registrationListSize, "Request has not deleted anything");
    assertEquals(HttpStatus.BAD_REQUEST, response5.getStatusCode(), "Response has correct status");
    //assertEquals("Registration does not exist!", response5.getBody(), "Response has correct error message");

    // null customerId, good courseOfferingId
    String endpointURL6 = "/registrations/delete?customerAccountID=&courseOfferingID=" + courseOfferingID;
    ResponseEntity<String> response6 = client.exchange(endpointURL6, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
    assertNotNull(response6);
    assertEquals(1, registrationListSize, "Request has not deleted anything");
    assertEquals(HttpStatus.BAD_REQUEST, response6.getStatusCode(), "Response has correct status");
    //assertEquals("Registration does not exist!", response5.getBody(), "Response has correct error message");
  }

}
