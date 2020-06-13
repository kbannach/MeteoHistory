package org.kbannach.config;

import lombok.Value;

@Value
public class ErrorResponse {

    ErrorType errorType;
    String message;
}
