/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bijhouding;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;

/**
 * Het antwoord bericht voor geboorte inschrijvingen.
 */
public class InschrijvingGeboorteAntwoordBericht extends AbstractBijhoudingAntwoordBericht {

    public InschrijvingGeboorteAntwoordBericht() {
        super(SoortBericht.A_F_S_REGISTREER_GEBOORTE_B_R);
    }

    public boolean isInschrijvingDoorGeboorte() {
        return getAdministratieveHandeling() != null
          && SoortAdministratieveHandeling.INSCHRIJVING_DOOR_GEBOORTE == getAdministratieveHandeling().getSoort();
    }

    public boolean isInschrijvingDoorGeboorteMetErkenning() {
        return getAdministratieveHandeling() != null
          && SoortAdministratieveHandeling.INSCHRIJVING_DOOR_GEBOORTE_MET_ERKENNING
                == getAdministratieveHandeling().getSoort();

    }

}
