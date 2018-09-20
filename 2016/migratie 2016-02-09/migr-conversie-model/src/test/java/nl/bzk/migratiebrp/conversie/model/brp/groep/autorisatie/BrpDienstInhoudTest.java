/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.testutils.EqualsAndHashcodeTester;

import org.junit.Assert;
import org.junit.Test;

public class BrpDienstInhoudTest {

    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException {
        final BrpDienstInhoud subject = new BrpDienstInhoud(new BrpDatum(20130101, null), null, false);
        final BrpDienstInhoud equal = new BrpDienstInhoud(new BrpDatum(20130101, null), null, false);
        final BrpDienstInhoud different = new BrpDienstInhoud(new BrpDatum(19900101, null), null, true);

        Assert.assertFalse(subject.isLeeg());
        Assert.assertTrue(new BrpDienstInhoud(null, null, null).isLeeg());

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(subject, equal, different);
        Assert.assertNull(subject.getDatumEinde());
        Assert.assertEquals(subject.getDatumIngang(), equal.getDatumIngang());
        Assert.assertNotSame(subject.getDatumIngang(), different.getDatumIngang());
        Assert.assertEquals(subject.getGeblokkeerd(), equal.getGeblokkeerd());
        Assert.assertNotSame(subject.getGeblokkeerd(), different.getGeblokkeerd());
    }
}
