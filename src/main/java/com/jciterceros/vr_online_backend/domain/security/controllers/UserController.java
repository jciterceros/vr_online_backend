package com.jciterceros.vr_online_backend.domain.security.controllers;

import com.jciterceros.vr_online_backend.domain.security.models.User;
import com.jciterceros.vr_online_backend.domain.security.services.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserDetailsService userDetailsService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userDetailsService.createUser(user);
    }
}
