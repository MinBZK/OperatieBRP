/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.objecttype.impl.gen;

import nl.bzk.brp.model.groep.interfaces.usr.BetrokkenheidOuderlijkGezagGroep;
import nl.bzk.brp.model.groep.interfaces.usr.BetrokkenheidOuderschapGroep;
import nl.bzk.brp.model.objecttype.interfaces.gen.BetrokkenheidBasis;
import nl.bzk.brp.model.objecttype.interfaces.usr.Persoon;
import nl.bzk.brp.model.objecttype.interfaces.usr.Relatie;
import nl.bzk.brp.model.objecttype.statisch.SoortBetrokkenheid;
import nl.bzk.brp.web.AbstractObjectTypeWeb;

/**
 * Implementatie voor objecttype betrokkenheid.
 */
public abstract class AbstractBetrokkenheidWeb extends AbstractObjectTypeWeb implements BetrokkenheidBasis {
    private SoortBetrokkenheid rol;
    private Relatie relatie;
    private Persoon betrokkene;
    private BetrokkenheidOuderlijkGezagGroep betrokkenheidOuderlijkGezag;
    private BetrokkenheidOuderschapGroep betrokkenheidOuderschap;

    @Override
    public SoortBetrokkenheid getRol() {
        return rol;
    }

    @Override
    public Relatie getRelatie() {
        return relatie;
    }

    @Override
    public Persoon getBetrokkene() {
        return betrokkene;
    }

    @Override
    public BetrokkenheidOuderlijkGezagGroep getBetrokkenheidOuderlijkGezag() {
        return betrokkenheidOuderlijkGezag;
    }

    @Override
    public BetrokkenheidOuderschapGroep getBetrokkenheidOuderschap() {
        return betrokkenheidOuderschap;
    }
}
