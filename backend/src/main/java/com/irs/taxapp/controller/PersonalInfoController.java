package com.irs.taxapp.controller;

import com.irs.taxapp.model.PersonalInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.irs.taxapp.common.ErrorResponse;


@RestController
@RequestMapping("/api/personal-info")
public class PersonalInfoController {

    @PostMapping
    public ResponseEntity<?> savePersonalInfo(@RequestBody PersonalInfo personalInfo) {
        try {
            // TODO: Add your logic to save personal info to database if needed
            
            // For now, we'll just return the received data
            return ResponseEntity.ok(personalInfo);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(new ErrorResponse("Failed to save personal information: " + e.getMessage()));
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
// }