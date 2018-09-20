/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bijhouding;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;


/**
 */
public final class RegistreerErkenningAntwoordBericht extends BijhoudingAntwoordBericht {

    /**
     * Standaard constructor die de soort van het bericht zet.
     */
    public RegistreerErkenningAntwoordBericht() {
        super(SoortBericht.BHG_AFS_REGISTREER_ERKENNING_R);
    }

    /**
     * Functie t.b.v. Jibx, kijkt wat of dit antwoord bericht een antwoord is op een registratie overlijden NL.
     *
     * @return true of false.
     */
    public boolean isErkenningNaGeboorte() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.ERKENNING_NA_GEBOORTE);
    }
}
