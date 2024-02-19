package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/



// line 62 "SportsCenter.ump"
public class Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Attributes
  private String name;
  private Integer floorNumber;
  private Integer roomNumber;
  private Integer capacity;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Room(String aName, Integer aFloorNumber, Integer aRoomNumber, Integer aCapacity)
  {
    name = aName;
    floorNumber = aFloorNumber;
    roomNumber = aRoomNumber;
    capacity = aCapacity;
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
    floorNumber = aFloorNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setRoomNumber(Integer aRoomNumber)
  {
    boolean wasSet = false;
    roomNumber = aRoomNumber;
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
    return floorNumber;
  }

  public Integer getRoomNumber()
  {
    return roomNumber;
  }

  public Integer getCapacity()
  {
    return capacity;
  }

}