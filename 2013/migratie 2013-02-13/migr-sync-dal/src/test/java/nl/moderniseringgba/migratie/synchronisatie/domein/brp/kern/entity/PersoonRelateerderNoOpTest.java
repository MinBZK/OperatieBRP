/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.math.BigDecimal;

import nl.moderniseringgba.migratie.synchronisatie.service.impl.relateren.PersoonRelateerderNoOp;

import org.junit.Assert;
import org.junit.Test;

public class PersoonRelateerderNoOpTest {

    private final PersoonRelateerderNoOp persRelateerderNoOp = new PersoonRelateerderNoOp();
    private final Persoon persoon = new Persoon();

    @Test
    public void ok() {
        final Persoon returnedPersoon = persRelateerderNoOp.updateRelatiesVanPersoon(persoon, null);
        Assert.assertEquals(persoon, returnedPersoon);
    }

    @Test
    public void fail() {
        try {
            persRelateerderNoOp.updateRelatiesVanPersoon(persoon, new BigDecimal(1));
            Assert.fail("Er wordt een IllegalArgumentException verwacht");
        } catch (final IllegalArgumentException iae) {
            Assert.assertFalse(iae.getMessage().isEmpty());
        }
    }
}
