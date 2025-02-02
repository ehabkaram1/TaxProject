package com.irs.taxapp.common;

/**
 * ErrorResponse class to encapsulate error message details.
 */
public class ErrorResponse {
    private String message;

    /**
     * Constructs an ErrorResponse with a specific message.
     * 
     * @param message the error message
     */
    public ErrorResponse(String message) {
        this.message = message;
    }

    /**
     * Gets the error message.
     * 
     * @return the error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the error message.
     * 
     * @param message the error message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
