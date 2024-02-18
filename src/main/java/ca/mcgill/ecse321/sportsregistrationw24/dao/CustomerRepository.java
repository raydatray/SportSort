package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.Customer;
import org.springframework.data.repository.CrudRepository;


public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Customer findCustomerById(Integer id);
}
