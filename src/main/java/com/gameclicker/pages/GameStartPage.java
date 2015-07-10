package com.gameclicker.pages;

import dnl.utils.text.table.TextTable;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

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

    @FindBy(css = ".restart-button")
    private WebElement buttonNewGame;

    @FindBy(css = ".notice-close-button")
    private WebElement buttonNoticeClose;

    @FindBy(css = ".grid-container")
    private WebElement gridContainer;

    @FindAll(@FindBy(xpath = "//div[contains(@class, 'tile-position')]"))
    private List<WebElement> tilePositions;

    @FindBy(css = ".tile-inner")
    private WebElement tilesInnerValue;

    @FindBy(css = ".game-over p")
    private WebElement labelGameOver;

    public GameStartPage(WebDriver wd) {
        super(wd);
        load();
    }

    public GameStartPage closeNotice() {
        waitForVisibility(labelAppDescription);
        waitForElementStopMoving(buttonNoticeClose);
        click(buttonNoticeClose);
        return this;
    }

    /**
     * comparing current and best score as fastest way to check if game is over yet.
     * "Game Over" label appears too long, will significantly increase run time if check it's presence after each click
     * <p/>
     * refreshPage() is needed to get valid locators for cells positions as after click there are duplicated deprecated items left.
     */

    public GameStartPage pressRandomKeysAndPrintResultUntilGameIsOver() throws InterruptedException {
        int i = 0;
        boolean isGameOver = false;
        do {
            if (labelScoreContainer.getText().substring(0, 1).equals(labelBestContainer.getText().substring(0, 1))) {
                printTilesValuesTableIntoLog(i);
                pressRandomArrowKey();
                i++;
                refreshPage();
                waitForVisibility(tilesInnerValue);
            } else {
                isGameOver = true;
            }
        }
        while (!isGameOver);

        return this;
    }

    public void showCurrentScore() {
        log.info("\n================================\n Game over. Your score is: {} \n================================\n", labelBestContainer.getText());
    }

    public GameStartPage pressRandomArrowKey() {
        int randomNum = new Random().nextInt(4);
        switch (randomNum) {
            case 0:
                pressKey(gridContainer, Keys.ARROW_DOWN);
                break;
            case 1:
                pressKey(gridContainer, Keys.ARROW_LEFT);
                break;
            case 2:
                pressKey(gridContainer, Keys.ARROW_UP);
                break;
            case 3:
                pressKey(gridContainer, Keys.ARROW_RIGHT);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return this;
    }

    public void printTilesValuesTableIntoLog(int stepNumber) {
        TextTable table = createDataTableWithCurrentCellsValues();
        // Create a stream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        // IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
        // Tell Java to use defined special stream
        System.setOut(ps);
        // Print some output which will go to defined special stream
        table.printTable();
        // Put things back
        System.out.flush();
        System.setOut(old);
        // use ByteArrayOutputStream.toString and do whatever you want with it like with any String variable
        log.info("Step {} grid view: \n {}", stepNumber, baos.toString());
    }


    protected Map<TileCoordinateEntity, String> collectTilePositionsAndValues() {
        Map<TileCoordinateEntity, String> tilePositionsValues = new HashMap<>();
        for (WebElement tile : tilePositions) {
            String[] coordinates = StringUtils.substringBetween(tile.getAttribute("class"), "tile-position-", " ").split("-", 2);
            tilePositionsValues.put(new TileCoordinateEntity(Integer.parseInt(coordinates[1]), Integer.parseInt(coordinates[0])), tile.findElement(By.cssSelector("div")).getText());
        }
        return tilePositionsValues;
    }

    private TextTable createDataTableWithCurrentCellsValues() {
        String[] columnNames = {
                "1.", "2.", "3.", "4."};
        Object[][] data = fillArrayTableWithTilesValues(createEmptyArrayTable(), collectTilePositionsAndValues());
        TextTable tt = new TextTable(columnNames, data);
        // this adds the numbering on the left
        tt.setAddRowNumbering(true);
        return tt;
    }

    private Object[][] fillArrayTableWithTilesValues(Object[][] tableToFill, Map<TileCoordinateEntity, String> mapWithValues) {
        Set<Map.Entry<TileCoordinateEntity, String>> setOfEntries = mapWithValues.entrySet();

        for (Map.Entry<TileCoordinateEntity, String> mapEntry : setOfEntries) {
            tableToFill[mapEntry.getKey().getRowNumber() - 1][mapEntry.getKey().getColumnNumber() - 1] = mapEntry.getValue();
        }
        return tableToFill;
    }

    private Object[][] createEmptyArrayTable() {
        return new String[][]{
                {"0", "0", "0", "0"},
                {"0", "0", "0", "0"},
                {"0", "0", "0", "0"},
                {"0", "0", "0", "0"}
        };
    }

    public static class TileCoordinateEntity {
        private int rowNumber;
        private int columnNumber;

        public TileCoordinateEntity(int rowNumber, int columnNumber) {
            this.rowNumber = rowNumber;
            this.columnNumber = columnNumber;
        }

        public int getRowNumber() {
            return rowNumber;
        }

        public void setRowNumber(int rowNumber) {
            this.rowNumber = rowNumber;
        }

        public int getColumnNumber() {
            return columnNumber;
        }

        public void setColumnNumber(int columnNumber) {
            this.columnNumber = columnNumber;
        }
    }
}
