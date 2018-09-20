/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bijhouding;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;


/**
 * Antwoord bericht voor registratie document / verzoek / mededeling.
 */
public final class RegistreerMededelingVerzoekAntwoordBericht extends BijhoudingAntwoordBericht {

    /**
     * Standaard constructor die de soort van het bericht zet.
     */
    public RegistreerMededelingVerzoekAntwoordBericht() {
        super(SoortBericht.BHG_DVM_REGISTREER_MEDEDELING_VERZOEK_R);
    }

    public boolean isWijzigingVerstrekkingsbeperking() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.WIJZIGING_VERSTREKKINGSBEPERKING);
    }

    public boolean isWijzigingGezag() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.WIJZIGING_GEZAG);
    }

    public boolean isWijzigingCuratele() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.WIJZIGING_CURATELE);
    }
}
