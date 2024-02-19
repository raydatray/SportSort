package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import ca.mcgill.ecse321.sportsregistrationw24.model.keys.RoomId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
// line 62 "SportsCenter.ump"
public class Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Attributes
  @Id
  private Integer id;
  private String name;
  private Integer floorNumber;
  private Integer roomNumber;
  private Integer capacity;


  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Room(Integer aId, String name, Integer floorNumber, Integer roomNumber, Integer capacity) {
    this.id = aId;
    this.name = name;
    this.floorNumber = floorNumber;
    this.roomNumber = roomNumber;
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
    wasSet = true;
    return wasSet;
  }

  public boolean setRoomNumber(Integer aRoomNumber)
  {
    boolean wasSet = false;
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

  public Integer getId() { return this.id; }

  public Integer getFloorNumber()
  {
    return this.floorNumber;
  }

  public Integer getRoomNumber()
  {
    return this.roomNumber;
  }

  public Integer getCapacity()
  {
    return capacity;
  }

}