/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Representeert een numerieke constante.
 */
public final class GetalLiteral implements Literal {

    private final Number value;

    /**
     * Constructor.
     *
     * @param aValue Waarde van het vaste-waardeobject.
     */
    public GetalLiteral(final long aValue) {
        this.value = aValue;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.GETAL;
    }

    /**
     * @return geeft het getal als long
     */
    public long getWaarde() {
        return value.longValue();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final GetalLiteral that = (GetalLiteral) o;

        return new EqualsBuilder()
                .append(value, that.value)
                .isEquals();
    }
}
