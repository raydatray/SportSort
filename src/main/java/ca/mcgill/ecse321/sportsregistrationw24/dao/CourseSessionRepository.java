package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseSession;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface CourseSessionRepository extends CrudRepository<CourseSession, Integer> {
    List<CourseSession> findCourseSessionByDate(Date date);
    List<CourseSession> findCourseSessionByFloorNumberAndRoomNumber(Integer floorNumber, Integer roomNumber);
    List<CourseSession> findCourseSessionBySportType(String sportType);

}
