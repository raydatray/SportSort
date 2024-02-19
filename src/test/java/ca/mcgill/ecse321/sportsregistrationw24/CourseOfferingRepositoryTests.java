package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseOfferingRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseTypeRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.InstructorAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.RoomRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CourseOfferingRepositoryTests {
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private InstructorAccountRepository instructorAccountRepository;

    @AfterEach
    public void clearDatabase() {
        courseOfferingRepository.deleteAll();
        courseTypeRepository.deleteAll();
        roomRepository.deleteAll();
        instructorAccountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCourseOffering(){
        //create a new course offering
        CourseType courseType = new CourseType(1, "Cardio", true);
        courseTypeRepository.save(courseType);

        Room testRoom = new Room(1, "Pool", 10, 10, 10);
        roomRepository.save(testRoom);

        InstructorAccount testInstructor = new InstructorAccount(1, "raydatray@gmail.com", "password");
        instructorAccountRepository.save(testInstructor);

        List<DayOfWeek> testDays = List.of(new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.FRIDAY});
        Date startDate = Date.valueOf("2024-02-18");
        Date endDate = Date.valueOf("2024-03-15");

        CourseOffering test  = new CourseOffering(1, startDate, endDate, testDays, testRoom, courseType, testInstructor);
        courseOfferingRepository.save(test);

        //Read the event
        Optional<CourseOffering> readOffering = courseOfferingRepository.findById(1);

        assertNotNull(test = readOffering.orElse(null));
        //Verify that the courseType is the same thing
        assertEquals(courseType.getId(), test.getCourseType().getId());

        assertEquals(testRoom.getId(), test.getRoom().getId());

        assertEquals(testInstructor.getId(), test.getInstructorAccount().getId());

        assertEquals(startDate, test.getStartDate());
        assertEquals(endDate, test.getEndDate());
        assertEquals(1, test.getId());
    }
}
