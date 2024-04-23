package ca.mcgill.ecse321.sportsregistrationw24.dto.UserAccounts;

import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;

public class InstructorDTO {
  private Integer id;
  private String name;

  public InstructorDTO() {
  }

  public InstructorDTO(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public InstructorDTO(UserAccount userAccount) {
    this(userAccount.getId(), userAccount.getName());
  }

  public Integer getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }
}