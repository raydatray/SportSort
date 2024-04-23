package ca.mcgill.ecse321.sportsregistrationw24.dto.Authentication;

public class AuthenticationCO {
  private String email;
  private String password;

  public AuthenticationCO() {};
  public AuthenticationCO(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() { return this.email; }
  public String getPassword() { return this.password; }
}
