package com.example.SpringMySQLRest.serializer;

import com.example.SpringMySQLRest.model.Persona;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 * @author David
 */
public class UserSerializer extends JsonSerializer<Persona> {

    @Override
    public void serialize(Persona user, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        jg.writeStartObject("user");
        jg.writeNumberField("id",user.getId());
        jg.writeStringField("name", user.getName());
        jg.writeNumberField("age", user.getAge());
        jg.writeStringField("fecha", user.getFecha().toString());
        jg.writeEndObject();
    }

}
