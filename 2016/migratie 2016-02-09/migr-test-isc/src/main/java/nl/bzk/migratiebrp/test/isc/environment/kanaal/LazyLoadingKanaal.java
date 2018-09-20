/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Kanaal 'wrapper' die de spring context voor het 'verwerkende' pas opstart bij de eerste echte verwerking door het
 * kanaal.
 */
public class LazyLoadingKanaal implements Kanaal, ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final Map<Configuration, ConfigurableApplicationContext> SHARED_CONTEXTS = new HashMap<>();

    private final Kanaal worker;
    private final Configuration configuration;
    private ConfigurableApplicationContext context;
    private ApplicationContext parentContext;

    /**
     * Constructor.
     *
     * @param worker
     *            de 'verwerkende' kanaal instantie
     * @param configuration
     *            lazy loading configuratie
     */
    protected LazyLoadingKanaal(final Kanaal worker, final Configuration configuration) {
        this.worker = worker;
        this.configuration = configuration;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.
     * ApplicationContext)
     */
    @Override
    public final void setApplicationContext(final ApplicationContext applicationContext) {
        parentContext = applicationContext;
    }

    private void initialize() {
        if (context == null) {
            LOG.info("Initializing kanaal: " + worker.getKanaal());
            if (configuration.isShared()) {
                LOG.info("Looking for shared application context ...");
                context = SHARED_CONTEXTS.get(configuration);
            }

            if (context == null) {
                LOG.info("Starting new application context ({}) ...", Arrays.asList(configuration.getConfigLocations()));
                context = new ClassPathXmlApplicationContext(configuration.getConfigLocations(), parentContext);
                context.registerShutdownHook();

                if (configuration.isShared()) {
                    SHARED_CONTEXTS.put(configuration, context);
                }
            }

            LOG.info("Autowiring worker ...");
            context.getAutowireCapableBeanFactory().autowireBean(worker);

            LOG.info("Initializing done.");
        }
    }

    /**
     * Geef de geinitialiseerde verwerkende kanaal instantie. Let op: dit veroorzaakt initialisatie.
     *
     * @return verwerkende kanaal instantie.
     */
    protected final Kanaal getWorker() {
        initialize();
        return worker;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getKanaal()
     */
    @Override
    public final String getKanaal() {
        return worker.getKanaal();
    }

    @Override
    public final void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
        initialize();
        worker.verwerkUitgaand(testCasus, bericht);
    }

    @Override
    public final Bericht verwerkInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht) throws KanaalException {
        initialize();
        return worker.verwerkInkomend(testCasus, verwachtBericht);
    }

    @Override
    public final boolean controleerInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht, final Bericht ontvangenBericht) {
        initialize();
        return worker.controleerInkomend(testCasus, verwachtBericht, ontvangenBericht);
    }

    @Override
    public final void voorTestcase(final TestCasusContext testCasus) {
        initialize();
        worker.voorTestcase(testCasus);
    }

    @Override
    public final List<Bericht> naTestcase(final TestCasusContext testCasus) {
        if (context != null) {
            return worker.naTestcase(testCasus);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getStandaardVerzendendePartij()
     */
    @Override
    public final String getStandaardVerzendendePartij() {
        return worker.getStandaardVerzendendePartij();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getStandaardOntvangendePartij()
     */
    @Override
    public final String getStandaardOntvangendePartij() {
        return worker.getStandaardOntvangendePartij();
    }

    /**
     * Lazy loading configuratie.
     */
    public static final class Configuration {
        private final String[] configLocations;
        private final boolean shared;

        /**
         * Constructor (voor een deelbare spring configuratie).
         *
         * @param configLocations
         *            spring configuratie bestanden
         */
        public Configuration(final String... configLocations) {
            this(configLocations, true);
        }

        /**
         * Constructor.
         *
         * @param configLocations
         *            spring configuratie bestanden
         * @param shared
         *            true, als een ander kanaal met exact dezelfde spring configuratie bestanden dezelfde
         *            ApplicationContext mag gebruiken
         */
        public Configuration(final String[] configLocations, final boolean shared) {
            this.configLocations = ArrayUtils.clone(configLocations);
            this.shared = shared;
        }

        /**
         * Geef de waarde van config locations.
         *
         * @return config locations
         */
        public String[] getConfigLocations() {
            return ArrayUtils.clone(configLocations);
        }

        /**
         * Geef de shared.
         *
         * @return shared
         */
        public boolean isShared() {
            return shared;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + Arrays.hashCode(configLocations);
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Configuration other = (Configuration) obj;
            if (!Arrays.equals(configLocations, other.configLocations)) {
                return false;
            }
            return true;
        }
    }
}
