package ca.mcgill.ecse321.sportsregistrationw24.integration;
import ca.mcgill.ecse321.sportsregistrationw24.dao.PaymentInfoRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dto.PaymentInfoDto;
import ca.mcgill.ecse321.sportsregistrationw24.dto.RoomDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.PaymentInfo;
import ca.mcgill.ecse321.sportsregistrationw24.service.AuthenticationService;
import ca.mcgill.ecse321.sportsregistrationw24.service.CustomerAccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PaymentInfoIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private PaymentInfoRepository paymentInfoRepository;
  @Autowired
  private CustomerAccountService customerAccountService;
  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private UserAccountRepository userAccountRepository;
  private String logInCustomerAccount(String email, String password) { return authenticationService.login(email, password).getToken();}
  private CustomerAccount createCustomerAccount(String email, String password, String name) { return customerAccountService.createCustomerAccount(email, password, name);}

  @BeforeEach
  @AfterEach
  public void clearDatabase() {
    paymentInfoRepository.deleteAll();
    userAccountRepository.deleteAll();
  }

  @Test
  public void testCreatePaymentInfo() {
    // Create a new customer account
    String customerName = "Test User";
    String customerEmail = "houman@example.com";
    String customerPassword = "password";
    CustomerAccount customer = createCustomerAccount(customerEmail, customerPassword, customerName);
    // Log in to get a token
    String token = logInCustomerAccount(customerEmail, customerPassword);

    // Now proceed to create payment info with the obtained token
    PaymentInfoDto paymentInfoDto = new PaymentInfoDto(1, PaymentInfo.PaymentType.Credit, 123456789, 123, 2025, 12);
    HttpHeaders headers = new HttpHeaders();
    headers.set("token", token);
    HttpEntity<PaymentInfoDto> request = new HttpEntity<>(paymentInfoDto, headers);

    ResponseEntity<String> response = restTemplate.postForEntity("/paymentInfo/create", request, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Payment information successfully created", response.getBody());
  }
}
