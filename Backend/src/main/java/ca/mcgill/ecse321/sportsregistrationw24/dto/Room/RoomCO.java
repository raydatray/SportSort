package ca.mcgill.ecse321.sportsregistrationw24.dto.Room;

public class RoomCO {
  private String name;
  private Integer floorNumber;
  private Integer roomNumber;
  private Integer capacity;

  public RoomCO() {
  }

  public RoomCO(String name, Integer floorNumber, Integer roomNumber, Integer capacity) {
    this.name = name;
    this.floorNumber = floorNumber;
    this.roomNumber = roomNumber;
    this.capacity = capacity;
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
