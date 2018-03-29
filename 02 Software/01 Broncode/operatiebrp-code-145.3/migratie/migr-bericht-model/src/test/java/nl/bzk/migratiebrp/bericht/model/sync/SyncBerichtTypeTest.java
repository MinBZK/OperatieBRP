/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testklasse voor SyncBerichtType.
 */
public class SyncBerichtTypeTest {

    private static String STRING_VALUE_PERSOON = "IV.PERS";
    private static String STRING_VALUE_AUTORISATIE = "IV.AUT";
    private static String STRING_VALUE_AFNEMERSINDICATIE = "IV.IND";
    private static String STRING_VALUE_PROTOCOLLERING = "IV.PROT";

    @Test
    public void test() {
        Assert.assertEquals(STRING_VALUE_PERSOON, SyncBerichtType.PERSOON.getType());
        Assert.assertEquals(STRING_VALUE_AUTORISATIE, SyncBerichtType.AUTORISATIE.getType());
        Assert.assertEquals(STRING_VALUE_AFNEMERSINDICATIE, SyncBerichtType.AFNEMERINDICATIE.getType());
        Assert.assertEquals(STRING_VALUE_PROTOCOLLERING, SyncBerichtType.PROTOCOLLERING.getType());
        SyncBerichtType persoon = SyncBerichtType.valueOfType(STRING_VALUE_PERSOON);
        SyncBerichtType autorisatie = SyncBerichtType.valueOfType(STRING_VALUE_AUTORISATIE);
        SyncBerichtType afnemersindicatie = SyncBerichtType.valueOfType(STRING_VALUE_AFNEMERSINDICATIE);
        SyncBerichtType protocollering = SyncBerichtType.valueOfType(STRING_VALUE_PROTOCOLLERING);
        Assert.assertEquals(persoon, SyncBerichtType.PERSOON);
        Assert.assertEquals(autorisatie, SyncBerichtType.AUTORISATIE);
        Assert.assertEquals(afnemersindicatie, SyncBerichtType.AFNEMERINDICATIE);
        Assert.assertEquals(protocollering, SyncBerichtType.PROTOCOLLERING);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOnbekend() {
        SyncBerichtType.valueOfType("ONGELDIG");
    }
}
