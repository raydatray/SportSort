package ca.mcgill.ecse321.sportsregistrationw24.utilities;

import java.util.ArrayList;

public class Utilities {
  public static <T> ArrayList<T> iterableToArrayList(Iterable<T> anIterable) {
    ArrayList<T> returnList = new ArrayList<T>();

    for (T element : anIterable) {
      returnList.add(element);
    }

    return returnList;
  }
}
