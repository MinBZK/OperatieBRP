/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.configuratie;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConversionException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Configuratie object dat BRP configuratie parameters inleest uit een properties bestand dat in de classpath staat.
 * De parameters worden via deze class aan de rest van de applicatie ter beschikking gesteld.
 * LET OP: BrpConfiguratie mag enkel gebruikt worden voor parameters die RunTime mogen worden aangepast.
 */
public class BrpConfiguratie {

    private static final Logger           LOGGER                   = LoggerFactory.getLogger(BrpConfiguratie.class);
    private static final int              MILLISECONDEN_IN_MINUTEN = 60 * 1000;

    private static final String           DATABASE_TIMEOUT_SECONDEN_PROPERTY         = "brp.database.timeout.seconden";
    private static final int              DATABASE_TIMEOUT_SECONDEN_PROPERTY_DEFAULT = 5;

    private final PropertiesConfiguration configuratie;

    /**
     * Construeert een BrpConfiguratie object. Dit object laadt alle properties uit propertiesBestand.
     * Dit bestand moet in de ClassPath aanwezig zijn. Let op: Constructor is private, want deze class is als
     * utility class ingericht. Wordt enkel geinstantieerd door Spring.
     *
     * @param propertiesBestand Bestandsnaam van het properties bestand. (Bijv. brp.properties)
     * @param verversingsInterval Interval in seconden waarop de gegevens in het bestand moeten worden herladen.
     * @throws ConfigurationException Indien er problemen optreden bij het initialiseren van de configuratie.
     */
    public BrpConfiguratie(final String propertiesBestand, final int verversingsInterval) throws ConfigurationException
    {
        configuratie = new PropertiesConfiguration(propertiesBestand);
        final FileChangedReloadingStrategy reloadingStrategy = new FileChangedReloadingStrategy();
        reloadingStrategy.setRefreshDelay((long) verversingsInterval * MILLISECONDEN_IN_MINUTEN);
        configuratie.setReloadingStrategy(reloadingStrategy);
    }

    /**
     * Vraagt het database time out property op wat geconfigureerd staat in het bestande brp.properties.
     *
     * @return Het aantal seconden voordat een database time out optreedt, of de standaardwaarde indien de configuratie
     *         niet is opgegeven in het configuratie bestand.
     * @brp.bedrijfsregel BRPE0009, FTPE0004
     */
    public int getDatabaseTimeOutProperty() {
        try {
            return configuratie.getInt(DATABASE_TIMEOUT_SECONDEN_PROPERTY, DATABASE_TIMEOUT_SECONDEN_PROPERTY_DEFAULT);
        } catch (ConversionException ce) {
            LOGGER.warn("Configuratie parameter \"" + DATABASE_TIMEOUT_SECONDEN_PROPERTY
                + "\" is niet correct ingesteld.");
            LOGGER.warn("De standaard waarde wordt gebruikt.");
        }
        return DATABASE_TIMEOUT_SECONDEN_PROPERTY_DEFAULT;
    }
}
