package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.InstructorAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Deprecated
@Service
public class InstructorAccountService {

  @Autowired
  private InstructorAccountRepository instructorAccountRepository;

  @Transactional
  public InstructorAccount createInstructorAccount(String email, String password, String name) {
    if (email == null || password == null || name == null) {
      throw new IllegalArgumentException("Null value detected!");
    }
    Optional<InstructorAccount> existingInstructorAccount = instructorAccountRepository.findByEmail(email);
    if (existingInstructorAccount.isPresent()) {
      throw new IllegalArgumentException("Instructor email is already in use!");
    }
    InstructorAccount instructorAccount = new InstructorAccount();
    instructorAccount.setEmail(email);
    instructorAccount.setPassword(password);
    instructorAccount.setName(name);
    instructorAccountRepository.save(instructorAccount);
    return instructorAccount;
  }

  @Transactional
  public void updateInstructorEmail(String oldEmail, String newEmail) {
    InstructorAccount instructorAccount = instructorAccountRepository.findByEmail(oldEmail).orElse(null);
    // Instructor with this email does not exist
    if (instructorAccount == null) {
      throw new IllegalArgumentException("Instructor account does not exist!");
    }
    // Instructor's old email matches new email
    if (oldEmail.equals(newEmail)) {
      throw new IllegalArgumentException("New email matches old email!");
    }
    // New email is already in use
    Optional<InstructorAccount> existingInstructor = instructorAccountRepository.findByEmail(newEmail);
    if (existingInstructor.isPresent()) {
      throw new IllegalArgumentException("New email is already in use!");
    }
    instructorAccount.setEmail(newEmail);
    instructorAccountRepository.save(instructorAccount);
  }

  @Transactional
  public void updateInstructorPassword(String newPassword, String oldPassword, String token) {
    InstructorAccount instructorAccount = instructorAccountRepository.findByToken(token).orElse(null);
    if (instructorAccount == null) {
      throw new IllegalArgumentException("Instructor account does not exist!");
    }
    // Incorrect old password
    if (!instructorAccount.getPassword().equals(oldPassword)) {
      throw new IllegalArgumentException("Incorrect old password!");
    }
    // New password matches old password
    if (instructorAccount.getPassword().equals(newPassword)) {
      throw new IllegalArgumentException("New password cannot match the old password!");
    }
    instructorAccount.setPassword(newPassword);
    instructorAccountRepository.save(instructorAccount);
  }

  @Transactional
  public void deleteInstructorAccountByEmail(String email) {
    InstructorAccount instructorAccount = instructorAccountRepository.findByEmail(email).orElse(null);
    if (instructorAccount == null) {
      throw new IllegalArgumentException("Instructor account does not exist!");
    }
    instructorAccountRepository.delete(instructorAccount);
  }

  @Transactional
  public InstructorAccount getInstructorAccountByEmail(String email) {
    return instructorAccountRepository.findByEmail(email).orElse(null);
  }

  @Transactional
  public List<InstructorAccount> getAllInstructorAccounts() {
    Utilities utilities = new Utilities();
    return Utilities.iterableToArrayList(instructorAccountRepository.findAll());
  }

}

