package ca.mcgill.ecse321.sportsregistrationw24.dto.Registration;

import ca.mcgill.ecse321.sportsregistrationw24.model.Registration;

import java.sql.Date;

public class RegistrationStaffDTO {
  private String customerEmail;
  private Integer courseOfferingId;
  private Date registrationDate;
  private Integer pricePaid;

  public RegistrationStaffDTO() {
  }

  public RegistrationStaffDTO(String customerEmail, Integer courseOfferingId, Date registrationDate, Integer pricePaid) {
    this.customerEmail = customerEmail;
    this.courseOfferingId = courseOfferingId;
    this.registrationDate = registrationDate;
    this.pricePaid = pricePaid;
  }

  public RegistrationStaffDTO(Registration registration) {
    this(registration.getCustomerAccount().getEmail(), registration.getCourseOffering().getId(), registration.getRegisteredDate(), registration.getPricePaid());
  }

  public String getCustomerEmail() {
    return this.customerEmail;
  }

  public Integer getCourseOfferingId() {
    return this.courseOfferingId;
  }

  public Date getRegistrationDate() {
    return this.registrationDate;
  }

  public Integer getPricePaid() {
    return this.pricePaid;
  }

}
