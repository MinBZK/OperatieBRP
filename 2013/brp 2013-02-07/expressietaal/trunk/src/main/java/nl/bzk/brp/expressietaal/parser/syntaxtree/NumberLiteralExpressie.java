/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import java.math.BigDecimal;

/**
 * Representeert een numerieke constante.
 */
public class NumberLiteralExpressie extends AbstractLiteralExpressie {

    private final int value;

    /**
     * Constructor.
     *
     * @param value Waarde van het vaste-waardeobject.
     */
    public NumberLiteralExpressie(final int value) {
        this.value = value;
    }

    /**
     * Constructor.
     *
     * @param value Waarde van het vaste-waardeobject.
     */
    public NumberLiteralExpressie(final long value) {
        this.value = (int) value;
    }

    /**
     * Constructor.
     *
     * @param value Waarde van het vaste-waardeobject.
     */
    public NumberLiteralExpressie(final BigDecimal value) {
        this.value = value.intValue();
    }

    public final int getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ExpressieType getType() {
        return ExpressieType.NUMBER;
    }

    @Override
    public final boolean isRootObject() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String alsLeesbareString() {
        return String.valueOf(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String alsFormeleString() {
        return "#" + value;
    }
}
