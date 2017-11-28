/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.operator;

import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.Prioriteit;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.NullLiteral;

/**
 * Representeert een logische-niet-expressie.
 */
public final class LogischeInverseOperator extends AbstractUnaireOperator {

    /**
     * Constructor.
     *
     * @param expressie De te inverteren expressie.
     */
    public LogischeInverseOperator(final Expressie expressie) {
        super(expressie);
    }

    @Override
    protected Expressie pasOperatorToe(final Expressie berekendeOperand, final Context context) {
        return berekendeOperand.isConstanteWaarde(ExpressieType.BOOLEAN, context)
                ? BooleanLiteral.valueOf(!berekendeOperand.alsBoolean())
                : NullLiteral.INSTANCE;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.BOOLEAN;
    }

    @Override
    public Prioriteit getPrioriteit() {
        return Prioriteit.PRIORITEIT_LOGISCHE_NIET;
    }

    @Override
    public String operatorAlsString() {
        return "NIET ";
    }
}
