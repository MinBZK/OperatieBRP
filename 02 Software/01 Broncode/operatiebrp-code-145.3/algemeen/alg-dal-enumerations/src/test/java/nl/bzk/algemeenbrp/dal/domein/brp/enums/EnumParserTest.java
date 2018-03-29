/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class EnumParserTest {

    private EnumParser<TestEnum> parser;

    @Before
    public void before() {
        parser = new EnumParser<>(TestEnum.class);
    }

    @Test
    public void testOpvragenOpId() {
        final TestEnum testEnum = parser.parseId(42);
        assertNotNull(testEnum);
        assertEquals(42, testEnum.getId());

        assertNull(parser.parseId(null));
    }

    @Test
    public void heeftId() {
        assertTrue(parser.heeftId(42));
    }

    @Test
    public void heeftCode() {
        assertTrue(parser.heeftCode("CODE-B"));
    }

    @Test
    public void testOpvragenOpCode() {
        final TestEnum testEnum = parser.parseCode("CODE-A");
        assertNotNull(testEnum);
        assertEquals(TestEnum.A, testEnum);
        assertFalse(TestEnum.B.equals(testEnum));
        assertFalse(TestEnum.C.equals(testEnum));
        assertEquals("CODE-A", testEnum.getCode());
        assertNull(parser.parseCode(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFoutmeldingBijParseId() {
        parser.parseId(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFoutmeldingBijParseCOde() {
        parser.parseCode("CODE_BESTAAT_NIET");
    }

    @Test
    public void testGetFoutmelding() {
        assertEquals("Geen enumeratiewaarde van het type nl.bzk.algemeenbrp.dal.domein.brp.enums.EnumParserTest$TestEnum voor %s %s", parser.getFoutmelding());
    }

    private enum TestEnum implements Enumeratie {
        A(Short.MIN_VALUE, "CODE-A"), B((short) 42, "CODE-B"), C(Short.MAX_VALUE, "CODE-C");

        private final short id;
        private final String code;

        TestEnum(final short id, final String code) {
            this.id = id;
            this.code = code;

        }

        /*
         * (non-Javadoc)
         * 
         * @see Enumeratie#getId()
         */
        @Override
        public int getId() {
            return id;
        }

        /*
         * (non-Javadoc)
         * 
         * @see Enumeratie#getCode()
         */
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

        @Override
        public String getNaam() {
            throw new UnsupportedOperationException(String.format("De enumeratie %s heeft geen naam", this.name()));
        }

    }
}
