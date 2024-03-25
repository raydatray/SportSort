package ca.mcgill.ecse321.sportsregistrationw24.integration;

import ca.mcgill.ecse321.sportsregistrationw24.dao.RoomRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import ca.mcgill.ecse321.sportsregistrationw24.dto.RoomDto;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RoomIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  private RoomRepository roomRepo;

  @BeforeEach
  @AfterEach
  public void clearDatabase() {
    roomRepo.deleteAll();
  }

  @Test
  public void testCreateRoom() {
    RoomDto roomDto = new RoomDto("Conference Room", 3, 305, 20);
    ResponseEntity<RoomDto> responseEntity = restTemplate.postForEntity("/room/create", roomDto, RoomDto.class);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Response should be OK");
    assertNotNull(responseEntity.getBody(), "Response body should not be null");
    assertEquals(roomDto.getName(), responseEntity.getBody().getName(), "Name should match");
    assertEquals(roomDto.getFloorNumber(), responseEntity.getBody().getFloorNumber(), "Floor number should match");
    assertEquals(roomDto.getRoomNumber(), responseEntity.getBody().getRoomNumber(), "Room number should match");
    assertEquals(roomDto.getCapacity(), responseEntity.getBody().getCapacity(), "Capacity should match");
  }

  @Test
  public void testUpdateRoom() {
    // Assume this RoomDto matches an existing room in your database
    RoomDto originalRoomDto = new RoomDto("Initial Room", 1, 101, 10);
    // First, create the room or make sure it exists in the database
    restTemplate.postForEntity("/room/create", originalRoomDto, RoomDto.class);

    // RoomDto with updated information
    RoomDto updatedRoomDto = new RoomDto("Updated Room", 1, 101, 20);

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<RoomDto> requestUpdate = new HttpEntity<>(updatedRoomDto, headers);

    // Perform the update operation
    ResponseEntity<RoomDto> responseEntity = restTemplate.exchange("/room/update", HttpMethod.PUT, requestUpdate, RoomDto.class);

    // Assertions to verify the update operation was successful
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Response should be OK");
    RoomDto responseBody = responseEntity.getBody();
    assertNotNull(responseBody, "Response body should not be null");
    assertEquals("Updated Room", responseBody.getName(), "Room name should be updated");
    assertEquals(1, responseBody.getFloorNumber(), "Floor number should match");
    assertEquals(101, responseBody.getRoomNumber(), "Room number should match");
    assertEquals(20, responseBody.getCapacity(), "Room capacity should be updated");
  }

  @Test
  public void testGetRoom() {
    // First, create a room to ensure the database is not empty
    RoomDto roomDto = new RoomDto("Library", 1, 101, 50);
    ResponseEntity<RoomDto> createResponse = restTemplate.postForEntity("/room/create", roomDto, RoomDto.class);
    assertEquals(HttpStatus.OK, createResponse.getStatusCode(), "Room should be created successfully");

    // Now, test the getRoom functionality
    ResponseEntity<RoomDto> responseEntity = restTemplate.getForEntity("/room/get?aFloorNumber=1&aRoomNumber=101", RoomDto.class);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Response should be OK");
    assertNotNull(responseEntity.getBody(), "Response body should not be null");
    assertEquals(roomDto.getName(), responseEntity.getBody().getName(), "Name should match");
    assertEquals(roomDto.getFloorNumber(), responseEntity.getBody().getFloorNumber(), "Floor number should match");
    assertEquals(roomDto.getRoomNumber(), responseEntity.getBody().getRoomNumber(), "Room number should match");
    assertEquals(roomDto.getCapacity(), responseEntity.getBody().getCapacity(), "Capacity should match");
  }

  @Test
  public void testDeleteRoom() {
    // Assume this RoomDto corresponds to an existing room in the database
    RoomDto roomDto = new RoomDto("Meeting Room", 2, 202, 15);
    // Optionally, create the room first if it doesn't already exist

    // Set up the request
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<RoomDto> entity = new HttpEntity<>(roomDto, headers);

    // Perform the DELETE operation
    ResponseEntity<String> responseEntity = restTemplate.exchange("/room/delete", HttpMethod.DELETE, entity, String.class);

    // Assertions to verify the deletion was successful
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Response should be OK");
    assertTrue(responseEntity.getBody().contains("successfully deleted"), "Response should confirm successful deletion");
  }
}
