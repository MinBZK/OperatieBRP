/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.autorisatie;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.testutils.EqualsAndHashcodeTester;
import org.junit.Test;

public class BrpAfnemersindicatieTest {

    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException {
        final BrpAfnemersindicatie subject = maak(1);
        final BrpAfnemersindicatie equals = maak(1);
        final BrpAfnemersindicatie different = maak(2);

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(subject, equals, different);
    }

    public static BrpAfnemersindicatie maak(final int partijCode) {
        return new BrpAfnemersindicatie(new BrpPartijCode(partijCode), null, null);
    }

}
