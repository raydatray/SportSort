package ca.mcgill.ecse321.sportsregistrationw24.dto;

public class InstructorAccountSafeDto {
  private String email;
  private String name;

  public InstructorAccountSafeDto() {
  }

  public InstructorAccountSafeDto(String email, String name) {
    this.email = email;
    this.name = name;
  }

  public String getEmail() {
    return this.email;
  }

  public String getName() { return this.name; }
}