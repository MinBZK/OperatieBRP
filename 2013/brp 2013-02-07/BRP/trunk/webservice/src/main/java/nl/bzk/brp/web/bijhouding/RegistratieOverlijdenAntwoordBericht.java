/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bijhouding;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;


/**
 * Het antwoord bericht voor registratie huwelijk en geregistreerd partnerschap.
 */
public class RegistratieOverlijdenAntwoordBericht extends AbstractBijhoudingAntwoordBericht {

    public RegistratieOverlijdenAntwoordBericht() {
        super(SoortBericht.O_V_L_REGISTREER_OVERLIJDEN_B_R);
    }

    /**
     * Functie t.b.v. Jibx, kijkt wat of dit antwoord bericht een antwoord is op een registratie overlijden NL.
     * @return true of false.
     */
    public boolean isRegistratieOverlijdenNederland() {
        return getAdministratieveHandeling() != null
          && SoortAdministratieveHandeling.REGISTRATIE_OVERLIJDEN_NEDERLAND == getAdministratieveHandeling().getSoort();
    }

    /**
     * Functie t.b.v. Jibx, kijkt wat of dit antwoord bericht een antwoord is op een registratie overlijden buitenland.
     * @return true of false.
     */
    public boolean isRegistratieOverlijdenBuitenland() {
        return getAdministratieveHandeling() != null
         && SoortAdministratieveHandeling.REGISTRATIE_OVERLIJDEN_BUITENLAND == getAdministratieveHandeling().getSoort();
    }
}
