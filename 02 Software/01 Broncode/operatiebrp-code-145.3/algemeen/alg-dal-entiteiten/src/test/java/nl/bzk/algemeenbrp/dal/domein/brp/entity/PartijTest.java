/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static org.junit.Assert.*;

import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import org.junit.Test;

/**
 * Testen van niet standaard get/set functionaliteit in Partij.
 */
public class PartijTest {

    @Test
    public void testGetRollen() {
        //setup test data
        final Partij partij = maakPartijMetRollen(Rol.AFNEMER, Rol.BIJHOUDINGSORGAAN_COLLEGE);
        //execute
        final Set<Rol> rollen = partij.getRollen();
        //verify
        assertTrue(rollen.contains(Rol.AFNEMER));
        assertTrue(rollen.contains(Rol.BIJHOUDINGSORGAAN_COLLEGE));
        assertFalse(rollen.contains(Rol.BIJHOUDINGSORGAAN_MINISTER));
        assertTrue(partij.isBijhouder());
    }

    @Test
    public void testIsGeenBijhouder() {
        //setup test data
        final Partij partij = maakPartijMetRollen(Rol.AFNEMER);
        //execute
        final Set<Rol> rollen = partij.getRollen();
        //verify
        assertTrue(rollen.contains(Rol.AFNEMER));
        assertFalse(rollen.contains(Rol.BIJHOUDINGSORGAAN_COLLEGE));
        assertFalse(rollen.contains(Rol.BIJHOUDINGSORGAAN_MINISTER));
        assertFalse(rollen.contains(Rol.BIJHOUDINGSVOORSTELORGAAN));
        assertFalse(partij.isBijhouder());
    }

    @Test
    public void testIsMinisterBijhouder() {
        //setup test data
        final Partij partij = maakPartijMetRollen(Rol.BIJHOUDINGSORGAAN_MINISTER);
        //execute & valideer
        assertTrue(partij.isBijhouder());
    }

    @Test
    public void testIsCollegeBijhouder() {
        //setup test data
        final Partij partij = maakPartijMetRollen(Rol.BIJHOUDINGSORGAAN_COLLEGE);
        //execute & valideer
        assertTrue(partij.isBijhouder());
    }

    @Test
    public void testIsBijhoudingsvoorstelOrgaanBijhouder() {
        //setup test data
        final Partij partij = maakPartijMetRollen(Rol.BIJHOUDINGSVOORSTELORGAAN);
        //execute & valideer
        assertTrue(partij.isBijhouder());
    }

    @Test
    public void testIsBijhouder() {
        //setup test data
        final Partij partij = maakPartijMetRollen(Rol.values());
        //execute & valideer
        assertTrue(partij.isBijhouder());
    }

    private static Partij maakPartijMetRollen(final Rol... rollen) {
        final Partij result = new Partij("test partij", "000000");
        for (final Rol rol : rollen) {
            result.addPartijRol(new PartijRol(result, rol));
        }
        return result;
    }
}
