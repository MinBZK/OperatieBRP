/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen.autorisatie;

import java.util.Collections;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpStelselCode;
import nl.bzk.migratiebrp.conversie.model.testutils.EqualsAndHashcodeTester;
import org.junit.Test;

public class TussenAutorisatieTest {

    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException {
        final TussenAutorisatie subject = maak("000001");
        final TussenAutorisatie equals = maak("000001");
        final TussenAutorisatie different = maak("000002");

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(subject, equals, different);
    }

    public static TussenAutorisatie maak(final String partij) {
        return new TussenAutorisatie(new BrpPartijCode(partij), new BrpBoolean(false),
                Collections.singletonList(new TussenLeveringsautorisatie(BrpStelselCode.GBA, false, null, null)));
    }
}
