package com.example.SpringMySQLRest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToOne;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author David
 */
@NamedStoredProcedureQueries({
@NamedStoredProcedureQuery(name = "procedimiento", procedureName = "procedimiento", resultClasses = Persona.class),
@NamedStoredProcedureQuery(name = "insertarP", procedureName = "insertarP",resultClasses = Persona.class,
parameters = {
@StoredProcedureParameter(mode = ParameterMode.IN, name = "ageIn", type = Long.class),
@StoredProcedureParameter(mode = ParameterMode.IN, name = "emailIn", type = String.class),
@StoredProcedureParameter(mode = ParameterMode.IN, name = "fechaIn", type = String.class),
@StoredProcedureParameter(mode = ParameterMode.IN, name = "nomIn", type = String.class),
@StoredProcedureParameter(mode = ParameterMode.IN, name = "addressIn", type = Long.class),
})})
@Data
@ToString(exclude = "address")
@JsonIgnoreProperties(ignoreUnknown = false)
@Entity
@Table(name = "persona")
public class Persona {

    @ApiModelProperty(notes = "El ID generado por la base de datos")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ApiModelProperty(notes = "El nombre de la persona")
    @NotBlank(message = "error.nameBlank")
    @NotNull(message = "error.nameNull")
    private String name;

    @ApiModelProperty(notes = "La edad de la persona")
    @NotNull(message = "error.ageNull")
    private int age;

    @Email(message = "error.emailPattern")
    @NotNull(message = "error.emailNull")
    @ApiModelProperty(notes = "El correo electronico de la persona")
    @Column(name = "email", nullable = false)
    private String email;

    @ApiModelProperty(notes = "La fecha de nacimiento de la persona")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "error.dateNull")
    private LocalDate fecha;

//    @JsonIgnore
    @ApiModelProperty(notes = "La direccion de la persona")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @NotNull(message = "error.addressNull")
    private Address address;

}
