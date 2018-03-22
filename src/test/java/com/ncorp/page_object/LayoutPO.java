package com.ncorp.page_object;

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

        assertTrue(signUpPO.isOnPage());

        return signUpPO;
    }

}
