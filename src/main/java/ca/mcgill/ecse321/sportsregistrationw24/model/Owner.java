package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/



// line 31 "SportsCenter.ump"
public class Owner extends Staff
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Associations
  private SportCenter sportCenter;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(int aId, String aEmail, String aPassword, SportCenter aSportCenter)
  {
    super(aId, aEmail, aPassword);
    if (aSportCenter == null || aSportCenter.getOwner() != null)
    {
      throw new RuntimeException("Unable to create Owner due to aSportCenter. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    sportCenter = aSportCenter;
  }

  public Owner(int aId, String aEmail, String aPassword, String aNameForSportCenter, int aOpeningHourForSportCenter, int aClosingHourForSportCenter)
  {
    super(aId, aEmail, aPassword);
    sportCenter = new SportCenter(aNameForSportCenter, aOpeningHourForSportCenter, aClosingHourForSportCenter, this);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public SportCenter getSportCenter()
  {
    return sportCenter;
  }

  public void delete()
  {
    SportCenter existingSportCenter = sportCenter;
    sportCenter = null;
    if (existingSportCenter != null)
    {
      existingSportCenter.delete();
    }
    super.delete();
  }

}