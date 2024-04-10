package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.UserAccounts.InstructorDTO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.UserAccounts.UserAccountCO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.UserAccounts.UserAccountDTO;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import ca.mcgill.ecse321.sportsregistrationw24.service.UserAccountService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class UserAccountRestController {
  @Autowired
  private UserAccountService service;

  @PostMapping(value = {"/accounts/createCustomer"})
  public ResponseEntity<?> createCustomerAccount(@RequestBody UserAccountCO userAccountCO) {
    try {
      String name = userAccountCO.getName();
      String email = userAccountCO.getEmail();
      String password = userAccountCO.getPassword();
      service.createCustomerAccount(name, email, password);
      return ResponseEntity.status(HttpStatus.CREATED).body("Customer account created successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping(value = {"/accounts/createInstructor"})
  public ResponseEntity<?> createInstructorAccount(@RequestHeader String userToken, @RequestBody UserAccountCO userAccountCO) {
    try {
      String name = userAccountCO.getName();
      String email = userAccountCO.getEmail();
      String password = userAccountCO.getPassword();
      service.createInstructorAccount(userToken, name, email,password);
      return ResponseEntity.status(HttpStatus.CREATED).body("Instructor account created successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping(value = {"/accounts/update"})
  public ResponseEntity<?> updateAccount(@RequestHeader String userToken, @RequestBody UserAccountCO userAccountCO) {
    try {
      String name = userAccountCO.getName();
      String email = userAccountCO.getEmail();
      String password = userAccountCO.getPassword();
      service.updateUserAccount(userToken, name, email, password);
      return ResponseEntity.ok().body("Account updated successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {"/accounts/getAll"})
  public ResponseEntity<?> getAllAccounts(@RequestHeader String userToken, @RequestParam(required = false) List<String> userTypes)   {
    try {
      List<UserAccount> userAccounts = service.getAllUsers(userToken, userTypes);
      List<UserAccountDTO> userAccountDTOs = userAccounts.stream().map(UserAccountDTO::new).toList(); //wtf?

      return ResponseEntity.ok().body(userAccountDTOs);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {"/accounts/getInstructors"})
  public ResponseEntity<?> getInstructors(){
    try{
      List<UserAccount> userAccounts = service.getAllInstructors();
      List<InstructorDTO> instructorDTOs = userAccounts.stream().map(InstructorDTO::new).toList();

      return ResponseEntity.ok().body(instructorDTOs);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping(value = {"/accounts/delete"})
  public ResponseEntity<?> deleteAccount(@RequestHeader String userToken, @RequestParam String email) {
    try {
      service.deleteUserAccount(userToken, email);
      return ResponseEntity.ok().body("Account deleted successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
