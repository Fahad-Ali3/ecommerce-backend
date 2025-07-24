package com.ecommerce.backend.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
    public static void main(String[] args) {
        BCryptPasswordEncoder cryptPasswordEncoder=new BCryptPasswordEncoder();
        System.out.println(cryptPasswordEncoder.encode("khan1234"));
        System.out.println(cryptPasswordEncoder.encode("Saeed124@"));
    }
}
