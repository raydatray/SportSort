package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Integer> {
    Optional<List<Room>> findRoomByRoomNumber(Integer roomNumber);
    Optional<List<Room>> findRoomByFloorNumber(Integer floorNumber);
    Optional<Room> findRoomByFloorNumberAndRoomNumber(Integer floorNumber,  Integer roomNumber);

    void deleteRoomByFloorNumberAndRoomNumber(Integer floorNumber, Integer roomNumber);

}
