package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.Instructor;
import ca.mcgill.ecse321.sportsregistrationw24.model.Owner;
import ca.mcgill.ecse321.sportsregistrationw24.model.SportClass;
import ca.mcgill.ecse321.sportsregistrationw24.model.SportSession;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.sql.Time;

public interface OwnerRepository extends CrudRepository<Owner, Integer> {
    Owner findByOwnerEmail(String email);
    Owner findByOwnerName(String name);
}
