/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.operatoren;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.DefaultKeywordMapping;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.Keyword;
import nl.bzk.brp.expressietaal.expressies.literals.BooleanLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;

/**
 * Representeert een logische-of-expressie.
 */
public class LogischeOfExpressie extends AbstractBooleanOperatorExpressie {

    /**
     * Constructor.
     *
     * @param operandLinks  De linker operand van de boolean operator.
     * @param operandRechts De rechter operand van de boolean operator.
     */
    public LogischeOfExpressie(final Expressie operandLinks, final Expressie operandRechts)
    {
        super(operandLinks, operandRechts);
    }

    @Override
    public final int getPrioriteit() {
        return PRIORITEIT_LOGISCHE_OF;
    }

    @Override
    public final Expressie optimaliseer(final Context context) {
        final Expressie geoptimaliseerdeOperandLinks = getOperandLinks().optimaliseer(context);
        final Expressie geoptimaliseerdeOperandRechts = getOperandRechts().optimaliseer(context);
        final Expressie resultaat;

        if (geoptimaliseerdeOperandLinks.isConstanteWaarde(ExpressieType.BOOLEAN, context)) {
            if (geoptimaliseerdeOperandLinks.alsBoolean()) {
                resultaat = BooleanLiteralExpressie.WAAR;
            } else {
                resultaat = geoptimaliseerdeOperandRechts;
            }
        } else if (geoptimaliseerdeOperandRechts.isConstanteWaarde(ExpressieType.BOOLEAN, context)) {
            if (geoptimaliseerdeOperandRechts.alsBoolean()) {
                resultaat = BooleanLiteralExpressie.WAAR;
            } else {
                resultaat = geoptimaliseerdeOperandLinks;
            }
        } else if (geoptimaliseerdeOperandLinks.isNull(context) || geoptimaliseerdeOperandRechts.isNull(context)) {
            resultaat = NullValue.getInstance();
        } else if (geoptimaliseerdeOperandLinks.isConstanteWaarde(ExpressieType.BOOLEAN, context)
            && geoptimaliseerdeOperandRechts.isConstanteWaarde(ExpressieType.BOOLEAN, context))
        {
            resultaat = pasOperatorToe(geoptimaliseerdeOperandLinks, geoptimaliseerdeOperandRechts, context);
        } else {
            resultaat = new LogischeOfExpressie(geoptimaliseerdeOperandLinks, geoptimaliseerdeOperandRechts);
        }
        return resultaat;
    }

    @Override
    protected final String operatorAlsString() {
        return DefaultKeywordMapping.getSyntax(Keyword.BOOLEAN_OR);
    }

    @Override
    protected final Expressie pasOperatorToe(final Expressie berekendeOperandLinks,
        final Expressie berekendeOperandRechts,
        final Context context)
    {
        final Expressie resultaat;
        if (berekendeOperandLinks.isFout()) {
            resultaat = berekendeOperandLinks;
        } else if (berekendeOperandLinks.isNull(context)) {
            if (berekendeOperandRechts.alsBoolean()) {
                resultaat = BooleanLiteralExpressie.WAAR;
            } else {
                resultaat = NullValue.getInstance();
            }
        } else if (berekendeOperandLinks.isConstanteWaarde(ExpressieType.BOOLEAN, context)) {
            if (berekendeOperandLinks.alsBoolean()) {
                resultaat = BooleanLiteralExpressie.WAAR;
            } else {
                resultaat = berekendeOperandRechts;
            }
        } else {
            resultaat = NullValue.getInstance();
        }
        return resultaat;
    }
}
