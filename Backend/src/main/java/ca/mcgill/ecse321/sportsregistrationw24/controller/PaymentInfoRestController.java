package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.PaymentInfo.PaymentInfoCO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.PaymentInfo.PaymentInfoDTO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.PaymentInfo.PaymentInfoUpdateCO;
import ca.mcgill.ecse321.sportsregistrationw24.model.PaymentInfo;
import ca.mcgill.ecse321.sportsregistrationw24.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class PaymentInfoRestController {
  @Autowired
  private PaymentInfoService service;

  @PostMapping(value = {"/paymentInfo/create"})
  public ResponseEntity<?> createPaymentInfo(@RequestHeader String userToken, @RequestBody PaymentInfoCO paymentInfoCO) {
    try {
      PaymentInfo.PaymentType paymentType = paymentInfoCO.getPaymentType();
      String cardNumber = paymentInfoCO.getCardNumber();
      Integer cvv = paymentInfoCO.getCvv();
      Integer expirationYear = paymentInfoCO.getExpirationYear();
      Integer expirationMonth = paymentInfoCO.getExpirationMonth();

      PaymentInfo paymentInfo = service.createPaymentInfo(userToken, paymentType, cardNumber, cvv, expirationYear, expirationMonth);

      return ResponseEntity.status(HttpStatus.CREATED).body(paymentInfo);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping(value = {"/paymentInfo/update"})
  public ResponseEntity<?> updatePaymentInfo(@RequestHeader String userToken, @RequestBody PaymentInfoUpdateCO paymentInfoUpdateCO) {
    try {
      Integer newExpirationYear = paymentInfoUpdateCO.getExpirationYear();
      Integer newExpirationMonth = paymentInfoUpdateCO.getExpirationMonth();
      Integer id = paymentInfoUpdateCO.getId();

      service.updatePaymentInfo(userToken, id, newExpirationYear, newExpirationMonth);
      return ResponseEntity.accepted().body("Payment info updated successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  /**
   * public ResponseEntity<?> getPaymentInfo(@RequestHeader Integer id) {
   * try {
   * PaymentInfoDto paymentInfoDto = convertToDto(service.getPaymentInfo(id));
   * return ResponseEntity.ok().body(paymentInfoDto);
   * } catch (Exception e) {
   * return ResponseEntity.badRequest().body(e.getMessage());
   * }
   * }
   **/

  @GetMapping(value = {"/paymentInfo/getAll"})
  public ResponseEntity<?> getAllPaymentInfosPerCustomer(@RequestHeader String userToken) {
    try {
      List<PaymentInfo> paymentInfos = service.getAllPaymentInfos(userToken);
      List<PaymentInfoDTO> paymentInfoDTOs = paymentInfos.stream().map(PaymentInfoDTO::new).toList();

      return ResponseEntity.ok().body(paymentInfoDTOs);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping(value = {"/paymentInfo/delete"})
  public ResponseEntity<?> deletePaymentInfo(@RequestHeader String userToken, @RequestParam Integer id) {
    try {
      service.deletePaymentInfo(userToken, id);
      return ResponseEntity.ok().body("Payment info deleted successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
