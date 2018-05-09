package com.example.SpringMySQLRest.exception;

/**
 * @author David
 */
public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException() {
        super("El recurso solicitado no existe");
    }
   
    
    public ResourceNotFoundException(String resourceName) {
         super("El recurso '"+resourceName+"' no existe");
    }
}
