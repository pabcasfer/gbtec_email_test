package com.gbtec.email.api.client.business.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SuccessOrErrorResponse implements Serializable {
    private EmailExceptionDTO exception;

    public static SuccessOrErrorResponse success(){
        return new SuccessOrErrorResponse(null);
    }

    public static SuccessOrErrorResponse error(EmailExceptionDTO e){
        return new SuccessOrErrorResponse(e);
    }

    public boolean ok() {
        return this.exception == null;
    }
}
