package com.pycogroup.superblog.exception;

public class CategoryNotFound extends RuntimeException{
    public CategoryNotFound(String id){
        super(String.format("Category with id %s is not found", id));
    }
}
