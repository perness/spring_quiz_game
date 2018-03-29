package com.ncorp.service;

import com.ncorp.StubApplication;
import com.ncorp.entity.Category;
import com.ncorp.entity.Quiz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StubApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext(classMode = BEFORE_CLASS)
public class DefaultDataInitializerServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private QuizService quizService;

    @Test
    public void verifyDataInDatabase(){
        List<Category> categoryList = categoryService.getAllCategories(false);
        List<Category> categoryListWithSub = categoryService.getAllCategories(true);
        List<Quiz> quizList = quizService.getQuizzes();

        assertTrue(categoryList.size() > 0);

        assertTrue(categoryListWithSub.stream().mapToLong(c -> c.getSubCategories().size()).sum() > 0);

        assertTrue(quizList.size() > 0);
    }

}
