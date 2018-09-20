/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import nl.bzk.brp.expressietaal.symbols.DefaultKeywordMapping;
import nl.bzk.brp.expressietaal.symbols.Keywords;

/**
 * Representeert een logische-of-expressie.
 */
public class BooleanOROperatorExpressie extends AbstractBooleanOperatorExpressie {

    /**
     * Constructor.
     *
     * @param termLinks  Linkerterm van de operator.
     * @param termRechts Rechterterm van de operator.
     */
    public BooleanOROperatorExpressie(final Expressie termLinks, final Expressie termRechts) {
        super(termLinks, termRechts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final String operatorAsString() {
        return DefaultKeywordMapping.getSyntax(Keywords.BOOLEAN_OR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String operatorAsFormalString() {
        return "." + operatorAsString() + ".";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final Expressie create(final Expressie operatorTermLinks, final Expressie operatorTermRechts) {
        return new BooleanOROperatorExpressie(operatorTermLinks, operatorTermRechts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final boolean calculate(final boolean waardeLinks, final boolean waardeRechts) {
        return waardeLinks || waardeRechts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie optimaliseer() {
        Expressie gereduceerdeTermLinks = getTermLinks().optimaliseer();
        Expressie gereduceerdeTermRechts = getTermRechts().optimaliseer();
        Expressie exp = null;
        if (gereduceerdeTermLinks.isConstanteWaarde() && gereduceerdeTermLinks.getType() == ExpressieType.BOOLEAN) {
            if (!((BooleanLiteralExpressie) gereduceerdeTermLinks).getValue()) {
                exp = gereduceerdeTermRechts;
            } else {
                exp = new BooleanLiteralExpressie(true);
            }
        } else if (gereduceerdeTermRechts.isConstanteWaarde() && gereduceerdeTermRechts.getType() == ExpressieType
                .BOOLEAN)
        {
            if (!((BooleanLiteralExpressie) gereduceerdeTermRechts).getValue()) {
                exp = gereduceerdeTermLinks;
            } else {
                exp = new BooleanLiteralExpressie(true);
            }
        }

        if (exp == null) {
            return create(gereduceerdeTermLinks, gereduceerdeTermRechts);
        } else {
            return exp;
        }
    }
}
