package ca.mcgill.ecse321.sportsregistrationw24.dto;

public class CustomerAccountSafeDto {
  private String email;
  private String name;

  public CustomerAccountSafeDto() {
  }

  public CustomerAccountSafeDto(String email, String name) {
    this.email = email;
    this.name = name;
  }

  public String getEmail() {
    return this.email;
  }

  public String getName() { return this.name; }
}