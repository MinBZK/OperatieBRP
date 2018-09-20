/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3;

import java.util.Arrays;
import java.util.List;

import nl.moderniseringgba.isc.esb.message.lo3.impl.Ib01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.If01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.If21Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.If31Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ii01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Iv01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.La01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Lg01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.NullBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Pf01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Pf02Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Pf03Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tf01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tf11Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Builder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
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
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.testutils.VerplichteStapel;

import org.junit.Test;

public class Lo3BerichtenTest extends AbstractLo3BerichtTest {

    private static final String LO3_PL_STRING =
            "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200170000000000000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    @Test
    public void testIb01Bericht() throws Exception {
        final Ib01Bericht bericht = new Ib01Bericht();
        bericht.setLo3Persoonslijst(maakLo3Persoonslijst());
        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testIf01Bericht() throws Exception {
        testFormatAndParseBericht(new If01Bericht());
    }

    @Test
    public void testIf21Bericht() throws Exception {
        testFormatAndParseBericht(new If21Bericht());
    }

    @Test
    public void testIf31Bericht() throws Exception {
        testFormatAndParseBericht(new If31Bericht());
    }

    @Test
    public void testIi01Bericht() throws Exception {
        // testFormatAndParseBericht(new Ii01Bericht());

        final Ii01Bericht bericht = new Ii01Bericht();
        bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ELEMENT_0110, "1234567890"); // A-nummer
        bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ELEMENT_0120, "123456789"); // Bsn

        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testIv01Bericht() throws Exception {
        final Lo3VerwijzingInhoud inhoud = new Lo3VerwijzingInhoud(2349326344L, // aNummer
                546589734L, // burgerservicenummer
                "Jaap", // voornamen
                null, // adellijkeTitelPredikaatCode
                null, // voorvoegselGeslachtsnaam
                "Appelenberg", // geslachtsnaam
                new Lo3Datum(19540307), // geboortedatum
                new Lo3GemeenteCode("0518"), // geboorteGemeenteCode
                new Lo3LandCode(Lo3LandCode.CODE_NEDERLAND), // geboorteLandCode
                new Lo3GemeenteCode("0518"), // gemeenteInschrijving
                new Lo3Datum(19540309), // datumInschrijving
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

        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(20000101), new Lo3Datum(20000102));

        final Lo3Categorie<Lo3VerwijzingInhoud> categorie =
                new Lo3Categorie<Lo3VerwijzingInhoud>(inhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_21, 1, 1));

