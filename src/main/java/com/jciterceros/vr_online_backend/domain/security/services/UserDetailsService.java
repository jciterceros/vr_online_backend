package com.jciterceros.vr_online_backend.domain.security.services;

import com.jciterceros.vr_online_backend.domain.security.models.User;

public interface UserDetailsService {
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(User user);
    User getUserById(Long id);
}
