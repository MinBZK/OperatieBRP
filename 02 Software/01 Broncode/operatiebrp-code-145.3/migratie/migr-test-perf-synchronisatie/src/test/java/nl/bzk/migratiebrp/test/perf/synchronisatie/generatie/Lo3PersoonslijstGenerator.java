/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.synchronisatie.generatie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AangifteAdreshoudingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;

/**
 * Persoonslijst generator.
 *
 * Huidig versie is erg basis en genereert enkel een actuele categorie voor categorieen 01, 02, 03, 04, 07 en 08.
 */
public class Lo3PersoonslijstGenerator {
    private static final ANummerGenerator ANUMMER_GENERATOR = new ANummerGenerator();
    private static final BooleanGenerator BOOLEAN_GENERATOR = new BooleanGenerator();
    private static final BsnGenerator BSN_GENERATOR = new BsnGenerator();
    private static final VoornaamGenerator VOORNAAM_GENERATOR = new VoornaamGenerator();
    private static final GeslachtsnaamGenerator GESLACHTSNAAM_GENERATOR = new GeslachtsnaamGenerator();
    private static final GeslachtGenerator GESLACHT_GENERATOR = new GeslachtGenerator();
    private static final GezagGenerator GEZAG_GENERATOR = new GezagGenerator();
    private static final DatumGenerator DATUM_GENERATOR = new DatumGenerator();
    private static final GemeenteGenerator GEMEENTE_GENERATOR = new GemeenteGenerator();
    private static final StraatnaamGenerator STRAAT_GENERATOR = new StraatnaamGenerator();
    private static final HuisnummerGenerator HUISNUMMER_GENERATOR = new HuisnummerGenerator();
    private static final ReisdocumentGenerator REISDOCUMENT_GENERATOR = new ReisdocumentGenerator();

    public Lo3Persoonslijst genereer() {
        return new Helper().genereer();
    }

    private static class Helper {
        final Lo3Datum geboorteDatum = DATUM_GENERATOR.genereer();
        final Lo3GemeenteCode geboorteGemeente = GEMEENTE_GENERATOR.genereer();
        final Lo3Documentatie documentatie =
                Lo3StapelHelper.lo3Documentatie(1L, null, null, geboorteGemeente.getWaarde(), geboorteDatum.getIntegerWaarde(), "Geboorteakte");
        final Lo3Historie historie = Lo3StapelHelper.lo3His(geboorteDatum.getIntegerWaarde());

        public Lo3Persoonslijst genereer() {
            final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

            builder.persoonStapel(genereerPersoonStapel());
            builder.ouder1Stapel(genereerOuderStapel(Lo3CategorieEnum.CATEGORIE_02));
            builder.ouder2Stapel(genereerOuderStapel(Lo3CategorieEnum.CATEGORIE_03));
            builder.nationaliteitStapel(genereerNationaliteitStapel());
            builder.huwelijkOfGpStapel(genereerHuwelijkStapel());
            builder.inschrijvingStapel(genereerInschrijvingStapel());
            builder.verblijfplaatsStapel(genereerVerblijfplaatsStapel());
            builder.kindStapels(genereerKindStapels());
            builder.verblijfstitelStapel(genereerVerblijfstitel());
            builder.gezagsverhoudingStapel(genereerGezagsverhouding());
            builder.reisdocumentStapels(genereerReisdocumentStapels());
            builder.kiesrechtStapel(genereerKiesrecht());

            return builder.build();
        }

