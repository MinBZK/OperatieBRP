/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.rondconversie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Builder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.testutils.VerplichteStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractConversieTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test het contract van ReisdocumentConverteerder.
 */
public class ReisdocumentRondConversieTest extends AbstractConversieTest {

    @Test
    public void testRondConverteerReisdocument() {
        final Lo3PersoonslijstBuilder builder = maakLo3PersoonslijstBuilder();
        voegReisdocumentToe(builder);
        final Lo3Persoonslijst lo3Persoonslijst = builder.build();

        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Persoonslijst);

        final Lo3Persoonslijst lo3Terug = converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst);

        Assert.assertEquals(lo3Persoonslijst.getReisdocumentStapels(), lo3Terug.getReisdocumentStapels());
    }

    private Lo3PersoonslijstBuilder maakLo3PersoonslijstBuilder() {
        final List<Lo3Categorie<Lo3PersoonInhoud>> persoonCategorieen = new ArrayList<>();

        persoonCategorieen.add(Lo3Builder.createLo3Persoon(
            1234567890L,
            987654321,
            "Piet Jan",
            "P",
            "van",
            "HorenZeggen",
            VerplichteStapel.GEBOORTE_DATUM,
            "0000",
            "6030",
            "M",
            null,
            null,
            "E",
            null,
            19800101,
            19800202,
            0));

        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel = new Lo3Stapel<>(persoonCategorieen);
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
                new Lo3Stapel<>(Arrays.asList(new Lo3Categorie<>(new Lo3InschrijvingInhoud(
                    null,
                    null,
                    null,
                    new Lo3Datum(19800101),
                    null,
                    null,
                    null,
                    null,
                    new Lo3Integer(1),
                    new Lo3Datumtijdstempel(20070401000000000L),
                    null), null, Lo3Historie.NULL_HISTORIE, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0))));
        final Lo3Stapel<Lo3OuderInhoud> ouder1 = VerplichteStapel.createOuder1Stapel();
        final Lo3Stapel<Lo3OuderInhoud> ouder2 = VerplichteStapel.createOuder2Stapel();
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaats = VerplichteStapel.createVerblijfplaatsStapel();
        return new Lo3PersoonslijstBuilder().persoonStapel(persoonStapel)
                                            .ouder1Stapel(ouder1)
                                            .ouder2Stapel(ouder2)
                                            .inschrijvingStapel(lo3InschrijvingStapel)
                                            .verblijfplaatsStapel(verblijfplaats);
    }

    private void voegReisdocumentToe(final Lo3PersoonslijstBuilder builder) {
        final List<Lo3Categorie<Lo3ReisdocumentInhoud>> reisdocumentCategorieen = new ArrayList<>();
        reisdocumentCategorieen.add(new Lo3Categorie<>(new Lo3ReisdocumentInhoud(
            new Lo3SoortNederlandsReisdocument("P"),
            Lo3String.wrap("P12345678"),
            new Lo3Datum(20120101),
            new Lo3AutoriteitVanAfgifteNederlandsReisdocument("123456"),
            new Lo3Datum(20170101),
            new Lo3Datum(20130101),
            null,
                    null),
                null,
                new Lo3Historie(null, new Lo3Datum(20120102), new Lo3Datum(20010102)),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_12, 0, 0)));

        builder.reisdocumentStapel(new Lo3Stapel<>(reisdocumentCategorieen));
    }
}
