package com.irs.taxapp.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.irs.taxapp.model.TaxFilingData;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class Form8843Service {
    private static final String FORM_TEMPLATE_PATH = "src/main/resources/forms/f8843.pdf";
    private static final Map<String, String> COUNTRY_MAP = new HashMap<String, String>() {{
    put("AF", "Afghanistan");
    put("AX", "Åland Islands");
    put("AL", "Albania");
    put("DZ", "Algeria");
    put("AS", "American Samoa");
    put("AD", "Andorra");
    put("AO", "Angola");
    put("AI", "Anguilla");
    put("AQ", "Antarctica");
    put("AG", "Antigua and Barbuda");
    put("AR", "Argentina");
    put("AM", "Armenia");
    put("AW", "Aruba");
    put("AU", "Australia");
    put("AT", "Austria");
    put("AZ", "Azerbaijan");
    put("BS", "Bahamas");
    put("BH", "Bahrain");
    put("BD", "Bangladesh");
    put("BB", "Barbados");
    put("BY", "Belarus");
    put("BE", "Belgium");
    put("BZ", "Belize");
    put("BJ", "Benin");
    put("BM", "Bermuda");
    put("BT", "Bhutan");
    put("BO", "Bolivia");
    put("BQ", "Bonaire, Sint Eustatius and Saba");
    put("BA", "Bosnia and Herzegovina");
    put("BW", "Botswana");
    put("BV", "Bouvet Island");
    put("BR", "Brazil");
    put("IO", "British Indian Ocean Territory");
    put("BN", "Brunei Darussalam");
    put("BG", "Bulgaria");
    put("BF", "Burkina Faso");
    put("BI", "Burundi");
    put("CV", "Cabo Verde");
    put("KH", "Cambodia");
    put("CM", "Cameroon");
    put("CA", "Canada");
    put("KY", "Cayman Islands");
    put("CF", "Central African Republic");
    put("TD", "Chad");
    put("CL", "Chile");
    put("CN", "China");
    put("CX", "Christmas Island");
    put("CC", "Cocos (Keeling) Islands");
    put("CO", "Colombia");
    put("KM", "Comoros");
    put("CD", "Congo (the Democratic Republic of the)");
    put("CG", "Congo");
    put("CK", "Cook Islands");
    put("CR", "Costa Rica");
    put("HR", "Croatia");
    put("CU", "Cuba");
    put("CW", "Curaçao");
    put("CY", "Cyprus");
    put("CZ", "Czechia");
    put("CI", "Côte d'Ivoire");
    put("DK", "Denmark");
    put("DJ", "Djibouti");
    put("DM", "Dominica");
    put("DO", "Dominican Republic");
    put("EC", "Ecuador");
    put("EG", "Egypt");
    put("SV", "El Salvador");
    put("GQ", "Equatorial Guinea");
    put("ER", "Eritrea");
    put("EE", "Estonia");
    put("SZ", "Eswatini");
    put("ET", "Ethiopia");
    put("FK", "Falkland Islands [Malvinas]");
    put("FO", "Faroe Islands");
    put("FJ", "Fiji");
    put("FI", "Finland");
    put("FR", "France");
    put("GF", "French Guiana");
    put("PF", "French Polynesia");
    put("TF", "French Southern Territories");
    put("GA", "Gabon");
    put("GM", "Gambia");
    put("GE", "Georgia");
    put("DE", "Germany");
    put("GH", "Ghana");
    put("GI", "Gibraltar");
    put("GR", "Greece");
    put("GL", "Greenland");
    put("GD", "Grenada");
    put("GP", "Guadeloupe");
    put("GU", "Guam");
    put("GT", "Guatemala");
    put("GG", "Guernsey");
    put("GN", "Guinea");
    put("GW", "Guinea-Bissau");
    put("GY", "Guyana");
    put("HT", "Haiti");
    put("HM", "Heard Island and McDonald Islands");
    put("VA", "Holy See");
    put("HN", "Honduras");
    put("HK", "Hong Kong");
    put("HU", "Hungary");
    put("IS", "Iceland");
    put("IN", "India");
    put("ID", "Indonesia");
    put("IR", "Iran");
    put("IQ", "Iraq");
    put("IE", "Ireland");
    put("IM", "Isle of Man");
    put("IL", "Israel");
    put("IT", "Italy");
    put("JM", "Jamaica");
    put("JP", "Japan");
    put("JE", "Jersey");
    put("JO", "Jordan");
    put("KZ", "Kazakhstan");
    put("KE", "Kenya");
    put("KI", "Kiribati");
    put("KP", "Korea (Democratic People's Republic of)");
    put("KR", "Korea, Republic of");
    put("KW", "Kuwait");
    put("KG", "Kyrgyzstan");
    put("LA", "Lao People's Democratic Republic");
    put("LV", "Latvia");
    put("LB", "Lebanon");
    put("LS", "Lesotho");
    put("LR", "Liberia");
    put("LY", "Libya");
    put("LI", "Liechtenstein");
    put("LT", "Lithuania");
    put("LU", "Luxembourg");
    put("MO", "Macao");
    put("MG", "Madagascar");
    put("MW", "Malawi");
    put("MY", "Malaysia");
    put("MV", "Maldives");
    put("ML", "Mali");
    put("MT", "Malta");
    put("MH", "Marshall Islands");
    put("MQ", "Martinique");
    put("MR", "Mauritania");
    put("MU", "Mauritius");
    put("YT", "Mayotte");
    put("MX", "Mexico");
    put("FM", "Micronesia (Federated States of)");
    put("MD", "Moldova, Republic of");
    put("MC", "Monaco");
    put("MN", "Mongolia");
    put("ME", "Montenegro");
    put("MS", "Montserrat");
    put("MA", "Morocco");
    put("MZ", "Mozambique");
    put("MM", "Myanmar");
    put("NA", "Namibia");
    put("NR", "Nauru");
    put("NP", "Nepal");
    put("NL", "Netherlands");
    put("NC", "New Caledonia");
    put("NZ", "New Zealand");
    put("NI", "Nicaragua");
    put("NE", "Niger");
    put("NG", "Nigeria");
    put("NU", "Niue");
    put("NF", "Norfolk Island");
    put("MP", "Northern Mariana Islands");
    put("NO", "Norway");
    put("OM", "Oman");
    put("PK", "Pakistan");
    put("PW", "Palau");
    put("PS", "Palestine, State of");
    put("PA", "Panama");
    put("PG", "Papua New Guinea");
    put("PY", "Paraguay");
    put("PE", "Peru");
    put("PH", "Philippines");
    put("PN", "Pitcairn");
    put("PL", "Poland");
    put("PT", "Portugal");
    put("PR", "Puerto Rico");
    put("QA", "Qatar");
    put("MK", "Republic of North Macedonia");
    put("RO", "Romania");
    put("RU", "Russian Federation");
    put("RW", "Rwanda");
    put("RE", "Réunion");
    put("BL", "Saint Barthélemy");
    put("SH", "Saint Helena, Ascension and Tristan da Cunha");
    put("KN", "Saint Kitts and Nevis");
    put("LC", "Saint Lucia");
    put("MF", "Saint Martin (French part)");
    put("PM", "Saint Pierre and Miquelon");
    put("VC", "Saint Vincent and the Grenadines");
    put("WS", "Samoa");
    put("SM", "San Marino");
    put("ST", "Sao Tome and Principe");
    put("SA", "Saudi Arabia");
    put("SN", "Senegal");
    put("RS", "Serbia");
    put("SC", "Seychelles");
    put("SL", "Sierra Leone");
    put("SG", "Singapore");
    put("SX", "Sint Maarten (Dutch part)");
    put("SK", "Slovakia");
    put("SI", "Slovenia");
    put("SB", "Solomon Islands");
    put("SO", "Somalia");
    put("ZA", "South Africa");
    put("GS", "South Georgia and the South Sandwich Islands");
    put("SS", "South Sudan");
    put("ES", "Spain");
    put("LK", "Sri Lanka");
    put("SD", "Sudan");
    put("SR", "Suriname");
    put("SJ", "Svalbard and Jan Mayen");
    put("SE", "Sweden");
    put("CH", "Switzerland");
    put("SY", "Syrian Arab Republic");
    put("TW", "Taiwan, Province of China");
    put("TJ", "Tajikistan");
    put("TZ", "Tanzania, United Republic of");
    put("TH", "Thailand");
    put("TL", "Timor-Leste");
    put("TG", "Togo");
    put("TK", "Tokelau");
    put("TO", "Tonga");
    put("TT", "Trinidad and Tobago");
    put("TN", "Tunisia");
    put("TR", "Turkey");
    put("TM", "Turkmenistan");
    put("TC", "Turks and Caicos Islands");
    put("TV", "Tuvalu");
    put("UG", "Uganda");
    put("UA", "Ukraine");
    put("AE", "United Arab Emirates");
    put("GB", "United Kingdom of Great Britain and Northern Ireland");
    put("UM", "United States Minor Outlying Islands");
    put("US", "United States of America");
    put("UY", "Uruguay");
    put("UZ", "Uzbekistan");
    put("VU", "Vanuatu");
    put("VE", "Venezuela (Bolivarian Republic of)");
    put("VN", "Viet Nam");
    put("VG", "Virgin Islands (British)");
    put("VI", "Virgin Islands (U.S.)");
    put("WF", "Wallis and Futuna");
    put("EH", "Western Sahara");
    put("YE", "Yemen");
    put("ZM", "Zambia");
    put("ZW", "Zimbabwe");
}};

private String getCountryName(String countryCode) {
    return COUNTRY_MAP.getOrDefault(countryCode, countryCode);
}

    public byte[] generateForm(TaxFilingData taxData) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        File file = new File(FORM_TEMPLATE_PATH);
        try (PdfReader reader = new PdfReader(file);
             PdfDocument pdfDocument = new PdfDocument(reader, new PdfWriter(baos))) {
            PdfAcroForm acroForm = PdfAcroForm.getAcroForm(pdfDocument, true);

            if (acroForm != null) {
                System.out.println("Listing all fields:");
                for (Map.Entry<String, PdfFormField> entry : acroForm.getFormFields().entrySet()) {
                    System.out.println("Field Name: " + entry.getKey());
                    System.out.println("Field Type: " + entry.getValue().getFormType());
                    System.out.println("Field Value: " + entry.getValue().getValueAsString());
                    System.out.println("---");
                }

                String formattedDate = "";
                if (taxData.getPersonalInfo().getArrivalDate() != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    formattedDate = dateFormat.format(taxData.getPersonalInfo().getArrivalDate());
                }


                // Set a single field to demonstrate functionality
                setField(acroForm, "topmostSubform[0].Page1[0].f1_4[0]", taxData.getPersonalInfo().getFirstName());
                setField(acroForm, "topmostSubform[0].Page1[0].f1_5[0]", taxData.getPersonalInfo().getLastName());
                setField(acroForm, "topmostSubform[0].Page1[0].f1_6[0]", taxData.getPersonalInfo().getSsn());
                setField(acroForm, "topmostSubform[0].Page1[0].f1_7[0]", taxData.getPersonalInfo().getForeignAddress());
                setField(acroForm, "topmostSubform[0].Page1[0].f1_8[0]", taxData.getPersonalInfo().getUsAddress());
                setField(acroForm, "topmostSubform[0].Page1[0].f1_9[0]", (taxData.getPersonalInfo().getVisaType() != null ? taxData.getPersonalInfo().getVisaType() : "") + (!formattedDate.isEmpty() ? " " + formattedDate : ""));
                setField(acroForm, "topmostSubform[0].Page1[0].f1_10[0]", taxData.getPersonalInfo().getVisaType());//this is immigration
                setField(acroForm, "topmostSubform[0].Page1[0].f1_11[0]", getCountryName(taxData.getPersonalInfo().getCitizenshipCountry()));
                setField(acroForm, "topmostSubform[0].Page1[0].f1_12[0]", getCountryName(taxData.getPersonalInfo().getPassportCountry()));
                setField(acroForm, "topmostSubform[0].Page1[0].f1_13[0]", taxData.getPersonalInfo().getPassportNumber());
                setField(acroForm, "topmostSubform[0].Page1[0].f1_14[0]", String.valueOf(taxData.getPersonalInfo().getDaysInUS2023()));
                setField(acroForm, "topmostSubform[0].Page1[0].f1_15[0]", String.valueOf(taxData.getPersonalInfo().getDaysInUS2022()));
                setField(acroForm, "topmostSubform[0].Page1[0].f1_16[0]", String.valueOf(taxData.getPersonalInfo().getDaysInUS2021()));
                
                String employerName = taxData.getW2Data().getEmployerName();
                String employerAddress = taxData.getW2Data().getEmployerAddress();
                String phoneNumber = taxData.getPersonalInfo().getAcademicInstitutionPhone();

                String institutionInfo = String.format("%s, %s, %s", employerName, employerAddress, phoneNumber);
                setField(acroForm, "topmostSubform[0].Page1[0].f1_31[0]", institutionInfo);

                String advisorName = taxData.getPersonalInfo().getAcademicDirectorName();
                String advisorPhone= taxData.getPersonalInfo().getAcademicDirectorPhone();
                String advisorInfo = String.format("%s, %s, %s", advisorName, employerAddress, advisorPhone);

                setField(acroForm, "topmostSubform[0].Page1[0].f1_33[0]", advisorInfo);

                if(taxData.getPersonalInfo().getVisaType().equals("F1")){
                    if(taxData.getPersonalInfo().getDaysInUS2023() > 0) {
                        setField(acroForm, "topmostSubform[0].Page1[0].f1_41[0]", "F");
                    }
                    if(taxData.getPersonalInfo().getDaysInUS2022() > 0) {
                        setField(acroForm, "topmostSubform[0].Page1[0].f1_40[0]", "F");
                    }
                    if(taxData.getPersonalInfo().getDaysInUS2021() > 0) {
                        setField(acroForm, "topmostSubform[0].Page1[0].f1_39[0]", "F");
                    }
                }

                try {
                    setField(acroForm, "topmostSubform[0].Page1[0].c1_1[1]", "Yes");
                    setField(acroForm, "topmostSubform[0].Page1[0].c1_2[1]", "Yes");
                    setField(acroForm, "topmostSubform[0].Page1[0].c1_3[1]", "Yes");
                } catch (Exception e) {
                    System.out.println("Error setting checkbox fields: " + e.getMessage());
                    // Continue execution - don't let checkbox errors fail the whole process
                }
        

            } else {
                System.out.println("No AcroForm found in the PDF.");
                throw new IOException("Failed to get AcroForm from PDF");
            }



            pdfDocument.close(); // Save changes
            return baos.toByteArray();
        } catch (Exception e) {
            System.err.println("Error generating form: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Failed to generate form: " + e.getMessage(), e);
        }
    }

    private void setField(PdfAcroForm acroForm, String fieldName, String value) {
        try {
            // Add null check for value
            if (value == null) {
                value = ""; // Convert null to empty string
            }
            
            PdfFormField field = acroForm.getField(fieldName);
            if (field != null) {
                // Add debug logging
                System.out.println("Setting field: " + fieldName + " with value: " + value);
                try {
                    field.setValue(value);
                    System.out.println("Successfully set field: " + fieldName);
                } catch (Exception e) {
                    System.err.println("Error setting value for field " + fieldName + ": " + e.getMessage());
                    // Continue execution instead of failing
                }
            } else {
                System.out.println("Field NOT FOUND: " + fieldName);
            }
        } catch (Exception e) {
            System.err.println("Error in setField for " + fieldName + ": " + e.getMessage());
            // Don't throw the exception, just log it
        }
    }
}
