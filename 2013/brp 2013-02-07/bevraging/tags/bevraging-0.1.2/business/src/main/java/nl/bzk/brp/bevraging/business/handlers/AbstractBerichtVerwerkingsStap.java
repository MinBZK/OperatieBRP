/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;


/**
 * Abstracte implementatie van de {@link BerichtVerwerkingsStap} interface waarbij deze implementatie een standaard
 * lege implementatie biedt voor de {@link BerichtVerwerkingsStap#naVerwerkingsStapVoorBericht()}
 * methode die verder niets doet.
 */
public abstract class AbstractBerichtVerwerkingsStap implements BerichtVerwerkingsStap {

    /**
     * Standaard lege implementatie daar er in de meeste stappen geen naverwerking nodig is.
     * @param bericht het bericht waarvoor de stap is uitgevoerd.
     */
    @Override
    public void naVerwerkingsStapVoorBericht(final BrpBerichtCommand bericht) {
        // Do Nothing
    }

}
