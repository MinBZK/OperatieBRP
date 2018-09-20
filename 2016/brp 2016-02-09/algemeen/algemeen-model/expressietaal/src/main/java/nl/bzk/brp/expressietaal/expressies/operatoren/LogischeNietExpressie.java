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
 * Representeert een logische-niet-expressie.
 */
public class LogischeNietExpressie extends AbstractUnaireOperatorExpressie {

    /**
     * Constructor.
     *
     * @param expressie De te inverteren expressie.
     */
    public LogischeNietExpressie(final Expressie expressie) {
        super(expressie);
    }

    @Override
    public final ExpressieType getType(final Context context) {
        return ExpressieType.BOOLEAN;
    }

    @Override
    public final int getPrioriteit() {
        return PRIORITEIT_LOGISCHE_NIET;
    }

    @Override
    public final Expressie optimaliseer(final Context context) {
        final Expressie result;
        final Expressie geoptimaliseerdeOperand = getOperand().optimaliseer(context);
        if (geoptimaliseerdeOperand.isNull(context)) {
            result = NullValue.getInstance();
        } else if (geoptimaliseerdeOperand.isConstanteWaarde(ExpressieType.BOOLEAN, context)) {
            result = BooleanLiteralExpressie.getExpressie(!geoptimaliseerdeOperand.alsBoolean());
        } else {
            result = this;
        }
        return result;
    }

    @Override
    protected final String operatorAlsString() {
        return DefaultKeywordMapping.getSyntax(Keyword.BOOLEAN_NOT) + " ";
    }

    @Override
    protected final Expressie pasOperatorToe(final Expressie berekendeOperand, final Context context) {
        final Expressie resultaat;
        if (berekendeOperand.isConstanteWaarde(ExpressieType.BOOLEAN, context)) {
            resultaat = BooleanLiteralExpressie.getExpressie(!berekendeOperand.alsBoolean());
        } else {
            resultaat = NullValue.getInstance();
            // resultaat = new LogischeNietExpressie(berekendeOperand);
        }
        return resultaat;
    }
}

