package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.PaymentInfoRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.PaymentInfo;
import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PaymentInfoRepositoryTests {
    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @AfterEach
    public void clearDatabase() {
        paymentInfoRepository.deleteAll();
    }
    @Test
    public void testPersistAndLoadPaymentInfo() {
        PaymentInfo.PaymentType paymentType = PaymentInfo.PaymentType.Credit;
        Integer testCardNumber = 12345;
        Integer testCvv = 123;
        Integer testExpiryYear = 24;
        Integer testExpiryMonth = 6;
        CustomerAccount testCustomer = new CustomerAccount(1,"houman@gmail.com", "eye");

        PaymentInfo testPayment = new PaymentInfo(1,paymentType,testCardNumber,testCvv,testExpiryYear,testExpiryMonth,testCustomer);

        paymentInfoRepository.save(testPayment);

        Optional<PaymentInfo> readPayment = paymentInfoRepository.findById(1);

        assertNotNull(testPayment = readPayment.orElse(null));
        assertEquals(paymentType, testPayment.getPaymentType());
        assertEquals(testCardNumber, testPayment.getCardNumber());
        assertEquals(testCvv,testPayment.getCvv());
        assertEquals(testExpiryYear, testPayment.getExpirationYear());
        assertEquals(testExpiryMonth, testPayment.getExpirationMonth());
        assertEquals(testCustomer, testPayment.getCustomerAccount());
    }

}
