package com.ncorp.page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ResultPO extends PageObject {

    public ResultPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public ResultPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Match Result");
    }

    public boolean haveWon(){
        return getDriver().findElements(By.id("wonId")).size() > 0;
    }

    public boolean haveLost(){
        return getDriver().findElements(By.id("lostId")).size() > 0;
    }
}
