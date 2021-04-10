package com.pycogroup.superblog.controller;

import com.pycogroup.superblog.api.CategoriesApi;
import com.pycogroup.superblog.api.model.*;
import com.pycogroup.superblog.model.Category;
import com.pycogroup.superblog.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class CategoryController implements CategoriesApi {
    @Autowired
    CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ResponseEntity<CategoryResponseModel> createCategory(@Valid CreateCategoryRequest createCategoryRequest) {
        Category category = modelMapper.map(createCategoryRequest, Category.class);
        System.out.println("category description"+category.getDescription());
        Category persistCate = categoryService.createCategory(category);
        CategoryResponseModel result = new CategoryResponseModel();
        result.setCategoryname(persistCate.getCategoryname());
        result.setDescription(persistCate.getDescription());
        result.setId(persistCate.getId().toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ObjectDeletionSuccessResponse> deleteCategory(String cateId) {
        categoryService.findAndDeleteCategoryById(new ObjectId(cateId));
        ObjectDeletionSuccessResponse result = new ObjectDeletionSuccessResponse();
        result.setId(cateId);
        result.responseCode(200);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryResponseModel> getCategory(String cateId) {
        Category existCategory = categoryService.findCategoryById(new ObjectId(cateId));
        CategoryResponseModel result = new CategoryResponseModel();
        result.setId(existCategory.getId().toString());
        result.setCategoryname(existCategory.getCategoryname());
        result.setDescription(existCategory.getDescription());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private ResponseEntity<CategoryListResponse> buildCategoryListResponse(List<Category> categoryList) {
        CategoryListResponse categoryListResponse = new CategoryListResponse();

        if (categoryList != null) {
            categoryList.forEach(item -> categoryListResponse.addCategoriesItem(modelMapper.map(item, com.pycogroup.superblog.api.model.CategoryResponseModel.class)));
        }
        return new ResponseEntity(categoryListResponse, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<CategoryListResponse> getCategoryList() {
        List<Category> categories = categoryService.getAllCategories();
        return buildCategoryListResponse(categories);
    }

    @Override
    public ResponseEntity<CategoryResponseModel> updateCategory(String cateId, @Valid UpdateCategoryRequest updateCategoryRequest) {
        Category category = modelMapper.map(updateCategoryRequest, Category.class);
        Category updatedCategory = categoryService.updateCategoryById(new ObjectId(cateId), category);

        CategoryResponseModel result = new CategoryResponseModel();
        result.setDescription(updatedCategory.getDescription());
        result.setCategoryname(updatedCategory.getCategoryname());
        result.setId(updatedCategory.getId().toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
