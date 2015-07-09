package com.gameclicker.pages;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TuXuu on 08.07.2015.
 */
public class GameStartPage extends BasePage {

    @FindBy(className = "title")
    private WebElement labelGameTitle;

    @FindBy(css = ".score-container")
    private WebElement labelScoreContainer;

    @FindBy(css = ".best-container")
    private WebElement labelBestContainer;

    @FindBy(css = "p.game-intro")
    private WebElement labelGameIntro;

    @FindBy(css = "div.app-notice p")
    private WebElement labelAppDescription;

    @FindBy(css=".restart-button")
    private WebElement buttonNewGame;

    @FindBy(css = ".notice-close-button")
    private WebElement buttonNoticeClose;

    @FindAll(@FindBy(xpath="//div[contains(@class, 'tile-position')]"))
    private List<WebElement> tilePositions;

    @FindAll(@FindBy(css=".tile-inner"))
    private List<WebElement> tilesInnerValues;

    public GameStartPage(WebDriver wd) {
        super(wd);
        load();
    }

    public GameStartPage closeNotice() {
        click(buttonNoticeClose);
        return this;
    }

    public List<String> getTilePositions(){
        refreshPage();
        List<String> results = new ArrayList<>();
//        List<String> tilesClasses = tilePositions.stream().map(tile -> tile.getAttribute("class")).collect(Collectors.toList());
        for(WebElement tile:tilePositions){
            results.add(StringUtils.substringBetween(tile.getAttribute("class"),"tile-position-"," "));
        }
        return results;
    }

}
