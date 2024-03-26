package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseOfferingRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseTypeRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.RoomRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Date;
import java.time.DayOfWeek;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class CourseOfferingServiceTests {
  @Mock
  private CourseTypeRepository courseTypeRepository;
  @Mock
  private CourseOfferingRepository courseOfferingRepository;
  @Mock
  private UserAccountRepository userAccountRepository;
  @Mock
  private RoomRepository roomRepository;
  @InjectMocks
  private CourseOfferingService courseOfferingService;

  //Course Type Fields
  private static final Integer COURSE_TYPE_ID = 1 ;
  private static final String COURSE_TYPE_NAME = "Soccer";

  //Room Fields
  private static final Integer ROOM_ID = 1;
  private static final String ROOM_NAME = "Gym";
  private static final Integer ROOM_CAPACITY = 50;
  private static final Integer ROOM_FLOOR_NUMBER = 1;
  private static final Integer ROOM_NUMBER = 1;

  //Course Offering Fields
  private static final Integer COURSE_OFFERING_ID_1 = 1;
  private static final Date START_DATE_1 = Date.valueOf("2022-01-01");
  private static final Date END_DATE_1 = Date.valueOf("2022-04-01");
  private static final ArrayList<DayOfWeek> DAYS_OFFERED_1 = new ArrayList<DayOfWeek>(List.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));


  private static final Integer COURSE_OFFERING_ID_2 = 2;
  private static final Date START_DATE_2 = Date.valueOf("2022-01-02");
  private static final Date END_DATE_2 = Date.valueOf("2022-04-02");
  private static final ArrayList<DayOfWeek> DAYS_OFFERED_2 = new ArrayList<DayOfWeek>(List.of(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));

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
    lenient().when(courseTypeRepository.findById(anyInt())).thenAnswer((invocation) -> {
      if (invocation.getArgument(0).equals(COURSE_TYPE_ID)) {
        CourseType courseType = new CourseType();
        courseType.setId(COURSE_TYPE_ID);
        courseType.setCourseName(COURSE_TYPE_NAME);
        return Optional.of(courseType);
      } else {
        return Optional.empty();
      }
    });

    lenient().when(courseOfferingRepository.findById(anyInt())).thenAnswer((invocation) -> {
      if(invocation.getArgument(0).equals(COURSE_OFFERING_ID_1)) {
        CourseOffering courseOffering = new CourseOffering();
        courseOffering.setId(COURSE_OFFERING_ID_1);
        courseOffering.setStartDate(START_DATE_1);
        courseOffering.setEndDate(END_DATE_1);
        courseOffering.setDaysOffered(DAYS_OFFERED_1);
        return Optional.of(courseOffering);
      } else if (invocation.getArgument(0).equals(COURSE_OFFERING_ID_2)) {
        CourseOffering courseOffering = new CourseOffering();
        courseOffering.setId(COURSE_OFFERING_ID_2);
        courseOffering.setStartDate(START_DATE_2);
        courseOffering.setEndDate(END_DATE_2);
        courseOffering.setDaysOffered(DAYS_OFFERED_2);
        return Optional.of(courseOffering);
      } else {
        return Optional.empty();
      }
    });

    lenient().when(courseOfferingRepository.findByInstructorAccount(any(InstructorAccount.class))).thenAnswer((invocation) -> {
      return null;
    });

    lenient().when(courseOfferingRepository.findAll()).thenAnswer((invocation) -> {
      List<CourseOffering> courseOfferings = new ArrayList<CourseOffering>();
      CourseOffering courseOffering = new CourseOffering();
      courseOffering.setId(COURSE_OFFERING_ID_1);
      courseOffering.setStartDate(START_DATE_1);
      courseOffering.setEndDate(END_DATE_1);
      courseOffering.setDaysOffered(DAYS_OFFERED_1);
      courseOfferings.add(courseOffering);
      return courseOfferings;
    });

    lenient().when(roomRepository.findById(anyInt())).thenAnswer((invocation) -> {
      if (invocation.getArgument(0).equals(ROOM_ID)) {
        Room room = new Room();;
        room.setName(ROOM_NAME);
        room.setCapacity(ROOM_CAPACITY);
        room.setFloorNumber(ROOM_FLOOR_NUMBER);
        room.setRoomNumber(ROOM_NUMBER);
        return Optional.of(room);
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

    lenient().when(courseOfferingRepository.save(any(CourseOffering.class))).thenAnswer((invocation) -> {
      return null;
    });







  }




  @Test
  public void testCreateCourseOffering() {
    CourseOffering courseOffering = null;

    try {
      courseOffering = courseOfferingService.createCourseOffering(START_DATE_1, END_DATE_1, DAYS_OFFERED_1, "instructorToken", ROOM_ID, COURSE_TYPE_ID);
    } catch (IllegalArgumentException e) {
      fail();
    }

    assertNotNull(courseOffering);
    assertEquals(START_DATE_1, courseOffering.getStartDate());
    assertEquals(END_DATE_1, courseOffering.getEndDate());
    assertEquals(DAYS_OFFERED_1, courseOffering.getDaysOffered());
    assertEquals(ROOM_NAME, courseOffering.getRoom().getName());
    assertEquals(COURSE_TYPE_NAME, courseOffering.getCourseType().getCourseName());
    assertEquals(INSTRUCTOR_NAME, courseOffering.getInstructorAccount().getName());
  }

  @Test
  public void testCreateCourseOfferingInvalidUserType() {
    String error = null;
    CourseOffering courseOffering = null;

    try {
      courseOffering = courseOfferingService.createCourseOffering(START_DATE_1, END_DATE_1, DAYS_OFFERED_1, "customerToken", ROOM_ID, COURSE_TYPE_ID);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Only instructors can create course offerings!", error);
  }

  @Test
  public void testCreateCourseOfferingInvalidRoom() {
    String error = null;
    CourseOffering courseOffering = null;

    try {
      courseOffering = courseOfferingService.createCourseOffering(START_DATE_1, END_DATE_1, DAYS_OFFERED_1, "instructorToken", 2, COURSE_TYPE_ID);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Room does not exist!", error);
  }

  @Test
  public void getCourseOfferingById() {
    CourseOffering courseOffering = null;

    try {
      courseOffering = courseOfferingService.getCourseOfferingById(COURSE_OFFERING_ID_1, "instructorToken");
    } catch (IllegalArgumentException e) {
      fail();
    }

    assertNotNull(courseOffering);
    assertEquals(START_DATE_1, courseOffering.getStartDate());
    assertEquals(END_DATE_1, courseOffering.getEndDate());
    assertEquals(DAYS_OFFERED_1, courseOffering.getDaysOffered());
  }

  @Test
  public void testDeleteCourseOffering() {
    try {
      courseOfferingService.deleteCourseOffering(COURSE_OFFERING_ID_1, "instructorToken");
    } catch (IllegalArgumentException e) {
      fail();
    }
  }
}
