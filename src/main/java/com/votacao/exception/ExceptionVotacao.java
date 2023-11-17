package com.votacao.exception;

import com.votacao.enuns.MensagemException;
import org.springframework.http.HttpStatus;

public class ExceptionVotacao extends RuntimeException{
    private final HttpStatus httpStatus;

    public ExceptionVotacao(MensagemException mensagemException, HttpStatus httpStatus) {
        super(mensagemException.getErroMensagem(), null, true, false);
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return this.httpStatus.value();
    }
}
