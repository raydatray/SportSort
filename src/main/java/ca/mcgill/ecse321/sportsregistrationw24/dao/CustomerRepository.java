package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Optional<Customer> findCustomerByEmail(String customerEmail);
    Optional<Customer> findCustomerByName(String customerName);

    // Check if a customer with a specific email exists
    boolean existsByCustomerEmail(String email);

    // Delete a customer by email
    void deleteByEmail(String email);
    }

