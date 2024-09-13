package com.jciterceros.vr_online_backend.service;

import com.jciterceros.vr_online_backend.model.User;

import java.util.UUID;

public interface UserService {
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(User user);
    User getUserById(Long id);
}
