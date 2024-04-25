package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dto.Authentication.AuthenticationDTO;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import ca.mcgill.ecse321.sportsregistrationw24.utilities.TokenProvider;
import ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.getUserFromToken;

@Service
public class AuthenticationService {
  private final UserAccountRepository userAccountRepository;
  private final TokenProvider tokenProvider;

  @Autowired
  public AuthenticationService(UserAccountRepository userAccountRepository, TokenProvider tokenProvider) {
    this.userAccountRepository = userAccountRepository;
    this.tokenProvider = tokenProvider;
  }

  @Transactional
  public AuthenticationDTO login(String email, String password) {
    UserAccount foundUser = userAccountRepository.findUserByEmail(email).orElse(null);

    if (foundUser == null || !foundUser.getPassword().equals(password)) {
      throw new IllegalArgumentException("No account found for provided credentials");
    }

    String generatedToken = tokenProvider.generateToken(email);
    foundUser.setToken(generatedToken);
    userAccountRepository.save(foundUser);

    return new AuthenticationDTO(foundUser.getToken(), foundUser.getUserType(), foundUser.getEmail(), foundUser.getName());
  }

  @Transactional
  public void logout(String userToken) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);
    user.setToken(null);
    userAccountRepository.save(user);
  }
}
