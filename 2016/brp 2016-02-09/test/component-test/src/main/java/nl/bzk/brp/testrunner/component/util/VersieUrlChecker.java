/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.testrunner.component.Poorten;
import nl.bzk.brp.testrunner.omgeving.Component;
import org.apache.commons.io.IOUtils;
import org.springframework.util.Assert;

/**
 * Helper klasse om the controleren of of de versie URL van het gegeven component
 * opgevraagd kan worden. Dit is de indicatie dat het component functioneel
 * beschikbaar is,
 */
public class VersieUrlChecker {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    public static boolean check(final Component component, String contextRoot) {
        Assert.notNull(contextRoot, "Contextroot mag niet leeg zijn");
        URL url;
        try {
            final Integer integer = component.getPoortMap().get(Poorten.WEB_POORT_8080);
            if (integer == null) {
                throw new IllegalStateException(String.format("Poort %d niet beschikbaar", Poorten.WEB_POORT_8080));
            }
            url = new URL(String.format("http://%s:%d/%s/versie.html",
                component.getOmgeving().geefOmgevingHost(),
                integer,
                contextRoot));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        final StringWriter sw = new StringWriter();
        try (final InputStream input = url.openStream()){
            IOUtils.copy(input, sw);
            return true;
        } catch (IOException e) {
            LOGGER.debug("Versie URL nog niet beschikbaar: " + e.getMessage());
        }
        return false;
    }
}
