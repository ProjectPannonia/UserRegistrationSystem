package com.apress.ravi.UserRegistrationSystem.exceptionHandler;

import com.apress.ravi.UserRegistrationSystem.dto.UserDTO;

public class CustomErrorType extends UserDTO {
    private String errorMessage;

    public CustomErrorType(final String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
}
