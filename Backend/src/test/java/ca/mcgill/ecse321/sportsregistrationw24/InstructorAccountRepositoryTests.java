package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.InstructorAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class InstructorAccountRepositoryTests {
    @Autowired
    private InstructorAccountRepository instructorAccountRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        instructorAccountRepository.deleteAll();
    }


    @Test
    public void testPersistAndLoadInstructorAccount() {
        String testName = "colin";
        String testEmail = "colin@gmail.com";
        String testPassword = "obama";

        InstructorAccount testInstructor = new InstructorAccount(testName, testEmail, testPassword);

        instructorAccountRepository.save(testInstructor);

        Optional<InstructorAccount> readInstructor = instructorAccountRepository.findByEmail(testEmail);

        assertNotNull(testInstructor = readInstructor.orElse(null));
        assertEquals(testEmail, testInstructor.getEmail());
        assertEquals(testPassword, testInstructor.getPassword());
    }
}
