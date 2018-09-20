/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.repository;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Betrokkenheid;

/**
 * CRUD functionaliteit voor Betrokkenheid entities.
 * 
 */
public interface BetrokkenheidRepository {

    /**
     * Verwijderd de historie van de meegeven betrokkenheid.
     * 
     * @param betrokkenheid
     *            de betrokkenheid waarvan de historie moet worden verwijderd
     */
    void removeBetrokkenheidHistorie(final Betrokkenheid betrokkenheid);

    /**
     * Verwijderd de betrokkenheid inclusief zijn historie.
     * 
     * @param ouderBetrokkenheid
     *            de betrokkenheid die moet worden verwijderd
     */
    void remove(Betrokkenheid ouderBetrokkenheid);

    /**
     * Overschrijft de actuele en historie gegevens van ouder en ouderlijk gezag voor de betrokkenheid daarnaast wordt
     * de persoon overschreven.
     * 
     * @param doel
     *            de betrokkenheid waarnaar de gegevens moeten worden gekopieerd
     * @param bron
     *            de betrokkenheid vanwaar de gegevens worden gekopieerd
     */
    void overschrijfBetrokkenheid(Betrokkenheid doel, Betrokkenheid bron);
}
