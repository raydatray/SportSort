package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.PaymentInfoRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.PaymentInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.YearMonth;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
public class PaymentInfoServiceTests {

  @Mock
  private PaymentInfoRepository paymentInfoRepository;

  @Mock
  private CustomerAccountRepository customerAccountRepository;

  @InjectMocks
  private PaymentInfoService paymentInfoService;

  private PaymentInfo paymentInfo;
  private CustomerAccount customerAccount;

  // Constants for testing
  private static final long VALID_CARD_NUMBER_LONG = 123456789012345L;
  private static final Integer VALID_CVV = 123;
  private static final Integer VALID_EXPIRATION_YEAR = YearMonth.now().plusMonths(1).getYear();
  private static final Integer VALID_EXPIRATION_MONTH = YearMonth.now().plusMonths(1).getMonthValue();
  private static final String VALID_TOKEN = "validToken"; //TODO How to create a valid token in testing

  @BeforeEach
  public void setup() {
    // Initialize your PaymentInfo and CustomerAccount objects here
    customerAccount = new CustomerAccount();
    paymentInfo = new PaymentInfo();

    // Setup mocks
    when(customerAccountRepository.findByToken(anyString())).thenReturn(Optional.of(customerAccount));
    when(paymentInfoRepository.save(any(PaymentInfo.class))).thenAnswer(i -> i.getArguments()[0]);
  }

  // Define tests here
  // ---- Payment Info test ---- \\
  // Successful test
  @Test
  public void testCreatePaymentInfo_Success() {
    // Assuming all input parameters are valid
    assertDoesNotThrow(() -> paymentInfoService.createPaymentInfo(
      PaymentInfo.PaymentType.Credit,
      (int) 1234567890123456L,
      123,
      2025,
      12,
      VALID_TOKEN));

    verify(paymentInfoRepository, times(1)).save(any(PaymentInfo.class));
  }

  // CVV is not 3 digits
  @Test
  public void testCreatePaymentInfo_InvalidCVV() {
    // Precondition: The CVV is invalid
    PaymentInfo.PaymentType paymentType = PaymentInfo.PaymentType.Debit;
    long cardNumber = 1234567890123456L; // Still a valid card number
    int cvv = 12; // Incorrect CVV length, should be 3 digits
    int expirationYear = 2025; // Still a future year
    int expirationMonth = 12; // Still a valid month
    String token = VALID_TOKEN; // Assume this token is valid
    PaymentInfo paymentinfo = null;
    String error = null;
    try {
      paymentinfo = paymentInfoService.createPaymentInfo(paymentType, (int)cardNumber, cvv, expirationYear, expirationMonth, token);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(paymentinfo);
    // Check error
    assertEquals("This is not a valid credit/debit card cvv", error);
  }

  // Expired card
  @Test
  public void testCreatePaymentInfo_CardExpired() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> paymentInfoService.createPaymentInfo(
      PaymentInfo.PaymentType.Debit,
      (int) 1234567890123456L,
      123,
      2020, // Expired year
      1, // Expired month
      VALID_TOKEN));

    assertEquals("This credit card is expired", exception.getMessage());
  }

}
