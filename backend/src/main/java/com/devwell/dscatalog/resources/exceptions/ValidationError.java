package com.devwell.dscatalog.resources.exceptions;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandarError {
    private static final long serialVersionUID = 1L;

    private List<FieldMessage> errors = new ArrayList<>();


    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }



}
