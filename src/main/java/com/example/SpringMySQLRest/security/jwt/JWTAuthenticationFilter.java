package com.example.SpringMySQLRest.security.jwt;

import com.example.SpringMySQLRest.model.ApplicationUser;
import com.example.SpringMySQLRest.model.enums.Roles;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author David
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private AuthenticationManager authenticationManager;
    private Parameters parameters;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, Parameters parameters) {
        this.authenticationManager = authenticationManager;
        this.parameters = parameters;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
            HttpServletResponse res) throws AuthenticationException {
        log.info("-------------------- attemptAuthentication");
        try {
            ApplicationUser creds = new ObjectMapper()
                    .readValue(req.getInputStream(), ApplicationUser.class);
            //Collection<? extends GrantedAuthority> authorities = Arrays.asList(() -> "ADMIN", () -> "MASTER");

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
           log.info("Exception :"+e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        log.info("--------------------------- successfulAuthentication");

        JwtBuilder builder = Jwts.builder()
                
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + parameters.getEXPIRATION_TIME()))
                .signWith(SignatureAlgorithm.HS512, parameters.getSECRET());
        
        
        
        Map<String, Object> props = new HashMap<>();
        if (auth.getName().startsWith("davi")) {
            props.put("roleAdmin", Roles.ADMIN.name());
            props.put("roleMaster", Roles.MASTER.name());
            props.put("roleUser", Roles.USER.name());
            
        }
        else{
            props.put("roleUser", Roles.USER);
        }
        builder.addClaims(props);
        String token = builder.compact();

        res.addHeader(parameters.getHEADER_STRING(), parameters.getTOKEN_PREFIX() + token);
    }
}
