package com.example.SpringMySQLRest.security.jwt;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

/**
 * @author David
 */
@AllArgsConstructor
@Data
@Setter(AccessLevel.NONE)
public class Parameters {
 
    private final  String SECRET;
 
    private final long EXPIRATION_TIME; // 10 days
 
    private final String TOKEN_PREFIX;
 
    private final String HEADER_STRING;
 
    private final String SIGN_UP_URL;
    
    
}
