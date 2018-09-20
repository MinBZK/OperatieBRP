/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.domein.brm;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;

import nl.bzk.brp.bevraging.domein.RedenOpschorting;
import nl.bzk.brp.bevraging.domein.Verantwoordelijke;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class RegelGedragTest {

    private static final Verantwoordelijke VERANTWOORDELIJKE = Verantwoordelijke.COLLEGE;
    private static final Boolean           IS_OPSCHORTING    = true;
    private static final RedenOpschorting  REDEN_OPSCHORTING = RedenOpschorting.FOUT;

    @Test
    public void testIsSpecifiekerDanEenAlgemeenGedrag() {
        RegelGedrag gedrag1 = creeerGedrag(VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING);
        RegelGedrag gedrag2 = creeerGedrag(VERANTWOORDELIJKE, null, REDEN_OPSCHORTING);

        assertTrue(gedrag1.isSpecifiekerDan(gedrag2));
    }

    @Test
    public void testIsSpecifiekerDanEenAnderAlgemeenGedrag() {
        RegelGedrag gedrag1 = creeerGedrag(null, IS_OPSCHORTING, REDEN_OPSCHORTING);
        RegelGedrag gedrag2 = creeerGedrag(VERANTWOORDELIJKE, null, REDEN_OPSCHORTING);

        assertTrue(gedrag2.isSpecifiekerDan(gedrag1));
    }

    @Test
    public void testIsSpecifiekerDanNull() {
        RegelGedrag gedrag1 = creeerGedrag(null, null, null);

        assertTrue(gedrag1.isSpecifiekerDan(null));
    }

    private RegelGedrag creeerGedrag(final Verantwoordelijke verantwoordelijke, final Boolean opschorting,
            final RedenOpschorting redenOpschorting)
    {
        RegelGedrag resultaat;
        try {
            Constructor<RegelGedrag> constructor = RegelGedrag.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            resultaat = constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ReflectionTestUtils.setField(resultaat, "verantwoordelijke", verantwoordelijke);
        ReflectionTestUtils.setField(resultaat, "opschorting", opschorting);
        ReflectionTestUtils.setField(resultaat, "redenOpschorting", redenOpschorting);
        return resultaat;
    }

}
