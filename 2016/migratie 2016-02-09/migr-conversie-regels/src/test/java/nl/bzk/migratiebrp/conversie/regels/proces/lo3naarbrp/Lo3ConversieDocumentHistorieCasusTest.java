/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingNaamgebruikCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieOnjuistEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.testutils.StapelUtils;
import nl.bzk.migratiebrp.conversie.model.testutils.VerplichteStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.casus.AbstractCasusTest;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.casus.BrpTestObject;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test op basis van de voorbeelden in het Bidirectionele Conversie Document, Hoofdstuk 7 Historie.
 */
public class Lo3ConversieDocumentHistorieCasusTest extends AbstractCasusTest {

    public static final String VOORNAAM_KLAAS = "Klaas";
    public static final String GESLACHTSNAAM_JANSEN = "Jansen";
    public static final String GEMEENTE_CODE_0363 = "0363";
    public static final String GEMEENTE_CODE_0518 = "0518";
    public static final String GEMEENTE_CODE_0599 = "0599";
    public static final String VOORNAAM_JAN = "Jan";

    private static final Comparator<BrpGroep<?>> BRP_GROEPEN_COMPARATOR = new BrpGroepenAkteComparator();
    private static final Lo3IndicatieOnjuist ONJUIST = Lo3IndicatieOnjuistEnum.ONJUIST.asElement();
    private static final Comparator<BrpTestObject<?>> BRP_TESTOBJECT_COMPARATOR = new BrpTestOjbectAkteComparator();

    private static Lo3Categorie<Lo3PersoonInhoud> buildPersoon(
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
        final Lo3Herkomst herkomst)
    {

        final Lo3PersoonInhoud inhoud =
                new Lo3PersoonInhoud(
                    Lo3Long.wrap(anummer),
                    null,
                    Lo3String.wrap(voornamen),
                    null,
                    null,
                    Lo3String.wrap(geslachtsnaam),
                    new Lo3Datum(geboortedatum),
                    new Lo3GemeenteCode(gemeenteCodeGeboorte),
                    Lo3LandCode.NEDERLAND,
                    Lo3GeslachtsaanduidingEnum.MAN.asElement(),
                    null,
                    null,
                    Lo3AanduidingNaamgebruikCodeEnum.EIGEN_GESLACHTSNAAM.asElement());

        final Lo3Historie historie = new Lo3Historie(indicatieOnjuist, new Lo3Datum(ingangsdatumGeldigheid), new Lo3Datum(datumVanOpneming));
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(documentId, new Lo3GemeenteCode(gemeenteCodeAkte), Lo3String.wrap(nummerAkte), null, null, null, null, null);

        return new Lo3Categorie<>(inhoud, documentatie, historie, herkomst);
    }

