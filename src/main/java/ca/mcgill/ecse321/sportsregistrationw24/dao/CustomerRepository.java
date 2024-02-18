package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.Customer;
import ca.mcgill.ecse321.sportsregistrationw24.model.SportClass;
import org.springframework.data.repository.CrudRepository;


public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Customer findCustomerById(Integer id);
    boolean isRegisteredForClass(Integer classId);
    boolean registerForClass(Integer classId);
    boolean registerForSession(Integer sessionId);
    boolean cancelClass(Integer classId);
    }

