package ca.mcgill.ecse321.sportsregistrationw24.dto;

public class UserAccountDto {
    private String email;
    private String password;
    public UserAccountDto() {
    }

    public UserAccountDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
