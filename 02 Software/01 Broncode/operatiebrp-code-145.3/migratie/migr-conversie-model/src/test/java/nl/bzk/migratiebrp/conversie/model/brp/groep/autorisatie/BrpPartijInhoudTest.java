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

public class BrpPartijInhoudTest {

    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException {
        final BrpPartijInhoud subject = new BrpPartijInhoud(new BrpDatum(20130101, null), null, Boolean.FALSE, true);
        final BrpPartijInhoud equal = new BrpPartijInhoud(new BrpDatum(20130101, null), null, Boolean.FALSE, true);
        final BrpPartijInhoud different = new BrpPartijInhoud(new BrpDatum(19900101, null), null, Boolean.FALSE, true);

        Assert.assertFalse(subject.isLeeg());
        Assert.assertTrue(new BrpPartijInhoud(null, null, null, false).isLeeg());

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(subject, equal, different);
    }
}
