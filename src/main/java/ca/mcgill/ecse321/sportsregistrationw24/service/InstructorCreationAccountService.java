package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.InstructorAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstructorCreationAccountService {

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
    public void changePassword(InstructorAccount instructor, String newPassword) {
        if (instructor.getPassword().equals(newPassword)) {
            throw new IllegalArgumentException("New password cannot match the old password!");
        }
        instructor.setPassword(newPassword);
        instructorAccountRepository.save(instructor);
    }

    @Transactional            //TODO Find by mail does not work?
    public InstructorAccount getInstructorAccount (String email){
        return instructorAccountRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public List<InstructorAccount> getAllInstructorAccounts() {
        return toList(instructorAccountRepository.findAll());
    }

    @Transactional
    public void deleteInstructor(String email) {
        instructorAccountRepository.deleteByEmail(email);
    }

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}

