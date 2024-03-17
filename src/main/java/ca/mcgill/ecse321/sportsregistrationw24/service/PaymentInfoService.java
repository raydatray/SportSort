package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.PaymentInfoRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.PaymentInfo;
import ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.xml.SimpleTransformErrorListener;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentInfoService {
  @Autowired
  private PaymentInfoRepository paymentInfoRepository;
  @Autowired
  private CustomerAccountRepository customerAccountRepository;



  @Transactional
  public PaymentInfo createPaymentInfo(PaymentInfo.PaymentType aPaymentType, Integer aCardNumber, Integer aCvv, Integer aExpirationYear, Integer aExpirationMonth, String aToken) {
    /*
    if (aCardNumber.toString().length() != 16 || aCardNumber == null) {
      throw new IllegalArgumentException("This is not a valid credit/debit card number");
    }
    */

    if (aCvv == null || aCvv.toString().length() != 3) {
      throw new IllegalArgumentException("This is not a valid credit/debit card cvv");
    }

    if (aExpirationYear == null || aExpirationMonth == null) {
      throw new IllegalArgumentException("Cannot have a null month/year");
    }
    YearMonth currYearMonth = YearMonth.now();
    YearMonth inputYearMonth = YearMonth.of(aExpirationYear, aExpirationMonth);

    if (inputYearMonth.isBefore(currYearMonth)) {
      throw new IllegalArgumentException("This credit card is expired");
    }


    PaymentInfo newPaymentInfo = new PaymentInfo();
    newPaymentInfo.setPaymentType(aPaymentType);
    newPaymentInfo.setCardNumber(aCardNumber);
    newPaymentInfo.setCvv(aCvv);
    newPaymentInfo.setExpirationYear(aExpirationYear);
    newPaymentInfo.setExpirationMonth(aExpirationMonth);

    CustomerAccount fetchedCustomer = customerAccountRepository.findByToken(aToken).orElse(null);
    if (fetchedCustomer == null) {
      throw new IllegalArgumentException("CustomerAccount not found");
    }
    else {
      newPaymentInfo.setCustomerAccount(fetchedCustomer);
    }

    paymentInfoRepository.save(newPaymentInfo);

    return newPaymentInfo;
  }

  @Transactional
  public void updatePaymentInfo (Integer aID, PaymentInfo.PaymentType newPaymentType, Integer newCardNumber, Integer newCvv, Integer newExpirationYear, Integer newExpirationMonth) {
     /*
    if (aCardNumber == null || aCardNumber.toString().length() != 16) {
      throw new IllegalArgumentException("This is not a valid credit/debit card number");
    }
    */

    if (newCvv == null || newCvv.toString().length() != 3) {
      throw new IllegalArgumentException("This is not a valid credit/debit card cvv");
    }

    if (newExpirationYear == null || newExpirationMonth == null) {
      throw new IllegalArgumentException("Cannot have a null month/year");
    }

    Optional<PaymentInfo> wrappedPaymentInfo = paymentInfoRepository.findById(aID);
    PaymentInfo paymentInfo = wrappedPaymentInfo.orElseThrow(() -> new IllegalArgumentException("There is no paymentInfo with this id"));

    YearMonth currYearMonth = YearMonth.now();
    YearMonth inputYearMonth = YearMonth.of(newExpirationYear, newExpirationMonth);

    if (inputYearMonth.isBefore(currYearMonth)) {
      throw new IllegalArgumentException("This credit card is expired");
    }

    paymentInfo.setPaymentType(newPaymentType);
    paymentInfo.setCardNumber(newCardNumber);
    paymentInfo.setCvv(newCvv);
    paymentInfo.setExpirationYear(newExpirationYear);
    paymentInfo.setExpirationMonth(newExpirationMonth);

    paymentInfoRepository.save(paymentInfo);
  }

  @Transactional
  public PaymentInfo getPaymentInfo(Integer aID) { return paymentInfoRepository.findById(aID).orElseThrow(() -> new IllegalArgumentException("payment info with this ID doesn't exist")); }

  @Transactional
  public List<PaymentInfo> getAllPaymentInfoPerCustomer(String token) {
    Utilities utilities = new Utilities();
    ArrayList<PaymentInfo> allPaymentInfos = utilities.iterableToArrayList(paymentInfoRepository.findAll());

    ArrayList<PaymentInfo> filteredPaymentInfos = new ArrayList<>();
    CustomerAccount targetCustomer = customerAccountRepository.findByToken(token).orElseThrow(() -> new IllegalArgumentException("Customer not found"));

    for (PaymentInfo info: allPaymentInfos) {
      if (info.getCustomerAccount().equals(targetCustomer)) {
        filteredPaymentInfos.add(info);
      }
    }

    return filteredPaymentInfos;
  }

  @Transactional
  public void deletePaymentInfo(Integer aID) { paymentInfoRepository.deleteById(aID); }

}
