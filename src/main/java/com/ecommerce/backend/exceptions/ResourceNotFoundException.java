package com.ecommerce.backend.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    String resourceName;
    String fieldName;
    Long fieldValue;
    String value;

    public ResourceNotFoundException(String resourceName, Long fieldValue, String  fieldName) {
        super(String.format("%s not found with %s: %s",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
        this.fieldName = fieldName;
    }

    public ResourceNotFoundException(String resourceName, String value, String  fieldName) {
        super(String.format("%s not found with %s: %s",resourceName,fieldName,value));
        this.resourceName = resourceName;
        this.value = value;
        this.fieldName = fieldName;
    }

}
