package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.getUserFromToken;

@Deprecated
@Service
public class CustomerAccountService {
  @Autowired
  private CustomerAccountRepository customerAccountRepository;
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
  public void updateCustomerEmail(String userToken, String newEmail) {
    UserAccount userAccount = getUserFromToken(userAccountRepository, userToken);

    // Customer's old email matches new email
    if (userAccount.getEmail().equals(newEmail)) {
      throw new IllegalArgumentException("New email matches old email!");
    }
    // New email is already in use
    UserAccount existingUserAccount = userAccountRepository.findUserByEmail(newEmail).orElse(null);

    if (existingUserAccount != null) {
      throw new IllegalArgumentException("Customer email is already in use!");
    }

    userAccount.setEmail(newEmail);
    userAccountRepository.save(userAccount);
  }

  @Transactional
  public void updateCustomerPassword(String newPassword, String oldPassword, String token) {
    CustomerAccount customerAccount = customerAccountRepository.findByToken(token).orElse(null);
    if (customerAccount == null) {
      throw new IllegalArgumentException("Customer account does not exist!");
    }
    // Incorrect old password
    if (!customerAccount.getPassword().equals(oldPassword)) {
      throw new IllegalArgumentException("Incorrect old password!");
    }
    // New password matches old password
    if (customerAccount.getPassword().equals(newPassword)) {
      throw new IllegalArgumentException("New password cannot match the old password!");
    }
    customerAccount.setPassword(newPassword);
    customerAccountRepository.save(customerAccount);
  }

  @Transactional
  public void deleteCustomerAccountByEmail(String email) {
    CustomerAccount customerAccount = customerAccountRepository.findByEmail(email).orElse(null);

    if (customerAccount == null) {
      throw new IllegalArgumentException("Customer account does not exist!");
    }
    customerAccountRepository.delete(customerAccount);
  }

  @Transactional
  public void deleteCustomerAccountByToken(String token) {
    CustomerAccount customerAccount = customerAccountRepository.findByToken(token).orElse(null);
    if (customerAccount == null) {
      throw new IllegalArgumentException("Customer account does not exist!");
    }
    customerAccountRepository.delete(customerAccount);
  }

  @Transactional
  public CustomerAccount getCustomerAccountByEmail(String email) {
    return customerAccountRepository.findByEmail(email).orElse(null);
  }

  @Transactional
  public List<CustomerAccount> getAllCustomerAccounts() {
    Utilities utilities = new Utilities();
    return utilities.iterableToArrayList(customerAccountRepository.findAll());
  }

}