        private Lo3Stapel<Lo3PersoonInhoud> genereerPersoonStapel() {
            final Lo3PersoonInhoud inhoud =
                    Lo3StapelHelper.lo3Persoon(
                            ANUMMER_GENERATOR.genereer(),
                            BSN_GENERATOR.genereer(),
                            VOORNAAM_GENERATOR.genereer(),
                            null,
                            null,
                            GESLACHTSNAAM_GENERATOR.genereer(),
                            geboorteDatum.getIntegerWaarde(),
                            geboorteGemeente.getWaarde(),
                            Lo3LandCode.NEDERLAND.getWaarde(),
                            GESLACHT_GENERATOR.genereer().getWaarde(),
                            null,
                            null,
                            "E");

            return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(inhoud, documentatie, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0)));
        }

        private Lo3Stapel<Lo3OuderInhoud> genereerOuderStapel(final Lo3CategorieEnum categorie) {
            final Lo3OuderInhoud inhoud =
                    Lo3StapelHelper.lo3Ouder(
                            ANUMMER_GENERATOR.genereer(),
                            BSN_GENERATOR.genereer(),
                            VOORNAAM_GENERATOR.genereer(),
                            null,
                            null,
                            GESLACHTSNAAM_GENERATOR.genereer(),
                            DATUM_GENERATOR.genereer().getIntegerWaarde(),
                            GEMEENTE_GENERATOR.genereer().getWaarde(),
                            Lo3LandCode.NEDERLAND.getWaarde(),
                            GESLACHT_GENERATOR.genereer().getWaarde(),
                            geboorteDatum.getIntegerWaarde());

            return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(inhoud, documentatie, historie, new Lo3Herkomst(categorie, 0, 0)));
        }

        private Lo3Stapel<Lo3NationaliteitInhoud> genereerNationaliteitStapel() {
            final Lo3NationaliteitInhoud inhoud = Lo3StapelHelper.lo3Nationaliteit("0001", "001", null, null, null);

            return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(inhoud, documentatie, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0)));
        }

        private Lo3Stapel<Lo3HuwelijkOfGpInhoud> genereerHuwelijkStapel() {
            final Lo3HuwelijkOfGpInhoud inhoud =
                    Lo3StapelHelper.lo3HuwelijkOfGp(
                            ANUMMER_GENERATOR.genereer(),
                            BSN_GENERATOR.genereer(),
                            VOORNAAM_GENERATOR.genereer(),
                            null,
                            null,
                            GESLACHT_GENERATOR.genereer().getWaarde(),
                            DATUM_GENERATOR.genereer().getIntegerWaarde(),
                            GEMEENTE_GENERATOR.genereer().getWaarde(),
                            Lo3LandCode.NEDERLAND.getWaarde(),
                            GESLACHT_GENERATOR.genereer().getWaarde(),
                            DATUM_GENERATOR.genereer().getIntegerWaarde(),
                            GEMEENTE_GENERATOR.genereer().getWaarde(),
                            Lo3LandCode.NEDERLAND.getWaarde(),
                            null,
                            null,
                            null,
                            null,
                            Lo3SoortVerbintenisEnum.HUWELIJK.getCode());

            return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(inhoud, null, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 0)));
        }

        private Lo3Stapel<Lo3InschrijvingInhoud> genereerInschrijvingStapel() {
            final Lo3InschrijvingInhoud inhoud =
                    Lo3StapelHelper.lo3Inschrijving(
                            null,
                            null,
                            null,
                            geboorteDatum.getIntegerWaarde(),
                            geboorteGemeente.getWaarde(),
                            0,
                            1,
                            geboorteDatum.getIntegerWaarde() * 1000000000L,
                            true);

            return Lo3StapelHelper.lo3Stapel(
                    Lo3StapelHelper.lo3Cat(inhoud, null, new Lo3Historie(null, null, null), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));
        }

        private Lo3Stapel<Lo3VerblijfplaatsInhoud> genereerVerblijfplaatsStapel() {
            final Lo3VerblijfplaatsInhoud inhoud =
                    Lo3StapelHelper.lo3Verblijfplaats(
                            geboorteGemeente.getWaarde(),
                            geboorteDatum.getIntegerWaarde(),
                            geboorteDatum.getIntegerWaarde(),
                            STRAAT_GENERATOR.genereer(),
                            HUISNUMMER_GENERATOR.genereer().getIntegerWaarde(),
                            "9999AA",
                            Lo3AangifteAdreshoudingEnum.OUDER.getCode());

            return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(inhoud, null, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0)));
        }

        private List<Lo3Stapel<Lo3KindInhoud>> genereerKindStapels() {
            final List<Lo3Stapel<Lo3KindInhoud>> stapels = new ArrayList<>();
            stapels.add(genereerKindStapel());
            stapels.add(genereerKindStapel());
            return stapels;
        }

        private Lo3Stapel<Lo3KindInhoud> genereerKindStapel() {
            final Lo3KindInhoud inhoud =
                    Lo3StapelHelper.lo3Kind(
                            ANUMMER_GENERATOR.genereer(),
                            BSN_GENERATOR.genereer(),
                            VOORNAAM_GENERATOR.genereer(),
                            null,
                            null,
                            GESLACHT_GENERATOR.genereer().getWaarde(),
                            DATUM_GENERATOR.genereer().getIntegerWaarde(),
                            GEMEENTE_GENERATOR.genereer().getWaarde(),
                            Lo3LandCode.NEDERLAND.getWaarde());

            return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(inhoud, null, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_09, 0, 0)));
        }

        private Lo3Stapel<Lo3VerblijfstitelInhoud> genereerVerblijfstitel() {
            final Lo3VerblijfstitelInhoud inhoud = Lo3StapelHelper.lo3Verblijfstitel("00", null, DATUM_GENERATOR.genereer().getIntegerWaarde());

            return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(inhoud, null, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_10, 0, 0)));
        }

        private Lo3Stapel<Lo3GezagsverhoudingInhoud> genereerGezagsverhouding() {
            final Lo3GezagsverhoudingInhoud inhoud = Lo3StapelHelper.lo3Gezag(GEZAG_GENERATOR.genereer().getWaarde(), BOOLEAN_GENERATOR.genereer());

            return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(inhoud, null, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_11, 0, 0)));

        }

        private List<Lo3Stapel<Lo3ReisdocumentInhoud>> genereerReisdocumentStapels() {
            final List<Lo3Stapel<Lo3ReisdocumentInhoud>> stapels = new ArrayList<>();
            stapels.add(genereerReisdocumentStapel());
            return stapels;
        }

        private Lo3Stapel<Lo3ReisdocumentInhoud> genereerReisdocumentStapel() {
            final Lo3ReisdocumentInhoud inhoud =
                    Lo3StapelHelper.lo3Reisdocument(
                            REISDOCUMENT_GENERATOR.genereerSoort().getWaarde(),
                            REISDOCUMENT_GENERATOR.genereerNummer(),
                            DATUM_GENERATOR.genereer().getIntegerWaarde(),
                            REISDOCUMENT_GENERATOR.genereerAutoriteit().getWaarde(),
                            DATUM_GENERATOR.genereer().getIntegerWaarde(),
                            null,
                            null,
                            null
                    );

            return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(inhoud, null, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_12, 0, 0)));
        }

        private Lo3Stapel<Lo3KiesrechtInhoud> genereerKiesrecht() {
            final boolean indicatieUitsluitingNl = BOOLEAN_GENERATOR.genereer();
            final Integer datumEindeUitSluitingNl = indicatieUitsluitingNl ? DATUM_GENERATOR.genereer().getIntegerWaarde() : null;

            final Lo3KiesrechtInhoud inhoud =
                    Lo3StapelHelper.lo3Kiesrecht(
                            BOOLEAN_GENERATOR.genereer(),
                            DATUM_GENERATOR.genereer().getIntegerWaarde(),
                            DATUM_GENERATOR.genereer().getIntegerWaarde(),
                            indicatieUitsluitingNl,
                            datumEindeUitSluitingNl);

            return Lo3StapelHelper.lo3Stapel(
                    Lo3StapelHelper.lo3Cat(inhoud, null, new Lo3Historie(null, null, null), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_13, 0, 0)));
        }
    }

}
