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
 * Representeert een logische-en-expressie.
 */
public final class EnOperator extends AbstractBinaireOperator {

    /**
     * Constructor.
     *
     * @param operandLinks  De linker operand van de boolean operator.
     * @param operandRechts De rechter operand van de boolean operator.
     */
    public EnOperator(final Expressie operandLinks, final Expressie operandRechts) {
        super(operandLinks, operandRechts);
    }

    @Override
    protected Expressie pasOperatorToe(final Expressie berekendeOperandLinks,
                                       final Expressie berekendeOperandRechts,
                                       final Context context) {
        final Expressie resultaat;
        if (berekendeOperandLinks.isNull()) {
            resultaat = !berekendeOperandRechts.alsBoolean()
                    && !berekendeOperandRechts.isNull() ? BooleanLiteral.ONWAAR : NullLiteral.INSTANCE;
        } else {
            resultaat = berekendeOperandLinks.alsBoolean()
                    ? berekendeOperandRechts : BooleanLiteral.ONWAAR;
        }
        return resultaat;
    }

    @Override
    public Prioriteit getPrioriteit() {
        return Prioriteit.PRIORITEIT_LOGISCHE_EN;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.BOOLEAN;
    }

    @Override
    public String operatorAlsString() {
        return "EN";
    }

}
