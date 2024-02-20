package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CustomerAccountRepositoryTests {
    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        customerAccountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCustomerAccount() {
        String testEmail = "joebama@gmail.com";
        String testPassword = "obama";

        CustomerAccount testCustomer = new CustomerAccount(1, testEmail, testPassword);

        customerAccountRepository.save(testCustomer);

        Optional<CustomerAccount> readCustomer = customerAccountRepository.findByEmail(testEmail);

        assertNotNull(testCustomer = readCustomer.orElse(null));
        assertEquals(testEmail, testCustomer.getEmail());
        assertEquals(testPassword, testCustomer.getPassword());
    }
}
