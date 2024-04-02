package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;

import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;

import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserAccountService {
  @Autowired
  private UserAccountRepository userAccountRepository;

  @Transactional
  public void createCustomerAccount(String aName, String aEmail, String aPassword) {
    if (aName.trim().isEmpty()){
      throw new IllegalArgumentException("Name cannot be empty!");
    }

    if (aEmail.trim().isEmpty()){
      throw new IllegalArgumentException("Email cannot be empty!");
    }

    if (aPassword.trim().isEmpty()){
      throw new IllegalArgumentException("Password cannot be empty!");
    }

    UserAccount existingUserAccount = userAccountRepository.findUserByEmail(aEmail).orElse(null);

    if (existingUserAccount != null) {
      throw new IllegalArgumentException("Email is already in use!");
    }

    CustomerAccount customerAccount = new CustomerAccount();

    customerAccount.setName(aName);
    customerAccount.setEmail(aEmail);
    customerAccount.setPassword(aPassword);

    userAccountRepository.save(customerAccount);
  }

  @Transactional
  public void createInstructorAccount(String userToken, String aName, String aEmail, String aPassword) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (!user.getUserType().equals("OWNER")) {
      throw new IllegalArgumentException("Only owners can create instructor accounts!");
    }

    if (aName.trim().isEmpty()){
      throw new IllegalArgumentException("Name cannot be empty!");
    }

    if (aEmail.trim().isEmpty()){
      throw new IllegalArgumentException("Email cannot be empty!");
    }

    if (aPassword.trim().isEmpty()){
      throw new IllegalArgumentException("Password cannot be empty!");
    }

    UserAccount existingUserAccount = userAccountRepository.findUserByEmail(aEmail).orElse(null);

    if (existingUserAccount != null) {
      throw new IllegalArgumentException("Email is already in use!");
    }

    InstructorAccount instructorAccount = new InstructorAccount();

    instructorAccount.setName(aName);
    instructorAccount.setEmail(aEmail);
    instructorAccount.setPassword(aPassword);

    userAccountRepository.save(instructorAccount);
  }

  @Transactional
  public void updateUserAccount(String userToken, String newName, String newEmail, String newPassword) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (newName.trim().isEmpty()){
      throw new IllegalArgumentException("Name cannot be empty!");
    }

    if (newEmail.trim().isEmpty()){
      throw new IllegalArgumentException("Email cannot be empty!");
    }

    if (newPassword.trim().isEmpty()){
      throw new IllegalArgumentException("Password cannot be empty!");
    }

    UserAccount existingUserAccount = userAccountRepository.findUserByEmail(newEmail).orElse(null);

    if (existingUserAccount != null) {
      throw new IllegalArgumentException("Email is already in use!");
    }

    user.setName(newName);
    user.setEmail(newEmail);
    user.setPassword(newPassword);

    userAccountRepository.save(user);
  }
  @Deprecated
  @Transactional
  public UserAccount getUserByEmail(String userToken, String aEmail) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (!user.getUserType().equals("OWNER")) {
      throw new IllegalArgumentException("Only owners can view user accounts!");
    }

    UserAccount foundUser = userAccountRepository.findUserByEmail(aEmail).orElse(null);

    if (foundUser == null) {
      throw new IllegalArgumentException("No user found with email: " + aEmail);
    }

    return foundUser;
  }

  @Transactional
  public List<UserAccount> getAllUsers(String userToken, List<String> discriminators){
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (!user.getUserType().equals("OWNER")) {
      throw new IllegalArgumentException("Only owners can view all users!");
    }

    if (discriminators == null) {
      return iterableToArrayList(userAccountRepository.findAll());
    } else {
      List<UserAccount> foundUsers = userAccountRepository.findByUserType(discriminators).orElse(null);

      if (foundUsers == null) {
        throw new IllegalArgumentException("No users found with discriminator: " + discriminators);
      }

      return foundUsers;
    }
  }

  @Transactional
  public void deleteUserAccount(String userToken, String aEmail) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (!user.getUserType().equals("OWNER")) {
      throw new IllegalArgumentException("Only owners can delete user accounts!");
    }

    UserAccount existingUserAccount = userAccountRepository.findUserByEmail(aEmail).orElse(null);

    if (existingUserAccount == null) {
      throw new IllegalArgumentException("No user found with email: " + aEmail);
    }

    userAccountRepository.delete(existingUserAccount);
  }
}
