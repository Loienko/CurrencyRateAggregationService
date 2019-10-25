package net.ukr.dreamsicle.security.jwt;

import net.ukr.dreamsicle.Application;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Authentication exception for {@link Application} application.
 *
 * @author yurii.loienko
 * @version 1.0
 */
@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.addHeader("WWW-Authenticate", "UNAUTHORIZED");
        response.setContentType("application/json");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "HTTP Status 401 - " + e.getMessage());
    }
}