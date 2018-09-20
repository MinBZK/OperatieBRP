/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale.berichten;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class GbaBijhoudingNotificatieBerichtTest {

    @Test
    public void test() {
        final GbaBijhoudingNotificatieBericht subject = new GbaBijhoudingNotificatieBericht();
        Assert.assertEquals(null, subject.getAdministratieveHandelingId());
        Assert.assertEquals(null, subject.getBijgehoudenPersoonIds());

        subject.setAdministratieveHandelingId(1L);
        final ArrayList<Integer> bijgehoudenPersoonIds = new ArrayList<Integer>();
        subject.setBijgehoudenPersoonIds(bijgehoudenPersoonIds);

        Assert.assertEquals(Long.valueOf(1L), subject.getAdministratieveHandelingId());
        Assert.assertEquals(bijgehoudenPersoonIds, subject.getBijgehoudenPersoonIds());

        Assert.assertNotNull(subject.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvenienceConstructorGeenAdmHndId() {
        new GbaBijhoudingNotificatieBericht(null, Arrays.asList(Integer.valueOf(1)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvenienceConstructorGeenLijst() {
        new GbaBijhoudingNotificatieBericht(1L, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvenienceConstructorEmptyLijst() {
        new GbaBijhoudingNotificatieBericht(1L, new ArrayList<Integer>());
    }

    @Test
    public void testConvenienceConstructor() {
        final List<Integer> bijgehoudenPersoonIds = Arrays.asList(Integer.valueOf(2));
        final GbaBijhoudingNotificatieBericht subject = new GbaBijhoudingNotificatieBericht(1L, bijgehoudenPersoonIds);

        Assert.assertEquals(Long.valueOf(1L), subject.getAdministratieveHandelingId());
        Assert.assertEquals(bijgehoudenPersoonIds, subject.getBijgehoudenPersoonIds());
    }

}
