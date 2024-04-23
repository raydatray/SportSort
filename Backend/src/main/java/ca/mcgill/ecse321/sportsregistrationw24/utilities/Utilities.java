package ca.mcgill.ecse321.sportsregistrationw24.utilities;

import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class Utilities {
   public static <T> ArrayList<T> iterableToArrayList(Iterable<T> anIterable) {
    ArrayList<T> returnList = new ArrayList<T>();

    for (T element : anIterable) {
      returnList.add(element);
    }

    return returnList;
  }

  public static UserAccount getUserFromToken(UserAccountRepository userAccountRepository, String token) {
    UserAccount userAccount = userAccountRepository.findUserByToken(token).orElse(null);
    if (userAccount == null) {
      throw new IllegalArgumentException("User not found");
    }
    return userAccount;
  }
}
