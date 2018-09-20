/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bijhouding;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;


/**
 * Het antwoord bericht voor actualiseer afstamming.
 */
public final class ActualiseerAfstammingAntwoordBericht extends BijhoudingAntwoordBericht {

    /**
     * Standaard constructor (die direct de soort van het bericht initialiseert/zet).
     */
    public ActualiseerAfstammingAntwoordBericht() {
        super(SoortBericht.BHG_AFS_ACTUALISEER_AFSTAMMING_R);
    }

    /**
     * Check of actie van de goede type (wordt gebruikt in Jibx).
     *
     * @return true als goede type, false anders.
     */
    public boolean isVaststellingOuderschap() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.VASTSTELLING_OUDERSCHAP);
    }

    /**
     * Check of actie van de goede type (wordt gebruikt in Jibx).
     *
     * @return true als goede type, false anders.
     */
    public boolean isVerbeteringGeboorteakte() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.VERBETERING_GEBOORTEAKTE);
    }

    /**
     * Check of actie van de goede type (wordt gebruikt in Jibx).
     *
     * @return true als goede type, false anders.
     */
    public boolean isToevoegingGeboorteakte() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.TOEVOEGING_GEBOORTEAKTE);
    }
}
