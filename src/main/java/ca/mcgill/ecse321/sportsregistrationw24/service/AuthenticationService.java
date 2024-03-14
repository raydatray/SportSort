package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dto.AuthenticationDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import ca.mcgill.ecse321.sportsregistrationw24.utilities.TokenProvider;

import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
  @Autowired
  private UserAccountRepository userAccountRepository;

  @Autowired
  private TokenProvider tokenProvider;

  @Transactional
  public AuthenticationDto login(String email, String password) {
    UserAccount foundUser = userAccountRepository.findUserByEmail(email).orElse(null);

    if (foundUser == null) {
      throw new IllegalArgumentException("User not found");
    }

    if (!foundUser.getPassword().equals(password)) {
      throw new IllegalArgumentException("Invalid password");
    }

    String generatedToken = tokenProvider.generateToken(email);
    foundUser.setToken(generatedToken);
    userAccountRepository.save(foundUser);

    return new AuthenticationDto(foundUser.getToken(), foundUser.getUserType());
  }

  @Transactional
  public void logout(String token) {
    UserAccount foundUser = userAccountRepository.findUserByToken(token).orElse(null);

    if (foundUser == null) {
      throw new IllegalArgumentException("User not found");
    }

    foundUser.setToken(null);
    userAccountRepository.save(foundUser);
  }
}
