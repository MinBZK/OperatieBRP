/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;

import org.apache.commons.collections.functors.TruePredicate;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/** Unit test voor de {@link BevragingResultaat} class. */
public class BevragingResultaatTest {

    @Test
    public final void testGettersEnSettersGevondenPersonenNotNull() {
        final BevragingResultaat bevragingResultaat = new BevragingResultaat(new ArrayList<Melding>());

        final PersoonHisVolledigView persoon1 = maakPersoon();
        ReflectionTestUtils.setField(ReflectionTestUtils.getField(persoon1, "persoon"), "iD", 2);
        bevragingResultaat.voegGevondenPersoonToe(persoon1);
        Assert.assertFalse(bevragingResultaat.getGevondenPersonen().isEmpty());

        final PersoonHisVolledigView persoon2 = maakPersoon();
        ReflectionTestUtils.setField(ReflectionTestUtils.getField(persoon2, "persoon"), "iD", 1);
        bevragingResultaat.voegGevondenPersoonToe(persoon2);

        final Iterator<PersoonHisVolledigView> personen = bevragingResultaat.getGevondenPersonen().iterator();
        Assert.assertEquals(Integer.valueOf(1), personen.next().getID());
        Assert.assertEquals(Integer.valueOf(2), personen.next().getID());
    }

    private PersoonHisVolledigView maakPersoon() {
        return new PersoonHisVolledigView(new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build(), TruePredicate.INSTANCE);
    }

    @Test
    public final void testStandaardConstructor() {
        BevragingResultaat bevragingResultaat = new BevragingResultaat(null);
        Assert.assertNotNull(bevragingResultaat.getMeldingen());
        Assert.assertTrue(bevragingResultaat.getMeldingen().isEmpty());

        bevragingResultaat =
            new BevragingResultaat(Arrays.asList(new Melding(SoortMelding.INFORMATIE, Regel.ALG0001)));
        Assert.assertNotNull(bevragingResultaat.getMeldingen());
        Assert.assertEquals(1, bevragingResultaat.getMeldingen().size());
    }

}
