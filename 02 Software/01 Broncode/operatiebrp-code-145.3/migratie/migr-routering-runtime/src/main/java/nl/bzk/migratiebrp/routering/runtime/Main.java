/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.routering.runtime;

import java.util.Calendar;
import nl.bzk.algemeenbrp.util.common.Version;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

/**
 * Routering runtime.
 */
public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger();

    public static final String BEAN_NAME = "main";

    private GenericXmlApplicationContext context;
    private final PropertySource<?> configuration;
    private final KeepAlive keepAlive = new KeepAlive();

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
        new Main(new PropertiesPropertySource("configuratie", new ClassPathResource("/routering-runtime.properties"))).start();
    }

    /**
     * Start de applicatie.
     */
    public void start() {
        final Version version = Version.readVersion("nl.bzk.migratiebrp.routering", "migr-routering-runtime");
        LOG.info(FunctioneleMelding.ROUTERING_STARTEN_APPLICATIE, "Starten applicatie ({}) ...\nComponenten:\n{}", version.toString(),
                version.toDetailsString());

        final DefaultListableBeanFactory parentBeanFactory = new DefaultListableBeanFactory();
        parentBeanFactory.registerSingleton(BEAN_NAME, this);
        final GenericApplicationContext parentContext = new GenericApplicationContext(parentBeanFactory);
        parentContext.refresh();

        context = new GenericXmlApplicationContext();
        context.setParent(parentContext);
        context.load("classpath:routering-runtime.xml");
        context.getEnvironment().getPropertySources().addLast(configuration);
        context.refresh();

        // Try to shutdown neatly even if stop() is not called
        context.registerShutdownHook();

        new Thread(keepAlive).start();
        LOG.info(FunctioneleMelding.ROUTERING_APPLICATIE_CORRECT_GESTART, "Applicatie ({}) gestart om {}", version.toString(),
                Calendar.getInstance().getTime());
    }

    /**
     * Stop de applicatie.
     */
    public void stop() {
        LOG.info("Stoppen applicatie ...");
        context.close();
        LOG.info(FunctioneleMelding.ROUTERING_APPLICATIE_GESTOPT, "Applicatie gestopt ...");
        keepAlive.stop();
    }

}
