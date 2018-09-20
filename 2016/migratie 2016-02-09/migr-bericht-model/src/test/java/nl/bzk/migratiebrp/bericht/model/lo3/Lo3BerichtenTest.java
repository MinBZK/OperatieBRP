/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ib01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.If01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.If21Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.If31Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ii01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Iv01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.La01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.NullBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf02Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf03Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tf11Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Builder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerwijzingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.testutils.VerplichteStapel;
import org.junit.Ignore;
import org.junit.Test;

public class Lo3BerichtenTest extends AbstractLo3BerichtTest {

    private static final String LO3_PL_STRING =
            "00700011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207058681000819900101701000108010004000180200171990010112000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    @Test
    public void testIb01Bericht() throws ClassNotFoundException, BerichtInhoudException, IOException, BerichtSyntaxException {
        final Ib01Bericht bericht = new Ib01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setLo3Persoonslijst(Lo3BerichtenTest.maakLo3Persoonslijst());
        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testIf01Bericht() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final If01Bericht bericht = new If01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testIf21Bericht() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final If21Bericht bericht = new If21Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testIf31Bericht() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final If31Bericht bericht = new If31Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testIi01Bericht() throws ClassNotFoundException, BerichtInhoudException, IOException {
        // testFormatAndParseBericht(new Ii01Bericht());

        final Ii01Bericht bericht = new Ii01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ELEMENT_0110, "1234567890"); // A-nummer
        bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ELEMENT_0120, "123456789"); // Bsn

        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testIv01Bericht() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Lo3VerwijzingInhoud inhoud = new Lo3VerwijzingInhoud(Lo3Long.wrap(2349326344L), // aNummer
            new Lo3Integer(546589734), // burgerservicenummer
            new Lo3String("Jaap"), // voornamen
            null, // adellijkeTitelPredikaatCode
            null, // voorvoegselGeslachtsnaam
            new Lo3String("Appelenberg"), // geslachtsnaam
            new Lo3Datum(19540307), // geboortedatum
            new Lo3GemeenteCode("0518"), // geboorteGemeenteCode
            new Lo3LandCode(Lo3LandCode.CODE_NEDERLAND), // geboorteLandCode
            new Lo3GemeenteCode("0518"), // gemeenteInschrijving
            new Lo3Datum(19540309), // datumInschrijving
            // straatnaam
            // naamOpenbareRuimte
            // huisnummer
            // huisletter
            // huisnummertoevoeging
            // aanduidingHuisnummer
            // postcode
            // woonplaatsnaam
            // identificatiecodeVerblijfplaats
            // identificatiecodeNummeraanduiding
            // locatieBeschrijving
            Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement() // indicatieGeheimCode
                );

        final Iv01Bericht bericht = new Iv01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setVerwijzing(inhoud);

        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testLa01Bericht() throws ClassNotFoundException, BerichtInhoudException, IOException, BerichtSyntaxException {
        final La01Bericht bericht = new La01Bericht();
        bericht.setCategorieen(Lo3BerichtenTest.maakLo3Categorieen());
        bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testLg01Bericht() throws ClassNotFoundException, BerichtInhoudException, IOException, BerichtSyntaxException {
        final Lg01Bericht bericht = new Lg01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setCategorieen(Lo3BerichtenTest.maakLo3Categorieen());
        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testNullBericht() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final NullBericht bericht = new NullBericht();
        bericht.setMessageId(MessageIdGenerator.generateId());

        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testPf01Bericht() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Pf01Bericht bericht = new Pf01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testPf02Bericht() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Pf02Bericht bericht = new Pf02Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testPf03Bericht() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Pf03Bericht bericht = new Pf03Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testTf01Bericht() throws ClassNotFoundException, BerichtInhoudException, IOException, BerichtSyntaxException {
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
        tf01_A.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(tf01_A);

        final String tf01_LO3_foutreden_B = "00000002Tf01B12340123456789000190101481200071234567";
        final Tf01Bericht tf01_B = new Tf01Bericht();
        tf01_B.parse(tf01_LO3_foutreden_B);
        tf01_B.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(tf01_B);

        final String tf01_LO3_foutreden_E = "00000003Tf01E12340123456789000190101481200071234567";
        final Tf01Bericht tf01_E = new Tf01Bericht();
        tf01_E.parse(tf01_LO3_foutreden_E);
        tf01_E.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(tf01_E);

        final String tf01_LO3_foutreden_G = "00000004Tf01G12340123456789000190101481200071234567";
        final Tf01Bericht tf01_G = new Tf01Bericht();
        tf01_G.parse(tf01_LO3_foutreden_G);
        tf01_G.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(tf01_G);

        final String tf01_LO3_foutreden_M = "00000005Tf01M12340123456789000190101481200071234567";
        final Tf01Bericht tf01_M = new Tf01Bericht();
        tf01_M.parse(tf01_LO3_foutreden_M);
        tf01_M.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(tf01_M);

        final String tf01_LO3_foutreden_O = "00000006Tf01O12340123456789000190101481200071234567";
        final Tf01Bericht tf01_O = new Tf01Bericht();
        tf01_O.parse(tf01_LO3_foutreden_O);
        tf01_O.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(tf01_O);

        final String tf01_LO3_foutreden_U = "00000007Tf01U12340123456789000190101481200071234567";
        final Tf01Bericht tf01_U = new Tf01Bericht();
        tf01_U.parse(tf01_LO3_foutreden_U);
        tf01_U.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(tf01_U);

        final String tf01_LO3_foutreden_V = "00000008Tf01V12340123456789000190101481200071234567";
        final Tf01Bericht tf01_V = new Tf01Bericht();
        tf01_V.parse(tf01_LO3_foutreden_V);
        tf01_V.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(tf01_V);
    }

    @Test
    public void testTf11Bericht() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Tf11Bericht emptyBericht = new Tf11Bericht();
        emptyBericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(emptyBericht);

        final Tf11Bericht bericht = new Tf11Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setANummer("2324234223");

        testFormatAndParseBericht(bericht);
    }

    @Test
    @Ignore
    public void testTb01Bericht() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Tb01Bericht bericht = new Tb01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setBronGemeente("1234");
        bericht.setDoelGemeente("5678");

        final Lo3Persoonslijst geboorte = Lo3BerichtenTest.maakGeboorte(Lo3BerichtenTest.maakLo3PersoonInhoudTb01());
        bericht.setLo3Persoonslijst(geboorte);
        testFormatAndParseBericht(bericht);
    }

