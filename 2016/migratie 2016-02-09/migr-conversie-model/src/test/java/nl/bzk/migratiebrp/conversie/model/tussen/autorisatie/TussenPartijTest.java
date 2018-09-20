/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen.autorisatie;

import java.util.Collections;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpPartijInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.testutils.EqualsAndHashcodeTester;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;

import org.junit.Test;

public class TussenPartijTest {

    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException {
        final TussenPartij subject = maak(19900101);
        final TussenPartij equals = maak(19900101);
        final TussenPartij different = maak(20000101);

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(subject, equals, different);
    }

    public static TussenPartij maak(final int partijCode) {
        return new TussenPartij(null, "Partij " + partijCode, new BrpPartijCode(partijCode), maakStapel(1990010));
    }

    private static TussenStapel<BrpPartijInhoud> maakStapel(final int datumIngang) {
        final BrpPartijInhoud inhoud = new BrpPartijInhoud(new BrpDatum(datumIngang, null), null, null, true);
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(datumIngang), new Lo3Datum(datumIngang));

        final TussenGroep<BrpPartijInhoud> groep = new TussenGroep<>(inhoud, historie, null, null);

        return new TussenStapel<>(Collections.singletonList(groep));
    }
}
