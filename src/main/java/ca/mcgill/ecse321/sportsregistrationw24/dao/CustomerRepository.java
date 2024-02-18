package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Customer findCustomerById(Integer id);
    Optional<Customer> findByEmail(String email);

    // Check if a customer with a specific email exists
    boolean existsByEmail(String email);

    // Delete a customer by email
    void deleteByEmail(String email);
    }

