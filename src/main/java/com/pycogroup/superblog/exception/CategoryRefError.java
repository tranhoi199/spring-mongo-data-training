package com.pycogroup.superblog.exception;

public class CategoryRefError extends RuntimeException{
    public CategoryRefError(String cateId){
        super(String.format("Category with id has reference from articles", cateId));
    }
}
