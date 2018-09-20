/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.dataaccess;

import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate session factory provider.
 */
public final class HibernateSessionFactoryProvider {

    private static Logger log = Logger.getLogger(HibernateSessionFactoryProvider.class);

    private static final String KERN_CONFIG_FILE = "hibernate/hibernate_kern.cfg.xml";
    private static final String AUTAUT_CONFIG_FILE = "hibernate/hibernate_autaut.cfg.xml";
    private static final String BRONNEN_CONFIG_FILE = "hibernate/hibernate_bronnen.cfg.xml";

    private final Properties properties;

    private final SessionFactory kernFactory;
    private final SessionFactory autautFactory;
    private final SessionFactory bronnenFactory;
    private final SessionFactory infoSchemaFactory;

    private static HibernateSessionFactoryProvider instance;

    /**
     * Instantieert Hibernate session factory provider.
     */
    public HibernateSessionFactoryProvider() {
        this(new Properties());
    }

    /**
     * Initialiseer configuratie.
     *
     * @param configuration configuratie
     */
    private void initConfiguration(final Configuration configuration) {
        for (Entry<Object, Object> entry: properties.entrySet()) {
            String name = (String) entry.getKey();
            if (name.startsWith("hibernate")) {
                configuration.setProperty(name, (String) entry.getValue());
            }
        }
    }

    /**
     * Instantieert Hibernate session factory provider.
     *
     * @param properties properties
     */
    public HibernateSessionFactoryProvider(final Properties properties) {
        this.properties = properties;
        try {
            Configuration kernconfig = new Configuration().configure(KERN_CONFIG_FILE);
            initConfiguration(kernconfig);
            kernFactory = kernconfig.buildSessionFactory();

            Configuration autautconfig = new Configuration().configure(AUTAUT_CONFIG_FILE);
            initConfiguration(autautconfig);
            autautFactory = autautconfig.buildSessionFactory();

            Configuration bronnenconfig = new Configuration().configure(BRONNEN_CONFIG_FILE);
            initConfiguration(bronnenconfig);
            bronnenFactory = bronnenconfig.buildSessionFactory();

            infoSchemaFactory = null;
            //config.buildSessionFactory();
        } catch (RuntimeException e) {
            try {
                close();
            } catch (RuntimeException ex) {
                log.error("", ex);
            }
            throw e;
        }
    }

    /**
     * Geeft instance.
     *
     * @return instance
     */
    public static HibernateSessionFactoryProvider getInstance() {
        return instance;
    }

    /**
     * Zet instance.
     *
     * @param instance instance
     */
    public static void setInstance(final HibernateSessionFactoryProvider instance) {
        HibernateSessionFactoryProvider.instance = instance;
    }

    /**
     * Sluiten.
     */
    public void close() {
        if (kernFactory != null && !kernFactory.isClosed()) try {
            kernFactory.close();
        } catch (RuntimeException e) {
            log.error("", e);
        } finally {
            if (bronnenFactory != null && !bronnenFactory.isClosed()) try {
                bronnenFactory.close();
            } catch (RuntimeException e) {
                log.error("", e);
            } finally {
                if (autautFactory != null && !autautFactory.isClosed()) try {
                    autautFactory.close();
                } catch (RuntimeException e) {
                    log.error("", e);
                } finally {
                    if (infoSchemaFactory != null && !infoSchemaFactory.isClosed()) try {
                        infoSchemaFactory.close();
                    } catch (RuntimeException e) {
                        log.error("", e);
                    }
                }
            }
        }
    }

    public SessionFactory getKernFactory() {
        return kernFactory;
    }

    public SessionFactory getBronnenFactory() {
        return bronnenFactory;
    }

    public SessionFactory getInfoSchemaFactory() {
        return infoSchemaFactory;
    }

    public SessionFactory getAutautFactory() {
        return autautFactory;
    }
}
