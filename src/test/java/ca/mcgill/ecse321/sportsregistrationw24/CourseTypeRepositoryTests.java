package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseTypeRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CourseTypeRepositoryTests {
    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        courseTypeRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCourseType(){
        String typeName = "Cardio";
        boolean approval = true;
        CourseType testType = new CourseType(1, typeName, approval);

        courseTypeRepository.save(testType);

        Optional<CourseType> readType = courseTypeRepository.findByCourseName("Cardio");

        assertNotNull(testType = readType.orElse(null));
        assertEquals(typeName, testType.getCourseName());
        assertEquals(approval, testType.getApproved());
        assertEquals(1, testType.getId());
    }
}
