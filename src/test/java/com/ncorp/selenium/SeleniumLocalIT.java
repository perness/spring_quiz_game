package com.ncorp.selenium;

import com.ncorp.Application;
import com.ncorp.page_object.IndexPO;
import com.ncorp.page_object.MatchPO;
import com.ncorp.page_object.ResultPO;
import com.ncorp.service.QuizService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
public class SeleniumLocalIT {

    private static WebDriver webDriver;
    private IndexPO indexPO;

    @Autowired
    private QuizService quizService;

    @LocalServerPort
    private int port;

    @BeforeClass
    public static void initClass(){
        webDriver = SeleniumDriverHandler.getChromeDriver();

        if (webDriver == null) throw new AssumptionViolatedException("Cannot find/initialize Chrome driver");
    }

    @AfterClass
    public static void tearDown(){
        if (webDriver != null) webDriver.close();
    }

    @Before
    public void init(){
        webDriver.manage().deleteAllCookies();

        indexPO = new IndexPO(webDriver, "localhost", port);
        indexPO.toStartingPage();

        assertTrue("Failed to start from Home Page", indexPO.isOnPage());
    }

    @Test
    public void testNewMatch(){
        assertTrue(indexPO.isOnPage());

        MatchPO matchPO = indexPO.startNewMatch();

        assertTrue(matchPO.isOnPage());

        assertTrue(matchPO.canSelectCategory());
    }

    @Test
    public void testFirstQuiz(){
        assertTrue(indexPO.isOnPage());

        MatchPO matchPO = indexPO.startNewMatch();

        assertTrue(matchPO.isOnPage());
        assertFalse(matchPO.isQuestionDisplayed());

        matchPO.chooseCategoryById(matchPO.getCategoryIds().get(0));

        assertTrue(matchPO.isQuestionDisplayed());
        assertFalse(matchPO.canSelectCategory());
    }

    @Test
    public void testWrongAnswer(){
        assertTrue(indexPO.isOnPage());

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
}





























