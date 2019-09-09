package net.ukr.dreamsicle.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    @Getter
    private  String errorMessage;
    @Getter
    private  String requestedURI;
}
