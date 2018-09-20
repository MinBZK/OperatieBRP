/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bijhouding;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;


/**
 * Antwoord bericht voor registratie kiesrecht.
 */
public final class RegistreerKiesrechtAntwoordBericht extends BijhoudingAntwoordBericht {

    /**
     * Standaard constructor die de soort van het bericht zet.
     */
    public RegistreerKiesrechtAntwoordBericht() {
        super(SoortBericht.BHG_VKZ_REGISTREER_KIESRECHT_R);
    }

    public boolean isWijzigingDeelnameEuVerkiezingen() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.WIJZIGING_DEELNAME_E_U_VERKIEZINGEN);
    }

    public boolean isWijzigingUitsluitingKiesrecht() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.WIJZIGING_UITSLUITING_KIESRECHT);
    }

}
