/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.dao;

/**
 * Interface klasse voor Lock DAO.
 */
public interface RuntimeDao {

    /**
     * Voegt in de database een lock met de meegegeven runtimenaam toe.
     * @param runtimeNaam De runtime waarvoor we de lock toevoegen.
     */
    void voegRuntimeToe(String runtimeNaam);

    /**
     * Verwijdert in de database een lock van de meegegeven runtimenaam.
     * @param runtimeNaam De runtime waarvoor we de lock verwijderen.
     */
    void verwijderRuntime(String runtimeNaam);

}
