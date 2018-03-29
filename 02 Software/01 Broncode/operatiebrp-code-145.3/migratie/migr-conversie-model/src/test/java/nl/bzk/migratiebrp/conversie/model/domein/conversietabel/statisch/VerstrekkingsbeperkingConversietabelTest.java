/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpProtocolleringsniveauCode;
import org.junit.Assert;
import org.junit.Test;

public class VerstrekkingsbeperkingConversietabelTest {

    private final VerstrekkingsbeperkingConversietabel subject = new VerstrekkingsbeperkingConversietabel();

    @Test
    public void test() {
        Assert.assertEquals(null, subject.converteerNaarBrp(null));
        Assert.assertEquals(BrpProtocolleringsniveauCode.GEEN_BEPERKINGEN, subject.converteerNaarBrp(0));
        Assert.assertEquals(BrpProtocolleringsniveauCode.GEEN_BEPERKINGEN, subject.converteerNaarBrp(1));
        Assert.assertEquals(BrpProtocolleringsniveauCode.GEHEIM, subject.converteerNaarBrp(2));

        Assert.assertEquals(null, subject.converteerNaarLo3(null));
        Assert.assertEquals(Integer.valueOf(2), subject.converteerNaarLo3(BrpProtocolleringsniveauCode.GEHEIM));

        Assert.assertTrue(subject.valideerLo3(null));
        Assert.assertTrue(subject.valideerLo3(0));
        Assert.assertTrue(subject.valideerLo3(1));
        Assert.assertTrue(subject.valideerLo3(2));
        Assert.assertFalse(subject.valideerLo3(-1));
        Assert.assertFalse(subject.valideerLo3(3));

        Assert.assertTrue(subject.valideerBrp(null));
        Assert.assertTrue(subject.valideerBrp(BrpProtocolleringsniveauCode.GEHEIM));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalLo3() {
        subject.converteerNaarBrp(3);
    }
}
