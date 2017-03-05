package com.coursed.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * Created by Trach on 1/12/2017.
 */
public class GenericResponse {

    private String message;
    private Object data;

    public GenericResponse(String message) {
        this(message, new Object());
    }

    /**
     *
     * @param data Use for response body or to describe your error.
     */
    public GenericResponse(Object data) {
        this("", data);
    }

    /**
     *
     * @param message Use only for "fail" or "error" statuses. Short error description. Example: "UserAlreadyExist"
     * @param data Use for response body or to describe your error.
     */
    public GenericResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    /**
     *
     * @param globalErrors Use only for "fail" or "error" statuses. Short error description. Example: "UserAlreadyExist"
     * @param fieldErrors Use for response body or to describe your error.
     */
    // TODO TEST
    public GenericResponse(List<ObjectError> globalErrors, List<FieldError> fieldErrors) {
        super();
        final ObjectMapper mapper = new ObjectMapper();
        try {
            this.message = mapper.writeValueAsString(globalErrors);
            this.data = mapper.writeValueAsString(fieldErrors);
        } catch (final JsonProcessingException e) {
            this.message = "Internal Error";
            this.data = "";
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GenericResponse{" +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
