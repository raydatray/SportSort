package ca.mcgill.ecse321.sportsregistrationw24.utilities;

import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;

import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Utilities {
  public static <T> ArrayList<T> iterableToArrayList(Iterable<T> anIterable) {
    ArrayList<T> returnList = new ArrayList<T>();

    for (T element : anIterable) {
      returnList.add(element);
    }

    return returnList;
  }

  public static UserAccount getUserFromToken(UserAccountRepository userAccountRepository, String userToken) {
    UserAccount foundUser = userAccountRepository.findUserByToken(userToken).orElse(null);
    if (foundUser == null) {
      throw new IllegalArgumentException("No user found with provided token");
    }
    return foundUser;
  }
}
