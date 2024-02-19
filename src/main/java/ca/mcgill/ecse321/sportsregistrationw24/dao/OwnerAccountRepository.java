package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.OwnerAccount;
import org.springframework.data.repository.CrudRepository;

public interface OwnerAccountRepository extends CrudRepository<OwnerAccount, Integer> {
    OwnerAccount findByOwnerAccountEmail(String email);
}
