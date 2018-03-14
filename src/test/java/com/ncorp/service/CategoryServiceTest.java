package com.ncorp.service;

import com.ncorp.Application;
import com.ncorp.entity.Category;
import com.ncorp.entity.SubCategory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CategoryServiceTest extends ServiceTestBase {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void testNoCategory(){
        List<Category> categories = categoryService.getAllCategories(false);

        assertEquals(0, categories.size());
    }

    @Test
    public void testCreateCategory(){
        Long id = categoryService.createCategory("firstCategory");

        List<Category> categories = categoryService.getAllCategories(false);

        assertEquals(1, categories.size());
        assertNotNull(id);
    }

    @Test
    public void testGetCategory(){
        String categoryName = "name";

        Long id = categoryService.createCategory(categoryName);

        Category category = categoryService.getCategory(id, false);

        assertEquals(category.getName(), categoryName);
    }

    @Test
    public void testCreateSubCategory(){
        String categoryName = "CategoryName";
        String subCategoryName = "SubCategoryName";

        Long categoryId = categoryService.createCategory(categoryName);
        Long subCategoryId = categoryService.createSubCategory(categoryId, subCategoryName);

        SubCategory subCategory = categoryService.getSubCategory(subCategoryId);

        System.out.println("Category name: " + subCategory.getCategory().getName());

        assertEquals(subCategory.getName(), subCategoryName);
        assertEquals(subCategory.getCategory().getName(), categoryName);

    }

    @Test
    public void testGetAllCategories(){
        Long categoryId1, categoryId2, categoryId3, subCategory1, subCategory2, subCategory3;
        String firstCategoryName = "firstCategoryName",
                secondCategoryName = "secondCategoryName",
                thirdCategoryName = "thirdCategoryName",
                subCategoryName = "subCategoryName";

        categoryId1 = categoryService.createCategory(firstCategoryName);
        categoryId2 = categoryService.createCategory(secondCategoryName);
        categoryId3 = categoryService.createCategory(thirdCategoryName);

        subCategory1 = categoryService.createSubCategory(categoryId1, subCategoryName);
        subCategory2 = categoryService.createSubCategory(categoryId2, subCategoryName);
        subCategory3 = categoryService.createSubCategory(categoryId3, subCategoryName);

        List<Category> categoriesWithoutSub = categoryService.getAllCategories(false);

        assertEquals(3, categoriesWithoutSub.size());

        try {
            categoriesWithoutSub.get(0).getSubCategories().size();
            fail();
        }
        catch (Exception e){
        }

        List<Category> categoriesWithSub = categoryService.getAllCategories(true);

        assertEquals(1, categoriesWithSub.get(0).getSubCategories().size());
    }

    @Test
    public void testCreateTwice(){
        String name = "name";
        Long categoryId1 = categoryService.createCategory(name);

        assertNotNull(categoryId1);

        try {
            categoryService.createCategory(name);
            fail();
        }
        catch (Exception e){
        }
    }
}
