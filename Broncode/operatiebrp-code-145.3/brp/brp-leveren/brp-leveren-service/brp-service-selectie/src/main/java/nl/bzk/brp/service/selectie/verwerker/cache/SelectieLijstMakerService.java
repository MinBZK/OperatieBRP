/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker.cache;

import nl.bzk.brp.domain.expressie.SelectieLijst;

/**
 * SelectieLijstMakerService.
 */
public interface SelectieLijstMakerService {
    /**
     * @param dienstId dienstId
     * @param selectieTaakId selectieTaakId
     * @return selectielijst
     */
    SelectieLijst maak(final Integer dienstId, Integer selectieTaakId);
}
