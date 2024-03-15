package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.PaymentInfoDto;
import ca.mcgill.ecse321.sportsregistrationw24.dto.RoomDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.PaymentInfo;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import ca.mcgill.ecse321.sportsregistrationw24.service.PaymentInfoService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
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
  public PaymentInfoDto createPaymentInfo(@RequestBody PaymentInfoDto paymentInfoDto) {

    PaymentInfo.PaymentType paymentType = paymentInfoDto.getPaymentType();
    Integer cardNumber = paymentInfoDto.getCardNumber();
    Integer cvv = paymentInfoDto.getCvv();
    Integer expirationYear = paymentInfoDto.getExpirationYear();
    Integer expirationMonth = paymentInfoDto.getExpirationMonth();
    String customerEmail = paymentInfoDto.getCustomerAccountEmail();

    PaymentInfo paymentInfo = service.createPaymentInfo(paymentType, cardNumber, cvv, expirationYear, expirationMonth, customerEmail);

    return convertToDto(paymentInfo);
  }

  @PutMapping(value = {
    "/paymentInfo/update",
    "/paymentInfo/update/"
  })
  public void updatePaymentInfo(@RequestBody PaymentInfoDto paymentInfoDto, @RequestParam Integer id) {
    PaymentInfo.PaymentType newPaymentType = paymentInfoDto.getPaymentType();
    Integer newCardNumber = paymentInfoDto.getCardNumber();
    Integer newCvv = paymentInfoDto.getCvv();
    Integer newExpirationYear = paymentInfoDto.getExpirationYear();
    Integer newExpirationMonth = paymentInfoDto.getExpirationMonth();

    service.updatePaymentInfo(id, newPaymentType, newCardNumber, newCvv, newExpirationYear, newExpirationMonth);
  }

  @GetMapping(value = {
    "/paymentInfo/get",
    "/paymentInfo/get/"
  })
  public PaymentInfoDto getPaymentInfo(@RequestHeader Integer id) { return convertToDto(service.getPaymentInfo(id)); }


  @GetMapping(value = {
          "/paymentInfo/getAll",
          "/paymentInfo/getAll/"
  })
  public List<PaymentInfoDto> getAllPaymentInfosPerCustomer(@RequestParam String email) { return convertToDtos(service.getAllPaymentInfoPerCustomer(email)); }



  @DeleteMapping(value = {
    "/paymentInfo/delete",
    "/paymentInfo/delete/"
  })
  public void deletePaymentInfo(@RequestParam Integer id) { service.deletePaymentInfo(id); }

  private PaymentInfoDto convertToDto(PaymentInfo paymentInfo) {
      if (paymentInfo == null) {
          throw new IllegalArgumentException("There is no such room");
      }
      return new PaymentInfoDto(paymentInfo.getId(), paymentInfo.getPaymentType(),
              paymentInfo.getCardNumber(), paymentInfo.getCvv(), paymentInfo.getExpirationYear(),
              paymentInfo.getExpirationMonth(), paymentInfo.getCustomerAccount().getEmail());
  }

  private List<PaymentInfoDto> convertToDtos(List<PaymentInfo> paymentInfo) {
    ArrayList<PaymentInfoDto> paymentInfoDtos = new ArrayList<>();
    for (PaymentInfo info: paymentInfo) {
      if (info == null) {
        throw new IllegalArgumentException("There is no such room");
      }
      paymentInfoDtos.add(convertToDto(info));
    }

    return paymentInfoDtos;
  }
}
