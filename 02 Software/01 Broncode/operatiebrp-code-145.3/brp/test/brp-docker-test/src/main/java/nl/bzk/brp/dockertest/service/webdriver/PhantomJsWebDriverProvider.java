/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * PhantomJS implementatie van {@link WebDriverProvider}.
 */
public final class PhantomJsWebDriverProvider extends AbstractWebDriverProvider {

    @Override
    protected DesiredCapabilities createCapabilities() {
        return DesiredCapabilities.phantomjs();
    }

    @Override
    protected WebDriver doProvide(String binaryPath, DesiredCapabilities capabilities) {
        capabilities.setCapability(
                PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                binaryPath
        );

        return new PhantomJSDriver(capabilities);
    }
}
