package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.StaffAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StaffAccountRepositoryTests {
    @Autowired
    private StaffAccountRepository staffAccountRepository;

    @AfterEach
    public void clearDatabase() {
        staffAccountRepository.deleteAll();
    }
}
