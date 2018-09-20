/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale.berichten;

import org.junit.Assert;
import org.junit.Test;

public class AfnemerindicatieOnderhoudAntwoordTest {

    @Test
    public void test() {
        final AfnemerindicatieOnderhoudAntwoord subject = new AfnemerindicatieOnderhoudAntwoord();
        Assert.assertEquals(null, subject.getFoutcode());
        Assert.assertEquals(null, subject.getReferentienummer());

        subject.setFoutcode('X');
        subject.setReferentienummer("12312313");

        Assert.assertEquals(Character.valueOf('X'), subject.getFoutcode());
        Assert.assertEquals("12312313", subject.getReferentienummer());
    }
}
