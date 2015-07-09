package com.gameclicker.automation;

import com.gameclicker.pages.GameStartPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by TuXuu on 08.07.2015.
 */
public class TestGameRunner extends BaseTest {
    private  GameStartPage startPage;

    @Override
    @BeforeClass
    public  void beforeClass() {
        super.beforeClass();
        startPage = new GameStartPage(getWdInstance());
    }

    @Test
    public void testClickGameRandomly() {
        startPage.closeNotice();
        startPage.getTilePosition();
    }

}
