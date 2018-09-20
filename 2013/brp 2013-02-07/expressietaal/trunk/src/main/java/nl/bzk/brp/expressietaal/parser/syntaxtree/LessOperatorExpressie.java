/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

/**
 * Representeert een kleiner-dan-expressie.
 */
public class LessOperatorExpressie extends AbstractComparisonOperatorExpressie {

    /**
     * Constructor.
     *
     * @param termLinks  Linkerterm van de operator.
     * @param termRechts Rechterterm van de operator.
     */
    public LessOperatorExpressie(final Expressie termLinks, final Expressie termRechts) {
        super(termLinks, termRechts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final String operatorAsString() {
        return "<";
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
        return waardeLinks < waardeRechts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final boolean calculate(final boolean waardeLinks, final boolean waardeRechts) {
        return !waardeLinks && waardeRechts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final boolean calculate(final String waardeLinks, final String waardeRechts) {
        return waardeLinks.compareTo(waardeRechts) < 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final boolean calculate(final DateLiteralExpressie waardeLinks,
                                      final DateLiteralExpressie waardeRechts)
    {
        boolean result = false;
        if (waardeLinks.getJaar() < waardeRechts.getJaar()) {
            result = true;
        } else if (waardeLinks.getJaar() == waardeRechts.getJaar()) {
            if (waardeLinks.getMaand() < waardeRechts.getMaand()) {
                result = true;
            } else if (waardeLinks.getMaand() == waardeRechts.getMaand()) {
                result = waardeLinks.getDag() < waardeRechts.getDag();
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final Expressie create(final Expressie operatorTermLinks, final Expressie operatorTermRechts) {
        return new LessOperatorExpressie(operatorTermLinks, operatorTermRechts);
    }
}
