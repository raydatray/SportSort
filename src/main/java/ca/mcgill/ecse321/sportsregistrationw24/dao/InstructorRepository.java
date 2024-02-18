package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.Customer;
import ca.mcgill.ecse321.sportsregistrationw24.model.Instructor;
import ca.mcgill.ecse321.sportsregistrationw24.model.SportSession;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.sql.Time;

public interface InstructorRepository extends CrudRepository<Instructor, Integer> {
    Customer findInstructorById(Integer id);
    boolean isTeachingClass(Integer classId);
    boolean proposeClass(Integer classId, String className);
    boolean createSession(Integer sessionId, Date date, SportSession.SessionType sessionType, Time startTime, Time endTime, Integer floorNumber, Integer roomNumber);
    boolean deleteSession(Integer sessionId);
}
