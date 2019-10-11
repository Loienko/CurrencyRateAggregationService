package net.ukr.dreamsicle.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Lombok;
import lombok.NoArgsConstructor;

/**
 * ExceptionResponse class for return message with exception
 * Used by {@link Lombok} to create template methods of an object like getters. setters, etc.
 *
 * @author yurii.loienko
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

    private String errorMessage;

    private String requestedURI;
}
