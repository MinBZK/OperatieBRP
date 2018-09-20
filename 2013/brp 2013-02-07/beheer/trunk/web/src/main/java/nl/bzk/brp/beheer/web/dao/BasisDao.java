/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.dao;

import java.util.List;


/**
 * Basis Dao interface voor de beheer applicatie.
 *
 *
 * @param <T>
 */
public interface BasisDao<T> {

    /**
     * Haal entity op met id.
     *
     * @param id id van het object
     * @return object van het type <T>
     */
    T getById(final int id);

    /**
     * Zoek objecten op met de object.
     *
     * @param zoekterm zoek criteria
     * @param startPosition
     *            start positie van de lijst
     * @param maxResult
     *            maximaal resultaten dat returneerd mag worden
     * @return lijst van partijen
     */
    List<T> findObject(final String zoekterm, final int startPosition, final int maxResult);

    /**
     * Tell het totaal aan rijen met de zoek object, de zoek criteria is
     * hetzelfde als in de methode findObject(final T object, final int startPosition, final int maxResult).
     *
     * @param zoekterm
     *            zoek criteria
     * @return aantal rijen
     */
    Long tellObjecten(final String zoekterm);

    /**
     * Opslaan van de partij.
     *
     * @param object <T>
     * @return nieuwe referentie naar het object
     */
    T save(T object);
}
