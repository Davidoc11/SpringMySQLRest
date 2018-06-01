package com.example.SpringMySQLRest.service;

import com.example.SpringMySQLRest.model.Persona;
import com.example.SpringMySQLRest.repository.PersonaRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author David
 */
@Service
public class PersonaServiceImpl implements PersonaService {
private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    EntityManager entityManager;

    @Autowired
    PersonaRepository personaRepository;

    public List<Persona> todos() {
        StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("procedimiento");
        storedProcedureQuery.execute();
        return storedProcedureQuery.getResultList();
    }

    public void insertarP(Persona p) {
        log.info(p.toString());
        StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("insertarP");
        storedProcedureQuery.setParameter("ageIn", p.getAge());
        storedProcedureQuery.setParameter("emailIn", p.getEmail());
        storedProcedureQuery.setParameter("fechaIn", p.getFecha().toString().replaceAll("-", "/"));
        storedProcedureQuery.setParameter("nomIn", p.getName());
        storedProcedureQuery.setParameter("addressIn", p.getName());
        storedProcedureQuery.execute();
        //return (Persona)storedProcedureQuery.getSingleResult();
    }

    public List<Persona> obtenerTodas() {
        
        return personaRepository.findAll();
    }
}
