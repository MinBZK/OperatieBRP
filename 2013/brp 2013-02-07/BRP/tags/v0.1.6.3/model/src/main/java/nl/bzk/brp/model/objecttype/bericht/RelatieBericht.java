/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.bericht;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.objecttype.bericht.basis.AbstractRelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;


/**
 *
 */
@SuppressWarnings("serial")
public class RelatieBericht extends AbstractRelatieBericht implements Relatie, RootObject {

    @Override
    public Set<BetrokkenheidBericht> getOuderBetrokkenheden() {
        final Set<BetrokkenheidBericht> ouderBetr = new HashSet<BetrokkenheidBericht>();
        if (getBetrokkenheden() != null) {
            for (BetrokkenheidBericht betrokkenheidBericht : getBetrokkenheden()) {
                if (SoortBetrokkenheid.OUDER == betrokkenheidBericht.getRol()) {
                    ouderBetr.add(betrokkenheidBericht);
                }
            }
        }
        return ouderBetr;
    }

    @Override
    public BetrokkenheidBericht getKindBetrokkenheid() {
        if (getBetrokkenheden() != null) {
            for (BetrokkenheidBericht betrokkenheidBericht : getBetrokkenheden()) {
                if (SoortBetrokkenheid.KIND == betrokkenheidBericht.getRol()) {
                    return betrokkenheidBericht;
                }
            }
        }
        return null;
    }
}
