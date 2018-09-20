/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.service.bericht;

import nl.bzk.brp.logging.MDC;
import nl.bzk.brp.logging.MDCVeld;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;

/**
 * Abstracte Afleverservice, die het proces van het leveren stuurt.
 */
public abstract class AbstractAfleverService implements AfleverService {

    /**
     * Voegt elementen toe aan de logging context.
     *
     * @param toegangLeveringsautorisatie model waar waardes uitgehaald worden om op de context te plaatsen
     */
    protected final void updateMDC(final ToegangLeveringsautorisatie toegangLeveringsautorisatie) {
        MDC.put(MDCVeld.MDC_TOEGANG_LEVERINGSAUTORISATIE_ID, String.valueOf(toegangLeveringsautorisatie.getID()));
        MDC.put(MDCVeld.MDC_PARTIJ_CODE,
            String.valueOf(toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode().toString()));
        MDC.put(MDCVeld.MDC_LEVERINGAUTORISATIEID,
            toegangLeveringsautorisatie.getLeveringsautorisatie().getID().toString());
    }

    /**
     * Verwijdert gelpaatste elementen uit de logging context.
     */
    protected final void cleanMDC() {
        MDC.remove(MDCVeld.MDC_TOEGANG_LEVERINGSAUTORISATIE_ID);
        MDC.remove(MDCVeld.MDC_PARTIJ_CODE);
        MDC.remove(MDCVeld.MDC_LEVERINGAUTORISATIEID);
    }
}
