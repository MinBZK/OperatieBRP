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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpEffectAfnemerindicatiesCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpSoortDienstCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstInhoud;
import nl.bzk.migratiebrp.conversie.model.testutils.EqualsAndHashcodeTester;
import org.junit.Test;

public class BrpDienstTest {

    public static BrpDienst maak(final int datumIngang) {
        return new BrpDienst(
            BrpEffectAfnemerindicatiesCode.PLAATSEN,
            BrpSoortDienstCode.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERSINDICATIE,
            maakStapel(new BrpDatum(datumIngang, null)),
            null,
            null);
    }

    private static BrpStapel<BrpDienstInhoud> maakStapel(final BrpDatum datumIngang) {
        final BrpDienstInhoud inhoud = new BrpDienstInhoud(datumIngang, null, false);
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

        final BrpGroep<BrpDienstInhoud> groep = new BrpGroep<>(inhoud, historie, actie, null, null);

        return new BrpStapel<>(Collections.singletonList(groep));
    }

    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException {
        final BrpDienst subject = maak(19900101);
        final BrpDienst equals = maak(19900101);
        final BrpDienst different = maak(20000101);

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(subject, equals, different);
    }
}
