package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.PaymentInfoRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dto.PaymentInfo.PaymentInfoDTO;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.PaymentInfo;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;

import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.getUserFromToken;

@Service
public class PaymentInfoService {
  @Autowired
  private PaymentInfoRepository paymentInfoRepository;
  @Autowired
  private UserAccountRepository userAccountRepository;

  @Transactional
  public PaymentInfo createPaymentInfo(String userToken, PaymentInfo.PaymentType aPaymentType, String aCardNumber, Integer aCvv, Integer aExpirationYear, Integer aExpirationMonth) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

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
    newPaymentInfo.setCustomerAccount((CustomerAccount) user);

    paymentInfoRepository.save(newPaymentInfo);

    return newPaymentInfo;
  }

  @Transactional
  public PaymentInfoDTO updatePaymentInfo(String userToken, Integer aId, Integer newExpirationYear, Integer newExpirationMonth) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    YearMonth currYearMonth = YearMonth.now();
    YearMonth inputYearMonth = YearMonth.of(newExpirationYear, newExpirationMonth);

    if (inputYearMonth.isBefore(currYearMonth)) {
      throw new IllegalArgumentException("This credit card is expired");
    }

    PaymentInfo foundPaymentInfo = paymentInfoRepository.findById(aId).orElse(null);

    if (foundPaymentInfo == null) {
      throw new IllegalArgumentException("Payment Info not found");
    }

    if (!foundPaymentInfo.getCustomerAccount().getId().equals(user.getId())) {
      throw new IllegalArgumentException("This payment info does not belong to the user");
    }

    foundPaymentInfo.setExpirationYear(newExpirationYear);
    foundPaymentInfo.setExpirationMonth(newExpirationMonth);

    paymentInfoRepository.save(foundPaymentInfo);

    return convertToDto(foundPaymentInfo);
  }

  @Transactional
  public List<PaymentInfo> getAllPaymentInfos(String userToken) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    List<PaymentInfo> foundPaymentInfos = paymentInfoRepository.findByCustomerAccount((CustomerAccount) user).orElse(null);

    return foundPaymentInfos;
  }

  @Transactional
  public void deletePaymentInfo(String userToken, Integer aId) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    PaymentInfo foundPaymentInfo = paymentInfoRepository.findById(aId).orElse(null);

    if (foundPaymentInfo == null) {
      throw new IllegalArgumentException("Payment Info not found");
    }

    if (!foundPaymentInfo.getCustomerAccount().getId().equals(user.getId())) {
      throw new IllegalArgumentException("This payment info does not belong to the user");
    }

    paymentInfoRepository.delete(foundPaymentInfo);
  }
}
