/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.migratie.service;

/**
 * Gemeente service.
 */
public interface GemeenteService {
    /**
     * Bepaal in welk stelsel de bijhoudignsverantwoordelijkheid voor een gemeente ligt.
     * 
     * @param gemeenteCode
     *            gemeente code
     * @return stelsel
     */
    Stelsel geefStelselVoorGemeente(int gemeenteCode);
}
