package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseTypeRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CourseTypeServiceTests {
  @Mock
  private CourseTypeRepository courseTypeRepository;
  @Mock
  private UserAccountRepository userAccountRepository;
  @InjectMocks
  private CourseTypeService courseTypeService;

  //Constant Course Type Name
  private static final String COURSE_TYPE_NAME = "Soccer";
  private static final Integer COURSE_TYPE_ID_1 = 1 ;
  private static final String COURSE_TYPE_NAME2 = "Basketball";
  private static final Integer COURSE_TYPE_ID_2 = 2;
  private static final boolean DEFAULT_APPROVAL = false;

  //Dummy Instructor Account
  private static final String INSTRUCTOR_NAME = "Leon";
  private static final String INSTRUCTOR_EMAIL = "Leon@gmail.com";
  private static final String INSTRUCTOR_PASSWORD = "password";

  //Dummy Customer Account
  private static final String CUSTOMER_NAME = "Joe";
  private static final String CUSTOMER_EMAIL = "Joe@gmail.com";
  private static final String CUSTOMER_PASSWORD = "password";

  //Dummy Owner Account
  private static final String OWNER_NAME = "Alex";
  private static final String OWNER_EMAIL = "Alex@gmail.com";
  private static final String OWNER_PASSWORD = "password";


  @BeforeEach
  public void setMockOutput() {
    lenient().when(courseTypeRepository.findByCourseName(anyString())).thenAnswer((invocation) -> {
      if (invocation.getArgument(0).equals(COURSE_TYPE_NAME)) {
        CourseType courseType = new CourseType();
        courseType.setCourseName(COURSE_TYPE_NAME);
        courseType.setId(COURSE_TYPE_ID_1);
        return Optional.of(courseType);
      } else if (invocation.getArgument(0).equals(COURSE_TYPE_NAME2)) {
        CourseType courseType = new CourseType();
        courseType.setCourseName(COURSE_TYPE_NAME2);
        courseType.setId(COURSE_TYPE_ID_2);
        return Optional.of(courseType);
      } else {
        return Optional.empty();
      }
    });

    lenient().when(courseTypeRepository.findById(anyInt())).thenAnswer((invocation) -> {
      if (invocation.getArgument(0).equals(COURSE_TYPE_ID_1)) {
        CourseType courseType = new CourseType();
        courseType.setCourseName(COURSE_TYPE_NAME);
        courseType.setId(COURSE_TYPE_ID_1);
        return Optional.of(courseType);
      } else if (invocation.getArgument(0).equals(COURSE_TYPE_ID_2)) {
        CourseType courseType = new CourseType();
        courseType.setCourseName(COURSE_TYPE_NAME2);
        courseType.setId(COURSE_TYPE_ID_2);
        return Optional.of(courseType);
      } else {
        return Optional.empty();
      }
    });

    lenient().when(userAccountRepository.findUserByToken(anyString())).thenAnswer((invocation) -> {
      if (invocation.getArgument(0).equals("instructorToken")) {
        InstructorAccount instructor = new InstructorAccount();
        instructor.setName(INSTRUCTOR_NAME);
        instructor.setEmail(INSTRUCTOR_EMAIL);
        instructor.setPassword(INSTRUCTOR_PASSWORD);
        return Optional.of(instructor);
      } else if (invocation.getArgument(0).equals("customerToken")) {
        CustomerAccount customer = new CustomerAccount();
        customer.setName(CUSTOMER_NAME);
        customer.setEmail(CUSTOMER_EMAIL);
        customer.setPassword(CUSTOMER_PASSWORD);
        return Optional.of(customer);
      } else if (invocation.getArgument(0).equals("ownerToken")) {
        OwnerAccount owner = new OwnerAccount();
        owner.setName(OWNER_NAME);
        owner.setEmail(OWNER_EMAIL);
        owner.setPassword(OWNER_PASSWORD);
        return Optional.of(owner);
      } else {
        return Optional.empty();
      }
    });

    lenient().when(courseTypeRepository.save(any(CourseType.class))).thenAnswer((invocation)-> {
      CourseType courseType = (CourseType) invocation.getArgument(0);
      if (courseType.getCourseName().equals(COURSE_TYPE_NAME)) {
        courseType.setId(COURSE_TYPE_ID_1);
      } else if (courseType.getCourseName().equals(COURSE_TYPE_NAME2)) {
        courseType.setId(COURSE_TYPE_ID_2);
      }
      return courseType;
    });
  }

  @Test
  public void testCreateCourseType() {
    assertEquals(0, courseTypeService.getAllCourseTypes("instructorToken").size());
    CourseType courseType = null;

    try {
      courseType = courseTypeService.createCourseType(COURSE_TYPE_NAME, "instructorToken");
    } catch (IllegalArgumentException e) {
      fail();
    }

    assertNotNull(courseType);
    assertEquals(courseType.getCourseName(), COURSE_TYPE_NAME);
    assertEquals(courseType.getApproved(), DEFAULT_APPROVAL);
  }

  @Test
  public void testCreateCourseTypeWithEmptyName() {
    assertEquals(0, courseTypeService.getAllCourseTypes("instructorToken").size());
    CourseType courseType = null;
    String error = null;

    try {
      courseType = courseTypeService.createCourseType("", "instructorToken");
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Course name cannot be empty!", error);
  }

  @Test
  public void testCreateCourseTypeWithNullName() {
    assertEquals(0, courseTypeService.getAllCourseTypes("instructorToken").size());
    CourseType courseType = null;
    String error = null;

    try {
      courseType = courseTypeService.createCourseType(null, "instructorToken");
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Course name cannot be empty!", error);
  }

  @Test
  public void testCreateCourseTypeWithDuplicate() {
    assertEquals(0, courseTypeService.getAllCourseTypes("instructorToken").size());

    courseTypeService.createCourseType(COURSE_TYPE_NAME, "instructorToken");

    assertEquals(1, courseTypeService.getAllCourseTypes("instructorToken").size());

    CourseType courseType = null;
    String error = null;

    try {
      courseType = courseTypeService.createCourseType(COURSE_TYPE_NAME, "instructorToken");
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("A course type with this name already exists!", error);
  }

  @Test
  public void testCreateCourseTypeWithCustomerAccount() {
    assertEquals(0, courseTypeService.getAllCourseTypes("instructorToken").size());
    CourseType courseType = null;
    String error = null;

    try {
      courseType = courseTypeService.createCourseType(COURSE_TYPE_NAME, "customerToken");
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Only instructors and owners can create course types!", error);
  }

  @Test
  public void testSetCourseTypeApproved() {
    assertEquals(0, courseTypeService.getAllCourseTypes("ownerToken").size());
    CourseType courseType = courseTypeService.createCourseType(COURSE_TYPE_NAME, "instructorToken");
    CourseType updatedCourseType = null;

    try {
      updatedCourseType = courseTypeService.updateCourseTypeApproval(courseType.getId(), true, "ownerToken");
    } catch (IllegalArgumentException e) {
      fail();
    }

    assertNotNull(updatedCourseType);
    assertTrue(updatedCourseType.getApproved());
  }

  @Test
  public void testSetCourseTypeApprovedWithCustomerAccount() {
    assertEquals(0, courseTypeService.getAllCourseTypes("ownerToken").size());
    CourseType courseType = courseTypeService.createCourseType(COURSE_TYPE_NAME, "instructorToken");
    CourseType updatedCourseType = null;
    String error = null;

    try {
      updatedCourseType = courseTypeService.updateCourseTypeApproval(courseType.getId(), true, "customerToken");
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertNull(updatedCourseType);
    assertEquals("Only the owner can approve course types!", error);
  }

  @Test
  public void testSetCourseTypeApprovedWithInstructorAccount() {
    assertEquals(0, courseTypeService.getAllCourseTypes("ownerToken").size());
    CourseType courseType = courseTypeService.createCourseType(COURSE_TYPE_NAME, "instructorToken");
    CourseType updatedCourseType = null;
    String error = null;

    try {
      updatedCourseType = courseTypeService.updateCourseTypeApproval(courseType.getId(), true, "instructorToken");
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertNull(updatedCourseType);
    assertEquals("Only the owner can approve course types!", error);
  }

  @Test
  public void viewCourseType() {
    assertEquals(0, courseTypeService.getAllCourseTypes("ownerToken").size());
    CourseType courseType = courseTypeService.createCourseType(COURSE_TYPE_NAME, "instructorToken");
    CourseType viewedCourseType = null;

    try {
      viewedCourseType = courseTypeService.getCourseType(courseType.getId(), "ownerToken");
    } catch (IllegalArgumentException e) {
      fail();
    }

    assertNotNull(viewedCourseType);
    assertEquals(viewedCourseType.getCourseName(), COURSE_TYPE_NAME);
  }

  @Test
  public void viewCourseTypes() {
    assertEquals(0, courseTypeService.getAllCourseTypes("ownerToken").size());
    CourseType courseType1 = courseTypeService.createCourseType(COURSE_TYPE_NAME, "instructorToken");
    CourseType courseType2 = courseTypeService.createCourseType(COURSE_TYPE_NAME2, "instructorToken");

    assertEquals(2, courseTypeService.getAllCourseTypes("ownerToken").size());
  }

  @Test
  public void deleteCourseType() {
    assertEquals(0, courseTypeService.getAllCourseTypes("ownerToken").size());
    CourseType courseType = courseTypeService.createCourseType(COURSE_TYPE_NAME, "instructorToken");

    try {
      courseTypeService.deleteCourseType(courseType.getId(), "ownerToken");
    } catch (IllegalArgumentException e) {
      fail();
    }

    assertEquals(0, courseTypeService.getAllCourseTypes("ownerToken").size());
  }

  @Test
  public void deleteCourseTypeWithCustomerAccount() {
    assertEquals(0, courseTypeService.getAllCourseTypes("ownerToken").size());
    CourseType courseType = courseTypeService.createCourseType(COURSE_TYPE_NAME, "instructorToken");
    String error = null;

    try {
      courseTypeService.deleteCourseType(courseType.getId(), "customerToken");
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("Only the owner can delete course types!", error);
  }

  @Test
  public void deleteCourseTypeWithInstructorAccount() {
    assertEquals(0, courseTypeService.getAllCourseTypes("ownerToken").size());
    CourseType courseType = courseTypeService.createCourseType(COURSE_TYPE_NAME, "instructorToken");
    String error = null;

    try {
      courseTypeService.deleteCourseType(courseType.getId(), "instructorToken");
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("Only the owner can delete course types!", error);
  }
}
