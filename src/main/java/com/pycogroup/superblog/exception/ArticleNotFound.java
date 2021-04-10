package com.pycogroup.superblog.exception;

public class ArticleNotFound extends RuntimeException{
    public ArticleNotFound(String articleID){
        super(String.format("Article with id %s not found", articleID));
    }
}
