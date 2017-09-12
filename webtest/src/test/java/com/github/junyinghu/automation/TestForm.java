package com.github.junyinghu.automation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class TestForm {
    private WebDriver driver;
    private Properties properties = new Properties();
    private File file = new File("src/test/resources/xpath_data.properties");
    private KeyWords searchKeyword;


    @BeforeClass
    public void beforeClass() {
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver = new FirefoxDriver();
        driver.get("http://google.com.sg");
        searchKeyword = new KeyWords(driver, properties);

    }

    @AfterClass
    public void afterClass() {
        Reporter.log("afterClass", true);
        driver.close();
    }

    @Test
    public void openGoogleSearch() throws InterruptedException {
        searchKeyword.enterKeywordsSearch();
        searchKeyword.clickUrl();
        searchKeyword.login();
        searchKeyword.setting();

        Thread.sleep(5000);
    }

    @Test(dependsOnMethods = {"openGoogleSearch"}) //can put multiple by ,
    public void modificationsetting() {
        searchKeyword.toggleAllRadioCheck();
    }


}