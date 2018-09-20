/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

/**
 * Representeert een ongelijkheidsexpressie.
 */
public class InequalityOperatorExpressie extends AbstractComparisonOperatorExpressie {

    /**
     * Constructor.
     *
     * @param termLinks  Linkerterm van de operator.
     * @param termRechts Rechterterm van de operator.
     */
    public InequalityOperatorExpressie(final Expressie termLinks, final Expressie termRechts) {
        super(termLinks, termRechts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final String operatorAsString() {
        return "<>";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String operatorAsFormalString() {
        return operatorAsString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final boolean calculate(final int waardeLinks, final int waardeRechts) {
        return waardeLinks != waardeRechts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final boolean calculate(final boolean waardeLinks, final boolean waardeRechts) {
        return waardeLinks != waardeRechts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final boolean calculate(final String waardeLinks, final String waardeRechts) {
        return !waardeLinks.equals(waardeRechts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final boolean calculate(final DateLiteralExpressie waardeLinks,
                                      final DateLiteralExpressie waardeRechts)
    {
        return !(waardeLinks.getJaar() == waardeRechts.getJaar()
                && waardeLinks.getMaand() == waardeRechts.getMaand()
                && waardeLinks.getDag() == waardeRechts.getDag());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final Expressie create(final Expressie operatorTermLinks, final Expressie operatorTermRechts) {
        return new InequalityOperatorExpressie(operatorTermLinks, operatorTermRechts);
    }
}
