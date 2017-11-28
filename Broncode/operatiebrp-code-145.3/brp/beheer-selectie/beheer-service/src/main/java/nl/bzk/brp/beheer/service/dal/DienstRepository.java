/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.dal;

import java.util.Collection;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;

/**
 * Repository voor {@link Dienst}.
 */
public interface DienstRepository {

    /**
     * Geef selectiediensten binnen een periode.
     * @param beginDatum de begindatum van de periode
     * @param eindDatum de einddatum van de periode
     * @return de collectie van diensten
     */
    Collection<Dienst> getSelectieDienstenBinnenPeriode(Integer beginDatum, Integer eindDatum);

    /**
     * Vind een dienst op basis van het ID.
     * @param id het ID
     * @return {@link Dienst}
     */
    Dienst findDienstById(Integer id);
}
