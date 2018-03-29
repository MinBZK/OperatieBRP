/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Helper klasse om the controleren of of de versie URL van het gegeven component
 * opgevraagd kan worden. Dit is de indicatie dat het component functioneel
 * beschikbaar is,
 */
final class VersieUrlChecker {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final int HTTP_OK = 200;
    private static final int CONNECT_TIMEOUT = 1000;

    private VersieUrlChecker() {
    }

    /**
     * Check de beschikbaarheid van de versie URL.
     * @param component het component.
     * @param contextRoot contextroot van het component.
     * @return boolean indicatie of de URL beschikbaar is.
     */
    public static boolean check(final Docker component, final String contextRoot) {
        Assert.notNull(contextRoot, "Contextroot mag niet leeg zijn");
        try {
            final Integer poort = component.getPoortMap().get(Poorten.APPSERVER_PORT);
            final URL url = new URL(String.format("http://%s:%d/%s/versie.html", component.getOmgeving().getDockerHostname(), poort, contextRoot));
            final HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            LOGGER.info("URL: {}", url.toString());
            huc.setRequestMethod("GET");
            huc.setConnectTimeout(CONNECT_TIMEOUT);
            huc.setReadTimeout(CONNECT_TIMEOUT);
            huc.connect();
            return huc.getResponseCode() == HTTP_OK;
        } catch (IOException e) {
            LOGGER.debug("URL nog niet beschikbaar");
            return false;
        }
    }
}
