package com.irs.taxapp.model;

import java.util.Date;
import java.util.Optional;

public class PersonalInfo {
    // General Personal Information
    private String firstName;
    private String lastName;
    private String ssn;  // getter required
    private String usAddress;
    private String foreignAddress;
    private String citizenshipCountry; //getter required
    private String passportCountry;
    private String passportNumber;
    private String phoneNumber;
    private String email;
    
    // Immigration Information
    private String visaType; //getter required
    private Date arrivalDate;
    private int daysInUS2023;
    private int daysInUS2022;
    private int daysInUS2021;
    
    // Academic Information
    private String academicInstitutionName;
    private String academicInstitutionAddress;
    private String academicInstitutionPhone;
    private String academicDirectorName;
    public String academicDirectorPhone; //getter required
    private String studyProgram;  // e.g., "Masters in Computer Science"
    
    // Tax Filing Information
    private String filingStatus; //getter required
    private boolean canBeClaimed;  // getter required
    private boolean hadUSIncomePriorYears;
    private Double additionalIncome = 0.0;  // Initialize with default value
    private boolean isFirstTimeStudent;
    
    // Form 8843 Specific
    private boolean appliedForResidency;
    private String residencyApplicationExplanation;
    
    // Form 1040NR Specific
    private String stateOfResidence;
    private boolean claimingTreatyBenefits;
    private String treatyCountry;
    private String treatyArticle;

    // Constructors, getters, and setters...
    // Add your getters and setters here
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSsn() {
        return ssn;
    }


    public String getCitizenshipCountry() {
        return citizenshipCountry;
    }
    public String getVisaType() {
        return visaType;
    }

    public String getFilingStatus() {
        return filingStatus;
    }


    public Optional<Double> getAdditionalIncome() {
        return Optional.ofNullable(additionalIncome);
    }
    
    public boolean isCanBeClaimed() {
        return canBeClaimed;
    }   


    

    public String getForeignAddress() {
        return foreignAddress;
    }

    public String getUsAddress() {
        return usAddress;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public String getPassportCountry() {
        return passportCountry;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public int getDaysInUS2023() {
        return daysInUS2023;
    }

    public int getDaysInUS2022() {
        return daysInUS2022;
    }

    public int getDaysInUS2021() {
        return daysInUS2021;
    }

    public String getAcademicInstitutionName() {
        return academicInstitutionName;
    }

    public String getAcademicInstitutionAddress() {
        return academicInstitutionAddress;
    }

    public String getAcademicInstitutionPhone() {
        return academicInstitutionPhone;
    }

    public String getAcademicDirectorName() {
        // TODO Auto-generated method stub
        return academicDirectorName;
    }

    public String getAcademicDirectorPhone() {
        return academicDirectorPhone;
    }
}
