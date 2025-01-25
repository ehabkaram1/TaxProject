package com.irs.taxapp.model;

public class W2Data {
    private String employerName;
    private String employerAddress;
    private String employeeName;
    private String employeeAddress;
    private String employerEIN;
    private String employeeSSN;
    private double wages;
    private double federalTaxWithheld;
    private double stateTaxWithheld;
    private double stateWages;
    private String state;

    // Constructors
    public W2Data() {}

    public W2Data(String employerName, String employerAddress, String employeeName, String employeeAddress, 
                  String employerEIN, String employeeSSN, double wages, double federalTaxWithheld, 
                  double stateTaxWithheld, double stateWages, String state) {
        this.employerName = employerName;
        this.employerAddress = employerAddress;
        this.employeeName = employeeName;
        this.employeeAddress = employeeAddress;
        this.employerEIN = employerEIN;
        this.employeeSSN = employeeSSN;
        this.wages = wages;
        this.federalTaxWithheld = federalTaxWithheld;
        this.stateTaxWithheld = stateTaxWithheld;
        this.stateWages = stateWages;
        this.state = state;
    }

    // Getters and Setters
    public String getEmployerName() { return employerName; }
    public void setEmployerName(String employerName) { this.employerName = employerName; }

    public String getEmployerAddress() { return employerAddress; }
    public void setEmployerAddress(String employerAddress) { this.employerAddress = employerAddress; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getEmployeeAddress() { return employeeAddress; }
    public void setEmployeeAddress(String employeeAddress) { this.employeeAddress = employeeAddress; }

    public String getEmployerEIN() { return employerEIN; }
    public void setEmployerEIN(String employerEIN) { this.employerEIN = employerEIN; }

    public String getEmployeeSSN() { return employeeSSN; }
    public void setEmployeeSSN(String employeeSSN) { this.employeeSSN = employeeSSN; }

    public double getWages() { return wages; }
    public void setWages(double wages) { this.wages = wages; }

    public double getFederalTaxWithheld() { return federalTaxWithheld; }
    public void setFederalTaxWithheld(double federalTaxWithheld) { this.federalTaxWithheld = federalTaxWithheld; }

    public double getStateTaxWithheld() { return stateTaxWithheld; }
    public void setStateTaxWithheld(double stateTaxWithheld) { this.stateTaxWithheld = stateTaxWithheld; }

    public double getStateWages() { return stateWages; }
    public void setStateWages(double stateWages) { this.stateWages = stateWages; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
}
