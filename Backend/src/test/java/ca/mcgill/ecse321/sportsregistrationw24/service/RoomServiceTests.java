package ca.mcgill.ecse321.sportsregistrationw24.service;
import ca.mcgill.ecse321.sportsregistrationw24.dao.RoomRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import ca.mcgill.ecse321.sportsregistrationw24.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTests {

  @Mock
  private RoomRepository roomRepository;

  @InjectMocks
  private RoomService roomService;

  @BeforeEach
  public void setUp() {
    // This is where you can place common setup code if needed
  }

  @Test
  public void testCreateRoom_Success() {
    // Arrange
    String aName = "Conference Room";
    Integer aFloorNumber = 2;
    Integer aRoomNumber = 204;
    Integer capacity = 50;

    when(roomRepository.findRoomByFloorNumberAndRoomNumber(aFloorNumber, aRoomNumber)).thenReturn(Optional.empty());
    when(roomRepository.save(any(Room.class))).thenAnswer(i -> i.getArgument(0));

    // Act
    Room result = roomService.createRoom(aName, aFloorNumber, aRoomNumber, capacity);

    // Assert
    assertNotNull(result);
    assertEquals(aName, result.getName());
    assertEquals(aFloorNumber, result.getFloorNumber());
    assertEquals(aRoomNumber, result.getRoomNumber());
    assertEquals(capacity, result.getCapacity());
  }

  @Test
  public void testCreateRoom_NoNameSpecified() {
    // Arrange
    Integer aFloorNumber = 3;
    Integer aRoomNumber = 305;
    Integer capacity = 25;

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> roomService.createRoom(null, aFloorNumber, aRoomNumber, capacity));
    assertEquals("Cannot create a room with no name specified", exception.getMessage());
  }

  @Test
  public void testCreateRoom_NoFloorAndNoRoomNumberSpecified() {
    String aName = "Conference Room";
    Integer capacity = 50;

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
      () -> roomService.createRoom(aName, null, null, capacity));

    assertEquals("Cannot create a room with no floor and no room number specified", thrown.getMessage());
  }

  @Test
  public void testCreateRoom_NoFloorNumberSpecified() {
    String aName = "Meeting Room";
    Integer aRoomNumber = 101;
    Integer capacity = 20;

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
      () -> roomService.createRoom(aName, null, aRoomNumber, capacity));

    assertEquals("Cannot create a room with no floor number specified", thrown.getMessage());
  }

  @Test
  public void testCreateRoom_NoRoomNumberSpecified() {
    String aName = "Lecture Room";
    Integer aFloorNumber = 2;
    Integer capacity = 40;

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
      () -> roomService.createRoom(aName, aFloorNumber, null, capacity));

    assertEquals("Cannot create a room with no room number specified", thrown.getMessage());
  }

  @Test
  public void testCreateRoom_RoomAlreadyExists() {
    // Arrange
    String aName = "Study Room";
    Integer aFloorNumber = 1;
    Integer aRoomNumber = 101;
    Integer capacity = 10;

    Room existingRoom = new Room();
    existingRoom.setName(aName);
    existingRoom.setFloorNumber(aFloorNumber);
    existingRoom.setRoomNumber(aRoomNumber);
    existingRoom.setCapacity(capacity);

    when(roomRepository.findRoomByFloorNumberAndRoomNumber(aFloorNumber, aRoomNumber)).thenReturn(Optional.of(existingRoom));

    // Act & Assert
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
      () -> roomService.createRoom(aName, aFloorNumber, aRoomNumber, capacity));

    assertEquals("A room with this floor or room number already exists", thrown.getMessage());
  }

  @Test
  public void testCreateRoom_CapacityIsNull() {
    // Arrange
    String aName = "Library Room";
    Integer aFloorNumber = 2;
    Integer aRoomNumber = 202;

    // Act & Assert
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
      () -> roomService.createRoom(aName, aFloorNumber, aRoomNumber, null));

    assertEquals("A room with this capacity cannot exist", thrown.getMessage());
  }

  @Test
  public void testCreateRoom_CapacityIsInvalid() {
    // Arrange
    String aName = "Auditorium";
    Integer aFloorNumber = 3;
    Integer aRoomNumber = 303;
    Integer capacity = -1;

    // Act & Assert
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
      () -> roomService.createRoom(aName, aFloorNumber, aRoomNumber, capacity));

    assertEquals("A room with this capacity cannot exist", thrown.getMessage());
  }

  @Test
  public void testUpdateRoom_Success() {
    // Arrange
    String newName = "Updated Room Name";
    Integer aFloorNumber = 1;
    Integer aRoomNumber = 101;
    Integer newCapacity = 25;
    Room existingRoom = new Room(); // Assume this room already has initial values

    when(roomRepository.findRoomByFloorNumberAndRoomNumber(aFloorNumber, aRoomNumber))
      .thenReturn(Optional.of(existingRoom));

    // Act
    assertDoesNotThrow(() -> roomService.updateRoom(newName, aFloorNumber, aRoomNumber, newCapacity));

    // Assert
    assertEquals(newName, existingRoom.getName());
    assertEquals(newCapacity, existingRoom.getCapacity());
    verify(roomRepository, times(1)).save(existingRoom);
  }
  @Test
  public void testUpdateRoom_NoNameSpecified() {
    // Arrange
    Integer aFloorNumber = 1;
    Integer aRoomNumber = 102;
    Integer newCapacity = 20;

    // Act & Assert
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
      () -> roomService.updateRoom(null, aFloorNumber, aRoomNumber, newCapacity));

    assertEquals("Cannot update a room with no name specified", thrown.getMessage());
  }

  @Test
  public void testUpdateRoom_InvalidCapacity() {
    // Arrange
    String newName = "Study Room";
    Integer aFloorNumber = 2;
    Integer aRoomNumber = 202;
    Integer newCapacity = -5; // Invalid capacity

    Room room = new Room();
    when(roomRepository.findRoomByFloorNumberAndRoomNumber(aFloorNumber, aRoomNumber))
      .thenReturn(Optional.of(room));

    // Act & Assert
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
      () -> roomService.updateRoom(newName, aFloorNumber, aRoomNumber, newCapacity));

    assertEquals("A room with this capacity cannot exist", thrown.getMessage());
  }

  @Test
  public void testUpdateRoom_RoomNotFound() {
    // Arrange
    String newName = "Library";
    Integer aFloorNumber = 99; // Non-existing
    Integer aRoomNumber = 999; // Non-existing
    Integer newCapacity = 50;

    when(roomRepository.findRoomByFloorNumberAndRoomNumber(aFloorNumber, aRoomNumber))
      .thenReturn(Optional.empty());

    // Act & Assert
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
      () -> roomService.updateRoom(newName, aFloorNumber, aRoomNumber, newCapacity));

    assertEquals("No room with this floor and room number was found", thrown.getMessage());
  }

  @Test
  public void testGetRoom_Success() {
    Integer aFloorNumber = 1;
    Integer aRoomNumber = 101;
    Room expectedRoom = new Room();
    expectedRoom.setName("Conference Room");
    expectedRoom.setFloorNumber(aFloorNumber);
    expectedRoom.setRoomNumber(aRoomNumber);
    expectedRoom.setCapacity(50);

    when(roomRepository.findRoomByFloorNumberAndRoomNumber(aFloorNumber, aRoomNumber)).thenReturn(Optional.of(expectedRoom));

    Room result = roomService.getRoom(aFloorNumber, aRoomNumber);

    assertNotNull(result);
    assertEquals(expectedRoom, result);
  }

  @Test
  public void testGetRoom_NoFloorAndNoRoomNumberSpecified() {
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
      () -> roomService.getRoom(-1, -1));

    assertEquals("Cannot get a specific room with no floor and no room number specified", thrown.getMessage());
  }

  @Test
  public void testGetRoom_NoFloorNumberSpecified() {
    Integer aRoomNumber = 101;

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
      () -> roomService.getRoom(-1, aRoomNumber));

    assertEquals("Cannot get a specific room with no floor number specified", thrown.getMessage());
  }

  @Test
  public void testGetRoom_NoRoomNumberSpecified() {
    Integer aFloorNumber = 1;

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
      () -> roomService.getRoom(aFloorNumber, -1));

    assertEquals("Cannot get a specific room with no room number specified", thrown.getMessage());
  }

  @Test
  public void testGetRoom_RoomNotFound() {
    Integer aFloorNumber = 99;
    Integer aRoomNumber = 999;

    when(roomRepository.findRoomByFloorNumberAndRoomNumber(aFloorNumber, aRoomNumber)).thenReturn(Optional.empty());

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
      () -> roomService.getRoom(aFloorNumber, aRoomNumber));

    assertEquals("No room with this floor and room number was found", thrown.getMessage());
  }

  @Test
  public void testDeleteRoom_Success() {
    Integer aFloorNumber = 1;
    Integer aRoomNumber = 101;
    Room mockRoom = new Room();
    mockRoom.setFloorNumber(aFloorNumber);
    mockRoom.setRoomNumber(aRoomNumber);

    when(roomRepository.findRoomByFloorNumberAndRoomNumber(aFloorNumber, aRoomNumber))
      .thenReturn(Optional.of(mockRoom));

    // No exception means success
    assertDoesNotThrow(() -> roomService.deleteRoom(aFloorNumber, aRoomNumber));

    // Verify delete was called on the repository with the correct room
    verify(roomRepository, times(1)).delete(mockRoom);
  }

  @Test
  public void testDeleteRoom_RoomNotFound() {
    Integer aFloorNumber = 99; // Assume this floor number doesn't exist
    Integer aRoomNumber = 999; // Assume this room number doesn't exist

    when(roomRepository.findRoomByFloorNumberAndRoomNumber(aFloorNumber, aRoomNumber))
      .thenReturn(Optional.empty());

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
      () -> roomService.deleteRoom(aFloorNumber, aRoomNumber),
      "Expected deleteRoom to throw an exception for non-existing room");

    assertEquals("There is no room with this floor and room number", thrown.getMessage());
    // Verify delete was never called due to the exception
    verify(roomRepository, never()).delete(any(Room.class));
  }

  @Test
  public void testGetAllRooms_ByRoomNumber() {
    Room commonRoom = new Room();
    commonRoom.setName("Common Room");
    commonRoom.setFloorNumber(1);
    commonRoom.setRoomNumber(101);
    commonRoom.setCapacity(30);
    when(roomRepository.findRoomByRoomNumber(101)).thenReturn(Optional.of(Arrays.asList(commonRoom)));

    List<Room> result = roomService.getAllRooms(-1, 101);

    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
    assertEquals(commonRoom, result.get(0));
  }

  @Test
  public void testGetAllRooms_ByFloorNumber() {
    Room commonRoom = new Room();
    commonRoom.setName("Common Room");
    commonRoom.setFloorNumber(1);
    commonRoom.setRoomNumber(101);
    commonRoom.setCapacity(30);
    when(roomRepository.findRoomByFloorNumber(1)).thenReturn(Optional.of(Arrays.asList(commonRoom)));

    List<Room> result = roomService.getAllRooms(1, -1);

    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
    assertEquals(commonRoom, result.get(0));
  }

  @Test
  public void testGetAllRooms_ByFloorAndRoomNumber() {
    Room commonRoom = new Room();
    commonRoom.setName("Common Room");
    commonRoom.setFloorNumber(1);
    commonRoom.setRoomNumber(101);
    commonRoom.setCapacity(30);
    when(roomRepository.findRoomByFloorNumberAndRoomNumber(1, 101)).thenReturn(Optional.of(commonRoom));
    when(roomRepository.findRoomByFloorNumber(1)).thenReturn(Optional.of(Arrays.asList(commonRoom)));

    List<Room> result = roomService.getAllRooms(1, 101);

    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
    assertEquals(commonRoom, result.get(0));
  }

  @Test
  public void testGetAllRooms_NoFilters() {
    Room commonRoom = new Room();
    commonRoom.setName("Common Room");
    commonRoom.setFloorNumber(1);
    commonRoom.setRoomNumber(101);
    commonRoom.setCapacity(30);
    when(roomRepository.findAll()).thenReturn(Arrays.asList(commonRoom));

    List<Room> result = roomService.getAllRooms(-1, -1);

    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
    assertEquals(commonRoom, result.get(0));
  }

  @Test
  public void testGetAllRooms_NoRoomsFound() {
    when(roomRepository.findRoomByFloorNumber(99)).thenReturn(Optional.empty());

    Exception exception = assertThrows(IllegalArgumentException.class,
      () -> roomService.getAllRooms(99, -1));

    assertEquals("There are no rooms with this floor number", exception.getMessage());
  }
}
