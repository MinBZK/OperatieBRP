/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingNaamgebruikCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieOnjuistEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.proces.AbstractConversieServiceTest;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.testutils.StapelUtils;
import nl.moderniseringgba.migratie.testutils.VerplichteStapel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Lo3HistorieConversieTest extends AbstractConversieServiceTest {

    private static final Lo3IndicatieOnjuist ONJUIST = Lo3IndicatieOnjuistEnum.ONJUIST.asElement();

    // CHECKSTYLE:OFF - Parameters
    private static Lo3Categorie<Lo3PersoonInhoud> buildPersoon(
    // CHECKSTYLE:ON
            final Long anummer,
            final String voornamen,
            final String geslachtsnaam,
            final Integer geboortedatum,
            final String gemeenteCodeGeboorte,
            final Lo3IndicatieOnjuist indicatieOnjuist,
            final Integer ingangsdatumGeldigheid,
            final Integer datumVanOpneming,
            final Integer documentId,
            final String gemeenteCodeAkte,
            final String nummerAkte,
            final Lo3Herkomst herkomst) {

        final Lo3PersoonInhoud inhoud =
                new Lo3PersoonInhoud(anummer, null, voornamen, null, null, geslachtsnaam,
                        new Lo3Datum(geboortedatum), new Lo3GemeenteCode(gemeenteCodeGeboorte),
                        Lo3LandCode.NEDERLAND, Lo3GeslachtsaanduidingEnum.MAN.asElement(),
                        Lo3AanduidingNaamgebruikCodeEnum.EIGEN_GESLACHTSNAAM.asElement(), null, null);
        // inhoud.valideer();

        final Lo3Historie historie =
                new Lo3Historie(indicatieOnjuist, new Lo3Datum(ingangsdatumGeldigheid),
                        new Lo3Datum(datumVanOpneming));
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(documentId, new Lo3GemeenteCode(gemeenteCodeAkte), nummerAkte, null, null, null,
                        null, null);

        return new Lo3Categorie<Lo3PersoonInhoud>(inhoud, documentatie, historie, herkomst);
    }

    private Lo3Stapel<Lo3PersoonInhoud> buildPersoonStapel() {
        final List<Lo3Categorie<Lo3PersoonInhoud>> categorieen = new ArrayList<Lo3Categorie<Lo3PersoonInhoud>>();

        // @formatter:off
        // CHECKSTYLE:OFF magic numbers in de volgnummer 0-5
        categorieen.add(buildPersoon(1000000000L, "Klaas", "Jansen", 19900101, "0363", null, 20000101, 20000602, 6,
                "0518", "Naamsw-1", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_01, 0, 0)));
        categorieen.add(buildPersoon(1000000000L, "Klaas", "Jansen", 19900101, "0599", ONJUIST, 20000101, 20000102,
                5, "0518", "Naamsw-1", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_01, 0, 1)));
        categorieen.add(buildPersoon(1000000000L, "Henk", "Jansen", 19900101, "0363", null, 19950101, 20010103, 4,
                "0518", "Naamsw-2b", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_01, 0, 2)));
        categorieen.add(buildPersoon(1000000000L, "Hank", "Jansen", 19900101, "0363", ONJUIST, 19950101, 20010102, 3,
                "0518", "Naamsw-2a", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_01, 0, 3)));
        categorieen.add(buildPersoon(1000000000L, "Jan", "Jansen", 19900101, "0363", null, 19900101, 20000602, 2,
                "0518", "Geb.akte-1b", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_01, 0, 4)));
        categorieen.add(buildPersoon(1000000000L, "Jan", "Jansen", 19900101, "0599", ONJUIST, 19900101, 19900102, 1,
                "0518", "Geb.akte-1a", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_01, 0, 5)));
        // @formatter:on
        // CHECKSTYLE:ON
        final Lo3Stapel<Lo3PersoonInhoud> stapel = StapelUtils.createStapel(categorieen);
        // stapel.valideer();

        return stapel;
    }

    // CHECKSTYLE:OFF - Parameters
    private Lo3Categorie<Lo3NationaliteitInhoud> buildNationaliteit(
    // CHECKSTYLE:ON
            final String nationaliteit,
            final Lo3IndicatieOnjuist indicatieOnjuist,
            final Integer ingangsdatumGeldigheid,
            final Integer datumVanOpneming,
            final Integer documentId,
            final String gemeenteCodeAkte,
            final String nummerAkte,
            final Lo3Herkomst herkomst) {

        final Lo3NationaliteitInhoud inhoud =
                new Lo3NationaliteitInhoud(nationaliteit == null ? null : new Lo3NationaliteitCode(nationaliteit),
                        null, null, null);

        // inhoud.valideer();

        final Lo3Historie historie =
                new Lo3Historie(indicatieOnjuist, new Lo3Datum(ingangsdatumGeldigheid),
                        new Lo3Datum(datumVanOpneming));
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(documentId, new Lo3GemeenteCode(gemeenteCodeAkte), nummerAkte, null, null, null,
                        null, null);

        return new Lo3Categorie<Lo3NationaliteitInhoud>(inhoud, documentatie, historie, herkomst);
    }

    private Lo3Stapel<Lo3NationaliteitInhoud> buildNationaliteitStapelCasus2() {
        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3NationaliteitInhoud>>();

        // @formatter:off
        //CHECKSTYLE:OFF magic numbers voor de stapel aanduiding
        categorieen.add(buildNationaliteit(null, null, 19941231, 19950114, 14, "0518", "Verlies-1c", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_08, 0 , 0)));
        categorieen.add(buildNationaliteit(null, ONJUIST, 19950110, 19950112, 13, "0518", "Verlies-1b", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_08, 0 , 1)));
        categorieen.add(buildNationaliteit(null, ONJUIST, 19950101, 19950103, 12, "0518", "Verlies-1a", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_08, 0 , 2)));
        categorieen.add(buildNationaliteit("0055", null, 19900101, 19900103, 11, "0518", "Verkrijging-1", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_08, 0 , 3)));
        // @formatter:on
        // CHECKSTYLE:ON

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = StapelUtils.createStapel(categorieen);
        // stapel.valideer();

        return stapel;
    }

    private Lo3Stapel<Lo3NationaliteitInhoud> buildNationaliteitStapelCasus3() {
        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3NationaliteitInhoud>>();

        // @formatter:off
        //CHECKSTYLE:OFF magic numbers voor de stapel aanduiding
        categorieen.add(buildNationaliteit(null, null, 19900101, 19950105, 22, "0518", "Verkrijging-1b", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_08, 0 , 0)));
        categorieen.add(buildNationaliteit("0001", ONJUIST, 19900101, 19900103, 21, "0518", "Verkrijging-1a", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_08, 0 , 1)));
        // CHECKSTYLE:ON
        // @formatter:on

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = StapelUtils.createStapel(categorieen);
        // stapel.valideer();

        return stapel;
    }

    private Lo3Stapel<Lo3NationaliteitInhoud> buildNationaliteitStapelCasus36() {
        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3NationaliteitInhoud>>();

        // @formatter:off
        //CHECKSTYLE:OFF magic numbers voor de stapel aanduiding
        categorieen.add(buildNationaliteit("0036", null, 20050101, 20080102, 46, "0518", "A6", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_08, 0 , 0)));
        categorieen.add(buildNationaliteit("0036", null, 19900101, 20070102, 45, "0518", "A5", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_08, 0 , 1)));
        categorieen.add(buildNationaliteit(null, null, 20000101, 20060110, 44, "0518", "A4", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_08, 0 , 2)));
        categorieen.add(buildNationaliteit(null, ONJUIST, 20060101, 20060102, 43, "0518", "A3", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_08, 0 , 3)));
        categorieen.add(buildNationaliteit("0036", ONJUIST, 19920101, 20040110, 42, "0518", "A2", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_08, 0 , 4)));
        categorieen.add(buildNationaliteit("0036", ONJUIST, 20040101, 20040102, 41, "0518", "A1", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_08, 0 , 5)));
        // CHECKSTYLE:ON
        // @formatter:on

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = StapelUtils.createStapel(categorieen);
        // stapel.valideer();

        return stapel;
    }

    // CHECKSTYLE:OFF - Parameters
    private Lo3Categorie<Lo3HuwelijkOfGpInhoud> buildHuwelijk(
    // CHECKSTYLE:ON
            final Integer datumSluiting,
            final String gemeenteSluiting,
            final Integer datumOntbinding,
            final String gemeenteOntbinding,
            final Lo3IndicatieOnjuist indicatieOnjuist,
            final Integer ingangsdatumGeldigheid,
            final Integer datumVanOpneming,
            final Integer documentId,
            final String gemeenteCodeAkte,
            final String nummerAkte,
            final Lo3Herkomst herkomst) {

        final Lo3Datum lo3DatumSluiting = datumSluiting == null ? null : new Lo3Datum(datumSluiting);

        final Lo3GemeenteCode lo3GemeenteSluiting =
                gemeenteSluiting == null ? null : new Lo3GemeenteCode(gemeenteSluiting);
        final Lo3Datum lo3DatumOntbinding = datumOntbinding == null ? null : new Lo3Datum(datumOntbinding);
        final Lo3GemeenteCode lo3GemeenteOntbinding =
                gemeenteOntbinding == null ? null : new Lo3GemeenteCode(gemeenteOntbinding);

        final Lo3LandCode lo3LandOntbinding = lo3GemeenteOntbinding == null ? null : Lo3LandCode.NEDERLAND;
        final Lo3LandCode lo3LandSluiting = lo3GemeenteSluiting == null ? null : Lo3LandCode.NEDERLAND;
        final Lo3HuwelijkOfGpInhoud inhoud =
                new Lo3HuwelijkOfGpInhoud(2000000000L, null, "Jessica", null, null, "Pietersen", null, null, null,
                        Lo3GeslachtsaanduidingEnum.VROUW.asElement(), lo3DatumSluiting, lo3GemeenteSluiting,
                        lo3LandSluiting, lo3DatumOntbinding, lo3GemeenteOntbinding, lo3LandOntbinding, null,
                        Lo3SoortVerbintenisEnum.HUWELIJK.asElement());

        // inhoud.valideer();

        final Lo3Historie historie =
                new Lo3Historie(indicatieOnjuist, new Lo3Datum(ingangsdatumGeldigheid),
                        new Lo3Datum(datumVanOpneming));
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(documentId, new Lo3GemeenteCode(gemeenteCodeAkte), nummerAkte, null, null, null,
                        null, null);

        return new Lo3Categorie<Lo3HuwelijkOfGpInhoud>(inhoud, documentatie, historie, herkomst);
    }

    private Lo3Stapel<Lo3HuwelijkOfGpInhoud> buildHuwelijkStapel() {
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3HuwelijkOfGpInhoud>>();

        // @formatter:off
        //CHECKSTYLE:OFF magic number stapelnummering
        categorieen.add(buildHuwelijk(null, null, null, null, null, 19900201, 19950105, 34, "0518",
                "Correctie-1", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_05, 0, 0)));
        categorieen.add(buildHuwelijk(null, null, 19950103, "0599", ONJUIST, 19950103, 19950104, 33, "0518",
                "Ontbinding-1", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_05, 0, 1)));
        categorieen.add(buildHuwelijk(19900201, "0599", null, null, ONJUIST, 19900201, 19900203, 32, "0518",
                "Sluiting-1b", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_05, 0, 2)));
        categorieen.add(buildHuwelijk(19900201, "0518", null, null, ONJUIST, 19900201, 19900202, 31, "0518",
                "Sluiting-1a", new Lo3Herkomst (Lo3CategorieEnum.CATEGORIE_05, 0, 3)));
        // CHECKSTYLE:ON
        // @formatter:on

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel = StapelUtils.createStapel(categorieen);
        // stapel.valideer();

        return stapel;

    }

    @Before
    public void setUp() {
        Logging.initContext();
    }

    @After
    public void tearDown() {
        Logging.destroyContext();
    }

    @Test
    public void test() throws InputValidationException {
        System.out.println("test()");

        System.out.println("build");
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(buildPersoonStapel());
        builder.inschrijvingStapel(VerplichteStapel.createInschrijvingStapel());
        builder.verblijfplaatsStapel(VerplichteStapel.createVerblijfplaatsStapel());
        builder.nationaliteitStapel(buildNationaliteitStapelCasus2());
        builder.nationaliteitStapel(buildNationaliteitStapelCasus3());
        builder.nationaliteitStapel(buildNationaliteitStapelCasus36());
        builder.huwelijkOfGpStapel(buildHuwelijkStapel());
        builder.ouder1Stapel(VerplichteStapel.createOuderStapel());
        builder.ouder2Stapel(VerplichteStapel.createOuderStapel());

        final Lo3Persoonslijst lo3Persoonslijst = builder.build();

        System.out.println("convert");

        conversieService.converteerLo3Persoonslijst(lo3Persoonslijst);
        // TODO echte test maken ipv alleen System.out.println
    }
}
