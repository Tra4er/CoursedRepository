package com.coursed.error.handler;

import com.coursed.error.exception.*;
import com.coursed.util.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailParseException;
import org.springframework.security.access.AccessDeniedException;
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
        final GenericResponse bodyOfResponse = new GenericResponse(result.getGlobalErrors(), result.getFieldErrors());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers, final HttpStatus status,
                                                                  final WebRequest request) {
        LOGGER.error("400 Status Code: " + ex.getMessage());
        final BindingResult result = ex.getBindingResult();
        final GenericResponse bodyOfResponse = new GenericResponse(result.getGlobalErrors(), result.getFieldErrors());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> handleValidation(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("400 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse(ex.getMessage(), "Введені дані не пройшли валідацію.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({MailParseException.class})
    public ResponseEntity<Object> handleMailParser(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("400 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse("WrongEmailCharacters", "Введений емейл є невірним.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({InvalidOldPasswordException.class})
    public ResponseEntity<Object> handleInvalidOldPassword(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("400 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse("InvalidOldPassword", "Старий пароль який ви ввели є невірним.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ReCaptchaInvalidException.class})
    public ResponseEntity<Object> handleReCaptchaInvalid(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("400 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse("InvalidReCaptcha", "Помилка підтвердження на людяність.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgument(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("400 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse("BadRequestParam", "Ви передали невірні парметри.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // 403
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDenied(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("403 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse("AccessDenied", "Вам закритий доступ до даного ресурсу.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    // 404
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFound(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("404 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse("UserNotFound", "Користувача із цим емейлом не існує.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({TokenNotFoundException.class})
    public ResponseEntity<Object> handleTokenNotFound(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("404 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse("TokenNotFound", "Не вдалось знайти ключ для даного користувача.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // 409
    @ExceptionHandler({UserAlreadyExistException.class})
    public ResponseEntity<Object> handleUserAlreadyExist(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("409 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse("UserAlreadyExist", "Профіль для даного емейлу вже існує. Введіть інший емейл.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    // 416
    @ExceptionHandler({PageSizeTooBigException.class})
    public ResponseEntity<Object> handlePageSize(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("416 Status Code: " + ex.getMessage());
        final GenericResponse bodyOfResponse = new GenericResponse("PageSizeIsTooBig",
                "Ви перевищили дозволений розмір елементів для однієї сторінки.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE,
                request);
    }

    //    500
    @ExceptionHandler({ReCaptchaUnavailableException.class})
    public ResponseEntity<Object> handleReCaptchaUnavailable(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("500 Status Code: " + ex);
        final GenericResponse bodyOfResponse = new GenericResponse("ReCaptchaUnavailable",
                "Реєстрація неможлива в даний момент. Спробуйте пізніше.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


    @ExceptionHandler({MessagingException.class, SSLHandshakeException.class})
    public ResponseEntity<Object> handleMessagingError(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("500 Status Code: " + ex);
        final GenericResponse bodyOfResponse = new GenericResponse("SendingEmailFailed",
                "Щось пішло не так. Ми не змогли відправити вам листа на емейл.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({RegistrationException.class})
    public ResponseEntity<Object> handleRegistrationError(final RuntimeException ex, final WebRequest request) {
        LOGGER.error("500 Status Code: " + ex);
        final GenericResponse bodyOfResponse = new GenericResponse("RegistrationFailed",
                "Щось пішло не так. Ми не змогли зареєструвати ваш профіль.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleInternal(final Exception ex, final WebRequest request) {
        LOGGER.error("500 Status Code: " + ex);
        ex.printStackTrace();
        final GenericResponse bodyOfResponse = new GenericResponse("InternalError", "Сталась помилка");
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
