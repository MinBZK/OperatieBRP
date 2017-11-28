/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Representeert een boolean constante.
 */
public final class BooleanLiteral implements Literal {

    /**
     * Constante waarde WAAR (true).
     */
    public static final BooleanLiteral WAAR = new BooleanLiteral(true);
    /**
     * Constante waarde ONWAAR (false).
     */
    public static final BooleanLiteral ONWAAR = new BooleanLiteral(false);

    /**
     * De waarde van de boolean expressie.
     */
    private final boolean value;

    /**
     * Constructor.
     *
     * @param aValue Waarde van de constante.
     */
    private BooleanLiteral(final boolean aValue) {
        this.value = aValue;
    }

    /**
     * Geeft de (constante) boolean expressie die hoort bij de gegeven boolean waarde.
     *
     * @param value Boolean.
     * @return De constante expressie die hoort bij de gegeven boolean waarde.
     */
    public static BooleanLiteral valueOf(final boolean value) {
        return value ? BooleanLiteral.WAAR : BooleanLiteral.ONWAAR;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.BOOLEAN;
    }

    @Override
    public boolean alsBoolean() {
        return value;
    }

    @Override
    public String toString() {
        return value ? "WAAR" : "ONWAAR";
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(value);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final BooleanLiteral that = (BooleanLiteral) o;

        return new EqualsBuilder()
                .append(value, that.value)
                .isEquals();
    }
}
