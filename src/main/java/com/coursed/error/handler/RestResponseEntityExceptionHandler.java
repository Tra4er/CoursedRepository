package com.coursed.error.handler;

import com.coursed.error.exception.*;
import com.coursed.util.GenericResponse;
import com.coursed.util.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailParseException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.mail.MessagingException;
import javax.net.ssl.SSLHandshakeException;
import javax.validation.ValidationException;

/**
 * Created by Trach on 12/20/2016.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    public RestResponseEntityExceptionHandler() {
        super();
    }

    // 400
    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
                                                         final HttpStatus status, final WebRequest request) {
        LOGGER.error("400 Status Code: " + ex.getMessage());
        final BindingResult result = ex.getBindingResult();
        final GenericResponse bodyOfResponse = new GenericResponse(HttpStatus.BAD_REQUEST.value(), "fail",
                result.getGlobalErrors(), result.getFieldErrors());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers, final HttpStatus status,
                                                                  final WebRequest request) {
        LOGGER.error("400 Status Code: " + ex.getMessage());
        final BindingResult result = ex.getBindingResult();
        final GenericResponse bodyOfResponse = new GenericResponse(HttpStatus.BAD_REQUEST.value(), "fail",
                result.getGlobalErrors(), result.getFieldErrors());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> handleValidation(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("400 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse(HttpStatus.BAD_REQUEST.value(), "fail",
                ex.getMessage(), "Введені дані не пройшли валідацію.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({MailParseException.class})
    public ResponseEntity<Object> handleMailParser(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("400 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse(HttpStatus.BAD_REQUEST.value(), "fail",
                "WrongEmailCharacters", "Введений емейл є невірним.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({InvalidOldPasswordException.class})
    public ResponseEntity<Object> handleInvalidOldPassword(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("400 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse(HttpStatus.BAD_REQUEST.value(), "fail",
                "InvalidOldPassword", "Старий пароль який ви ввели є невірним.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ReCaptchaInvalidException.class})
    public ResponseEntity<Object> handleReCaptchaInvalid(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("400 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse(HttpStatus.BAD_REQUEST.value(), "fail",
                "InvalidReCaptcha", "Помилка підтвердження на людяність.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // 404
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFound(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("404 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse(HttpStatus.NOT_FOUND.value(), "fail",
                "UserNotFound", "Користувача із цим емейлом не існує.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({TokenNotFoundException.class})
    public ResponseEntity<Object> handleTokenNotFound(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("404 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse(HttpStatus.NOT_FOUND.value(), "fail",
                "TokenNotFound", "Не вдалось знайти ключ для даного користувача.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // 409
    @ExceptionHandler({UserAlreadyExistException.class})
    public ResponseEntity<Object> handleUserAlreadyExist(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("409 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse(HttpStatus.CONFLICT.value(), "fail",
                "UserAlreadyExist", "Профіль для даного емейлу вже існує. Введіть інший емейл.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    //    500
    @ExceptionHandler({ReCaptchaUnavailableException.class})
    public ResponseEntity<Object> handleReCaptchaUnavailable(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("500 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error",
                "InvalidReCaptcha", "Реєстрація неможлива в даний момент. Спробуйте пізніше.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


    @ExceptionHandler({MessagingException.class, SSLHandshakeException.class})
    public ResponseEntity<Object> handleMessagingError(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("500 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error",
                "SendingEmailFailed", "Щось пішло не так. Ми не змогли відправити вам листа на емейл.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({RegistrationException.class})
    public ResponseEntity<Object> handleRegistrationError(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("500 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error",
                "RegistrationFailed", "Щось пішло не так. Ми не змогли зареєструвати ваш профіль.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("500 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error",
                "InternalError", "Сталась помилка");
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
