package ca.mcgill.ecse321.sportsregistrationw24.validation;

//TODO: DO we need to try hard so much

public class CustomerAccountCreationPasswordValidation {
    public static void validatePassword(String password, String passwordConfirmation) {
        if (password.equals(passwordConfirmation)) {
            throw new IllegalArgumentException("Passwords do not match!");
        }
    }
}
