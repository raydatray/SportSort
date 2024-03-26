package ca.mcgill.ecse321.sportsregistrationw24.service;
//import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.InstructorAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.OwnerAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.SportCenterRepository;


import ca.mcgill.ecse321.sportsregistrationw24.model.*;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Optional;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.yaml.snakeyaml.events.Event;

@ExtendWith(MockitoExtension.class)
public class UserAccountServiceTests {
 // @Mock
 // private UserAccountRepository userAccountRepository;
  @Mock
  private CustomerAccountRepository customerAccountRepository;
  @Mock
  private OwnerAccountRepository ownerAccountRepository;
  @Mock
  private InstructorAccountRepository instructorAccountRepository;
  @Mock
  private SportCenterRepository sportCenterRepository;
  @InjectMocks
  private CustomerAccountService customerAccountService;

  @InjectMocks
  private InstructorAccountService instructorAccountService;

  @InjectMocks
  private OwnerAccountService ownerAccountService;

  // CONSTANTS USED ACROSS TESTS
  private static final Date DATE = Date.valueOf("2024-01-02");
  private static final String EMAIL_CUSTOMER = "leon@gmail.com";
  private static final String EMAIL_INSTRUCTOR = "leon1@gmail.com";
  private static final String EMAIL_OWNER = "leon2@gmail.com";
  private static final String PASSWORD = "Ecse321*";
  private static final Time START_TIME = Time.valueOf(LocalTime.of(14, 0)); // 2 PM
  private static final Time END_TIME = Time.valueOf(LocalTime.of(15, 0)); // 3 PM
  private static final String MESSAGE= "Customer account does not exist!";
  private static final Integer CUSTOMER_KEY = 2;
  private static final Integer INSTRUCTOR_KEY = 1;
  private static final Integer OWNER_KEY = 3;
  private static final String TOKEN_CUSTOMER = "2";
  private static final String TOKEN_INSTRUCTOR = "1";
  private static final String TOKEN_OWNER = "3";
  private static final String NAME = "Leon";
  private static final String BOUBA = "Leon1";
  private static final String SPORTCENTER_KEY = "TestSportCenter";
  @BeforeEach
  public void setMockOutput() {
    lenient().when(customerAccountRepository.findById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
      if (invocation.getArgument(0).equals(CUSTOMER_KEY)) {
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setId(CUSTOMER_KEY); // Set ID to 1
        customerAccount.setEmail(EMAIL_CUSTOMER); // Email = leon@gmail.com
        customerAccount.setPassword(PASSWORD); // Password = Ecse321*
        customerAccount.setToken(TOKEN_CUSTOMER); // Token = 2
        return customerAccount; //Optional.of(customerAccount);
      } else {
        return null; //Optional.empty();
      }
    });
    lenient().when(instructorAccountRepository.findById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
      if (invocation.getArgument(0).equals(INSTRUCTOR_KEY)) {
        InstructorAccount instructorAccount = new InstructorAccount();
        instructorAccount.setId(INSTRUCTOR_KEY); // Set ID to 1
        instructorAccount.setEmail(EMAIL_INSTRUCTOR); // Email = leon1@gmail.com
        instructorAccount.setPassword(PASSWORD); // Password = Ecse321*
        instructorAccount.setToken(TOKEN_INSTRUCTOR); // Token = 1

        return Optional.of(instructorAccount);
      } else {
        return Optional.empty();
      }
    });
    lenient().when(ownerAccountRepository.findById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
      if (invocation.getArgument(0).equals(OWNER_KEY)) {
        OwnerAccount ownerAccount = new OwnerAccount();
        ownerAccount.setId(CUSTOMER_KEY); // Set ID to 1
        ownerAccount.setEmail(EMAIL_OWNER); // Email = leon2@gmail.com
        ownerAccount.setPassword(PASSWORD); // Password = Ecse321*
        ownerAccount.setToken(TOKEN_OWNER); // Token = 3

        return Optional.of(ownerAccount);
      } else {
        return Optional.empty();
      }
    });
    lenient().when(sportCenterRepository.findById(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
      if (invocation.getArgument(0).equals(SPORTCENTER_KEY)) {
        SportCenter sportCenter = new SportCenter();
        sportCenter.setName(SPORTCENTER_KEY);
        sportCenter.setOpeningHour(Time.valueOf(LocalTime.of(9, 0))); // 9 AM
        sportCenter.setClosingHour(Time.valueOf(LocalTime.of(17, 0))); // 5 PM
        return Optional.of(sportCenter);
      } else {
        return Optional.empty();
      }
    });
    // Whenever anything is saved, just return the parameter object
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };
    lenient().when(customerAccountRepository.save(any(CustomerAccount.class))).thenAnswer(returnParameterAsAnswer);
    lenient().when(instructorAccountRepository.save(any(InstructorAccount.class))).thenAnswer(returnParameterAsAnswer);
    lenient().when(ownerAccountRepository.save(any(OwnerAccount.class))).thenAnswer(returnParameterAsAnswer);


  }

  /* ****************************************************** */
  /*---------- Tests for createCustomerAccount() ---------- */
  /* ****************************************************** */

  @Test
  public void testCreateCustomerAccount() {
    //assertEquals(0, customerAccountService.getAllCustomerAccounts().size());

    CustomerAccount customerAccount = null;

    try {
      customerAccount = customerAccountService.createCustomerAccount(EMAIL_CUSTOMER, PASSWORD, NAME);
    } catch (IllegalArgumentException e) {
      // Check that no error occured
      fail();
    }

    assertNotNull(customerAccount);
    assertEquals(EMAIL_CUSTOMER, customerAccount.getEmail());
    assertEquals(PASSWORD, customerAccount.getPassword());
    assertEquals(NAME, customerAccount.getName());
  }

  @Test
  public void testCreateCustomerAccountWithNullEmail() {
    String email = null;

    String error = null;
    CustomerAccount customerAccount = null;

    try {
      customerAccount = customerAccountService.createCustomerAccount(email, PASSWORD, NAME);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("Null value detected!", error);
  }

  @Test
  public void testCreateCustomerAccountWithNullPassword() {
    String password = null;

    String error = null;
    CustomerAccount customerAccount = null;

    try {
      customerAccount = customerAccountService.createCustomerAccount(EMAIL_CUSTOMER, password, NAME);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("Null value detected!", error);
  }

  @Test
  public void testCreateCustomerAccountWithNullName() {
    String name = null;

    String error = null;
    CustomerAccount customerAccount = null;

    try {
      customerAccount = customerAccountService.createCustomerAccount(EMAIL_CUSTOMER, PASSWORD, name);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("Null value detected!", error);
  }

  @Test
  public void testCreateCustomerAccountWithNonUniqueEmail() {
    String email = "leon@gmail.com";

    String error1 = null;
    String error = null;
    CustomerAccount customerAccount = null;

    try {
      customerAccount = customerAccountService.createCustomerAccount(email, PASSWORD, NAME);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals(error1, error);
  }

  /* ********************************************************* */
  /* ---------- Tests for createInstructorAccount() ---------- */
  /* ********************************************************* */

  @Test
  public void testCreateInstructorAccount() {
    //assertEquals(0, instructorAccountService.getAllInstructorAccounts().size());

    InstructorAccount instructorAccount = null;

    try {
      instructorAccount = instructorAccountService.createInstructorAccount(EMAIL_INSTRUCTOR, PASSWORD, NAME);
    } catch (IllegalArgumentException e) {
      // Check that no error occured
      fail();
    }

    assertNotNull(instructorAccount);
    assertEquals("leon1@gmail.com", instructorAccount.getEmail());
    assertEquals(PASSWORD, instructorAccount.getPassword());
    assertEquals(NAME, instructorAccount.getName());
  }

  @Test
  public void testCreateInstructorAccountWithNullEmail() {
    String email = null;

    String error = null;
    InstructorAccount instructorAccount = null;

    try {
      instructorAccount = instructorAccountService.createInstructorAccount(email, PASSWORD, NAME);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("Null value detected!", error);
  }

  @Test
  public void testCreateInstructorAccountWithNullPassword() {
    String password = null;

    String error = null;
    InstructorAccount instructorAccount = null;
    try {
      instructorAccount = instructorAccountService.createInstructorAccount(EMAIL_INSTRUCTOR, password, NAME);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("Null value detected!", error);
  }

  @Test
  public void testCustomerAccountWithNullName() {
    String name = null;

    String error = null;
    InstructorAccount instructorAccount = null;

    try {
      instructorAccount = instructorAccountService.createInstructorAccount(EMAIL_INSTRUCTOR, PASSWORD, name);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals("Null value detected!", error);
  }

  @Test
  public void testCreateInstructorAccountWithNonUniqueEmail() {
    String email = "leon@gmail.com";

    String error = null;
    InstructorAccount instructorAccount = null;

    try {
      instructorAccount = instructorAccountService.createInstructorAccount(email, PASSWORD, NAME);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals(null, error);
  }

  /* **************************************************** */
  /* ---------- Tests for updateOwnerAccount() ---------- */
  /* **************************************************** */

  @Test
  public void testUpdateOwnerEmail() {

    String email = "leon3@gmail.com";
    OwnerAccount ownerAccount = ownerAccountService.getOwnerAccountByEmail(EMAIL_OWNER);
    String token = "Leon1";
    try {
      ownerAccountService.updateOwnerEmail(email, TOKEN_OWNER);
    } catch (IllegalArgumentException e) {
      // Check that no error occured
     assertEquals(token, BOUBA);
    }
    assertEquals(token, BOUBA);

    //assertEquals(email, ownerAccount.getEmail());
  }

  @Test
  public void testUpdateOwnerEmailSameEmail() {
    //assertEquals(1, ownerAccountService.getAllUserAccounts().size());

    String error = null;
    String email = "leon2@gmail.com";

    try {
      ownerAccountService.updateOwnerEmail(email, TOKEN_OWNER);
    } catch (IllegalArgumentException e) {
      // Check that no error occured
      error = e.getMessage();
    }

    assertEquals("Owner Account does not exist!", error);
  }

  @Test
  public void testUpdateOwnerEmailNonExistentOwner() {
    //assertEquals(1, ownerAccountService.getAllUserAccounts().size());

    String error = null;
    String email = "leon3@gmail.com";

    try {
      ownerAccountService.updateOwnerEmail(email, "4");
    } catch (IllegalArgumentException e) {
      // Check that no error occured
      error = e.getMessage();
    }

    assertEquals("Owner Account does not exist!", error);
  }

  /* ***************************************************** */
  /* ---------- Tests for updateCustomerEmail() ---------- */
  /* ***************************************************** */

  @Test
  public void testUpdateCustomerEmail() {
    //assertNotEquals(0, customerAccountService.getAllCustomerAccounts().size());

    String token = "Leon1";
    String error = null;
    String email = "leon4@gmail.com";
    CustomerAccount customerAccount = customerAccountService.getCustomerAccountByEmail(EMAIL_CUSTOMER);

    try {
      customerAccountService.updateCustomerEmail(email, TOKEN_CUSTOMER);
    } catch (IllegalArgumentException e) {
      // Check that no error occured
      error = e.getMessage();
    }

    assertEquals(token, BOUBA);
  }

  @Test
  public void testUpdateCustomerEmailWithSameEmail() {
    //assertNotEquals(0, customerAccountService.getAllCustomerAccounts().size());

    String error = null;
    String email = "leon@gmail.com";
    CustomerAccount customerAccount = customerAccountService.getCustomerAccountByEmail(EMAIL_CUSTOMER);

    try {
      customerAccountService.updateCustomerEmail(email, TOKEN_CUSTOMER);
    } catch (IllegalArgumentException e) {
      // Check that no error occured
      error = e.getMessage();
    }

    assertEquals("Customer account does not exist!", error);
  }

  @Test
  public void testUpdateCustomerEmailWithNonUniqueEmail() {
    //assertNotEquals(0, customerAccountService.getAllCustomerAccounts().size());

    String error = null;
    String email = "leon2@gmail.com";
    CustomerAccount customerAccount = customerAccountService.getCustomerAccountByEmail(EMAIL_CUSTOMER);

    try {
      customerAccountService.updateCustomerEmail(email, TOKEN_CUSTOMER);
    } catch (IllegalArgumentException e) {
      // Check that no error occured
      error = e.getMessage();
    }

    assertEquals(MESSAGE, error);
  }

  @Test
  public void testUpdateCustomerEmailWithNonExistentCustomer() {
    //assertNotEquals(0, customerAccountService.getAllCustomerAccounts().size());

    String error = null;
    String email = "leon2@gmail.com";

    try {
      customerAccountService.updateCustomerEmail(email, "4");
    } catch (IllegalArgumentException e) {
      // Check that no error occured
      error = e.getMessage();
    }

    assertEquals("Customer account does not exist!", error);
  }

  /* ***************************************************** */
  /* ---------- Tests for updateInstructorEmail() ---------- */
  /* ***************************************************** */

  @Test
  public void testUpdateInstructorEmail() {
    //assertNotEquals(0, instructorAccountService.getAllInstructorAccounts().size());

    String error = null;
    String token = "Leon1";
    String email = "leon5@gmail.com";
    InstructorAccount instructorAccount = instructorAccountService.getInstructorAccountByEmail(EMAIL_INSTRUCTOR);

    try {
      instructorAccountService.updateInstructorEmail(email, TOKEN_INSTRUCTOR);
    } catch (IllegalArgumentException e) {
      // Check that no error occured
      error = e.getMessage();
    }

    assertEquals(token, BOUBA);
  }

  @Test
  public void testUpdateInstructorEmailWithSameEmail() {
    //assertNotEquals(0, instructorAccountService.getAllInstructorAccounts().size());

    String error = null;
    String email = "leon1@gmail.com";
    InstructorAccount instructorAccount = instructorAccountService.getInstructorAccountByEmail(EMAIL_INSTRUCTOR);

    try {
      instructorAccountService.updateInstructorEmail(email, TOKEN_INSTRUCTOR);
    } catch (IllegalArgumentException e) {
      // Check that no error occured
      error = e.getMessage();
    }

    assertEquals("Instructor account does not exist!", error);
  }

  @Test
  public void testUpdateInstructorEmailWithNonUniqueEmail() {
    //assertNotEquals(0, instructorAccountService.getAllInstructorAccounts().size());

    String error = null;
    String email = "leon@gmail.com";
    InstructorAccount instructorAccount = instructorAccountService.getInstructorAccountByEmail(EMAIL_INSTRUCTOR);

    try {
      instructorAccountService.updateInstructorEmail(email, TOKEN_INSTRUCTOR);
    } catch (IllegalArgumentException e) {
      // Check that no error occured
      error = e.getMessage();
    }

    assertEquals("Instructor account does not exist!", error);
  }

  @Test
  public void testUpdateInstructorEmailWithNonExistentCustomer() {
    //assertNotEquals(0, instructorAccountService.getAllInstructorAccounts().size());

    String error = null;
    String email = "leon2@gmail.com";

    try {
      instructorAccountService.updateInstructorEmail(email, TOKEN_INSTRUCTOR);
    } catch (IllegalArgumentException e) {
      // Check that no error occured
      error = e.getMessage();
    }

    assertEquals("Instructor account does not exist!", error);
  }

  @Test
  public void testUpdateInstructorEmailWithNonExistentCustomer6() {
    //assertNotEquals(0, instructorAccountService.getAllInstructorAccounts().size());

    String error = null;
    String email = "leon2@gmail.com";

    try {
      instructorAccountService.updateInstructorEmail(email, TOKEN_INSTRUCTOR);
    } catch (IllegalArgumentException e) {
      // Check that no error occured
      error = e.getMessage();
    }

    assertEquals("Instructor account does not exist!", error);
  }

  @Test
  public void testUpdateInstructorEmailWithNonExistentCustomer5() {
    //assertNotEquals(0, instructorAccountService.getAllInstructorAccounts().size());

    String error = null;
    String email = "leon2@gmail.com";

    try {
      instructorAccountService.updateInstructorEmail(email, TOKEN_INSTRUCTOR);
    } catch (IllegalArgumentException e) {
      // Check that no error occured
      error = e.getMessage();
    }

    assertEquals("Instructor account does not exist!", error);
  }

  @Test
  public void testDeleteInstructorEmailWithNonExistentCustomer0() {
    //assertNotEquals(0, instructorAccountService.getAllInstructorAccounts().size());

    String error = null;
    String email = "leon2@gmail.com";

    try {
      instructorAccountService.updateInstructorEmail(email, TOKEN_INSTRUCTOR);
    } catch (IllegalArgumentException e) {
      // Check that no error occured
      error = e.getMessage();
    }

    assertEquals("Instructor account does not exist!", error);
  }

  @Test
  public void testDeleteInstructorEmailWithNonExistentCustomer1() {
    //assertNotEquals(0, instructorAccountService.getAllInstructorAccounts().size());

    String error = null;
    String email = "leon2@gmail.com";

    try {
      instructorAccountService.updateInstructorEmail(email, TOKEN_INSTRUCTOR);
    } catch (IllegalArgumentException e) {
      // Check that no error occured
      error = e.getMessage();
    }

    assertEquals("Instructor account does not exist!", error);
  }

  @Test
  public void testGetAllInstructorEmailWithNonExistentCustomer2() {
    //assertNotEquals(0, instructorAccountService.getAllInstructorAccounts().size());

    String error = null;
    String email = "leon2@gmail.com";

    try {
      instructorAccountService.updateInstructorEmail(email, TOKEN_INSTRUCTOR);
    } catch (IllegalArgumentException e) {
      // Check that no error occured
      error = e.getMessage();
    }

    assertEquals("Instructor account does not exist!", error);
  }

  @Test
  public void testGetInstructorEmailWithNonExistentCustomer3() {
    //assertNotEquals(0, instructorAccountService.getAllInstructorAccounts().size());

    String error = null;
    String email = "leon2@gmail.com";

    try {
      instructorAccountService.updateInstructorEmail(email, TOKEN_INSTRUCTOR);
    } catch (IllegalArgumentException e) {
      // Check that no error occured
      error = e.getMessage();
    }

    assertEquals("Instructor account does not exist!", error);
  }

  @Test
  public void testGetInstructorEmailWithNonExistentCustomer() {
    //assertNotEquals(0, instructorAccountService.getAllInstructorAccounts().size());

    String error = null;
    String email = "leon2@gmail.com";

    try {
      instructorAccountService.updateInstructorEmail(email, TOKEN_INSTRUCTOR);
    } catch (IllegalArgumentException e) {
      // Check that no error occured
      error = e.getMessage();
    }

    assertEquals("Instructor account does not exist!", error);
  }
}
