/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

/**
 * Representeert expressies van vergelijkingsoperatoren.
 */
public abstract class AbstractComparisonOperatorExpressie extends AbstractBinaryOperatorExpressie {

    /**
     * Constructor.
     *
     * @param termLinks  Linkerterm van de operator.
     * @param termRechts Rechterterm van de operator.
     */
    AbstractComparisonOperatorExpressie(final Expressie termLinks, final Expressie termRechts) {
        super(termLinks, termRechts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ExpressieType getType() {
        return ExpressieType.BOOLEAN;
    }

    /**
     * Pas de operator toe op de twee numeriek waarden.
     *
     * @param waardeLinks  De linkerwaarde.
     * @param waardeRechts De rechterwaarde.
     * @return Resultaat van de berekening.
     */
    protected abstract boolean calculate(final int waardeLinks, final int waardeRechts);

    /**
     * Pas de operator toe op de twee boolean waarden.
     *
     * @param waardeLinks  De linkerwaarde.
     * @param waardeRechts De rechterwaarde.
     * @return Resultaat van de berekening.
     */
    protected abstract boolean calculate(final boolean waardeLinks, final boolean waardeRechts);

    /**
     * Pas de operator toe op de twee string-waarden.
     *
     * @param waardeLinks  De linkerwaarde.
     * @param waardeRechts De rechterwaarde.
     * @return Resultaat van de berekening.
     */
    protected abstract boolean calculate(final String waardeLinks, final String waardeRechts);

    /**
     * Pas de operator toe op de twee datumwaarden.
     *
     * @param waardeLinks  De linkerwaarde.
     * @param waardeRechts De rechterwaarde.
     * @return Resultaat van de berekening.
     */
    protected abstract boolean calculate(final DateLiteralExpressie waardeLinks,
                                         final DateLiteralExpressie waardeRechts);

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie bereken(final Expressie gereduceerdeTermLinks, final Expressie gereduceerdeTermRechts) {
        Expressie result;
        if (!gereduceerdeTermLinks.isConstanteWaarde() || !gereduceerdeTermRechts.isConstanteWaarde()
                || gereduceerdeTermLinks.getType() != gereduceerdeTermRechts.getType())
        {
            result = create(gereduceerdeTermLinks, gereduceerdeTermRechts);
        } else if (gereduceerdeTermLinks.getType() == ExpressieType.NUMBER) {
            int v1 = ((NumberLiteralExpressie) gereduceerdeTermLinks).getValue();
            int v2 = ((NumberLiteralExpressie) gereduceerdeTermRechts).getValue();
            result = new BooleanLiteralExpressie(calculate(v1, v2));
        } else if (gereduceerdeTermLinks.getType() == ExpressieType.BOOLEAN) {
            boolean v1 = ((BooleanLiteralExpressie) gereduceerdeTermLinks).getValue();
            boolean v2 = ((BooleanLiteralExpressie) gereduceerdeTermRechts).getValue();
            result = new BooleanLiteralExpressie(calculate(v1, v2));
        } else if (gereduceerdeTermLinks.getType() == ExpressieType.STRING) {
            String v1 = ((StringLiteralExpressie) gereduceerdeTermLinks).getValue();
            String v2 = ((StringLiteralExpressie) gereduceerdeTermRechts).getValue();
            result = new BooleanLiteralExpressie(calculate(v1, v2));
        } else if (gereduceerdeTermLinks.getType() == ExpressieType.DATE) {
            DateLiteralExpressie v1 = ((DateLiteralExpressie) gereduceerdeTermLinks);
            DateLiteralExpressie v2 = ((DateLiteralExpressie) gereduceerdeTermRechts);
            result = new BooleanLiteralExpressie(calculate(v1, v2));
        } else {
            result = this;
        }
        return result;
    }

    @Override
    public final Expressie optimaliseer() {
        return create(getTermLinks().optimaliseer(), getTermRechts().optimaliseer());
    }
}
