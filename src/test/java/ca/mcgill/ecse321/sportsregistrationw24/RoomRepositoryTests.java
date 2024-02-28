package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.RoomRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RoomRepositoryTests {
    @Autowired
    private RoomRepository roomRepository;

    @BeforeEach
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

        Optional<Room> readRoom = roomRepository.findRoomByFloorNumberAndRoomNumber(testFloor, testRoomNum);

        if (readRoom.isPresent()) {
            // Retrieve the Room object
            Room room = readRoom.get();

            assertNotNull(room);
            assertEquals(testName, room.getName());
            assertEquals(testFloor, room.getFloorNumber());
            assertEquals(testRoomNum, room.getRoomNumber());
            assertEquals(testCapacity, room.getCapacity());
        }
    }
}
