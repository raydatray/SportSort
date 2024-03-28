package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class CustomerAccountService {

  @Autowired
  private CustomerAccountRepository customerAccountRepository;

  @Transactional
  public CustomerAccount createCustomerAccount(String email, String password, String name) {
    if (email == null || password == null || name == null){
      throw new IllegalArgumentException("Null value detected!");
    }
    CustomerAccount existingCustomeraccount = getCustomerAccountByEmail(email);
    if (existingCustomeraccount != null) {
      throw new IllegalArgumentException("Customer email is already in use!");
    }
    CustomerAccount customerAccount = new CustomerAccount();
    customerAccount.setEmail(email);
    customerAccount.setPassword(password);
    customerAccount.setName(name);
    customerAccountRepository.save(customerAccount);
    return customerAccount;
  }

  @Transactional
  public void updateCustomerEmail(String newEmail, String token) {
    CustomerAccount customerAccount = customerAccountRepository.findByToken(token).orElse(null);
    // Customer with this email does not exist
    if (customerAccount == null) {
      throw new IllegalArgumentException("Customer account does not exist!");
    }
    // Customer's old email matches new email
    if (customerAccount.getEmail().equals(newEmail)) {
      throw new IllegalArgumentException("New email matches old email!");
    }
    // New email is already in use
    Optional<CustomerAccount> existingCustomer = customerAccountRepository.findByEmail(newEmail);
    if (existingCustomer.isPresent()) {
      throw new IllegalArgumentException("New email is already in use!");
    }
    customerAccount.setEmail(newEmail);
    customerAccountRepository.save(customerAccount);
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
