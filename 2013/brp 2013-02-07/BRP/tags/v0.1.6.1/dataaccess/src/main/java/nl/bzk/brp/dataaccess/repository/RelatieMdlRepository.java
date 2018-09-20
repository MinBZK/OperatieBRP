/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.List;

import nl.bzk.brp.dataaccess.selectie.RelatieSelectieFilter;
import org.springframework.stereotype.Repository;

/** Repository voor de {@link RelatieMdl} class. */
@Repository
public interface RelatieMdlRepository {

    /**
     * Een gnerieke methode om de relaties mbt. de persoon op te halen. Aan de hand van de waarden in de filter
     * kan men bepaalde relatie type / betrekking type etc. uit selecteren.
     *
     * @param persoonId de hoofdpersoon
     * @param filter de filter
     * @return de relaties
     */
    List<Long> haalopRelatiesVanPersoon(final Long persoonId, final RelatieSelectieFilter filter);


}
