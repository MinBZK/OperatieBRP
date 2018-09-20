/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.partijen.dao;

import java.util.List;

import nl.bzk.brp.beheer.model.Partij;


/**
 * Deze class verzorgd voor het ophalen en opslaan van de Partij.
 *
 * @author hosing.lee
 *
 */
public interface PartijDao {

    /**
     * Haal een partij op met een id.
     *
     * @param id partij id
     * @return Partij
     */
    Partij getById(final long id);

    /**
     * Zoek partij op met de waarde, de waarde wordt gebruikt om te zoeken in
     * naam, sector en soort.
     *
     * @param waarde de zoek waarde
     * @param startPosition
     *            start positie van de lijst
     * @param maxResult
     *            maximaal resultaten dat returneerd mag worden
     * @return lijst van partijen
     */
    List<Partij> findPartij(final String waarde, final int startPosition, final int maxResult);

    /**
     * Tell het totaal aan rijen met de zoek waarde, de zoek criteria is
     * hetzelfde als in de methode findPartij(String waarde, int startPosition,
     * int maxResult).
     *
     * @param waarde
     *            zoek criteria
     * @return aantal rijen
     */
    Long tellPartijen(String waarde);

    /**
     * Opslaan van de partij.
     *
     * @param partij Partij
     * @return nieuwe referentie naar het object Partij
     */
    Partij save(Partij partij);
}
