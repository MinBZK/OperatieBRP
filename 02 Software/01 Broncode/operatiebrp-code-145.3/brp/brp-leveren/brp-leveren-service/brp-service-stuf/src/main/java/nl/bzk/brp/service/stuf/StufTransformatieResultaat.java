/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.stuf;

import com.google.common.collect.Lists;
import java.util.List;
import nl.bzk.brp.domain.algemeen.Melding;

/**
 * StufTransformatieResultaat.
 */
final class StufTransformatieResultaat {
    private List<Melding> meldingen = Lists.newArrayList();
    private List<StufTransformatieVertaling> vertalingen = Lists.newArrayList();

    /**
     * Gets meldingen.
     * @return the meldingen
     */
    List<Melding> getMeldingen() {
        return meldingen;
    }

    /**
     * Gets vertalingen.
     * @return the vertalingen
     */
    List<StufTransformatieVertaling> getVertalingen() {
        return vertalingen;
    }
}
