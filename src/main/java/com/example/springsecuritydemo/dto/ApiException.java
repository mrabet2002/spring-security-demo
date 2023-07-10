
package com.example.springsecuritydemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

/**
 * Represents an error response to send to the client.
 *
 * This class encapsulates the details of an error, including success status, error message, HTTP status code,
 * and the timestamp when the error occurred (in the form of a string representation of a `ZonedDateTime`).
 */
@AllArgsConstructor
@Data
public class ApiException {
    private Boolean success;
    private String message;
    private HttpStatus status;
    private String zonedDateTime;

    /**
     * Constructs an ApiException object with the specified error message and HTTP status.
     *
     * @param message The error message describing the encountered problem.
     * @param status  The HTTP status code indicating the nature of the error.
     */
    public ApiException(String message, HttpStatus status) {
        this.success = Boolean.FALSE;
        this.message = message;
        this.status = status;
        this.zonedDateTime = ZonedDateTime.now().toString();
    }


    /**
     * Default constructor for ApiException.
     *
     * Initializes the object with default values for success, message, status, and the current timestamp.
     */
    public ApiException() {
        this.success = Boolean.FALSE;
        this.message = null;
        this.status = null;
        this.zonedDateTime = ZonedDateTime.now().toString();
    }
}
