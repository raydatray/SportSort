package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;
import ca.mcgill.ecse321.sportsregistrationw24.utilities.*;

import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserAccountRepositoryTests {
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private TokenProvider tokenProvider;


    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        userAccountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadUsers() {
        String testInstructorName = "ray";
        String testInstructorEmail = "colin@gmail.com";
        String testInstructorPassword = "obama";

        InstructorAccount testInstructor = new InstructorAccount(testInstructorName,testInstructorEmail, testInstructorPassword);


        String testOwnerName = "alex";
        String testOwnerEmail = "alex@hotmail.com";
        String testOwnerPassword = "password";

        OwnerAccount testOwner = new OwnerAccount("alex",testOwnerEmail, testOwnerPassword);

        String testCustomerName = "joe";
        String testCustomerEmail = "joebama@gmail.com";
        String testCustomerPassword = "obama";

        CustomerAccount testCustomer = new CustomerAccount(testCustomerName,testCustomerEmail, testCustomerPassword);

        //We want to verify the hash generators are working
        //1. Generate the hash for each user and save it in the user
        //2. Retrieve users and ensure that they are correct
        //TODO: Check if the expiration is working???
        testInstructor.setToken(tokenProvider.generateToken(testInstructorEmail));
        testOwner.setToken(tokenProvider.generateToken(testOwnerEmail));
        testCustomer.setToken(tokenProvider.generateToken(testCustomerEmail));
        //LETS GO COPILOT

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
