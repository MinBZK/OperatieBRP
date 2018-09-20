/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import org.junit.Assert;
import org.junit.Test;

public class RelatieTest {
    @Test
    public void addStapel() {
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        final Stapel stapel = new Stapel(new Persoon(SoortPersoon.INGESCHREVENE), "05", 0);

        relatie.addStapel(stapel);
        assertEnkeleKoppeling(relatie, stapel);

        relatie.addStapel(stapel);
        assertEnkeleKoppeling(relatie, stapel);
    }

    private void assertEnkeleKoppeling(final Relatie relatie, final Stapel stapel) {
        Assert.assertEquals(1, relatie.getStapels().size());
        Assert.assertTrue(relatie.getStapels().contains(stapel));
        Assert.assertEquals(1, stapel.getRelaties().size());
        Assert.assertTrue(stapel.getRelaties().contains(relatie));

        final Stapel stapel1 = relatie.getStapels().iterator().next();
        final Relatie relatie1 = stapel.getRelaties().iterator().next();

        Assert.assertSame(stapel, stapel1);
        Assert.assertSame(relatie, relatie1);
    }
}
