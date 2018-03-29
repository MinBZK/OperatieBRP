/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.injection;

import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.exception.TestException;

/**
 * Bericht informatie extractor.
 *
 * Ondersteunde sleutels:
 * <ul>
 * <li>messageid</li>
 * <li>correlationid</li>
 * <li>mssequencenr</li>
 * <li>originator</li>
 * <li>recipient</li>
 * </ul>
 */
public final class ExtractorHeader implements Extractor {

    @Override
    public String extract(final Context context, final Bericht bericht, final String key) throws TestException {
        final String resultaat;

        switch (key.toLowerCase()) {
            case "messageid":
                resultaat = bericht.getBerichtReferentie();
                break;
            case "correlationid":
                resultaat = bericht.getCorrelatieReferentie();
                break;
            case "mssequencenr":
                resultaat = bericht.getMsSequenceNumber();
                break;
            case "originator":
                resultaat = bericht.getVerzendendePartij();
                break;
            case "recipient":
                resultaat = bericht.getOntvangendePartij();
                break;
            default:
                throw new TestException("Key '" + key + "' onbekend in header extractor.");
        }

        return resultaat;
    }
}
