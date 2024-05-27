package com.findar.bookstore.enums;


import lombok.Getter;

@Getter
public enum Constant {

    REQUEST_SUCCESSFULLY_TREATED("Request successfully treated"),
    LOGIN_SUCCESSFUL("Login successful");


    private final String message;


    Constant(String message){
        this.message= message;
    }
}
