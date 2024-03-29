package ca.mcgill.ecse321.sportsregistrationw24.dto;

import ca.mcgill.ecse321.sportsregistrationw24.model.Registration;

public class RegistrationIdDto  {
  private Integer customerAccountId;
  private Integer courseOfferingId;

  public RegistrationIdDto() {}

  public RegistrationIdDto (Integer customerAccountId, Integer courseOfferingId) {
    this.customerAccountId = customerAccountId;
    this.courseOfferingId = courseOfferingId;
  }

  public RegistrationIdDto (Registration aRegistration) {
    this(aRegistration.getId().getCustomerAccountId(), aRegistration.getId().getCourseOfferingId());
  }

  public Integer getCustomerAccountId() { return customerAccountId; }
  public Integer getCourseOfferingId() { return courseOfferingId; }
}

