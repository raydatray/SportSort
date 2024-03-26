package ca.mcgill.ecse321.sportsregistrationw24.integration;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseTypeRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dto.courseSession.CourseSessionDto;
import ca.mcgill.ecse321.sportsregistrationw24.dto.courseType.CourseTypeCO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.courseType.CourseTypeDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.OwnerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.service.AuthenticationService;
import ca.mcgill.ecse321.sportsregistrationw24.service.UserAccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseTypeIntegrationTests {
  @Autowired
  private
  TestRestTemplate client;

  @Autowired
  private CourseTypeRepository courseTypeRepository;

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private UserAccountRepository userAccountRepository;

  @Autowired
  private UserAccountService userAccountService;

  private Integer createInstructorAccount(String name, String email, String password) {
    return userAccountRepository.save(new InstructorAccount(name, email, password)).getId();
  }

  private Integer createOwnerAccount(String name, String email, String password) {
    return userAccountRepository.save(new OwnerAccount(name, email, password)).getId();
  }

  private String loginInstructorAccount(String email, String password) {
    return authenticationService.login(email, password).getToken();
  }

  @AfterEach
  public void cleanDatabase() {
    courseTypeRepository.deleteAll();
    userAccountRepository.deleteAll();
  }

  @Test
  public void testCreateGetUpdateAndDeleteCourseType() {
    String testInstructorName = "Joe";
    String testInstructorEmail = "joe@gmail.com";
    String testInstructorPassword = "123";
    Integer instructorId = createInstructorAccount(testInstructorName, testInstructorEmail, testInstructorPassword);
    String token = loginInstructorAccount(testInstructorEmail, testInstructorPassword);

    String testCourseName = "Soccer";

    ResponseEntity<?> response = client.postForEntity("/courseTypes/create", new CourseTypeCO(testCourseName, token), String.class);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Course type created succesfully!", response.getBody());

    CourseType createdCourseType = courseTypeRepository.findByCourseName(testCourseName).orElse(null);

    assertNotNull(createdCourseType);
    assertEquals(testCourseName, createdCourseType.getCourseName());
    assertFalse(createdCourseType.getApproved());

    Integer courseTypeID = createdCourseType.getId();

    HttpHeaders headers = new HttpHeaders();
    headers.set("userToken", token);
    HttpEntity<String> entity = new HttpEntity<>(headers);

    String requestURL = "/courseTypes/get?id=" + courseTypeID;

    ResponseEntity<CourseTypeDto> response2 = client.exchange(requestURL, HttpMethod.GET, entity, CourseTypeDto.class);

    assertNotNull(response2);
    assertEquals(HttpStatus.OK, response2.getStatusCode());
    assertEquals(testCourseName, response2.getBody().getCourseName());
    assertFalse(response2.getBody().getApprovalStatus());


    String testOwnerName = "Bob";
    String testOwnerEmail = "bob@gmail.com";
    String testOwnerPassword = "123";
    Integer ownerId = createOwnerAccount(testOwnerName, testOwnerEmail, testOwnerPassword);
    String ownerToken = authenticationService.login(testOwnerEmail, testOwnerPassword).getToken();

    HttpHeaders headers2 = new HttpHeaders();
    headers2.set("userToken", ownerToken);
    HttpEntity<String> entity2 = new HttpEntity<>(headers2);

    String requestURL2 = "/courseTypes/update?id=" + courseTypeID + "&approved=" + true;
    ResponseEntity<?> response3 = client.exchange(requestURL2, HttpMethod.PUT, entity2, String.class);

    assertNotNull(response3);
    assertEquals(HttpStatus.OK, response3.getStatusCode());
    assertEquals("Course type approval updated successfully!", response3.getBody());
    assertTrue(courseTypeRepository.findById(courseTypeID).orElse(null).getApproved());

    String requestURL3 = "/courseTypes/delete?id=" + courseTypeID;
    ResponseEntity<?> response4 = client.exchange(requestURL3, HttpMethod.DELETE, entity2, String.class);

    assertNotNull(response4);
    assertEquals(HttpStatus.OK, response4.getStatusCode());
    assertEquals("Course type deleted successfully!", response4.getBody());
    assertFalse(courseTypeRepository.findById(courseTypeID).isPresent());
  }

  @Test
  public void testCreateCourseTypeFailName() {
    String testInstructorName = "Joe";
    String testInstructorEmail = "joe@gmail.com";
    String testInstructorPassword = "123";
    Integer instructorId = createInstructorAccount(testInstructorName, testInstructorEmail, testInstructorPassword);
    String token = loginInstructorAccount(testInstructorEmail, testInstructorPassword);

    String testCourseNameNull = null;
    String testCourseNameEmpty = "";
    String testCourseName = "Soccer";

    ResponseEntity<?> response1 = client.postForEntity("/courseTypes/create", new CourseTypeCO(testCourseNameNull, token), String.class);
    assertNotNull(response1);
    assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());
    assertEquals("Course name cannot be empty!", response1.getBody());

    ResponseEntity<?> response2 = client.postForEntity("/courseTypes/create", new CourseTypeCO(testCourseNameEmpty, token), String.class);
    assertNotNull(response2);
    assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
    assertEquals("Course name cannot be empty!", response2.getBody());

    ResponseEntity<?> response3 = client.postForEntity("/courseTypes/create", new CourseTypeCO(testCourseName, token), String.class);
    assertNotNull(response3);
    assertEquals(HttpStatus.OK, response3.getStatusCode());
    assertEquals("Course type created succesfully!", response3.getBody());

    ResponseEntity<?> response4 = client.postForEntity("/courseTypes/create", new CourseTypeCO(testCourseName, token), String.class);
    assertNotNull(response4);
    assertEquals(HttpStatus.BAD_REQUEST, response4.getStatusCode());
    assertEquals("A course type with this name already exists!", response4.getBody());
  }
}