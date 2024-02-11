package ch.hearc.adminservice.api.web.exception;

import ch.hearc.adminservice.service.models.UpdateCampagneStatusAction;
import ch.hearc.adminservice.shared.CampagneStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestApiExceptionHandler  {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpServletRequest request, HttpMessageNotReadableException ex) {

        String error = "Malformed json body";
        return ResponseEntity.badRequest().body(new ApiError(error, ex.getLocalizedMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiValidationError> handleValidationExceptions(HttpServletRequest request, MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        String error = "Not valid json body";
        return ResponseEntity.badRequest().body(new ApiValidationError(error, errors));

    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(HttpServletRequest request, ConversionFailedException ex) {

        if(ex.getCause().getMessage().contains(UpdateCampagneStatusAction.class.getSimpleName())){
            String error = "Action inexistante";
            String message = "Les actions possibles sont: " +
                    String.join(",", Arrays.stream(UpdateCampagneStatusAction.values()).map(Enum::name).toList());
            return ResponseEntity.badRequest().body(new ApiError(error, message));
        } else if (ex.getCause().getMessage().contains(CampagneStatus.class.getSimpleName())) {
            String error = "Status inexistant";
            String message = "Les status possibles sont: " +
                    String.join(",", Arrays.stream(CampagneStatus.values()).map(Enum::name).toList());
            return ResponseEntity.badRequest().body(new ApiError(error, message));
        } else  {
            String error = ex.getCause().getMessage();

            return ResponseEntity.badRequest().body(new ApiError(error, ex.getLocalizedMessage()));
        }


    }



}