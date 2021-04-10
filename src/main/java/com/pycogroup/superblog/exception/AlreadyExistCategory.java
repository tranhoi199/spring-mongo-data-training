package com.pycogroup.superblog.exception;

public class AlreadyExistCategory extends RuntimeException{
    public  AlreadyExistCategory(String catename){
        super(String.format("Categoryname with %s is already exist", catename));
    }
}
