package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserAccountRepositoryTests {
    @Autowired
    private UserAccountRepository userAccountRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        userAccountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadUsers() {
        String testInstructorEmail = "colin@gmail.com";
        String testInstructorPassword = "obama";

        InstructorAccount testInstructor = new InstructorAccount(1, testInstructorEmail, testInstructorPassword);


        String testOwnerEmail = "alex@hotmail.com";
        String testOwnerPassword = "password";

        OwnerAccount testOwner = new OwnerAccount(2, testOwnerEmail, testOwnerPassword);

        String testCustomerEmail = "joebama@gmail.com";
        String testCustomerPassword = "obama";

        CustomerAccount testCustomer = new CustomerAccount(3, testCustomerEmail, testCustomerPassword);

        userAccountRepository.save(testInstructor);
        userAccountRepository.save(testOwner);
        userAccountRepository.save(testCustomer);

        Optional<UserAccount> readInstructor = userAccountRepository.findUserByEmail(testInstructorEmail);
        Optional<UserAccount> readOwner = userAccountRepository.findUserByEmail(testOwnerEmail);
        Optional<UserAccount> readCustomer = userAccountRepository.findUserByEmail(testCustomerEmail);

        assertNotNull(testInstructor = (InstructorAccount) readInstructor.orElse(null));
        assertEquals(testInstructorEmail, testInstructor.getEmail());
        assertEquals(testInstructorPassword, testInstructor.getPassword());

        assertNotNull(testOwner = (OwnerAccount) readOwner.orElse(null));
        assertEquals(testOwnerEmail, testOwner.getEmail());
        assertEquals(testOwnerPassword, testOwner.getPassword());

        assertNotNull(testCustomer = (CustomerAccount) readCustomer.orElse(null));
        assertEquals(testCustomerEmail, testCustomer.getEmail());
        assertEquals(testCustomerPassword, testCustomer.getPassword());


    }
}
