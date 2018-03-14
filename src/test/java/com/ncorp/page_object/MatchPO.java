package com.ncorp.page_object;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class MatchPO extends PageObject {


    public MatchPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public MatchPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().equalsIgnoreCase("This is in the tab title");
    }

    public List<String> getCategoryIds(){
        return getDriver().findElements(By.xpath("//input[@data-ctgid]")).stream()
                .map(e -> e.getAttribute("data-ctgid"))
                .collect(Collectors.toList());
    }

    public boolean canSelectCategory(){
        return getCategoryIds().size() > 0;
    }

    public void chooseCategoryById(String id){
        clickAndWait("ctgBtnId_" + id);
    }

    public boolean isQuestionDisplayed(){
        return getDriver().findElements(By.id("questionId")).size() > 0;
    }

    public int getQuestionCounter(){
        return getInteger("questionCounterId");
    }

    public long getQuizId(){
        String id = getDriver().findElement(By.xpath("//p//*[@data-quizid]")).getAttribute("data-quizid");
        return Long.parseLong(id);
    }

    public ResultPO answerQuestion(int index){
        clickAndWait("answerId_" + index);

        if (isOnPage()){
            return null;
        }

        ResultPO resultPO = new ResultPO(this);
        assertTrue(resultPO.isOnPage());

        return resultPO;
    }

}
