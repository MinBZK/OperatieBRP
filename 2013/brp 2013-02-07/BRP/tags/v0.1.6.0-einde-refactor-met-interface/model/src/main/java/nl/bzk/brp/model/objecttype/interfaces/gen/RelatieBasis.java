/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.interfaces.gen;

import java.util.Set;

import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.groep.interfaces.usr.RelatieStandaardGroep;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.statisch.SoortRelatie;

/**
 * Interface voor objecttyp relatie.
 */
public interface RelatieBasis extends ObjectType  {

    /**
     * Retourneert de soort relatie.
     * @return Soort relatie.
     */
    SoortRelatie getSoort();

    /**
     * Retourneert de standaard groep van dit objecttype.
     * @return Standaard groep relatie.
     */
    RelatieStandaardGroep getGegevens();

    /**
     * Retourneert een lijst van objecten die betrokkenheid implementeren.
     * @return de lijst
     */
    Set<? extends Betrokkenheid> getBetrokkenheden();

}
