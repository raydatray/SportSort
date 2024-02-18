package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.SportSession;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface SportSessionRepository extends CrudRepository<SportSession, Integer> {
    SportSession findSessionById(Integer classId);
    List<SportSession> findBySessionType(SportSession.SessionType sessionType);
    List<SportSession> findByDate(Date date);
    List<SportSession> findByFloorNumberAndRoomNumber(Integer floorNumber, Integer roomNumber);
}
