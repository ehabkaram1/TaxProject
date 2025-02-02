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