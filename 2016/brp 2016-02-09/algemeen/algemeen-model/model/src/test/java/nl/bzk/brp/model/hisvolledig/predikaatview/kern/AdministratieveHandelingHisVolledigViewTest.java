/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.basis.IdComparator;
import nl.bzk.brp.model.hisvolledig.impl.kern.ActieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class AdministratieveHandelingHisVolledigViewTest {

    private AdministratieveHandelingHisVolledigImpl administratieveHandelingModel = new AdministratieveHandelingHisVolledigImpl(null, null, null, null);
    private AdministratieveHandelingHisVolledigView administratieveHandelingView =
        new AdministratieveHandelingHisVolledigView(administratieveHandelingModel, null);

    @Test
    public void testHeeftActiesDieGeleverdMogenWordenFalse() throws Exception {
        Set<ActieHisVolledigImpl> acties = new TreeSet<>(new IdComparator());
        final ActieHisVolledigImpl actie = new ActieHisVolledigImpl(null, null, null, null, null, null, null);
        ReflectionTestUtils.setField(actie, "iD", 2L);
        acties.add(actie);
        ReflectionTestUtils.setField(administratieveHandelingModel, "acties", acties);

        assertFalse(administratieveHandelingView.heeftActiesDieGeleverdMogenWorden());
    }

    @Test
    public void testHeeftActiesDieGeleverdMogenWordenTrue() throws Exception {
        final ActieHisVolledigImpl actie = new ActieHisVolledigImpl(null, null, null, null, null, null, null);
        ReflectionTestUtils.setField(actie, "iD", 1L);
        final SoortActieAttribuut soortActie = new SoortActieAttribuut(SoortActie.BEEINDIGING_BEHANDELD_ALS_NEDERLANDER);
        soortActie.setMagGeleverdWorden(true);
        ReflectionTestUtils.setField(actie, "soort", soortActie);
        actie.setMagGeleverdWorden(true);

        final SortedSet<ActieHisVolledigImpl> acties = new TreeSet<>(new IdComparator());
        final ActieHisVolledigImpl actie2 = new ActieHisVolledigImpl(null, null, null, null, null, null, null);
        ReflectionTestUtils.setField(actie2, "iD", 2L);
        acties.add(actie2);
        acties.add(actie);
        ReflectionTestUtils.setField(administratieveHandelingModel, "acties", acties);

        assertTrue(administratieveHandelingView.heeftActiesDieGeleverdMogenWorden());
    }

    @Test
    public void testHeeftActiesDieGeleverdMogenWordenLeeg() throws Exception {
        assertFalse(administratieveHandelingView.heeftActiesDieGeleverdMogenWorden());
    }
}
