/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.generateplugin.mojo;

import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void convertToJavaName() {
        Assert.assertEquals("OUDER_1", Utils.convertToJavaEnumName("Ouder 1"));
        Assert.assertEquals("DEELS_UITGESTELD", Utils.convertToJavaEnumName("(Deels) uitgesteld"));
        Assert.assertEquals("NIET_INGEZETENE", Utils.convertToJavaEnumName("Niet-ingezetene"));
        Assert.assertEquals("GBA_INITIELE_VULLING", Utils.convertToJavaEnumName("GBA - Initiele vulling"));
        Assert.assertEquals("@Annotatie\nWAARDE", Utils.convertToJavaEnumName("literal:@Annotatie\nWAARDE"));
        Assert.assertEquals("TEST_LITERALVALUE", Utils.convertToJavaEnumName("Test-literal:value"));
    }
}
