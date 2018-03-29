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

public class BrpAfnemersindicatieInhoudTest {

    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException {
        final BrpAfnemersindicatieInhoud subject = new BrpAfnemersindicatieInhoud(null, null, false);
        final BrpAfnemersindicatieInhoud equal = new BrpAfnemersindicatieInhoud(null, null, false);
        final BrpAfnemersindicatieInhoud different = new BrpAfnemersindicatieInhoud(BrpDatum.ONBEKEND, new BrpDatum(19900101, null), false);

        Assert.assertFalse(subject.isLeeg());
        Assert.assertTrue(new BrpAfnemersindicatieInhoud(null, null, true).isLeeg());

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(subject, equal, different);
    }
}
