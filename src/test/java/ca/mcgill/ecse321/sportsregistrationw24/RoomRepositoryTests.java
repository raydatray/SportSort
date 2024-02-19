package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.RoomRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RoomRepositoryTests {
    @Autowired
    private RoomRepository roomRepository;

    @AfterEach
    public void clearDatabase() {
        roomRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadRoom() {
        String testName = "Rink";
        Integer testFloor = 1;
        Integer testRoomNum = 10;
        Integer testCapacity = 100;

        Room testRoom = new Room(testName, testFloor, testRoomNum, testCapacity);

        roomRepository.save(testRoom);

        Room readRoom = roomRepository.findRoomByFloorNumberAndRoomNumber(testFloor, testRoomNum);

        assertNotNull(readRoom);
        assertEquals(testName, readRoom.getName());
        assertEquals(testFloor, readRoom.getFloorNumber());
        assertEquals(testRoomNum, readRoom.getRoomNumber());
        assertEquals(testCapacity, readRoom.getCapacity());
    }
}
