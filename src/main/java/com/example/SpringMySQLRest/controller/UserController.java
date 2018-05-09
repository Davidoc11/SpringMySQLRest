package com.example.SpringMySQLRest.controller;

import com.example.SpringMySQLRest.exception.ResourceNotFoundException;
import com.example.SpringMySQLRest.model.ApplicationUser;
import com.example.SpringMySQLRest.repository.UserRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author David
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    
    
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApplicationUser> registrarUsuario(@RequestBody @Valid ApplicationUser userPost) throws ResourceNotFoundException{
        userPost.setPassword(bCryptPasswordEncoder.encode(userPost.getPassword()));
        ApplicationUser user=userRepository.save(userPost);
        if(user==null)
            throw new ResourceNotFoundException();
        return ResponseEntity.ok(user);
    }

}
