package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.Authentication.AuthenticationCO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.Authentication.AuthenticationDTO;
import ca.mcgill.ecse321.sportsregistrationw24.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class AuthenticationRestController {
  @Autowired
  private AuthenticationService service;

  @PostMapping(value = {"/login"})
  public ResponseEntity<?> login(@RequestBody AuthenticationCO authenticationCO) {
    try {
      AuthenticationDTO generatedToken = service.login(authenticationCO.getEmail(), authenticationCO.getPassword());
      return ResponseEntity.accepted().body(generatedToken);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping(value = {"/logout"})
  public ResponseEntity<?> logout(@RequestHeader String token) {
    try {
      service.logout(token);
      return ResponseEntity.accepted().body("Logout successful");
    } catch (Exception e){
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
