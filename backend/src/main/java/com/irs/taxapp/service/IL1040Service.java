package com.irs.taxapp.service;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.forms.fields.PdfFormField;
import com.irs.taxapp.model.TaxFilingData;

import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class IL1040Service {
    private static final String FORM_TEMPLATE_PATH = "src/main/resources/forms/fil1040.pdf";

    public byte[] generateForm(TaxFilingData taxData) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        File file = new File(FORM_TEMPLATE_PATH);
        
        try (PdfReader reader = new PdfReader(file);
             PdfDocument pdfDocument = new PdfDocument(reader, new PdfWriter(baos))) {
            
            PdfAcroForm acroForm = PdfAcroForm.getAcroForm(pdfDocument, true);

            if (acroForm != null) {
                // Log all available fields
                System.out.println("Listing all fields in IL-1040:");
                for (Map.Entry<String, PdfFormField> entry : acroForm.getFormFields().entrySet()) {
                    System.out.println("Field Name: " + entry.getKey());
                    System.out.println("Field Type: " + entry.getValue().getFormType());
                    System.out.println("Field Value: " + entry.getValue().getValueAsString());
                    System.out.println("---");
                }

                setField(acroForm, "Your first name and initial", taxData.getPersonalInfo().getFirstName());
                setField(acroForm, "Your last name", taxData.getPersonalInfo().getLastName());
                setField(acroForm, "Your social security number", taxData.getPersonalInfo().getSsn());
                setField(acroForm, "address", taxData.getPersonalInfo().getUsAddress());

                setField(acroForm, "Federally adjusted income", String.valueOf(taxData.getW2Data().getWages()));
                setField(acroForm, "Total income", String.valueOf(taxData.getW2Data().getWages()));
                setField(acroForm, "Illinois base income", String.valueOf(taxData.getW2Data().getWages()));

                int valueA = 2775;
                setField(acroForm, "Exemption amount", String.valueOf(valueA));
                setField(acroForm, "Exemption allowance", String.valueOf(valueA));
                //setField(acroForm, "Illinois net income from Schedule NR", //Value B);

                //int valueC = 4.95% * valueB;

                //setField(acroForm, "Multiply residency rate", //Value C);
                //setField(acroForm, "Income tax", //Value C);
                //setField(acroForm, "Tax after nonrefundable credits", //Value C);
                //setField(acroForm, "Total Tax", //Value C);
                //setField(acroForm, "Total tax from Page 1", //Value C);
                setField(acroForm, "Illinois Income Tax withheld", String.valueOf(taxData.getW2Data().getStateTaxWithheld()));
                setField(acroForm, "Total payments and refundable credit", String.valueOf(taxData.getW2Data().getStateTaxWithheld()));

                //float calc = taxData.getW2Data().getStateTaxWithheld() - valueC;
                //if(calc>=0) {
                //    setField(acroForm, "If Line 31 is greater", calc);
                //    setField(acroForm, "Overpayment amount", calc);
                //    setField(acroForm, "Refunded to you", calc);
                //} else if() {
                //    setField(acroForm, "If Line 24 is greater", -calc);
                //}


                //if(taxData.getPersonalInfo().isDirectDeposit()) {
                    //setField(acroForm, "Refund Method.1", "Yes");
                    //setField(acroForm, "Bank Routing Number", //String.valueOf(taxData.getPersonalInfo().getBankRoutingNumber()));
                    //setField(acroForm, "Bank account number", //String.valueOf(taxData.getPersonalInfo().getBankAccountNumber()));
                    //if(taxData.getPersonalInfo().isCheckingAccount()) {
                    //    setField(acroForm, "Checking/Savings Account.1", "Yes");
                    //} else {
                    //    setField(acroForm, "Checking/Savings Account.2", "Yes");
                    //}
                //} else {
                    //setField(acroForm, "Refund Method.2", "Yes");
                //}
                setField(acroForm, "DaytimeAreaCode", String.valueOf(taxData.getPersonalInfo().getPhoneNumber()).substring(0, 3));
                setField(acroForm, "DaytimePhoneNumber", String.valueOf(taxData.getPersonalInfo().getPhoneNumber()).substring(3));
                
                pdfDocument.close();
            }
        }
        return baos.toByteArray();
    }

    private void setField(PdfAcroForm acroForm, String fieldName, String value) {
        try {
            if (value == null) {
                value = "";
            }
            
            PdfFormField field = acroForm.getField(fieldName);
            if (field != null) {
                System.out.println("Setting field: " + fieldName + " with value: " + value);
                field.setValue(value);
            } else {
                System.out.println("Field NOT FOUND: " + fieldName);
            }
        } catch (Exception e) {
            System.err.println("Error setting field " + fieldName + ": " + e.getMessage());
        }
    }
}