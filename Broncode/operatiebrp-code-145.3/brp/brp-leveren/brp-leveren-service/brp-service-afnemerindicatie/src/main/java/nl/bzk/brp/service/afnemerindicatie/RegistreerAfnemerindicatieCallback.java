/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.afnemerindicatie;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.berichtmodel.OnderhoudAfnemerindicatieAntwoordBericht;

/**
 * BevragingCallback.
 * @param <T> type resultaat
 */
public interface RegistreerAfnemerindicatieCallback<T> {

    /**
     * Verwerk het {@link OnderhoudAfnemerindicatieAntwoordBericht}.
     * @param soortDienst de soortDiensto
     * @param bericht het {@link OnderhoudAfnemerindicatieAntwoordBericht}
     */
    void verwerkResultaat(SoortDienst soortDienst, OnderhoudAfnemerindicatieAntwoordBericht bericht);

    /**
     * Geef het verwerkte resultaat.
     * @return het verwerkte resultaat
     */
    T getResultaat();
}
