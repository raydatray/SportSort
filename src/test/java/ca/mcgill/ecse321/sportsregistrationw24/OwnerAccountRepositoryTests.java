package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.OwnerAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.OwnerAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class OwnerAccountRepositoryTests {
    @Autowired
    private OwnerAccountRepository ownerAccountRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        ownerAccountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadOwnerAccount() {
        String testEmail = "joebama@gmail.com";
        String testPassword = "obama";

        OwnerAccount testOwner = new OwnerAccount(1, testEmail, testPassword);

        ownerAccountRepository.save(testOwner);

        Optional<OwnerAccount> readOwner = ownerAccountRepository.findByEmail(testEmail);

        assertNotNull(testOwner = readOwner.orElse(null));
        assertEquals(testEmail, testOwner.getEmail());
        assertEquals(testPassword, testOwner.getPassword());
    }
}
