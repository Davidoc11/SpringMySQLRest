/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.SpringMySQLRest.service;

import com.example.SpringMySQLRest.model.Persona;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author David
 */

public interface PersonaService {
    public List<Persona> todos();
    public void insertarP(Persona p);
    public List<Persona> obtenerTodas();
    
}
