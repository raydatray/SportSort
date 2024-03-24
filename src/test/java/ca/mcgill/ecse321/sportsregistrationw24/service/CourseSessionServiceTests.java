package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseOfferingRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseSessionRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.SportCenterRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseSession;
import ca.mcgill.ecse321.sportsregistrationw24.model.SportCenter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;



@ExtendWith(MockitoExtension.class)
public class CourseSessionServiceTests {
  @Mock
  private CourseOfferingRepository courseOfferingRepository;
  @Mock
  private SportCenterRepository sportCenterRepository;
  @Mock
  private CourseSessionRepository courseSessionRepository;
  @InjectMocks
  private CourseSessionService service;

  // CONSTANTS USED ACROSS TESTS
  private static final Date DATE = Date.valueOf("2024-01-02");
  private static final Time START_TIME = Time.valueOf(LocalTime.of(14, 0)); // 2 PM
  private static final Time END_TIME = Time.valueOf(LocalTime.of(15, 0)); // 3 PM
  private static final Integer COURSE_OFFERING_KEY = 1;
  private static final String SPORTCENTER_KEY = "TestSportCenter";

  @BeforeEach
  public void setMockOutput() {
    lenient().when(courseOfferingRepository.findById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
      if (invocation.getArgument(0).equals(COURSE_OFFERING_KEY)) {
        CourseOffering courseOffering = new CourseOffering();
        courseOffering.setId(COURSE_OFFERING_KEY); // Set ID to 1
        courseOffering.setStartDate(Date.valueOf("2024-01-01")); // Start date = Jan 1st, 2024
        courseOffering.setEndDate(Date.valueOf("2024-01-07")); // End date = Feb 1st, 2024
        ArrayList<DayOfWeek> daysOffered = new ArrayList<>();
        daysOffered.add(DayOfWeek.MONDAY);
        daysOffered.add(DayOfWeek.WEDNESDAY);
        courseOffering.setDaysOffered(daysOffered);
        return Optional.of(courseOffering);
      } else {
        return Optional.empty();
      }
    });
    lenient().when(sportCenterRepository.findById(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
      if (invocation.getArgument(0).equals(SPORTCENTER_KEY)) {
        SportCenter sportCenter = new SportCenter();
        sportCenter.setName(SPORTCENTER_KEY);
        sportCenter.setOpeningHour(Time.valueOf(LocalTime.of(9, 0))); // 9 AM
        sportCenter.setClosingHour(Time.valueOf(LocalTime.of(17, 0))); // 5 PM
        return Optional.of(sportCenter);
      } else {
        return Optional.empty();
      }
    });
    // Whenever anything is saved, just return the parameter object
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };
    lenient().when(courseOfferingRepository.save(any(CourseOffering.class))).thenAnswer(returnParameterAsAnswer);
  }

  /*---------- Tests for createCourseSession() ----------*/
  @Test
  public void testCreateCourseSession() {
    assertEquals(0, service.getAllCourseSessions().size());

    CourseSession courseSession = null;

    try {
      courseSession = service.createCourseSession(DATE, START_TIME, END_TIME, COURSE_OFFERING_KEY);
    } catch (IllegalArgumentException e) {
      // Check that no error occured
      fail();
    }

    assertNotNull(courseSession);
    assertEquals(DATE, courseSession.getDate());
    assertEquals(START_TIME, courseSession.getStartTime());
    assertEquals(END_TIME, courseSession.getEndTime());
    assertEquals(COURSE_OFFERING_KEY, courseSession.getCourseOffering().getId());
  }

  @Test // TODO - TELL RAY TO FIX THIS (Null pointer exn instead of illegal argument)
  public void testCreateCourseSessionWithNullDate() {
    Date date = null;

    String error = null;
    CourseSession courseSession = null;

    try {
      courseSession = service.createCourseSession(date, START_TIME, END_TIME, COURSE_OFFERING_KEY);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("???", error);
  }

  @Test // TODO - TELL RAY TO FIX THIS (Null pointer exn instead of illegal argument)
  public void testCreateCourseSessionWithNullStartTime() {
    Time startTime = null;

    String error = null;
    CourseSession courseSession = null;

    try {
      courseSession = service.createCourseSession(DATE, startTime, END_TIME, COURSE_OFFERING_KEY);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("???", error);
  }

  @Test // TODO - TELL RAY TO FIX THIS (Null pointer exn instead of illegal argument)
  public void testCreateCourseSessionWithNullEndTime() {
    Time endTime = null;

    String error = null;
    CourseSession courseSession = null;

    try {
      courseSession = service.createCourseSession(DATE, START_TIME, endTime, COURSE_OFFERING_KEY);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("???", error);
  }

  @Test
  public void testCreateCourseSessionWithNullOfferingId() {
    Integer courseOfferingId = null;

    String error = null;
    CourseSession courseSession = null;

    try {
      courseSession = service.createCourseSession(DATE, START_TIME, END_TIME, courseOfferingId);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("Course Offering not found", error);
  }

  @Test
  public void testCreateCourseSessionWithInvalidOfferingId() {
    Integer courseOfferingId = 3;

    String error = null;
    CourseSession courseSession = null;

    try {
      courseSession = service.createCourseSession(DATE, START_TIME, END_TIME, courseOfferingId);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("Course Offering not found", error);
  }

  @Test // TODO - TELL RAY TO FIX THIS IN SERVICE LAYER
  public void testCreateCourseSessionWithDateBeforeOfferingStartDate() {
    Date date = Date.valueOf("2023-01-01");

    String error = null;
    CourseSession courseSession = null;

    try {
      courseSession = service.createCourseSession(date, START_TIME, END_TIME, COURSE_OFFERING_KEY);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("???", error);
  }

  @Test // TODO - TELL RAY TO FIX THIS
  public void testCreateCourseSessionWithDateAfterOfferingEndDate() {
    Date date = Date.valueOf("2025-01-01");

    String error = null;
    CourseSession courseSession = null;

    try {
      service.createCourseSession(date, START_TIME, END_TIME, COURSE_OFFERING_KEY);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("???", error);
  }

  @Test // TODO - Tell ray to fix this
  public void testCreateCourseSessionWithSameStartAndEndTimes() {
    Time startTime = START_TIME;
    Time endTime = END_TIME;

    String error = null;
    CourseSession courseSession = null;

    try {
      courseSession = service.createCourseSession(DATE, startTime, endTime, COURSE_OFFERING_KEY);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("???", error);
  }

  @Test // TODO - Tell ray to fix this
  public void testCreateCourseSessionWithEndTimeBeforeStartTime() {
    Time startTime = END_TIME;
    Time endTime = START_TIME;

    String error = null;
    CourseSession courseSession = null;

    try {
      courseSession = service.createCourseSession(DATE, startTime, endTime, COURSE_OFFERING_KEY);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("???", error);
  }

  @Test
  public void testCreateCourseSessionWithStartTimeBeforeOpeningHour() {

  }

  @Test
  public void testCreateCourseSessionWithStartTimeAfterClosingHour() {

  }

  @Test
  public void testCreateCourseSessionWithEndTimeBeforeOpeningHour() {

  }

  @Test
  public void testCreateCourseSessionWithEndTimeAfterClosingHour() {

  }

  @Test // TODO - TELL RAY TO FIX THIS
  public void testCreateCourseSessionWithDurationOver1Hour() {
    Time startTime = Time.valueOf(java.time.LocalTime.of(14, 0)); // 2 PM
    Time endTime = Time.valueOf(java.time.LocalTime.of(17, 0)); // 5 PM

    String error = null;
    CourseSession courseSession = null;

    try {
      courseSession = service.createCourseSession(DATE, startTime, endTime, COURSE_OFFERING_KEY);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("???", error);
  }

  /*---------- Tests for createCourseSessions() ----------*/
  @Test
  public void testCreateCourseSessions() {
    assertEquals(0, service.getAllCourseSessions().size());

    ArrayList<Time> mondayTimes = new ArrayList<>();
    mondayTimes.add(START_TIME);
    mondayTimes.add(END_TIME);

    ArrayList<Time> wednesdayTimes = new ArrayList<>();
    Time startTime = Time.valueOf(LocalTime.of(12, 0));
    Time endTime = Time.valueOf(LocalTime.of(13, 0));
    wednesdayTimes.add(startTime);
    wednesdayTimes.add(endTime);

    HashMap<DayOfWeek, ArrayList<Time>> dayTimeMapping = new HashMap<>();
    dayTimeMapping.put(DayOfWeek.MONDAY, mondayTimes);
    dayTimeMapping.put(DayOfWeek.WEDNESDAY, wednesdayTimes);

    ArrayList<CourseSession> courseSessions = null;

    try {
      courseSessions = service.createCourseSessions(dayTimeMapping, COURSE_OFFERING_KEY);
    } catch (IllegalArgumentException e) {
      fail();
    }

    assertNotNull(courseSessions);
    assertEquals(2, courseSessions.size());
    assertEquals(START_TIME, courseSessions.get(0).getStartTime());
    assertEquals(END_TIME, courseSessions.get(0).getEndTime());
    assertEquals(COURSE_OFFERING_KEY, courseSessions.get(0).getCourseOffering().getId());
    assertEquals(startTime, courseSessions.get(1).getStartTime());
    assertEquals(endTime, courseSessions.get(1).getEndTime());
    assertEquals(COURSE_OFFERING_KEY, courseSessions.get(1).getCourseOffering().getId());
  }

  @Test
  public void testCreateCourseSessionsWithNullCourseOfferingId() {
    Integer courseOfferingId = null;

    ArrayList<Time> mondayTimes = new ArrayList<>();
    mondayTimes.add(START_TIME);
    mondayTimes.add(END_TIME);

    ArrayList<Time> wednesdayTimes = new ArrayList<>();
    Time startTime = Time.valueOf(LocalTime.of(12, 0));
    Time endTime = Time.valueOf(LocalTime.of(13, 0));
    wednesdayTimes.add(startTime);
    wednesdayTimes.add(endTime);

    HashMap<DayOfWeek, ArrayList<Time>> dayTimeMapping = new HashMap<>();
    dayTimeMapping.put(DayOfWeek.MONDAY, mondayTimes);
    dayTimeMapping.put(DayOfWeek.WEDNESDAY, wednesdayTimes);

    String error = null;
    ArrayList<CourseSession> courseSessions = null;

    try {
      courseSessions = service.createCourseSessions(dayTimeMapping, courseOfferingId);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("Course Offering not found", error);
  }

  @Test  // TODO - Tell Ray to fix this
  public void testCreateCourseSessionsWithNullDayTimeMapping() {
    HashMap<DayOfWeek, ArrayList<Time>> dayTimeMappping = null;

    String error = null;
    ArrayList<CourseSession> courseSessions = null;

    try {
      courseSessions = service.createCourseSessions(dayTimeMappping, COURSE_OFFERING_KEY);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("??", error);
  }

  @Test // TODO - Tell Ray to fix this
  public void testCreateCourseSessionsWithInvalidDayTimeMapping() {
    ArrayList<Time> mondayTimes = new ArrayList<>();
    mondayTimes.add(START_TIME);
    mondayTimes.add(END_TIME);

    ArrayList<Time> tuesdayTimes = new ArrayList<>();
    Time startTime = Time.valueOf(LocalTime.of(12, 0));
    Time endTime = Time.valueOf(LocalTime.of(13, 0));
    tuesdayTimes.add(startTime);
    tuesdayTimes.add(endTime);

    HashMap<DayOfWeek, ArrayList<Time>> dayTimeMapping = new HashMap<>();
    dayTimeMapping.put(DayOfWeek.MONDAY, mondayTimes);
    dayTimeMapping.put(DayOfWeek.TUESDAY, tuesdayTimes); // Set Tuesday instead of Wednesday

    String error = null;
    ArrayList<CourseSession> courseSessions = null;

    try {
      courseSessions = service.createCourseSessions(dayTimeMapping, COURSE_OFFERING_KEY);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("???", error);
  }

  /*---------- Tests for deleteCourseSessionByID() ----------*/
  @Test
  public void testDeleteCourseSessionById() {
    assertEquals(0, service.getAllCourseSessions().size());

    CourseSession courseSession = null;

    try {
      courseSession = service.createCourseSession(DATE, START_TIME, END_TIME, COURSE_OFFERING_KEY);
    } catch (IllegalArgumentException e) {
      fail();
    }

    assertNotNull(courseSession);

    service.deleteCourseSessionByID(courseSession.getId());

    assertEquals(0, service.getAllCourseSessions().size());
  }

  @Test // TODO - Ask if we want to keep behaviour this way... i.e. not throw error even if input is invalid/null
  public void testDeleteCourseSessionByNullId() {
    Integer courseSessionId = null;

    String error = null;

    try {
      service.deleteCourseSessionByID(courseSessionId);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("???", error);
  }

  @Test // TODO - Ask if we want to keep behaviour this way... i.e. not throw error even if input is invalid/null
  public void testDeleteCourseSessionByInvalidId() {
    Integer courseSessionId = -1;

    String error = null;

    try {
      service.deleteCourseSessionByID(courseSessionId);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("???", error);
  }

  /*---------- Tests for deleteCourseSessionsByCourseOfferingId() ----------*/
  @Test
  public void testDeleteCourseSessionsByCourseOfferingId() {
    assertEquals(0, service.getAllCourseSessions().size());

    Date otherDate = Date.valueOf("2024-01-04");

    CourseSession courseSession1 = service.createCourseSession(DATE, START_TIME, END_TIME, COURSE_OFFERING_KEY);
    CourseSession courseSession2 = service.createCourseSession(otherDate, START_TIME, END_TIME, COURSE_OFFERING_KEY);

    assertNotNull(courseSession1);
    assertNotNull(courseSession2);
    // assertEquals(2, service.getAllCourseSessions().size());

    service.deleteCourseSessionsByCourseOfferingID(COURSE_OFFERING_KEY);

    assertEquals(0, service.getAllCourseSessions().size());
  }

  @Test
  public void testDeleteCourseSessionsByCourseOfferingIdWithInvalidCourseOfferingId() {
    Integer courseOfferingId = 3;

    String error = null;
    CourseSession courseSession = null;

    try {
      service.deleteCourseSessionsByCourseOfferingID(courseOfferingId);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("Course Offering not found", error);
  }

  @Test
  public void testDeleteCourseSessionsByCourseOfferingIdWithNullCourseOfferingId() {
    Integer courseOfferingId = null;

    String error = null;
    CourseSession courseSession = null;

    try {
      service.deleteCourseSessionsByCourseOfferingID(courseOfferingId);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("Course Offering not found", error);
  }
}
