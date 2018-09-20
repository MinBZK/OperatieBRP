/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

/**
 * Representeert numerieke delingsexpressie.
 */
public class DivideOperatorExpressie extends AbstractNumericOperatorExpressie {

    /**
     * Constructor.
     *
     * @param termLinks  Linkerterm van de operator.
     * @param termRechts Rechterterm van de operator.
     */
    public DivideOperatorExpressie(final Expressie termLinks, final Expressie termRechts) {
        super(termLinks, termRechts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final String operatorAsString() {
        return "/";
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
    protected final Expressie create(final Expressie operatorTermLinks, final Expressie operatorTermRechts) {
        return new DivideOperatorExpressie(operatorTermLinks, operatorTermRechts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final int calculate(final int waardeLinks, final int waardeRechts) {
        if (waardeRechts == 0) {
            return 0;
        } else {
            return waardeLinks / waardeRechts;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie optimaliseer() {
        Expressie gereduceerdeTermLinks = getTermLinks().optimaliseer();
        Expressie gereduceerdeTermRechts = getTermRechts().optimaliseer();
        Expressie exp = null;
        if (gereduceerdeTermLinks.isConstanteWaarde() && gereduceerdeTermLinks.getType() == ExpressieType.NUMBER) {
            if (((NumberLiteralExpressie) gereduceerdeTermLinks).getValue() == 0) {
                exp = new NumberLiteralExpressie(0);
            }
        } else if (gereduceerdeTermRechts.isConstanteWaarde() && gereduceerdeTermRechts.getType()
                == ExpressieType.NUMBER
                && ((NumberLiteralExpressie) gereduceerdeTermRechts).getValue() == 1)
        {
            exp = gereduceerdeTermLinks;
        }

        if (exp == null) {
            return create(gereduceerdeTermLinks, gereduceerdeTermRechts);
        } else {
            return exp;
        }
    }
}
