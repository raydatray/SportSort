package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CustomerAccountService {

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Transactional
    public CustomerAccount createCustomerAccount(String email, String password, String name) {
        CustomerAccount existingCustomeraccount = getCustomerAccount(email);
        if (existingCustomeraccount != null) {
            throw new IllegalArgumentException("An customer account already exists with this email");
        }
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setEmail(email);
        customerAccount.setPassword(password);
        customerAccount.setName(name);
        customerAccountRepository.save(customerAccount);
        return customerAccount;
    }

    @Transactional
    public void updateCustomerEmail(String oldEmail, String email) {
        CustomerAccount customerAccount = customerAccountRepository.findByEmail(oldEmail).orElse(null);
        if (customerAccount == null) {
            throw new IllegalArgumentException("Customer Account does not exist!");
        }
        customerAccount.setEmail(email);
        customerAccountRepository.save(customerAccount);
    }

    @Transactional
    public void updateCustomerPassword(CustomerAccount customer, String newPassword, String oldPassword) {
        // Incorrect old password
        if (!customer.getPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("Incorrect old password!");
        }
        // New password matches old password
        if (customer.getPassword().equals(newPassword)) {
            throw new IllegalArgumentException("New password cannot match the old password!");
        }
        customer.setPassword(newPassword);
        customerAccountRepository.save(customer);
    }

    @Transactional
    public void deleteCustomerAccount(String email) {
        CustomerAccount customerAccount = customerAccountRepository.findByEmail(email).orElse(null);

        if (customerAccount == null) {
            throw new IllegalArgumentException("Customer Account does not exist!");
        }

        customerAccountRepository.delete(customerAccount);
    }

    @Transactional
    public CustomerAccount getCustomerAccount(String email) {
        return customerAccountRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public List<CustomerAccount> getAllCustomerAccounts() {
        Utilities utilities = new Utilities();
        return utilities.iterableToArrayList(customerAccountRepository.findAll());
    }

}
