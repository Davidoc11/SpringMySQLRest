package com.example.SpringMySQLRest.security;

import com.example.SpringMySQLRest.model.ApplicationUser;
import com.example.SpringMySQLRest.repository.UserRepository;
import java.util.Arrays;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author David
 */
@Service
public class UserDetailsSecurityService implements UserDetailsService {
private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(ApplicationUser user) {
        Collection<? extends GrantedAuthority> authorities;
        if (user.getUsername().equals("david")) {
            log.info("Entro a admin");
            authorities = Arrays.asList(() -> "ROLE_ADMIN",()->"MASTER");
        } else {
            log.info("Entro a user");
            authorities = Arrays.asList(() -> "USER");
        }
       
        return authorities;
    }

}
