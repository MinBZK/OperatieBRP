/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bevraging.bijhouding;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.bevraging.BevragingAntwoordBericht;


/**
 * Het antwoord bericht voor bevragingen van betrokkenheden op een adres.
 */
public class BepaalKandidaatVaderAntwoordBericht extends BevragingAntwoordBericht {

    /**
     * Standaard constructor (die direct de soort van het bericht initialiseert/zet).
     */
    public BepaalKandidaatVaderAntwoordBericht() {
        super(SoortBericht.BHG_BVG_BEPAAL_KANDIDAAT_VADER_R);
    }
}
