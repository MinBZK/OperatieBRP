/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

/**
 * Representeert boolean operatorexpressies.
 */
public abstract class AbstractBooleanOperatorExpressie extends AbstractBinaryOperatorExpressie {

    /**
     * Constructor.
     *
     * @param expressieLinks  De linkerterm van de boolean-operatorexpressie.
     * @param expressieRechts De rechterterm van de boolean-operatorexpressie.
     */
    AbstractBooleanOperatorExpressie(final Expressie expressieLinks, final Expressie expressieRechts) {
        super(expressieLinks, expressieRechts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ExpressieType getType() {
        return ExpressieType.BOOLEAN;
    }

    /**
     * Pas de operator toe op de twee boolean waarden.
     *
     * @param waardeLinks  De linkerwaarde.
     * @param waardeRechts De rechterwaarde.
     * @return Resultaat van de berekening.
     */
    protected abstract boolean calculate(final boolean waardeLinks, final boolean waardeRechts);

    @Override
    public final Expressie bereken(final Expressie gereduceerdeTermLinks, final Expressie gereduceerdeTermRechts) {
        if (gereduceerdeTermLinks.isConstanteWaarde() && gereduceerdeTermLinks.getType() == ExpressieType.BOOLEAN
                && gereduceerdeTermRechts.isConstanteWaarde() && gereduceerdeTermRechts.getType() == ExpressieType
                .BOOLEAN)
        {
            boolean v1 = ((BooleanLiteralExpressie) gereduceerdeTermLinks).getValue();
            boolean v2 = ((BooleanLiteralExpressie) gereduceerdeTermRechts).getValue();
            return new BooleanLiteralExpressie(calculate(v1, v2));
        } else {
            return this;
        }
    }
}
