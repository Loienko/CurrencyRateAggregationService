package net.ukr.dreamsicle.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HttpHeaderProvider {
    public static  <T> HttpEntity<T> getHeader(T model, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(model, headers);
    }
}
