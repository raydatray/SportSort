package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.AuthenticationCO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.AuthenticationDto;
import ca.mcgill.ecse321.sportsregistrationw24.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class AuthenticationRestController {
  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping(value = {
          "/login",
          "/login/"
  })
  public ResponseEntity<?> login(@RequestBody AuthenticationCO authenticationCO) {
    try {
      AuthenticationDto generatedToken = authenticationService.login(authenticationCO.getEmail(), authenticationCO.getPassword());
      return ResponseEntity.ok().body(generatedToken);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping(value = {
          "/logout",
          "/logout/"
  })
  public ResponseEntity<?> logout(@RequestHeader String token) {
    try {
      authenticationService.logout(token);
      return ResponseEntity.ok().body("Logout Successful");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
