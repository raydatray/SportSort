package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.OwnerAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.OwnerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Deprecated
@Service
public class OwnerAccountService {
  @Autowired
  private UserAccountRepository userAccountRepository;
  @Autowired
  private OwnerAccountRepository ownerAccountRepository;

  @Transactional
  public void updateOwnerEmail(String newEmail, String token) {
    OwnerAccount ownerAccount = ownerAccountRepository.findByToken(token).orElse(null);
    if (ownerAccount == null) {
      throw new IllegalArgumentException("Owner Account does not exist!");
    }
    if (ownerAccount.getEmail().equals(newEmail)) {
      throw new IllegalArgumentException("New email matches old email!");
    }
    ownerAccount.setEmail(newEmail);
    ownerAccountRepository.save(ownerAccount);
  }

  @Transactional
  public void updateOwnerPassword(String newPassword, String oldPassword, String token) {
    OwnerAccount ownerAccount = ownerAccountRepository.findByToken(token).orElse(null);
    if (ownerAccount == null) {
      throw new IllegalArgumentException("Owner account does not exist!");
    }
    // Incorrect old password
    if (!ownerAccount.getPassword().equals(oldPassword)) {
      throw new IllegalArgumentException("Incorrect old password!");
    }
    // New password matches old password
    if (ownerAccount.getPassword().equals(newPassword)) {
      throw new IllegalArgumentException("New password cannot match the old password!");
    }
    ownerAccount.setPassword(newPassword);
    ownerAccountRepository.save(ownerAccount);
  }

  @Transactional
  public OwnerAccount getOwnerAccountByEmail(String email) {
    return ownerAccountRepository.findByEmail(email).orElse(null);
  }

  @Transactional
  public List<UserAccount> getAllUserAccounts() {
    Utilities utilities = new Utilities();
    return Utilities.iterableToArrayList(userAccountRepository.findAll());
  }
}
