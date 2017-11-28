/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.algemeen;

import nl.bzk.brp.service.algemeen.StapMeldingException;

/**
 * Service-interface vh valideren van formele en/of materiele peilmomenten.
 * Een peilmoment moet het juiste datum formaat hebben en mag niet in de toekomst liggen.
 */
public interface PeilmomentValidatieService {

    /**
     * Valideert een materieel peilmoment op formaat en inhoud (mag niet toekomstig zijn).
     * @param peilmomentMaterieel materieel peilmoment
     * @throws StapMeldingException indien validatie faalt
     */
    void valideerMaterieel(String peilmomentMaterieel) throws StapMeldingException;

    /**
     * Valideert een materieel/formeel peilmoment combinatie op formaat en inhoud (mag niet toekomstig zijn).
     * @param peilmomentMaterieel materieel peilmoment
     * @param peilmomentFormeel formeel peilmoment
     * @throws StapMeldingException indien validatie faalt
     */
    void valideerFormeelEnMaterieel(String peilmomentMaterieel, String peilmomentFormeel) throws StapMeldingException;
}
