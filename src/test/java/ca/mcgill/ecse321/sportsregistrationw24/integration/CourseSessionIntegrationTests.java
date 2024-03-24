package ca.mcgill.ecse321.sportsregistrationw24.integration;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseOfferingRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseSessionRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.SportCenterRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dto.courseSession.CourseSessionDto;
import ca.mcgill.ecse321.sportsregistrationw24.dto.courseSession.singleCourseSessionCO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseSessionIntegrationTests {
  @Autowired
  private TestRestTemplate client;
  @Autowired
  private CourseOfferingRepository courseOfferingRepository;
  @Autowired
  private SportCenterRepository sportCenterRepository;
  @Autowired
  private CourseSessionRepository courseSessionRepository;

  @BeforeEach
  @AfterEach
  public void clearDatabase() {
    courseSessionRepository.deleteAll();
  }

  @Test
  public void testCreateAndGetCourseSession() {
    int id = testCreateCourseSession();
    testGetCourseSession(id);
  }

  private int testCreateCourseSession() {
    Date date = Date.valueOf("2024-01-01");
    Time startTime = Time.valueOf(LocalTime.of(12, 0));
    Time endTime = Time.valueOf(LocalTime.of(13, 0));

    ResponseEntity<CourseSessionDto> response = client.postForEntity("/courseSession/createSession", new singleCourseSessionCO(date, startTime, endTime, 1), CourseSessionDto.class);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals(date, response.getBody().getDate());
    assertEquals(startTime, response.getBody().getStartTime());
    assertEquals(endTime, response.getBody().getEndTime());
    assertTrue(response.getBody().getId() > 0, "Response has valid ID");

    return response.getBody().getId();
  }

  private void testGetCourseSession(int id) {
    ResponseEntity<CourseSessionDto> response = client.getForEntity("/courseSession/getSession/" + id, CourseSessionDto.class);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals(id, response.getBody().getId(), "Response has correct ID");
  }

  @Test
  public void testCreateInvalidCourseSession() {
    ResponseEntity<CourseSessionDto> response = client.postForEntity("/courseSession", new singleCourseSessionCO(null, null, null, null), CourseSessionDto.class);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
  }
}
