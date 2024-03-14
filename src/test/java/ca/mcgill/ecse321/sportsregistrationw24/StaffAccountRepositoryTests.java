package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.StaffAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.OwnerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.StaffAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class StaffAccountRepositoryTests {
    @Autowired
    private StaffAccountRepository staffAccountRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        staffAccountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadStaff() {
        String testInstructorName = "Colin";
        String testInstructorEmail = "colin@gmail.com";
        String testInstructorPassword = "obama";

        InstructorAccount testInstructor = new InstructorAccount(testInstructorName, testInstructorEmail, testInstructorPassword);

        String testOwnerName = "Alex";
        String testOwnerEmail = "alex@hotmail.com";
        String testOwnerPassword = "password";

        OwnerAccount testOwner = new OwnerAccount(testOwnerName, testOwnerEmail, testOwnerPassword);

        staffAccountRepository.save(testInstructor);
        staffAccountRepository.save(testOwner);

        Optional<StaffAccount> readInstructor = staffAccountRepository.findStaffByEmail(testInstructorEmail);
        Optional<StaffAccount> readOwner = staffAccountRepository.findStaffByEmail(testOwnerEmail);

        assertNotNull(testInstructor = (InstructorAccount) readInstructor.orElse(null));
        assertEquals(testInstructorEmail, testInstructor.getEmail());
        assertEquals(testInstructorPassword, testInstructor.getPassword());

        assertNotNull(testOwner = (OwnerAccount) readOwner.orElse(null));
        assertEquals(testOwnerEmail, testOwner.getEmail());
        assertEquals(testOwnerPassword, testOwner.getPassword());
    }
}
