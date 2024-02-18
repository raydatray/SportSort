package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.PaymentInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PaymentInfoRepositoryTests {
    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @AfterEach
    public void clearDatabase() {
        paymentInfoRepository.deleteAll();
    }
}
