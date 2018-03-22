package com.ncorp.page_object;

import org.openqa.selenium.WebDriver;
import static org.junit.Assert.*;

public class SignUpPO extends LayoutPO {


    public SignUpPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public SignUpPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Sign Up");
    }

    public IndexPO registerNewUser(String userName, String password){
        setText("username", userName);
        setText("password", password);

        clickAndWait("submit");

        IndexPO indexPO = new IndexPO(this);

        if (indexPO.isOnPage()){
            return indexPO;
        }
        else {
         return null;
        }
    }
}
