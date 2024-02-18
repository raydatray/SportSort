package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.SportSession;
import org.springframework.data.repository.CrudRepository;

public interface SportSessionRepository extends CrudRepository<SportSession, Integer> {
}
