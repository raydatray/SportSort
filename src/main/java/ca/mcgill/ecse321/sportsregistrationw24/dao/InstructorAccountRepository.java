package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InstructorAccountRepository extends CrudRepository<InstructorAccount, Integer> {
    Optional<InstructorAccount> findByEmail(String email);
    boolean existsByEmail(String email);
}
