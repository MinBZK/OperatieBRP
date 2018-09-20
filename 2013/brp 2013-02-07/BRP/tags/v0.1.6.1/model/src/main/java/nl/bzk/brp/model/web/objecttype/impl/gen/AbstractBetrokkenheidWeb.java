/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.web.objecttype.impl.gen;

import nl.bzk.brp.model.objecttype.interfaces.gen.BetrokkenheidBasis;
import nl.bzk.brp.model.objecttype.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.web.AbstractObjectTypeWeb;
import nl.bzk.brp.model.web.groep.impl.usr.BetrokkenheidOuderlijkGezagGroepWeb;
import nl.bzk.brp.model.web.groep.impl.usr.BetrokkenheidOuderschapGroepWeb;
import nl.bzk.brp.model.web.objecttype.impl.usr.PersoonWeb;
import nl.bzk.brp.model.web.objecttype.impl.usr.RelatieWeb;

/**
 * Implementatie voor objecttype betrokkenheid.
 */
@SuppressWarnings("serial")
public abstract class AbstractBetrokkenheidWeb extends AbstractObjectTypeWeb implements BetrokkenheidBasis {
    private SoortBetrokkenheid rol;
    private RelatieWeb relatie;
    private PersoonWeb betrokkene;
    private BetrokkenheidOuderlijkGezagGroepWeb betrokkenheidOuderlijkGezag;
    private BetrokkenheidOuderschapGroepWeb betrokkenheidOuderschap;

    @Override
    public SoortBetrokkenheid getRol() {
        return rol;
    }

    @Override
    public RelatieWeb getRelatie() {
        return relatie;
    }

    @Override
    public PersoonWeb getBetrokkene() {
        return betrokkene;
    }

    @Override
    public BetrokkenheidOuderlijkGezagGroepWeb getBetrokkenheidOuderlijkGezag() {
        return betrokkenheidOuderlijkGezag;
    }

    @Override
    public BetrokkenheidOuderschapGroepWeb getBetrokkenheidOuderschap() {
        return betrokkenheidOuderschap;
    }
}
