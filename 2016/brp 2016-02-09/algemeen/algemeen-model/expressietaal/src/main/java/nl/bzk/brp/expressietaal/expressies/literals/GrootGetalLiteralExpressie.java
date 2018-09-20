/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.literals;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Representeert een numerieke constante.
 */
public final class GrootGetalLiteralExpressie extends AbstractLiteralExpressie implements Comparable<GrootGetalLiteralExpressie> {

    /**
     * Constante (expressie) voor het getal nul.
     */
    public static final GrootGetalLiteralExpressie NUL = new GrootGetalLiteralExpressie(0L);
    private static final int TWEEENDERTIG = 32;

    private final long value;

    /**
     * Constructor.
     *
     * @param aValue Waarde van het vaste-waardeobject.
     */
    public GrootGetalLiteralExpressie(final long aValue) {
        super();
        this.value = aValue;
    }

    /**
     * Constructor.
     *
     * @param object Numerieke waarde van de expressie.
     */
    public GrootGetalLiteralExpressie(final Object object) {
        super();
        if (object instanceof Long) {
            this.value = (Long) object;
        } else if (object instanceof Integer) {
            this.value = ((Integer) object).longValue();
        } else if (object instanceof Short) {
            this.value = ((Short) object).longValue();
        } else {
            this.value = 0L;
        }
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.GROOT_GETAL;
    }

    @Override
    public boolean alsBoolean() {
        // Default boolean-waarde voor een expressie is FALSE.
        return false;
    }

    @Override
    public int alsInteger() {
        return ((Long) value).intValue();
    }

    @Override
    public long alsLong() {
        return value;
    }

    @Override
    public String alsString() {
        return stringRepresentatie();
    }

    @Override
    public Attribuut getAttribuut() {
        return null;
    }

    @Override
    public Groep getGroep() {
        return null;
    }

    @Override
    protected String stringRepresentatie() {
        return String.valueOf(value);
    }

    @Override
    public int compareTo(final GrootGetalLiteralExpressie o) {
        return Long.valueOf(alsLong()).compareTo(o.alsLong());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final GrootGetalLiteralExpressie that = (GrootGetalLiteralExpressie) o;

        return value == that.value;

    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> TWEEENDERTIG));
    }
}
