package net.ukr.dreamsicle.exception;

import com.mongodb.MongoWriteException;
import io.jsonwebtoken.JwtException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Objects;

/**
 * ExceptionHandlerControllerAdvice class that mark with annotation @ControllerAdvice and handle exception from app
 *
 * @author yurii.loienko
 * @version 1.0
 * @deprecated handleResourceIsStale
 */
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody
    ExceptionResponse handleResourceNotFound(final ResourceNotFoundException exception,
                                             final HttpServletRequest request) {
        return new ExceptionResponse(exception.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ExceptionResponse handleException(final Exception exception,
                                      final HttpServletRequest request) {
        return new ExceptionResponse(exception.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public @ResponseBody
    ExceptionResponse databaseError(final Exception exception,
                                    final HttpServletRequest request) {
        return new ExceptionResponse(exception.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler({ResourceIsStaleException.class, IllegalStateException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @Deprecated
    public @ResponseBody
    ExceptionResponse handleResourceIsStale(final Exception exception,
                                            final HttpServletRequest request) {
        return new ExceptionResponse(exception.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler({ArgumentNotValidException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ExceptionResponse validationError(final MethodArgumentNotValidException ex,
                                      final HttpServletRequest request) {

        return new ExceptionResponse(
                Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(CustomDataAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public @ResponseBody
    ExceptionResponse notUniqueValueHandle(final Exception exception,
                                           final HttpServletRequest request) {

        return new ExceptionResponse(exception.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler({IllegalArgumentException.class, EmailExistsException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ExceptionResponse notValidData(final Exception exception,
                                   final HttpServletRequest request) {

        return new ExceptionResponse(exception.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler({JwtAuthenticationException.class, JwtException.class, AuthenticationException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public @ResponseBody
    ExceptionResponse unauthorized(final Exception ex,
                                   final HttpServletRequest request) {

        return new ExceptionResponse(ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ExceptionResponse badCredential(final Exception ex,
                                    final HttpServletRequest request) {

        return new ExceptionResponse(ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(CollectionNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody
    ExceptionResponse collectionNotFound(final Exception ex,
                                         final HttpServletRequest request) {

        return new ExceptionResponse(ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(MongoWriteException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public @ResponseBody
    ExceptionResponse duplicateKey(final Exception ex,
                                   final HttpServletRequest request) {

        return new ExceptionResponse(ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(JMSException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public @ResponseBody
    ExceptionResponse jmsHandle(final Exception ex,
                                final HttpServletRequest request) {

        return new ExceptionResponse(ex.getMessage(), request.getRequestURI());
    }
}
