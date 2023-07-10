package com.example.springsecuritydemo.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonMapper extends ObjectMapper {

    /**
     *  Maps an object to a json string
     *
     * @param object the object to convert
     * */
    public String objectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        return writeValueAsString(object);
    }
}
