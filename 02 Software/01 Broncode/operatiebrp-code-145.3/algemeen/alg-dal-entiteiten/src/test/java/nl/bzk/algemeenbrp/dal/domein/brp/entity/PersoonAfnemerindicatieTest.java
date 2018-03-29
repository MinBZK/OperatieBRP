/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Assert;
import org.junit.Test;

public class PersoonAfnemerindicatieTest {

    @Test
    public void abonnementConstructorExcepties() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        try {
            new PersoonAfnemerindicatie(null, null, null);
            Assert.fail("NullPointerException verwacht");
        } catch (final NullPointerException npe) {
            Assert.assertEquals("persoon mag niet null zijn", npe.getMessage());
        }

        try {
            new PersoonAfnemerindicatie(persoon, new Partij("afnemer", "123124"), null);
            Assert.fail("NullPointerException verwacht");
        } catch (final NullPointerException npe) {
            Assert.assertEquals("leveringsautorisatie mag niet null zijn", npe.getMessage());
        }
    }

}
