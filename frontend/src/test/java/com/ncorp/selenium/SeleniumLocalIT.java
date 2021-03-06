package com.ncorp.selenium;

import com.ncorp.Application;
import org.junit.AfterClass;
import org.junit.AssumptionViolatedException;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
public class SeleniumLocalIT extends SeleniumTestBase{

    private static WebDriver webDriver;

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

    @Override
    protected WebDriver getDriver() {
        return webDriver;
    }

    @Override
    protected String getServerHost() {
        return "localhost";
    }

    @Override
    protected int getServerPort() {
        return port;
    }
}