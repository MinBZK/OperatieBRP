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
public final class GetalLiteralExpressie extends AbstractLiteralExpressie implements Comparable<GetalLiteralExpressie> {

    /**
     * Constante (expressie) voor het getal nul.
     */
    public static final GetalLiteralExpressie NUL = new GetalLiteralExpressie(0);

    private final int value;


    /**
     * Constructor.
     *
     * @param aValue Waarde van het vaste-waardeobject.
     */
    public GetalLiteralExpressie(final int aValue) {
        super();
        this.value = aValue;
    }

    /**
     * Constructor.
     *
     * @param object Numerieke waarde van de expressie.
     */
    public GetalLiteralExpressie(final Object object) {
        super();
        if (object instanceof Integer) {
            this.value = (Integer) object;
        } else if (object instanceof Long) {
            this.value = ((Long) object).intValue();
        } else if (object instanceof Short) {
            this.value = ((Short) object).intValue();
        } else {
            this.value = 0;
        }
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.GETAL;
    }

    @Override
    public boolean alsBoolean() {
        // Default boolean-waarde voor een expressie is FALSE.
        return false;
    }

    @Override
    public int alsInteger() {
        return value;
    }

    @Override
    public long alsLong() {
        return ((Integer) value).longValue();
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
    public int compareTo(final GetalLiteralExpressie o) {
        return Integer.valueOf(alsInteger()).compareTo(o.alsInteger());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final GetalLiteralExpressie that = (GetalLiteralExpressie) o;

        return value == that.value;

    }

    @Override
    public int hashCode() {
        return value;
    }
}
