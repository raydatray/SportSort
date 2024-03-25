package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.RoomDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import ca.mcgill.ecse321.sportsregistrationw24.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class RoomRestController {
  @Autowired
  private RoomService service;

  @PostMapping(value = {
    "/room/create",
    "/room/create/"
  })
  public ResponseEntity<?> createRoom(@RequestBody RoomDto roomDto) {
    try {
      String name = roomDto.getName();
      Integer floorNumber = roomDto.getFloorNumber();
      Integer roomNumber = roomDto.getRoomNumber();
      Integer capacity = roomDto.getCapacity();

      Room room = service.createRoom(name, floorNumber, roomNumber, capacity);

      return ResponseEntity.ok().body(room);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping(value = {
    "/room/update",
    "/room/update/"
  })
  public ResponseEntity<?> updateRoom(@RequestBody RoomDto roomDto) {
    try {
      String newName = roomDto.getName();
      Integer aFloorNumber = roomDto.getFloorNumber();
      Integer aRoomNumber = roomDto.getRoomNumber();
      Integer newCapacity = roomDto.getCapacity();

      service.updateRoom(newName, aFloorNumber, aRoomNumber, newCapacity);

      return ResponseEntity.ok().body(roomDto);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {
    "/room/get",
    "/room/get/"
  })
  public ResponseEntity<?> getRoom(@RequestParam Integer aFloorNumber, @RequestParam Integer aRoomNumber) {
    try {
      RoomDto roomDto = convertToDto(service.getRoom(aFloorNumber, aRoomNumber));

      return ResponseEntity.ok().body(roomDto);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {
    "/room/getAll",
    "/room/getAll/"
  })
  public ResponseEntity<?> getRooms(@RequestParam Integer aFloorNumber, @RequestParam Integer aRoomNumber) {
    try {
      List<RoomDto> filteredRoomDtos = new ArrayList<>();
      for (Room room: service.getAllRooms(aFloorNumber, aRoomNumber)) {
        filteredRoomDtos.add(convertToDto(room));
      }

      return ResponseEntity.ok().body(filteredRoomDtos);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping(value = {
    "/room/delete",
    "/room/delete/"
  })
  public ResponseEntity<?> deleteRoom(@RequestBody RoomDto roomDto) {
    try {
      service.deleteRoom(roomDto.getFloorNumber(), roomDto.getRoomNumber());

      return ResponseEntity.ok().body("Room successfully deleted");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  private RoomDto convertToDto(Room room) {
    if (room == null) {
      throw new IllegalArgumentException("There is no such room");
    }
    return new RoomDto(room.getName(), room.getFloorNumber(),
            room.getRoomNumber(), room.getCapacity());
  }
}
