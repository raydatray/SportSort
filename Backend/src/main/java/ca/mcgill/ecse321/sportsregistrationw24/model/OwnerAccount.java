package ca.mcgill.ecse321.sportsregistrationw24.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("OWNER")
public class OwnerAccount extends StaffAccount {
  public OwnerAccount() {
    super();
  }
  public OwnerAccount(String aName, String aEmail, String aPassword) {
    super(aName, aEmail, aPassword);
  }
}