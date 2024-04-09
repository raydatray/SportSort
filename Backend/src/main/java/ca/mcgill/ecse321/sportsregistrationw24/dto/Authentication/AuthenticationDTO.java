package ca.mcgill.ecse321.sportsregistrationw24.dto.Authentication;

public class AuthenticationDTO {
  private String token;
  private String role;

  public AuthenticationDTO() {};
  public AuthenticationDTO(String token, String role) {
    this.token = token;
    this.role = role;
  }
  public String getToken() { return this.token; }
  public String getRole() { return this.role; }
}
