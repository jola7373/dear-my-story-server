package com.qnnect.auth;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String socialId){
        super(socialId + " NotFoundException");
    }
}

