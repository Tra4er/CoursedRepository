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

    private Integer code;
    private String status;
    private String message;
    private Object data;

    /**
     *
     * @param code HTTP status code
     * @param status "success", "fail", "error"
     * @param globalErrors Use only for "fail" or "error" statuses. Short error description. Example: "UserAlreadyExist"
     * @param fieldErrors Use for response body or to describe your error.
     */
    // TODO TEST
    public GenericResponse(Integer code, String status, List<ObjectError> globalErrors, List<FieldError> fieldErrors) {
        super();
        final ObjectMapper mapper = new ObjectMapper();
        try {
            this.code = code;
            this.status = status;
            this.message = mapper.writeValueAsString(globalErrors);
            this.data = mapper.writeValueAsString(fieldErrors);
        } catch (final JsonProcessingException e) {
            this.code = 500;
            this.status = "error";
            this.message = "Internal Error";
            this.data = "";
        }
    }

    /**
     *
     * @param code HTTP status code
     * @param status "success", "fail", "error"
     * @param data Use for response body or to describe your error.
     */
    public GenericResponse(Integer code, String status, Object data) {
        this(code, status, null, data);
    }

    /**
     *
     * @param code HTTP status code
     * @param status "success", "fail", "error"
     * @param message Use only for "fail" or "error" statuses. Short error description. Example: "UserAlreadyExist"
     * @param data Use for response body or to describe your error.
     */
    public GenericResponse(Integer code, String status, String message, Object data) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
                "code='" + code + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
