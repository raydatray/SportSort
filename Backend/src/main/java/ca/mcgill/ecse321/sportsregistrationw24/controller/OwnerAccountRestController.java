package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.OwnerAccountSafeDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.OwnerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import ca.mcgill.ecse321.sportsregistrationw24.dto.UserAccountSafeDto;
import ca.mcgill.ecse321.sportsregistrationw24.service.OwnerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class OwnerAccountRestController {

  @Autowired
  private OwnerAccountService service;

  @GetMapping(value = {
    "/ownerAccount/get",
    "/ownerAccount/get/"
  })
  public ResponseEntity<?> getOwnerAccountByEmail(@RequestParam String email) {
    try {
      OwnerAccount ownerAccount = service.getOwnerAccountByEmail(email);
      return ResponseEntity.ok().body(convertToSafeDto(ownerAccount));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping(value = {
    "/ownerAccount/updateEmail",
    "/ownerAccount/updateEmail/"
  })
  public ResponseEntity<?> updateOwnerEmail(@RequestParam String newEmail, @RequestHeader String token) {
    try {
      service.updateOwnerEmail(newEmail, token);
      return ResponseEntity.ok().body("Owner account updated successfully!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping(value = {
    "/ownerAccount/updatePassword",
    "/ownerAccount/updatePassword/"
  })
  public ResponseEntity<?> updateOwnerPassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestHeader String token) {
    try {
      service.updateOwnerPassword(newPassword, oldPassword, token);
      return ResponseEntity.ok().body("Password updated successfully.");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {
    "/userAccount/getAll",
    "/userAccount/getAll/"
  })
  public ResponseEntity<?> getAllUserAccounts() {
    try {
      List<UserAccountSafeDto> userAccountDtos = new ArrayList<>();
      for (UserAccount userAccount : service.getAllUserAccounts()) {
        UserAccountSafeDto dto = convertToDto(userAccount);
        userAccountDtos.add(dto);
      }
      return ResponseEntity.ok().body(userAccountDtos);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  private OwnerAccountSafeDto convertToSafeDto(OwnerAccount ownerAccount) {
    if (ownerAccount == null) {
      throw new IllegalArgumentException("There is no such owner account!");
    }
    return new OwnerAccountSafeDto(ownerAccount.getEmail(), ownerAccount.getName());
  }

  private UserAccountSafeDto convertToDto(UserAccount userAccount) {
    if (userAccount == null) {
      throw new IllegalArgumentException("There is no such user account!");
    }
    return new UserAccountSafeDto(userAccount.getEmail(),
      userAccount.getName());
  }
}
