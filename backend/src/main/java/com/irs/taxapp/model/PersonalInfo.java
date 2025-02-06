package com.irs.taxapp.model;

import java.util.Date;

public class PersonalInfo {
    // General Personal Information
    private String firstName;
    private String lastName;
    private String ssn;  // or ITIN
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
    private String studyProgram;  // e.g., "Masters in Computer Science"
    
    // Tax Filing Information
    private String filingStatus;
    private boolean canBeClaimed;  // getter required
    private boolean hadUSIncomePriorYears;
    private Double additionalIncome; //getter required
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
    public String getCitizenshipCountry() {
        return citizenshipCountry;
    }
    public String getVisaType() {
        return visaType;
    }
    public Double getAdditionalIncome() {
        return additionalIncome;
    }

    public boolean isCanBeClaimed() {
        return canBeClaimed;
    }   
}