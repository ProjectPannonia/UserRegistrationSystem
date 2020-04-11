package com.apress.ravi.UserRegistrationSystem.exceptionHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class RestValidationHandler {

    private MessageSource messageSource;

    @Autowired
    public RestValidationHandler(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    //method to handle validation error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<FieldValidationErrorDetails> handleValidationError(
            MethodArgumentNotValidException mNotValidException, HttpServletRequest request){

        FieldValidationErrorDetails fErrorDetails = new FieldValidationErrorDetails();

        fErrorDetails.setError_timeStamp(new Date().getTime());
        fErrorDetails.setError_status(HttpStatus.BAD_REQUEST.value());
        fErrorDetails.setError_title("Field Validation Error");
        fErrorDetails.setError_detail("Input field validation failed");
        fErrorDetails.getError_developer_Message(mNotValidException.getClass().getName());
        fErrorDetails.setError_path(request.getRequestURI());

        BindingResult result = mNotValidException.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        for (FieldError error : fieldErrors){
            FieldValidationError fError = processFieldError(error);
            List<FieldValidationError> fieldValidationErrorList =
                    fErrorDetails.getErrors().get(error.getField());
            if(fieldValidationErrorList == null){
                fieldValidationErrorList = new ArrayList<FieldValidationError>();
            }
            fieldValidationErrorList.add(fError);
            fErrorDetails.getErrors().put(error.getField(),fieldValidationErrorList);
        }
        return new ResponseEntity<FieldValidationErrorDetails>(fErrorDetails,HttpStatus.BAD_REQUEST);
    }

    //method to process field error
    private FieldValidationError processFieldError(final FieldError error){
        FieldValidationError fieldValidationError = new FieldValidationError();

        if(error != null){
            Locale currentLocale = LocaleContextHolder.getLocale();
            String msg = messageSource.getMessage(error.getDefaultMessage(),null,currentLocale);
            fieldValidationError.setField(error.getField());
            fieldValidationError.setType(TrayIcon.MessageType.ERROR);
            fieldValidationError.setMessage(error.getDefaultMessage());
        }
        return fieldValidationError;
    }
}
