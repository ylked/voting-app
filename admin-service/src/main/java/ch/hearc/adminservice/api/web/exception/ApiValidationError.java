package ch.hearc.adminservice.api.web.exception;

import java.util.Map;

public class ApiValidationError {


    private String error;

    Map<String, String> validationErrors;

    public ApiValidationError(String error, Map<String, String> validationErrors ) {

        this.error = error;
        this.validationErrors = validationErrors;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }


    public String getError() {
        return error;
    }

}
