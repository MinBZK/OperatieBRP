/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.synchronisatie;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.bericht.ber.AntwoordBericht;


/**
 * Het antwoordbericht voor synchronisatie persoon.
 */
public class GeefSynchronisatiePersoonAntwoordBericht extends AntwoordBericht {

    /**
     * Standaard constructor (die direct de soort van het bericht initialiseert/zet).
     */
    public GeefSynchronisatiePersoonAntwoordBericht() {
        super(SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_PERSOON_R);
    }
}
