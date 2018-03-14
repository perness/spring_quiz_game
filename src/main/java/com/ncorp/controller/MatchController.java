package com.ncorp.controller;

import com.ncorp.entity.Category;
import com.ncorp.entity.Quiz;
import com.ncorp.service.CategoryService;
import com.ncorp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named @SessionScoped
public class MatchController implements Serializable {

    private boolean isGameOn = false;
    private Long selectedCategoryId;
    private List<Quiz> quizList;
    private int counter;
    private final int NUMBER_OF_QUIZZES = 5;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private QuizService quizService;

    public boolean isMatchOn(){
        return isGameOn;
    }

    public String newMatch(){
        isGameOn = true;
        selectedCategoryId = null;
        return "/match.jsf&faces-redirect=true";
    }

    public String home(){
        return "/index.jsf&faces-redirect=true";
    }

    public List<Category> getCategories(){
        return categoryService.getAllCategories(false);
    }

    public boolean isCategorySelected(){
        return selectedCategoryId != null;
    }

    public void selectCategory(long id){
        selectedCategoryId = id;
        counter = 0;
        quizList = quizService.getRandomQuizzes(NUMBER_OF_QUIZZES, selectedCategoryId);
    }

    public Quiz getCurrentQuiz(){
        return quizList.get(counter);
    }

    public String answerQuiz(int index){
        if (index == getCurrentQuiz().getCorrectAnswer()){
            counter++;
            if (counter == NUMBER_OF_QUIZZES) {
                isGameOn = false;
                return "result.jsf?victory=true&faces-redirect=true";
            }
        }
        else {
            isGameOn = true;
            return "result.jsf?defeat=true&faces-redirect=true";
        }

        return null;

    }

    public int getNUMBER_OF_QUIZZES() {
        return NUMBER_OF_QUIZZES;
    }

    public int getIncreasedCounter(){
        return counter + 1;
    }
}
