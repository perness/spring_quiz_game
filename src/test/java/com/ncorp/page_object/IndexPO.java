package com.ncorp.page_object;

import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;


public class IndexPO extends LayoutPO {

    public IndexPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public IndexPO(PageObject other) {
        super(other);
    }

    public void toStartingPage(){
        getDriver().get(host + ":" + port);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Quiz Game");
    }

    public MatchPO startNewMatch(){
        clickAndWait("newMatchBtnId");
        MatchPO matchPO = new MatchPO(this);
        assertTrue(matchPO.isOnPage());

        return matchPO;
    }
}
