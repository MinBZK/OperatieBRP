/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;


import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;

/**
 * Interface voor Betrokkenheid.
 */
public interface BetrokkenheidHisVolledig extends BetrokkenheidHisVolledigBasis {

    /**
     * Geef de verwerkingssoort terug.
     *
     * @return de verwerkingssoort
     */
    Verwerkingssoort getVerwerkingssoort();

    /**
     * Zet de verwerkingssoort.
     *
     * @param verwerkingssoort de verwerkingssoort
     */
    void setVerwerkingssoort(Verwerkingssoort verwerkingssoort);

}
