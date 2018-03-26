package com.ncorp.page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static org.junit.Assert.*;

public abstract class LayoutPO extends PageObject {

    public LayoutPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public LayoutPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("This is the main title");
    }

    public SignUpPO goToSignUp(){
        clickAndWait("linkToSignupId");
        SignUpPO signUpPO = new SignUpPO(this);

        if (signUpPO.isOnPage()){
            return signUpPO;
        }
        else {
            return null;
        }
    }

    public IndexPO doLogout(){
        clickAndWait("logoutId");

        IndexPO indexPO = new IndexPO(this);
        if(indexPO.isOnPage()){
            return indexPO;
        }
        else {
            return null;
        }
    }

    public boolean isLoggedIn(){
        return getDriver().findElements(By.id("logoutId")).size() > 0 &&
                getDriver().findElements(By.id("linkToSignupId")).isEmpty();
    }

    public boolean userNamePresent(){
        return getDriver().findElement(By.id("usernameOutputId")).isDisplayed();
    }

}
