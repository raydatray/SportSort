package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.InstructorAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructorAccountService {

    @Autowired
    private InstructorAccountRepository instructorAccountRepository;

    public void updateInstructorPassword(InstructorAccount instructor, String newPassword, String oldPassword) {
        // Incorrect old password
        if (!instructor.getPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("Incorrect old password!");
        }
        // New password matches old password
        if (instructor.getPassword().equals(newPassword)) {
            throw new IllegalArgumentException("New password cannot match the old password!");
        }
        instructor.setPassword(newPassword);
        instructorAccountRepository.save(instructor);
    }

}




