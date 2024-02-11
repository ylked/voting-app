package ch.hearc.adminservice.api.web.exception;

public class ApiError {



    private String error;

    private String exceptionMessage;



    public ApiError(String error, String message) {
        this.error = error;
        this.exceptionMessage = message;
    }


    public String getExceptionMessage() {
        return exceptionMessage;
    }



    public String getError() {
        return error;
    }




}
