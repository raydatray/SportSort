package ca.mcgill.ecse321.sportsregistrationw24.dto.UserAccounts;

import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;

public class UserAccountDTO {
  public String type;
  private String email;
  private String name;

  public UserAccountDTO() {}

  public UserAccountDTO(String type, String email, String name) {
    this.type = type;
    this.email = email;
    this.name = name;
  }

  public UserAccountDTO(UserAccount userAccount) {
    this (userAccount.getUserType(), userAccount.getEmail(), userAccount.getName());
  }

  public String getType() { return this.type; }
  public String getEmail() {
    return this.email;
  }
  public String getName() { return this.name; }
}
