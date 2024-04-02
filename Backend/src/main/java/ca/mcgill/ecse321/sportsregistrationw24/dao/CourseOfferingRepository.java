package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface CourseOfferingRepository extends CrudRepository<CourseOffering, Integer> {
    Optional<List<CourseOffering>> findByInstructorAccount(InstructorAccount instructor);
    Optional<List<CourseOffering>> findByCourseType(CourseType courseType);
    Optional<List<CourseOffering>> findByRoom(Room room);
    Optional<List<CourseOffering>> findByStartDate(Date date);
    Optional<List<CourseOffering>> findByEndDate(Date date);

    //No delete by courseType as it will cascade down to courseOffering
    //No delete by instructorAccount as it will cascade down to courseOffering
    @Query("SELECT c FROM CourseOffering c JOIN c.courseSessions s WHERE " +
      "(:startDate is null or c.startDate >= :startDate) and " +
      "(:endDate is null or c.endDate <= :endDate) and " +
      "(:courseType is null or c.courseType = :courseType) and " +
      "(:dayOffered is null or :dayOffered member of c.daysOffered) and " +
      "(:startTime is null or s.startTime >= :startTime) and " +
      "(:endTime is null or s.endTime <= :endTime) and " +
      "(:instructor is null or c.instructorAccount = :instructor)")
    List<CourseOffering> findCourseOfferingsByFilters(@Param("startDate") Date startDate,
                                                      @Param("endDate") Date endDate,
                                                      @Param("courseType") CourseType courseType,
                                                      @Param("dayOffered") DayOfWeek dayOffered,
                                                      @Param("startTime") LocalTime startTime,
                                                      @Param("endTime") LocalTime endTime,
                                                      @Param("instructor") InstructorAccount instructor);

}

