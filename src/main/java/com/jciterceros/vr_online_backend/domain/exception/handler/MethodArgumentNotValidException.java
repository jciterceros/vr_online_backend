package com.jciterceros.vr_online_backend.domain.exception.handler;

@SuppressWarnings("serial")
public class MethodArgumentNotValidException extends RuntimeException{
    public MethodArgumentNotValidException(String msg) {
        super(msg);
    }
}
