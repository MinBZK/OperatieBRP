/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.service;

import static java.util.Collections.sort;
import static junit.framework.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.preview.model.Melding;
import nl.bzk.brp.preview.model.MeldingSoort;
import org.junit.Test;

public class MeldingComparatorTest {

    @Test
    public void testSorteerVolgorde() {
        final Melding m1 = getTestMelding("I", "Informatie");
        final Melding m2 = getTestMelding("F", "Fout");
        final Melding m3 = getTestMelding("W", "Waarschuwing");
        final Melding m4 = getTestMelding("O", "Overrulebaar");

        final List<Melding> meldingen = Arrays.asList(new Melding[]{m1, m2, m3, m4});

        sort(meldingen, new MeldingComparator());

        assertEquals(m2, meldingen.get(0));
        assertEquals(m3, meldingen.get(1));
        assertEquals(m4, meldingen.get(2));
        assertEquals(m1, meldingen.get(3));
    }

    @Test
    public void testCompareToHoger() {
        final Melding m1 = getTestMelding("I", null);
        final Melding m2 = getTestMelding("F", null);

        final MeldingComparator comparator = new MeldingComparator();
        int compareResult = comparator.compare(m1, m2);

        assertEquals(1, compareResult);
    }

    @Test
    public void testCompareToLager() {
        final Melding m1 = getTestMelding("W", null);
        final Melding m2 = getTestMelding("I", null);

        final MeldingComparator comparator = new MeldingComparator();
        int compareResult = comparator.compare(m1, m2);

        assertEquals(-1, compareResult);
    }

    @Test
    public void testCompareGelijk() {
        final Melding m1 = getTestMelding("O", null);
        final Melding m2 = getTestMelding("O", null);

        final MeldingComparator comparator = new MeldingComparator();
        int compareResult = comparator.compare(m1, m2);

        assertEquals(0, compareResult);
    }

    @Test
    public void testCompareOnbekendeMeldingSoort() {
        final Melding m1 = getTestMelding("O", null);
        final Melding m2 = getTestMelding("X", null);

        final MeldingComparator comparator = new MeldingComparator();
        int compareResult = comparator.compare(m1, m2);

        assertEquals(-1, compareResult);
    }

    private Melding getTestMelding(final String soortCode, final String soortNaam) {
        final Melding melding = new Melding();
        final MeldingSoort meldingSoort = new MeldingSoort(soortCode, soortNaam);
        melding.setSoort(meldingSoort);
        return melding;
    }

}
