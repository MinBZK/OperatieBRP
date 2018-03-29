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
import nl.bzk.brp.domain.expressie.GetalLiteral;
import nl.bzk.brp.domain.expressie.NullLiteral;

/**
 * Representeert een numerieke inversie-expressie.
 */
public final class NumeriekeInverseOperator extends AbstractUnaireOperator {

    /**
     * Constructor.
     *
     * @param expressie De te inverteren expressie.
     */
    public NumeriekeInverseOperator(final Expressie expressie) {
        super(expressie);
    }

    @Override
    protected Expressie pasOperatorToe(final Expressie berekendeOperand, final Context context) {
        Expressie resultaat = NullLiteral.INSTANCE;
        if (berekendeOperand.isConstanteWaarde(ExpressieType.GETAL, context)) {
            resultaat = new GetalLiteral(-((GetalLiteral)berekendeOperand).getWaarde());
        }
        return resultaat;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return getOperand().getType(context) == ExpressieType.GETAL
                ? ExpressieType.GETAL
                : ExpressieType.ONBEKEND_TYPE;
    }

    @Override
    public Prioriteit getPrioriteit() {
        return Prioriteit.PRIORITEIT_INVERSE;
    }

    @Override
    public String operatorAlsString() {
        return "-";
    }
}
