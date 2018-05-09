/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.SpringMySQLRest.repository;

import com.example.SpringMySQLRest.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author David
 */
//@RestResource(path = "address")
public interface AddressRepository extends JpaRepository<Address, Long>{
    
}
