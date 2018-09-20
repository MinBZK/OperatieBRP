/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

/**
 * Representeert expressies van numerieke operatoren.
 */
public abstract class AbstractNumericOperatorExpressie extends AbstractBinaryOperatorExpressie {

    /**
     * Constructor.
     *
     * @param termLinks  Linkerterm van de operator.
     * @param termRechts Rechterterm van de operator.
     */
    AbstractNumericOperatorExpressie(final Expressie termLinks, final Expressie termRechts) {
        super(termLinks, termRechts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ExpressieType getType() {
        return ExpressieType.NUMBER;
    }

    /**
     * Pas de operator toe op de twee numerieke waarden.
     *
     * @param waardeLinks  De linkerwaarde.
     * @param waardeRechts De rechterwaarde.
     * @return Resultaat van de berekening.
     */
    protected abstract int calculate(final int waardeLinks, final int waardeRechts);

    @Override
    public final Expressie bereken(final Expressie gereduceerdeTermLinks, final Expressie gereduceerdeTermRechts) {
        if (gereduceerdeTermLinks.isConstanteWaarde() && gereduceerdeTermLinks.getType() == ExpressieType.NUMBER
                && gereduceerdeTermRechts.isConstanteWaarde() && gereduceerdeTermRechts.getType() == ExpressieType
                .NUMBER)
        {
            int v1 = ((NumberLiteralExpressie) gereduceerdeTermLinks).getValue();
            int v2 = ((NumberLiteralExpressie) gereduceerdeTermRechts).getValue();
            return new NumberLiteralExpressie(calculate(v1, v2));
        } else {
            return this;
        }
    }
}
