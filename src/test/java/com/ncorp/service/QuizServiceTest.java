package com.ncorp.service;

import com.ncorp.Application;
import com.ncorp.entity.Quiz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class QuizServiceTest extends ServiceTestBase {

    @Autowired
    private QuizService quizService;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void testNoQuiz(){
        List<Quiz> quizzes = quizService.getQuizzes();

        assertEquals(0, quizzes.size());
    }

    @Test
    public void testCreateQuiz(){
        long categoryId = categoryService.createCategory("category name");
        long subCategoryId = categoryService.createSubCategory(categoryId, "sub cat name");
        long quiz = quizService.createQuiz(subCategoryId,
                "question",
                "firstAnswer",
                "secondAnswer",
                "thirdAnswer",
                "fourthAnswer",
                0);

        List<Quiz> quizzes = quizService.getQuizzes();

        assertEquals(1,quizzes.size());
        assertEquals("question", quizService.getQuiz(quiz).getQuestion());
    }

    @Test
    public void testNotEnoughQuizzes(){
        long categoryId = categoryService.createCategory("category name");
        long subCategoryId = categoryService.createSubCategory(categoryId, "sub cat name");

        long firstQuiz = quizService.createQuiz(subCategoryId,
                "What is 1 + (-1)?",
                "0",
                "1",
                "2",
                "3",
                0),
                secondQuiz = quizService.createQuiz(subCategoryId,
                        "What is the capital of Norway?",
                        "N",
                        "Oslo",
                        "Europe",
                        "Scandinavia",
                        1),
                thirdQuiz = quizService.createQuiz(subCategoryId,
                        "What is the capital of Italy?",
                        "Florence",
                        "Milan",
                        "Rome",
                        "Turin",
                        2);

        try {
            List<Quiz> quizList = quizService.getRandomQuizzes(5,categoryId);
            fail();
        }
        catch (Exception e){

        }
    }

    @Test
    public void testGetRandomQuizzes(){
        long categoryId = categoryService.createCategory("category name");
        long subCategoryId = categoryService.createSubCategory(categoryId, "sub cat name");
        String questionOne = "What is 1 + (-1)?",
                questionTwo = "What is the capital of Norway?",
                questionThree = "What is the capital of Italy?";

        long firstQuiz = quizService.createQuiz(subCategoryId,
                questionOne,
                "0",
                "1",
                "2",
                "3",
                0),
                secondQuiz = quizService.createQuiz(subCategoryId,
                        questionTwo,
                        "N",
                        "Oslo",
                        "Europe",
                        "Scandinavia",
                        1),
                thirdQuiz = quizService.createQuiz(subCategoryId,
                        questionThree,
                        "Florence",
                        "Milan",
                        "Rome",
                        "Turin",
                        2);

        Set<String> questions = new HashSet<>();

        for (int i = 0; i < 50; i++){
            List<Quiz> quizzes = quizService.getRandomQuizzes(2,categoryId);

            assertEquals(2,quizzes.size());
            assertNotEquals(quizzes.get(0).getQuestion(),quizzes.get(1).getQuestion());

            questions.add(quizzes.get(0).getQuestion());
            questions.add(quizzes.get(1).getQuestion());
        }

        assertEquals(3,questions.size());
        assertTrue(questions.contains(questionOne));
        assertTrue(questions.contains(questionTwo));
        assertTrue(questions.contains(questionThree));


    }

}
