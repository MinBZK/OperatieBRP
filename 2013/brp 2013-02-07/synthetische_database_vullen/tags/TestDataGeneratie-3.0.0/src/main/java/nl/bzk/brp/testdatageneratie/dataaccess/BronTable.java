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

public class BronTable<T extends Bron> {

    private final Class<T> klasse;
    private final int max;
    private final T[] bronnen;
    private final int[] ranges;

    public BronTable(final Class<T> klasse, final List<T> bronnenList) {
        int i = 0;
        this.klasse = klasse;

        @SuppressWarnings("unchecked")
        T[] typedBron = (T[]) new Bron[bronnenList.size()];

        bronnen = typedBron;

        ranges = new int[bronnen.length];
        for (Iterator<T> iter = bronnenList.iterator(); iter.hasNext(); i++) {
            T bron = iter.next();
            bronnen[i] = bron;
            ranges[i] = bron.getTotEnMet();
        }
        max = bronnen[bronnen.length - 1].getTotEnMet();
    }

    public T getBron(final Random random) {
        int val = random.nextInt(max);
        int gok = (int) ((val * (long) ranges.length) / max);
        int gok0 = ranges[gok];
        if (gok0 <= val) {
            for (int i = gok+1; i < ranges.length; i++) {
                if (ranges[i] > val) return bronnen[i];
            }
        }
        for (int i = gok-1; i >= 0; i--) {
            if (ranges[i] <= val) return bronnen[i+1];
        }
        return bronnen[0];
    }

    public String toString() {
        return String.format("Aantal %s: %s", klasse.getSimpleName(), bronnen.length);
    }

}
