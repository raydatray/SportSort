package ca.mcgill.ecse321.sportsregistrationw24.dto;

public class RoomDto {

  private String name;
  private Integer floorNumber;
  private Integer roomNumber;
  private Integer capacity;

  public RoomDto() {}

  public RoomDto(String name, Integer floorNumber, Integer roomNumber, Integer capacity) {
    this.name = name;
    this.floorNumber = floorNumber;
    this.roomNumber = roomNumber;
    this.capacity = capacity;
  }

  public Integer getCapacity() { return capacity; }
  public Integer getFloorNumber() { return floorNumber; }
  public Integer getRoomNumber() { return roomNumber; }
  public String getName() { return name; }
}