    private static Lo3Persoonslijst maakLo3Persoonslijst() throws BerichtSyntaxException {
        return new Lo3PersoonslijstParser().parse(Lo3BerichtenTest.maakLo3Categorieen());
    }

    private static List<Lo3CategorieWaarde> maakLo3Categorieen() throws BerichtSyntaxException {
        return Lo3Inhoud.parseInhoud(LO3_PL_STRING);
    }

    private static Lo3Persoonslijst maakGeboorte(final Lo3PersoonInhoud lo3PersoonInhoud) {
        // final Lo3Categorie<Lo3NationaliteitInhoud> lo3Nationaliteit =
        // Lo3Builder.createLo3Nationaliteit(NATIONALITEIT_CODE, REDEN_VERKRIJGEN_NEDERLANDSCHAP_CODE, null,
        // null, null, DATE, DATE2);
        // final Lo3Stapel<Lo3NationaliteitInhoud> lo3NationaliteitStapel =
        // new Lo3Stapel<Lo3NationaliteitInhoud>(Arrays.asList(lo3Nationaliteit));
        final Lo3Historie historie = Lo3Builder.createLo3Historie(null, 20121101, 20121103);
        final Lo3Categorie<Lo3PersoonInhoud> persoon1 =
                new Lo3Categorie<>(lo3PersoonInhoud, null, null, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0));
        final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel =
        // new Lo3Stapel<Lo3PersoonInhoud>(Arrays.asList(lo3Persoon1, lo3Persoon2));
                new Lo3Stapel<>(Arrays.asList(persoon1));
        // final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
        // new Lo3Stapel<Lo3InschrijvingInhoud>(Arrays.asList(new Lo3Categorie<Lo3InschrijvingInhoud>(
        // new Lo3InschrijvingInhoud(null, null, null, new Lo3Datum(GEBOORTEDATUM), null, null, 1,
        // new Lo3Datumtijdstempel(20070401000000000L), null), null, Lo3Historie.NULL_HISTORIE,
        // Collections.<Lo3Herkomst>emptySet())));
        final Lo3Stapel<Lo3OuderInhoud> ouder1 = VerplichteStapel.createOuder1Stapel();
        final Lo3Stapel<Lo3OuderInhoud> ouder2 = VerplichteStapel.createOuder2Stapel();
        // final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaats = VerplichteStapel.createVerblijfplaatsStapel();
        return new Lo3PersoonslijstBuilder().persoonStapel(lo3PersoonStapel).ouder1Stapel(ouder1).ouder2Stapel(ouder2).build();
    }

    private static Lo3PersoonInhoud maakLo3PersoonInhoudTb01() {
        final Lo3Long aNummer = null;// Lo3Long.wrap(2349326344L);
        final Lo3Integer burgerservicenummer = null;// new Lo3Integer(123456789);
        final Lo3String voornamen = new Lo3String("Billy");
        final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode = null;
        final Lo3String voorvoegselGeslachtsnaam = null;
        final Lo3String geslachtsnaam = new Lo3String("Barendsen");
        final Lo3Datum geboortedatum = new Lo3Datum(20121024);
        final Lo3GemeenteCode geboorteGemeenteCode = new Lo3GemeenteCode("0518");
        final Lo3LandCode geboorteLandCode = new Lo3LandCode("6030");
        final Lo3Geslachtsaanduiding geslachtsaanduiding = new Lo3Geslachtsaanduiding("M");
        final Lo3Long vorigANummer = null;
        final Lo3Long volgendANummer = null;
        final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode = null;// new Lo3AanduidingNaamgebruikCode("E");

        return new Lo3PersoonInhoud(
            aNummer,
            burgerservicenummer,
            voornamen,
            adellijkeTitelPredikaatCode,
            voorvoegselGeslachtsnaam,
            geslachtsnaam,
            geboortedatum,
            geboorteGemeenteCode,
            geboorteLandCode,
            geslachtsaanduiding,
            vorigANummer,
            volgendANummer,
            aanduidingNaamgebruikCode);
    }
}
