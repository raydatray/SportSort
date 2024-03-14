package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.InstructorAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
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
    public InstructorAccount createInstructorAccount(String email, String password, int id, String passwordConfirmation) {

        if (!(password.equals(passwordConfirmation))) {
            throw new IllegalArgumentException("Passwords do not match!");
        }

        InstructorAccount instructorAccount = new InstructorAccount();
        instructorAccount.setEmail(email);
        instructorAccount.setPassword(password);
        instructorAccount.setId(id);                    // TODO is an id needed?
        instructorAccountRepository.save(instructorAccount);
        return instructorAccount;
    }

    @Transactional      //TODO Correct return type?
    public void changeInstructorPassword(InstructorAccount instructor, String newPassword) {
        if (instructor.getPassword().equals(newPassword)) {
            throw new IllegalArgumentException("New password cannot match the old password!");
        }
        instructor.setPassword(newPassword);
        instructorAccountRepository.save(instructor);
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

    @Transactional
    public void deleteInstructor(String email) {
        instructorAccountRepository.deleteByEmail(email);
    }
}




