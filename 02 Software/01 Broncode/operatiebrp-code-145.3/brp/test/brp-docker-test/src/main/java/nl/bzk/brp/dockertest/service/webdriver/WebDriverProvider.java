/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service.webdriver;

import org.openqa.selenium.WebDriver;

/**
 * Interface voor het oplepelen van WebDriver instanties.
 */
public interface WebDriverProvider {

    /**
     * Lepel de driver op.
     * @param binaryPath binary path
     * @return de {@link WebDriver}
     */
    WebDriver provide(String binaryPath);
}
