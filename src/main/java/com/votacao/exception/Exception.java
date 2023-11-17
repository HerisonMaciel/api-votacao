package com.votacao.exception;

import com.votacao.enuns.MensagemException;
import org.springframework.http.HttpStatus;

public class Exception extends RuntimeException{
    private final HttpStatus httpStatus;

    public Exception(MensagemException mensagemException, HttpStatus httpStatus) {
        super(mensagemException.getErroMensagem(), null, true, false);
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return this.httpStatus.value();
    }
}
