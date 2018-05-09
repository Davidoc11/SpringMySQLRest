/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.SpringMySQLRest.repository;

import com.example.SpringMySQLRest.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 *
 * @author David
 */

public interface UserRepository extends JpaRepository<ApplicationUser, Long>{
    public ApplicationUser findByUsername(String username);
    
    
    
    @Override
    @PreAuthorize("hasRole('ROLE_MASTER')")
    public ApplicationUser save(ApplicationUser user);
    
}
