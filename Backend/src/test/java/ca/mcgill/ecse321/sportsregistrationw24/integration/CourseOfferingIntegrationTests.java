package ca.mcgill.ecse321.sportsregistrationw24.integration;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseTypeRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.RoomRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dto.CourseOffering.CourseOfferingCO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.CourseOffering.CourseOfferingDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import ca.mcgill.ecse321.sportsregistrationw24.service.AuthenticationService;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.TimeZone;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseOfferingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseOfferingIntegrationTests {
  @Autowired
  private TestRestTemplate client;

  @Autowired
  private CourseTypeRepository courseTypeRepository;

  @Autowired
  private CourseOfferingRepository courseOfferingRepository;

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private UserAccountRepository userAccountRepository;

  @Autowired
  private RoomRepository roomRepository;

  private Integer createInstructorAccount(String name, String email, String password) { return userAccountRepository.save(new InstructorAccount(name, email, password)).getId(); }
  private String loginInstructorAccount(String email, String password) { return authenticationService.login(email, password).getToken(); }
  private Integer createCourseType(String courseName) { return courseTypeRepository.save(new CourseType(courseName)).getId(); }
  private Integer createRoom(String roomName, Integer roomNumber, Integer floorNumber, Integer capacity ) { return roomRepository.save(new Room(roomName, floorNumber, roomNumber, capacity)).getId(); }
  private Integer createCourseOffering(Date startDate, Date endDate, ArrayList<DayOfWeek> daysOffered, Room room, CourseType courseType, InstructorAccount instructorAccount) { return courseOfferingRepository.save(new CourseOffering(startDate, endDate, daysOffered, room, courseType, instructorAccount)).getId(); }

  @BeforeEach
  public void setUp() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }

  @AfterEach
  public void clearDatabase() {
    courseOfferingRepository.deleteAll();
    courseTypeRepository.deleteAll();
    userAccountRepository.deleteAll();
    roomRepository.deleteAll();
  }

  @Test
  public void testCreateGetDeleteCourseOffering() {
    String testInstructorName = "Joe";
    String testInstructorEmail = "joe@gmail.com";
    String testInstructorPassword = "123";

    Integer instructorId = createInstructorAccount(testInstructorName, testInstructorEmail, testInstructorPassword);
    String token = loginInstructorAccount(testInstructorEmail, testInstructorPassword);


    String testCourseType = "Soccer";
    Integer courseTypeID = createCourseType(testCourseType);

    String testRoom = "Room1";
    Integer testRoomNumber = 1;
    Integer testFloorNumber = 1;
    Integer testCapacity = 10;
    Integer roomID = createRoom(testRoom, testRoomNumber, testFloorNumber, testCapacity);


    Date startDate = Date.valueOf("2022-01-01");
    Date endDate = Date.valueOf("2022-04-01");
    ArrayList<DayOfWeek> daysOffered = new ArrayList<>(List.of(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY));


    ResponseEntity<?> response = client.postForEntity("/courseOfferings/create", new CourseOfferingCO(startDate, endDate, daysOffered, token, roomID, courseTypeID), String.class);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    CourseOffering createdCourseOffering = courseOfferingRepository.findAll().iterator().next(); //Get first element

    assertNotNull(createdCourseOffering);
    assertEquals(startDate, createdCourseOffering.getStartDate());
    assertEquals(endDate, createdCourseOffering.getEndDate());
    assertEquals(daysOffered, createdCourseOffering.getDaysOffered());
    assertEquals(roomID, createdCourseOffering.getRoom().getId());
    assertEquals(courseTypeID, createdCourseOffering.getCourseType().getId());
    assertEquals(instructorId, createdCourseOffering.getInstructorAccount().getId());

    Integer courseOfferingID = createdCourseOffering.getId();

    HttpHeaders headers = new HttpHeaders();
    headers.set("userToken", token);
    HttpEntity<String> entity = new HttpEntity<>(headers);

    String requestURL1 = "/courseOfferings/get?id=" + courseOfferingID;

    ResponseEntity<CourseOfferingDto> response2 = client.exchange(requestURL1, HttpMethod.GET, entity, CourseOfferingDto.class);

    assertNotNull(response2);
    assertEquals(HttpStatus.OK, response2.getStatusCode());
    assertEquals(startDate, response2.getBody().getStartDate());
    assertEquals(endDate, response2.getBody().getEndDate());
    assertEquals(daysOffered, response2.getBody().getDaysOffered());
    assertEquals(roomID, response2.getBody().getRoom().getId());

    String requestURL2 = "/courseOfferings/delete?id=" + courseOfferingID;

    ResponseEntity<?> response3 = client.exchange(requestURL2, HttpMethod.DELETE, entity, String.class);

    assertNotNull(response3);
    assertEquals(HttpStatus.OK, response3.getStatusCode());
    assertEquals("Course offering deleted successfully!", response3.getBody());
    assertFalse(courseOfferingRepository.findById(courseOfferingID).isPresent());
  }

}
