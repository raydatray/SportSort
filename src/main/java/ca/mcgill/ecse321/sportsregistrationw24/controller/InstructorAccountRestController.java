package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.InstructorAccountDto;
import ca.mcgill.ecse321.sportsregistrationw24.dto.InstructorAccountSafeDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.service.InstructorAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class InstructorAccountRestController {

  @Autowired
  private InstructorAccountService service;

  @PostMapping(value = {
    "/instructorAccounts/create",
    "/instructorAccounts/create/"
  })
  public ResponseEntity<?> createInstructorAccount(@RequestBody InstructorAccountDto instructorAccountDto) {
    try {
      String email = instructorAccountDto.getEmail();
      String password = instructorAccountDto.getPassword();
      String name = instructorAccountDto.getName();
      service.createInstructorAccount(email, password, name);
      return ResponseEntity.ok().body("Instructor account created successfully!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {
    "/instructorAccounts/get",
    "/instructorAccounts/get/"
  })
  public ResponseEntity<?> getInstructorAccountByEmail(@RequestParam String email) {
    try {
      InstructorAccount instructorAccount = service.getInstructorAccountByEmail(email);
      return ResponseEntity.ok().body(convertToSafeDto(instructorAccount));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {
    "/instructorAccounts/getAll",
    "/instructorAccounts/getAll/"
  })
  public ResponseEntity<?> getAllInstructorAccounts() {
    try {
      List<InstructorAccountSafeDto> instructorAccountDtos = new ArrayList<>();
      for (InstructorAccount instructorAccount : service.getAllInstructorAccounts()) {
        InstructorAccountSafeDto dto = convertToSafeDto(instructorAccount);
        instructorAccountDtos.add(dto);
      }
      return ResponseEntity.ok().body(instructorAccountDtos);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping(value = {
    "/instructorAccounts/updateEmail",
    "/instructorAccounts/updateEmail/"
  })
  public ResponseEntity<?> updateInstructorEmail(@RequestParam String oldEmail, @RequestParam String newEmail) {
    try {
      service.updateInstructorEmail(oldEmail, newEmail);
      return ResponseEntity.ok().body("Instructor account updated successfully!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping(value = {
    "/instructorAccounts/updatePassword",
    "/instructorAccounts/updatePassword/"
  })
  public ResponseEntity<?> updateInstructorPassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestHeader String token) {
    try {
      service.updateInstructorPassword(newPassword, oldPassword, token);
      return ResponseEntity.ok().body("Password updated successfully!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping(value = {
    "/instructorAccounts/deleteByEmail",
    "/instructorAccounts/deleteByEmail/"
  })
  public ResponseEntity<?> deleteInstructorAccountByEmail(@RequestParam String email) {
    try {
      service.deleteInstructorAccountByEmail(email);
      return ResponseEntity.ok().body("Instructor account deleted successfully!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  private InstructorAccountSafeDto convertToSafeDto(InstructorAccount instructorAccount) {
    if (instructorAccount == null) {
      throw new IllegalArgumentException("There is no such instructor account!");
    }
    return new InstructorAccountSafeDto(instructorAccount.getEmail(), instructorAccount.getName());
  }

}
