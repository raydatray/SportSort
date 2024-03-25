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
import java.util.List;
import java.util.ArrayList;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;

@ExtendWith(MockitoExtension.class)
public class PaymentInfoServiceTests {
  @Mock
  private PaymentInfoRepository paymentInfoRepository;
  @Mock
  private CustomerAccountRepository customerAccountRepository;
  @InjectMocks
  private PaymentInfoService paymentInfoService;

  private static final String VALID_TOKEN = "validToken";
  private static final Integer VALID_ID = 123;

  @BeforeEach
  public void setUp() {
    lenient().when(customerAccountRepository.findByToken(VALID_TOKEN)).thenReturn(Optional.of(new CustomerAccount()));
    lenient().when(paymentInfoRepository.findById(anyInt())).thenAnswer(invocation -> {
      if (invocation.getArgument(0).equals(VALID_ID)) {
        PaymentInfo paymentInfo = new PaymentInfo();
        // Set properties if necessary
        return Optional.of(paymentInfo);
      }
      return Optional.empty();
    });
    lenient().when(paymentInfoRepository.save(any(PaymentInfo.class))).thenAnswer(invocation -> invocation.getArgument(0));
  }

  // Define tests here
  // ---- Payment Info tests ---- \\
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

    // Act and Assert: Expect an IllegalArgumentException due to invalid CVV
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
      paymentInfoService.createPaymentInfo(paymentType, (int) cardNumber, cvv, expirationYear, expirationMonth, token)
    );
    assertEquals("This is not a valid credit/debit card cvv", exception.getMessage());
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

  @Test
  public void testUpdatePaymentInfo_Success() {
    Integer paymentInfoId = VALID_ID; // Assumed to be a valid ID that exists
    PaymentInfo.PaymentType newPaymentType = PaymentInfo.PaymentType.Credit;
    Integer newCardNumber = 1234567890; // Assuming this passes your internal checks
    Integer newCvv = 123; // Valid CVV
    Integer newExpirationYear = YearMonth.now().plusYears(1).getYear(); // Future year, valid
    Integer newExpirationMonth = 12; // Valid month

    assertDoesNotThrow(() -> paymentInfoService.updatePaymentInfo(
      paymentInfoId,
      newPaymentType,
      newCardNumber,
      newCvv,
      newExpirationYear,
      newExpirationMonth));

    verify(paymentInfoRepository, times(1)).save(any(PaymentInfo.class));
  }

  @Test
  public void testUpdatePaymentInfo_ExpiredCard() {
    YearMonth lastMonth = YearMonth.now().minusMonths(1);
    Integer expiredYear = lastMonth.getYear();
    Integer expiredMonth = lastMonth.getMonthValue();

    Exception exception = assertThrows(IllegalArgumentException.class, () -> paymentInfoService.updatePaymentInfo(
      VALID_ID,
      PaymentInfo.PaymentType.Credit,
      1234567890,
      123,
      expiredYear,
      expiredMonth));

    assertEquals("This credit card is expired", exception.getMessage());
  }

  @Test
  public void testUpdatePaymentInfo_InvalidCvv() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> paymentInfoService.updatePaymentInfo(
      VALID_ID,
      PaymentInfo.PaymentType.Credit,
      1234567890,
      12, // Invalid CVV
      2025,
      12));

    assertEquals("This is not a valid credit/debit card cvv", exception.getMessage());
  }

  @Test
  public void testGetPaymentInfo_Success() {
    // Given
    PaymentInfo expectedPaymentInfo = new PaymentInfo();
    // Assume other necessary properties are set on expectedPaymentInfo as needed
    when(paymentInfoRepository.findById(VALID_ID)).thenReturn(Optional.of(expectedPaymentInfo));
    // When
    PaymentInfo actualPaymentInfo = paymentInfoService.getPaymentInfo(VALID_ID);
    // Then
    assertNotNull(actualPaymentInfo, "Expected a non-null PaymentInfo.");
  }
  @Test
  public void testGetPaymentInfo_NonExistingId() {
    // Given an invalid ID, the method should throw an IllegalArgumentException.
    final Integer nonExistingId = 999; // This ID does not exist.
    when(paymentInfoRepository.findById(nonExistingId)).thenReturn(Optional.empty());
    // When & Then
    Exception exception = assertThrows(IllegalArgumentException.class, () -> paymentInfoService.getPaymentInfo(nonExistingId));
    assertEquals("payment info with this ID doesn't exist", exception.getMessage(), "Expected exception message to indicate the non-existence of the PaymentInfo.");
  }

  @Test
  public void testDeletePaymentInfo_Success() {
    // Arrange: Assume VALID_ID refers to an existing PaymentInfo
    PaymentInfo existingPaymentInfo = new PaymentInfo();
    when(paymentInfoRepository.findById(VALID_ID)).thenReturn(Optional.of(existingPaymentInfo));
    // Act: Attempt to delete the PaymentInfo
    assertDoesNotThrow(() -> paymentInfoService.deletePaymentInfo(VALID_ID));
    // Assert: Verify that the repository's delete method was called with the correct PaymentInfo
    verify(paymentInfoRepository, times(1)).delete(existingPaymentInfo);
  }

  @Test
  public void testDeletePaymentInfo_NonExistingId() {
    // Arrange: Set up the repository to return an empty Optional for a non-existing ID
    final Integer nonExistingId = 999; // An ID that does not exist
    when(paymentInfoRepository.findById(nonExistingId)).thenReturn(Optional.empty());
    // Act & Assert: Expect an IllegalArgumentException when attempting to delete a non-existing PaymentInfo
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
      () -> paymentInfoService.deletePaymentInfo(nonExistingId),
      "Expected deletePaymentInfo to throw for non-existing ID");
    assertEquals("There is no PaymentInfo with this id", thrown.getMessage(), "Expected exception message to indicate non-existence of the PaymentInfo.");

    // Additionally, ensure no deletion attempts were made on the repository
    verify(paymentInfoRepository, never()).delete(any(PaymentInfo.class));
  }




}
