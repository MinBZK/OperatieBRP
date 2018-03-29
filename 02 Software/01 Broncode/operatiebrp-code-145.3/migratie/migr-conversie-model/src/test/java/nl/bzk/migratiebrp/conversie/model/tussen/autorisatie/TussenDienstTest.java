/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen.autorisatie;

import java.util.Collections;
import org.junit.Assert;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpEffectAfnemerindicatiesCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpSoortDienstCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.testutils.EqualsAndHashcodeTester;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import org.junit.Test;

public class TussenDienstTest {

    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException {
        final TussenDienst subject = maak(19900101);
        final TussenDienst equals = maak(19900101);
        final TussenDienst different = maak(20000101);

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(subject, equals, different);
        Assert.assertEquals(subject.getDienstStapel(), equals.getDienstStapel());
        Assert.assertNotSame(subject.getDienstStapel(), different.getDienstStapel());
    }

    public static TussenDienst maak(final int datumIngang) {
        return new TussenDienst(
                BrpEffectAfnemerindicatiesCode.PLAATSEN,
                BrpSoortDienstCode.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERSINDICATIE,
                maakStapel(datumIngang),
                null,
                null);
    }

    private static TussenStapel<BrpDienstInhoud> maakStapel(final int datumIngang) {
        final BrpDienstInhoud inhoud = new BrpDienstInhoud(new BrpDatum(datumIngang, null), null, false);
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(datumIngang), new Lo3Datum(datumIngang));

        final TussenGroep<BrpDienstInhoud> groep = new TussenGroep<>(inhoud, historie, null, null);

        return new TussenStapel<>(Collections.singletonList(groep));
    }
}
