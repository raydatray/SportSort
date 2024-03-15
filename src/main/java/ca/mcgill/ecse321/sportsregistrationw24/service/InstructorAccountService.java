package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.InstructorAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InstructorAccountService {

    @Autowired
    private InstructorAccountRepository instructorAccountRepository;

    @Transactional
    public InstructorAccount createInstructorAccount(String email, String password, String passwordConfirmation) {

        if (!(password.equals(passwordConfirmation))) {
            throw new IllegalArgumentException("Passwords do not match!");
        }

        InstructorAccount instructorAccount = new InstructorAccount();
        instructorAccount.setEmail(email);
        instructorAccount.setPassword(password);
        instructorAccountRepository.save(instructorAccount);
        return instructorAccount;
    }

    @Transactional
    public void updateInstructorEmail(String oldEmail, String email, String password) {
        InstructorAccount instructorAccount = instructorAccountRepository.findByEmail(oldEmail).orElse(null);

        if (instructorAccount == null) {
            throw new IllegalArgumentException("Owner Account does not exist!");
        }

        instructorAccount.setEmail(email);
        instructorAccount.setPassword(password);

        instructorAccountRepository.save(instructorAccount);
    }

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

    @Transactional
    public void deleteInstructorAccount(String email) {
        InstructorAccount instructorAccount = instructorAccountRepository.findByEmail(email).orElse(null);

        if (instructorAccount == null) {
            throw new IllegalArgumentException("Instructor account does not exist!");
        }

        instructorAccountRepository.delete(instructorAccount);
    }

    @Transactional
    public InstructorAccount getInstructorAccount(String email) {
        return instructorAccountRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public List<InstructorAccount> getAllInstructorAccounts() {
        Utilities utilities = new Utilities();
        return utilities.iterableToArrayList(instructorAccountRepository.findAll());
    }

}




