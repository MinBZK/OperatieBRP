/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.repositories;

/**
 * Definieert methode voor het leegmaken van de cache die gebruikt kan worden door cache controllers.
 */
public interface CacheClearable {

    /**
     * Maakt de cache leeg.
     */
    void clear();

}
