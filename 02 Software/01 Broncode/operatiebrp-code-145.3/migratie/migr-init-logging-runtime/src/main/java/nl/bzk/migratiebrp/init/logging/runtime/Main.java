/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime;

import nl.bzk.algemeenbrp.util.common.Version;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Basis runtime. Start de spring container; de spring container bevat componenten die een live
 * thread opstarten (JMS queue poller).
 */
public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static ConfigurableApplicationContext context;

    private Main() {
    }

    /**
     * Run.
     * @param args argumenten (ongebruikt).
     */
    public static void main(final String[] args) {
        final Version version = Version.readVersion("nl.bzk.migratiebrp.init.logging", "migr-init-logging-runtime");
        LOG.info("Starten applicatie ({}) ...\nComponenten:\n{}", version.toString(), version.toDetailsString());

        synchronized (Main.class) {
            context = new ClassPathXmlApplicationContext("init-logging-runtime.xml");
        }

        LOG.info("Starting application context");
    }

    /**
     * Stop de applicatie.
     */
    public static void stop() {
        if (context != null) {
            context.close();
        }
    }
}
