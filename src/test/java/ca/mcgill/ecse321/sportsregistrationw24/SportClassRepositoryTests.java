package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.SportClassRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SportClassRepositoryTests {
    @Autowired
    private SportClassRepository sportClassRepository;

    @AfterEach
    public void clearDatabase() {
        sportClassRepository.deleteAll();
    }
}
