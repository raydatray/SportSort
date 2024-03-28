package ca.mcgill.ecse321.sportsregistrationw24.dao;


import ca.mcgill.ecse321.sportsregistrationw24.model.PaymentInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PaymentInfoRepository extends CrudRepository<PaymentInfo, Integer> {

}
