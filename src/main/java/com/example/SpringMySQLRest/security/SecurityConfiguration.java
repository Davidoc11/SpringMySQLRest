package com.example.SpringMySQLRest.security;

import com.example.SpringMySQLRest.security.jwt.JWTAuthenticationFilter;
import com.example.SpringMySQLRest.security.jwt.JWTAuthorizationFilter;
import com.example.SpringMySQLRest.security.jwt.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author David
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${jwt.secret}")
    public String SECRET;
    @Value("${jwt.expiration}")
    public long EXPIRATION_TIME; // 10 days
    @Value("${jwt.prefix}")
    public String TOKEN_PREFIX;
    @Value("${jwt.header}")
    public String HEADER_STRING;
    @Value("${jwt.sign}")
    public String SIGN_UP_URL;
    
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /*public SecurityConfiguration(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        Parameters p = new Parameters(SECRET, EXPIRATION_TIME, TOKEN_PREFIX, HEADER_STRING, SIGN_UP_URL);
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% SECRET: " + SECRET);
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% EXPIRATION: " + EXPIRATION_TIME);
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% PREFIX: " + TOKEN_PREFIX);
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% HEADER: " + HEADER_STRING);
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% SIGN: " + SIGN_UP_URL);
        http.csrf().disable().authorizeRequests()
                 .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.GET, "/login").permitAll()
                .antMatchers("/api**").fullyAuthenticated()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), p))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), p));
        
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**");
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        
    }
    /*
    @Autowired
    private SecurityService securityService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**").fullyAuthenticated();
        http.httpBasic();
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService);
    }
     */
}
