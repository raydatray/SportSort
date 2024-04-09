package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseSession;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface CourseSessionRepository extends CrudRepository<CourseSession, Integer>, CourseSessionRepositoryCustom {
    Optional<List<CourseSession>> findByDate(Date date);
    Optional<List<CourseSession>> findByCourseOffering(CourseOffering courseOffering);
    Optional<List<CourseSession>> findByStartTime(Time time);
    Optional<List<CourseSession>> findByEndTime(Time time);
    void deleteByDate(Date date);
}
