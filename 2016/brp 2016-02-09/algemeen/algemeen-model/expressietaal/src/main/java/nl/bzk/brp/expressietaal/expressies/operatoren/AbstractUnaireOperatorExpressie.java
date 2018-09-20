/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.operatoren;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;

/**
 * Representeert expressies van unaire operatoren.
 */
public abstract class AbstractUnaireOperatorExpressie extends AbstractOperatorExpressie {

    private final Expressie operand;

    /**
     * Constructor.
     *
     * @param aOperand Operand van de operator.
     */
    AbstractUnaireOperatorExpressie(final Expressie aOperand) {
        super();
        this.operand = veiligeExpressie(aOperand);
    }

    public final Expressie getOperand() {
        return operand;
    }

    @Override
    public final Expressie evalueer(final Context context) {
        final Expressie berekendeOperand = getOperand().evalueer(context);
        return pasOperatorToe(berekendeOperand, context);
    }

    @Override
    public final boolean bevatOngebondenVariabele(final String id) {
        return getOperand().bevatOngebondenVariabele(id);
    }

    @Override
    protected final String stringRepresentatie() {
        final String string;
        if (getOperand().getPrioriteit() <= this.getPrioriteit()) {
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
