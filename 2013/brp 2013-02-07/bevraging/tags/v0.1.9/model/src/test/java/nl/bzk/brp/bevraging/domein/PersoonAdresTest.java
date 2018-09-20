/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link PersoonAdres} class.
 */
public class PersoonAdresTest {

    @Test
    public void testConstructorMetNulls() {
        PersoonAdres persoonAdres = new PersoonAdres(null, null);
        assertNull(persoonAdres.getSoort());
        assertNull(ReflectionTestUtils.getField(persoonAdres, "persoon"));
    }

    @Test
    public void testConstructorMetValideArgumenten() {
        Persoon persoon = new Persoon(SoortPersoon.NIET_INGESCHREVENE);

        PersoonAdres persoonAdres = new PersoonAdres(persoon, FunctieAdres.BRIEFADRES);
        assertSame(FunctieAdres.BRIEFADRES, persoonAdres.getSoort());
        assertSame(persoon, ReflectionTestUtils.getField(persoonAdres, "persoon"));
    }

}