        final Iv01Bericht bericht = new Iv01Bericht();
        bericht.setVerwijzing(categorie);

        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testLa01Bericht() throws Exception {
        final La01Bericht bericht = new La01Bericht();
        bericht.setLo3Persoonslijst(maakLo3Persoonslijst());
        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testLg01Bericht() throws Exception {
        final Lg01Bericht bericht = new Lg01Bericht();
        bericht.setLo3Persoonslijst(maakLo3Persoonslijst());
        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testNullBericht() throws Exception {
        testFormatAndParseBericht(new NullBericht());
    }

    @Test
    public void testPf01Bericht() throws Exception {
        testFormatAndParseBericht(new Pf01Bericht());
    }

    @Test
    public void testPf02Bericht() throws Exception {
        testFormatAndParseBericht(new Pf02Bericht());
    }

    @Test
    public void testPf03Bericht() throws Exception {
        testFormatAndParseBericht(new Pf03Bericht());
    }

    @Test
    public void testTf01Bericht() throws Exception {
        // // headers:
        // final String randomKey = "01234567";
        // final String foutReden = Foutreden.A.toString();
        // final String gemeente = "1234";
        // final String a_nummer = "0123456789";

        // tf01_A.setHeader(Lo3HeaderVeld.RANDOM_KEY, randomKey);
        // tf01_A.setHeader(Lo3HeaderVeld.FOUTREDEN, foutReden);
        // tf01_A.setHeader(Lo3HeaderVeld.GEMEENTE, gemeente);
        // tf01_A.setHeader(Lo3HeaderVeld.A_NUMMER, a_nummer);
        // tf01_A.setAktenummer("1234567");

        final String tf01_LO3_foutreden_A = "00000001Tf01A12340123456789000190101481200071234567";
        final Tf01Bericht tf01_A = new Tf01Bericht();
        tf01_A.parse(tf01_LO3_foutreden_A);
        testFormatAndParseBericht(tf01_A);

        final String tf01_LO3_foutreden_B = "00000002Tf01B12340123456789000190101481200071234567";
        final Tf01Bericht tf01_B = new Tf01Bericht();
        tf01_B.parse(tf01_LO3_foutreden_B);
        testFormatAndParseBericht(tf01_B);

        final String tf01_LO3_foutreden_E = "00000003Tf01E12340123456789000190101481200071234567";
        final Tf01Bericht tf01_E = new Tf01Bericht();
        tf01_E.parse(tf01_LO3_foutreden_E);
        testFormatAndParseBericht(tf01_E);

        final String tf01_LO3_foutreden_G = "00000004Tf01G12340123456789000190101481200071234567";
        final Tf01Bericht tf01_G = new Tf01Bericht();
        tf01_G.parse(tf01_LO3_foutreden_G);
        testFormatAndParseBericht(tf01_G);

        final String tf01_LO3_foutreden_M = "00000005Tf01M12340123456789000190101481200071234567";
        final Tf01Bericht tf01_M = new Tf01Bericht();
        tf01_M.parse(tf01_LO3_foutreden_M);
        testFormatAndParseBericht(tf01_M);

        final String tf01_LO3_foutreden_O = "00000006Tf01O12340123456789000190101481200071234567";
        final Tf01Bericht tf01_O = new Tf01Bericht();
        tf01_O.parse(tf01_LO3_foutreden_O);
        testFormatAndParseBericht(tf01_O);

        final String tf01_LO3_foutreden_U = "00000007Tf01U12340123456789000190101481200071234567";
        final Tf01Bericht tf01_U = new Tf01Bericht();
        tf01_U.parse(tf01_LO3_foutreden_U);
        testFormatAndParseBericht(tf01_U);

        final String tf01_LO3_foutreden_V = "00000008Tf01V12340123456789000190101481200071234567";
        final Tf01Bericht tf01_V = new Tf01Bericht();
        tf01_V.parse(tf01_LO3_foutreden_V);
        testFormatAndParseBericht(tf01_V);
    }

    @Test
    public void testTf11Bericht() throws Exception {
        testFormatAndParseBericht(new Tf11Bericht());

        final Tf11Bericht bericht = new Tf11Bericht();
        bericht.setANummer("2324234223");

        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testTb01Bericht() throws Exception {
        final Tb01Bericht bericht = new Tb01Bericht();
        bericht.setBronGemeente("1234");
        bericht.setDoelGemeente("5678");

        final Lo3Persoonslijst geboorte = maakGeboorte(maakLo3PersoonInhoud());
        bericht.setLo3Persoonslijst(geboorte);
        testFormatAndParseBericht(bericht);
    }

    private static Lo3Persoonslijst maakLo3Persoonslijst() throws Exception {
        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(LO3_PL_STRING);
        final Lo3Persoonslijst result = new Lo3PersoonslijstParser().parse(categorieen);
        return result;
    }

    private static Lo3Persoonslijst maakGeboorte(final Lo3PersoonInhoud lo3PersoonInhoud) {
        // final Lo3Categorie<Lo3NationaliteitInhoud> lo3Nationaliteit =
        // Lo3Builder.createLo3Nationaliteit(NATIONALITEIT_CODE, REDEN_VERKRIJGEN_NEDERLANDSCHAP_CODE, null,
        // null, null, DATE, DATE2);
        // final Lo3Stapel<Lo3NationaliteitInhoud> lo3NationaliteitStapel =
        // new Lo3Stapel<Lo3NationaliteitInhoud>(Arrays.asList(lo3Nationaliteit));
        final Lo3Historie historie = Lo3Builder.createLo3Historie("S", 20121101, 20121103);
        final Lo3Categorie<Lo3PersoonInhoud> persoon1 =
                new Lo3Categorie<Lo3PersoonInhoud>(lo3PersoonInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_01, 0, 0));
        @SuppressWarnings("unchecked")
        final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel =
        // new Lo3Stapel<Lo3PersoonInhoud>(Arrays.asList(lo3Persoon1, lo3Persoon2));
                new Lo3Stapel<Lo3PersoonInhoud>(Arrays.asList(persoon1));
        // final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
        // new Lo3Stapel<Lo3InschrijvingInhoud>(Arrays.asList(new Lo3Categorie<Lo3InschrijvingInhoud>(
        // new Lo3InschrijvingInhoud(null, null, null, new Lo3Datum(GEBOORTEDATUM), null, null, 1,
        // new Lo3Datumtijdstempel(20070401000000000L), null), null, Lo3Historie.NULL_HISTORIE,
        // Collections.<Lo3Herkomst>emptySet())));
        final Lo3Stapel<Lo3OuderInhoud> ouder = VerplichteStapel.createOuderStapel();
        // final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaats = VerplichteStapel.createVerblijfplaatsStapel();
        return new Lo3PersoonslijstBuilder().persoonStapel(lo3PersoonStapel).ouder1Stapel(ouder).ouder2Stapel(ouder)
                .build();
    }

    private static Lo3PersoonInhoud maakLo3PersoonInhoud() {
        final Long aNummer = 2349326344L;
        final Long burgerservicenummer = 123456789L;
        final String voornamen = "Billy";
        final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode = null;
        final String voorvoegselGeslachtsnaam = null;
        final String geslachtsnaam = "Barendsen";
        final Lo3Datum geboortedatum = new Lo3Datum(20121024);
        final Lo3GemeenteCode geboorteGemeenteCode = new Lo3GemeenteCode("0518");
        final Lo3LandCode geboorteLandCode = new Lo3LandCode("6030");
        final Lo3Geslachtsaanduiding geslachtsaanduiding = new Lo3Geslachtsaanduiding("M");
        final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode = new Lo3AanduidingNaamgebruikCode("E");
        final Long vorigANummer = null;
        final Long volgendANummer = null;

        final Lo3PersoonInhoud persoonInhoud =
                new Lo3PersoonInhoud(aNummer, burgerservicenummer, voornamen, adellijkeTitelPredikaatCode,
                        voorvoegselGeslachtsnaam, geslachtsnaam, geboortedatum, geboorteGemeenteCode,
                        geboorteLandCode, geslachtsaanduiding, aanduidingNaamgebruikCode, vorigANummer,
                        volgendANummer);
        return persoonInhoud;
    }

}
