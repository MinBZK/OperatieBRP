/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.dal;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.repositories.BasisRepository;

import java.util.List;

/**
 * CRUD functionaliteit voor de {@link ToegangBijhoudingsautorisatie} entity.
 */
public interface ToegangBijhoudingsautorisatieRepository extends BasisRepository<ToegangBijhoudingsautorisatie, Integer> {

    /**
     * Zoekt een ToegangBijhoudingsautorisatie a.d.h.v. de gegeven {@link Partij}.
     * @param geautoriseerde de geautoriseerde partij
     * @return de ToegangBijhoudingsautorisatie
     */
    List<ToegangBijhoudingsautorisatie> findByGeautoriseerde(Partij geautoriseerde);
}
