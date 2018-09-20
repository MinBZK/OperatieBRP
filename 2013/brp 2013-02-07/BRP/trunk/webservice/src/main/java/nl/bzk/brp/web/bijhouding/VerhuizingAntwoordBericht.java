/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bijhouding;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;

/**
 * Het antwoord bericht voor verhuizingen.
 */
public class VerhuizingAntwoordBericht extends AbstractBijhoudingAntwoordBericht {

    public VerhuizingAntwoordBericht() {
        super(SoortBericht.M_I_G_REGISTREER_VERHUIZING_B_R);
    }

    public boolean isRegistratieBinnengemeentelijkeVerhuizing() {
        return getAdministratieveHandeling() != null
                && SoortAdministratieveHandeling.REGISTRATIE_BINNENGEMEENTELIJKE_VERHUIZING
                == getAdministratieveHandeling().getSoort();
    }

    public boolean isRegistratieIntergemeentelijkeVerhuizing() {
        return getAdministratieveHandeling() != null
                && SoortAdministratieveHandeling.REGISTRATIE_INTERGEMEENTELIJKE_VERHUIZING
                == getAdministratieveHandeling().getSoort();
    }
}
