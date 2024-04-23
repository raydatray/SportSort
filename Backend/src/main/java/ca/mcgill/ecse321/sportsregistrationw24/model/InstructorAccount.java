package ca.mcgill.ecse321.sportsregistrationw24.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("INSTRUCTOR")
public class InstructorAccount extends StaffAccount {
  public InstructorAccount() {
    super();
  }
  public InstructorAccount(String aName, String aEmail, String aPassword)
  {
    super(aName, aEmail,  aPassword);
  }
}