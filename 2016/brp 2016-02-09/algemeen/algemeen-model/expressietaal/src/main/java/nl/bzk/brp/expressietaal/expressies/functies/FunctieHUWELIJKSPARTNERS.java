/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.functies;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;

/**
 * Representeert de functie HUWELIJKSPARTNER(P). De functie geeft de huwelijkspartners van persoon P in een lijst terug.
 * (In de regel is dit 1 persoon.)
 */
public final class FunctieHUWELIJKSPARTNERS extends AbstractGerelateerdenFunctie {
    /**
     * constructor.
     */
    public FunctieHUWELIJKSPARTNERS() {
        super(SoortRelatie.HUWELIJK, SoortBetrokkenheid.PARTNER, SoortBetrokkenheid.PARTNER);
    }

}
