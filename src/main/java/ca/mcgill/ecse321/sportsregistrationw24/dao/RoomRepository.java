package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Integer> {
    Room findRoomByRoomNumber(Integer roomNumber);
    Room findRoomByFloorNumberAndRoomNumber(Integer floorNumber, Integer roomNumber);
}
