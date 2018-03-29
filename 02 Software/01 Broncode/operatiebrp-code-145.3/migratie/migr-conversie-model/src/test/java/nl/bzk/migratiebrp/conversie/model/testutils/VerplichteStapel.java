/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.testutils;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingNaamgebruikCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AangifteAdreshoudingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3FunctieAdresEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

/**
 * Utilities om verplichte stapels te maken.
 */
public final class VerplichteStapel {

    public static final int GEBOORTE_DATUM = 19900101;
    public static final String STRING_0518 = "0518";

    private VerplichteStapel() {
        throw new AssertionError("Niet instantieerbaar");
    }

    public static Lo3Stapel<Lo3PersoonInhoud> createPersoonStapel() {
        final List<Lo3Categorie<Lo3PersoonInhoud>> categorieen = new ArrayList<>();
        categorieen.add(buildPersoon("1000000000", "Klaas", "Jansen", GEBOORTE_DATUM, "0363", null, 19950101, 19950110, 66, STRING_0518, "3A"));
        return StapelUtils.createStapel(categorieen);
    }

    public static Lo3Stapel<Lo3OuderInhoud> createOuder1Stapel() {
        return createOuderStapel(Lo3CategorieEnum.CATEGORIE_02);
    }

    public static Lo3Stapel<Lo3OuderInhoud> createOuder2Stapel() {
        return createOuderStapel(Lo3CategorieEnum.CATEGORIE_03);
    }

    public static Lo3Stapel<Lo3OuderInhoud> createOuderStapel(final Lo3CategorieEnum categorie) {
        return createOuderStapel("1000000000", categorie);
    }

    public static Lo3Stapel<Lo3OuderInhoud> createOuderStapel(final String anummer, final Lo3CategorieEnum categorie) {
        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(createOuder(anummer, categorie));
        return new Lo3Stapel<>(ouders);
    }

    /**
     * @return een ouder
     */
    public static Lo3Categorie<Lo3OuderInhoud> createOuder(final String anummer, final Lo3CategorieEnum categorie) {
        return createOuder(anummer, new Lo3Herkomst(categorie, 0, 0), new Lo3Datum(GEBOORTE_DATUM));
    }

    /**
     * @return een ouder
     */
    public static Lo3Categorie<Lo3OuderInhoud> createOuder(final String anummer, final String geslachtsNaam, final Lo3CategorieEnum categorie) {
        return createOuder(anummer, geslachtsNaam, new Lo3Herkomst(categorie, 0, 0), new Lo3Datum(GEBOORTE_DATUM));
    }

    /**
     * @return een ouder
     */
    public static Lo3Categorie<Lo3OuderInhoud> createOuder(final String anummer, final Lo3Herkomst herkomst, final Lo3Datum ouderschapsDatum) {
        final Lo3OuderInhoud inhoud = createOuderInhoud(anummer, ouderschapsDatum);
        final Lo3Historie historie = new Lo3Historie(null, ouderschapsDatum, ouderschapsDatum);
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(-2000, new Lo3GemeenteCode(STRING_0518), Lo3String.wrap("1OuderAkte"), null, null, null, null, null);

        return new Lo3Categorie<>(inhoud, documentatie, null, historie, herkomst);
    }

    /**
     * @return een ouder
     */
    public static Lo3Categorie<Lo3OuderInhoud> createOuder(final String anummer, final String geslachtsNaam, final Lo3Herkomst herkomst, final Lo3Datum
            ouderschapsDatum) {
        return createOuder(anummer, geslachtsNaam, herkomst, ouderschapsDatum, ouderschapsDatum);
    }

    /**
     * @return een ouder
     */
    public static Lo3Categorie<Lo3OuderInhoud> createOuder(final String anummer, final String geslachtsNaam, final Lo3Herkomst herkomst, final Lo3Datum
            ouderschapsDatum, final Lo3Datum ingangsDatum) {
        final Lo3OuderInhoud inhoud = createOuderInhoud(anummer, geslachtsNaam, ouderschapsDatum);
        final Lo3Historie historie = new Lo3Historie(null, ingangsDatum, ingangsDatum);
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(-2000, new Lo3GemeenteCode(STRING_0518), Lo3String.wrap("1OuderAkte"), null, null, null, null, null);

        return new Lo3Categorie<>(inhoud, documentatie, null, historie, herkomst);
    }

    /**
     * @return een ouder inhoud
     */
    public static Lo3OuderInhoud createOuderInhoud(final String anummer) {
        return createOuderInhoud(anummer, new Lo3Datum(GEBOORTE_DATUM));
    }

    /**
     * @return een ouder inhoud
     */
    public static Lo3OuderInhoud createOuderInhoud(final String anummer, final Lo3Datum ouderschapsDatum) {
        return new Lo3OuderInhoud(
                Lo3String.wrap(anummer),
                Lo3String.wrap("123456789"),
                null,
                null,
                null,
                Lo3String.wrap("geslachtsnaam"),
                new Lo3Datum(20120101),
                new Lo3GemeenteCode("1234"),
                new Lo3LandCode("1234"),
                Lo3GeslachtsaanduidingEnum.MAN.asElement(),
                ouderschapsDatum);
    }

