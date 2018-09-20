/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import nl.bzk.brp.expressietaal.symbols.DefaultKeywordMapping;
import nl.bzk.brp.expressietaal.symbols.Keywords;


/**
 * Representeert een logische-niet-expressie.
 */
public class BooleanNegateExpressie extends AbstractUnaryOperatorExpressie {

    /**
     * Constructor.
     *
     * @param expressie De te inverteren expressie.
     */
    public BooleanNegateExpressie(final Expressie expressie) {
        super(expressie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String operatorAsString() {
        return DefaultKeywordMapping.getSyntax(Keywords.BOOLEAN_NOT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String operatorAsFormalString() {
        return "!";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expressie bereken(final Expressie gereduceerdeTerm) {
        if (gereduceerdeTerm.isConstanteWaarde() && gereduceerdeTerm.getType() == ExpressieType.BOOLEAN) {
            BooleanLiteralExpressie v = (BooleanLiteralExpressie) gereduceerdeTerm;
            return new BooleanLiteralExpressie(!v.getValue());
        } else {
            return optimaliseer(gereduceerdeTerm);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expressie optimaliseer(final Expressie gereduceerdeTerm) {
        if (gereduceerdeTerm.isConstanteWaarde() && gereduceerdeTerm.getType() == ExpressieType.BOOLEAN) {
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
        return new BooleanNegateExpressie(operatorTerm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ExpressieType getType() {
        return ExpressieType.BOOLEAN;
    }
}

