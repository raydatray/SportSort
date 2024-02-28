package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.*;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private InstructorAccountRepository instructorAccountRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        courseSessionRepository.deleteAll();
        courseOfferingRepository.deleteAll();
        courseTypeRepository.deleteAll();
        roomRepository.deleteAll();
        instructorAccountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCourseSession() {
        CourseType courseType = new CourseType("Cardio", true);
        courseTypeRepository.save(courseType);

        Room testRoom = new Room("Pool", 10, 10, 10);
        roomRepository.save(testRoom);

        InstructorAccount testInstructor = new InstructorAccount("raydatray@gmail.com", "password");
        instructorAccountRepository.save(testInstructor);

        List<DayOfWeek> testDays = List.of(new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.FRIDAY});

        CourseOffering testOffering  = new CourseOffering(Date.valueOf("2024-02-18"), Date.valueOf("2024-03-15"), testDays, testRoom, courseType, testInstructor);
        courseOfferingRepository.save(testOffering);

        Date sessionDate = Date.valueOf("2024-02-20");
        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("09:00:00");

        CourseSession testSession = new CourseSession(sessionDate, startTime, endTime, testOffering);
        courseSessionRepository.save(testSession);
        Integer generatedTestSessionID = testSession.getId();

        Optional<CourseSession> readSession = courseSessionRepository.findById(generatedTestSessionID);


        assertNotNull(testSession = readSession.orElse(null));

        assertEquals(testOffering.getId(), testSession.getCourseOffering().getId());

        assertEquals(courseType.getId(), testSession.getCourseOffering().getCourseType().getId());

        assertEquals(testInstructor.getId(), testSession.getCourseOffering().getInstructorAccount().getId());

        assertEquals(testRoom.getId(), testSession.getCourseOffering().getRoom().getId());

        assertEquals(sessionDate, testSession.getDate());
        assertEquals(startTime, testSession.getStartTime());
        assertEquals(endTime, testSession.getEndTime());
        assertEquals(generatedTestSessionID, testSession.getId());
    }
}
