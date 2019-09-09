package net.ukr.dreamsicle.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody
    ExceptionResponse handleResourceNotFound(final ResourceNotFoundException exception,
                                             final HttpServletRequest request) {
        log.info("" + exception.getClass().getSimpleName());
        return new ExceptionResponse(exception.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ExceptionResponse handleException(final Exception exception,
                                      final HttpServletRequest request) {
        log.info("" + exception.getClass().getSimpleName());
        return new ExceptionResponse(exception.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public String databaseError(Exception exception) {
        log.error("Request raised " + exception.getClass().getSimpleName());
        return "databaseError";
    }
}
