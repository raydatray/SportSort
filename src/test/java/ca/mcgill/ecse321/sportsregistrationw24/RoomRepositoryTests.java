package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.RoomRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoomRepositoryTests {
    @Autowired
    private RoomRepository roomRepository;

    @AfterEach
    public void clearDatabase() {
        roomRepository.deleteAll();
    }
}
