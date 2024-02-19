package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InstructorAccountRepository extends CrudRepository<InstructorAccount, Integer> {
    Optional<InstructorAccount> findInstructorAccountByEmail(String email);
    Optional<InstructorAccount> findInstructorAccountByName(String name);
    boolean existsByInstructorAccountEmail(String email);
}
