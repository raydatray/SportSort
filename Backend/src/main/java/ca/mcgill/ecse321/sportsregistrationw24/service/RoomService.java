package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseOfferingRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.RoomRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.getUserFromToken;
import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.iterableToArrayList;

@Service
public class RoomService {
  private final RoomRepository roomRepository;
  private final UserAccountRepository userAccountRepository;

  @Autowired
  public RoomService(RoomRepository roomRepository, UserAccountRepository userAccountRepository) {
    this.roomRepository = roomRepository;
    this.userAccountRepository = userAccountRepository;
  }
  @Transactional
  public void createRoom(String userToken, String aName, Integer aFloorNumber, Integer aRoomNumber, Integer aCapacity) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);
    Validate.isTrue(user.getUserType().equals("OWNER"), "Only owners can create rooms");

    Validate.notBlank(aName, "Name cannot be empty");
    Validate.isTrue(aCapacity > 0, "Capacity must be greater than 0");
    Validate.isTrue(aRoomNumber > 0, "Room number must be greater than 0");


    Room duplicateRoom = roomRepository.findRoomByFloorNumberAndRoomNumber(aFloorNumber, aRoomNumber).orElse(null);
    Validate.notNull(duplicateRoom, "Room with this floor and room number already exists");

    Room room = new Room();
    room.setName(aName);
    room.setFloorNumber(aFloorNumber);
    room.setRoomNumber(aRoomNumber);
    room.setCapacity(aCapacity);

    roomRepository.save(room);
  }

  @Transactional
  public void updateRoom(String userToken, Integer aId, String aNewName, Integer aNewCapacity) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);
    Validate.isTrue(user.getUserType().equals("OWNER"), "Only owners can update rooms");

    Room foundRoom = roomRepository.findById(aId).orElse(null);
    Validate.notNull(foundRoom, "Room does not exist");

    if (aNewName != null){
      Validate.notBlank(aNewName, "Name cannot be empty");
      foundRoom.setName(aNewName);
    }

    if (aNewCapacity != null) {
      Validate.isTrue(aNewCapacity > 0, "Capacity must be greater than 0");
      foundRoom.setCapacity(aNewCapacity);
    }

    roomRepository.save(foundRoom);
  }

  @Transactional
  public List<Room> getAllRooms(Integer floorNumber, Integer capacityLow, Integer capacityHigh) {
    List<Room> foundRooms = roomRepository.findRoomsByFilters(floorNumber, capacityLow, capacityHigh).orElse(null);
    if (foundRooms == null) {
      throw new IllegalArgumentException("No rooms found with these filters");
    }
    return foundRooms;
  }

  @Transactional
  public List<Room> getRoomsForFilters() {
    List<Room> foundRooms = iterableToArrayList(roomRepository.findAll());
    if (foundRooms.isEmpty()) {
      throw new IllegalArgumentException("No rooms found with these filters");
    }
    return foundRooms;
  }

  @Transactional
  public void deleteRoom(String userToken, Integer aId) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);
    Validate.isTrue(user.getUserType().equals("OWNER"), "Only owners can delete rooms");

    Room room = roomRepository.findById(aId).orElse(null);
    Validate.notNull(room, "Room does not exist");

    roomRepository.delete(room);
  }
}
