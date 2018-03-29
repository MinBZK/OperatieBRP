/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox;

import java.util.Calendar;
import nl.bzk.algemeenbrp.util.common.Version;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

/**
 * Main voor mailbox server.
 */
public final class MailboxMain {

    private static final Logger LOG = LoggerFactory.getLogger();
    private final PropertySource<?> configuration;
    private GenericXmlApplicationContext context;

    /**
     * Constructor.
     * @param configuration configuratie
     */
    public MailboxMain(final PropertySource<?> configuration) {
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
        new MailboxMain(new PropertiesPropertySource("configuratie", new ClassPathResource("/tools-mailbox.properties"))).start();
    }

    /**
     * Start de applicatie.
     */
    public void start() {
        final Version version = Version.readVersion("nl.bzk.migratiebrp.tools", "migr-tools-mailbox");

        LOG.debug("Starten applicatie ({}) ...\nComponenten:\n{}", version.toString(), version.toDetailsString());

        context = new GenericXmlApplicationContext();
        context.load("classpath:tools-mailbox.xml");
        context.getEnvironment().getPropertySources().addLast(configuration);
        context.refresh();

        // Try to shutdown neatly even if stop() is not called
        context.registerShutdownHook();

        LOG.info("Applicatie ({}) gestart om {}", version.toString(), Calendar.getInstance().getTime());
    }

    /**
     * Stop de applicatie.
     */
    public void stop() {
        context.close();
    }
}
