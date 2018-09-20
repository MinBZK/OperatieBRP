/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.configuratie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 */
public class BrpBusinessConfiguratieImplTest {

    private BrpBusinessConfiguratie brpBusinessConfiguratie;

    @Before
    public void setup() {
        brpBusinessConfiguratie = new BrpBusinessConfiguratieImpl();
        ReflectionTestUtils.setField(brpBusinessConfiguratie, "databaseTimeOut", 101);
        ReflectionTestUtils.setField(brpBusinessConfiguratie, "lockingTimeOut", 101);
    }

    @Test
    public void testGetDatabaseTimeOutProperty() {
        Assert.assertEquals(101, brpBusinessConfiguratie.getDatabaseTimeOutProperty());
    }

    @Test
    public void testGetLockingTimeOutProperty() {
        Assert.assertEquals(101, brpBusinessConfiguratie.getLockingTimeOutProperty());
    }
}
