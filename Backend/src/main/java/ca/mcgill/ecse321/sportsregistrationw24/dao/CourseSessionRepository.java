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

public interface CourseSessionRepository extends CrudRepository<CourseSession, Integer> {
    Optional<List<CourseSession>> findByDate(Date date);
    Optional<List<CourseSession>> findByCourseOffering(CourseOffering courseOffering);
    Optional<List<CourseSession>> findByStartTime(Time time);
    Optional<List<CourseSession>> findByEndTime(Time time);

    @Query("SELECT c FROM CourseSession c JOIN c.courseOffering co WHERE " +
            "(:date is null or c.date >= :lowDate) and " +
            "(:date is null or c.date <= :highDate) and "+
            "(:startTime is null or c.startTime >= :startTime) and " +
            "(:endTime is null or c.endTime <= :endTime) and "+
            "(:instructor is null or co.instructorAccount = :instructor)")

    Optional<List<CourseSession>> findByFilters(@Param("lowDate") Date lowDate,
                                                @Param("highDate") Date highDate,
                                                @Param("startTime") Time startTime,
                                                @Param("endTime") Time endTime,
                                                @Param("instructor")InstructorAccount instructor);

    void deleteByDate(Date date);
}
