package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.Registration;
import ca.mcgill.ecse321.sportsregistrationw24.model.keys.RegistrationId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends CrudRepository<Registration, RegistrationId> {
    Optional<List<Registration>> findByCustomerAccount(CustomerAccount customerAccount);
    Optional<List<Registration>> findByCourseOffering(CourseOffering courseOffering);
    Optional<List<Registration>> findByRegisteredDate(Date date);

    @Query("SELECT r FROM Registration r WHERE " +
            "(:courseOfferings is null or r.courseOffering IN :courseOfferings) and " +
            "(:customerAccount is null or r.customerAccount = :customerAccount) and " +
            "(:dateLow is null or r.registeredDate >= :dateLow) and " +
            "(:dateHigh is null or r.registeredDate <= :dateHigh)")
    Optional<List<Registration>> findByFilters(
      @Param("courseOfferings") List<CourseOffering> courseOfferings,
      @Param("customerAccount") CustomerAccount customerAccount,
      @Param("dateLow") Date dateLow,
      @Param("dateHigh") Date dateHigh
    );

}