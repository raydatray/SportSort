package ca.mcgill.ecse321.sportsregistrationw24.dto;

import java.sql.Date;

public class RegistrationDto {
    // NOTE: CHANGE THIS LATER TO SOMETHING MORE RELEVANT -> COURSE OFFERING NAME OR SMTH
    Integer courseOfferingId;
    String userAccountEmail;
    Date registrationDate;

    public RegistrationDto(Integer courseOfferingId, String userAccountEmail, Date registrationDate) {
        this.courseOfferingId = courseOfferingId;
        this.userAccountEmail = userAccountEmail;
        this.registrationDate = registrationDate;
    }

    public Integer getCourseId() {
        return this.courseOfferingId;
    }

    public String getUserEmail() {
        return this.userAccountEmail;
    }

    public Date getDate() {
        return this.registrationDate;
    }
}
