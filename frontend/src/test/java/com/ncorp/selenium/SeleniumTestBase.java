package com.ncorp.selenium;

import com.ncorp.page_object.IndexPO;
import com.ncorp.page_object.MatchPO;
import com.ncorp.page_object.ResultPO;
import com.ncorp.page_object.SignUpPO;
import com.ncorp.service.QuizService;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract class SeleniumTestBase {

    /* Abstract functions */

    protected abstract WebDriver getDriver();

    protected abstract String getServerHost();

    protected abstract int getServerPort();

    /* Fields */

    private IndexPO indexPO;
    private static final AtomicInteger counter = new AtomicInteger(0);

    @Autowired
    private QuizService quizService;

    @Before
    public void init(){
        getDriver().manage().deleteAllCookies();

        indexPO = new IndexPO(getDriver(), getServerHost(), getServerPort());
        indexPO.toStartingPage();

        assertTrue("Failed to start from Home Page", indexPO.isOnPage());
    }

    /* Helper functions */

    private String getUniqueId(){
        return "foo_SeleniumLocalIT_" + counter.getAndIncrement();
    }

    private IndexPO createUser(){
        indexPO.toStartingPage();

        SignUpPO signUpPO = indexPO.goToSignUp();

        assertTrue(signUpPO.isOnPage());

        IndexPO po = signUpPO.registerNewUser(getUniqueId(), "password");

        assertTrue(po.isOnPage());

        return po;
    }

    /* Test functions */

    @Test
    public void testNewMatch(){
        indexPO = createUser();

        MatchPO matchPO = indexPO.startNewMatch();

        assertTrue(matchPO.isOnPage());

        assertTrue(matchPO.canSelectCategory());
    }

    @Test
    public void testFirstQuiz(){
        indexPO = createUser();

        MatchPO matchPO = indexPO.startNewMatch();

        assertTrue(matchPO.isOnPage());
        assertFalse(matchPO.isQuestionDisplayed());

        matchPO.chooseCategoryById(matchPO.getCategoryIds().get(0));

        assertTrue(matchPO.isQuestionDisplayed());
        assertFalse(matchPO.canSelectCategory());
    }

    @Test
    public void testWrongAnswer(){
        indexPO = createUser();

        MatchPO matchPO = indexPO.startNewMatch();

        assertTrue(matchPO.isOnPage());

        matchPO.chooseCategoryById(matchPO.getCategoryIds().get(0));

        assertTrue(matchPO.isQuestionDisplayed());

        long quizID = matchPO.getQuizId();

        int correctAnswerIndex = quizService.getQuiz(quizID).getCorrectAnswer();
        int wrongAnswerIndex = (correctAnswerIndex + 1) % 4;

        ResultPO resultPO = matchPO.answerQuestion(wrongAnswerIndex);

        assertTrue(resultPO.isOnPage());
        assertTrue(resultPO.haveLost());
        assertFalse(resultPO.haveWon());
    }

    @Test
    public void testWinMatch(){
        indexPO = createUser();

        ResultPO resultPO = null;

        assertTrue(indexPO.isOnPage());

        MatchPO matchPO = indexPO.startNewMatch();

        assertTrue(matchPO.isOnPage());

        matchPO.chooseCategoryById(matchPO.getCategoryIds().get(0));

        assertTrue(matchPO.isQuestionDisplayed());

        for (int i = 0; i < 5; i++) {

            long quizID = matchPO.getQuizId();

            int quizCounter = matchPO.getQuestionCounter();
            System.out.println("counter: " + i);
            assertEquals(i+1, quizCounter);

            int correctAnswerIndex = quizService.getQuiz(quizID).getCorrectAnswer();

            resultPO = matchPO.answerQuestion(correctAnswerIndex);
        }

        assertTrue(resultPO.isOnPage());
        assertTrue(resultPO.haveWon());
        assertFalse(resultPO.haveLost());
    }

    @Test
    public void testCreateAndLogoutUser(){
        String username = getUniqueId();

        assertFalse(indexPO.isLoggedIn());

        SignUpPO signUpPO = indexPO.goToSignUp();

        IndexPO indexPO1 = signUpPO.registerNewUser(username, "password");

        assertNotNull(indexPO1);

        assertTrue(indexPO1.isLoggedIn());
        assertTrue(indexPO1.isOnPage());
        assertTrue(indexPO1.getDriver().getPageSource().contains(username));

        indexPO1.doLogout();

        assertFalse(indexPO1.isLoggedIn());
        assertFalse(indexPO1.getDriver().getPageSource().contains(username));
    }
}
