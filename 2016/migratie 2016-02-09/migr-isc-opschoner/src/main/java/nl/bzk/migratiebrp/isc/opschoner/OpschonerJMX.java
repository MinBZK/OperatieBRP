/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner;

/**
 * Interface voor het starten van het opschonen van tellingen.
 */
public interface OpschonerJMX {

    /**
     * Service voor het opschonen van de tellingen.
     */
    void opschonen();

    /**
     * JMX service voor het opschonen van tellingen.
     *
     * @param aantalUren
     *            aantal uren
     */
    void opschonen(final int aantalUren);
}
