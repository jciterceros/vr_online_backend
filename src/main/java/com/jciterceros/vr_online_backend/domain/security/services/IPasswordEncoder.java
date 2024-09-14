package com.jciterceros.vr_online_backend.domain.security.services;

public interface IPasswordEncoder {
    String encode(String password);

    Boolean matches(String password, String encodedPassword);
}
