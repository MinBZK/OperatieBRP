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
import nl.bzk.brp.model.hisvolledig.impl.kern.ActieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.ActieHisVolledig;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class ActieComparatorTest {

    private ActieComparator actieComparator = new ActieComparator();
    private static final String TIJDSTIP_REGISTRATIE_VELD = "tijdstipRegistratie";

    private ActieHisVolledigImpl actie1 = new ActieHisVolledigImpl(null, null, null, null, null, null, null);
    private ActieHisVolledigImpl actie2 = new ActieHisVolledigImpl(null, null, null, null, null, null, null);

    @Before
    public void setup() {
        ReflectionTestUtils.setField(actie1, "iD", 1L);
        ReflectionTestUtils.setField(actie2, "iD", 2L);
    }

    @Test
    public void testTsRegBeideNullDusOpId() {
        final int resultaat = actieComparator.compare(actie1, actie2);

        assertEquals(-1, resultaat);
    }

    @Test
    public void testTsRegEersteNull() {
        ReflectionTestUtils.setField(actie2, TIJDSTIP_REGISTRATIE_VELD, DatumTijdAttribuut.nu());

        final int resultaat = actieComparator.compare(actie1, actie2);

        assertEquals(1, resultaat);
    }

    @Test
    public void testTsRegTweedeNull() {
        ReflectionTestUtils.setField(actie1, TIJDSTIP_REGISTRATIE_VELD, DatumTijdAttribuut.nu());

        final int resultaat = actieComparator.compare(actie1, actie2);

        assertEquals(-1, resultaat);
    }

    @Test
    public void testTsRegGelijkDusOpId() {
        final DatumTijdAttribuut nu = DatumTijdAttribuut.nu();
        final DatumTijdAttribuut ookNu = new DatumTijdAttribuut(nu.getWaarde());
        ReflectionTestUtils.setField(actie1, TIJDSTIP_REGISTRATIE_VELD, nu);
        ReflectionTestUtils.setField(actie2, TIJDSTIP_REGISTRATIE_VELD, ookNu);

        final int resultaat = actieComparator.compare(actie1, actie2);

        assertEquals(-1, resultaat);
    }

    @Test
    public void testTsRegEerstGroter() {
        final DatumTijdAttribuut nu = DatumTijdAttribuut.nu();
        final DatumTijdAttribuut straks = DatumTijdAttribuut.over24Uur();
        ReflectionTestUtils.setField(actie1, TIJDSTIP_REGISTRATIE_VELD, straks);
        ReflectionTestUtils.setField(actie2, TIJDSTIP_REGISTRATIE_VELD, nu);

        final int resultaat = actieComparator.compare(actie1, actie2);

        assertEquals(-1, resultaat);
    }

    @Test
    public void testTsRegTweedeGroter() {
        final DatumTijdAttribuut nu = DatumTijdAttribuut.nu();
        final DatumTijdAttribuut straks = DatumTijdAttribuut.over24Uur();
        ReflectionTestUtils.setField(actie1, TIJDSTIP_REGISTRATIE_VELD, nu);
        ReflectionTestUtils.setField(actie2, TIJDSTIP_REGISTRATIE_VELD, straks);

        final int resultaat = actieComparator.compare(actie1, actie2);

        assertEquals(1, resultaat);
    }

    @Test
    public void testMetLijst() {
        final List<ActieHisVolledig> lijstVanActies = new ArrayList<>();

        final DatumTijdAttribuut geweest = DatumTijdAttribuut.terug24Uur();
        final DatumTijdAttribuut nu = DatumTijdAttribuut.nu();
        final DatumTijdAttribuut straks = DatumTijdAttribuut.over24Uur();

        ReflectionTestUtils.setField(actie1, TIJDSTIP_REGISTRATIE_VELD, nu);
        ReflectionTestUtils.setField(actie2, TIJDSTIP_REGISTRATIE_VELD, straks);
        final ActieHisVolledigImpl actie3 = new ActieHisVolledigImpl(null, null, null, null, null, geweest, null);
        ReflectionTestUtils.setField(actie3, "iD", 3L);
        final ActieHisVolledigImpl actie4 = new ActieHisVolledigImpl(null, null, null, null, null, null, null);
        ReflectionTestUtils.setField(actie4, "iD", 4L);
        final ActieHisVolledigImpl actie5 = new ActieHisVolledigImpl(null, null, null, null, null, null, null);
        ReflectionTestUtils.setField(actie5, "iD", 5L);

        lijstVanActies.add(actie1);
        lijstVanActies.add(actie2);
        lijstVanActies.add(actie3);
        lijstVanActies.add(actie4);
        lijstVanActies.add(actie5);
        Collections.sort(lijstVanActies, actieComparator);

        assertEquals(actie2, lijstVanActies.get(0));
        assertEquals(actie1, lijstVanActies.get(1));
        assertEquals(actie3, lijstVanActies.get(2));
        assertEquals(actie4, lijstVanActies.get(3));
        assertEquals(actie5, lijstVanActies.get(4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullwaarde1() {
        actieComparator.compare(actie1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullwaarde2() {
        actieComparator.compare(null, actie1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullwaardes() {
        actieComparator.compare(null, null);
    }

}
