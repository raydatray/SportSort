package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.Registration;
import ca.mcgill.ecse321.sportsregistrationw24.model.keys.RegistrationId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegistrationRepository extends CrudRepository<Registration, Integer> {
    Registration getRegistrationById(RegistrationId id);
    List<Registration> findByCustomerId(Integer customerId);
    List<Registration> findBySportSessionId(Integer sportSessionId);
    boolean existsByRegistrationById(RegistrationId id);
}