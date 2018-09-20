/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview;

import static junit.framework.TestCase.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class AdministratieveHandelingComparatorTest {

    private AdministratieveHandelingComparator administratieveHandelingComparator = new AdministratieveHandelingComparator();

    private AdministratieveHandelingHisVolledigImpl ah1 = new AdministratieveHandelingHisVolledigImpl(null, null, null, null);
    private AdministratieveHandelingHisVolledigImpl ah2 = new AdministratieveHandelingHisVolledigImpl(null, null, null, null);

    @Before
    public void setup() {
        zetiDVoorHandeling(ah1, 1L);
        zetiDVoorHandeling(ah2, 2L);
    }

    @Test
    public void testTsRegBeideNull() {
        final int resultaat = administratieveHandelingComparator.compare(ah1, ah2);

        assertEquals(-1, resultaat);
    }

    @Test
    public void testTsRegEersteNull() {
        zetTijdstipregistratieVoorHandeling(ah2, DatumTijdAttribuut.nu());

        final int resultaat = administratieveHandelingComparator.compare(ah1, ah2);

        assertEquals(1, resultaat);
    }

    @Test
    public void testTsRegTweedeNull() {
        zetTijdstipregistratieVoorHandeling(ah1, DatumTijdAttribuut.nu());

        final int resultaat = administratieveHandelingComparator.compare(ah1, ah2);

        assertEquals(-1, resultaat);
    }

    @Test
    public void testTsRegGelijk() {
        final DatumTijdAttribuut nu = DatumTijdAttribuut.nu();
        final DatumTijdAttribuut ookNu = new DatumTijdAttribuut(nu.getWaarde());
        zetTijdstipregistratieVoorHandeling(ah1, nu);
        zetTijdstipregistratieVoorHandeling(ah2, ookNu);

        final int resultaat = administratieveHandelingComparator.compare(ah1, ah2);

        assertEquals(-1, resultaat);
    }

    @Test
    public void testTsRegEerstGroter() {
        final DatumTijdAttribuut nu = DatumTijdAttribuut.nu();
        final DatumTijdAttribuut straks = DatumTijdAttribuut.over24Uur();
        zetTijdstipregistratieVoorHandeling(ah1, straks);
        zetTijdstipregistratieVoorHandeling(ah2, nu);

        final int resultaat = administratieveHandelingComparator.compare(ah1, ah2);

        assertEquals(-1, resultaat);
    }

    @Test
    public void testTsRegTweedeGroter() {
        final DatumTijdAttribuut nu = DatumTijdAttribuut.nu();
        final DatumTijdAttribuut straks = DatumTijdAttribuut.over24Uur();
        zetTijdstipregistratieVoorHandeling(ah1, nu);
        zetTijdstipregistratieVoorHandeling(ah2, straks);

        final int resultaat = administratieveHandelingComparator.compare(ah1, ah2);

        assertEquals(1, resultaat);
    }

    @Test
    public void testMetLijst() {
        final List<AdministratieveHandelingHisVolledigImpl> lijstVanHandelingen = new ArrayList<>();

        final DatumTijdAttribuut geweest = DatumTijdAttribuut.terug24Uur();
        final DatumTijdAttribuut nu = DatumTijdAttribuut.nu();
        final DatumTijdAttribuut straks = DatumTijdAttribuut.over24Uur();

        zetTijdstipregistratieVoorHandeling(ah1, nu);
        zetTijdstipregistratieVoorHandeling(ah2, straks);
        final AdministratieveHandelingHisVolledigImpl ah3 = new AdministratieveHandelingHisVolledigImpl(null, null, null, geweest);
        zetiDVoorHandeling(ah3, 3L);
        final AdministratieveHandelingHisVolledigImpl ah4 = new AdministratieveHandelingHisVolledigImpl(null, null, null, null);
        zetiDVoorHandeling(ah4, 4L);

        lijstVanHandelingen.add(ah1);
        lijstVanHandelingen.add(ah2);
        lijstVanHandelingen.add(ah3);
        lijstVanHandelingen.add(ah4);
        Collections.sort(lijstVanHandelingen, administratieveHandelingComparator);

        assertEquals(ah2, lijstVanHandelingen.get(0));
        assertEquals(ah1, lijstVanHandelingen.get(1));
        assertEquals(ah3, lijstVanHandelingen.get(2));
        assertEquals(ah4, lijstVanHandelingen.get(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEersteNull() {
        administratieveHandelingComparator.compare(null, ah2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTweedeNull() {
        administratieveHandelingComparator.compare(ah1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAllesNull() {
        administratieveHandelingComparator.compare(null, null);
    }

    private void zetTijdstipregistratieVoorHandeling(final AdministratieveHandelingHisVolledigImpl ah, final DatumTijdAttribuut tsreg) {
        ReflectionTestUtils.setField(ah, "tijdstipRegistratie", tsreg);
    }

    private void zetiDVoorHandeling(final AdministratieveHandelingHisVolledigImpl ah, final long id) {
        ReflectionTestUtils.setField(ah, "iD", id);
    }

}
