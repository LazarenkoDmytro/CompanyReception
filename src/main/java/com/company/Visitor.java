package com.company;

import java.sql.Timestamp;

public class Visitor {
    private String firstName;
    private String lastName;
    private String companyFrom;
    private final String visitorId;
    private String photoPath;
    private String staffVisitingName;
    private String officeNo;
    private final Timestamp dateTimeIn;
    private final Timestamp dateTimeOut;

    public Visitor(String firstName, String lastName, String companyFrom, String visitorId,
                   String photoPath, String staffVisitingName, String officeNo,
                   Timestamp dateTimeIn, Timestamp dateTimeOut) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyFrom = companyFrom;
        this.visitorId = visitorId;
        this.photoPath = photoPath;
        this.staffVisitingName = staffVisitingName;
        this.officeNo = officeNo;
        this.dateTimeIn = dateTimeIn;
        this.dateTimeOut = dateTimeOut;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyFrom() {
        return companyFrom;
    }

    public void setCompanyFrom(String companyFrom) {
        this.companyFrom = companyFrom;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getStaffVisitingName() {
        return staffVisitingName;
    }

    public void setStaffVisitingName(String staffVisitingName) {
        this.staffVisitingName = staffVisitingName;
    }

    public String getOfficeNo() {
        return officeNo;
    }

    public void setOfficeNo(String officeNo) {
        this.officeNo = officeNo;
    }

    public Timestamp getDateTimeIn() {
        return dateTimeIn;
    }

    public Timestamp getDateTimeOut() {
        return dateTimeOut;
    }

}
