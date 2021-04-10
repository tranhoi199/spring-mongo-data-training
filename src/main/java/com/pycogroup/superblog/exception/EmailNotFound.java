package com.pycogroup.superblog.exception;

public class EmailNotFound extends RuntimeException{
    public EmailNotFound(String email){
        super(String.format("User with email %s not found", email));
    }
}
