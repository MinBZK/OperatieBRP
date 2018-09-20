/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.impl.gen;

import java.util.Set;

import nl.bzk.brp.model.attribuuttype.TechnischIdMiddel;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.groep.interfaces.usr.RelatieStandaardGroep;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.interfaces.gen.RelatieBasis;
import nl.bzk.brp.model.objecttype.statisch.SoortRelatie;

/**
 * Implementatie voor objecttyp relatie.
 */
public abstract class AbstractRelatieMdl extends AbstractDynamischObjectType implements RelatieBasis {

    private TechnischIdMiddel iD;
    private SoortRelatie soort;
    private RelatieStandaardGroep gegevens;
    private Set<? extends Betrokkenheid> betrokkenheden;

    public TechnischIdMiddel getID() {
        return iD;
    }

    public SoortRelatie getSoort() {
        return soort;
    }

    public RelatieStandaardGroep getGegevens() {
        return gegevens;
    }

    @Override
    public Set<? extends Betrokkenheid> getBetrokkenheden() {
        return this.betrokkenheden;
    }
}
