/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.operatoren;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.AbstractNonLiteralExpressie;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Representeert operatorexpressies.
 */
public abstract class AbstractOperatorExpressie extends AbstractNonLiteralExpressie {

    @Override
    public final boolean isVariabele() {
        return false;
    }

    @Override
    public final Attribuut getAttribuut() {
        return null;
    }

    @Override
    public final Groep getGroep() {
        return null;
    }

    @Override
    public final ExpressieType bepaalTypeVanElementen(final Context context) {
        return getType(context);
    }

    @Override
    public final int aantalElementen() {
        return 1;
    }

    /**
     * Geeft de stringrepresentatie van de operator.
     *
     * @return De stringrepresentatie van de operator.
     */
    protected abstract String operatorAlsString();
}
