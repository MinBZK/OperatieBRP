/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bevraging;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;


/**
 * Het antwoord bericht voor bevragingen van betrokkenheden op een adres.
 *
 */
public class VraagKandidaatVaderAntwoordBericht extends AbstractBevragingAntwoordBericht {

    public VraagKandidaatVaderAntwoordBericht() {
        super(SoortBericht.A_L_G_BEPAAL_KANDIDAAT_VADER_VI_R);
    }

    /**
     * Controlleert of de persoon gevonden is.
     *
     * @return true als de persoon gevonden is
     */
    public boolean isPersoonGevonden() {
        return getPersonen() != null && !getPersonen().isEmpty();
    }
}
