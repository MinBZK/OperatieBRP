/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel;

import java.util.List;
import java.util.Map;

/**
 * Een adapter interface voor het vullen van stamtabellen o.b.v. een ander model.
 * @param <L> het LO3 type
 * @param <B> het BRP type
 */
public interface ConversietabelVuller<L, B> {

    /**
     * Geef de waarde van stamtabel vulling.
     * @return een lijst van mappings tussen LO3 en BRP codes
     */
    List<Map.Entry<L, B>> getStamtabelVulling();
}
