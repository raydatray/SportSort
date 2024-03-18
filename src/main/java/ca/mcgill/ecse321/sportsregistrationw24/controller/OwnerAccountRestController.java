package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.OwnerAccountDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import ca.mcgill.ecse321.sportsregistrationw24.dto.UserAccountDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.OwnerAccount;
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

  @PutMapping(value = {
    "/ownerAccounts/updateEmail",
    "/ownerAccounts/updateEmail/"
  })
  public ResponseEntity<?> updateOwnerEmail(@RequestBody OwnerAccountDto ownerAccountDto, @RequestParam String newEmail) {
    try {
      String oldEmail = ownerAccountDto.getEmail();
      service.updateOwnerEmail(oldEmail, newEmail);
      return ResponseEntity.ok().body("Owner account updated successfully.");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping(value = {
    "/ownerAccounts/updatePassword",
    "/ownerAccounts/updatePassword/"
  })
  public ResponseEntity<?> updateOwnerPassword(@RequestBody OwnerAccountDto ownerAccountDto, String newPassword) {
    try {
      OwnerAccount ownerAccount = service.getOwnerAccount(ownerAccountDto.getEmail());
      String oldPassword = ownerAccountDto.getPassword();
      service.updateOwnerPassword(ownerAccount, newPassword, oldPassword);
      return ResponseEntity.ok().body("Password updated successfully.");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {
    "/userAccounts/getAll",
    "/userAccounts/getAll/"
  })
  public ResponseEntity<?> getAllUserAccounts() {
    try {
      List<UserAccountDto> userAccountDtos = new ArrayList<>();
      for (UserAccount userAccount : service.getAllUserAccounts()) {
        UserAccountDto dto = convertToDto(userAccount);
        userAccountDtos.add(dto);
      }
      return ResponseEntity.ok().body(userAccountDtos);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  private UserAccountDto convertToDto(UserAccount userAccount) {
    if (userAccount == null) {
      throw new IllegalArgumentException("There is no such user account!");
    }
    return new UserAccountDto(userAccount.getEmail(),
      userAccount.getPassword(), userAccount.getName());
  }
}
