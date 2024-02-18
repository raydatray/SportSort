package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.Customer;
import ca.mcgill.ecse321.sportsregistrationw24.model.Instructor;
import ca.mcgill.ecse321.sportsregistrationw24.model.SportSession;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

public interface InstructorRepository extends CrudRepository<Instructor, Integer> {
    Optional<Instructor> findInstructorById(Integer id);
    Optional<Instructor> findInstructorByEmail(String email);
    boolean isTeachingClass(Integer classId);
    boolean proposeClass(Integer classId, String className);
    boolean createSession(Integer sessionId, Date date, SportSession.SessionType sessionType, Time startTime, Time endTime, Integer floorNumber, Integer roomNumber);
    boolean deleteSession(Integer sessionId);
}
