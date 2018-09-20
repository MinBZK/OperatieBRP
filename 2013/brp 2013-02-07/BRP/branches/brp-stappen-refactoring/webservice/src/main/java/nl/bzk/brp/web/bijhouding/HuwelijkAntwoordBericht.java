/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bijhouding;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;


/**
 * Het antwoord bericht voor registratie huwelijk en geregistreerd partnerschap.
 */
public class HuwelijkAntwoordBericht extends AbstractBijhoudingAntwoordBericht {

    public HuwelijkAntwoordBericht() {
        super(SoortBericht.H_G_P_REGISTREER_HUWELIJK_B_R);
    }
}
