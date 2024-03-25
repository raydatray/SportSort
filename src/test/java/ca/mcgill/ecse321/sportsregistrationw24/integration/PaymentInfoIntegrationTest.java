package ca.mcgill.ecse321.sportsregistrationw24.integration;

import ca.mcgill.ecse321.sportsregistrationw24.dao.PaymentInfoRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dto.PaymentInfoDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.PaymentInfo;
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

  @BeforeEach
  @AfterEach
  public void clearDatabase() {
    paymentInfoRepository.deleteAll();
  }

  @Test
  public void testCreatePaymentInfo() {
    PaymentInfoDto paymentInfoDto = new PaymentInfoDto(null, PaymentInfo.PaymentType.Credit, 123456789, 123, 2025, 12);
    HttpHeaders headers = new HttpHeaders();
    headers.set("token", "someTokenValue");

    HttpEntity<PaymentInfoDto> request = new HttpEntity<>(paymentInfoDto, headers);

    ResponseEntity<String> response = restTemplate.postForEntity("/paymentInfo/create", request, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Payment information successfully created", response.getBody());
  }

  @Test
  public void testUpdatePaymentInfo() {
    // This assumes a PaymentInfoDto exists; you might need to create one as part of the setup.
    PaymentInfoDto updatedPaymentInfoDto = new PaymentInfoDto(1, PaymentInfo.PaymentType.Debit, 987654321, 321, 2030, 11);

    ResponseEntity<String> response = restTemplate.exchange("/paymentInfo/update", HttpMethod.PUT, new HttpEntity<>(updatedPaymentInfoDto), String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Payment information successfully updated", response.getBody());
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
    // Assuming an existing ID; you may need to insert a record as part of setup.
    Integer id = 1;
    HttpHeaders headers = new HttpHeaders();
    headers.set("id", id.toString());

    ResponseEntity<String> response = restTemplate.exchange("/paymentInfo/delete", HttpMethod.DELETE, new HttpEntity<>(headers), String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Payment Info successfully deleted", response.getBody());
  }
}
