package com.jciterceros.vr_online_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.jciterceros.vr_online_backend.domain"})
public class VrOnlineBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(VrOnlineBackendApplication.class, args);
    }
}
