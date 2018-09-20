/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.configuratie;

import org.jbpm.calendar.Duration;

/**
 * Configuratie.
 */
public interface ConfiguratieDao {

    /**
     * Haal een configuratie setting op.
     *
     * @param naam
     *            setting
     *
     * @return waarde
     */
    String getConfiguratie(String naam);

    /**
     * Haal een configuratie setting op als integer.
     *
     * @param naam
     *            setting
     * @return waarde
     */
    Integer getConfiguratieAsInteger(String naam);

    /**
     * Haal een configuratie settings op als (jbpm) duration.
     *
     * @param naam
     *            setting
     * @return waarde
     */
    Duration getConfiguratieAsDuration(String naam);

}
