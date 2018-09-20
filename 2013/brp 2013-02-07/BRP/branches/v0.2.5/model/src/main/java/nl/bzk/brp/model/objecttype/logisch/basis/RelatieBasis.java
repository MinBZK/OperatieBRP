/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.logisch.basis;

import java.util.Collection;

import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.groep.logisch.RelatieStandaardGroep;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;

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
     * Retourneert een collectie van objecten die betrokkenheid implementeren.
     * Let op: in het bericht is het een lijst, in het model een set.
     * @return de collectie
     */
    Collection<? extends Betrokkenheid> getBetrokkenheden();

}
