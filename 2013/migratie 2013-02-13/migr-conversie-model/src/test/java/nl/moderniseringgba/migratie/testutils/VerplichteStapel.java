/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.testutils;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingNaamgebruikCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AangifteAdreshoudingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3FunctieAdresEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;

/**
 * Utilities om verplichte stapels te maken.
 */
public final class VerplichteStapel {

    public static final int GEBOORTE_DATUM = 19900101;

    private VerplichteStapel() {
        throw new AssertionError("Niet instantieerbaar");
    }

    public static Lo3Stapel<Lo3PersoonInhoud> createPersoonStapel() {
        final List<Lo3Categorie<Lo3PersoonInhoud>> categorieen = new ArrayList<Lo3Categorie<Lo3PersoonInhoud>>();
        categorieen.add(buildPersoon(1000000000L, "Klaas", "Jansen", GEBOORTE_DATUM, "0363", null, 19950101,
                19950110, 6, "0518", "A3"));
        return StapelUtils.createStapel(categorieen);
    }

    public static Lo3Stapel<Lo3OuderInhoud> createOuderStapel() {
        return createOuderStapel(1000000000L);
    }

    public static Lo3Stapel<Lo3OuderInhoud> createOuderStapel(final long anummer) {
        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<Lo3Categorie<Lo3OuderInhoud>>();
        ouders.add(createOuder(anummer));
        return new Lo3Stapel<Lo3OuderInhoud>(ouders);
    }

    /**
     * @return
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public static Lo3Categorie<Lo3OuderInhoud> createOuder(final long anummer) {
        return createOuder(anummer, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 0));
    }

    /**
     * @return
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public static Lo3Categorie<Lo3OuderInhoud> createOuder(final long anummer, final Lo3Herkomst herkomst) {
        final Lo3OuderInhoud inhoud = createOuderInhoud(anummer);
        final Lo3Historie historie =
                new Lo3Historie(null, new Lo3Datum(GEBOORTE_DATUM), new Lo3Datum(GEBOORTE_DATUM));
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(-2000, new Lo3GemeenteCode("0518"), "Ouder-Akte", null, null, null, null, null);

        return new Lo3Categorie<Lo3OuderInhoud>(inhoud, documentatie, historie, herkomst);
    }

    /**
     * @return
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public static Lo3OuderInhoud createOuderInhoud(final long anummer) {
        return new Lo3OuderInhoud(anummer, 123456789L, null, null, null, "geslachtsnaam", new Lo3Datum(20120101),
                new Lo3GemeenteCode("1234"), new Lo3LandCode("1234"), Lo3GeslachtsaanduidingEnum.MAN.asElement(),
                new Lo3Datum(GEBOORTE_DATUM));
    }

    public static Lo3Stapel<Lo3VerblijfplaatsInhoud> createVerblijfplaatsStapel() {
        final List<Lo3Categorie<Lo3VerblijfplaatsInhoud>> verblijfplaatsen =
                new ArrayList<Lo3Categorie<Lo3VerblijfplaatsInhoud>>();
        verblijfplaatsen.add(createVerblijfplaats());
        return new Lo3Stapel<Lo3VerblijfplaatsInhoud>(verblijfplaatsen);
    }

    /**
     * @return
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public static Lo3Categorie<Lo3VerblijfplaatsInhoud> createVerblijfplaats() {
        final Lo3VerblijfplaatsInhoud inhoud = createVerblijfplaatsInhoud();
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(20120101), new Lo3Datum(20120101));
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(-3000, new Lo3GemeenteCode("0518"), "Verblijf-Akte", null, null, null, null, null);

        return new Lo3Categorie<Lo3VerblijfplaatsInhoud>(inhoud, documentatie, historie, new Lo3Herkomst(
                Lo3CategorieEnum.CATEGORIE_08, 0, 0));
    }

    /**
     * @return
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public static Lo3VerblijfplaatsInhoud createVerblijfplaatsInhoud() {
        return new Lo3VerblijfplaatsInhoud(new Lo3GemeenteCode("1234"), new Lo3Datum(20120101),
                Lo3FunctieAdresEnum.WOONADRES.asElement(), null, new Lo3Datum(20120101), null, null, null, null,
                null, null, null, null, null, null, "locatieBeschrijving", null, null, null, null, null, null, null,
                Lo3AangifteAdreshoudingEnum.AMBSTHALVE.asElement(), null);
    }

    public static Lo3Stapel<Lo3InschrijvingInhoud> createInschrijvingStapel() {
        final List<Lo3Categorie<Lo3InschrijvingInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3InschrijvingInhoud>>();
        categorieen.add(createInschrijving());

        return new Lo3Stapel<Lo3InschrijvingInhoud>(categorieen);
    }

    public static Lo3Categorie<Lo3InschrijvingInhoud> createInschrijving() {
        final Lo3InschrijvingInhoud inhoud = createInschrijvingInhoud();
        final Lo3Historie historie = Lo3Historie.NULL_HISTORIE;
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(-1000, new Lo3GemeenteCode("0518"), "Inschr-Akte", null, null, null, null, null);

        return new Lo3Categorie<Lo3InschrijvingInhoud>(inhoud, documentatie, historie, new Lo3Herkomst(
                Lo3CategorieEnum.CATEGORIE_07, 0, 0));
    }

    public static Lo3InschrijvingInhoud createInschrijvingInhoud() {
        return new Lo3InschrijvingInhoud(null, null, null, new Lo3Datum(18000101), null, null, 1,
                new Lo3Datumtijdstempel(18000101120000000L), null);
    }

    // CHECKSTYLE:OFF - meer dan 7 parameters
    public static Lo3Categorie<Lo3PersoonInhoud> buildPersoon(
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
            final String nummerAkte) {
        // CHECKSTYLE:ON

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

        return new Lo3Categorie<Lo3PersoonInhoud>(inhoud, documentatie, historie, new Lo3Herkomst(
                Lo3CategorieEnum.CATEGORIE_01, 0, 0));
    }
}
