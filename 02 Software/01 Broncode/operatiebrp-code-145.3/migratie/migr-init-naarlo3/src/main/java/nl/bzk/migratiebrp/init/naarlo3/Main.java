/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarlo3;

import nl.bzk.algemeenbrp.util.common.Version;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import nl.bzk.migratiebrp.init.naarlo3.service.VulNaarLo3QueueService;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

/**
 * Basis runtime. Start de spring container.
 */
public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final PropertySource<?> configuration;


    /**
     * Constructor.
     * @param configuration configuratie
     */
    public Main(final PropertySource<?> configuration) {
        this.configuration = configuration;
        if (this.configuration == null) {
            throw new IllegalArgumentException("Configuratie verplicht");
        }
    }

    /**
     * Start de applicatie.
     * @param args argumenten (ongebruikt)
     */
    public static void main(final String[] args) {
        new Main(new PropertiesPropertySource("config", new ClassPathResource("/init-naarlo3.properties"))).start();
    }

    /**
     * Start de applicatie.
     */
    public void start() {
        final Version version = Version.readVersion("nl.bzk.migratiebrp", "migr-init-naarlo3");
        LOG.info("Starten applicatie ({}) ...\nComponenten:\n{}", version.toString(), version.toDetailsString());

        final GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.load("init-naarlo3.xml");
        context.getEnvironment().getPropertySources().addLast(configuration);
        context.refresh();

        // Try to shutdown neatly even if stop() is not called
        context.registerShutdownHook();

        LOG.info("Application context started");

        final VulNaarLo3QueueService vulNaarLo3QueueService = (VulNaarLo3QueueService) context.getBean("vulNaarLo3QueueService");

        LOG.info("Starten: lees ingeschreven en vul queue");
        vulNaarLo3QueueService.leesIngeschrevenenInBrpEnVulQueue();
        LOG.info("Gereed: lees ingeschreven en vul queue");

        context.close();
    }
}
