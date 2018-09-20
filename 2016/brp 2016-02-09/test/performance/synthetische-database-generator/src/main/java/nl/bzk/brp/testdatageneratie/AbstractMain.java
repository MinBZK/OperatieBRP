/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie;

import java.io.IOException;
import java.util.Properties;

import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import org.apache.log4j.Logger;

public abstract class AbstractMain {

    private static final Logger LOGGER = Logger.getLogger(AfnemerIndicatiesVullenGen.class);

    /**
     * Naam van de properties bestanden waarin de configuratie voor de synthetische database generatie staat.
     */
    protected static final String PROPERTIES_FILE = "syndbgen.properties";

    private static final Properties PROPERTIES = new Properties();

    private static HibernateSessionFactoryProvider hibernateSessionFactoryProvider;

    public AbstractMain() {

    }

    private static void initProperties(){
        try {
            PROPERTIES.load(ClassLoader.getSystemResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            LOGGER.error("Fout bij laden properties-file.", e);
        }
    }

    /**
     * Verkrijgt de property op basis van key.
     *
     * @param key key van property
     * @return property waarde
     */
    public static int getProperty(final String key) {
        if(PROPERTIES.isEmpty()){
            initProperties();
        }
        final String value = PROPERTIES.getProperty(key);
        return Integer.parseInt(value);
    }

    protected static void startHibernate(){
        hibernateSessionFactoryProvider = new HibernateSessionFactoryProvider(PROPERTIES);
        HibernateSessionFactoryProvider.setInstance(hibernateSessionFactoryProvider);
    }

    protected static void sluitHibernate(){
        if (hibernateSessionFactoryProvider != null) {
            hibernateSessionFactoryProvider.close();
        }
    }

}
