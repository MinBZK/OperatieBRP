/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.stamgegevens.service;

import java.util.List;

import nl.bzk.brp.beheer.model.Rol;
import nl.bzk.brp.beheer.model.Sector;
import nl.bzk.brp.beheer.model.SoortPartij;


/**
 * Service om de stamgegevens op te halen.
 *
 * @author hosing.lee
 *
 */
public interface StamgegevensService {

    /**
     * Haal alle partij soorten op.
     *
     * @return lijst van SrtPartij
     */
    List<SoortPartij> getSoortPartij();

    /**
     * Haal alle sectoren op.
     *
     * @return lijst van Sector
     */
    List<Sector> getSector();

    /**
     * Haal alle rollen op.
     *
     * @return lijst van Rol
     */
    List<Rol> getRollen();

    /**
     * Algemene ophaal met id methode.
     *
     * @param <T> type van de op te halen object
     * @param entityClass
     *            type entity dat gezocht moet worden
     * @param id
     *            id van de entity
     * @return object van de opgegeven type
     */
    <T> Object find(final Class<T> entityClass, final int id);
}
