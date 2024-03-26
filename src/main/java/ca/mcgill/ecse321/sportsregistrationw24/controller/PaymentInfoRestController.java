package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.PaymentInfoDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.PaymentInfo;
import ca.mcgill.ecse321.sportsregistrationw24.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class PaymentInfoRestController {
  @Autowired
  private PaymentInfoService service;

  @PostMapping(value = {
    "/paymentInfo/create",
    "/paymentInfo/create/"
  })
  public ResponseEntity<?> createPaymentInfo(@RequestBody PaymentInfoDto paymentInfoDto, @RequestHeader String token) {
    try {
      PaymentInfo.PaymentType paymentType = paymentInfoDto.getPaymentType();
      Integer cardNumber = paymentInfoDto.getCardNumber();
      Integer cvv = paymentInfoDto.getCvv();
      Integer expirationYear = paymentInfoDto.getExpirationYear();
      Integer expirationMonth = paymentInfoDto.getExpirationMonth();

      PaymentInfo paymentInfo = service.createPaymentInfo(paymentType, cardNumber, cvv, expirationYear, expirationMonth, token);

      return ResponseEntity.ok().body("Payment information successfully created");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping(value = {
    "/paymentInfo/update",
    "/paymentInfo/update/"
  })
  public ResponseEntity<?> updatePaymentInfo(@RequestBody PaymentInfoDto paymentInfoDto) {
    try {
      PaymentInfo.PaymentType newPaymentType = paymentInfoDto.getPaymentType();
      Integer newCardNumber = paymentInfoDto.getCardNumber();
      Integer newCvv = paymentInfoDto.getCvv();
      Integer newExpirationYear = paymentInfoDto.getExpirationYear();
      Integer newExpirationMonth = paymentInfoDto.getExpirationMonth();
      Integer id = paymentInfoDto.getId();

      service.updatePaymentInfo(id, newPaymentType, newCardNumber, newCvv, newExpirationYear, newExpirationMonth);
      return ResponseEntity.ok().body("Payment information successfully updated");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {
    "/paymentInfo/get",
    "/paymentInfo/get/"
  })
  public ResponseEntity<?> getPaymentInfo(@RequestHeader Integer id) {
    try {
      PaymentInfoDto paymentInfoDto = convertToDto(service.getPaymentInfo(id));
      return ResponseEntity.ok().body(paymentInfoDto);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {
    "/paymentInfo/getAll",
    "/paymentInfo/getAll/"
  })
  public ResponseEntity<?> getAllPaymentInfosPerCustomer(@RequestHeader String token, @RequestParam Integer paymentType) {
    try {
      List<PaymentInfoDto> paymentInfoDtos = convertToDtos(service.getAllPaymentInfoPerCustomer(token, paymentType));
      return ResponseEntity.ok().body(paymentInfoDtos);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping(value = {
    "/paymentInfo/delete",
    "/paymentInfo/delete/"
  })
  public ResponseEntity<?> deletePaymentInfo(@RequestHeader Integer id) {
    try {
      service.deletePaymentInfo(id);
      return ResponseEntity.ok().body("Payment Info successfully deleted");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  private PaymentInfoDto convertToDto(PaymentInfo paymentInfo) {
    if (paymentInfo == null) {
      throw new IllegalArgumentException("There is no such payment info");
    }
      return new PaymentInfoDto(paymentInfo.getId(), paymentInfo.getPaymentType(), paymentInfo.getCardNumber(), paymentInfo.getCvv(), paymentInfo.getExpirationYear(), paymentInfo.getExpirationMonth());
  }

  private List<PaymentInfoDto> convertToDtos(List<PaymentInfo> paymentInfo) {
    ArrayList<PaymentInfoDto> paymentInfoDtos = new ArrayList<>();
    for (PaymentInfo info: paymentInfo) {
      if (info == null) {
        throw new IllegalArgumentException("There is no such payment info");
      }
      paymentInfoDtos.add(convertToDto(info));
    }

    return paymentInfoDtos;
  }
}
