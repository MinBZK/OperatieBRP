/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bijhouding;

import nl.bzk.brp.business.dto.BerichtResultaat;

/**
 * Het antwoord bericht voor verhuizingen.
 */
public class VerhuizingAntwoordBericht extends BijhoudingAntwoordBericht {

    /**
     * Constructor op basis van een bericht resultaat object uit de business laag.
     *
     * @param berichtResultaat Het bericht resultaat uit de business laag.
     */
    public VerhuizingAntwoordBericht(final BerichtResultaat berichtResultaat) {
        super(berichtResultaat);
    }
}
