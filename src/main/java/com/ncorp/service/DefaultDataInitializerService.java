package com.ncorp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;

@Service
public class DefaultDataInitializerService {

    @Autowired
    private QuizService quizService;

    @Autowired
    private CategoryService categoryService;

    @PostConstruct
    public void init(){
        Long firstCategoryId = categoryService.createCategory("first category"),
                secondCategoryId = categoryService.createCategory("second category");

        Long firstSubCategoryId = categoryService.createSubCategory(firstCategoryId, "first sub category"),
                secondSubCategoryId = categoryService.createSubCategory(firstCategoryId, "second sub category"),
                thirdSubCategoryId = categoryService.createSubCategory(secondCategoryId, "third sub category"),
                fourthSubCategoryId = categoryService.createSubCategory(secondCategoryId, "fourth sub category"),
                fifthSubCategoryId = categoryService.createSubCategory(secondCategoryId, "fifth sub category");

        Long firstQuiz = quizService.createQuiz(firstSubCategoryId,
                "What is 1 + (-1)?",
                "0",
                "1",
                "2",
                "3",
                0),

                secondQuiz = quizService.createQuiz(firstSubCategoryId,
                        "What is the capital of Norway?",
                        "N",
                        "Oslo",
                        "Europe",
                        "Scandinavia",
                        1),

                thirdQuiz = quizService.createQuiz(secondSubCategoryId,
                        "What is the capital of Italy?",
                        "Florence",
                        "Milan",
                        "Rome",
                        "Turin",
                        2),

                fourthQuiz = quizService.createQuiz(secondSubCategoryId,
                        "When ended the second world war?",
                        "1925",
                        "1935",
                        "1945",
                        "1955",
                        2),

                fifthQuiz = quizService.createQuiz(secondSubCategoryId,
                        "Name the seventh planet from the sun?",
                        "Uranus",
                        "Earth",
                        "Jupiter",
                        "Saturn",
                        0),

                sixthQuiz = quizService.createQuiz(thirdSubCategoryId,
                        "Name the world's biggest island?",
                        "Island",
                        "Greenland",
                        "Australia",
                        "North pole",
                        1),

                seventhQuiz = quizService.createQuiz(fourthSubCategoryId,
                        "What is the world's longest river?",
                        "Nile",
                        "Danube",
                        "Ganges",
                        "Amazon",
                        3),

                eigthQuiz = quizService.createQuiz(fourthSubCategoryId,
                        "When did the Cold War end?",
                        "1789",
                        "1889",
                        "1989",
                        "2009",
                        2),

                nithQuiz = quizService.createQuiz(fifthSubCategoryId,
                        "What colour is Absynthe?",
                        "Yellow",
                        "Red",
                        "Blue",
                        "Green",
                        3),

                tenthQuiz = quizService.createQuiz(fifthSubCategoryId,
                        "Who is Real Madrid's all-time top goalscorer?",
                        "Raul",
                        "Di Stefano",
                        "Zidane",
                        "Cristiano Ronaldo",
                        3);
    }
}
