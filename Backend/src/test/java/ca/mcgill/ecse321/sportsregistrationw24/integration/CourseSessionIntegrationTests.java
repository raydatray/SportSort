package ca.mcgill.ecse321.sportsregistrationw24.integration;

import ca.mcgill.ecse321.sportsregistrationw24.dao.*;
import ca.mcgill.ecse321.sportsregistrationw24.dto.courseSession.CourseSessionDto;
import ca.mcgill.ecse321.sportsregistrationw24.dto.courseSession.multipleClassSessionsCO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.courseSession.singleCourseSessionCO;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseSessionIntegrationTests {
  @Autowired
  private TestRestTemplate client;
  @Autowired
  private CourseSessionRepository courseSessionRepository;
  @Autowired
  private CourseOfferingRepository courseOfferingRepository;
  @Autowired
  private InstructorAccountRepository instructorAccountRepository;
  @Autowired
  private RoomRepository roomRepository;
  @Autowired
  private CourseTypeRepository courseTypeRepository;

  // CREATE DEPENDENCIES
  private int createInstructorAccount(String name, String email, String password) {
    return instructorAccountRepository.save(new InstructorAccount(name, email, password)).getId();
  }
  private int createRoom(String name, Integer floorNumber, Integer roomNumber, Integer capacity) {
    return roomRepository.save(new Room(name, floorNumber, roomNumber, capacity)).getId();
  }
  private int createCourseType(String name) {
    return courseTypeRepository.save(new CourseType(name)).getId();
  }
  private int createCourseOffering(Date startDate, Date endDate, ArrayList<DayOfWeek> daysOffered, Room room, CourseType courseType, InstructorAccount instructorAccount) {
    return courseOfferingRepository.save(new CourseOffering(startDate, endDate, daysOffered, room, courseType, instructorAccount)).getId();
  }

  @BeforeEach
  public void setUp() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }
  @AfterEach
  public void clearDatabase() {
    courseSessionRepository.deleteAll();
    courseOfferingRepository.deleteAll();
    instructorAccountRepository.deleteAll();
    roomRepository.deleteAll();
    courseTypeRepository.deleteAll();
  }

  @Test
  public void testCreateAndGetCourseSession() {
    // SETTING UP DEPENDENCIES
    Date startDate = Date.valueOf("2024-01-01");
    Date endDate = Date.valueOf("2024-01-07");
    ArrayList<DayOfWeek> daysOffered = new ArrayList<>();
    daysOffered.add(DayOfWeek.MONDAY);
    daysOffered.add(DayOfWeek.THURSDAY);
    Integer instructorId = createInstructorAccount("John Doe", "johndoe@gmail.com", "123456");
    Integer roomId = createRoom("Room 1", 1, 1, 30);
    Integer courseTypeId = createCourseType("Test Class");
    Integer courseOfferingId = createCourseOffering(startDate, endDate, daysOffered, roomRepository.findById(roomId).get(), courseTypeRepository.findById(courseTypeId).get(), instructorAccountRepository.findById(instructorId).get());

    Date date = Date.valueOf("2024-01-01");
    Time startTime = Time.valueOf(LocalTime.of(12, 0));
    Time endTime = Time.valueOf(LocalTime.of(13, 0));

    int id = testCreateCourseSession(date, startTime, endTime, courseOfferingId);
    testGetCourseSession(id);
  }

  private int testCreateCourseSession(Date date, Time startTime, Time endTime, Integer courseOfferingId) {
    ResponseEntity<CourseSessionDto> response = client.postForEntity("/courseSession/createSession", new singleCourseSessionCO(date, startTime, endTime, courseOfferingId), CourseSessionDto.class);

    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals(date, response.getBody().getDate(), "Response has correct date");
    assertEquals(startTime, response.getBody().getStartTime(), "Response has correct start time");
    assertEquals(endTime, response.getBody().getEndTime(), "Response has correct end time");
    assertTrue(response.getBody().getId() > 0, "Response has valid ID");

    return response.getBody().getId();
  }

  private void testGetCourseSession(int id) {
    ResponseEntity<CourseSessionDto> response = client.getForEntity("/courseSession/getSession?courseSessionId=" + id, CourseSessionDto.class);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals(id, response.getBody().getId(), "Response has correct ID");
  }

  @Test
  public void testCreateInvalidCourseSession() {
    Date date = Date.valueOf("2024-01-30");
    Time startTime = Time.valueOf(LocalTime.of(12, 0));
    Time endTime = Time.valueOf(LocalTime.of(13, 0));

    ResponseEntity<String> response = client.postForEntity("/courseSession/createSession", new singleCourseSessionCO(date, startTime, endTime, 1), String.class);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
  }

  @Test
  public void testGetInvalidCourseSession() {
    ResponseEntity<String> response = client.getForEntity("/courseSession/getSession?courseSessionId=" + Integer.MAX_VALUE, String.class);

    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
    assertEquals("Course session was not found", response.getBody(), "Response has correct error message");
  }

  @Test
  public void testDeleteCourseSession() {
    Integer id = 4;
    ResponseEntity<String> response = client.exchange("/courseSession/deleteSession?sessionId=" + id, HttpMethod.DELETE, null, String.class);

    assertNotNull(response);
    assertEquals("Course Session deleted successfully", response.getBody());
  }

  @Test
  public void testCreateAndGetAllSessions() {
    // SETTING UP DEPENDENCIES
    Date startDate = Date.valueOf("2024-01-01");
    Date endDate = Date.valueOf("2024-01-07");
    ArrayList<DayOfWeek> daysOffered = new ArrayList<>();
    daysOffered.add(DayOfWeek.MONDAY);
    daysOffered.add(DayOfWeek.THURSDAY);
    Integer instructorId = createInstructorAccount("John Doe", "johndoe@gmail.com", "123456");
    Integer roomId = createRoom("Room 1", 1, 1, 30);
    Integer courseTypeId = createCourseType("Test Class");
    Integer courseOfferingId = createCourseOffering(startDate, endDate, daysOffered, roomRepository.findById(roomId).get(), courseTypeRepository.findById(courseTypeId).get(), instructorAccountRepository.findById(instructorId).get());

    Date date1 = Date.valueOf("2024-01-01");
    Time startTime1 = Time.valueOf(LocalTime.of(12, 0));
    Time endTime1 = Time.valueOf(LocalTime.of(13, 0));

    Date date2 = Date.valueOf("2024-01-02");
    Time startTime2 = Time.valueOf(LocalTime.of(15, 0));
    Time endTime2 = Time.valueOf(LocalTime.of(16, 0));

    testCreateCourseSession(date1, startTime1, endTime1, courseOfferingId);
    testCreateCourseSession(date2, startTime2, endTime2, courseOfferingId);

    ResponseEntity<ArrayList> response = client.getForEntity("/courseSession/getAllSessions", ArrayList.class);

    assertNotNull(response);
    assertEquals(2, response.getBody().size());
  }

  @Test
  public void testDeleteCourseSessionByCourseOfferingId() {
    // SETTING UP DEPENDENCIES
    Date startDate = Date.valueOf("2024-01-01");
    Date endDate = Date.valueOf("2024-01-07");
    ArrayList<DayOfWeek> daysOffered = new ArrayList<>();
    daysOffered.add(DayOfWeek.MONDAY);
    daysOffered.add(DayOfWeek.THURSDAY);
    Integer instructorId = createInstructorAccount("John Doe", "johndoe@gmail.com", "123456");
    Integer roomId = createRoom("Room 1", 1, 1, 30);
    Integer courseTypeId = createCourseType("Test Class");
    Integer courseOfferingId = createCourseOffering(startDate, endDate, daysOffered, roomRepository.findById(roomId).get(), courseTypeRepository.findById(courseTypeId).get(), instructorAccountRepository.findById(instructorId).get());

    Date date1 = Date.valueOf("2024-01-01");
    Time startTime1 = Time.valueOf(LocalTime.of(12, 0));
    Time endTime1 = Time.valueOf(LocalTime.of(13, 0));

    Date date2 = Date.valueOf("2024-01-02");
    Time startTime2 = Time.valueOf(LocalTime.of(15, 0));
    Time endTime2 = Time.valueOf(LocalTime.of(16, 0));

    testCreateCourseSession(date1, startTime1, endTime1, courseOfferingId);
    testCreateCourseSession(date2, startTime2, endTime2, courseOfferingId);

    ResponseEntity<ArrayList> response1 = client.getForEntity("/courseSession/getAllSessions", ArrayList.class);

    assertEquals(2, response1.getBody().size());

    ResponseEntity<String> response2 = client.exchange("/courseSession/deleteSessionsByOffering?courseOfferingId=" + courseOfferingId, HttpMethod.DELETE, null, String.class);

    assertNotNull(response2);
    assertEquals("Course Sessions deleted successfully", response2.getBody());

    ResponseEntity<ArrayList> response3 = client.getForEntity("/courseSession/getAllSessions", ArrayList.class);

    assertNotNull(response2);
    assertEquals(0, response3.getBody().size());
  }

  @Test
  public void testCreateMultipleCourseSessions() {
    // SETTING UP DEPENDENCIES
    Date startDate = Date.valueOf("2024-01-01");
    Date endDate = Date.valueOf("2024-01-07");
    ArrayList<DayOfWeek> daysOffered = new ArrayList<>();
    daysOffered.add(DayOfWeek.MONDAY);
    daysOffered.add(DayOfWeek.WEDNESDAY);
    Integer instructorId = createInstructorAccount("John Doe", "johndoe@gmail.com", "123456");
    Integer roomId = createRoom("Room 1", 1, 1, 30);
    Integer courseTypeId = createCourseType("Test Class");
    Integer courseOfferingId = createCourseOffering(startDate, endDate, daysOffered, roomRepository.findById(roomId).get(), courseTypeRepository.findById(courseTypeId).get(), instructorAccountRepository.findById(instructorId).get());

    ArrayList<Time> mondayTimes = new ArrayList<>();
    mondayTimes.add(Time.valueOf(LocalTime.of(12,0)));
    mondayTimes.add(Time.valueOf(LocalTime.of(13, 0)));

    ArrayList<Time> wednesdayTimes = new ArrayList<>();
    Time startTime = Time.valueOf(LocalTime.of(15, 0));
    Time endTime = Time.valueOf(LocalTime.of(16, 0));
    wednesdayTimes.add(startTime);
    wednesdayTimes.add(endTime);

    HashMap<DayOfWeek, ArrayList<Time>> dayTimeMapping = new HashMap<>();
    dayTimeMapping.put(DayOfWeek.MONDAY, mondayTimes);
    dayTimeMapping.put(DayOfWeek.WEDNESDAY, wednesdayTimes);

    ResponseEntity<ArrayList> response = client.postForEntity("/courseSession/createSessions", new multipleClassSessionsCO(dayTimeMapping, courseOfferingId), ArrayList.class);

    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status code");
    assertEquals(2, response.getBody().size());
  }

  @Test
  public void testGetCourseSessionsByCourseOffering() {
    // SETTING UP DEPENDENCIES
    Date startDate = Date.valueOf("2024-01-01");
    Date endDate = Date.valueOf("2024-01-07");
    ArrayList<DayOfWeek> daysOffered = new ArrayList<>();
    daysOffered.add(DayOfWeek.MONDAY);
    daysOffered.add(DayOfWeek.WEDNESDAY);
    Integer instructorId = createInstructorAccount("John Doe", "johndoe@gmail.com", "123456");
    Integer roomId = createRoom("Room 1", 1, 1, 30);
    Integer courseTypeId = createCourseType("Test Class");
    Integer courseOfferingId = createCourseOffering(startDate, endDate, daysOffered, roomRepository.findById(roomId).get(), courseTypeRepository.findById(courseTypeId).get(), instructorAccountRepository.findById(instructorId).get());

    ArrayList<Time> mondayTimes = new ArrayList<>();
    mondayTimes.add(Time.valueOf(LocalTime.of(12,0)));
    mondayTimes.add(Time.valueOf(LocalTime.of(13, 0)));

    ArrayList<Time> wednesdayTimes = new ArrayList<>();
    Time startTime = Time.valueOf(LocalTime.of(15, 0));
    Time endTime = Time.valueOf(LocalTime.of(16, 0));
    wednesdayTimes.add(startTime);
    wednesdayTimes.add(endTime);

    HashMap<DayOfWeek, ArrayList<Time>> dayTimeMapping = new HashMap<>();
    dayTimeMapping.put(DayOfWeek.MONDAY, mondayTimes);
    dayTimeMapping.put(DayOfWeek.WEDNESDAY, wednesdayTimes);

    ResponseEntity<ArrayList> response1 = client.postForEntity("/courseSession/createSessions", new multipleClassSessionsCO(dayTimeMapping, courseOfferingId), ArrayList.class);

    assertNotNull(response1);
    assertEquals(HttpStatus.CREATED, response1.getStatusCode(), "Response has correct status code");
    assertEquals(2, response1.getBody().size());

    ResponseEntity<ArrayList> response2 = client.getForEntity("/courseSession/getSessionsByOffering?courseOfferingId=" + courseOfferingId, ArrayList.class);

    assertNotNull(response2);
    assertEquals(2, response2.getBody().size());
  }
}