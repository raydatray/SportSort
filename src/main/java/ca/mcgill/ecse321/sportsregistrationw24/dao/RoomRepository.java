package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Integer> {
    Room findRoomByRoomNumber(Integer roomNumber);
    Optional<Room> findRoomByFloorNumberAndRoomNumber(@Param("floorNumber") Integer floorNumber, @Param("roomNumber") Integer roomNumber);

}
