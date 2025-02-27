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


    public Double getAdditionalIncome() {
        return additionalIncome;
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
        return academicDirectorName;
    }

    public String getAcademicDirectorPhone() {
        return academicDirectorPhone;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }


    // Add these setter methods to your PersonalInfo class

public void setFirstName(String firstName) {
    this.firstName = firstName;
}

public void setLastName(String lastName) {
    this.lastName = lastName;
}

public void setSsn(String ssn) {
    this.ssn = ssn;
}

public void setUsAddress(String usAddress) {
    this.usAddress = usAddress;
}

public void setForeignAddress(String foreignAddress) {
    this.foreignAddress = foreignAddress;
}

public void setCitizenshipCountry(String citizenshipCountry) {
    this.citizenshipCountry = citizenshipCountry;
}

public void setPassportCountry(String passportCountry) {
    this.passportCountry = passportCountry;
}

public void setPassportNumber(String passportNumber) {
    this.passportNumber = passportNumber;
}

public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
}

public void setEmail(String email) {
    this.email = email;
}

public void setVisaType(String visaType) {
    this.visaType = visaType;
}

public void setArrivalDate(Date arrivalDate) {
    this.arrivalDate = arrivalDate;
}

public void setDaysInUS2023(int daysInUS2023) {
    this.daysInUS2023 = daysInUS2023;
}

public void setDaysInUS2022(int daysInUS2022) {
    this.daysInUS2022 = daysInUS2022;
}

public void setDaysInUS2021(int daysInUS2021) {
    this.daysInUS2021 = daysInUS2021;
}

public void setAcademicInstitutionName(String academicInstitutionName) {
    this.academicInstitutionName = academicInstitutionName;
}

public void setAcademicInstitutionAddress(String academicInstitutionAddress) {
    this.academicInstitutionAddress = academicInstitutionAddress;
}

public void setAcademicInstitutionPhone(String academicInstitutionPhone) {
    this.academicInstitutionPhone = academicInstitutionPhone;
}

public void setAcademicDirectorName(String academicDirectorName) {
    this.academicDirectorName = academicDirectorName;
}

public void setAcademicDirectorPhone(String academicDirectorPhone) {
    this.academicDirectorPhone = academicDirectorPhone;
}

public void setFilingStatus(String filingStatus) {
    this.filingStatus = filingStatus;
}

public void setCanBeClaimed(boolean canBeClaimed) {
    this.canBeClaimed = canBeClaimed;
}

public void setHadUSIncomePriorYears(boolean hadUSIncomePriorYears) {
    this.hadUSIncomePriorYears = hadUSIncomePriorYears;
}

public void setAdditionalIncome(Double additionalIncome) {
    this.additionalIncome = additionalIncome;
}

public void setFirstTimeStudent(boolean isFirstTimeStudent) {
    this.isFirstTimeStudent = isFirstTimeStudent;
}

public void setAppliedForResidency(boolean appliedForResidency) {
    this.appliedForResidency = appliedForResidency;
}

public void setResidencyApplicationExplanation(String residencyApplicationExplanation) {
    this.residencyApplicationExplanation = residencyApplicationExplanation;
}

public void setStateOfResidence(String stateOfResidence) {
    this.stateOfResidence = stateOfResidence;
}

public void setClaimingTreatyBenefits(boolean claimingTreatyBenefits) {
    this.claimingTreatyBenefits = claimingTreatyBenefits;
}

public void setTreatyCountry(String treatyCountry) {
    this.treatyCountry = treatyCountry;
}

public void setTreatyArticle(String treatyArticle) {
    this.treatyArticle = treatyArticle;
}

public String getStudyProgram() {
    return studyProgram;
}

public void setStudyProgram(String studyProgram) {
    this.studyProgram = studyProgram;
}

public boolean isHadUSIncomePriorYears() {
    return hadUSIncomePriorYears;
}

public boolean isFirstTimeStudent() {
    return isFirstTimeStudent;
}

public boolean isAppliedForResidency() {
    return appliedForResidency;
}

public boolean isClaimingTreatyBenefits() {
    return claimingTreatyBenefits;
}
}
