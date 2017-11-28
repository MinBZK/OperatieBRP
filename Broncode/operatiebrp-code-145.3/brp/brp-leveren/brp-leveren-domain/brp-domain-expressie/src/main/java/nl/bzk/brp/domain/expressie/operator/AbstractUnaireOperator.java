/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.operator;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieLogger;
import nl.bzk.brp.domain.expressie.NullLiteral;

/**
 * Representeert expressies van unaire operatoren.
 */
public abstract class AbstractUnaireOperator implements Operator {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final Expressie operand;

    /**
     * Constructor.
     *
     * @param aOperand Operand van de operator.
     */
    AbstractUnaireOperator(final Expressie aOperand) {
        this.operand = NullLiteral.veiligeExpressie(aOperand);
    }

    @Override
    public final Expressie evalueer(final Context context) {
        if (ExpressieLogger.IS_DEBUG_ENABLED) {
            LOGGER.debug("evalueer");
        }
        final Expressie berekendeOperand = getOperand().evalueer(context);
        return pasOperatorToe(berekendeOperand, context);
    }

    public final Expressie getOperand() {
        return operand;
    }

    @Override
    public final String toString() {
        final String string;
        if (getOperand().getPrioriteit().getWaarde() <= this.getPrioriteit().getWaarde()) {
            string = operatorAlsString() + "(" + getOperand().toString() + ")";
        } else {
            string = operatorAlsString() + getOperand().toString();
        }
        return string;
    }

    /**
     * Past de operator toe op de operand. De methode gaat ervan uit dat de operand zelf niet verder geëvalueerd kan worden. Als berekening niet mogelijk
     * is, is het resultaat een NULL-expressie.
     *
     * @param berekendeOperand Operand.
     * @param context          De bekende symbolische namen (identifiers) afgebeeld op hun waarde; of NULL als er geen context is.
     * @return De resultaat van de berekening.
     */
    protected abstract Expressie pasOperatorToe(final Expressie berekendeOperand, final Context context);
}
