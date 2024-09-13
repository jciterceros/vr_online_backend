package com.jciterceros.vr_online_backend.service;

public interface IPasswordEncoder {
    String encode(String password);

    Boolean matches(String password, String encodedPassword);
}
