package com.jciterceros.vr_online_backend.controller;

import com.jciterceros.vr_online_backend.model.User;
import com.jciterceros.vr_online_backend.service.UserDetailsService;
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
