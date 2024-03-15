package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseOfferingRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.RoomRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RoomService {

  @Autowired
  private RoomRepository roomRepository;

  @Autowired
  CourseOfferingRepository courseOfferingRepository;
  @Transactional
  public Room createRoom(String aName, Integer aFloorNumber, Integer aRoomNumber, Integer capacity) {
    Optional<Room> wrappedTestRoom = roomRepository.findRoomByFloorNumberAndRoomNumber(aFloorNumber, aRoomNumber);
    Room testRoom = wrappedTestRoom.orElse(null);
    if (testRoom != null) {
      throw new IllegalArgumentException("A room with this floor and room number already exists");
    }

    Room room = new Room();
    room.setName(aName);
    room.setFloorNumber(aFloorNumber);
    room.setRoomNumber(aRoomNumber);
    room.setCapacity(capacity);

    roomRepository.save(room);

    return room;
  }

  @Transactional
  public void updateRoom(String newName, Integer aFloorNumber, Integer aRoomNumber, Integer newCapacity) {
    Optional<Room> wrappedRoomToUpdate = roomRepository.findRoomByFloorNumberAndRoomNumber(aFloorNumber, aRoomNumber);
    Room roomToUpdate = wrappedRoomToUpdate.orElseThrow(() -> new IllegalArgumentException("No room with this floor and room number was found"));

    roomToUpdate.setCapacity(newCapacity);
    roomToUpdate.setName(newName);
    roomToUpdate.setRoomNumber(aRoomNumber);
    roomToUpdate.setFloorNumber(aFloorNumber);

    roomRepository.save(roomToUpdate);
  }

  @Transactional
  public Room getRoom(Integer aFloorNumber, Integer aRoomNumber) {
    Optional<Room> wrappedRoom = roomRepository.findRoomByFloorNumberAndRoomNumber(aFloorNumber, aRoomNumber);
    return wrappedRoom.orElseThrow(() -> new IllegalArgumentException("No room with this floor and room number was found"));
  }

  @Transactional
  public List<Room> getAllRooms(Integer floorNumber, Integer roomNumber) {
    // room number specified
    if(Objects.equals(floorNumber, null) && !Objects.equals(roomNumber, null)) {
      Optional<List<Room>> wrappedFilteredByRoomNumber = roomRepository.findRoomByRoomNumber(roomNumber);
      if (wrappedFilteredByRoomNumber.isEmpty()) {
        throw new IllegalArgumentException("There is no room with this room number");
      }
      else {
        return toList(wrappedFilteredByRoomNumber.orElse(null));
      }
    }

    // floor number specified
    else if (!Objects.equals(floorNumber, null) && Objects.equals(roomNumber, null)) {
      Optional<List<Room>> wrappedFilteredByRoomNumber = roomRepository.findRoomByFloorNumber(floorNumber);
      if (wrappedFilteredByRoomNumber.isEmpty()) {
        throw new IllegalArgumentException("There is no room with this floor number");
      }
      else {
        return toList(wrappedFilteredByRoomNumber.orElse(null));
      }
    }

    // floor and room number specified
    else if (!Objects.equals(floorNumber, null) && !Objects.equals(roomNumber, null)) {
      Optional<Room> wrappedSpecificRoom = roomRepository.findRoomByFloorNumberAndRoomNumber(floorNumber, roomNumber);
      Room specificRoom = wrappedSpecificRoom.orElseThrow(() -> new IllegalArgumentException("There is no room with this floor number and room number"));

      List<Room> filteredList = toList(roomRepository.findRoomByFloorNumber(floorNumber).orElse(null));
      if (!Objects.equals(filteredList, null)) {
        filteredList.removeIf(room -> !Objects.equals(room.getRoomNumber(), specificRoom.getRoomNumber()) || !Objects.equals(room.getFloorNumber(), specificRoom.getFloorNumber()));
      }

      return filteredList;
    }

    // no filters specified
    else {
      return toList(roomRepository.findAll());
    }
  }

  @Transactional
  public void deleteRoom(Integer aFloorNumber, Integer aRoomNumber) {
    Optional<Room> wrappedRoomToDelete = roomRepository.findRoomByFloorNumberAndRoomNumber(aFloorNumber, aRoomNumber);
    roomRepository.delete(wrappedRoomToDelete.orElseThrow(() -> new IllegalArgumentException("There is no room with this floor and room number")));
  }

  private <T> List<T> toList(Iterable<T> iterable){
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
      resultList.add(t);
    }
    return resultList;
  }
}
