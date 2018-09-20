/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel.repository;

import java.util.List;

import nl.bzk.brp.bmr.metamodel.Laag;


/**
 * Abstracte repository interface voor alle metamodel element repositories.
 */
public interface LaagRepository extends ModelRepository {

    /**
     * Haal alle instanties van type <T> uit de BMR.
     *
     * @return een lijst van alle instanties van type <T>.
     */
    List<Laag> findAll();

    /**
     * Haal de instantie van type <T> uit de BMR met de gegeven identificatie.
     *
     * @param id de identificatie van de instantie van type <T>.
     *
     * @return de instantie van type <T> met de gegeven identificatie.
     */
    Laag findOne(final Long id);

    /**
     * Haal de laag uit de BMR met de gegeven naam.
     *
     * @param naam de naam van de laag waarop gezocht wordt.
     * @return de gevonden naam.
     */
    Laag findByNaam(final String naam);

}
