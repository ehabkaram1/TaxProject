package com.irs.taxapp.controller;

import com.irs.taxapp.model.W2Data;
import com.irs.taxapp.utils.W2Extractor;
import com.irs.taxapp.utils.W2Parser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.irs.taxapp.common.ErrorResponse;


@RestController
@RequestMapping("/api/w2")
public class W2UploadController {
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/w2-uploads/";

    @PostMapping("/upload")
    public ResponseEntity<?> uploadW2(@RequestParam("w2") MultipartFile file) {
        System.out.println("Received file upload request: " + file.getOriginalFilename());
        
        if (file.isEmpty()) {
            System.out.println("File is empty");
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("No file selected. Please upload a valid W-2 file."));
        }

        try {
            // Ensure the directory exists
            Files.createDirectories(Paths.get(UPLOAD_DIR));
            
            // Save the file
            File saveFile = new File(UPLOAD_DIR + file.getOriginalFilename());
            file.transferTo(saveFile);
            System.out.println("File saved to: " + saveFile.getAbsolutePath());
            
            // Extract text from W-2 PDF
            String extractedText = W2Extractor.extractTextFromPDF(saveFile);
            System.out.println("Text extracted successfully");
            
            // Parse extracted text into structured W2Data
            W2Data w2Data = W2Parser.parseW2Text(extractedText);
            System.out.println("W2 data parsed successfully");
            
            return ResponseEntity.ok(w2Data);
            
        } catch (IOException e) {
            System.out.println("Error processing file: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                .body(new ErrorResponse("File upload failed - " + e.getMessage()));
        }
    }

    @PostMapping("/manual")
    public ResponseEntity<?> manualW2(@RequestBody W2Data w2Data) {
        System.out.println("\n=== Received W2 Data from iOS ===");
        System.out.println("Employer Name: " + w2Data.getEmployerName());
        System.out.println("Employee Name: " + w2Data.getEmployeeName());
        System.out.println("Employee Address: " + w2Data.getEmployeeAddress());

        // ... print other fields
        System.out.println("==================================\n");
        
        try {
            validateW2Data(w2Data);
            return ResponseEntity.ok(w2Data);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("Invalid W2 data: " + e.getMessage()));
        }
    }

    private void validateW2Data(W2Data w2Data) {
        // Add validation logic here
        if (w2Data.getEmployerName() == null || w2Data.getEmployerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employer name is required");
        }
        if (w2Data.getEmployerEIN() == null || !w2Data.getEmployerEIN().matches("\\d{2}-\\d{7}")) {
            throw new IllegalArgumentException("Valid Employer EIN is required (XX-XXXXXXX)");
        }
        // Add more validation as needed
    }
}





// class ErrorResponse {
//     private String message;

//     public ErrorResponse(String message) {
//         this.message = message;
//     }

//     public String getMessage() {
//         return message;
//     }

//     public void setMessage(String message) {
//         this.message = message;
//     }
// }