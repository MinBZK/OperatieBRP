/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Representatie van 'onderdelen' van een datum (jaartal, maandnummer, dagnummer). Value object met de mogelijkheid
 * om een waarde 'onbekend' te gebruiken.
 */
public final class Datumdeel implements Comparable<Datumdeel> {

    /**
     * Constante waarde voor datumdelen die 'onbekend' zijn.
     */
    public static final Datumdeel ONBEKEND_DATUMDEEL;

    private static final int PRIEM_17 = 17;
    private static final int PRIEM_37 = 37;

    /**
     * Het type datumdeel.
     */
    enum Type {
        /**
         * Datumdeel met een numerieke waarde. Waarde 0
         * heeft de betekenis dat het onbekend is
         * (zo wordt het ook geregistreed).
         */
        WAARDE,
        /**
         * Het vraagteken heeft betekenis in vergelijkingen.
         * Vergelijkingen met datumdeel van type vraagteken zijn niet
         * relevant in een vergelijking.
         */
        VRAAGTEKEN
    }

    /**
     * Code voor onbekend jaartal in een datum.
     */
    private static final int WAARDE_ONBEKEND = 0;

    static {
        ONBEKEND_DATUMDEEL = new Datumdeel();
        ONBEKEND_DATUMDEEL.type = Type.VRAAGTEKEN;
    }

    private int waarde;
    private Type type;

    /**
     * Constructor.
     */
    private Datumdeel() {
    }

    /**
     * Constructor.
     * Maakt een datumdeel met een bekende waarde. Dit hoeft niet noodzakelijk een voor een datum
     * correcte waarde te zijn.
     *
     * @param waarde Numerieke waarde van het datumdeel
     * @return een {@link Datumdeel}
     */
    public static Datumdeel valueOf(final int waarde) {
        final Datumdeel datumdeel = new Datumdeel();
        datumdeel.waarde = waarde;
        datumdeel.type = Type.WAARDE;
        return datumdeel;
    }

    public boolean isBekend() {
        return type == Type.WAARDE && waarde != WAARDE_ONBEKEND;
    }

    public boolean isOnbekend() {
        return type == Type.WAARDE && waarde == WAARDE_ONBEKEND;
    }

    public boolean isWaarde() {
        return type == Type.WAARDE;
    }

    public boolean isVraagteken() {
        return type == Type.VRAAGTEKEN;
    }

    public int getWaarde() {
        return waarde;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Datumdeel datumdeel = (Datumdeel) o;

        return new EqualsBuilder()
                .append(waarde, datumdeel.waarde)
                .append(type, datumdeel.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(PRIEM_17, PRIEM_37)
                .append(waarde)
                .append(type)
                .toHashCode();
    }

    @Override
    public int compareTo(final Datumdeel o) {
        return Integer.valueOf(waarde).compareTo(o.getWaarde());
    }

    @Override
    public String toString() {
        final String value;
        if (type == Type.VRAAGTEKEN) {
            value = DatumLiteral.ONBEKEND_DATUMDEEL_STRING;
        } else {
            value = String.valueOf(waarde);
        }
        return value;
    }
}
