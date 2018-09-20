/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.operatoren;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.literals.GetalLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.GrootGetalLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;

/**
 * Representeert een numerieke inversie-expressie.
 */
public class NumeriekeInverseOperatorExpressie extends AbstractUnaireOperatorExpressie {

    /**
     * Constructor.
     *
     * @param expressie De te inverteren expressie.
     */
    public NumeriekeInverseOperatorExpressie(final Expressie expressie) {
        super(expressie);
    }

    @Override
    public final ExpressieType getType(final Context context) {
        final ExpressieType result;
        if (getOperand().getType(context) == ExpressieType.GETAL) {
            result = ExpressieType.GETAL;
        } else if (getOperand().getType(context) == ExpressieType.GROOT_GETAL) {
            result = ExpressieType.GROOT_GETAL;
        } else {
            result = ExpressieType.ONBEKEND_TYPE;
        }
        return result;
    }

    @Override
    public final int getPrioriteit() {
        return PRIORITEIT_INVERSE;
    }

    @Override
    public final Expressie optimaliseer(final Context context) {
        final Expressie geoptimaliseerdeExpressie;
        final Expressie geoptimaliseerdeOperand = getOperand().optimaliseer(context);
        if (geoptimaliseerdeOperand.isConstanteWaarde(ExpressieType.GETAL, context)) {
            geoptimaliseerdeExpressie = new GetalLiteralExpressie(-geoptimaliseerdeOperand.alsInteger());
        } else if (geoptimaliseerdeOperand.isConstanteWaarde(ExpressieType.GROOT_GETAL, context)) {
            geoptimaliseerdeExpressie = new GrootGetalLiteralExpressie(-geoptimaliseerdeOperand.alsLong());
        } else {
            geoptimaliseerdeExpressie = new NumeriekeInverseOperatorExpressie(geoptimaliseerdeOperand);
        }
        return geoptimaliseerdeExpressie;
    }

    @Override
    protected final String operatorAlsString() {
        return "-";
    }

    @Override
    protected final Expressie pasOperatorToe(final Expressie berekendeOperand, final Context context) {
        final Expressie resultaat;
        if (berekendeOperand.isConstanteWaarde(ExpressieType.GETAL, context)) {
            resultaat = new GetalLiteralExpressie(-berekendeOperand.alsInteger());
        } else if (berekendeOperand.isConstanteWaarde(ExpressieType.GROOT_GETAL, context)) {
            resultaat = new GrootGetalLiteralExpressie(-berekendeOperand.alsLong());
        } else {
            resultaat = NullValue.getInstance();
        }
        return resultaat;
    }
}
