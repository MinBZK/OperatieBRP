/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bevraging.bijhouding;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.bevraging.BevragingAntwoordBericht;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;


/**
 * Het antwoord bericht voor bevragingen.
 */
public class GeefDetailsPersoonAntwoordBericht extends BevragingAntwoordBericht {

    /**
     * Standaard constructor (die direct de soort van het bericht initialiseert/zet).
     */
    public GeefDetailsPersoonAntwoordBericht() {
        super(SoortBericht.BHG_BVG_GEEF_DETAILS_PERSOON_R);
    }

    /**
     * Vraagt de gevonden persoon op.
     *
     * @return Gevonden persoon indien er een persoon gevonden is.
     */
    public final PersoonHisVolledigView getGevondenPersoon() {
        if (getGevondenPersonen() != null && !getGevondenPersonen().isEmpty()) {
            return getGevondenPersonen().iterator().next();
        }
        return null;
    }

}
