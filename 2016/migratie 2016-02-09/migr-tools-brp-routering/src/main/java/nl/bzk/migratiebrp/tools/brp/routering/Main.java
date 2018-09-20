/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.brp.routering;

import java.util.Calendar;
import nl.bzk.migratiebrp.util.common.Version;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main voor BRP routering centrale.
 */
public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static ConfigurableApplicationContext context;
    private static final KeepAlive keepAlive = new KeepAlive();

    private Main() {
        // Niet instantieerbaar.
    }

    /**
     * Start de applicatie.
     *
     * @param argv
     *            argumenten (ongebruikt)
     */
    public static void main(final String[] argv) {
        final Version version = Version.readVersion("nl.bzk.migratiebrp.tools", "migr-tools-brp-routering");

        LOG.debug("Starten applicatie ({}) ...\nComponenten:\n{}", version.toString(), version.toDetailsString());

        context = new ClassPathXmlApplicationContext("tools-brp-routering.xml");
        context.registerShutdownHook();

        new Thread(keepAlive).start();
        LOG.info("Applicatie ({}) gestart om {}", version.toString(), Calendar.getInstance().getTime());
    }

    /**
     * Stop de applicatie.
     */
    public static void stop() {
        LOG.info("Stoppen applicatie ...");
        context.close();
        LOG.info("Applicatie gestopt ...");
        keepAlive.stop();
    }

    /**
     * Geef de applicatie context.
     *
     * @return applicatie context
     */
    public static Object getContext() {
        return context;
    }
}
