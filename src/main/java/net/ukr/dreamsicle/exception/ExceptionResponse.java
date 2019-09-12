package net.ukr.dreamsicle.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

    private  String errorMessage;

    private  String requestedURI;
}
