/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.SpringMySQLRest.repository;

import com.example.SpringMySQLRest.model.BaseModel;
import com.example.SpringMySQLRest.model.Persona;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
//import org.springframework.security.access.prepost.PreAuthorize;

/**
 *
 * @author David
 */
//@RestResource(path = "personas",rel = "personas")
//@PreAuthorize("hasAuthority('MASTER')")
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    @Procedure
public void asjbnajs();
}
