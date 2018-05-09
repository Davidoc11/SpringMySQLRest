package com.example.SpringMySQLRest.controller;

import com.example.SpringMySQLRest.exception.ResourceNotFoundException;
import com.example.SpringMySQLRest.model.Address;
import com.example.SpringMySQLRest.model.BaseModel;
import com.example.SpringMySQLRest.model.Persona;
import com.example.SpringMySQLRest.model.enums.Status;
import com.example.SpringMySQLRest.repository.PersonaRepository;
import com.example.SpringMySQLRest.service.PersonaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author David
 */
@RestController()
@RequestMapping(value = "/api/personas", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "personas", description = "Operaciones relacionadas con el catalogo de Personas")
@ApiResponses(value = {
    @ApiResponse(code = 401, message = "No se esta autorizado para ver el recurso")
    ,
        @ApiResponse(code = 403, message = "No se tienen las credenciales necesarias para accesar al recurso")
    ,
        @ApiResponse(code = 404, message = "El recurso no existe")
}
)
public class PersonaController {
    

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    Environment env;
    @Autowired
    PersonaRepository personaRepository;

    @Value("${jwt.secret}")
    private String SECRET;
    @Autowired
    PersonaService service;

    @GetMapping(value = "")
    @ApiOperation(value = "Obtiene una lista de personas", response = BaseModel.class)
    public ResponseEntity<BaseModel> getAllUsers() {
        List<Persona> personas = personaRepository.findAll();
        BaseModel bm = new BaseModel();
        bm.setStatus(Status.bad);
        bm.setResult(personas);
        return ResponseEntity.ok(bm);

    }

    @GetMapping(value = "/pruebaa")
    public String jasb() {
        
     
        
        return "";
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obtiene una persona con base en su ID", response = Persona.class)
    public ResponseEntity<Persona> findPersona(@PathVariable Long id) throws ResourceNotFoundException {
        Persona persona = personaRepository.findOne(id);
        if (persona == null) {
            throw new ResourceNotFoundException("persona");
        } else {
            return ResponseEntity.ok(persona);
        }
    }

    @ApiOperation(value = "Agrega una persona al catalogo", response = Persona.class)
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Persona> addPersona(@Valid @RequestBody Persona persona) {
        //service.insertarP(persona);
        return ResponseEntity.ok(personaRepository.save(persona));

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualiza una persona con base en su ID", response = Persona.class)
    public ResponseEntity<Persona> updatePersona(@Valid @PathVariable Long id, @RequestBody Persona persona)
            throws ResourceNotFoundException {
        Persona p = personaRepository.findOne(id);
        if (p == null) {
            throw new ResourceNotFoundException();
        }
        return ResponseEntity.ok(personaRepository.save(persona));
    }

    //, NullPointerException.class
    @ExceptionHandler({ResourceNotFoundException.class, DataIntegrityViolationException.class})
    void handleBadRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler({io.jsonwebtoken.ExpiredJwtException.class})
    void token(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Elimina una persona del catalogo con base en su ID")
    public void deletePersona(@PathVariable Long id) throws ResourceNotFoundException {
        Persona p = personaRepository.findOne(id);
        if (p != null) {
            personaRepository.delete(id);
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping("/{id}/address")
    @ApiOperation(value = "Obtiene la direccion asociada a una persona con base en el ID de la persona", response = Address.class)
    public ResponseEntity<Address> getPersonaAddress(@PathVariable Long id) throws ResourceNotFoundException {
        Persona p = personaRepository.findOne(id);
        if (p == null) {

            throw new ResourceNotFoundException("persona");
        }
        if (p.getAddress() == null) {
            throw new ResourceNotFoundException("address");
        } else {
            return ResponseEntity.ok(p.getAddress());
        }
    }

}
