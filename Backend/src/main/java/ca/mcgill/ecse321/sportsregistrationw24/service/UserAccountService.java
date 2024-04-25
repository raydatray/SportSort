package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.OwnerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.getUserFromToken;
import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.iterableToArrayList;


@Service
public class UserAccountService {
  private final UserAccountRepository userAccountRepository;
  @Autowired
  public UserAccountService(UserAccountRepository userAccountRepository) {
    this.userAccountRepository = userAccountRepository;
  }

  @Transactional
  public void createCustomerAccount(String aName, String aEmail, String aPassword) {
    Validate.notBlank(aName, "Name cannot be empty");
    Validate.notBlank(aEmail, "Email cannot be empty");
    Validate.notBlank(aPassword, "Password cannot be empty");

    UserAccount existingUserAccount = userAccountRepository.findUserByEmail(aEmail).orElse(null);

    Validate.notNull(existingUserAccount , "Email: " + aEmail + " is already in use!");

    CustomerAccount customerAccount = new CustomerAccount();

    customerAccount.setName(aName);
    customerAccount.setEmail(aEmail);
    customerAccount.setPassword(aPassword);

    userAccountRepository.save(customerAccount);
  }

  @Transactional
  public void createInstructorAccount(String userToken, String aName, String aEmail, String aPassword) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);
    Validate.isTrue(user.getUserType().equals("OWNER"), "Only owners can create instructor accounts");

    Validate.notBlank(aName, "Name cannot be empty");
    Validate.notBlank(aEmail, "Email cannot be empty");
    Validate.notBlank(aPassword, "Password cannot be empty");

    UserAccount existingUserAccount = userAccountRepository.findUserByEmail(aEmail).orElse(null);

    Validate.notNull(existingUserAccount , "Email: " + aEmail + " is already in use!");

    InstructorAccount instructorAccount = new InstructorAccount();

    instructorAccount.setName(aName);
    instructorAccount.setEmail(aEmail);
    instructorAccount.setPassword(aPassword);

    userAccountRepository.save(instructorAccount);
  }

  @Transactional
  public void updateUserAccountSelf(String userToken, String newName, String newEmail, String newPassword) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (newName != null) {
      Validate.notBlank(newName, "Name cannot be empty");
      user.setName(newName);
    }

    if (newEmail != null) {
      Validate.notBlank(newEmail, "Email cannot be empty");
      UserAccount existingUserAccount = userAccountRepository.findUserByEmail(newEmail).orElse(null);
      Validate.notNull(existingUserAccount, "Email: " + newEmail + " is already in use");
      user.setEmail(newEmail);
    }

    if (newPassword != null) {
      Validate.notBlank(newPassword, "Password cannot be empty");
      user.setPassword(newPassword);
    }
    userAccountRepository.save(user);
  }

  @Transactional
  public void updateUserAccountEmail(String userToken, String currEmail, String newName, String newEmail, String newPassword) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);
    Validate.isTrue(user.getUserType().equals("OWNER"), "Only owners can update user accounts by email");

    UserAccount userToUpdate = userAccountRepository.findUserByEmail(currEmail).orElse(null);
    Validate.notNull(userToUpdate, "No user found with email: " + currEmail);

    if (newName != null) {
      Validate.notBlank(newName, "Name cannot be empty");
      userToUpdate.setName(newName);
    }

    if (newEmail != null) {
      Validate.notBlank(newEmail, "Email cannot be empty");
      UserAccount existingUserAccount = userAccountRepository.findUserByEmail(newEmail).orElse(null);
      Validate.notNull(existingUserAccount, "Email: " + newEmail + " is already in use");
      userToUpdate.setEmail(newEmail);
    }

    if (newPassword != null) {
      Validate.notBlank(newPassword, "Password cannot be empty");
      userToUpdate.setPassword(newPassword);
    }
    userAccountRepository.save(userToUpdate);
  }

  @Transactional
  public UserAccount getUserByEmail(String userToken, String aEmail) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);
    Validate.isTrue(user.getUserType().equals("OWNER"), "Only owners can search user accounts by email");

    UserAccount foundUser = userAccountRepository.findUserByEmail(aEmail).orElse(null);

    if (foundUser == null) {
      throw new IllegalArgumentException("No user found with email: " + aEmail);
    }

    return foundUser;
  }

  @Transactional
  public List<UserAccount> getAllUsers(String userToken, List<String> discriminators) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);
    Validate.isTrue(user.getUserType().equals("OWNER"), "Only owners can view all users");

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
    Validate.notNull(foundUsers, "No instructors found");

    return foundUsers;
  }

  @Transactional
  public void deleteUserAccountSelf(String userToken) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    userAccountRepository.delete(user);
  }

  @Transactional
  public void deleteUserAccountByEmail(String userToken, String aEmail) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);
    Validate.isTrue(user.getUserType().equals("OWNER"), "Only owners can delete user accounts by email");

    UserAccount existingUserAccount = userAccountRepository.findUserByEmail(aEmail).orElse(null);
    Validate.notNull(existingUserAccount, "No user found with email: " + aEmail);

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
}
