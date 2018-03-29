/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.amlezer;

import nl.bzk.algemeenbrp.util.common.Version;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Executable hook om berichten amlezer uit te voeren.
 */
public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger();

    private Main() {
        // Niet instantieerbaar
    }

    /**
     * Laad de application context en start de BerichtLezer bean.
     * @param args command line argumenten (ongebruikt)
     */
    public static void main(final String[] args) {
        // Startup ApplicationContext
        final Version version = Version.readVersion("nl.bzk.migratiebrp.voisc", "migr-voisc-amlezer");
        LOG.info("Starten applicatie ({}) ...\nComponenten:\n{}", version.toString(), version.toDetailsString());

        final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("classpath:amlezer.xml");
        context.registerShutdownHook();

        // Get main bean
        final BerichtLezer berichtLezer = context.getBean(BerichtLezer.class);

        // Execute
        berichtLezer.execute();

        // Shutdown
        context.close();
    }
}
