package com.jciterceros.vr_online_backend.exception;

@SuppressWarnings("serial")
public class MethodArgumentNotValidException extends RuntimeException{
    public MethodArgumentNotValidException(String msg) {
        super(msg);
    }
}
