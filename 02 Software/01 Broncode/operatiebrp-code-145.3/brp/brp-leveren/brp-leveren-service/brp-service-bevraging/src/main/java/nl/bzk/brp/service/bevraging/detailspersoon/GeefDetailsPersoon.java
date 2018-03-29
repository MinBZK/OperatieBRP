/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.detailspersoon;

import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapException;


/**
 * Container interface voor bevragingstappen.
 */
interface GeefDetailsPersoon {
    /**
     * Bevraging stap om de persoon op te halen op basis van een persoon id.
     */
    @FunctionalInterface
    interface OphalenPersoonService {

        /**
         * Haal de persoon op.
         * @param verzoek het bevragingverzoek
         * @param autorisatiebundel autorisatiebundel
         * @return persoon
         * @throws StapException indien ophalen persoon mislukt
         */
        Persoonslijst voerStapUit(GeefDetailsPersoonVerzoek verzoek, Autorisatiebundel autorisatiebundel) throws
                StapException;

    }
}
