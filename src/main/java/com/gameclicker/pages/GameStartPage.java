package com.gameclicker.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by TuXuu on 08.07.2015.
 */
public class GameStartPage extends BasePage {

    @FindBy(css=".notice-close-button")
    private WebElement buttonNoticeClose;

    public GameStartPage(WebDriver wd) {
        super(wd);
        load();
    }

    public GameStartPage closeNotice(){
        click(buttonNoticeClose);
        return this;
    }
}
