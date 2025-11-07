package com.aprobapp.aprobapp.model;

import java.util.Date;

public class User {
    private final Integer id;
    private final String firstName;
    private final String surName;
    private final String email;
    private final String password;
    private final Date registrationDay;
    private final String study;
    private final int root;

    public User(Integer id, String firstName, String surName, String email, String password,
                Date registrationDay, String study, int root) {
        this.id = id;
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
        this.password = password;
        this.registrationDay = registrationDay;
        this.study = study;
        this.root = root;
    }

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getSurName() {
        return surName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getStudy() {
        return study;
    }
}