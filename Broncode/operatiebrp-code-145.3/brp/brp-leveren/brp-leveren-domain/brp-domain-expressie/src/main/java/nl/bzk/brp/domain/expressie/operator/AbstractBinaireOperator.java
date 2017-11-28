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
 * Representeert expressies van binaire operatoren.
 */
public abstract class AbstractBinaireOperator implements Operator {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String VARIABELE_BINNEN_HAKEN = "(%s)";
    private static final String VARIABELE = "%s";
    private final Expressie operandLinks;
    private final Expressie operandRechts;

    /**
     * Constructor.
     *
     * @param aOperandLinks  Linkeroperand van de operator.
     * @param aOperandRechts Rechteroperand van de operator.
     */
    AbstractBinaireOperator(final Expressie aOperandLinks, final Expressie aOperandRechts) {
        this.operandLinks = NullLiteral.veiligeExpressie(aOperandLinks);
        this.operandRechts = NullLiteral.veiligeExpressie(aOperandRechts);
    }

    @Override
    public final Expressie evalueer(final Context context) {
        final Expressie eLinks = operandLinks.evalueer(context);
        final Expressie eRechts = operandRechts.evalueer(context);
        final Expressie result = pasOperatorToe(eLinks, eRechts, context);
        if (ExpressieLogger.IS_DEBUG_ENABLED) {
            LOGGER.debug("{} {} {} =>  {}", eLinks, operatorAlsString(), eRechts, result);
        }
        return result;
    }

    public final Expressie getOperandLinks() {
        return operandLinks;
    }

    public final Expressie getOperandRechts() {
        return operandRechts;
    }

    @Override
    public final String toString() {
        final StringBuilder result = new StringBuilder();
        if (operandLinks.getPrioriteit().getWaarde() <= this.getPrioriteit().getWaarde()) {
            result.append(String.format(VARIABELE_BINNEN_HAKEN, operandLinks.toString()));
        } else {
            result.append(String.format(VARIABELE, operandLinks.toString()));
        }
        result.append(' ');
        result.append(operatorAlsString());
        result.append(' ');
        if (operandRechts.getPrioriteit().getWaarde() <= this.getPrioriteit().getWaarde()) {
            result.append(String.format(VARIABELE_BINNEN_HAKEN, operandRechts.toString()));
        } else {
            result.append(String.format(VARIABELE, operandRechts.toString()));
        }
        return result.toString();
    }

    /**
     * Past de operator toe op twee operanden. De methode gaat ervan uit dat de operanden zelf niet verder geëvalueerd kunnen worden. Als berekening niet
     * mogelijk is, is het resultaat een NULL-expressie.
     *
     * @param berekendeOperandLinks  Linkeroperand van de operator.
     * @param berekendeOperandRechts Rechteroperand van de operator.
     * @param context                De bekende symbolische namen (identifiers) afgebeeld op hun waarde.
     * @return De resultaat van de berekening.
     */
    protected abstract Expressie pasOperatorToe(final Expressie berekendeOperandLinks,
                                                final Expressie berekendeOperandRechts,
                                                final Context context);
}
