/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import org.junit.Assert;
import org.junit.Test;

public class StapelTest {
    @Test
    public void addRelatie() {
        final Stapel stapel = new Stapel(new Persoon(SoortPersoon.INGESCHREVENE), "05", 0);
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);

        stapel.addRelatie(relatie);
        assertEnkeleKoppeling(stapel, relatie);

        stapel.addRelatie(relatie);
        assertEnkeleKoppeling(stapel, relatie);
    }

    private void assertEnkeleKoppeling(final Stapel stapel, final Relatie relatie) {
        Assert.assertEquals(1, stapel.getRelaties().size());
        Assert.assertTrue(stapel.getRelaties().contains(relatie));
        Assert.assertEquals(1, relatie.getStapels().size());
        Assert.assertTrue(relatie.getStapels().contains(stapel));

        final Stapel stapel1 = relatie.getStapels().iterator().next();
        final Relatie relatie1 = stapel.getRelaties().iterator().next();

        Assert.assertSame(stapel, stapel1);
        Assert.assertSame(relatie, relatie1);
    }
}
