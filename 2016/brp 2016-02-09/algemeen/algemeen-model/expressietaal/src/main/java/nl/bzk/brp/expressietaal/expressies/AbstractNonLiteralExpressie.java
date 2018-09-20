/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies;

import nl.bzk.brp.expressietaal.AbstractExpressie;
import nl.bzk.brp.expressietaal.Expressie;

/**
 * Representeert niet-literal (niet-constante) expressies.
 */
public abstract class AbstractNonLiteralExpressie extends AbstractExpressie {

    @Override
    public final Iterable<Expressie> getElementen() {
        return maakLijstMetExpressie();
    }

    @Override
    public final Expressie getElement(final int index) {
        return getElementUitNietLijstExpressie(index);
    }

    @Override
    public final boolean isConstanteWaarde() {
        return false;
    }

    @Override
    public final boolean alsBoolean() {
        // Default boolean-waarde voor een expressie is FALSE.
        return false;
    }

    @Override
    public final int alsInteger() {
        // Default integer-waarde voor een expressie is 0.
        return 0;
    }

    @Override
    public final long alsLong() {
        return 0L;
    }

    @Override
    public final String alsString() {
        return stringRepresentatie();
    }
}
