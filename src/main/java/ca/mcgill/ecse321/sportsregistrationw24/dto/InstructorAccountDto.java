package ca.mcgill.ecse321.sportsregistrationw24.dto;

public class InstructorAccountDto {
  private String email;
  private String password;
  private String name;

  public InstructorAccountDto() {
  }

  public InstructorAccountDto(String email, String password, String name) {
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

  public String getName() {
    return this.name;
  }
}
