/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies;

import nl.bzk.brp.expressietaal.EvaluatieFoutCode;

/**
 * Interface voor foutmeldingen in expressies.
 */
public interface Foutmelding {
    /**
     * Geeft de foutcode die hoort bij de foutmelding.
     *
     * @return Foutcode die hoort bij de foutmelding.
     */
    EvaluatieFoutCode getFoutCode();

    /**
     * Geeft extra informatie (indien beschikbaar) bij de gevonden fout.
     *
     * @return Extra informatie bij de gevonden fout.
     */
    String getInformatie();
}
