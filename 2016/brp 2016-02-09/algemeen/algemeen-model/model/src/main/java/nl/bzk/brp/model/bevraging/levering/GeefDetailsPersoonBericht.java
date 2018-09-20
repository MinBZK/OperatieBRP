/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bevraging.levering;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.bevraging.BevragingsBericht;


/**
 * Bericht om persoon details op te vragen tbv levering.
 */
@SuppressWarnings("serial")
public final class GeefDetailsPersoonBericht extends BevragingsBericht {

    /**
     * Standaard constructor (die direct de soort van het bericht initialiseert/zet).
     */
    public GeefDetailsPersoonBericht() {
        super(SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON);
    }
}
