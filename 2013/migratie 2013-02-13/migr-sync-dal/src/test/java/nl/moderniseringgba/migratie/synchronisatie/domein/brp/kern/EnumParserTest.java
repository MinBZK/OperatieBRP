/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.EnumParser;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Enumeratie;

import org.junit.Before;
import org.junit.Test;

public class EnumParserTest {

    private EnumParser<TestEnum> parser;

    @Before
    public void before() {
        parser = new EnumParser<TestEnum>(TestEnum.class);
    }

    @Test
    public void testOpvragenOpId() {
        final TestEnum testEnum = parser.parseId(42);
        assertNotNull(testEnum);
        assertEquals(42, testEnum.getId());
    }

    @Test
    public void testOpvragenOpCode() {
        final TestEnum testEnum = parser.parseCode("CODE-A");
        assertNotNull(testEnum);
        assertEquals("CODE-A", testEnum.getCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFoutmeldingBijParseId() {
        parser.parseId(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFoutmeldingBijParseCOde() {
        parser.parseCode("CODE_BESTAAT_NIET");
    }

    private enum TestEnum implements Enumeratie {
        A(Integer.MIN_VALUE, "CODE-A"), B(42, "CODE-B"), C(Integer.MAX_VALUE, "CODE-C");

        private final int id;
        private final String code;

        private TestEnum(final int id, final String code) {
            this.id = id;
            this.code = code;

        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getCode() {
            return code;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean heeftCode() {
            return true;
        }
    }
}
