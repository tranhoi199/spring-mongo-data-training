package com.pycogroup.superblog.service;

import com.pycogroup.superblog.exception.AlreadyExistCategory;
import com.pycogroup.superblog.exception.CategoryNotFound;
import com.pycogroup.superblog.model.Category;
import com.pycogroup.superblog.repository.CategoryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService{
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(Category category) {
        String categoryName = category.getCategoryname();
        Category existCategory = categoryRepository.findCategoryByCategoryname(categoryName);
        if(existCategory != null) {
            throw new AlreadyExistCategory(categoryName);
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category findCategoryById(ObjectId id) {
        Category existCategory = categoryRepository.findCategoryById(id);
        if(existCategory == null){
            throw new CategoryNotFound(id.toString());
        }
        return existCategory;
    }

    @Override
    public void findAndDeleteCategoryById(ObjectId id) {
        Category existCategory = categoryRepository.findCategoryById(id);
        if(existCategory == null){
            throw new CategoryNotFound(id.toString());
        }
        categoryRepository.delete(existCategory);
    }

    @Override
    public Category updateCategoryById(ObjectId id, Category updateCategory) {
        Category existCategory = findCategoryById(id);
        existCategory.setCategoryname(updateCategory.getCategoryname());
        existCategory.setDescription(updateCategory.getDescription());
        categoryRepository.save(existCategory);
        return existCategory;

    }
}
