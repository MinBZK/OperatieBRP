/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Builder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.testutils.VerplichteStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractConversieTest;
import org.junit.Test;

/**
 * Test het contract van VerblijfsrechtConverteerder.
 */
public class VerblijfsrechtConverteerderTest extends AbstractConversieTest {

    @Test
    public void testConverteer() {
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(maakLo3Persoonslijst());
        assertNotNull(brpPersoonslijst.getVerblijfsrechtStapel());
        assertEquals(1, brpPersoonslijst.getVerblijfsrechtStapel().size());
        final BrpStapel<BrpVerblijfsrechtInhoud> verblijfstitelStapel = brpPersoonslijst.getVerblijfsrechtStapel();
        assertEquals(new BrpDatum(20000101, null), verblijfstitelStapel.get(0).getInhoud().getDatumMededelingVerblijfsrecht());
    }

    private Lo3Persoonslijst maakLo3Persoonslijst() {
        final List<Lo3Categorie<Lo3PersoonInhoud>> persoonCategorieen = new ArrayList<>();

        persoonCategorieen.add(
                Lo3Builder.createLo3Persoon(
                        "1234567890",
                        "987654321",
                        "Piet Jan",
                        "PS",
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
                        VerplichteStapel.GEBOORTE_DATUM,
                        VerplichteStapel.GEBOORTE_DATUM + 1,
                        0));

        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel = new Lo3Stapel<>(persoonCategorieen);

        final List<Lo3Categorie<Lo3VerblijfstitelInhoud>> verblijfstitelCategorieen = new ArrayList<>();
        verblijfstitelCategorieen.add(
                new Lo3Categorie<>(
                        new Lo3VerblijfstitelInhoud(new Lo3AanduidingVerblijfstitelCode("12"), null, new Lo3Datum(20000101)),
                        null,
                        new Lo3Historie(null, new Lo3Datum(20000101), new Lo3Datum(20000102)),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_10, 0, 0)));
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
                new Lo3Stapel<>(
                        Collections.singletonList(
                                new Lo3Categorie<>(
                                        new Lo3InschrijvingInhoud(
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
                                                null),
                                        null,
                                        new Lo3Historie(null, null, null),
                                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0))));
        final Lo3Stapel<Lo3OuderInhoud> ouder1 = VerplichteStapel.createOuder1Stapel();
        final Lo3Stapel<Lo3OuderInhoud> ouder2 = VerplichteStapel.createOuder2Stapel();
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaats = VerplichteStapel.createVerblijfplaatsStapel();
        return new Lo3PersoonslijstBuilder().persoonStapel(persoonStapel)
                .ouder1Stapel(ouder1)
                .ouder2Stapel(ouder2)
                .verblijfplaatsStapel(verblijfplaats)
                .verblijfstitelStapel(new Lo3Stapel<>(verblijfstitelCategorieen))
                .inschrijvingStapel(lo3InschrijvingStapel)
                .build();
    }
}
