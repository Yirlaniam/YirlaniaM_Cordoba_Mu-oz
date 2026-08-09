package com.ufide.biblioapp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerarHash {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println("Hash para password123:");
        System.out.println(encoder.encode("password123"));
    }
}