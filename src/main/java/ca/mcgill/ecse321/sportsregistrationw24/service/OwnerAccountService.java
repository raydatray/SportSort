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

@Service
public class OwnerAccountService {
  @Autowired
  private UserAccountRepository userAccountRepository;
  @Autowired
  private OwnerAccountRepository ownerAccountRepository;

  @Transactional
  public void updateOwnerEmail(String oldEmail, String email) {
    OwnerAccount ownerAccount = ownerAccountRepository.findByEmail(oldEmail).orElse(null);

    if (ownerAccount == null) {
      throw new IllegalArgumentException("Owner Account does not exist!");
    }
    ownerAccount.setEmail(email);
    ownerAccountRepository.save(ownerAccount);
  }

  @Transactional
  public void updateOwnerPassword(OwnerAccount owner, String newPassword, String oldPassword) {
    // Incorrect old password
    if (!owner.getPassword().equals(oldPassword)) {
      throw new IllegalArgumentException("Incorrect old password!");
    }
    // New password matches old password
    if (owner.getPassword().equals(newPassword)) {
      throw new IllegalArgumentException("New password cannot match the old password!");
    }
    owner.setPassword(newPassword);
    ownerAccountRepository.save(owner);
  }

  @Transactional
  public OwnerAccount getOwnerAccount(String email) {
    return ownerAccountRepository.findByEmail(email).orElse(null);
  }

  @Transactional
  public List<UserAccount> getAllUserAccounts() {
    Utilities utilities = new Utilities();
    return utilities.iterableToArrayList(userAccountRepository.findAll());
  }
}
