/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import nl.bzk.algemeenbrp.util.common.Version;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.util.Calendar;

/**
 * Basis runtime. Start de spring container; de spring container bevat componenten die een live
 * thread opstarten (JMS queue poller).
 */
public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger();

    public static final String BEAN_NAME = "main";

    private final Modus modus;
    private GenericXmlApplicationContext context;
    private final PropertySource<?> configuration;

    /**
     * Constructor.
     * @param modus modus (iv of synchronisatie)
     * @param configuration configuratie
     */
    public Main(final Modus modus, final PropertySource<?> configuration) {
        this.modus = modus;
        this.configuration = configuration;
        if (this.modus == null) {
            throw new IllegalArgumentException("Modus verplicht");
        }
        if (this.configuration == null) {
            throw new IllegalArgumentException("Configuratie verplicht");
        }
    }

    /**
     * Start de applicatie.
     * @param args argumenten
     */
    public static void main(final String[] args) {
        // create Options object
        final Options options = new Options();
        final Option modusOption = new Option("modus", true, "'SYNCHRONISATIE' of 'INITIELEVULLING'");
        options.addOption(modusOption);

        final CommandLineParser parser = new DefaultParser();
        try {
            final CommandLine cmd = parser.parse(options, args, true);

            if (!cmd.hasOption(modusOption.getOpt())) {
                LOG.error("Modus niet opgegeven");
                throw new IllegalArgumentException("Modus niet opgegeven.");
            }

            final Modus modus = Modus.valueOf(cmd.getOptionValue(modusOption.getOpt()).toUpperCase());

            LOG.info("Starting application (modus={})", modus);
            new Main(modus, new PropertiesPropertySource("configuratie", new ClassPathResource(modus.getProperties()))).start();
        } catch (final ParseException | IllegalArgumentException e) {
            LOG.debug("Fout tijdens uitvoeren", e);
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar synchronisatie-runtime.jar nl.bzk.migratiebrp.synchronisatie.runtime.Main", options);
            System.exit(1);
        }
    }

    /**
     * Start de applicatie.
     */
    public void start() {
        final Version version = Version.readVersion("nl.bzk.migratiebrp.synchronisatie", "migr-synchronisatie-runtime");
        LOG.info(FunctioneleMelding.SYNC_STARTEN_APPLICATIE, "Starten applicatie ({}) ...\nComponenten:\n{}", version.toString(), version
                .toDetailsString());

        final DefaultListableBeanFactory parentBeanFactory = new DefaultListableBeanFactory();
        parentBeanFactory.registerSingleton(BEAN_NAME, this);
        final GenericApplicationContext parentContext = new GenericApplicationContext(parentBeanFactory);
        parentContext.refresh();

        context = new GenericXmlApplicationContext();
        context.setParent(parentContext);
        context.load(modus.getConfiguratie());
        context.getEnvironment().getPropertySources().addLast(configuration);
        context.refresh();

        // Try to shutdown neatly even if stop() is not called
        context.registerShutdownHook();

        LOG.info(FunctioneleMelding.SYNC_APPLICATIE_GESTART, "Applicatie ({}) gestart om {}", version.toString(), Calendar.getInstance().getTime());
    }

    /**
     * Stop de applicatie.
     */
    public void stop() {
        context.close();
    }

    /**
     * Modus van de synchronisatie service.
     */
    public enum Modus {
        /**
         * Initiele vulling.
         */
        INITIELEVULLING("initielevulling.xml", "initielevulling.properties"),

        /**
         * Synchronisatie.
         */
        SYNCHRONISATIE("synchronisatie.xml", "synchronisatie.properties");


        private final String configuratie;
        private final String properties;

        /**
         * Constructor.
         * @param configuratie spring configuratie
         * @param properties properties
         */
        Modus(final String configuratie, final String properties) {
            this.configuratie = configuratie;
            this.properties = properties;
        }

        public String getConfiguratie() {
            return configuratie;
        }

        public String getProperties() {
            return properties;
        }
    }
}
