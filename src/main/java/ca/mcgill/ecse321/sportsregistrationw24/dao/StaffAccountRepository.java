package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.StaffAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StaffAccountRepository extends CrudRepository<StaffAccount, Integer> {
    Optional<StaffAccount> findStaffByEmail(String email);
    Optional<StaffAccount> findByToken(String token);
    void deleteByEmail(String email);

}
