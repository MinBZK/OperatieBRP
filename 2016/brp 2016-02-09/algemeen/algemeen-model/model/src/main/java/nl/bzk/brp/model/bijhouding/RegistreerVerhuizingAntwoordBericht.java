/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bijhouding;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;


/**
 * Het antwoord bericht voor verhuizingen.
 */
public final class RegistreerVerhuizingAntwoordBericht extends BijhoudingAntwoordBericht {

    /**
     * Standaard constructor (die direct de soort van het bericht initialiseert/zet).
     */
    public RegistreerVerhuizingAntwoordBericht() {
        super(SoortBericht.BHG_VBA_REGISTREER_VERHUIZING_R);
    }

    public boolean isVerhuizingBinnengemeentelijk() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK);
    }

    public boolean isVerhuizingIntergemeentelijk() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.VERHUIZING_INTERGEMEENTELIJK);
    }

    public boolean isVerhuizingNaarBuitenland() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.VERHUIZING_NAAR_BUITENLAND);
    }
}
