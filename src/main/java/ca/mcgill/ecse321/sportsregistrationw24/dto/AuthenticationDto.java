package ca.mcgill.ecse321.sportsregistrationw24.dto;

public class AuthenticationDto {
  private String token;
  private String role;

  public AuthenticationDto() {};

  public AuthenticationDto(String token, String role) {
    this.token = token;
    this.role = role;
  }

  public String getToken() { return this.token; }
  public String getRole() { return this.role; }
}
