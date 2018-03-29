/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieOnjuistEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractLoggingTest;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import org.junit.Test;

/**
 * Test het contract van OverlijdenConverteerder.
 */
public final class OverlijdenConverteerderTest extends AbstractLoggingTest {

    private final OverlijdenConverteerder overlijdenConverteerder = new OverlijdenConverteerder(new Lo3AttribuutConverteerder(new ConversietabelFactoryImpl()));

    @Test
    public void testConverteer() {

        final List<Lo3Categorie<Lo3OverlijdenInhoud>> overlijdenCategorieen = new ArrayList<>();

        overlijdenCategorieen.add(new Lo3Categorie<>(
                new Lo3OverlijdenInhoud(new Lo3Datum(20100101), new Lo3GemeenteCode("1234"), new Lo3LandCode("6030")),
                null,
                new Lo3Historie(Lo3IndicatieOnjuistEnum.ONJUIST.asElement(), new Lo3Datum(20100101), new Lo3Datum(20100101)),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_06, 0, 1)));
        overlijdenCategorieen.add(new Lo3Categorie<>(new Lo3OverlijdenInhoud(null, null, null), null, new Lo3Historie(
                null,
                new Lo3Datum(20100101),
                new Lo3Datum(20100201)), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_06, 0, 0)));

        final Lo3Stapel<Lo3OverlijdenInhoud> lo3OverlijdenStapel = new Lo3Stapel<>(overlijdenCategorieen);
        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();

        overlijdenConverteerder.converteer(lo3OverlijdenStapel, builder);
        final TussenPersoonslijst tussenPl = builder.build();
        final TussenStapel<BrpOverlijdenInhoud> overlijdenStapel = tussenPl.getOverlijdenStapel();

        assertNotNull(overlijdenStapel);
        assertEquals(2, overlijdenStapel.size());
        assertEquals(new BrpDatum(20100101, null), overlijdenStapel.get(0).getInhoud().getDatum());
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB025)
    public void testBijzonderSituatieLB025() {
        final List<Lo3Categorie<Lo3OverlijdenInhoud>> overlijdenCategorieen = new ArrayList<>();

        overlijdenCategorieen.add(new Lo3Categorie<>(
                new Lo3OverlijdenInhoud(new Lo3Datum(20100101), new Lo3GemeenteCode("1234"), new Lo3LandCode("6030")),
                null,
                new Lo3Historie(null, new Lo3Datum(20100102), new Lo3Datum(20100102)),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_06, 0, 0)));

        final Lo3Stapel<Lo3OverlijdenInhoud> lo3OverlijdenStapel = new Lo3Stapel<>(overlijdenCategorieen);
        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();

        overlijdenConverteerder.converteer(lo3OverlijdenStapel, builder);
        final TussenPersoonslijst tussenPl = builder.build();
        final TussenStapel<BrpOverlijdenInhoud> overlijdenStapel = tussenPl.getOverlijdenStapel();

        assertNotNull(overlijdenStapel);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB025, 1);
    }
}
