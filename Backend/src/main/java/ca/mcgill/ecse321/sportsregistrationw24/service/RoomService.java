package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseOfferingRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.RoomRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.getUserFromToken;

@Service
public class RoomService {
  @Autowired
  private RoomRepository roomRepository;
  @Autowired
  private CourseOfferingRepository courseOfferingRepository;
  @Autowired
  private UserAccountRepository userAccountRepository;

  @Transactional
  public void createRoom(String userToken, String aName, Integer aFloorNumber, Integer aRoomNumber, Integer aCapacity) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (!user.getUserType().equals("OWNER")){
      throw new IllegalArgumentException("Only owners can create rooms!");
    }

    if (aName.trim().isEmpty()) {
      throw new IllegalArgumentException("Cannot create a room with no name specified");
    }

    if (aCapacity <= 0) {
      throw new IllegalArgumentException("A room with this capacity cannot exist");
    }

    if (aRoomNumber < 0) {
      throw new IllegalArgumentException("Room number cannot be negative");
    }

    Room duplicateRoom = roomRepository.findRoomByFloorNumberAndRoomNumber(aFloorNumber, aRoomNumber).orElse(null);

    if (duplicateRoom != null) {
      throw new IllegalArgumentException("A room with this floor and room number already exists");
    }

    Room room = new Room();
    room.setName(aName);
    room.setFloorNumber(aFloorNumber);
    room.setRoomNumber(aRoomNumber);
    room.setCapacity(aCapacity);

    roomRepository.save(room);
  }

  @Transactional
  public void updateRoom(String userToken, Integer aId, String newName, Integer newCapacity) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (!user.getUserType().equals("OWNER")){
      throw new IllegalArgumentException("Only owners can update rooms!");
    }

    Room foundRoom = roomRepository.findById(aId).orElse(null);

    if (foundRoom == null) {
      throw new IllegalArgumentException("Room does not exist!");
    }

    if (newName.trim().isEmpty()) {
      throw new IllegalArgumentException("Cannot create a room with no name specified");
    }

    if (newCapacity <= 0) {
      throw new IllegalArgumentException("A room with this capacity cannot exist");
    }

    foundRoom.setName(newName);
    foundRoom.setCapacity(newCapacity);

    roomRepository.save(foundRoom);
  }

  //Do we even need this?
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
  public List<Room> getAllRooms(String userToken, Integer floorNumber, Integer capacityLow, Integer capacityHigh) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (user.getUserType().equals("CUSTOMER")){
      throw new IllegalArgumentException("Only instructors and owners can view rooms!");
    }
    List<Room> foundRooms = roomRepository.findRoomsByFilters(floorNumber, capacityLow, capacityHigh).orElse(null);
    if (foundRooms == null) {
      throw new IllegalArgumentException("No rooms found with these filters");
    }
    return foundRooms;
  }

  @Transactional
  public void deleteRoom(String userToken, Integer aId) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (!user.getUserType().equals("OWNER")){
      throw new IllegalArgumentException("Only owners can delete rooms!");
    }

    Room room = roomRepository.findById(aId).orElse(null);

    if (room == null) {
      throw new IllegalArgumentException("Room does not exist!");
    }

    roomRepository.delete(room);
  }


}
