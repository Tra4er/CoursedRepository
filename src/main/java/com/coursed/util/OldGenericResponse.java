package com.coursed.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * Created by Trach on 12/20/2016.
 */
public class OldGenericResponse {

    private String message;
    private String error;

    public OldGenericResponse(String message) {
        super();
        this.message = message;
    }

    public OldGenericResponse(String message, String error) {
        super();
        this.message = message;
        this.error = error;
    }

    public OldGenericResponse(List<FieldError> fieldErrors, List<ObjectError> globalErrors) {
        super();
        final ObjectMapper mapper = new ObjectMapper();
        try {
            this.message = mapper.writeValueAsString(fieldErrors);
            this.error = mapper.writeValueAsString(globalErrors);
        } catch (final JsonProcessingException e) {
            this.message = "";
            this.error = "";
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
