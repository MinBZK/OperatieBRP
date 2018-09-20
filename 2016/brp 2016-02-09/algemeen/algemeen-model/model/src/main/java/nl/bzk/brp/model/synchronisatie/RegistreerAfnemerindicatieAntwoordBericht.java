/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.synchronisatie;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.bijhouding.BijhoudingAntwoordBericht;


/**
 * Het antwoordbericht voor afnemer indicaties.
 */
public class RegistreerAfnemerindicatieAntwoordBericht extends BijhoudingAntwoordBericht {

    /**
     * Standaard constructor (die direct de soort van het bericht initialiseert/zet).
     */
    public RegistreerAfnemerindicatieAntwoordBericht() {
        super(SoortBericht.LVG_SYN_REGISTREER_AFNEMERINDICATIE_R);
    }

    /**
     * Bepaalt of het om een plaatsing gaat, gebruikt vanuit de bindings.
     *
     * @return Boolean true als het om een plaatsing gaat, anders false.
     */
    public boolean isPlaatsingAfnemerIndicatie() {
        return SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE.equals(getAdministratieveHandeling().getSoort()
            .getWaarde());
    }

    /**
     * Bepaalt of het om een verwijdering gaat, gebruikt vanuit de bindings.
     *
     * @return Boolean true als het om een plaatsing gaat, anders false.
     */
    public boolean isVerwijderingAfnemerIndicatie() {
        return SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE.equals(getAdministratieveHandeling()
            .getSoort().getWaarde());
    }

}
