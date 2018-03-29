/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.persoon;

import nl.bzk.brp.domain.berichtmodel.SynchroniseerPersoonAntwoordBericht;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;

/**
 * Interface voor het synchroniseren van een persoon.
 */
public interface SynchroniseerPersoonService {

    /**
     * Voert de synchroniseer persoon logica uit.
     * @param verzoek het binnenkomende verzoek
     * @param synchronisatieCallback synchronisatieCallback
     */
    void synchroniseer(SynchronisatieVerzoek verzoek, SynchronisatieCallback<String> synchronisatieCallback);

    /**
     * Maak het mogelijk de marshalling door de caller te laten doen, zonder de controle over flow uit handen te geven.
     * @param <T> resultaat type
     */
    interface SynchronisatieCallback<T> {

        /**
         * Verwerk het {@link SynchroniseerPersoonAntwoordBericht}.
         * @param bericht het {@link SynchroniseerPersoonAntwoordBericht}
         */
        void verwerkResultaat(SynchroniseerPersoonAntwoordBericht bericht);

        /**
         * Geef het verwerkte resultaat.
         * @return het verwerkte resultaat
         */
        T getResultaat();
    }
}
