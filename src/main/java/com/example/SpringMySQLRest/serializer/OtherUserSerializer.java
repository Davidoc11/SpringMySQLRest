package com.example.SpringMySQLRest.serializer;

import com.example.SpringMySQLRest.model.Comment;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.List;

/**
 * @author David
 */
public class OtherUserSerializer extends JsonSerializer<List<Comment>> {

    @Override
    public void serialize(List<Comment> otherUsers, JsonGenerator generator, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        generator.writeStartArray();

        for (Comment task : otherUsers) {
            generator.writeStartObject("com");
            generator.writeNumberField("id",task.getId());
            generator.writeStringField("text", task.getText());            
            generator.writeEndObject();
        }
        generator.writeEndArray();
    }

}
