package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.Staff;
import org.springframework.data.repository.CrudRepository;

public interface StaffRepository extends CrudRepository<Staff, Integer> {
}
