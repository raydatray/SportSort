package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

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

  public Room(String name, Integer floorNumber, Integer roomNumber, Integer capacity) {
    this.name = name;
    this.floorNumber = floorNumber;
    this.roomNumber = roomNumber;
    this.capacity = capacity;
  }

  public Room() {}

  public void setName (String aName) { this.name = aName; }

  public void setFloorNumber (Integer aFloorNumber) { this.floorNumber = aFloorNumber; }

  public void setRoomNumber (Integer aRoomNumber) { this.roomNumber = aRoomNumber; }

  public void setCapacity (Integer aCapacity) { this.capacity = aCapacity; }

  public String getName() { return this.name; }

  public Integer getId() { return this.id; }

  public Integer getFloorNumber() { return this.floorNumber; }

  public Integer getRoomNumber() { return this.roomNumber; }

  public Integer getCapacity() { return this.capacity; }

}