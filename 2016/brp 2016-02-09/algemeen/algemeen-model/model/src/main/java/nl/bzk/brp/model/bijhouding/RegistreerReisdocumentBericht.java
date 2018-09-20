/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bijhouding;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;


/**
 * Bijhouding bericht voor registratie reisdocument.
 */
public final class RegistreerReisdocumentBericht extends BijhoudingsBericht {

    /**
     * Standaard constructor die de soort van het bericht zet.
     */
    public RegistreerReisdocumentBericht() {
        super(SoortBericht.BHG_RSD_REGISTREER_REISDOCUMENT);
    }
}
