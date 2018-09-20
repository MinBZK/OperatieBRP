/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository;

import java.util.List;

import nl.bzk.brp.domein.lev.LeveringCommunicatie;


/**
 * Repository voor de {@link LeveringCommunicatie} class.
 */
public interface LeveringCommunicatieRepository {

    /**
     * Bewaar de meegegeven levering communicatie.
     *
     * @param levCom De levering communicatie die bewaard wordt.
     */
    void save(LeveringCommunicatie levCom);

    /**
     * Zoek alle levering communicaties.
     *
     * @return De gevonden levering communicaties.
     */
    List<LeveringCommunicatie> findAll();
}
