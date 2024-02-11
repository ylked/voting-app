package ch.hearc.adminservice.api.web.exception;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ApiError {


    private  HttpStatus httpStatus;
    private String error;

    private String exceptionMessage;

    Map<String, String> validationErrors = new HashMap<>();


    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setError(String error) {
        this.error = error;
    }


    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public ApiError(HttpStatus httpStatus, String error, String message) {
        this.httpStatus = httpStatus;
        this.error = error;
        this.exceptionMessage = message;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

    public ApiError(HttpStatus httpStatus, String error, String message, Map<String, String> validationErrors) {
        this.httpStatus = httpStatus;
        this.error = error;
        this.exceptionMessage = message;
        this.validationErrors = validationErrors;
    }

    public ApiError() {
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getError() {
        return error;
    }




}
