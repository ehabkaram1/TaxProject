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
import java.text.SimpleDateFormat;
import java.util.Map;

@Service
public class Form1040NRService {
    private static final String FORM_TEMPLATE_PATH = "src/main/resources/forms/nr1040.pdf";

    public byte[] generateForm(TaxFilingData taxData) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        File file = new File(FORM_TEMPLATE_PATH);
        
        try (PdfReader reader = new PdfReader(file);
             PdfDocument pdfDocument = new PdfDocument(reader, new PdfWriter(baos))) {
            
            PdfAcroForm acroForm = PdfAcroForm.getAcroForm(pdfDocument, true);

            if (acroForm != null) {
                // Print available fields for debugging
                System.out.println("Listing all fields in 1040NR:");
                for (Map.Entry<String, PdfFormField> entry : acroForm.getFormFields().entrySet()) {
                    System.out.println("Field Name: " + entry.getKey());
                    System.out.println("Field Type: " + entry.getValue().getFormType());
                    System.out.println("---");
                }

                //Page 1
                setField(acroForm, "topmostSubform[0].Page1[0].f1_4[0]", taxData.getPersonalInfo().getFirstName());
                setField(acroForm, "topmostSubform[0].Page1[0].f1_5[0]", taxData.getPersonalInfo().getLastName());
                setField(acroForm, "topmostSubform[0].Page1[0].f1_6[0]", taxData.getPersonalInfo().getSsn());
                //setField(acroForm, "topmostSubform[0].Page1[0].f1_4[0]", taxData.getPersonalInfo().getUsAddress());


                if(taxData.getPersonalInfo().getFilingStatus().equals("Single")) {
                    setField(acroForm, "topmostSubform[0].Page1[0].c1_1[0]", "Yes");
                } else {
                    setField(acroForm, "topmostSubform[0].Page1[0].c1_1[1]", "Yes");
                }

                setField(acroForm, "topmostSubform[0].Page1[0].c1_3[1]", "Yes");
                // Set Income Information from W-2
                String wages = String.format("%.2f", taxData.getW2Data().getWages());
                String fedTax = String.format("%.2f", taxData.getW2Data().getFederalTaxWithheld());
                
                setField(acroForm, "topmostSubform[0].Page1[0].f1_28[0]", wages);
                setField(acroForm, "topmostSubform[0].Page1[0].f1_38[0]", "0");

                setField(acroForm, "topmostSubform[0].Page1[0].f1_39[0]", wages);

                setField(acroForm, "topmostSubform[0].Page1[0].f1_44[0]", "0");
                setField(acroForm, "topmostSubform[0].Page1[0].f1_45[0]", "0");
                setField(acroForm, "topmostSubform[0].Page1[0].f1_46[0]", "0");
                setField(acroForm, "topmostSubform[0].Page1[0].f1_47[0]", "0");

                double addIncome=taxData.getPersonalInfo().getAdditionalIncome();
                setField(acroForm, "topmostSubform[0].Page1[0].f1_49[0]", "0");
                setField(acroForm, "topmostSubform[0].Page1[0].f1_50[0]", String.valueOf(addIncome));
                double grossIncome=taxData.getW2Data().getWages()+addIncome;
                setField(acroForm, "topmostSubform[0].Page1[0].f1_51[0]", String.valueOf(grossIncome));
                setField(acroForm, "topmostSubform[0].Page1[0].f1_52[0]", "0");
                setField(acroForm, "topmostSubform[0].Page1[0].f1_53[0]", String.valueOf(grossIncome));
                double standardDeduction=14600;
                setField(acroForm, "topmostSubform[0].Page1[0].f1_54[0]", String.valueOf(standardDeduction));
                setField(acroForm, "topmostSubform[0].Page1[0].f1_58[0]", String.valueOf(standardDeduction));
                double taxableIncome=(grossIncome-standardDeduction)>0?(grossIncome-standardDeduction):0;
                setField(acroForm, "topmostSubform[0].Page1[0].f1_59[0]", String.valueOf(taxableIncome));
                
                
                //End of Page 1


                //Page 2
                setField(acroForm, "topmostSubform[0].Page2[0].f2_2[0]", "0");
                setField(acroForm, "topmostSubform[0].Page2[0].f2_3[0]", "0");
                setField(acroForm, "topmostSubform[0].Page2[0].f2_4[0]", "0");
                setField(acroForm, "topmostSubform[0].Page2[0].f2_5[0]", "0");
                setField(acroForm, "topmostSubform[0].Page2[0].f2_6[0]", "0");
                setField(acroForm, "topmostSubform[0].Page2[0].f2_7[0]", "0");
                setField(acroForm, "topmostSubform[0].Page2[0].f2_8[0]", "0");
                setField(acroForm, "topmostSubform[0].Page2[0].Line23a_ReadOrder[0].f2_9[0]", "0");
                setField(acroForm, "topmostSubform[0].Page2[0].Line23b_ReadOrder[0].f2_10[0]", "0");
                setField(acroForm, "topmostSubform[0].Page2[0].Line23c_ReadOrder[0].f2_11[0]", "0");
                setField(acroForm, "topmostSubform[0].Page2[0].f2_12[0]", "0");
                setField(acroForm, "topmostSubform[0].Page2[0].f2_13[0]", "0");

                setField(acroForm, "topmostSubform[0].Page2[0].Line25_ReadOrder[0].f2_14[0]", fedTax);

                double value1099=0;
                setField(acroForm, "topmostSubform[0].Page2[0].Line26_ReadOrder[0].f2_15[0]", String.valueOf(value1099));

                double totalPayments=taxData.getW2Data().getFederalTaxWithheld()+value1099;
                setField(acroForm, "topmostSubform[0].Page2[0].f2_17[0]", String.valueOf(totalPayments));

                setField(acroForm, "topmostSubform[0].Page2[0].f2_18[0]", "0");
                setField(acroForm, "topmostSubform[0].Page2[0].f2_19[0]", "0");
                setField(acroForm, "topmostSubform[0].Page2[0].f2_20[0]", "0");
                setField(acroForm, "topmostSubform[0].Page2[0].f2_21[0]", "0");

                setField(acroForm, "topmostSubform[0].Page2[0].f2_23[0]", "0");
                setField(acroForm, "topmostSubform[0].Page2[0].f2_24[0]", "0");
                setField(acroForm, "topmostSubform[0].Page2[0].f2_26[0]", "0");

                setField(acroForm, "topmostSubform[0].Page2[0].f2_27[0]", "0");
                setField(acroForm, "topmostSubform[0].Page2[0].f2_28[0]", String.valueOf(totalPayments));
                setField(acroForm, "topmostSubform[0].Page2[0].f2_29[0]", String.valueOf(totalPayments));
                setField(acroForm, "topmostSubform[0].Page2[0].f2_30[0]", String.valueOf(totalPayments));

                //Routing Number
                //setField(acroForm, "topmostSubform[0].Page2[0].RoutingNo[0].f2_31[0]", taxData.getPersonalInfo().getBankRoutingNumber());
                //Account Number
                //setField(acroForm, "topmostSubform[0].Page2[0].AccountNo[0].f2_32[0]", taxData.getPersonalInfo().getBankAccountNumber());
                //AccountType
                //if(taxData.getPersonalInfo().isCheckingAccount()) {
                //    setField(acroForm, "topmostSubform[0].Page2[0].c2_5[0]", "Yes");
                //} else {
                //    setField(acroForm, "topmostSubform[0].Page2[0].c2_5[1]", "Yes");
                //}

                setField(acroForm, "topmostSubform[0].Page2[0].f2_34[0]", "0");

                setField(acroForm, "topmostSubform[0].Page2[0].f2_40[0]", "STUDENT");
                setField(acroForm, "topmostSubform[0].Page2[0].f2_42[0]", taxData.getPersonalInfo().getPhoneNumber());
                setField(acroForm, "topmostSubform[0].Page2[0].f2_43[0]", taxData.getPersonalInfo().getEmail());

                pdfDocument.close();
            } else {
                throw new IOException("Could not create acroform.");
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