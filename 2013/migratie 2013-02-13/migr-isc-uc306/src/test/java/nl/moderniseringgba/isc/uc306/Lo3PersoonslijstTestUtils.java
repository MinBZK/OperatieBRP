/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import java.util.Arrays;

import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Builder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerwijzingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Huisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.testutils.VerplichteStapel;

public class Lo3PersoonslijstTestUtils {

    private static final String GESLACHTSAANDUIDING_MAN = "M";
    // private static final String GESLACHTSAANDUIDING_VROUW = "V";
    private static final long BSN_NUMMER = 123456789L;
    private static final Long A_NUMMER = 2349326344L;
    private static final int DATE2 = 20121001;
    private static final int DATE = 20121024;
    // private static final String INDICATIE_ONJUIST = "O";
    private static final String AANDUIDING_NAAMGEBRUIK = "E";
    private static final String VOORNAMEN = "Billy";
    // private static final String ADELIJKE_TITEL_PREDIKAAT = "PS";
    private static final String GESLACHTSNAAM = "Barendsen";
    private static final String VOORNAMEN2 = "Henk";
    private static final String GEMEENTE_CODE = "0518";
    private static final String LAND_CODE = "6030";

    private Lo3PersoonslijstTestUtils() {
    }

    @SuppressWarnings("unchecked")
    public static Lo3Persoonslijst maakGeboorte(final Lo3PersoonInhoud lo3PersoonInhoud) {
        // final Lo3Categorie<Lo3NationaliteitInhoud> lo3Nationaliteit =
        // Lo3Builder.createLo3Nationaliteit(NATIONALITEIT_CODE, REDEN_VERKRIJGEN_NEDERLANDSCHAP_CODE, null,
        // null, null, DATE, DATE2);
        // final Lo3Stapel<Lo3NationaliteitInhoud> lo3NationaliteitStapel =
        // new Lo3Stapel<Lo3NationaliteitInhoud>(Arrays.asList(lo3Nationaliteit));
        final Lo3Historie historie = Lo3Builder.createLo3Historie("S", 20121101, 20121103);
        final Lo3Categorie<Lo3PersoonInhoud> persoon1 =
                new Lo3Categorie<Lo3PersoonInhoud>(lo3PersoonInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_01, 0, 0));
        final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel =
        // new Lo3Stapel<Lo3PersoonInhoud>(Arrays.asList(lo3Persoon1, lo3Persoon2));
                new Lo3Stapel<Lo3PersoonInhoud>(Arrays.asList(persoon1));
        // final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
        // new Lo3Stapel<Lo3InschrijvingInhoud>(Arrays.asList(new Lo3Categorie<Lo3InschrijvingInhoud>(
        // new Lo3InschrijvingInhoud(null, null, null, new Lo3Datum(GEBOORTEDATUM), null, null, 1,
        // new Lo3Datumtijdstempel(20070401000000000L), null), null, Lo3Historie.NULL_HISTORIE,
        // null)));
        final Lo3Stapel<Lo3OuderInhoud> ouder = VerplichteStapel.createOuderStapel();
        // final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaats = VerplichteStapel.createVerblijfplaatsStapel();
        return new Lo3PersoonslijstBuilder().persoonStapel(lo3PersoonStapel).ouder1Stapel(ouder).ouder2Stapel(ouder)
                .build();
    }

    public static Lo3PersoonInhoud maakLo3PersoonInhoud() {
        final Long aNummer = A_NUMMER;
        final Long burgerservicenummer = BSN_NUMMER;
        final String voornamen = VOORNAMEN;
        final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode = null;
        final String voorvoegselGeslachtsnaam = null;
        final String geslachtsnaam = GESLACHTSNAAM;
        final Lo3Datum geboortedatum = new Lo3Datum(DATE);
        final Lo3GemeenteCode geboorteGemeenteCode = new Lo3GemeenteCode(GEMEENTE_CODE);
        final Lo3LandCode geboorteLandCode = new Lo3LandCode(LAND_CODE);
        final Lo3Geslachtsaanduiding geslachtsaanduiding = new Lo3Geslachtsaanduiding(GESLACHTSAANDUIDING_MAN);
        final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode =
                new Lo3AanduidingNaamgebruikCode(AANDUIDING_NAAMGEBRUIK);
        final Long vorigANummer = null;
        final Long volgendANummer = null;

        final Lo3PersoonInhoud persoonInhoud =
                new Lo3PersoonInhoud(aNummer, burgerservicenummer, voornamen, adellijkeTitelPredikaatCode,
                        voorvoegselGeslachtsnaam, geslachtsnaam, geboortedatum, geboorteGemeenteCode,
                        geboorteLandCode, geslachtsaanduiding, aanduidingNaamgebruikCode, vorigANummer,
                        volgendANummer);
        return persoonInhoud;
    }

    public static Lo3PersoonInhoud maakLo3PersoonInhoud2() {
        final Long aNummer = A_NUMMER;
        final Long burgerservicenummer = BSN_NUMMER;
        final String voornamen = VOORNAMEN2;
        final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode = null;
        final String voorvoegselGeslachtsnaam = null;
        final String geslachtsnaam = GESLACHTSNAAM;
        final Lo3Datum geboortedatum = new Lo3Datum(DATE);
        final Lo3GemeenteCode geboorteGemeenteCode = new Lo3GemeenteCode(GEMEENTE_CODE);
        final Lo3LandCode geboorteLandCode = new Lo3LandCode(LAND_CODE);
        final Lo3Geslachtsaanduiding geslachtsaanduiding = new Lo3Geslachtsaanduiding(GESLACHTSAANDUIDING_MAN);
        final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode =
                new Lo3AanduidingNaamgebruikCode(AANDUIDING_NAAMGEBRUIK);
        final Long vorigANummer = null;
        final Long volgendANummer = null;

        final Lo3PersoonInhoud persoonInhoud =
                new Lo3PersoonInhoud(aNummer, burgerservicenummer, voornamen, adellijkeTitelPredikaatCode,
                        voorvoegselGeslachtsnaam, geslachtsnaam, geboortedatum, geboorteGemeenteCode,
                        geboorteLandCode, geslachtsaanduiding, aanduidingNaamgebruikCode, vorigANummer,
                        volgendANummer);
        return persoonInhoud;
    }

    public static Lo3PersoonInhoud maakLo3PersoonInhoud3() {
        final Long aNummer = A_NUMMER;
        final Long burgerservicenummer = BSN_NUMMER;
        final String voornamen = VOORNAMEN2;
        final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode = null;
        final String voorvoegselGeslachtsnaam = null;
        final String geslachtsnaam = GESLACHTSNAAM;
        final Lo3Datum geboortedatum = new Lo3Datum(DATE2);
        final Lo3GemeenteCode geboorteGemeenteCode = new Lo3GemeenteCode(GEMEENTE_CODE);
        final Lo3LandCode geboorteLandCode = new Lo3LandCode(LAND_CODE);
        final Lo3Geslachtsaanduiding geslachtsaanduiding = new Lo3Geslachtsaanduiding(GESLACHTSAANDUIDING_MAN);
        final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode =
                new Lo3AanduidingNaamgebruikCode(AANDUIDING_NAAMGEBRUIK);
        final Long vorigANummer = null;
        final Long volgendANummer = null;

        final Lo3PersoonInhoud persoonInhoud =
                new Lo3PersoonInhoud(aNummer, burgerservicenummer, voornamen, adellijkeTitelPredikaatCode,
                        voorvoegselGeslachtsnaam, geslachtsnaam, geboortedatum, geboorteGemeenteCode,
                        geboorteLandCode, geslachtsaanduiding, aanduidingNaamgebruikCode, vorigANummer,
                        volgendANummer);
        return persoonInhoud;
    }

    public static Lo3PersoonInhoud maakLo3PersoonInhoud4() {
        final Long aNummer = A_NUMMER;
        final Long burgerservicenummer = BSN_NUMMER;
        final String voornamen = VOORNAMEN;
        final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode = null;
        final String voorvoegselGeslachtsnaam = null;
        final String geslachtsnaam = GESLACHTSNAAM;
        final Lo3Datum geboortedatum = new Lo3Datum(DATE2);
        final Lo3GemeenteCode geboorteGemeenteCode = new Lo3GemeenteCode(GEMEENTE_CODE);
        final Lo3LandCode geboorteLandCode = new Lo3LandCode(LAND_CODE);
        final Lo3Geslachtsaanduiding geslachtsaanduiding = new Lo3Geslachtsaanduiding(GESLACHTSAANDUIDING_MAN);
        final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode =
                new Lo3AanduidingNaamgebruikCode(AANDUIDING_NAAMGEBRUIK);
        final Long vorigANummer = null;
        final Long volgendANummer = null;

        final Lo3PersoonInhoud persoonInhoud =
                new Lo3PersoonInhoud(aNummer, burgerservicenummer, voornamen, adellijkeTitelPredikaatCode,
                        voorvoegselGeslachtsnaam, geslachtsnaam, geboortedatum, geboorteGemeenteCode,
                        geboorteLandCode, geslachtsaanduiding, aanduidingNaamgebruikCode, vorigANummer,
                        volgendANummer);
        return persoonInhoud;
    }

    public static Lo3VerwijzingInhoud maakLo3VerwijzingInhoud() {
        return new Lo3VerwijzingInhoud(A_NUMMER, // aNummer
                BSN_NUMMER, // burgerservicenummer
                VOORNAMEN, // voornamen
                null, // adellijkeTitelPredikaatCode
                null, // voorvoegselGeslachtsnaam
                GESLACHTSNAAM, // geslachtsnaam
                new Lo3Datum(DATE), // geboortedatum
                new Lo3GemeenteCode(GEMEENTE_CODE), // geboorteGemeenteCode
                new Lo3LandCode(LAND_CODE), // geboorteLandCode
                new Lo3GemeenteCode(GEMEENTE_CODE), // gemeenteInschrijving
                new Lo3Datum(20121025), // datumInschrijving
                "Lange poten", // straatnaam
                null, // naamOpenbareRuimte
                new Lo3Huisnummer(14), // huisnummer
                null, // huisletter
                null, // huisnummertoevoeging
                null, // aanduidingHuisnummer
                "2543WW", // postcode
                "Den Haag", // woonplaatsnaam
                null, // identificatiecodeVerblijfplaats
                null, // identificatiecodeNummeraanduiding
                null, // locatieBeschrijving
                Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement() // indicatieGeheimCode
        );
    }

}
