/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.bericht.kern.basis.AbstractNaamgeverBericht;
import nl.bzk.brp.model.logisch.kern.Naamgever;


/**
 * De betrokkenheid in de rol van Naamgever.
 *
 *
 *
 */
public class NaamgeverBericht extends AbstractNaamgeverBericht implements Naamgever {

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     */
    public NaamgeverBericht() {
        super();
    }

}
