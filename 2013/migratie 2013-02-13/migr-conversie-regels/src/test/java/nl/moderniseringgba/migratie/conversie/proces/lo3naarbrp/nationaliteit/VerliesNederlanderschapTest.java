/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.nationaliteit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.proces.AbstractConversieServiceTest;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.testutils.VerplichteStapel;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test om de speciale casus van reden verkrijging en reden verlies nederlanderschap te testen bij het converteren van
 * nationaliteit.
 * 
 */

public class VerliesNederlanderschapTest extends AbstractConversieServiceTest {

    @Test
    public void testSimple() throws InputValidationException {
        // Input
        // Logging
        Logging.initContext();

        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3NationaliteitInhoud>>();

        categorieen.add(buildNationaliteit("0001", "017", null, null, 19000101, 19000102, 1, "0518", "Verkrijging-1",
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0)));
        categorieen.add(buildNationaliteit(null, null, "087", null, 19960101, 19960101, 1, "0518", "Verkrijging-1",
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 1)));

        final Lo3Stapel<Lo3NationaliteitInhoud> lo3 = new Lo3Stapel<Lo3NationaliteitInhoud>(categorieen);
        final BrpStapel<BrpNationaliteitInhoud> brp = test(lo3);

        Assert.assertEquals(1, brp.size());
        final BrpGroep<BrpNationaliteitInhoud> groep = brp.get(0);

        // Inhoud
        Assert.assertNotNull(groep.getInhoud());
        Assert.assertNotNull(groep.getInhoud().getRedenVerkrijgingNederlandschapCode());
        Assert.assertEquals(new BigDecimal("017"), groep.getInhoud().getRedenVerkrijgingNederlandschapCode()
                .getNaam());
        Assert.assertNotNull(groep.getInhoud().getRedenVerliesNederlandschapCode());
        Assert.assertEquals(new BigDecimal("087"), groep.getInhoud().getRedenVerliesNederlandschapCode().getNaam());

        // Historie
        Assert.assertNotNull(groep.getHistorie());
        Assert.assertEquals(new BrpDatum(19000101), groep.getHistorie().getDatumAanvangGeldigheid());
        Assert.assertEquals(new BrpDatum(19960101), groep.getHistorie().getDatumEindeGeldigheid());

        // Acties
        Assert.assertNotNull(groep.getActieInhoud());
        Assert.assertNotNull(groep.getActieGeldigheid());
        Logging.destroyContext();
    }

    private BrpStapel<BrpNationaliteitInhoud> test(final Lo3Stapel<Lo3NationaliteitInhoud> nationaliteitStapel)
            throws InputValidationException {
        final Lo3Persoonslijst lo3 = buildLo3Persoonslijst(nationaliteitStapel);
        final BrpPersoonslijst brp = conversieService.converteerLo3Persoonslijst(lo3);

        Assert.assertNotNull(brp);
        Assert.assertNotNull(brp.getNationaliteitStapels());
        Assert.assertEquals(1, brp.getNationaliteitStapels().size());
        return brp.getNationaliteitStapels().get(0);
    }

    // CHECKSTYLE:OFF - Parameters
    private static Lo3Categorie<Lo3NationaliteitInhoud> buildNationaliteit(
    // CHECKSTYLE:ON
            final String nationaliteit,
            final String redenVerkrijging,
            final String redenVerlies,
            final Lo3IndicatieOnjuist indicatieOnjuist,
            final Integer ingangsdatumGeldigheid,
            final Integer datumVanOpneming,
            final Integer documentId,
            final String gemeenteCodeAkte,
            final String nummerAkte,
            final Lo3Herkomst herkomst) {

        final Lo3NationaliteitInhoud inhoud =
                new Lo3NationaliteitInhoud(nationaliteit == null ? null : new Lo3NationaliteitCode(nationaliteit),
                        redenVerkrijging == null ? null : new Lo3RedenNederlandschapCode(redenVerkrijging),
                        redenVerlies == null ? null : new Lo3RedenNederlandschapCode(redenVerlies), null);

        // inhoud.valideer();

        final Lo3Historie historie =
                new Lo3Historie(indicatieOnjuist, new Lo3Datum(ingangsdatumGeldigheid),
                        new Lo3Datum(datumVanOpneming));
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(documentId, new Lo3GemeenteCode(gemeenteCodeAkte), nummerAkte, null, null, null,
                        null, null);

        return new Lo3Categorie<Lo3NationaliteitInhoud>(inhoud, documentatie, historie, herkomst);
    }

    private static Lo3Persoonslijst
            buildLo3Persoonslijst(final Lo3Stapel<Lo3NationaliteitInhoud> nationaliteitStapel) {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(VerplichteStapel.createPersoonStapel());
        builder.inschrijvingStapel(VerplichteStapel.createInschrijvingStapel());
        builder.verblijfplaatsStapel(VerplichteStapel.createVerblijfplaatsStapel());
        builder.ouder1Stapel(VerplichteStapel.createOuderStapel());
        builder.ouder2Stapel(VerplichteStapel.createOuderStapel());
        builder.nationaliteitStapel(nationaliteitStapel);

        return builder.build();
    }
}
