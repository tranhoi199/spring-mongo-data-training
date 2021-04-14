package com.pycogroup.superblog.service;

import com.pycogroup.superblog.exception.AlreadyExistCategory;
import com.pycogroup.superblog.exception.CategoryNotFound;
import com.pycogroup.superblog.model.Category;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CategoryServiceTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CategoryService categoryService;

    private static final int INIT_CATE_NUMBER = 5;

    private ArrayList<String> listOfIdCate = new ArrayList<>();
    @Before
    public void init(){
        mongoTemplate.remove(Category.class).all();
        for (int i = 0; i < INIT_CATE_NUMBER; i++){
            Category category = Category.builder()
                    .categoryname(RandomStringUtils.random(50))
                    .description(RandomStringUtils.random(10))
                    .build();
            mongoTemplate.save(category);
            listOfIdCate.add(category.getId().toString());

        }
    }

    @Test
    public void testFindAllMustReturnEnoughQuantity() {
        List<Category> categoryList = categoryService.getAllCategories();
        Assert.assertEquals(INIT_CATE_NUMBER, categoryList.size());
    }

    @Test
    public void testUpdateCategoryName(){
        String updateCategoryName = "test";
        ObjectId id = new ObjectId(listOfIdCate.get(0));
        Category updatedCategory = new Category();
        updatedCategory.setCategoryname("test");
        categoryService.updateCategoryById(id, updatedCategory);
        Assert.assertEquals(updateCategoryName, categoryService.findCategoryById(id).getCategoryname());
    }

    @Test
    public void testCreateCategory(){
        Category category = Category.builder()
                .categoryname(RandomStringUtils.random(50))
                .description(RandomStringUtils.random(10))
                .build();
        categoryService.createCategory(category);
        List<Category> categoryList = categoryService.getAllCategories();
        Assert.assertEquals(INIT_CATE_NUMBER + 1, categoryList.size());
    }

    @Test(expected = AlreadyExistCategory.class)
    public void testCreateDuplicateCategoryNameThrowException(){
        Category category = Category.builder()
                .categoryname(RandomStringUtils.random(50))
                .description(RandomStringUtils.random(10))
                .build();
        categoryService.createCategory(category);
        categoryService.createCategory(category);
    }

    @Test(expected = CategoryNotFound.class)
    public void testFindInvalidCategoryId(){
        categoryService.findCategoryById(ObjectId.get());
    }

    @Test
    public void testDeleteCategory(){
        categoryService.findAndDeleteCategoryById(categoryService.getAllCategories().get(0).getId());
        List<Category> categoryList = categoryService.getAllCategories();
        Assert.assertEquals(INIT_CATE_NUMBER - 1, categoryList.size());
    }

    @Test(expected = CategoryNotFound.class)
    public void testDeleteInvalidCategoryId(){
        categoryService.findAndDeleteCategoryById(ObjectId.get());
    }



}
