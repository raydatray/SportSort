package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.CustomerAccountDto;
import ca.mcgill.ecse321.sportsregistrationw24.dto.RoomDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import ca.mcgill.ecse321.sportsregistrationw24.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
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
  public RoomDto createRoom(@RequestBody RoomDto roomDto) {
    String name = roomDto.getName();
    Integer floorNumber = roomDto.getFloorNumber();
    Integer roomNumber = roomDto.getRoomNumber();
    Integer capacity = roomDto.getCapacity();

    Room room = service.createRoom(name, floorNumber, roomNumber, capacity);

    return convertToDto(room);
  }

  @PutMapping(value = {
    "/room/update",
    "/room/update/"
  })
  public void updateRoom(@RequestBody RoomDto roomDto, @RequestParam Integer aFloorNumber, @RequestParam Integer aRoomNumber) {
    String newName = roomDto.getName();
    Integer newCapacity = roomDto.getCapacity();

    service.updateRoom(newName, aFloorNumber, aRoomNumber, newCapacity);
  }

  @GetMapping(value = {
    "/room/get",
    "/room/get/"
  })
  public RoomDto getRoom(@RequestParam Integer aFloorNumber, @RequestParam Integer aRoomNumber) {
    return convertToDto(service.getRoom(aFloorNumber, aRoomNumber));
  }

  @GetMapping(value = {
    "/room/getAll",
    "/room/getAll/"
  })
  public List<RoomDto> getRooms(@RequestParam Integer aFloorNumber, @RequestParam Integer aRoomNumber) {
    List<RoomDto> filteredRoomDtos = new ArrayList<>();
    for (Room room: service.getAllRooms(aFloorNumber, aRoomNumber)) {
      filteredRoomDtos.add(convertToDto(room));
    }
    return filteredRoomDtos;
  }

  @DeleteMapping(value = {
    "/room/delete",
    "/room/delete/"
  })
  public void deleteRoom(@RequestParam Integer aFloorNumber, @RequestParam Integer aRoomNumber) { service.deleteRoom(aFloorNumber, aRoomNumber); }




  private RoomDto convertToDto(Room room) {
    if (room == null) {
      throw new IllegalArgumentException("There is no such room");
    }
    return new RoomDto(room.getName(), room.getFloorNumber(),
            room.getRoomNumber(), room.getCapacity());
  }
}
