package ca.mcgill.ecse321.sportsregistrationw24.dto.UserAccounts;

import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;

public class UserAccountDTO {
  public String type;
  private String email;
  private String name;
  private String token;

  public UserAccountDTO() {
  }

  public UserAccountDTO(String type, String email, String name, String token) {
    this.type = type;
    this.email = email;
    this.name = name;
    this.token = token;
  }

  public UserAccountDTO(UserAccount userAccount) {
    this(userAccount.getUserType(), userAccount.getEmail(), userAccount.getName(), userAccount.getToken());
  }

  public String getToken() {
    return this.token;
  }

  public String getType() {
    return this.type;
  }

  public String getEmail() {
    return this.email;
  }

  public String getName() {
    return this.name;
  }
}
