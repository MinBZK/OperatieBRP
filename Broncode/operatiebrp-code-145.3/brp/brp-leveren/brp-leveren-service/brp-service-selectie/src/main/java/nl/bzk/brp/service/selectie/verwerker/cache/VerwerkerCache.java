/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker.cache;

import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.expressie.SelectieLijst;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAutorisatieBericht;

/**
 * VerwerkerAutorisatieCache.
 */
public interface VerwerkerCache {

    /**
     * @param selectieAutorisatieBericht selectieAutorisatieBericht
     * @param selectierunId selectierunId
     * @return autorisatiebundel
     */
    Autorisatiebundel getAutorisatiebundel(SelectieAutorisatieBericht selectieAutorisatieBericht, Integer selectierunId);

    /**
     * @param dienstId dienstId
     * @param selectieTaakId selectieTaakId
     * @param selectierunId selectierunId
     * @return selectieLijstMap
     */
    SelectieLijst getSelectieLijst(final Integer dienstId, final Integer selectieTaakId, final Integer selectierunId);

    /**
     * clear.
     */
    void clear();
}
