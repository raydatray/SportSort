package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.SportsCenterDTO;
import ca.mcgill.ecse321.sportsregistrationw24.service.SportCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class SportCenterRestController {
  @Autowired
  private SportCenterService service;

  @PutMapping(value = {"/updateSportCenter"})
  public ResponseEntity<?> updateSportsCenter(@RequestHeader String userToken, @RequestBody SportsCenterDTO sportsCenterDTO) {
    try {
      SportsCenterDTO sportCenterDTO = service.updateSportsCenter(userToken, sportsCenterDTO.getName(), sportsCenterDTO.getAddress(), sportsCenterDTO.getPhoneNumber(), sportsCenterDTO.getOpeningHour(), sportsCenterDTO.getClosingHour());
      return ResponseEntity.accepted().body(sportCenterDTO);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {"/getSportCenter"})
  public ResponseEntity<?> getSportsCenter() {
    try {
      return ResponseEntity.ok().body(new SportsCenterDTO(service.getSportCenter()));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
