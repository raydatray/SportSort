package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.SportCenterRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dto.SportsCenterDTO;
import ca.mcgill.ecse321.sportsregistrationw24.model.SportCenter;

import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.*;

import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;

@Service
public class SportCenterService {
    @Autowired
    private SportCenterRepository sportCenterRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    public SportsCenterDTO updateSportsCenter(String userToken, String name, String address, String phoneNumber, Time openingHour, Time closingHour) {
        UserAccount user = getUserFromToken(userAccountRepository, userToken);

        if (!user.getUserType().equals("OWNER")) {
            throw new IllegalArgumentException("Only owners can update sports center information");
        }

        SportCenter sportCenter = sportCenterRepository.findByName(name).orElse(null);

        if (sportCenter == null) {
            throw new IllegalArgumentException("Sports center does not exist");
        }

        sportCenter.setAddress(address);
        sportCenter.setPhoneNumber(phoneNumber);
        sportCenter.setOpeningHour(openingHour);
        sportCenter.setClosingHour(closingHour);

        sportCenterRepository.save(sportCenter);

        return new SportsCenterDTO(sportCenter);
    }

    public SportCenter getSportCenter() {
        return iterableToArrayList(sportCenterRepository.findAll()).get(0);
    }
}
