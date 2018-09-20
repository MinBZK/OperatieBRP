/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.nationaliteit;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.testutils.VerplichteStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractConversieTest;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test om de speciale casus van reden verkrijging en reden verlies nederlanderschap te testen bij het converteren van
 * nationaliteit.
 *
 */

public class VerliesNederlanderschapTest extends AbstractConversieTest {

    @Test
    public void testSimple() {
        // Input
        // Logging
        Logging.initContext();

        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();

        categorieen.add(VerliesNederlanderschapTest.buildNationaliteit(
            "0001",
            "017",
            null,
            null,
            null,
            19000101,
            19000102,
            1,
            "0518",
            "1-Verkrijging-1",
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 1)));
        categorieen.add(VerliesNederlanderschapTest.buildNationaliteit(
            null,
            null,
            "087",
            null,
            null,
            19960101,
            19960101,
            1,
            "0518",
            "1-Verkrijging-1",
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0)));

        final Lo3Stapel<Lo3NationaliteitInhoud> lo3 = new Lo3Stapel<>(categorieen);
        final BrpStapel<BrpNationaliteitInhoud> brp = test(lo3);

        Assert.assertEquals(1, brp.size());
        final BrpGroep<BrpNationaliteitInhoud> groep = brp.get(0);

        // Inhoud
        Assert.assertNotNull(groep.getInhoud());
        Assert.assertNotNull(groep.getInhoud().getRedenVerkrijgingNederlandschapCode());
        Assert.assertEquals(Short.valueOf("17"), groep.getInhoud().getRedenVerkrijgingNederlandschapCode().getWaarde());
        Assert.assertNotNull(groep.getInhoud().getRedenVerliesNederlandschapCode());
        Assert.assertEquals(Short.valueOf("87"), groep.getInhoud().getRedenVerliesNederlandschapCode().getWaarde());

        // Historie
        Assert.assertNotNull(groep.getHistorie());
        Assert.assertEquals(new BrpDatum(19000101, null), groep.getHistorie().getDatumAanvangGeldigheid());
        Assert.assertEquals(new BrpDatum(19960101, null), groep.getHistorie().getDatumEindeGeldigheid());

        // Acties
        Assert.assertNotNull(groep.getActieInhoud());
        Assert.assertNotNull(groep.getActieGeldigheid());
        Logging.destroyContext();
    }

    private BrpStapel<BrpNationaliteitInhoud> test(final Lo3Stapel<Lo3NationaliteitInhoud> nationaliteitStapel) {
        final Lo3Persoonslijst lo3 = VerliesNederlanderschapTest.buildLo3Persoonslijst(nationaliteitStapel);
        final BrpPersoonslijst brp = converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3);

        Assert.assertNotNull(brp);
        Assert.assertNotNull(brp.getNationaliteitStapels());
        Assert.assertEquals(1, brp.getNationaliteitStapels().size());
        return brp.getNationaliteitStapels().get(0);
    }

    private static Lo3Categorie<Lo3NationaliteitInhoud> buildNationaliteit(
        final String nationaliteit,
        final String redenVerkrijging,
        final String redenVerlies,
        final String aanduidingBijzonder,
        final Lo3IndicatieOnjuist indicatieOnjuist,
        final Integer ingangsdatumGeldigheid,
        final Integer datumVanOpneming,
        final Integer documentId,
        final String gemeenteCodeAkte,
        final String nummerAkte,
        final Lo3Herkomst herkomst)
    {

        final Lo3NationaliteitInhoud inhoud =
                new Lo3NationaliteitInhoud(
                    nationaliteit == null ? null : new Lo3NationaliteitCode(nationaliteit),
                    redenVerkrijging == null ? null : new Lo3RedenNederlandschapCode(redenVerkrijging),
                    redenVerlies == null ? null : new Lo3RedenNederlandschapCode(redenVerlies),
                    aanduidingBijzonder == null ? null : new Lo3AanduidingBijzonderNederlandschap(aanduidingBijzonder));

        // inhoud.valideer();

        final Lo3Historie historie = new Lo3Historie(indicatieOnjuist, new Lo3Datum(ingangsdatumGeldigheid), new Lo3Datum(datumVanOpneming));
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(documentId, new Lo3GemeenteCode(gemeenteCodeAkte), Lo3String.wrap(nummerAkte), null, null, null, null, null);

        return new Lo3Categorie<>(inhoud, documentatie, historie, herkomst);
    }

    private static Lo3Persoonslijst buildLo3Persoonslijst(final Lo3Stapel<Lo3NationaliteitInhoud> nationaliteitStapel) {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(VerplichteStapel.createPersoonStapel());
        builder.inschrijvingStapel(VerplichteStapel.createInschrijvingStapel());
        builder.verblijfplaatsStapel(VerplichteStapel.createVerblijfplaatsStapel());
        builder.ouder1Stapel(VerplichteStapel.createOuder1Stapel());
        builder.ouder2Stapel(VerplichteStapel.createOuder2Stapel());
        builder.nationaliteitStapel(nationaliteitStapel);

        return builder.build();
    }
}
