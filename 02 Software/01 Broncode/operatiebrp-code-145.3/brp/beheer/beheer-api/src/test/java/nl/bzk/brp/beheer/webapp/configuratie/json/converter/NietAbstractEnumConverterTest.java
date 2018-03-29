/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.converter;

import com.fasterxml.jackson.databind.type.TypeFactory;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import org.junit.Assert;
import org.junit.Test;

/**
 * test voor AbstractEnumConverter.
 */
public class NietAbstractEnumConverterTest {
    private final TestEnumConverterImpl subject = new TestEnumConverterImpl();

    /** test normale convert. */
    @Test
    public final void testConvertTest() {
        Assert.assertEquals("Eerste in de lijst is Baron", AdellijkeTitel.B, subject.convert(1));
    }

    /** test null convert. */
    @Test
    public final void testConvertNullTest() {
        Assert.assertEquals("null input moet leiden tot null", null, subject.convert(null));
    }

    /** test inputtype. */
    @Test
    public final void testGetInputType() {
        Assert.assertEquals("moet onbekend zijn", TypeFactory.unknownType(), subject.getInputType(null));
    }

    /** test output type. */
    @Test
    public final void testGetOutputType() {
        Assert.assertEquals("moet onbekend zijn", TypeFactory.unknownType(), subject.getInputType(null));
    }

    /** test impl. */
    private static class TestEnumConverterImpl extends AbstractEnumConverter<AdellijkeTitel> {

        /** constructor. */
        TestEnumConverterImpl() {
            super(AdellijkeTitel.class);
        }
    }

}
