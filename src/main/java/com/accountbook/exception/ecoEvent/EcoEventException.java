package com.accountbook.exception.ecoEvent;

import lombok.Getter;

@Getter
public class EcoEventException extends RuntimeException{
    private EcoEventExceptionCode ecoEventExceptionCode;

    public EcoEventException() {
        super();
    }
    public EcoEventException(String message) {
        super(message);
    }

    public EcoEventException(EcoEventExceptionCode ecoEventExceptionCode) {
        this.ecoEventExceptionCode = ecoEventExceptionCode;
    }
}
