/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import org.junit.Assert;
import org.junit.Test;

public class PersoonAfnemerindicatieHistorieTest {

    @Test
    public void abonnementConstructorExcepties() {

        try {
            new PersoonAfnemerindicatieHistorie(null);
            Assert.fail("NullPointerException verwacht");
        } catch (final NullPointerException npe) {
            Assert.assertEquals("persoonAfnemerindicatie mag niet null zijn", npe.getMessage());
        }

    }

}
