/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import nl.bzk.brp.expressietaal.symbols.DefaultKeywordMapping;
import nl.bzk.brp.expressietaal.symbols.Keywords;

/**
 * Representeert een expressie die controleert op de aanwezigheid van een bepaald element
 * in een lijst.
 */
public class ListElementExpressie extends AbstractBinaryOperatorExpressie {

    /**
     * Constructor.
     *
     * @param termLinks  Linkerterm van de operator: het te zoeken element.
     * @param termRechts Rechterterm van de operator: de te doorzoeken lijst.
     */
    public ListElementExpressie(final Expressie termLinks, final ListExpressie termRechts) {
        super(termLinks, termRechts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie bereken(final Expressie gereduceerdeTermLinks, final Expressie gereduceerdeTermRechts) {
        Expressie result = null;
        if (gereduceerdeTermLinks.isConstanteWaarde() && gereduceerdeTermRechts.isLijstExpressie()) {
            Expressie simpleExp = null;
            ListExpressie list = (ListExpressie) gereduceerdeTermRechts;
            for (Expressie e : list.getElements()) {
                if (simpleExp == null) {
                    simpleExp = new EqualityOperatorExpressie(gereduceerdeTermLinks, e);
                } else {
                    simpleExp = new BooleanOROperatorExpressie(new EqualityOperatorExpressie(gereduceerdeTermLinks, e),
                            simpleExp);
                }
            }
            if (simpleExp != null) {
                EvaluatieResultaat evaluatieResultaat = simpleExp.evalueer(null);
                if (evaluatieResultaat.isBooleanWaarde()) {
                    result = new BooleanLiteralExpressie(evaluatieResultaat.getBooleanWaarde());
                }
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie optimaliseer() {
        return create(getTermLinks().optimaliseer(), getTermRechts().optimaliseer());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final Expressie create(final Expressie operatorTermLinks, final Expressie operatorTermRechts) {
        return new ListElementExpressie(operatorTermLinks, (ListExpressie) operatorTermRechts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String operatorAsString() {
        return DefaultKeywordMapping.getSyntax(Keywords.IN);
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
    public final ExpressieType getType() {
        return ExpressieType.BOOLEAN;
    }
}
