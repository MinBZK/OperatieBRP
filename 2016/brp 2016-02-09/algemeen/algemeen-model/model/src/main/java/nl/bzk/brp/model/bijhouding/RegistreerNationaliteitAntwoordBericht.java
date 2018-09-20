/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bijhouding;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;


/**
 * Antwoord bericht voor registratie nationaliteit.
 */
public final class RegistreerNationaliteitAntwoordBericht extends BijhoudingAntwoordBericht {

    /**
     * Standaard constructor die de soort van het bericht zet.
     */
    public RegistreerNationaliteitAntwoordBericht() {
        super(SoortBericht.BHG_NAT_REGISTREER_NATIONALITEIT_R);
    }

    public boolean isVerkrijgingNederlandseNationaliteit() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.VERKRIJGING_NEDERLANDSE_NATIONALITEIT);
    }

    public boolean isVerkrijgingVreemdeNationaliteit() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.VERKRIJGING_VREEMDE_NATIONALITEIT);
    }

    public boolean isWijzigingIndicatieNationaliteit() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.WIJZIGING_INDICATIE_NATIONALITEIT);
    }
}
