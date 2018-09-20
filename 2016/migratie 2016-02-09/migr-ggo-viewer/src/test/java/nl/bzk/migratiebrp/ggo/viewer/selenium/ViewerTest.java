/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.selenium;

import static junit.framework.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ViewerTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
    }

    @After
    public void tearDown() {
        driver.close();
    }

    @Ignore
    @Test
    public void testLo3CanBeFound() {
        final String baseUrl = "http://localhost:8080/migr-ggo-viewer";
        driver.get(baseUrl + "/");

        final InlogPage inlog = new InlogPage(driver);
        final String expectedinlog = "input";
        final WebElement actualinlog = inlog.login("admin");
        assertTrue(actualinlog.getTagName().contains(expectedinlog));

        final ViewerPage home = new ViewerPage(driver);
        final boolean actual = home.searchFor("01.10 A-nummer");
        assertTrue(actual);
    }
}
