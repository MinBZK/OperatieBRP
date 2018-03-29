/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import org.junit.Assert;
import org.junit.Test;

public class RegelConversietabelTest {

    private final RegelConversietabel subject = new RegelConversietabel();

    @Test
    public void naarBrp() {
        Assert.assertFalse(subject.valideerLo3(null));
        Assert.assertFalse(subject.valideerLo3('X'));

        try {
            subject.converteerNaarBrp('X');
            Assert.fail("Converteer naar BRP zou moeten falen");
        } catch (final IllegalArgumentException e) {
            // Ok
        }
    }

    @Test
    public void naarLo3() {
        Assert.assertFalse(subject.valideerBrp("ALG0001"));
        Assert.assertTrue(subject.valideerBrp("R1261"));

        Assert.assertEquals(null, subject.converteerNaarLo3("ALG0001"));
        Assert.assertEquals(Character.valueOf('X'), subject.converteerNaarLo3("R1261"));
    }

}
