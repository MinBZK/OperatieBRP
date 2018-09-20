/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EnumParser;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie;
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
        final TestEnum testEnum = parser.parseId((short) 42);
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
        parser.parseId((short) 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFoutmeldingBijParseCOde() {
        parser.parseCode("CODE_BESTAAT_NIET");
    }

    private enum TestEnum implements Enumeratie {
        A(Short.MIN_VALUE, "CODE-A"), B((short) 42, "CODE-B"), C(Short.MAX_VALUE, "CODE-C");

        private final short id;
        private final String code;

        private TestEnum(final short id, final String code) {
            this.id = id;
            this.code = code;

        }

        /* (non-Javadoc)
         * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getId()
         */
        @Override
        public short getId() {
            return id;
        }

        /* (non-Javadoc)
         * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
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
    }
}
