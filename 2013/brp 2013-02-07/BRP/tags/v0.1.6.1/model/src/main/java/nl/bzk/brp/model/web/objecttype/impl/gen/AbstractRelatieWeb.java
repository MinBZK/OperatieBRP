/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.web.objecttype.impl.gen;

import java.util.Set;

import nl.bzk.brp.model.objecttype.interfaces.gen.RelatieBasis;
import nl.bzk.brp.model.objecttype.statisch.SoortRelatie;
import nl.bzk.brp.model.web.AbstractObjectTypeWeb;
import nl.bzk.brp.model.web.groep.impl.usr.RelatieStandaardGroepWeb;
import nl.bzk.brp.model.web.objecttype.impl.usr.BetrokkenheidWeb;

/**
 * Implementatie voor objecttyp relatie.
 */
@SuppressWarnings("serial")
public abstract class AbstractRelatieWeb extends AbstractObjectTypeWeb implements RelatieBasis {

    private SoortRelatie soort;
    private RelatieStandaardGroepWeb gegevens;
    private Set<? extends BetrokkenheidWeb> betrokkenheden;

    @Override
    public SoortRelatie getSoort() {
        return soort;
    }

    @Override
    public RelatieStandaardGroepWeb getGegevens() {
        return gegevens;
    }
    @Override
    public Set<? extends BetrokkenheidWeb> getBetrokkenheden() {
        return betrokkenheden;
    }
}
