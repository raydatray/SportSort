package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.SportCenterRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dto.SportsCenterDTO;
import ca.mcgill.ecse321.sportsregistrationw24.model.SportCenter;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;

import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.getUserFromToken;
import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.iterableToArrayList;

@Service
public class SportCenterService {
  private final SportCenterRepository sportCenterRepository;
  private final UserAccountRepository userAccountRepository;

  @Autowired
  public SportCenterService(SportCenterRepository sportCenterRepository, UserAccountRepository userAccountRepository) {
    this.sportCenterRepository = sportCenterRepository;
    this.userAccountRepository = userAccountRepository;
  }

  @Transactional
  public void updateSportsCenter(String userToken, Integer id, String aName, String aAddress, String aPhoneNumber, HashMap<DayOfWeek, ArrayList<Time>> someOperatingHours) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);
    Validate.isTrue(user.getUserType().equals("OWNER"), "Only owners can update sports center information");

    SportCenter sportCenter = sportCenterRepository.findById(id).orElse(null);
    Validate.notNull(sportCenter, "Sports center does not exist");

    //TODO: refactor this to be reusable
    if (aName != null) {
      Validate.notBlank(aName, "New sports center name cannot be empty");
      sportCenter.setName(aName);
    }
    if (aAddress != null) {
      Validate.notBlank(aAddress, "New sports center address cannot be empty");
      sportCenter.setAddress(aAddress);
    }
    if (aPhoneNumber != null) {
      Validate.notBlank(aPhoneNumber, "New sports center phone number cannot be empty");
      sportCenter.setPhoneNumber(aPhoneNumber);
    }
    if (someOperatingHours != null) {
      Validate.notEmpty(someOperatingHours, "New operating hours cannot be empty");
      sportCenter.setOperatingHours(someOperatingHours);
    }
    sportCenterRepository.save(sportCenter);
  }

  @Transactional
  public SportCenter getSportCenter() {
    return iterableToArrayList(sportCenterRepository.findAll()).get(0);
  }
}
