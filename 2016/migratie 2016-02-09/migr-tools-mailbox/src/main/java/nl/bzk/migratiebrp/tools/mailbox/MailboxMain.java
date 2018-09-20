/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox;

import java.util.Calendar;
import nl.bzk.migratiebrp.util.common.Version;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main voor mailbox server.
 */
public final class MailboxMain {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static ConfigurableApplicationContext context;

    private MailboxMain() {
        // Niet instantieerbaar.
    }

    /**
     * Start de applicatie.
     *
     * @param argv
     *            argumenten (ongebruikt)
     */
    public static void main(final String[] argv) {
        final Version version = Version.readVersion("nl.bzk.migratiebrp.tools", "migr-tools-mailbox");

        LOG.debug("Starten applicatie ({}) ...\nComponenten:\n{}", version.toString(), version.toDetailsString());

        context = new ClassPathXmlApplicationContext("tools-mailbox.xml");
        context.registerShutdownHook();

        LOG.info("Applicatie ({}) gestart om {}", version.toString(), Calendar.getInstance().getTime());
    }

    /**
     * Stop de applicatie.
     */
    public static void stop() {
        context.close();
    }
}
