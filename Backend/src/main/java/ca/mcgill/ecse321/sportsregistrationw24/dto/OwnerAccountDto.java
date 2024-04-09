package ca.mcgill.ecse321.sportsregistrationw24.dto;
@Deprecated
public class OwnerAccountDto {
  private String email;
  private String password;
  private String name;

  public OwnerAccountDto() {
  }

  public OwnerAccountDto(String email, String password, String name) {
    this.email = email;
    this.password = password;
    this.name = name;
  }

  public String getEmail() {
    return this.email;
  }
  public String getPassword() {
    return this.password;
  }
  public String getName() { return this.name; }
}
