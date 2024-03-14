package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.Optional;


public interface CustomerAccountRepository extends CrudRepository<CustomerAccount, Integer> {
    Optional<CustomerAccount> findByEmail(String customerEmail);
    Optional<CustomerAccount> findByToken(String token);
    void deleteByEmail(String email);

}

