package com.gbtec.email.business.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmailException extends Exception {
    private final EmailExceptionType type;
    private final String message;
}
