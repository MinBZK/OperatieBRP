/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.autorisatie;

import java.util.Collections;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpPartijInhoud;
import nl.bzk.migratiebrp.conversie.model.testutils.EqualsAndHashcodeTester;
import org.junit.Test;

public class BrpPartijTest {

    public static BrpPartij maak(final String partijCode) {
        return new BrpPartij(null, "Partij " + partijCode, new BrpPartijCode(partijCode), maakStapel(new BrpDatum(19900101, null)));
    }

    private static BrpStapel<BrpPartijInhoud> maakStapel(final BrpDatum datumIngang) {
        final BrpPartijInhoud inhoud = new BrpPartijInhoud(datumIngang, null, null, true);
        final BrpHistorie historie = new BrpHistorie(BrpDatumTijd.fromDatum(datumIngang.getWaarde(), null), null, null);
        final BrpActie actie =
                new BrpActie(
                        1L,
                        BrpSoortActieCode.CONVERSIE_GBA,
                        BrpPartijCode.MIGRATIEVOORZIENING,
                        historie.getDatumTijdRegistratie(),
                        null,
                        null,
                        1,
                        null);

        final BrpGroep<BrpPartijInhoud> groep = new BrpGroep<>(inhoud, historie, actie, null, null);

        return new BrpStapel<>(Collections.singletonList(groep));
    }

    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException {
        final BrpPartij subject = maak("199001");
        final BrpPartij equals = maak("199001");
        final BrpPartij different = maak("200001");

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(subject, equals, different);
    }
}
