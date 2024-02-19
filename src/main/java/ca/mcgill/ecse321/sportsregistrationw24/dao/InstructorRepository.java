package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.Customer;
import ca.mcgill.ecse321.sportsregistrationw24.model.Instructor;
import ca.mcgill.ecse321.sportsregistrationw24.model.SportSession;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

public interface InstructorRepository extends CrudRepository<Instructor, Integer> {
    Optional<Instructor> findInstructorByEmail(String email);
    Optional<Instructor> findInstructorByName(String name);
    boolean existsByInstructorEmail(String email);
}
