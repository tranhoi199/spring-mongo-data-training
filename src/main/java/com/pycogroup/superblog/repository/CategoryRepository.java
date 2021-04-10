package com.pycogroup.superblog.repository;

import com.pycogroup.superblog.model.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CategoryRepository extends MongoRepository<Category, String>,
        QuerydslPredicateExecutor<Category>
{
    Category findCategoryByCategoryname(String name);
    Category findCategoryById(ObjectId id);
}
