/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

/**
 * Representeert een numerieke inversie-expressie.
 */
public class NumericNegateExpressie extends AbstractUnaryOperatorExpressie {

    /**
     * Constructor.
     *
     * @param expressie De te inverteren expressie.
     */
    public NumericNegateExpressie(final Expressie expressie) {
        super(expressie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String operatorAsString() {
        return "-";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String operatorAsFormalString() {
        return "-";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expressie bereken(final Expressie gereduceerdeTerm) {
        if (gereduceerdeTerm.isConstanteWaarde() && gereduceerdeTerm.getType() == ExpressieType.NUMBER) {
            NumberLiteralExpressie v = (NumberLiteralExpressie) gereduceerdeTerm;
            return new NumberLiteralExpressie(-v.getValue());
        } else {
            return optimaliseer(gereduceerdeTerm);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expressie optimaliseer(final Expressie gereduceerdeTerm) {
        if (gereduceerdeTerm.isConstanteWaarde() && gereduceerdeTerm.getType() == ExpressieType.NUMBER) {
            return bereken(gereduceerdeTerm);
        } else {
            return create(gereduceerdeTerm);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expressie create(final Expressie operatorTerm) {
        return new NumericNegateExpressie(operatorTerm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ExpressieType getType() {
        return ExpressieType.NUMBER;
    }

}
