package ca.mcgill.ecse321.sportsregistrationw24.dto.Room;

import ca.mcgill.ecse321.sportsregistrationw24.model.Room;

public class RoomDTO {
  private Integer id;
  private String name;
  private Integer floorNumber;
  private Integer roomNumber;
  private Integer capacity;

  public RoomDTO() {
  }

  public RoomDTO(Integer id, String name, Integer floorNumber, Integer roomNumber, Integer capacity) {
    this.id = id;
    this.name = name;
    this.floorNumber = floorNumber;
    this.roomNumber = roomNumber;
    this.capacity = capacity;
  }

  public RoomDTO(Room room) {
    this(room.getId(), room.getName(), room.getFloorNumber(), room.getRoomNumber(), room.getCapacity());
  }

  public Integer getId() {
    return this.id;
  }

  public Integer getCapacity() {
    return this.capacity;
  }

  public Integer getFloorNumber() {
    return this.floorNumber;
  }

  public Integer getRoomNumber() {
    return this.roomNumber;
  }

  public String getName() {
    return this.name;
  }
}
