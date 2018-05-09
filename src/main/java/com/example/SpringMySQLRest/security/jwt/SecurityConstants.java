package com.example.SpringMySQLRest.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author David
 */
@Service
@Getter
public class SecurityConstants {

    public static  final String SECRET = "SecretKeyToGenJWTs";
    public static  final long EXPIRATION_TIME=8600_000; // 10 days
    public static  final String TOKEN_PREFIX = "Bearer ";
    public static  final String HEADER_STRING = "Authorization";
    public static  final String SIGN_UP_URL = "/users";

   

    
    
}
