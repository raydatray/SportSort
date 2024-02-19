package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CustomerAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerAccountRepositoryTests {
    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @AfterEach
    public void clearDatabase() {
        customerAccountRepository.deleteAll();
    }
}
