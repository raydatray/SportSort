package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.Instructor;
import ca.mcgill.ecse321.sportsregistrationw24.model.SportClass;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SportClassRepository extends CrudRepository<SportClass, Integer> {
    Optional<List<CourseOffering>> findCourseOfferingByInstructor(Instructor instructor);
}

