/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.bericht.basis;

import java.util.List;

import javax.validation.Valid;

import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.logisch.basis.RelatieBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;

/**
 * Implementatie voor objecttyp relatie.
 */
@SuppressWarnings("serial")
public abstract class AbstractRelatieBericht extends AbstractObjectTypeBericht implements RelatieBasis {

    private SoortRelatie soort;
    @Valid
    private RelatieStandaardGroepBericht gegevens;
    @Valid
    private List<BetrokkenheidBericht> betrokkenheden;

    @Override
    public SoortRelatie getSoort() {
        return soort;
    }

    public void setSoort(final SoortRelatie soort) {
        this.soort = soort;
    }

    @Override
    public RelatieStandaardGroepBericht getGegevens() {
        return gegevens;
    }

    public void setGegevens(final RelatieStandaardGroepBericht gegevens) {
        this.gegevens = gegevens;
    }

    @Override
    public List<BetrokkenheidBericht> getBetrokkenheden() {
        return betrokkenheden;
    }

    public void setBetrokkenheden(final List<BetrokkenheidBericht> betrokkenheden) {
        this.betrokkenheden = betrokkenheden;
    }

}
