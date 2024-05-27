package com.findar.bookstore.enums;


import lombok.Getter;

@Getter
public enum Activity {


    LOGIN("User login to app"),
    SIGNUP("User onboard on app"),
    BORROW("User borrow book"),
    RETURNED("User returned book"),
    UPDATED("Admin updated book details"),
    DISABLE("Admin disable USE") ;


    private final String description;


    Activity(String description){

        this.description= description;

    }
}
