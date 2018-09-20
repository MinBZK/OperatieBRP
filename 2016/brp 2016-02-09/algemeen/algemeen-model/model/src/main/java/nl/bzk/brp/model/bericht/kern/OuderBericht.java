/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.logisch.kern.Ouder;


/**
 * De betrokkenheid van een persoon in de rol van ouder in een familierechtelijke betrekking.
 */
public final class OuderBericht extends AbstractOuderBericht implements Ouder {

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     */
    public OuderBericht() {
        super();
    }

}
