/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import nl.bzk.brp.expressietaal.lexical.tokens.TokenSubtype;

/**
 * Factory class voor expressies met operatoren.
 */
public abstract class OperatorExpressieFactory {

    /**
     * Constructor.
     */
    private OperatorExpressieFactory() {
    }

    /**
     * Maakt een Expressie-object behorend bij de opgegeven vergelijkingsoperator met twee termen.
     *
     * @param operator   Type van de operator.
     * @param termLinks  Linkerterm van de vergelijking.
     * @param termRechts Rechterterm van de vergelijking.
     * @return Expressie-object.
     */
    private static Expressie getComparisonOperatorExpressie(final TokenSubtype operator, final Expressie termLinks,
                                                            final Expressie termRechts)
    {
        Expressie result;
        switch (operator) {
            case LESS:
                result = new LessOperatorExpressie(termLinks, termRechts);
                break;
            case GREATER:
                result = new GreaterOperatorExpressie(termLinks, termRechts);
                break;
            case LESS_OR_EQUAL:
                result = new LessOrEqualOperatorExpressie(termLinks, termRechts);
                break;
            case GREATER_OR_EQUAL:
                result = new GreaterOrEqualOperatorExpressie(termLinks, termRechts);
                break;
            case EQUAL:
                result = new EqualityOperatorExpressie(termLinks, termRechts);
                break;
            case NOT_EQUAL:
                result = new InequalityOperatorExpressie(termLinks, termRechts);
                break;
            default:
                result = null;
                break;
        }
        return result;
    }

    /**
     * Maakt een Expressie-object behorend bij de opgegeven rekenoperator met twee termen.
     *
     * @param operator   Type van de operator.
     * @param termLinks  Linkerterm van de berekening.
     * @param termRechts Rechterterm van de berekening.
     * @return Expressie-object.
     */
    private static Expressie getNumeriekeOperatorExpressie(final TokenSubtype operator, final Expressie termLinks,
                                                           final Expressie termRechts)
    {
        Expressie result;
        switch (operator) {
            case PLUS:
                result = new PlusOperatorExpressie(termLinks, termRechts);
                break;
            case MINUS:
                result = new MinusOperatorExpressie(termLinks, termRechts);
                break;
            case MULTIPLY:
                result = new MultiplyOperatorExpressie(termLinks, termRechts);
                break;
            case DIVIDE:
                result = new DivideOperatorExpressie(termLinks, termRechts);
                break;
            default:
                result = null;
                break;
        }
        return result;
    }

    /**
     * Maakt een Expressie-object behorend bij de opgegeven operator met twee termen.
     *
     * @param operator   Type van de operator.
     * @param termLinks  Linkerterm van de expressie.
     * @param termRechts Rechterterm van de expressie.
     * @return Expressie-object.
     */
    public static Expressie getOperatorExpressie(final TokenSubtype operator, final Expressie termLinks,
                                                 final Expressie termRechts)
    {
        Expressie result = getComparisonOperatorExpressie(operator, termLinks, termRechts);
        if (result == null) {
            result = getNumeriekeOperatorExpressie(operator, termLinks, termRechts);
        }
        return result;
    }
}
