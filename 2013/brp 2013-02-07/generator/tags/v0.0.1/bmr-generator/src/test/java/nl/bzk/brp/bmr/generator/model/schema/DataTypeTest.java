/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.schema;

import org.junit.Assert;
import org.junit.Test;


public class DataTypeTest {

    @Test
    public void testGeheelGetal() {
        DataType dataType = DataType.getDataType("geheel getal");
        Assert.assertEquals("Integer", dataType.getPostgresType(1, 10));
    }

    @Test
    public void testDecimaalGetal() {
        DataType dataType = DataType.getDataType("decimaal getal");
        Assert.assertEquals("Numeric(1)", dataType.getPostgresType(1, 0));
    }

    public void testDatumMetOnzekerheidMetArgumenten() {
        DataType dataType = DataType.getDataType("datum met onzekerheid");
        Assert.assertEquals("Numeric(1, 0)", dataType.getPostgresType(1, 0));
    }
}
