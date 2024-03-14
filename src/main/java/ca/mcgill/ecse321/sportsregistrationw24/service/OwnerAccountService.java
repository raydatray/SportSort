package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.InstructorAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.OwnerAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.OwnerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OwnerAccountService {
    @Autowired
    private OwnerAccountRepository ownerAccountRepository;
    @Autowired
    private CustomerAccountRepository customerAccountRepository;
    @Autowired
    private InstructorAccountRepository instructorAccountRepository;

    @Transactional
    public void updateOwnerEmail(String oldEmail, String email, String password) {
        OwnerAccount ownerAccount = ownerAccountRepository.findByEmail(oldEmail).orElse(null);

        if (ownerAccount == null) {
            throw new IllegalArgumentException("Owner Account does not exist!");
        }

        ownerAccount.setEmail(email);
        ownerAccount.setPassword(password);

        ownerAccountRepository.save(ownerAccount);
    }

    @Transactional      //TODO Correct return type?
    public void changeOwnerPassword(OwnerAccount owner, String newPassword, String oldPassword) {
        // Incorrect old password
        if (!owner.getPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("Incorrect old password!");
        }
        // New password matches old password
        if (owner.getPassword().equals(newPassword)) {
            throw new IllegalArgumentException("New password cannot match the old password!");
        }
        owner.setPassword(newPassword);
        ownerAccountRepository.save(owner);
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
    public List<CustomerAccount> getAllCustomerAccounts() {
        Utilities utilities = new Utilities();
        return utilities.iterableToArrayList(customerAccountRepository.findAll());
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
    public List<InstructorAccount> getAllInstructorAccounts() {
        Utilities utilities = new Utilities();
        return utilities.iterableToArrayList(instructorAccountRepository.findAll());
    }
}
