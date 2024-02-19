package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.SportCenterRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.SportCenter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SportCenterRepositoryTests {
    @Autowired
    private SportCenterRepository sportCenterRepository;

    @AfterEach
    public void clearDatabase() {
        sportCenterRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadSportCenter() {
        String centerName = "McGym";
        Time testOpeningTime = Time.valueOf("08:00:00");
        Time testClosingTime = Time.valueOf("18:00:00");

        SportCenter testCenter = new SportCenter(centerName,testOpeningTime,testClosingTime);

        sportCenterRepository.save(testCenter);

        Optional<SportCenter> readCenter = sportCenterRepository.findById(centerName);

        assertNotNull(testCenter = readCenter.orElse(null));
        assertEquals(centerName, testCenter.getName());
        assertEquals(testOpeningTime, testCenter.getOpeningHour());
        assertEquals(testClosingTime, testCenter.getClosingHour());
    }
}
