package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dto.UserAccounts.UserAccountDTO;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.OwnerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.getUserFromToken;
import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.iterableToArrayList;


@Service
public class UserAccountService {
  @Autowired
  private UserAccountRepository userAccountRepository;

  @Transactional
  public void createCustomerAccount(String aName, String aEmail, String aPassword) {
    if (aName.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be empty!");
    }

    if (aEmail.trim().isEmpty()) {
      throw new IllegalArgumentException("Email cannot be empty!");
    }

    if (aPassword.trim().isEmpty()) {
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
  public UserAccountDTO createInstructorAccount(String userToken, String aName, String aEmail, String aPassword) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (!user.getUserType().equals("OWNER")) {
      throw new IllegalArgumentException("Only owners can create instructor accounts!");
    }

    if (aName.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be empty!");
    }

    if (aEmail.trim().isEmpty()) {
      throw new IllegalArgumentException("Email cannot be empty!");
    }

    if (aPassword.trim().isEmpty()) {
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

    return convertToDto(instructorAccount);
  }

  @Transactional
  public void updateAccount(String userToken, String newName, String newEmail, String newPassword) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (newName.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be empty!");
    }

    if (newEmail.trim().isEmpty()) {
      throw new IllegalArgumentException("Email cannot be empty!");
    }

    if (newPassword.trim().isEmpty()) {
      throw new IllegalArgumentException("Password cannot be empty!");
    }

    // Check if the new email is different from the current one and if it is already in use by another user
    if (newEmail.equals(user.getEmail())) { // Only check for email existence if it's been changed
      UserAccount existingUserAccount = userAccountRepository.findUserByEmail(newEmail).orElse(null);

      String existingUserToken = existingUserAccount.getToken();

      if (existingUserToken == null || !existingUserToken.equals(userToken)) {
        throw new IllegalArgumentException("Email is already in use!");
      }
    }

    user.setName(newName);
    user.setEmail(newEmail);
    user.setPassword(newPassword);

    userAccountRepository.save(user);
  }

  @Transactional
  public UserAccountDTO updateUserAccount(String userToken, String currEmail, String newName, String newEmail, String newPassword) {
    UserAccount user = getUserByEmail(userToken, currEmail);

    if (user == null) {
      throw new IllegalArgumentException("User could not be found with provided email!");
    }

    if (newName.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be empty!");
    }

    if (newEmail.trim().isEmpty()) {
      throw new IllegalArgumentException("Email cannot be empty!");
    }

    if (newPassword == null || newPassword.trim().isEmpty()) {
      newPassword = user.getPassword();
    }

    UserAccount existingUserAccount = userAccountRepository.findUserByEmail(newEmail).orElse(null);

    if (existingUserAccount != null && existingUserAccount != user) {
      throw new IllegalArgumentException("Email is already in use!");
    }

    user.setName(newName);
    user.setEmail(newEmail);
    user.setPassword(newPassword);

    userAccountRepository.save(user);
    return convertToDto(user);
  }

  @Transactional
  public UserAccount getUserByToken(String userToken) {
    return getUserFromToken(userAccountRepository, userToken);
  }

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
  public List<UserAccount> getAllUsers(String userToken, List<String> discriminators) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (!user.getUserType().equals("OWNER")) {
      throw new IllegalArgumentException("Only owners can view all users!");
    }

    if (discriminators == null) {
      return iterableToArrayList(userAccountRepository.findAll());
    } else {
      List<UserAccount> foundUsers = userAccountRepository.findByUserType(mapDiscriminatorsToClasses(discriminators)).orElse(null);

      if (foundUsers == null) {
        throw new IllegalArgumentException("No users found with discriminator: " + discriminators);
      }

      return foundUsers;
    }
  }

  @Transactional
  public List<UserAccount> getAllInstructors() {
    List<UserAccount> foundUsers = userAccountRepository.findByUserType(Collections.singletonList(InstructorAccount.class)).orElse(null);

    if (foundUsers == null) {
      throw new IllegalArgumentException("No instructors found!");
    }

    return foundUsers;
  }

  @Transactional
  public void deleteUserAccount(String userToken, String aEmail) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (!user.getUserType().equals("OWNER") && !user.getEmail().equals(aEmail)) {
      throw new IllegalArgumentException("Only owner or yourself can delete this account!");
    }

    UserAccount existingUserAccount = userAccountRepository.findUserByEmail(aEmail).orElse(null);

    if (existingUserAccount == null) {
      throw new IllegalArgumentException("No user found with email: " + aEmail);
    }

    userAccountRepository.delete(existingUserAccount);
  }

  private List<Class<?>> mapDiscriminatorsToClasses(List<String> discriminators) {
    Map<String, Class<?>> discriminatorMap = new HashMap<>();
    discriminatorMap.put("CUSTOMER", CustomerAccount.class);
    discriminatorMap.put("OWNER", OwnerAccount.class);
    discriminatorMap.put("INSTRUCTOR", InstructorAccount.class);

    List<Class<?>> classes = new ArrayList<>();
    for (String discriminator : discriminators) {
      Class<?> clazz = discriminatorMap.get(discriminator);
      if (clazz != null) {
        classes.add(clazz);
      }
    }

    return classes;
  }

  private UserAccountDTO convertToDto(UserAccount userAccount) {
    if (userAccount == null) {
      throw new IllegalArgumentException("There is no such user account!");
    }
    return new UserAccountDTO(userAccount.getUserType(), userAccount.getEmail(), userAccount.getName(), userAccount.getToken());
  }
}
