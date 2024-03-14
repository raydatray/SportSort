package ca.mcgill.ecse321.sportsregistrationw24.dto;

public class CustomerAccountDto {
    private String email;
    private String password;

    public CustomerAccountDto() {
    }

    public CustomerAccountDto(String email, String password) {
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
