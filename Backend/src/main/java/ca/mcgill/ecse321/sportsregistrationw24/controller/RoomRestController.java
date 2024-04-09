package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.Room.RoomCO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.Room.RoomDTO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.Room.RoomUpdateCO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.RoomDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import ca.mcgill.ecse321.sportsregistrationw24.service.RoomService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class RoomRestController {
  @Autowired
  private RoomService service;

  @PostMapping(value = {"/rooms/create"})
  public ResponseEntity<?> createRoom(@RequestHeader String userToken, @RequestBody RoomCO roomCO) {
    try {
      String name = roomCO.getName();
      Integer floorNumber = roomCO.getFloorNumber();
      Integer roomNumber = roomCO.getRoomNumber();
      Integer capacity = roomCO.getCapacity();

      service.createRoom(userToken, name, floorNumber, roomNumber, capacity);
      return ResponseEntity.status(HttpStatus.CREATED).body("Room created successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping(value = {"/rooms/update"})
  public ResponseEntity<?> updateRoom(@RequestHeader String userToken, @RequestBody RoomUpdateCO roomUpdateCO) {
    try {
      Integer id = roomUpdateCO.getId();
      String name = roomUpdateCO.getName();
      Integer capacity = roomUpdateCO.getCapacity();

      service.updateRoom(userToken, id, name, capacity);
      return ResponseEntity.ok().body("Room updated successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  /**
  public ResponseEntity<?> getRoom(@RequestParam Integer aFloorNumber, @RequestParam Integer aRoomNumber) {
    try {
      RoomDto roomDto = convertToDto(service.getRoom(aFloorNumber, aRoomNumber));

      return ResponseEntity.ok().body(roomDto);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  **/

  @GetMapping(value = {"/rooms/getAll"})
  public ResponseEntity<?> getRooms(@RequestHeader String userToken,
                                    @RequestParam(required = false) Integer floorNumber,
                                    @RequestParam(required = false) Integer lowCapacity,
                                    @RequestParam(required = false) Integer highCapacity) {
    try {
      List<Room> rooms = service.getAllRooms(userToken, floorNumber, lowCapacity, highCapacity);
      List<RoomDTO> roomDTOS = rooms.stream().map(RoomDTO::new).toList();

      return ResponseEntity.ok().body(roomDTOS);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping(value = {"/rooms/delete"})
  public ResponseEntity<?> deleteRoom(@RequestHeader String userToken, @RequestParam Integer id) {
    try {
      service.deleteRoom(userToken, id);
      return ResponseEntity.ok().body("Room successfully deleted");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
