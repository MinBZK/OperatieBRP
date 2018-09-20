/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Lo3RedenOpschortingBijhoudingCodeTest {

    public final String STANDAARDWAARDE = ".";
    public final String FOUT = "F";
    public final String RNI = "R";
    public final String OVERLEDEN = "O";
    public final String EMIGRATIE = "E";
    public final String MINISTERIEEL_BESLUIT = "M";

    @Test
    public void testIsOnbekend() throws Exception {
        Lo3RedenOpschortingBijhoudingCode code = new Lo3RedenOpschortingBijhoudingCode(STANDAARDWAARDE);
        assertTrue(code.isOnbekend());
        code = new Lo3RedenOpschortingBijhoudingCode("A");
        assertFalse(code.isOnbekend());
    }

    @Test
    public void testIsEmigratie() throws Exception {
        Lo3RedenOpschortingBijhoudingCode code = new Lo3RedenOpschortingBijhoudingCode(EMIGRATIE);
        assertTrue(code.isEmigratie());
        code = new Lo3RedenOpschortingBijhoudingCode("A");
        assertFalse(code.isEmigratie());
    }

    @Test
    public void testIsMinisterieelBesluit() throws Exception {
        Lo3RedenOpschortingBijhoudingCode code = new Lo3RedenOpschortingBijhoudingCode(MINISTERIEEL_BESLUIT);
        assertTrue(code.isMinisterieelBesluit());
        code = new Lo3RedenOpschortingBijhoudingCode("A");
        assertFalse(code.isMinisterieelBesluit());
    }

    @Test
    public void testIsFout() throws Exception {
        Lo3RedenOpschortingBijhoudingCode code = new Lo3RedenOpschortingBijhoudingCode(FOUT);
        assertTrue(code.isFout());
        code = new Lo3RedenOpschortingBijhoudingCode("A");
        assertFalse(code.isFout());
    }

    @Test
    public void testIsRNI() throws Exception {
        Lo3RedenOpschortingBijhoudingCode code = new Lo3RedenOpschortingBijhoudingCode(RNI);
        assertTrue(code.isRNI());
        code = new Lo3RedenOpschortingBijhoudingCode("A");
        assertFalse(code.isRNI());
    }

    @Test
    public void testIsOverleden() throws Exception {
        Lo3RedenOpschortingBijhoudingCode code = new Lo3RedenOpschortingBijhoudingCode(OVERLEDEN);
        assertTrue(code.isOverleden());
        code = new Lo3RedenOpschortingBijhoudingCode("A");
        assertFalse(code.isOnbekend());
    }
}
