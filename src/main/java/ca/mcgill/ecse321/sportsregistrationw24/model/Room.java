package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import ca.mcgill.ecse321.sportsregistrationw24.model.keys.RoomId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
// line 62 "SportsCenter.ump"
public class Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Attributes
  private String name;
  @EmbeddedId
  private RoomId id;
  private Integer capacity;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Room(String name, Integer floorNumber, Integer roomNumber, Integer capacity) {
    this.id = new RoomId(floorNumber, roomNumber);
    this.name = name;
    this.capacity = capacity;
  }

  public Room() {

  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setFloorNumber(Integer aFloorNumber)
  {
    boolean wasSet = false;
    id.setFloorNumber(aFloorNumber);
    wasSet = true;
    return wasSet;
  }

  public boolean setRoomNumber(Integer aRoomNumber)
  {
    boolean wasSet = false;
    id.setRoomNumber(aRoomNumber);
    wasSet = true;
    return wasSet;
  }

  public boolean setCapacity(Integer aCapacity)
  {
    boolean wasSet = false;
    capacity = aCapacity;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public Integer getFloorNumber()
  {
    return id.getFloorNumber();
  }

  public Integer getRoomNumber()
  {
    return id.getRoomNumber();
  }

  public Integer getCapacity()
  {
    return capacity;
  }

}