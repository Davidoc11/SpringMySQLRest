package com.example.SpringMySQLRest.security.jwt;

import com.example.SpringMySQLRest.model.enums.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author David
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Parameters parameters;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, Parameters parameters) {
        super(authenticationManager);
        this.parameters = parameters;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain) throws IOException, ServletException {

        String header = req.getHeader(parameters.getHEADER_STRING());
        if (header == null || !header.startsWith(parameters.getTOKEN_PREFIX())) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(parameters.getHEADER_STRING());
        if (token != null) {
            // parse the token.

            String user = Jwts.parser()
                    .setSigningKey(parameters.getSECRET())
                    .parseClaimsJws(token.replace(parameters.getTOKEN_PREFIX(), ""))
                    .getBody()
                    .getSubject();
           
            
            Claims c=Jwts.parser()
                    .setSigningKey(parameters.getSECRET())
                    .parseClaimsJws(token.replace(parameters.getTOKEN_PREFIX(), ""))
                    .getBody();
            List<GrantedAuthority> auths=new ArrayList<>();
            
           c.forEach((key,value)->{
               if(value instanceof String)
                   if(((String) key).startsWith("role"))
                       auths.add(()->(String)value);
           }
           );
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, auths);
            }
            return null;
        }
        return null;
    }
}
