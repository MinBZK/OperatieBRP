/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bijhouding;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;


/**
 * Het antwoord bericht voor registratie overlijden.
 */
public final class RegistreerOverlijdenAntwoordBericht extends BijhoudingAntwoordBericht {

    /**
     * Standaard constructor (die direct de soort van het bericht initialiseert/zet).
     */
    public RegistreerOverlijdenAntwoordBericht() {
        super(SoortBericht.BHG_OVL_REGISTREER_OVERLIJDEN_R);
    }

    /**
     * Functie t.b.v. Jibx, kijkt wat of dit antwoord bericht een antwoord is op een registratie overlijden NL.
     *
     * @return true of false.
     */
    public boolean isOverlijdenInNederland() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.OVERLIJDEN_IN_NEDERLAND);
    }

    /**
     * Functie t.b.v. Jibx, kijkt wat of dit antwoord bericht een antwoord is op een registratie overlijden buitenland.
     *
     * @return true of false.
     */
    public boolean isOverlijdenInBuitenland() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.OVERLIJDEN_IN_BUITENLAND);
    }
}
