package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.OwnerAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OwnerAccountRepositoryTests {
    @Autowired
    private OwnerAccountRepository ownerAccountRepository;

    @AfterEach
    public void clearDatabase() {
        ownerAccountRepository.deleteAll();
    }
}
