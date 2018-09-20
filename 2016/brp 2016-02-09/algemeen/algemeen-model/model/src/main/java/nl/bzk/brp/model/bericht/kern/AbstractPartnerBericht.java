/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.logisch.kern.PartnerBasis;

/**
 * De OT:Betrokkenheid van een OT:Persoon in de _rol_ van "_Partner_" in een OT:Huwelijk of
 * OT:"Geregistreerd partnerschap".
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPartnerBericht extends BetrokkenheidBericht implements BrpObject, PartnerBasis {

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     */
    public AbstractPartnerBericht() {
        super(new SoortBetrokkenheidAttribuut(SoortBetrokkenheid.PARTNER));
    }

}
