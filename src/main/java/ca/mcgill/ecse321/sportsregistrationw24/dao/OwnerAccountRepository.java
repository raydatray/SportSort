package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.OwnerAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OwnerAccountRepository extends CrudRepository<OwnerAccount, Integer> {
    Optional<OwnerAccount> findByEmail(String email);

}
