package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.util.*;
import java.sql.Date;
import java.sql.Time;

// line 1 "SportsCenter.ump"
public class SportCenter
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SportCenter Attributes
  private String name;
  private Time openingHour;
  private Time closingHour;

  //SportCenter Associations
  private List<SportSession> sportSessions;
  private List<Customer> customers;
  private List<Instructor> instructors;
  private Owner owner;
  private List<SportClass> sportClasses;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SportCenter(String aName, Time aOpeningHour, Time aClosingHour, Owner aOwner)
  {
    name = aName;
    openingHour = aOpeningHour;
    closingHour = aClosingHour;
    sportSessions = new ArrayList<SportSession>();
    customers = new ArrayList<Customer>();
    instructors = new ArrayList<Instructor>();
    if (aOwner == null || aOwner.getSportCenter() != null)
    {
      throw new RuntimeException("Unable to create SportCenter due to aOwner. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    owner = aOwner;
    sportClasses = new ArrayList<SportClass>();
  }

  public SportCenter(String aName, Time aOpeningHour, Time aClosingHour, String aEmailForOwner, String aPasswordForOwner)
  {
    name = aName;
    openingHour = aOpeningHour;
    closingHour = aClosingHour;
    sportSessions = new ArrayList<SportSession>();
    customers = new ArrayList<Customer>();
    instructors = new ArrayList<Instructor>();
    owner = new Owner(aEmailForOwner, aPasswordForOwner, this);
    sportClasses = new ArrayList<SportClass>();
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

  public boolean setOpeningHour(Time aOpeningHour)
  {
    boolean wasSet = false;
    openingHour = aOpeningHour;
    wasSet = true;
    return wasSet;
  }

  public boolean setClosingHour(Time aClosingHour)
  {
    boolean wasSet = false;
    closingHour = aClosingHour;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public Time getOpeningHour()
  {
    return openingHour;
  }

  public Time getClosingHour()
  {
    return closingHour;
  }
  /* Code from template association_GetMany */
  public SportSession getSportSession(Integer index)
  {
    SportSession aSportSession = sportSessions.get(index);
    return aSportSession;
  }

  public List<SportSession> getSportSessions()
  {
    List<SportSession> newSportSessions = Collections.unmodifiableList(sportSessions);
    return newSportSessions;
  }

  public Integer numberOfSportSessions()
  {
    Integer number = sportSessions.size();
    return number;
  }

  public boolean hasSportSessions()
  {
    boolean has = sportSessions.size() > 0;
    return has;
  }

  public Integer indexOfSportSession(SportSession aSportSession)
  {
    Integer index = sportSessions.indexOf(aSportSession);
    return index;
  }
  /* Code from template association_GetMany */
  public Customer getCustomer(Integer index)
  {
    Customer aCustomer = customers.get(index);
    return aCustomer;
  }

  public List<Customer> getCustomers()
  {
    List<Customer> newCustomers = Collections.unmodifiableList(customers);
    return newCustomers;
  }

  public Integer numberOfCustomers()
  {
    Integer number = customers.size();
    return number;
  }

  public boolean hasCustomers()
  {
    boolean has = customers.size() > 0;
    return has;
  }

  public Integer indexOfCustomer(Customer aCustomer)
  {
    Integer index = customers.indexOf(aCustomer);
    return index;
  }
  /* Code from template association_GetMany */
  public Instructor getInstructor(Integer index)
  {
    Instructor aInstructor = instructors.get(index);
    return aInstructor;
  }

  public List<Instructor> getInstructors()
  {
    List<Instructor> newInstructors = Collections.unmodifiableList(instructors);
    return newInstructors;
  }

  public Integer numberOfInstructors()
  {
    Integer number = instructors.size();
    return number;
  }

  public boolean hasInstructors()
  {
    boolean has = instructors.size() > 0;
    return has;
  }

  public Integer indexOfInstructor(Instructor aInstructor)
  {
    Integer index = instructors.indexOf(aInstructor);
    return index;
  }
  /* Code from template association_GetOne */
  public Owner getOwner()
  {
    return owner;
  }
  /* Code from template association_GetMany */
  public SportClass getSportClass(Integer index)
  {
    SportClass aSportClass = sportClasses.get(index);
    return aSportClass;
  }

  public List<SportClass> getSportClasses()
  {
    List<SportClass> newSportClasses = Collections.unmodifiableList(sportClasses);
    return newSportClasses;
  }

  public Integer numberOfSportClasses()
  {
    Integer number = sportClasses.size();
    return number;
  }

  public boolean hasSportClasses()
  {
    boolean has = sportClasses.size() > 0;
    return has;
  }

  public Integer indexOfSportClass(SportClass aSportClass)
  {
    Integer index = sportClasses.indexOf(aSportClass);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static Integer minimumNumberOfSportSessions()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public SportSession addSportSession(SportSession.SessionType aSessionType, Date aDate, Time aStartTime, Time aEndTime, Integer aFloorNumber, Integer aRoomNumber, SportClass aSportClass)
  {
    return new SportSession(aSessionType, aDate, aStartTime, aEndTime, aFloorNumber, aRoomNumber, this, aSportClass);
  }

  public boolean addSportSession(SportSession aSportSession)
  {
    boolean wasAdded = false;
    if (sportSessions.contains(aSportSession)) { return false; }
    SportCenter existingSportCenter = aSportSession.getSportCenter();
    boolean isNewSportCenter = existingSportCenter != null && !this.equals(existingSportCenter);
    if (isNewSportCenter)
    {
      aSportSession.setSportCenter(this);
    }
    else
    {
      sportSessions.add(aSportSession);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSportSession(SportSession aSportSession)
  {
    boolean wasRemoved = false;
    //Unable to remove aSportSession, as it must always have a sportCenter
    if (!this.equals(aSportSession.getSportCenter()))
    {
      sportSessions.remove(aSportSession);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSportSessionAt(SportSession aSportSession, Integer index)
  {
    boolean wasAdded = false;
    if(addSportSession(aSportSession))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSportSessions()) { index = numberOfSportSessions() - 1; }
      sportSessions.remove(aSportSession);
      sportSessions.add(index, aSportSession);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSportSessionAt(SportSession aSportSession, Integer index)
  {
    boolean wasAdded = false;
    if(sportSessions.contains(aSportSession))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSportSessions()) { index = numberOfSportSessions() - 1; }
      sportSessions.remove(aSportSession);
      sportSessions.add(index, aSportSession);
      wasAdded = true;
    }
    else
    {
      wasAdded = addSportSessionAt(aSportSession, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static Integer minimumNumberOfCustomers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Customer addCustomer(String aEmail, String aPassword)
  {
    return new Customer(aEmail, aPassword, this);
  }

  public boolean addCustomer(Customer aCustomer)
  {
    boolean wasAdded = false;
    if (customers.contains(aCustomer)) { return false; }
    SportCenter existingSportCenter = aCustomer.getSportCenter();
    boolean isNewSportCenter = existingSportCenter != null && !this.equals(existingSportCenter);
    if (isNewSportCenter)
    {
      aCustomer.setSportCenter(this);
    }
    else
    {
      customers.add(aCustomer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCustomer(Customer aCustomer)
  {
    boolean wasRemoved = false;
    //Unable to remove aCustomer, as it must always have a sportCenter
    if (!this.equals(aCustomer.getSportCenter()))
    {
      customers.remove(aCustomer);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCustomerAt(Customer aCustomer, Integer index)
  {
    boolean wasAdded = false;
    if(addCustomer(aCustomer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomers()) { index = numberOfCustomers() - 1; }
      customers.remove(aCustomer);
      customers.add(index, aCustomer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCustomerAt(Customer aCustomer, Integer index)
  {
    boolean wasAdded = false;
    if(customers.contains(aCustomer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomers()) { index = numberOfCustomers() - 1; }
      customers.remove(aCustomer);
      customers.add(index, aCustomer);
      wasAdded = true;
    }
    else
    {
      wasAdded = addCustomerAt(aCustomer, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static Integer minimumNumberOfInstructors()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Instructor addInstructor(String aEmail, String aPassword)
  {
    return new Instructor(aEmail, aPassword, this);
  }

  public boolean addInstructor(Instructor aInstructor)
  {
    boolean wasAdded = false;
    if (instructors.contains(aInstructor)) { return false; }
    SportCenter existingSportCenter = aInstructor.getSportCenter();
    boolean isNewSportCenter = existingSportCenter != null && !this.equals(existingSportCenter);
    if (isNewSportCenter)
    {
      aInstructor.setSportCenter(this);
    }
    else
    {
      instructors.add(aInstructor);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeInstructor(Instructor aInstructor)
  {
    boolean wasRemoved = false;
    //Unable to remove aInstructor, as it must always have a sportCenter
    if (!this.equals(aInstructor.getSportCenter()))
    {
      instructors.remove(aInstructor);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addInstructorAt(Instructor aInstructor, Integer index)
  {
    boolean wasAdded = false;
    if(addInstructor(aInstructor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfInstructors()) { index = numberOfInstructors() - 1; }
      instructors.remove(aInstructor);
      instructors.add(index, aInstructor);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveInstructorAt(Instructor aInstructor, Integer index)
  {
    boolean wasAdded = false;
    if(instructors.contains(aInstructor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfInstructors()) { index = numberOfInstructors() - 1; }
      instructors.remove(aInstructor);
      instructors.add(index, aInstructor);
      wasAdded = true;
    }
    else
    {
      wasAdded = addInstructorAt(aInstructor, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static Integer minimumNumberOfSportClasses()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public SportClass addSportClass(Integer aId, String aClassName, boolean aIsApproved)
  {
    return new SportClass(aId, aClassName, aIsApproved, this);
  }

  public boolean addSportClass(SportClass aSportClass)
  {
    boolean wasAdded = false;
    if (sportClasses.contains(aSportClass)) { return false; }
    SportCenter existingSportCenter = aSportClass.getSportCenter();
    boolean isNewSportCenter = existingSportCenter != null && !this.equals(existingSportCenter);
    if (isNewSportCenter)
    {
      aSportClass.setSportCenter(this);
    }
    else
    {
      sportClasses.add(aSportClass);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSportClass(SportClass aSportClass)
  {
    boolean wasRemoved = false;
    //Unable to remove aSportClass, as it must always have a sportCenter
    if (!this.equals(aSportClass.getSportCenter()))
    {
      sportClasses.remove(aSportClass);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSportClassAt(SportClass aSportClass, Integer index)
  {
    boolean wasAdded = false;
    if(addSportClass(aSportClass))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSportClasses()) { index = numberOfSportClasses() - 1; }
      sportClasses.remove(aSportClass);
      sportClasses.add(index, aSportClass);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSportClassAt(SportClass aSportClass, Integer index) {
    boolean wasAdded = false;
    if (sportClasses.contains(aSportClass)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfSportClasses()) {
        index = numberOfSportClasses() - 1;
      }
      sportClasses.remove(aSportClass);
      sportClasses.add(index, aSportClass);
      wasAdded = true;
    } else {
      wasAdded = addSportClassAt(aSportClass, index);
    }
    return wasAdded;
  }
}