/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.functies;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;

/**
 * Representeert de functie KINDEREN(P). De functie geeft de kinderen van persoon P in een lijst terug.
 */
public final class FunctieKINDEREN extends AbstractGerelateerdenFunctie {
    /**
     * constructor.
     */
    public FunctieKINDEREN() {
        super(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, SoortBetrokkenheid.OUDER, SoortBetrokkenheid.KIND);
    }
}
