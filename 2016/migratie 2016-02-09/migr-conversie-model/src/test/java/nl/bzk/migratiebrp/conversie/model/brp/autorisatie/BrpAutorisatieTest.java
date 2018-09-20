/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.autorisatie;

import java.util.Collections;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpStelselCode;
import nl.bzk.migratiebrp.conversie.model.testutils.EqualsAndHashcodeTester;

import org.junit.Test;

public class BrpAutorisatieTest {

    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException {
        final BrpAutorisatie subject = maak(1);
        final BrpAutorisatie equals = maak(1);
        final BrpAutorisatie different = maak(2);

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(subject, equals, different);
    }

    public static BrpAutorisatie maak(final int partij) {
        return new BrpAutorisatie(
            BrpPartijTest.maak(partij),
            Collections.singletonList(new BrpLeveringsautorisatie(BrpStelselCode.GBA, false, null, null)));
    }
}
