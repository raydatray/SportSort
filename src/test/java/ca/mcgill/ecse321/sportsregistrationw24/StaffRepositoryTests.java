package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.StaffRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StaffRepositoryTests {
    @Autowired
    private StaffRepository staffRepository;

    @AfterEach
    public void clearDatabase() {
        staffRepository.deleteAll();
    }
}
