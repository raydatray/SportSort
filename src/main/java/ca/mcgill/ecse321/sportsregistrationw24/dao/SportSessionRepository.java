package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.SportSession;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface SportSessionRepository extends CrudRepository<SportSession, Integer> {
    List<SportSession> findSessionBySessionType(SportSession.SessionType sessionType);
    List<SportSession> findSessionByDate(Date date);
    List<SportSession> findSessionByFloorNumberAndRoomNumber(Integer floorNumber, Integer roomNumber);
}
