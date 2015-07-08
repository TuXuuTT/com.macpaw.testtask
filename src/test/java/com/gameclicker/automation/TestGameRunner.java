package com.gameclicker.automation;

import com.gameclicker.pages.GameStartPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Created by TuXuu on 08.07.2015.
 */
public class TestGameRunner extends BaseTest {
    static GameStartPage startPage;

    @BeforeClass
    public static void beforeTestClass() {
        startPage = new GameStartPage(getWdInstance());
    }

    @Test
    public void test2() {
        assertTrue(5 > 1);
    }

}
