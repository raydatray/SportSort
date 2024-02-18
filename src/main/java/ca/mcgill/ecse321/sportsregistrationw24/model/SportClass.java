package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/



// line 14 "SportsCenter.ump"
public class SportClass
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SportClass Attributes
  private Integer id;
  private String className;
  private boolean isApproved;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SportClass(Integer aId, String aClassName, boolean aIsApproved)
  {
    id = aId;
    className = aClassName;
    isApproved = aIsApproved;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(Integer aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setClassName(String aClassName)
  {
    boolean wasSet = false;
    className = aClassName;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsApproved(boolean aIsApproved)
  {
    boolean wasSet = false;
    isApproved = aIsApproved;
    wasSet = true;
    return wasSet;
  }

  public Integer getId()
  {
    return id;
  }

  public String getClassName()
  {
    return className;
  }

  public boolean getIsApproved()
  {
    return isApproved;
  }

}