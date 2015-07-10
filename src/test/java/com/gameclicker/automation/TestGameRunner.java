package com.gameclicker.automation;

import com.gameclicker.pages.GameStartPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by TuXuu on 08.07.2015.
 */
public class TestGameRunner extends BaseTest {
    private GameStartPage startPage;

    @Override
    @BeforeClass
    public void beforeClass() {
        super.beforeClass();
        startPage = new GameStartPage(getWdInstance());
    }

/**
 * testClickGameRandomly() performs random actions in 2048 game, prints current game field to both console ant log.file
 * When game is over, result is printed as well, then page closes and WebDriver quits.
 * */

    @Test
    public void testClickGameRandomly() throws InterruptedException {
        startPage.closeNotice();

        startPage.pressRandomKeysAndPrintResultUntilGameIsOver()
                .showCurrentScore();
    }

}
