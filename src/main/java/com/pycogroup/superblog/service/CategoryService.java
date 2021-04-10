package com.pycogroup.superblog.service;

import com.pycogroup.superblog.model.Category;
import org.bson.types.ObjectId;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category createCategory(Category category);
    Category findCategoryById(ObjectId id);
    void findAndDeleteCategoryById(ObjectId id);
    Category updateCategoryById(ObjectId id, Category updateCategory);
}
