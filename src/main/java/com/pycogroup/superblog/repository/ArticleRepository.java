package com.pycogroup.superblog.repository;

import com.pycogroup.superblog.model.Article;
import com.pycogroup.superblog.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ArticleRepository extends MongoRepository<Article, ObjectId>,
        QuerydslPredicateExecutor<Article> {
    Article findArticleById(ObjectId id);
//    Article findById(ObjectId id);
}
