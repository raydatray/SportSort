package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.Instructor;
import ca.mcgill.ecse321.sportsregistrationw24.model.Owner;
import ca.mcgill.ecse321.sportsregistrationw24.model.SportClass;
import ca.mcgill.ecse321.sportsregistrationw24.model.SportSession;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.sql.Time;

public interface OwnerRepository extends CrudRepository<Owner, Integer> {
    Instructor createInstructor(Integer instructorId, String email, String password);
    boolean deleteInstructorById(Integer instructorId);
    boolean deleteInstructorByEmail(String instructorEmail);
    boolean deleteCustomerByEmail(String customerEmail);
    boolean modifySportCenter(Time openingHour, Time closingHour);
    boolean modifySession(Integer sessionId, Date date, SportSession.SessionType sessionType, Time startTime, Time endTime, Integer floorNumber, Integer roomNumber);
    boolean approveClass(Integer classId);
    boolean hasPendingClasses();
}