    /**
     * @return een ouder inhoud
     */
    public static Lo3OuderInhoud createOuderInhoud(final String anummer, final String geslachtsNaam, final Lo3Datum ouderschapsDatum) {
        return new Lo3OuderInhoud(
                Lo3String.wrap(anummer),
                Lo3String.wrap("123456789"),
                null,
                null,
                null,
                Lo3String.wrap(geslachtsNaam),
                new Lo3Datum(20120101),
                new Lo3GemeenteCode("1234"),
                new Lo3LandCode("1234"),
                Lo3GeslachtsaanduidingEnum.MAN.asElement(),
                ouderschapsDatum);
    }

    public static Lo3Stapel<Lo3VerblijfplaatsInhoud> createVerblijfplaatsStapel() {
        final List<Lo3Categorie<Lo3VerblijfplaatsInhoud>> verblijfplaatsen = new ArrayList<>();
        verblijfplaatsen.add(createVerblijfplaats());
        return new Lo3Stapel<>(verblijfplaatsen);
    }

    /**
     * @return een verblijfplaats
     */
    public static Lo3Categorie<Lo3VerblijfplaatsInhoud> createVerblijfplaats() {
        final Lo3VerblijfplaatsInhoud inhoud = createVerblijfplaatsInhoud();
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(20120101), new Lo3Datum(20120101));
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(-3000, new Lo3GemeenteCode(STRING_0518), Lo3String.wrap("1VerblijfAkte"), null, null, null, null, null);

        return new Lo3Categorie<>(inhoud, documentatie, null, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0));
    }

    /**
     * @return een verblijfplaats inhoud
     */
    public static Lo3VerblijfplaatsInhoud createVerblijfplaatsInhoud() {
        return new Lo3VerblijfplaatsInhoud(
                new Lo3GemeenteCode("1234"),
                new Lo3Datum(20120101),
                Lo3FunctieAdresEnum.WOONADRES.asElement(),
                null,
                new Lo3Datum(20120101),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                Lo3String.wrap("locatieBeschrijving"),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                Lo3AangifteAdreshoudingEnum.AMBSTHALVE.asElement(),
                null);
    }

    public static Lo3Stapel<Lo3InschrijvingInhoud> createInschrijvingStapel() {
        final List<Lo3Categorie<Lo3InschrijvingInhoud>> categorieen = new ArrayList<>();
        categorieen.add(createInschrijving());

        return new Lo3Stapel<>(categorieen);
    }

    public static Lo3Categorie<Lo3InschrijvingInhoud> createInschrijving() {
        final Lo3InschrijvingInhoud inhoud = createInschrijvingInhoud();
        final Lo3Historie historie = new Lo3Historie(null, null, null);
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(-1000, new Lo3GemeenteCode(STRING_0518), Lo3String.wrap("1Inschr-Akte"), null, null, null, null, null);

        return new Lo3Categorie<>(inhoud, documentatie, null, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0));
    }

    public static Lo3InschrijvingInhoud createInschrijvingInhoud() {
        return new Lo3InschrijvingInhoud(
                null,
                null,
                null,
                new Lo3Datum(18000101),
                null,
                null,
                null,
                null,
                new Lo3Integer(1),
                new Lo3Datumtijdstempel(18000101120000000L),
                null);
    }

    public static Lo3Stapel<Lo3InschrijvingInhoud> createInschrijvingStapelOpgeschort() {
        final List<Lo3Categorie<Lo3InschrijvingInhoud>> categorieen = new ArrayList<>();
        categorieen.add(createInschrijvingOpgeschort());

        return new Lo3Stapel<>(categorieen);
    }

    public static Lo3Categorie<Lo3InschrijvingInhoud> createInschrijvingOpgeschort() {
        final Lo3InschrijvingInhoud inhoud = createInschrijvingInhoudOpgeschort();
        final Lo3Historie historie = new Lo3Historie(null, null, null);
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(-1000, new Lo3GemeenteCode(STRING_0518), Lo3String.wrap("1Inschr-Akte"), null, null, null, null, null);

        return new Lo3Categorie<>(inhoud, documentatie, null, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0));
    }

    public static Lo3InschrijvingInhoud createInschrijvingInhoudOpgeschort() {
        return new Lo3InschrijvingInhoud(
                null,
                new Lo3Datum(19000202),
                Lo3RedenOpschortingBijhoudingCodeEnum.FOUT.asElement(),
                new Lo3Datum(18000101),
                null,
                null,
                null,
                null,
                new Lo3Integer(1),
                new Lo3Datumtijdstempel(18000101120000000L),
                null);
    }

    public static Lo3Categorie<Lo3PersoonInhoud> buildPersoon(
            final String anummer,
            final String voornamen,
            final String geslachtsnaam,
            final Integer geboortedatum,
            final String gemeenteCodeGeboorte,
            final Lo3IndicatieOnjuist indicatieOnjuist,
            final Integer ingangsdatumGeldigheid,
            final Integer datumVanOpneming,
            final Integer documentId,
            final String gemeenteCodeAkte,
            final String nummerAkte) {
        final Lo3PersoonInhoud inhoud =
                new Lo3PersoonInhoud(
                        Lo3String.wrap(anummer),
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

        return new Lo3Categorie<>(inhoud, documentatie, null, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0));
    }
}
