/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service.webdriver;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Chrome implementatie van {@link WebDriverProvider}.
 */
public final class ChromeWebDriverProvider extends AbstractWebDriverProvider {
    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    protected DesiredCapabilities createCapabilities() {
        return DesiredCapabilities.chrome();
    }

    @Override
    protected WebDriver doProvide(String binaryPath, DesiredCapabilities capabilities) {
        LOG.info("chrome binary path: " + binaryPath);
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, binaryPath);
        capabilities.setCapability(
                ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
                binaryPath
        );
        return new ChromeDriver(capabilities);
    }
}
