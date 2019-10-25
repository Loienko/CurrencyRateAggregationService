package net.ukr.dreamsicle.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * JwtAuthenticationException class that extends {@link AuthenticationException}
 *
 * @author yurii.loienko
 * @version 1.0
 */
public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
