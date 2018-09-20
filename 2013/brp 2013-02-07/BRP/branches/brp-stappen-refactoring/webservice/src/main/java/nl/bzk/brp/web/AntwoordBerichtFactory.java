/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;

/**
 *
 */
public interface AntwoordBerichtFactory {

    /**
     * Stelt het antwoord bericht samen op basis van het ingaande bericht en het resultaat van de verwerking.
     *
     * @param ingaandBericht Het ingaande bericht.
     * @param resultaat Resultaat van de verwerking uit de business laag.
     * @return Het antwoord bericht.
     */
    AbstractAntwoordBericht bouwAntwoordBericht(final Bericht ingaandBericht,
                                                final BerichtVerwerkingsResultaat resultaat);

}
