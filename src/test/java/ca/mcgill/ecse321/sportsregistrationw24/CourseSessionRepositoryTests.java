package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseSessionRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CourseSessionRepositoryTests {
    @Autowired
    private CourseSessionRepository courseSessionRepository;

    @AfterEach
    public void clearDatabase() {
        courseSessionRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCourseSession() {
        CourseType courseType = new CourseType(1, "Cardio", true);
        Room testRoom = new Room(1, "Pool", 10, 10, 10);
        InstructorAccount testInstructor = new InstructorAccount(1, "raydatray@gmail.com", "password");
        List<DayOfWeek> testDays = List.of(new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.FRIDAY});

        CourseOffering testOffering  = new CourseOffering(1, Date.valueOf("2024-02-18"), Date.valueOf("2024-03-15"), testDays, testRoom, courseType, testInstructor);
        Date sessionDate = Date.valueOf("2024-02-20");
        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("09:00:00");


        CourseSession testSession = new CourseSession(1, sessionDate, startTime, endTime, testOffering);

        courseSessionRepository.save(testSession);

        Optional<CourseSession> readSession = courseSessionRepository.findById(1);

        assertNotNull(testSession = readSession.orElse(null));
        assertEquals(sessionDate, testSession.getDate());
        assertEquals(startTime, testSession.getStartTime());
        assertEquals(endTime, testSession.getEndTime());
        assertEquals(testOffering, testSession.getCourseOffering());
        assertEquals(1, testSession.getId());
    }
}
