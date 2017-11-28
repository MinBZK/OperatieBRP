/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.autorisatie;

/**
 * API interface voor het beheren van diensten.
 */
public interface DienstService {

    /**
     * Geef een dienst terug op basis van dienst id.
     * @param id het dienst id
     * @return de {@link DienstDTO}
     */
    DienstDTO getDienst(Integer id);
}