    private Lo3Stapel<Lo3PersoonInhoud> buildPersoonStapel() {
        final List<Lo3Categorie<Lo3PersoonInhoud>> categorieen = new ArrayList<>();

        categorieen.add(
            buildPersoon(
                1000000000L,
                VOORNAAM_KLAAS,
                GESLACHTSNAAM_JANSEN,
                19900101,
                GEMEENTE_CODE_0363,
                null,
                20000101,
                20000602,
                6,
                GEMEENTE_CODE_0518,
                "Naamsw-1b",
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0)));
        categorieen.add(
            buildPersoon(
                1000000000L,
                VOORNAAM_KLAAS,
                GESLACHTSNAAM_JANSEN,
                19900101,
                GEMEENTE_CODE_0599,
                ONJUIST,
                20000101,
                20000102,
                5,
                GEMEENTE_CODE_0518,
                "Naamsw-1a",
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 1)));
        categorieen.add(
            buildPersoon(
                1000000000L,
                "Henk",
                GESLACHTSNAAM_JANSEN,
                19900101,
                GEMEENTE_CODE_0363,
                null,
                19950101,
                20010103,
                4,
                GEMEENTE_CODE_0518,
                "Naamsw-2b",
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 2)));
        categorieen.add(
            buildPersoon(
                1000000000L,
                "Hank",
                GESLACHTSNAAM_JANSEN,
                19900101,
                GEMEENTE_CODE_0363,
                ONJUIST,
                19950101,
                20010102,
                3,
                GEMEENTE_CODE_0518,
                "Naamsw-2a",
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 3)));
        categorieen.add(
            buildPersoon(
                1000000000L,
                VOORNAAM_JAN,
                GESLACHTSNAAM_JANSEN,
                19900101,
                GEMEENTE_CODE_0363,
                null,
                19900101,
                20000602,
                2,
                GEMEENTE_CODE_0518,
                "Geb.akte-1b",
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 4)));
        categorieen.add(
            buildPersoon(
                1000000000L,
                VOORNAAM_JAN,
                GESLACHTSNAAM_JANSEN,
                19900101,
                GEMEENTE_CODE_0599,
                ONJUIST,
                19900101,
                19900102,
                1,
                GEMEENTE_CODE_0518,
                "Geb.akte-1a",
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 5)));

        return StapelUtils.createStapel(categorieen);
    }

    private Lo3Categorie<Lo3NationaliteitInhoud> buildNationaliteit(
        final String nationaliteit,
        final Lo3IndicatieOnjuist indicatieOnjuist,
        final Integer ingangsdatumGeldigheid,
        final Integer datumVanOpneming,
        final Integer documentId,
        final String gemeenteCodeAkte,
        final String nummerAkte,
        final Lo3Herkomst herkomst)
    {

        final Lo3NationaliteitInhoud inhoud =
                new Lo3NationaliteitInhoud(nationaliteit == null ? null : new Lo3NationaliteitCode(nationaliteit), null, null, null);

        final Lo3Historie historie = new Lo3Historie(indicatieOnjuist, new Lo3Datum(ingangsdatumGeldigheid), new Lo3Datum(datumVanOpneming));
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(documentId, new Lo3GemeenteCode(gemeenteCodeAkte), Lo3String.wrap(nummerAkte), null, null, null, null, null);

        return new Lo3Categorie<>(inhoud, documentatie, historie, herkomst);
    }

    private Lo3Stapel<Lo3NationaliteitInhoud> buildNationaliteitStapelCasus2() {
        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();

        categorieen.add(
            buildNationaliteit(
                null,
                null,
                19941231,
                19950114,
                14,
                GEMEENTE_CODE_0518,
                "Verlies-1c",
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0)));
        categorieen.add(
            buildNationaliteit(
                null,
                ONJUIST,
                19950110,
                19950112,
                13,
                GEMEENTE_CODE_0518,
                "Verlies-1b",
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_58, 0, 1)));
        categorieen.add(
            buildNationaliteit(
                null,
                ONJUIST,
                19950101,
                19950103,
                12,
                GEMEENTE_CODE_0518,
                "Verlies-1a",
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_58, 0, 2)));
        categorieen.add(
            buildNationaliteit(
                "0055",
                null,
                19900101,
                19900103,
                11,
                GEMEENTE_CODE_0518,
                "Verkrijging-1",
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_58, 0, 3)));

        return StapelUtils.createStapel(categorieen);
    }

    private Lo3Stapel<Lo3NationaliteitInhoud> buildNationaliteitStapelCasus3() {
        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();

        categorieen.add(
            buildNationaliteit(
                null,
                null,
                19900101,
                19950105,
                22,
                GEMEENTE_CODE_0518,
                "Verkrijging-1b",
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0)));
        categorieen.add(
            buildNationaliteit(
                "0057",
                ONJUIST,
                19900101,
                19900103,
                21,
                GEMEENTE_CODE_0518,
                "Verkrijging-1a",
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_58, 0, 1)));

        return StapelUtils.createStapel(categorieen);
    }

    private Lo3Categorie<Lo3OverlijdenInhoud> buildOverlijden(
        final Integer datumOverlijden,
        final String gemeenteOverlijden,
        final Lo3IndicatieOnjuist indicatieOnjuist,
        final Integer ingangsdatumGeldigheid,
        final Integer datumVanOpneming,
        final Integer documentId,
        final String gemeenteCodeAkte,
        final String nummerAkte,
        final Lo3Herkomst herkomst)
    {

        final Lo3Datum lo3DatumOverlijden = datumOverlijden == null ? null : new Lo3Datum(datumOverlijden);
        final Lo3GemeenteCode lo3GemeenteOverlijden = gemeenteOverlijden == null ? null : new Lo3GemeenteCode(gemeenteOverlijden);
        final Lo3LandCode lo3LandOverlijden = lo3GemeenteOverlijden == null ? null : Lo3LandCode.NEDERLAND;

        final Lo3OverlijdenInhoud inhoud = new Lo3OverlijdenInhoud(lo3DatumOverlijden, lo3GemeenteOverlijden, lo3LandOverlijden);

        final Lo3Historie historie = new Lo3Historie(indicatieOnjuist, new Lo3Datum(ingangsdatumGeldigheid), new Lo3Datum(datumVanOpneming));
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(documentId, new Lo3GemeenteCode(gemeenteCodeAkte), Lo3String.wrap(nummerAkte), null, null, null, null, null);

        return new Lo3Categorie<>(inhoud, documentatie, historie, herkomst);
    }

    private Lo3Stapel<Lo3OverlijdenInhoud> buildOverlijdenStapel() {
        final List<Lo3Categorie<Lo3OverlijdenInhoud>> categorieen = new ArrayList<>();

        categorieen.add(
            buildOverlijden(
                null,
                null,
                null,
                19900201,
                19950105,
                34,
                GEMEENTE_CODE_0518,
                "Correctie-1",
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_06, 0, 0)));
        categorieen.add(
            buildOverlijden(
                19950103,
                GEMEENTE_CODE_0599,
                ONJUIST,
                19950103,
                19950104,
                33,
                GEMEENTE_CODE_0518,
                "Overlijden-2",
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_56, 0, 1)));
        categorieen.add(
            buildOverlijden(
                19900201,
                GEMEENTE_CODE_0599,
                ONJUIST,
                19900201,
                19900203,
                32,
                GEMEENTE_CODE_0518,
                "Overlijden-1b",
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_56, 0, 2)));
        categorieen.add(
            buildOverlijden(
                19900201,
                GEMEENTE_CODE_0518,
                ONJUIST,
                19900201,
                19900202,
                31,
                GEMEENTE_CODE_0518,
                "Overlijden-1a",
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_56, 0, 3)));

        return StapelUtils.createStapel(categorieen);

    }

    private BrpNationaliteitInhoud buildNationaliteitInhoud(final int code) {
        return new BrpNationaliteitInhoud(new BrpNationaliteitCode((short) code), null, null, null, null, null, null);
    }

    private BrpOverlijdenInhoud buildOverlijdenInhoud(final int datum, final int gemeenteCode) {
        return new BrpOverlijdenInhoud(
            new BrpDatum(datum, null),
            new BrpGemeenteCode((short) gemeenteCode),
            null,
            null,
            null,
            BrpLandOfGebiedCode.NEDERLAND,
            null);
    }

    @Override
    @After
    public void tearDown() {
        Logging.destroyContext();
    }

    private void controleerSamengesteldeNaamCasus1(final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel) {
        final BrpSamengesteldeNaamInhoud brpSamengesteldInh1 =
                new BrpSamengesteldeNaamInhoud(
                    null,
                    new BrpString("Henk", null),
                    null,
                    null,
                    null,
                    new BrpString(GESLACHTSNAAM_JANSEN, null),
                    new BrpBoolean(false, null),
                    new BrpBoolean(true, null));
        final BrpSamengesteldeNaamInhoud brpSamengesteldInh2 =
                new BrpSamengesteldeNaamInhoud(
                    null,
                    new BrpString("Hank", null),
                    null,
                    null,
                    null,
                    new BrpString(GESLACHTSNAAM_JANSEN, null),
                    new BrpBoolean(false, null),
                    new BrpBoolean(true, null));
        final BrpSamengesteldeNaamInhoud brpSamengesteldInh3 =
                new BrpSamengesteldeNaamInhoud(
                    null,
                    new BrpString(VOORNAAM_KLAAS, null),
                    null,
                    null,
                    null,
                    new BrpString(GESLACHTSNAAM_JANSEN, null),
                    new BrpBoolean(false, null),
                    new BrpBoolean(true, null));
        final BrpSamengesteldeNaamInhoud brpSamengesteldInh4 =
                new BrpSamengesteldeNaamInhoud(
                    null,
                    new BrpString(VOORNAAM_JAN, null),
                    null,
                    null,
                    null,
                    new BrpString(GESLACHTSNAAM_JANSEN, null),
                    new BrpBoolean(false, null),
                    new BrpBoolean(true, null));
        final BrpSamengesteldeNaamInhoud brpSamengesteldInh5 =
                new BrpSamengesteldeNaamInhoud(
                    null,
                    new BrpString(VOORNAAM_KLAAS, null),
                    null,
                    null,
                    null,
                    new BrpString(GESLACHTSNAAM_JANSEN, null),
                    new BrpBoolean(false, null),
                    new BrpBoolean(true, null));
        final BrpSamengesteldeNaamInhoud brpSamengesteldInh6 =
                new BrpSamengesteldeNaamInhoud(
                    null,
                    new BrpString(VOORNAAM_JAN, null),
                    null,
                    null,
                    null,
                    new BrpString(GESLACHTSNAAM_JANSEN, null),
                    new BrpBoolean(false, null),
                    new BrpBoolean(true, null));

        final BrpTestObject<BrpSamengesteldeNaamInhoud> brpSamengesteld1 = new BrpTestObject<>();
        final BrpTestObject<BrpSamengesteldeNaamInhoud> brpSamengesteld2 = new BrpTestObject<>();
        final BrpTestObject<BrpSamengesteldeNaamInhoud> brpSamengesteld3 = new BrpTestObject<>();
        final BrpTestObject<BrpSamengesteldeNaamInhoud> brpSamengesteld4 = new BrpTestObject<>();
        final BrpTestObject<BrpSamengesteldeNaamInhoud> brpSamengesteld5 = new BrpTestObject<>();
        final BrpTestObject<BrpSamengesteldeNaamInhoud> brpSamengesteld6 = new BrpTestObject<>();

        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19900102, null), "Geb.akte-1a");
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(20000102, null), "Naamsw-1a");
        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(20000602, null), "Geb.akte-1b");
        final BrpActie actie4 = buildBrpActie(BrpDatumTijd.fromDatum(20000602, null), "Naamsw-1b");
        final BrpActie actie5 = buildBrpActie(BrpDatumTijd.fromDatum(20010102, null), "Naamsw-2a");
        final BrpActie actie6 = buildBrpActie(BrpDatumTijd.fromDatum(20010103, null), "Naamsw-2b");

        brpSamengesteld1.vul(brpSamengesteldInh1, 19950101, 20000101, 20010103010000L, null, null, actie6, actie4, null);
        brpSamengesteld2.vul(brpSamengesteldInh2, 19950101, null, 20010102010000L, 20010102010000L, 'O', actie5, null, actie5);
        brpSamengesteld3.vul(brpSamengesteldInh3, 20000101, null, 20000602010000L, null, null, actie4, null, null);
        brpSamengesteld4.vul(brpSamengesteldInh4, 19900101, 19950101, 20000602010000L, null, null, actie3, actie6, null);
        brpSamengesteld5.vul(brpSamengesteldInh5, 20000101, null, 20000102010000L, 20000102010000L, 'O', actie2, null, actie2);
        brpSamengesteld6.vul(brpSamengesteldInh6, 19900101, null, 19900102010000L, 19900102010000L, 'O', actie1, null, actie1);

        Assert.assertEquals(6, samengesteldeNaamStapel.size());

        final List<BrpGroep<BrpSamengesteldeNaamInhoud>> groepen = samengesteldeNaamStapel.getGroepen();
        Collections.sort(groepen, BRP_GROEPEN_COMPARATOR);
        final List<BrpTestObject<BrpSamengesteldeNaamInhoud>> expected =
                Arrays.asList(brpSamengesteld6, brpSamengesteld5, brpSamengesteld4, brpSamengesteld3, brpSamengesteld2, brpSamengesteld1);
        Collections.sort(expected, BRP_TESTOBJECT_COMPARATOR);
        final Iterator<BrpTestObject<BrpSamengesteldeNaamInhoud>> expectedIterator = expected.iterator();

        for (final BrpGroep<BrpSamengesteldeNaamInhoud> brpSamengesteldeNaamGroep : groepen) {
            final BrpTestObject<BrpSamengesteldeNaamInhoud> expectedWaarde = expectedIterator.next();
            assertSamengesteldeNaam(brpSamengesteldeNaamGroep, expectedWaarde);
        }
    }

    private void controleerGeboorteCasus1(final BrpStapel<BrpGeboorteInhoud> geboorteStapel) {
        final BrpGeboorteInhoud brpGeboorteInhoud1 = buildBrpGeboorteInhoud(19900101, GEMEENTE_CODE_0363);
        final BrpGeboorteInhoud brpGeboorteInhoud2 = buildBrpGeboorteInhoud(19900101, GEMEENTE_CODE_0363);
        final BrpGeboorteInhoud brpGeboorteInhoud3 = buildBrpGeboorteInhoud(19900101, GEMEENTE_CODE_0363);
        final BrpGeboorteInhoud brpGeboorteInhoud4 = buildBrpGeboorteInhoud(19900101, GEMEENTE_CODE_0363);
        final BrpGeboorteInhoud brpGeboorteInhoud5 = buildBrpGeboorteInhoud(19900101, GEMEENTE_CODE_0599);
        final BrpGeboorteInhoud brpGeboorteInhoud6 = buildBrpGeboorteInhoud(19900101, GEMEENTE_CODE_0599);

        final BrpTestObject<BrpGeboorteInhoud> brpGeboorte1 = new BrpTestObject<>();
        final BrpTestObject<BrpGeboorteInhoud> brpGeboorte2 = new BrpTestObject<>();
        final BrpTestObject<BrpGeboorteInhoud> brpGeboorte3 = new BrpTestObject<>();
        final BrpTestObject<BrpGeboorteInhoud> brpGeboorte4 = new BrpTestObject<>();
        final BrpTestObject<BrpGeboorteInhoud> brpGeboorte5 = new BrpTestObject<>();
        final BrpTestObject<BrpGeboorteInhoud> brpGeboorte6 = new BrpTestObject<>();

        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19900102, null), "Geb.akte-1a");
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(20000102, null), "Naamsw-1a");
        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(20000602, null), "Geb.akte-1b");
        final BrpActie actie4 = buildBrpActie(BrpDatumTijd.fromDatum(20000602, null), "Naamsw-1b");
        final BrpActie actie5 = buildBrpActie(BrpDatumTijd.fromDatum(20010102, null), "Naamsw-2a");
        final BrpActie actie6 = buildBrpActie(BrpDatumTijd.fromDatum(20010103, null), "Naamsw-2b");

        brpGeboorte3.vul(brpGeboorteInhoud3, null, null, 20000602010100L, null, null, actie4, null, null);
        brpGeboorte5.vul(brpGeboorteInhoud5, null, null, 20000102010000L, 20000102010000L, 'O', actie2, null, actie2);
        brpGeboorte1.vul(brpGeboorteInhoud1, null, null, 20010103010000L, 20010103010000L, null, actie6, null, actie6);
        brpGeboorte2.vul(brpGeboorteInhoud2, null, null, 20010102010000L, 20010102010000L, 'O', actie5, null, actie5);
        brpGeboorte4.vul(brpGeboorteInhoud4, null, null, 20000602010000L, 20000602010000L, null, actie3, null, actie3);
        brpGeboorte6.vul(brpGeboorteInhoud6, null, null, 19900102010000L, 19900102010000L, 'O', actie1, null, actie1);

        Assert.assertEquals(6, geboorteStapel.size());

        final List<BrpGroep<BrpGeboorteInhoud>> groepen = geboorteStapel.getGroepen();
        Collections.sort(groepen, BRP_GROEPEN_COMPARATOR);
        final List<BrpTestObject<BrpGeboorteInhoud>> expected =
                Arrays.asList(brpGeboorte1, brpGeboorte2, brpGeboorte3, brpGeboorte4, brpGeboorte5, brpGeboorte6);
        Collections.sort(expected, BRP_TESTOBJECT_COMPARATOR);
        final Iterator<BrpTestObject<BrpGeboorteInhoud>> expectedIterator = expected.iterator();

        for (final BrpGroep<BrpGeboorteInhoud> brpGeboorteGroep : groepen) {
            final BrpTestObject<BrpGeboorteInhoud> expectedWaarde = expectedIterator.next();
            assertGeboorte(brpGeboorteGroep, expectedWaarde);
        }
    }

    private void controleerNationaliteitCasus2(final BrpStapel<BrpNationaliteitInhoud> nationaliteitStapel) {
        final BrpNationaliteitInhoud brpNationaliteitInhoud = buildNationaliteitInhoud(55);

        final BrpTestObject<BrpNationaliteitInhoud> brpNationaliteit1 = new BrpTestObject<>();
        final BrpTestObject<BrpNationaliteitInhoud> brpNationaliteit2 = new BrpTestObject<>();
        final BrpTestObject<BrpNationaliteitInhoud> brpNationaliteit3 = new BrpTestObject<>();

        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19900103, null), "Verkrijging-1");
        final BrpActie actie2 =
                buildBrpActie(BrpDatumTijd.fromDatum(19950103, null), "Verlies-1a", BrpSoortActieCode.CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST);
        final BrpActie actie3 =
                buildBrpActie(BrpDatumTijd.fromDatum(19950112, null), "Verlies-1b", BrpSoortActieCode.CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST);
        final BrpActie actie4 = buildBrpActie(BrpDatumTijd.fromDatum(19950114, null), "Verlies-1c");

        brpNationaliteit1.vul(brpNationaliteitInhoud, 19900101, 19941231, 19900103010000L, null, null, actie1, actie4, null);
        brpNationaliteit3.vul(brpNationaliteitInhoud, 19900101, 19950110, 19900103010200L, 19900103010200L, 'O', actie1, actie3, actie1);
        brpNationaliteit2.vul(brpNationaliteitInhoud, 19900101, 19950101, 19900103010100L, 19900103010100L, 'O', actie1, actie2, actie1);

        Assert.assertEquals(3, nationaliteitStapel.size());

        final List<BrpGroep<BrpNationaliteitInhoud>> groepen = nationaliteitStapel.getGroepen();
        Collections.sort(groepen, BRP_GROEPEN_COMPARATOR);
        final List<BrpTestObject<BrpNationaliteitInhoud>> expected = Arrays.asList(brpNationaliteit1, brpNationaliteit2, brpNationaliteit3);
        Collections.sort(expected, BRP_TESTOBJECT_COMPARATOR);
        final Iterator<BrpTestObject<BrpNationaliteitInhoud>> expectedIterator = expected.iterator();

        for (final BrpGroep<BrpNationaliteitInhoud> brpNationaliteitGroep : groepen) {
            final BrpTestObject<BrpNationaliteitInhoud> expectedWaarde = expectedIterator.next();
            assertNationaliteit(brpNationaliteitGroep, expectedWaarde);
        }
    }

    private void controleerNationaliteitCasus3(final BrpStapel<BrpNationaliteitInhoud> nationaliteitStapel) {
        final BrpNationaliteitInhoud brpNationaliteitInhoud = buildNationaliteitInhoud(57);

        final BrpTestObject<BrpNationaliteitInhoud> brpNationaliteit1 = new BrpTestObject<>();

        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19900103, null), "Verkrijging-1a");
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(19950105, null), "Verkrijging-1b");

        brpNationaliteit1.vul(brpNationaliteitInhoud, 19900101, 19900101, 19900103010000L, 19900103010000L, 'O', actie1, actie2, actie1);

        Assert.assertEquals(1, nationaliteitStapel.size());

        assertNationaliteit(nationaliteitStapel.get(0), brpNationaliteit1);
    }

    private void controleerOverlijdenCasus4(final BrpStapel<BrpOverlijdenInhoud> overlijdenStapel) {
        final BrpOverlijdenInhoud brpOverlijdenInhoud1 = buildOverlijdenInhoud(19950103, 599);
        final BrpOverlijdenInhoud brpOverlijdenInhoud2 = buildOverlijdenInhoud(19900201, 599);
        final BrpOverlijdenInhoud brpOverlijdenInhoud3 = buildOverlijdenInhoud(19900201, 518);

        final BrpTestObject<BrpOverlijdenInhoud> brpOverlijden1 = new BrpTestObject<>();
        final BrpTestObject<BrpOverlijdenInhoud> brpOverlijden2 = new BrpTestObject<>();
        final BrpTestObject<BrpOverlijdenInhoud> brpOverlijden3 = new BrpTestObject<>();

        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19900202, null), "Overlijden-1a");
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(19900203, null), "Overlijden-1b");
        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(19950104, null), "Overlijden-2");
        final BrpActie actie4 = buildBrpActie(BrpDatumTijd.fromDatum(19950105, null), "Correctie-1");

        brpOverlijden1.vul(brpOverlijdenInhoud1, null, null, 19950104010000L, 19950104010000L, 'O', actie3, null, actie3);
        brpOverlijden2.vul(brpOverlijdenInhoud2, null, null, 19900203010000L, 19950105010000L, null, actie2, null, actie4);
        brpOverlijden3.vul(brpOverlijdenInhoud3, null, null, 19900202010000L, 19900202010000L, 'O', actie1, null, actie1);

        Assert.assertEquals(3, overlijdenStapel.size());

        final List<BrpGroep<BrpOverlijdenInhoud>> groepen = overlijdenStapel.getGroepen();
        Collections.sort(groepen, BRP_GROEPEN_COMPARATOR);
        final List<BrpTestObject<BrpOverlijdenInhoud>> expected = Arrays.asList(brpOverlijden1, brpOverlijden2, brpOverlijden3);
        Collections.sort(expected, BRP_TESTOBJECT_COMPARATOR);
        final Iterator<BrpTestObject<BrpOverlijdenInhoud>> expectedIterator = expected.iterator();

        for (final BrpGroep<BrpOverlijdenInhoud> brpOverlijdenGroep : groepen) {
            final BrpTestObject<BrpOverlijdenInhoud> expectedWaarde = expectedIterator.next();
            assertOverlijden(brpOverlijdenGroep, expectedWaarde);
        }
    }

    @Test
    @Override
    public void testLo3NaarBrp() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(buildPersoonStapel());
        builder.inschrijvingStapel(VerplichteStapel.createInschrijvingStapel());
        builder.verblijfplaatsStapel(VerplichteStapel.createVerblijfplaatsStapel());
        builder.nationaliteitStapel(buildNationaliteitStapelCasus2());
        builder.nationaliteitStapel(buildNationaliteitStapelCasus3());
        builder.ouder1Stapel(VerplichteStapel.createOuder1Stapel());
        builder.ouder2Stapel(VerplichteStapel.createOuder2Stapel());
        builder.overlijdenStapel(buildOverlijdenStapel());

        final Lo3Persoonslijst lo3Persoonslijst = builder.build();

        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Persoonslijst);

        controleerSamengesteldeNaamCasus1(brpPersoonslijst.getSamengesteldeNaamStapel());
        controleerGeboorteCasus1(brpPersoonslijst.getGeboorteStapel());

        controleerNationaliteitCasus2(vindNationaliteitStapel(55, brpPersoonslijst.getNationaliteitStapels()));
        controleerNationaliteitCasus3(vindNationaliteitStapel(57, brpPersoonslijst.getNationaliteitStapels()));

        controleerOverlijdenCasus4(brpPersoonslijst.getOverlijdenStapel());
    }

    private BrpStapel<BrpNationaliteitInhoud> vindNationaliteitStapel(
        final int nationaliteitCode,
        final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels)
    {
        final BrpNationaliteitCode brpNationaliteitCode = new BrpNationaliteitCode((short) nationaliteitCode);
        for (final BrpStapel<BrpNationaliteitInhoud> nationaliteitStapel : nationaliteitStapels) {
            for (final BrpGroep<BrpNationaliteitInhoud> nationaliteitInhoudBrpGroep : nationaliteitStapel) {
                if (brpNationaliteitCode.equals(nationaliteitInhoudBrpGroep.getInhoud().getNationaliteitCode())) {
                    return nationaliteitStapel;
                }
            }
        }
        throw new NoSuchElementException(String.valueOf(nationaliteitCode));
    }

    @Override
    public void testRondverteer() {

    }

    private static final class BrpGroepenAkteComparator implements Comparator<BrpGroep<?>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpGroep<?> o1, final BrpGroep<?> o2) {
            return getAktenummer(o1).compareTo(getAktenummer(o2));
        }

        private String getAktenummer(final BrpGroep<?> o1) {
            return BrpString.unwrap(o1.getActieInhoud().getActieBronnen().get(0).getDocumentStapel().getActueel().getInhoud().getAktenummer());
        }
    }

    private static class BrpTestOjbectAkteComparator implements Comparator<BrpTestObject<?>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpTestObject<?> o1, final BrpTestObject<?> o2) {
            return getAktenummer(o1).compareTo(getAktenummer(o2));
        }

        private String getAktenummer(final BrpTestObject<?> o1) {
            return BrpString.unwrap(o1.getActieInhoud().getActieBronnen().get(0).getDocumentStapel().getActueel().getInhoud().getAktenummer());
        }
    }
}
