/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import junit.framework.Assert;

import org.junit.Test;

public class PartijRolTest {
    private final PartijRol partijRol = new PartijRol();

    @Test
    public void categorieSoortDocument() {
        partijRol.setRol(Rol.parseId(1));
        final Rol rol = partijRol.getRol();
        Assert.assertEquals(Rol.AFNEMER, rol);
        Assert.assertNotNull(rol.getNaam());
        Assert.assertFalse(rol.heeftCode());

        try {
            rol.getCode();
            Assert.fail("Er wordt een UnsupportedOperationException verwacht");
        } catch (final UnsupportedOperationException e) {
            Assert.assertNotNull(e);
        }
        partijRol.setRol(null);
        Assert.assertNull(partijRol.getRol());
    }
}
