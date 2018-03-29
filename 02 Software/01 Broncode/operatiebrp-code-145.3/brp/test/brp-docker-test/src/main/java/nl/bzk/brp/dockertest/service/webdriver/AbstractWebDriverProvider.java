/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service.webdriver;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.util.Assert;

/**
 * Generieke basis voor web driver provider.
 */
abstract class AbstractWebDriverProvider implements WebDriverProvider {

    @Override
    public final WebDriver provide(String binaryPath) {
        Assert.notNull(binaryPath, "binaryPath mag niet null zijn");
        final DesiredCapabilities capabilities = createCapabilities();
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability("takesScreenshot", true);
        WebDriver driver = doProvide(binaryPath, capabilities);
        driver.manage().window().setSize(new Dimension(1280, 1024));
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    protected abstract DesiredCapabilities createCapabilities();

    protected abstract WebDriver doProvide(String binaryPath, DesiredCapabilities capabilities);
}
