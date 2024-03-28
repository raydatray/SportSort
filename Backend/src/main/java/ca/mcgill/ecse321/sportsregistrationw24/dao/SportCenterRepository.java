package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.SportCenter;
import org.springframework.data.repository.CrudRepository;

import java.sql.Time;

public interface SportCenterRepository extends CrudRepository<SportCenter, String> {
}
