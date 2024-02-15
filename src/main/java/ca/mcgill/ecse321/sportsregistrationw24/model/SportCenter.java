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
  private int openingHour;
  private int closingHour;

  //SportCenter Associations
  private List<SpecificSession> specificSessions;
  private List<Customer> customers;
  private List<Instructor> instructors;
  private Owner owner;
  private List<Session> sessions;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SportCenter(String aName, int aOpeningHour, int aClosingHour, Owner aOwner)
  {
    name = aName;
    openingHour = aOpeningHour;
    closingHour = aClosingHour;
    specificSessions = new ArrayList<SpecificSession>();
    customers = new ArrayList<Customer>();
    instructors = new ArrayList<Instructor>();
    if (aOwner == null || aOwner.getSportCenter() != null)
    {
      throw new RuntimeException("Unable to create SportCenter due to aOwner. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    owner = aOwner;
    sessions = new ArrayList<Session>();
  }

  public SportCenter(String aName, int aOpeningHour, int aClosingHour, int aIdForOwner, String aEmailForOwner, String aPasswordForOwner)
  {
    name = aName;
    openingHour = aOpeningHour;
    closingHour = aClosingHour;
    specificSessions = new ArrayList<SpecificSession>();
    customers = new ArrayList<Customer>();
    instructors = new ArrayList<Instructor>();
    owner = new Owner(aIdForOwner, aEmailForOwner, aPasswordForOwner, this);
    sessions = new ArrayList<Session>();
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

  public boolean setOpeningHour(int aOpeningHour)
  {
    boolean wasSet = false;
    openingHour = aOpeningHour;
    wasSet = true;
    return wasSet;
  }

  public boolean setClosingHour(int aClosingHour)
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

  public int getOpeningHour()
  {
    return openingHour;
  }

  public int getClosingHour()
  {
    return closingHour;
  }
  /* Code from template association_GetMany */
  public SpecificSession getSpecificSession(int index)
  {
    SpecificSession aSpecificSession = specificSessions.get(index);
    return aSpecificSession;
  }

  public List<SpecificSession> getSpecificSessions()
  {
    List<SpecificSession> newSpecificSessions = Collections.unmodifiableList(specificSessions);
    return newSpecificSessions;
  }

  public int numberOfSpecificSessions()
  {
    int number = specificSessions.size();
    return number;
  }

  public boolean hasSpecificSessions()
  {
    boolean has = specificSessions.size() > 0;
    return has;
  }

  public int indexOfSpecificSession(SpecificSession aSpecificSession)
  {
    int index = specificSessions.indexOf(aSpecificSession);
    return index;
  }
  /* Code from template association_GetMany */
  public Customer getCustomer(int index)
  {
    Customer aCustomer = customers.get(index);
    return aCustomer;
  }

  public List<Customer> getCustomers()
  {
    List<Customer> newCustomers = Collections.unmodifiableList(customers);
    return newCustomers;
  }

  public int numberOfCustomers()
  {
    int number = customers.size();
    return number;
  }

  public boolean hasCustomers()
  {
    boolean has = customers.size() > 0;
    return has;
  }

  public int indexOfCustomer(Customer aCustomer)
  {
    int index = customers.indexOf(aCustomer);
    return index;
  }
  /* Code from template association_GetMany */
  public Instructor getInstructor(int index)
  {
    Instructor aInstructor = instructors.get(index);
    return aInstructor;
  }

  public List<Instructor> getInstructors()
  {
    List<Instructor> newInstructors = Collections.unmodifiableList(instructors);
    return newInstructors;
  }

  public int numberOfInstructors()
  {
    int number = instructors.size();
    return number;
  }

  public boolean hasInstructors()
  {
    boolean has = instructors.size() > 0;
    return has;
  }

  public int indexOfInstructor(Instructor aInstructor)
  {
    int index = instructors.indexOf(aInstructor);
    return index;
  }
  /* Code from template association_GetOne */
  public Owner getOwner()
  {
    return owner;
  }
  /* Code from template association_GetMany */
  public Session getSession(int index)
  {
    Session aSession = sessions.get(index);
    return aSession;
  }

  public List<Session> getSessions()
  {
    List<Session> newSessions = Collections.unmodifiableList(sessions);
    return newSessions;
  }

  public int numberOfSessions()
  {
    int number = sessions.size();
    return number;
  }

  public boolean hasSessions()
  {
    boolean has = sessions.size() > 0;
    return has;
  }

  public int indexOfSession(Session aSession)
  {
    int index = sessions.indexOf(aSession);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSpecificSessions()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public SpecificSession addSpecificSession(SpecificSession.SessionType aSessionType, Date aDate, Time aStartTime, Time aEndTime, int aFloorNumber, int aRoomNumber, Session aSession)
  {
    return new SpecificSession(aSessionType, aDate, aStartTime, aEndTime, aFloorNumber, aRoomNumber, this, aSession);
  }

  public boolean addSpecificSession(SpecificSession aSpecificSession)
  {
    boolean wasAdded = false;
    if (specificSessions.contains(aSpecificSession)) { return false; }
    SportCenter existingSportCenter = aSpecificSession.getSportCenter();
    boolean isNewSportCenter = existingSportCenter != null && !this.equals(existingSportCenter);
    if (isNewSportCenter)
    {
      aSpecificSession.setSportCenter(this);
    }
    else
    {
      specificSessions.add(aSpecificSession);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSpecificSession(SpecificSession aSpecificSession)
  {
    boolean wasRemoved = false;
    //Unable to remove aSpecificSession, as it must always have a sportCenter
    if (!this.equals(aSpecificSession.getSportCenter()))
    {
      specificSessions.remove(aSpecificSession);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSpecificSessionAt(SpecificSession aSpecificSession, int index)
  {  
    boolean wasAdded = false;
    if(addSpecificSession(aSpecificSession))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSpecificSessions()) { index = numberOfSpecificSessions() - 1; }
      specificSessions.remove(aSpecificSession);
      specificSessions.add(index, aSpecificSession);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSpecificSessionAt(SpecificSession aSpecificSession, int index)
  {
    boolean wasAdded = false;
    if(specificSessions.contains(aSpecificSession))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSpecificSessions()) { index = numberOfSpecificSessions() - 1; }
      specificSessions.remove(aSpecificSession);
      specificSessions.add(index, aSpecificSession);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSpecificSessionAt(aSpecificSession, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCustomers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Customer addCustomer(int aId, String aEmail, String aPassword)
  {
    return new Customer(aId, aEmail, aPassword, this);
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
  public boolean addCustomerAt(Customer aCustomer, int index)
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

  public boolean addOrMoveCustomerAt(Customer aCustomer, int index)
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
  public static int minimumNumberOfInstructors()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Instructor addInstructor(int aId, String aEmail, String aPassword)
  {
    return new Instructor(aId, aEmail, aPassword, this);
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
  public boolean addInstructorAt(Instructor aInstructor, int index)
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

  public boolean addOrMoveInstructorAt(Instructor aInstructor, int index)
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
  public static int minimumNumberOfSessions()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Session addSession(int aId, String aSessionName, boolean aIsApproved)
  {
    return new Session(aId, aSessionName, aIsApproved, this);
  }

  public boolean addSession(Session aSession)
  {
    boolean wasAdded = false;
    if (sessions.contains(aSession)) { return false; }
    SportCenter existingSportCenter = aSession.getSportCenter();
    boolean isNewSportCenter = existingSportCenter != null && !this.equals(existingSportCenter);
    if (isNewSportCenter)
    {
      aSession.setSportCenter(this);
    }
    else
    {
      sessions.add(aSession);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSession(Session aSession)
  {
    boolean wasRemoved = false;
    //Unable to remove aSession, as it must always have a sportCenter
    if (!this.equals(aSession.getSportCenter()))
    {
      sessions.remove(aSession);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSessionAt(Session aSession, int index)
  {  
    boolean wasAdded = false;
    if(addSession(aSession))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSessions()) { index = numberOfSessions() - 1; }
      sessions.remove(aSession);
      sessions.add(index, aSession);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSessionAt(Session aSession, int index)
  {
    boolean wasAdded = false;
    if(sessions.contains(aSession))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSessions()) { index = numberOfSessions() - 1; }
      sessions.remove(aSession);
      sessions.add(index, aSession);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSessionAt(aSession, index);
    }
    return wasAdded;
  }
}