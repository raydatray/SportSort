package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.Instructor;
import org.springframework.data.repository.CrudRepository;

public interface InstructorRepository extends CrudRepository<Instructor, Integer> {
}

