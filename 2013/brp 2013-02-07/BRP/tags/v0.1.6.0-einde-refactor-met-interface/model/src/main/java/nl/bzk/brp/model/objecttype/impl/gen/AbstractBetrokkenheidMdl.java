/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.impl.gen;

import nl.bzk.brp.model.attribuuttype.TechnischIdMiddel;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.groep.interfaces.usr.BetrokkenheidOuderlijkGezagGroep;
import nl.bzk.brp.model.groep.interfaces.usr.BetrokkenheidOuderschapGroep;
import nl.bzk.brp.model.objecttype.interfaces.gen.BetrokkenheidBasis;
import nl.bzk.brp.model.objecttype.interfaces.usr.Persoon;
import nl.bzk.brp.model.objecttype.interfaces.usr.Relatie;
import nl.bzk.brp.model.objecttype.statisch.SoortBetrokkenheid;

/**
 * Implementatie voor objecttype betrokkenheid.
 */
public abstract class AbstractBetrokkenheidMdl extends AbstractDynamischObjectType implements BetrokkenheidBasis {

    private TechnischIdMiddel iD;
    private SoortBetrokkenheid rol;
    private Relatie relatie;
    private Persoon betrokkene;
    private BetrokkenheidOuderlijkGezagGroep betrokkenheidOuderlijkGezag;
    private BetrokkenheidOuderschapGroep betrokkenheidOuderschap;

    public TechnischIdMiddel getID() {
        return iD;
    }

    public SoortBetrokkenheid getRol() {
        return rol;
    }

    public Relatie getRelatie() {
        return relatie;
    }

    public Persoon getBetrokkene() {
        return betrokkene;
    }

    public BetrokkenheidOuderlijkGezagGroep getBetrokkenheidOuderlijkGezag() {
        return betrokkenheidOuderlijkGezag;
    }

    public BetrokkenheidOuderschapGroep getBetrokkenheidOuderschap() {
        return betrokkenheidOuderschap;
    }
}
