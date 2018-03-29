/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Method;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Test;

/**
 * Testen voor {@link AbstractDelegatePersoon}.
 */
public class DelegatePersoonTest {

    private final static Aangever AANGEVER = new Aangever('A', "Aangever", "omschrijving");

    @Test(expected = UnsupportedOperationException.class)
    public void testReadOnly() {
        final Persoon readOnlyDelegatePersoon = new SimpleDelegatePersoon(true, new Persoon(SoortPersoon.INGESCHREVENE));
        readOnlyDelegatePersoon.setAangeverMigratie(AANGEVER);
    }

    @Test
    public void testMeerdereDelegates() {
        final Persoon persoon1 = new Persoon(SoortPersoon.INGESCHREVENE);
        final Persoon persoon2 = new Persoon(SoortPersoon.INGESCHREVENE);

        assertNull(persoon1.getAangeverMigratie());
        assertNull(persoon2.getAangeverMigratie());

        final Persoon delegatePersoon = new SimpleDelegatePersoon(false, persoon1, persoon2);
        delegatePersoon.setAangeverMigratie(AANGEVER);
        assertEquals(AANGEVER, persoon1.getAangeverMigratie());
        assertEquals(AANGEVER, persoon2.getAangeverMigratie());
    }

    @Test
    public void testDelegateMethodesAanwezig() throws NoSuchMethodException {
        for (final Method methodeVanPersoon : Persoon.class.getMethods()) {
            if (!ExcludedMethodList.EXCLUDED_METHODS.contains(methodeVanPersoon.getName())) {
                AbstractDelegatePersoon.class.getDeclaredMethod(methodeVanPersoon.getName(), methodeVanPersoon.getParameterTypes());
            }
        }

    }

    public final static class SimpleDelegatePersoon extends AbstractDelegatePersoon {

        SimpleDelegatePersoon(final boolean isReadOnly, final Persoon... delegates) {
            super(isReadOnly, delegates);
        }
    }
}
