/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.relateren.business;

/**
 * Interface voor business logica voor het relateren van een persoon.
 */
public interface RelateerPersoon {

    /**
     * Methode voor het relateren ip basis van het ID van een persoon.
     * 
     * @param id
     *            van persoon
     * @return true als er geen problemen zijn opgetreden
     */
    boolean relateerOpBasisVanID(Long id);
}
