/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.literals;

/**
 * Representatie van 'onderdelen' van een datum (jaartal, maandnummer, dagnummer). Value object met de mogelijkheid
 * om een waarde 'onbekend' te gebruiken.
 */
public final class Datumdeel implements Comparable<Datumdeel> {

    /**
     * Constante waarde voor datumdelen die 'onbekend' zijn.
     */
    public static final Datumdeel ONBEKEND_DATUMDEEL = new Datumdeel();
    private static final int EENENDERTIG = 31;

    private final int     waarde;
    private final boolean onbekend;


    /**
     * Constructor. Maakt een datumdeel met een bekende waarde. Dit hoeft niet noodzakelijk een voor een datum
     * correcte waarde te zijn.
     *
     * @param aWaarde Numerieke waarde van het datumdeel.
     */
    public Datumdeel(final int aWaarde) {
        this.waarde = aWaarde;
        this.onbekend = false;
    }

    /**
     * Constructor. Private constructor voor singleton die onbekende datumdelen representeert.
     */
    private Datumdeel() {
        this.waarde = 0;
        this.onbekend = true;
    }

    public boolean isOnbekend() {
        return onbekend;
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

        if (onbekend != datumdeel.onbekend) {
            return false;
        }
        if (waarde != datumdeel.waarde) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = waarde;
        result = EENENDERTIG * result + (onbekend ? 1 : 0);
        return result;
    }

    @Override
    public int compareTo(final Datumdeel o) {
        return Integer.valueOf(waarde).compareTo(o.getWaarde());
    }
}
