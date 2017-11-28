/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import java.util.List;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapException;

/**
 * ZoekPersoon.
 */
public interface ZoekPersoon {

    /**
     * Bevraging stap om de persoon op te halen op basis van een persoon id.
     * @param <T> specifieke implementatie van een zoek persoon verzoek
     */
    @FunctionalInterface
    interface OphalenPersoonService<T extends ZoekPersoonGeneriekVerzoek> {

        /**
         * Haal de personen op.
         * @param verzoek het bevragingverzoek
         * @param autorisatiebundel autorisatiebundel
         * @return persoon
         * @throws StapException indien ophalen personen mislukt
         */
        List<Persoonslijst> voerStapUit(T verzoek, Autorisatiebundel autorisatiebundel) throws
                StapException;

    }
}
