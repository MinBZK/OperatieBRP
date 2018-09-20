/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.element;

import org.junit.Assert;
import org.junit.Test;

public class Lo3DatumTest {

    @Test
    public void testOnbekend() {
        Assert.assertTrue("Volledig onbekend", new Lo3Datum(00000000).isOnbekend());
        Assert.assertTrue("Jaar onbekend", new Lo3Datum(00001122).isOnbekend());
        Assert.assertTrue("Maand onbekend", new Lo3Datum(12340022).isOnbekend());
        Assert.assertTrue("Dag onbekend", new Lo3Datum(12341100).isOnbekend());
        Assert.assertFalse("Volledig bekend", new Lo3Datum(12341122).isOnbekend());
    }
}
