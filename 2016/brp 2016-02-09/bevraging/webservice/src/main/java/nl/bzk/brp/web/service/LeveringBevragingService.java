/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonAntwoordBericht;
import nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.bevraging.levering.ZoekPersoonAntwoordBericht;
import nl.bzk.brp.model.bevraging.levering.ZoekPersoonBericht;


/** De Service Endpoint Interface voor bevragingen tbv leveringen. */
public interface LeveringBevragingService {

    /**
     * Dit is de webservice method voor het ophalen van persoon details.
     *
     * @param geefDetailsPersoonBericht Het bevragingsbericht voor het ophalen van de persoon
     * @return het resultaat van de bevraging
     */
    GeefDetailsPersoonAntwoordBericht geefDetailsPersoon(
            final GeefDetailsPersoonBericht geefDetailsPersoonBericht);

    /**
     * Dit is de webservice method voor het zoeken van een persoon.
     *
     * @param zoekPersoonBericht Het bevragingsbericht voor het zoeken van een persoon.
     * @return het resultaat van de bevraging
     */
    ZoekPersoonAntwoordBericht zoekPersoon(
            final ZoekPersoonBericht zoekPersoonBericht);

}
