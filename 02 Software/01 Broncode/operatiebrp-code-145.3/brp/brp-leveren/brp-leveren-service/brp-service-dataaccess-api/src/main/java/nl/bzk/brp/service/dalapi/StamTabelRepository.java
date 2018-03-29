/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.dalapi;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.domain.algemeen.StamgegevenTabel;


/**
 * Stamtabel service.
 */
@FunctionalInterface
public interface StamTabelRepository {


    /**
     * @param stamgegevenTabel stamgegevenTabel
     * @return stamgegevens
     */
    List<Map<String, Object>> vindAlleStamgegevensVoorTabel(StamgegevenTabel stamgegevenTabel);
}
