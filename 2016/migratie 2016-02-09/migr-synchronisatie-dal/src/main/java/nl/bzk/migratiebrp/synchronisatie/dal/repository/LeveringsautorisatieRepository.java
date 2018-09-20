/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import java.util.List;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Leveringsautorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PartijRol;

/**
 * Repository voor leveringsautorisaties.
 */
public interface LeveringsautorisatieRepository {

    /**
     * Vind alle leveringsautorisaties voor een gegeven partijrol.
     *
     * @param partijRol
     *            de partijrol
     * @return leveringsautorisaties
     */
    List<Leveringsautorisatie> findLeveringsautorisatiesVoorPartij(PartijRol partijRol);

}
