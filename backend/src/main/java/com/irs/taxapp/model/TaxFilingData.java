package com.irs.taxapp.model;

public class TaxFilingData {
    private W2Data w2Data;
    private PersonalInfo personalInfo;
    
    // Default constructor
    public TaxFilingData() {}
    
    // Constructor with all fields
    public TaxFilingData(W2Data w2Data, PersonalInfo personalInfo) {
        this.w2Data = w2Data;
        this.personalInfo = personalInfo;
    }
    
    // Getters and setters
    public W2Data getW2Data() {
        return w2Data;
    }
    
    public void setW2Data(W2Data w2Data) {
        this.w2Data = w2Data;
    }
    
    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }
    
    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    @Override
    public String toString() {
        return "TaxFilingData{" +
                "w2Data=" + w2Data +
                ", personalInfo=" + personalInfo +
                '}';
    }
}