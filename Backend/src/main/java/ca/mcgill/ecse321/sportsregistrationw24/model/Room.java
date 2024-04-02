package ca.mcgill.ecse321.sportsregistrationw24.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;

@Entity
// line 62 "SportsCenter.ump"
public class Room {
  @Id
  @GeneratedValue
  private Integer id;
  private String name;
  private Integer floorNumber;
  private Integer roomNumber;
  private Integer capacity;

  public Room() {}
  public Room(String aName, Integer aFloorNumber, Integer aRoomNumber, Integer aCapacity) {
    this.name = aName;
    this.floorNumber = aFloorNumber;
    this.roomNumber = aRoomNumber;
    this.capacity = aCapacity;
  }

  public void setName (String aName) { this.name = aName; }
  public void setFloorNumber (Integer aFloorNumber) { this.floorNumber = aFloorNumber; }
  public void setRoomNumber (Integer aRoomNumber) { this.roomNumber = aRoomNumber; }
  public void setCapacity (Integer aCapacity) { this.capacity = aCapacity; }

  public Integer getId() { return this.id; }
  public String getName() { return this.name; }
  public Integer getFloorNumber() { return this.floorNumber; }
  public Integer getRoomNumber() { return this.roomNumber; }
  public Integer getCapacity() { return this.capacity; }
}