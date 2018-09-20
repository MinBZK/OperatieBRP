/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.migratie.repository;

import java.util.List;

import nl.moderniseringgba.isc.migratie.domein.entities.Gemeente;

/**
 * Gemeente repository.
 */
public interface GemeenteRepository {

    /**
     * Zoek een gemeente.
     * 
     * @param gemeenteCode
     *            code
     * @return gemeente (of null als niet gevonden)
     */
    Gemeente findGemeente(final int gemeenteCode);

    /**
     * Geeft een lijst van gemeente die over gegaan zijn naar BRP.
     * 
     * @return Geeft een lijst van gemeente die een datumbrp hebben die op of voor vandaag ligt.
     */
    List<Gemeente> getBrpActiveGemeente();
}
