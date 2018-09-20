/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;

import org.junit.Before;
import org.junit.Test;

public class TransformatieDw001Test extends AbstractTransformatieTest {
    private Transformatie transformatie;

    @Before
    public void setup() {
        transformatie = new TransformatieDw001();
    }

    @Test
    public void testAccept() {
        final VerschilGroep verschilGroep = maakPersoonIDVerschilGroep(VerschilType.RIJ_VERWIJDERD);
        assertTrue(transformatie.accept(verschilGroep));
    }

    @Test
    public void testNotAccept() {
        final VerschilGroep verschilGroep = maakPersoonIDVerschilGroep(VerschilType.RIJ_TOEGEVOEGD);
        assertFalse(transformatie.accept(verschilGroep));

        final VerschilGroep verschilGroep2 = maakPersoonIDVerschilGroep(VerschilType.RIJ_VERWIJDERD, true, 2);
        assertFalse(transformatie.accept(verschilGroep2));
    }

    @Test
    public void testExecute() {
        final VerschilGroep verschilGroep = maakPersoonIDVerschilGroep(VerschilType.RIJ_VERWIJDERD);

        final VerschilGroep result = transformatie.execute(verschilGroep, maakDummyActie(), context);
        assertEquals(4, result.size());

        assertEquals(VerschilType.ELEMENT_NIEUW, result.get(0).getVerschilType());
        assertEquals(VerschilType.ELEMENT_NIEUW, result.get(1).getVerschilType());
        assertEquals(VerschilType.ELEMENT_NIEUW, result.get(2).getVerschilType());
        assertEquals(VerschilType.ELEMENT_NIEUW, result.get(3).getVerschilType());
    }

    @Test
    public void testGetCode() {
        assertEquals(DeltaWijziging.DW_001, transformatie.getDeltaWijziging());
    }
}
