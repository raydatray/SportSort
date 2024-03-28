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
    if (aName == null) {
      throw new IllegalArgumentException("Cannot create a room with no name specified");
    }
    if (aFloorNumber == null || aRoomNumber == null) {
      if (aFloorNumber == null && aRoomNumber == null) {
        throw new IllegalArgumentException("Cannot create a room with no floor and no room number specified");
      } else if (aFloorNumber == null && aRoomNumber != null) {
        throw new IllegalArgumentException("Cannot create a room with no floor number specified");
      } else {
        throw new IllegalArgumentException("Cannot create a room with no room number specified");
      }
    }
    else if (wrappedTestRoom.isPresent()) {
      throw new IllegalArgumentException("A room with this floor or room number already exists");
    }

    if (capacity == null || capacity <= 0) {
      throw new IllegalArgumentException("A room with this capacity cannot exist");
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
    if (newName == null) {
      throw new IllegalArgumentException("Cannot update a room with no name specified");
    }
    if (aFloorNumber == null || aRoomNumber == null) {
      if (aFloorNumber == null && aRoomNumber == null) {
        throw new IllegalArgumentException("Cannot update a room with no floor and no room number specified");
      } else if (aFloorNumber == null && aRoomNumber != null) {
        throw new IllegalArgumentException("Cannot update a room with no floor number specified");
      } else {
        throw new IllegalArgumentException("Cannot update a room with no room number specified");
      }
    }

    if (newCapacity == null || newCapacity <= 0) {
      throw new IllegalArgumentException("A room with this capacity cannot exist");
    }

    Room roomToUpdate = wrappedRoomToUpdate.orElseThrow(() -> new IllegalArgumentException("No room with this floor and room number was found"));

    roomToUpdate.setCapacity(newCapacity);
    roomToUpdate.setName(newName);
    roomToUpdate.setRoomNumber(aRoomNumber);
    roomToUpdate.setFloorNumber(aFloorNumber);

    roomRepository.save(roomToUpdate);
  }

  @Transactional
  public Room getRoom(Integer aFloorNumber, Integer aRoomNumber) {
    if (aFloorNumber.equals(-1) || aRoomNumber.equals(-1)) {
      if (aFloorNumber.equals(-1) && aRoomNumber.equals(-1)) {
        throw new IllegalArgumentException("Cannot get a specific room with no floor and no room number specified");
      } else if (aFloorNumber.equals(-1) && aRoomNumber > -1) {
        throw new IllegalArgumentException("Cannot get a specific room with no floor number specified");
      } else {
        throw new IllegalArgumentException("Cannot get a specific room with no room number specified");
      }
    }
    Optional<Room> wrappedRoom = roomRepository.findRoomByFloorNumberAndRoomNumber(aFloorNumber, aRoomNumber);
    if(wrappedRoom.isEmpty()) {
      throw new IllegalArgumentException("No room with this floor and room number was found");
    }
    return wrappedRoom.orElseThrow(() -> new IllegalArgumentException("No room with this floor and room number was found"));
  }

  @Transactional
  public List<Room> getAllRooms(Integer floorNumber, Integer roomNumber) {
    // room number specified
    if(floorNumber.equals(-1) && roomNumber > -1) {
      List<Room> filteredByRoomNumber = roomRepository.findRoomByRoomNumber(roomNumber).orElseThrow(() -> new IllegalArgumentException("There are no rooms with this room number"));
      if (filteredByRoomNumber.isEmpty()) {
        throw new IllegalArgumentException("There are no rooms with this room number");
      }
      return filteredByRoomNumber;
    }

    // floor number specified
    else if (floorNumber > -1 && roomNumber.equals(-1)) {
      List<Room> filteredByFloorNumber = roomRepository.findRoomByFloorNumber(floorNumber).orElseThrow(() -> new IllegalArgumentException("There are no rooms with this floor number"));
      if (filteredByFloorNumber.isEmpty()) {
        throw new IllegalArgumentException("There are no rooms with this floor number");
      }
      return filteredByFloorNumber;
    }

    // floor and room number specified
    else if (floorNumber > -1 && roomNumber > -1) {
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
