/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.dataaccess;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Bron;

/**
 * Bron table.
 *
 * @param <T> bron
 */
public final class BronTable<T extends Bron> {

    private final Class<T> klasse;
    private final int max;
    private final T[] bronnen;
    private final int[] ranges;

    /**
     * Instantieert Bron table.
     *
     * @param klasse klasse
     * @param bronnenList bronnen list
     */
    public BronTable(final Class<T> klasse, final List<T> bronnenList) {
        int i = 0;
        this.klasse = klasse;

        @SuppressWarnings("unchecked")
        final T[] typedBron = (T[]) new Bron[bronnenList.size()];

        bronnen = typedBron;

        ranges = new int[bronnen.length];
        for (final Iterator<T> iter = bronnenList.iterator(); iter.hasNext(); i++) {
            final T bron = iter.next();
            bronnen[i] = bron;
            ranges[i] = bron.getTotEnMet();
        }
        max = bronnen[bronnen.length - 1].getTotEnMet();
    }

    /**
     * Geeft bron.
     *
     * @param random random
     * @return bron
     */
    public T getBron(final Random random) {
        T bron = null;
        final int val = random.nextInt(max);
        final int gok = (int) ((val * (long) ranges.length) / max);
        final int gok0 = ranges[gok];
        if (gok0 <= val) {
            for (int i = gok + 1; i < ranges.length; i++) {
                if (ranges[i] > val) {
                    bron = bronnen[i];
                    break;
                }
            }
        } else {
            for (int i = gok - 1; i >= 0; i--) {
                if (ranges[i] <= val) {
                    bron = bronnen[i + 1];
                    break;
                }
            }
        }

        if (bron == null) {
            bron = bronnen[0];
        }

        return bron;
    }

    @Override
    public String toString() {
        return String.format("Aantal %s: %s", klasse.getSimpleName(), bronnen.length);
    }

}
