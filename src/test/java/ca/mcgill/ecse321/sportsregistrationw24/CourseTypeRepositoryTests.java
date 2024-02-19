package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseTypeRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CourseTypeRepositoryTests {
    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @AfterEach
    public void clearDatabase() {
        courseTypeRepository.deleteAll();
    }
}
