package net.ukr.dreamsicle.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

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
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ExceptionResponse handleResourceIsStale(final Exception exception,
                                            final HttpServletRequest request) {
        return new ExceptionResponse(exception.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler({ArgumentNotValidException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ExceptionResponse validationError(final HttpServletRequest request) {
        return new ExceptionResponse("Not valid Data", request.getRequestURI());
    }
}
