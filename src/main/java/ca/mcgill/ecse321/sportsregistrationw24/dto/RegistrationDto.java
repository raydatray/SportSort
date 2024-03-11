package ca.mcgill.ecse321.sportsregistrationw24.dto;

import java.sql.Date;

public class RegistrationDto {
    String courseTypeName;
    String instructorEmail;
    Date courseStartDate;
    Date courseEndDate;
    String userAccountEmail;
    Date registrationDate;

    public RegistrationDto(String courseTypeName, String instructorEmail, Date courseStartDate,
                           Date courseEndDate, String userAccountEmail, Date registrationDate) {
        this.courseTypeName = courseTypeName;
        this.instructorEmail = instructorEmail;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.userAccountEmail = userAccountEmail;
        this.registrationDate = registrationDate;
    }

    public String getCourseTypeName() {
        return this.courseTypeName;
    }

    public String getInstructorEmail() { return this.instructorEmail; }

    public Date getCourseStartDate() { return this.courseStartDate; }

    public Date getCourseEndDate() { return this.courseEndDate; }

    public String getUserAccountEmail() {
        return this.userAccountEmail;
    }

    public Date getRegistrationDateDate() {
        return this.registrationDate;
    }
}
