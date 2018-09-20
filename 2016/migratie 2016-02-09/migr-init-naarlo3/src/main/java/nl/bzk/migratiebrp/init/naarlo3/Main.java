/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarlo3;

import nl.bzk.migratiebrp.init.naarlo3.service.VulNaarLo3QueueService;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Basis runtime. Start de spring container.
 */
public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger();

    private Main() {
        throw new AssertionError("Niet instantieerbaar.");
    }

    /**
     * Run.
     *
     * @param args
     *            argumenten (ongebruikt).
     */
    public static void main(final String[] args) {

        LOG.info("Starting application context");
        final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("init-naarlo3.xml");

        context.registerShutdownHook();
        LOG.info("Application context started");

        final VulNaarLo3QueueService vulNaarLo3QueueService = (VulNaarLo3QueueService) context.getBean("vulNaarLo3QueueService");

        LOG.info("Starten: lees ingeschreven en vul queue");
        vulNaarLo3QueueService.leesIngeschrevenenInBrpEnVulQueue();
        LOG.info("Gereed: lees ingeschreven en vul queue");

        context.close();
    }

}
