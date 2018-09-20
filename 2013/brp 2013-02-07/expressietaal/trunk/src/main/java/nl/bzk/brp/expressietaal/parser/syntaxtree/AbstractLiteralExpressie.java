/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import nl.bzk.brp.expressietaal.symbols.Attributes;

/**
 * Representeert literal (constante) expressies.
 */
public abstract class AbstractLiteralExpressie extends AbstractExpressie {
    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isConstanteWaarde() {
        return true;
    }

    @Override
    public final boolean isVariabele() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isAttribuut() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isLijstExpressie() {
        return false;
    }

    @Override
    public final ExpressieType getTypeElementen() {
        return ExpressieType.UNKNOWN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie optimaliseer() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final EvaluatieResultaat evalueer(final Context context) {
        return new EvaluatieResultaat(this);
    }

    @Override
    public final boolean includes(final Attributes attribuut) {
        return false;
    }
}
