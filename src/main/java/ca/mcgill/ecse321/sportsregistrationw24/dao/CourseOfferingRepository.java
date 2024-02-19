package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CourseOfferingRepository extends CrudRepository<CourseOffering, Integer> {
    Optional<List<CourseOffering>> findCourseOfferingByInstructor(InstructorAccount instructor);
    List<CourseOffering> findSessionBySessionType(CourseType courseType);

}

