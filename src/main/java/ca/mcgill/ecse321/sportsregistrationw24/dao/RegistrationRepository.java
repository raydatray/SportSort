package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.Registration;
import ca.mcgill.ecse321.sportsregistrationw24.model.keys.RegistrationId;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends CrudRepository<Registration, RegistrationId> {
    Optional<List<Registration>> findByCustomerAccount(CustomerAccount customerAccount);
    Optional<List<Registration>> findByCourseOffering(CourseOffering courseOffering);
    Optional<List<Registration>> findByRegisteredDate(Date date);

}