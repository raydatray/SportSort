package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface CustomerAccountRepository extends CrudRepository<CustomerAccount, Integer> {
    Optional<CustomerAccount> findCustomerAccountByEmail(String customerEmail);
    Optional<CustomerAccount> findCustomerAccountByName(String customerName);

    // Check if a customer with a specific email exists
    boolean existsByCustomerAccountEmail(String email);

    // Delete a customer by email
    void deleteByEmail(String email);
    }

