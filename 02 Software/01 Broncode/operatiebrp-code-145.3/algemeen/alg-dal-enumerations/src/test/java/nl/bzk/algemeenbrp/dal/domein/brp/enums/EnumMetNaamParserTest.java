/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Unittest voor {@link EnumMetNaamParser}.
 */
public class EnumMetNaamParserTest {

    private EnumMetNaamParser<TestEnum> parser;

    @Before
    public void setUp () {
        parser = new EnumMetNaamParser<>(TestEnum.class);
    }

    @Test
    public void testOpvragenOpNaam () {
        final String naam = "code A";
        final TestEnum testEnum = parser.parseNaam(naam);
        assertNotNull(testEnum);
        assertEquals(naam, testEnum.getNaam());
        assertNull(parser.parseNaam(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFoutmeldingBijParseNaam() {
        parser.parseNaam("Pietje");
    }

    private enum TestEnum implements Enumeratie {
        A(Short.MIN_VALUE, "CODE-A", "code A");

        private final short id;
        private final String code;
        private final String naam;

        TestEnum(final short id, final String code, final String naam) {
            this.id = id;
            this.code = code;
            this.naam = naam;
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
            return naam;
        }

    }

}
