/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel.repository;

import javax.persistence.EntityManager;

import nl.bzk.brp.bmr.metamodel.NamedModelElement;


/**
 * Abstracte repository interface voor alle metamodel element repositories.
 */
public interface ModelRepository {

    /**
     * Haal de applicatie met de gegeven naam uit de BMR.
     *
     * @param naam de naam waarop gezocht wordt.
     * @return de gevonden applicatie, of <code>null</code> als er geen applicatie met de gegeven naam is.
     */
    NamedModelElement findByNaam(final String naam);

    /**
     * Getter voor de entityManager property.
     *
     * @return de waarde van de entityManager property.
     */
    EntityManager getEntityManager();
}
