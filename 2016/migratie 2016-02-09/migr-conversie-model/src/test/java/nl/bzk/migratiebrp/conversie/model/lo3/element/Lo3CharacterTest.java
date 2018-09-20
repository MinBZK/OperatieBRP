/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * tests Lo3CharacterTest.
 */
public class Lo3CharacterTest {

    /**
     * constructor test.
     * 
     * @throws Exception
     */
    @Test
    public void testGetCharacterWaarde() throws Exception {
        Character value = 'a';
        Lo3Character lo3 = new Lo3Character(value);
        Lo3Character lo3b = new Lo3Character("anders", null);
        assertEquals(lo3.getCharacterWaarde(), lo3b.getCharacterWaarde());

    }

    /**
     * wrapping test.
     *
     * @throws Exception
     */
    @Test
    public void testWrap() throws Exception {
        Lo3Character lo3 = Lo3Character.wrap("anummer".charAt(0));
        Lo3Character lo3b = new Lo3Character('a');
        assertNull(Lo3Character.wrap(null));
        assertEquals(lo3.getCharacterWaarde(), lo3b.getCharacterWaarde());

    }

    /**
     * unwrapping test
     * 
     * @throws Exception
     */
    @Test
    public void testUnwrap() throws Exception {
        assertTrue('a' == Lo3Character.unwrap(Lo3Character.wrap("anummer".charAt(0))));
        assertNull(Lo3Character.unwrap(new Lo3Character(null, null)));
        assertNull(Lo3Character.unwrap(null));
    }
}
