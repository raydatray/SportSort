package ca.mcgill.ecse321.sportsregistrationw24.dto;

public class RegistrationIdDto  {
  private Integer customerAccountId;
  private Integer courseOfferingId;

  public RegistrationIdDto() {}

  public RegistrationIdDto (Integer customerAccountId, Integer courseOfferingId) {
    this.customerAccountId = customerAccountId;
    this.courseOfferingId = courseOfferingId;
  }

  public Integer getCustomerAccountId() { return customerAccountId; }
  public Integer getCourseOfferingId() { return courseOfferingId; }
}

