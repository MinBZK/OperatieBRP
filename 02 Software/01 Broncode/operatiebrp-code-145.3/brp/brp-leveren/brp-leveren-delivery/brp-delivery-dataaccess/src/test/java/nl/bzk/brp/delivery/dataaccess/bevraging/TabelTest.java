/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistoriePatroon;
import org.junit.Assert;
import org.junit.Test;

/**
 * TabelTest.
 */
public class TabelTest {

    @Test
    public void testEquals() {
        final Tabel tabel1 = new Tabel("naam1", "a1", HistoriePatroon.G);
        final Tabel tabel2 = new Tabel("naam1", "a1", HistoriePatroon.G);
        final Tabel tabel3 = new Tabel("naam2", "a1", HistoriePatroon.G);

        Assert.assertTrue(tabel1.equals(tabel2));
        Assert.assertTrue(tabel1.equals(tabel1));
        Assert.assertFalse(tabel2.equals(tabel3));

        Assert.assertFalse(tabel2.equals("test"));
        Assert.assertFalse(tabel2.equals(null));
    }

    @Test
    public void testHashCode() {
        final Tabel tabel1 = new Tabel(null, "a1", HistoriePatroon.G);
        final Tabel tabel2 = new Tabel("naam", "a1", HistoriePatroon.G);

        assertThat(tabel1.hashCode(), is(0));
        assertThat(tabel2.hashCode(), is(3373343));
    }


    @Test
    public void testIsMaterieel() {
        final Tabel tabel1 = new Tabel("naam", "alias", HistoriePatroon.F_M);
        final Tabel tabel2 = new Tabel("naam", "alias", HistoriePatroon.F_M1);
        final Tabel tabel3 = new Tabel("naam", "alias", HistoriePatroon.M1);
        final Tabel tabel4 = new Tabel("naam", "alias", null);

        assertThat(tabel1.isMaterieel(), is(true));
        assertThat(tabel2.isMaterieel(), is(true));
        assertThat(tabel3.isMaterieel(), is(false));
        assertThat(tabel4.isMaterieel(), is(false));
    }
}
