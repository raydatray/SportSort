package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserAccountRepository extends CrudRepository<UserAccount, Integer> {
  Optional<UserAccount> findUserByEmail(String newEmail);
  Optional<UserAccount> findUserByToken(String token);
  @Query("SELECT u FROM UserAccount u WHERE TYPE(u) IN :types")
  Optional<List<UserAccount>> findByUserType(@Param("types") List<Class<?>> types);
  void deleteByEmail(String email);
}
