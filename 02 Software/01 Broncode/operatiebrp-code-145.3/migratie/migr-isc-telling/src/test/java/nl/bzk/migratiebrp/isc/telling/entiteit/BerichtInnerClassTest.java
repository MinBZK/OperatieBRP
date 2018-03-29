/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.entiteit;

import org.junit.Assert;
import nl.bzk.migratiebrp.isc.telling.entiteit.Bericht.Direction;
import org.junit.Test;

public class BerichtInnerClassTest {

    private static final Direction ENUM_RICHTING_INKOMEND = Bericht.Direction.INKOMEND;
    private static final Direction ENUM_RICHTING_UITGAAND = Bericht.Direction.UITGAAND;
    private static final char CHAR_RICHTING_INKOMEND = 'I';
    private static final char CHAR_RICHTING_UITGAAND = 'U';
    private static final char CHAR_RICHTING_ONGELDIG = 'O';

    @Test
    public void test() {
        Assert.assertEquals(ENUM_RICHTING_INKOMEND, Bericht.Direction.valueOfCode(CHAR_RICHTING_INKOMEND));
        Assert.assertNotSame(ENUM_RICHTING_UITGAAND, Bericht.Direction.valueOfCode(CHAR_RICHTING_INKOMEND));
        Assert.assertEquals(ENUM_RICHTING_UITGAAND, Bericht.Direction.valueOfCode(CHAR_RICHTING_UITGAAND));
        Assert.assertNotSame(ENUM_RICHTING_UITGAAND, Bericht.Direction.valueOfCode(CHAR_RICHTING_INKOMEND));
        Assert.assertNull(Bericht.Direction.valueOfCode(CHAR_RICHTING_ONGELDIG));
    }
}
