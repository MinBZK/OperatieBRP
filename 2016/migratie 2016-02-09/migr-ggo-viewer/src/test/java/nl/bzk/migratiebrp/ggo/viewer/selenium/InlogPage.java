/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.selenium;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InlogPage {
    private final WebDriver driver;

    public InlogPage(final WebDriver driver) {
        this.driver = driver;
    }

    public WebElement login(final String user) {
        try {
            final WebElement userNameField = driver.findElement(By.id("gebruikersnaam"));
            userNameField.clear();
            userNameField.sendKeys(user);
            final WebElement passwordField = driver.findElement(By.id("wachtwoord"));
            passwordField.clear();
            passwordField.sendKeys(user);
            final WebElement element = driver.findElement(By.className("submit"));
            element.click();
            return driver.findElement(By.name("file"));
        } catch (final RuntimeException e) {
            takeScreenShot(e, "searchError");
        }
        return null;
    }

    private void takeScreenShot(final RuntimeException e, final String fileName) {
        final File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenShot, new File(fileName + ".png"));
        } catch (final IOException ioe) {
            throw new RuntimeException(ioe.getMessage(), ioe);
        }
        throw e;
    }
}
