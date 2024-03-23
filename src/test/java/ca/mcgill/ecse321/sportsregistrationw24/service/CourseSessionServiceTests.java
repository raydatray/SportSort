package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseOfferingRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseSessionRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseSession;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

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



@ExtendWith(MockitoExtension.class)
public class CourseSessionServiceTests {
  @Mock
  private CourseOfferingRepository courseOfferingDao;
  @Mock
  private CourseSessionRepository courseSessionRepository;
  @InjectMocks
  private CourseSessionService service;

  private static final Integer courseOfferingKey = 1;

  @BeforeEach
  public void setMockOutput() {
    lenient().when(courseOfferingDao.findById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
      if (invocation.getArgument(0).equals(courseOfferingKey)) {
        CourseOffering courseOffering = new CourseOffering();
        courseOffering.setId(courseOfferingKey);
        return courseOffering;
      } else {
        return null;
      }
    });
    // Whenever anything is saved, just return the parameter object
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };
    lenient().when(courseOfferingDao.save(any(CourseOffering.class))).thenAnswer(returnParameterAsAnswer);
  }

  @Test
  public void testCreateCourseOffering() {
    assertEquals(0, service.getAllCourseSessions().size());

    Calendar calendar = Calendar.getInstance();
    calendar.clear(); // Clear the calendar to avoid any previous settings
    // Set date to January 1st, 2024
    calendar.set(Calendar.YEAR, 2024);
    calendar.set(Calendar.MONTH, Calendar.JANUARY);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    java.util.Date utilDate = calendar.getTime();

    Date date = new Date(utilDate.getTime());
    Time startTime = Time.valueOf(java.time.LocalTime.of(14, 0)); // 2 PM
    Time endTime = Time.valueOf(java.time.LocalTime.of(17, 0)); // 5 PM
    Integer courseOfferingId = 1;

    CourseSession courseSession = null;

    try {
      courseSession = service.createCourseSession(date, startTime, endTime, courseOfferingId);
    } catch (Exception e) {
      // Check that no error occured
      fail();
    }

    assertNotNull(courseSession);
    assertEquals(courseOfferingId, courseSession.getId());
  }
}
