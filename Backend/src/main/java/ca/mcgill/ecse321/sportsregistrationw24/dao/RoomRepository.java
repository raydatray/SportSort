package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Integer> {
    Optional<List<Room>> findRoomByRoomNumber(Integer roomNumber);
    Optional<List<Room>> findRoomByFloorNumber(Integer floorNumber);
    Optional<Room> findRoomByFloorNumberAndRoomNumber(Integer floorNumber,  Integer roomNumber);
    @Query("SELECT r FROM Room r WHERE (:floorNumber is null or r.floorNumber = :floorNumber) and (:capacityLow is null or r.capacity >= :capacityLow) and (:capacityHigh is null or r.capacity <= :capacityHigh)")
    Optional<List<Room>> findRoomsByFilters(@Param("floorNumber") Integer floorNumber, @Param("capacityLow") Integer capacityLow, @Param("capacityHigh") Integer capacityHigh);
    void deleteRoomByFloorNumberAndRoomNumber(Integer floorNumber, Integer roomNumber);
}
