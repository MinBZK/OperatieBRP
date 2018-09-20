/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto;


/**
 * Enumeratie voor de verschillende 'zwaartes' die een fout kan hebben.
 */
public enum BerichtVerwerkingsFoutZwaarte {

    /** Informatieve (fout) melding; niet werkelijk een fout, maar informatie aangaande veld. */
    INFO,
    /**
     * Waarschuwing; geen harde fout, maar mogelijk een incorrecte waarde of een waarde die problematisch
     * zou kunnen zijn.
     */
    WAARSCHUWING,
    /** Fout in de verwerking van het bericht en waardoor bericht niet correct is verwerkt. */
    FOUT,
    /** Systeem fout waardoor bericht niet correct kon worden verwerkt. */
    SYSTEEM;

}
