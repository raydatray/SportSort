package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.PaymentInfoRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.PaymentInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        paymentInfoRepository.deleteAll();
        customerAccountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadPaymentInfo() {
        PaymentInfo.PaymentType paymentType = PaymentInfo.PaymentType.Credit;
        Integer testCardNumber = 12345;
        Integer testCvv = 123;
        Integer testExpiryYear = 24;
        Integer testExpiryMonth = 6;

        CustomerAccount testCustomer = new CustomerAccount("houman","houman@gmail.com", "eye");
        CustomerAccount testCustomer2 = new CustomerAccount("ray", "rayemail", "raypassword");

        customerAccountRepository.save(testCustomer);
        customerAccountRepository.save(testCustomer2);


        PaymentInfo testPayment = new PaymentInfo(paymentType,testCardNumber,testCvv,testExpiryYear,testExpiryMonth,testCustomer);
        PaymentInfo testPayment2 = new PaymentInfo(paymentType,123490823,892,2099,7,testCustomer);

        PaymentInfo testPayment3 = new PaymentInfo(paymentType,testCardNumber,testCvv,testExpiryYear,testExpiryMonth,testCustomer2);
        PaymentInfo testPayment4 = new PaymentInfo(paymentType,123490823,892,2099,7,testCustomer2);

        paymentInfoRepository.save(testPayment);
        paymentInfoRepository.save(testPayment2);
        paymentInfoRepository.save(testPayment3);
        paymentInfoRepository.save(testPayment4);

        Integer paymentInfoId = testPayment.getId();


        Optional<PaymentInfo> readPayment = paymentInfoRepository.findById(paymentInfoId);

        assertNotNull(testPayment = readPayment.orElse(null));
        assertEquals(testCustomer.getId(), testPayment.getCustomerAccount().getId());

        assertEquals(paymentType, testPayment.getPaymentType());
        assertEquals(testCardNumber, testPayment.getCardNumber());
        assertEquals(testCvv,testPayment.getCvv());
        assertEquals(testExpiryYear, testPayment.getExpirationYear());
        assertEquals(testExpiryMonth, testPayment.getExpirationMonth());
    }

}
