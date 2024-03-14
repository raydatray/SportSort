package ca.mcgill.ecse321.sportsregistrationw24.dto;

import java.sql.Date;

public class RegistrationCO {
    Integer courseOfferingId;
    Integer customerAccountId;
    Integer paymentInfoId;
    Date registrationDate;

    public RegistrationCO(Integer courseOfferingId, Integer customerAccountId,
                          Integer paymentInfoId, Date registrationDate) {
        this.courseOfferingId = courseOfferingId;
        this.customerAccountId = customerAccountId;
        this.paymentInfoId = paymentInfoId;
        this.registrationDate = registrationDate;
    }

    public Integer getCourseOfferingId() { return this.courseOfferingId; }
    public Integer getCustomerAccountId() { return this.customerAccountId; }
    public Integer getPaymentInfoId() { return this.paymentInfoId; }
    public Date getRegistrationDate() {
        return this.registrationDate;
    }
}
