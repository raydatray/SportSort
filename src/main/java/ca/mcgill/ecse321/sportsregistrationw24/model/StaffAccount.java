package ca.mcgill.ecse321.sportsregistrationw24.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("STAFF")
public abstract class StaffAccount extends UserAccount {
  public StaffAccount(String aName, String aEmail, String aPassword)
  {
    super(aName, aEmail, aPassword);
  }

  public StaffAccount() {
    super();
  }
}