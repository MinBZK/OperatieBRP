/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity;

import junit.framework.Assert;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
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
            new PersoonAfnemerindicatie(persoon, new Partij("afnemer", 123124), null);
            Assert.fail("NullPointerException verwacht");
        } catch (final NullPointerException npe) {
            Assert.assertEquals("leveringsautorisatie mag niet null zijn", npe.getMessage());
        }

        //TODO BRM44
    }

}
