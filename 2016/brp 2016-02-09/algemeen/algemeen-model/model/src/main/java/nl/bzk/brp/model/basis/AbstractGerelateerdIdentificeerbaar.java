/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;

/**
 *
 */
public abstract class AbstractGerelateerdIdentificeerbaar implements GerelateerdIdentificeerbaar {

    @Transient
    private ElementEnum gerelateerdeObjectType;

    @Transient
    private BetrokkenheidHisVolledigView betrokkenPersoonBetrokkenheidView;

    public final ElementEnum getGerelateerdeObjectType() {
        return gerelateerdeObjectType;
    }

    public final void setGerelateerdeObjectType(final ElementEnum gerelateerdeObjectType) {
        this.gerelateerdeObjectType = gerelateerdeObjectType;
    }

    public final void setBetrokkenPersoonBetrokkenheidView(final BetrokkenheidHisVolledigView betrokkenPersoonBetrokkenheidView) {
        this.betrokkenPersoonBetrokkenheidView = betrokkenPersoonBetrokkenheidView;
    }

    @Override
    public final BetrokkenheidHisVolledigView getBetrokkenPersoonBetrokkenheidView() {
        return betrokkenPersoonBetrokkenheidView;
    }
}
