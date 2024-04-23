package ca.mcgill.ecse321.sportsregistrationw24.dto.Authentication;

public class AuthenticationDTO {
  private String token;
  private String role;
  private String email;
  private String name;

  public AuthenticationDTO() {};
  public AuthenticationDTO(String token, String role, String email, String name) {
    this.token = token;
    this.role = role;
    this.email = email;
    this.name = name;
  }
  public String getToken() { return this.token; }
  public String getRole() { return this.role; }
  public String getEmail() { return this.email; }
  public String getName() { return this.name; }
}
