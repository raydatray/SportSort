package ca.mcgill.ecse321.sportsregistrationw24.integration;
import ca.mcgill.ecse321.sportsregistrationw24.dao.PaymentInfoRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dto.PaymentInfoDto;
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
    PaymentInfoDto paymentInfoDto = new PaymentInfoDto(null, PaymentInfo.PaymentType.Credit, 123456789, 123, 2025, 12);
    HttpHeaders headers = new HttpHeaders();
    headers.set("token", token);
    HttpEntity<PaymentInfoDto> request = new HttpEntity<>(paymentInfoDto, headers);

    ResponseEntity<String> response = restTemplate.postForEntity("/paymentInfo/create", request, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Payment information successfully created", response.getBody());
  }

  @Test
  public void testUpdatePaymentInfo() {
    PaymentInfoDto originalPaymentInfoDto = new PaymentInfoDto(1, PaymentInfo.PaymentType.Debit, 100100100, 555, 2031, 12);
    restTemplate.postForEntity("/paymentInfo/create", originalPaymentInfoDto, PaymentInfoDto.class);

    PaymentInfoDto updatedPaymentInfoDto = new PaymentInfoDto(1, PaymentInfo.PaymentType.Debit, 987654321, 321, 2030, 11);

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<PaymentInfoDto> requestUpdate = new HttpEntity<>(updatedPaymentInfoDto, headers);

    ResponseEntity<PaymentInfoDto> response = restTemplate.exchange("/paymentInfo/update", HttpMethod.PUT, requestUpdate, PaymentInfoDto.class);

    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response should be OK");
    //assertEquals("Payment information successfully updated", response.getBody());
  }

  @Test
  public void testGetPaymentInfo() {
    // Assuming an existing ID, you might need to insert a record as part of the setup.
    Integer id = 1;
    HttpHeaders headers = new HttpHeaders();
    headers.set("id", id.toString());

    ResponseEntity<PaymentInfoDto> response = restTemplate.exchange("/paymentInfo/get", HttpMethod.GET, new HttpEntity<>(headers), PaymentInfoDto.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(id, response.getBody().getId());
  }

  @Test
  public void testGetAllPaymentInfosPerCustomer() {
    String token = "customerToken";
    Integer paymentType = 0; // Assuming 0 represents a specific type, e.g., CREDIT_CARD

    HttpHeaders headers = new HttpHeaders();
    headers.set("token", token);

    ResponseEntity<PaymentInfoDto[]> response = restTemplate.exchange("/paymentInfo/getAll?paymentType=" + paymentType, HttpMethod.GET, new HttpEntity<>(headers), PaymentInfoDto[].class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().length > 0);
  }

  @Test
  public void testDeletePaymentInfo() {
    // Step 1: Create a payment info to have an entry to delete
    PaymentInfoDto newPaymentInfo = new PaymentInfoDto(null, PaymentInfo.PaymentType.Debit, 100100100, 555, 2031, 12);
    ResponseEntity<PaymentInfoDto> createResponse = restTemplate.postForEntity("/paymentInfo/create", newPaymentInfo, PaymentInfoDto.class);
    assertEquals(HttpStatus.OK, createResponse.getStatusCode(), "Payment info creation failed");
    assertNotNull(createResponse.getBody().getId(), "Creation did not return an ID");

    // Step 2: Delete the payment info using the ID from creation
    HttpHeaders headers = new HttpHeaders();
    headers.set("id", createResponse.getBody().getId().toString());
    ResponseEntity<String> deleteResponse = restTemplate.exchange("/paymentInfo/delete", HttpMethod.DELETE, new HttpEntity<>(headers), String.class);

    // Step 3: Verify the deletion was successful
    assertEquals(HttpStatus.OK, deleteResponse.getStatusCode(), "Deletion did not return HTTP 200 OK");
    assertTrue(deleteResponse.getBody().contains("successfully deleted"), "Deletion confirmation message not as expected");

    // Optional: Verify the record does not exist anymore (requires an additional endpoint or method)
  }
}
