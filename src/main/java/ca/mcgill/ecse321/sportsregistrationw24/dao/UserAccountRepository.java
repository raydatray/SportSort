package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import org.apache.catalina.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserAccountRepository extends CrudRepository<UserAccount, Integer> {
  Optional<UserAccount> findUserByEmail(String newEmail);
  Optional<UserAccount> findUserByToken(String token);
  void deleteByEmail(String email);
}
