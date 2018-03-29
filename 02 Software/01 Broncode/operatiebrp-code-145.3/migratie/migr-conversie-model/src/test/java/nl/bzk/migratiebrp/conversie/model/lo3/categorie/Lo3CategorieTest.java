/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.categorie;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingEuropeesKiesrechtEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingNaamgebruikCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingUitgeslotenKiesrechtEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AangifteAdreshoudingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3FunctieAdresEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieCurateleregisterEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieGezagMinderjarigeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatiePKVolledigGeconverteerdCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3SignaleringEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.testutils.EqualsAndHashcodeTester;
import nl.bzk.migratiebrp.conversie.model.testutils.VerplichteStapel;
import org.junit.Test;

public final class Lo3CategorieTest {

    public static final String GESLACHTSNAAM = "geslachtsnaam";
    private static final String ANDERS = "anders";
    private static final String VOORNAMEN = "voornamen";
    private static final String STRING_1234 = "1234";
    private static final String STRING_4321 = "4321";

    public static Lo3Categorie createDummy_Lo3InschrijvingInhoud() {
        Lo3InschrijvingInhoud inhoud = new Lo3InschrijvingInhoud(
                new Lo3Datum(20120101),
                new Lo3Datum(20120101),
                Lo3RedenOpschortingBijhoudingCodeEnum.EMIGRATIE.asElement(),
                new Lo3Datum(15330103),
                new Lo3GemeenteCode("0518"),
                Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement(),
                null,
                null,
                new Lo3Integer(1),
                new Lo3Datumtijdstempel(12345678901234567L),
                Lo3IndicatiePKVolledigGeconverteerdCodeEnum.VOLLEDIG_GECONVERTEERD.asElement());
        Lo3Historie his = new Lo3Historie(null, new Lo3Datum(20010101), new Lo3Datum(20010102));
        Lo3Herkomst herkomst = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        return new Lo3Categorie(inhoud, null, his, herkomst);
    }

    @Test
    public void testLo3InschrijvingInhoud() throws NoSuchMethodException, IllegalAccessException {

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new Lo3InschrijvingInhoud(
                new Lo3Datum(20120101),
                new Lo3Datum(20120101),
                Lo3RedenOpschortingBijhoudingCodeEnum.EMIGRATIE.asElement(),
                new Lo3Datum(15330103),
                new Lo3GemeenteCode("0518"),
                Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement(),
                null,
                null,
                new Lo3Integer(1),
                new Lo3Datumtijdstempel(12345678901234567L),
                Lo3IndicatiePKVolledigGeconverteerdCodeEnum.VOLLEDIG_GECONVERTEERD.asElement()), new Lo3InschrijvingInhoud(
                new Lo3Datum(20120101),
                new Lo3Datum(20120101),
                Lo3RedenOpschortingBijhoudingCodeEnum.EMIGRATIE.asElement(),
                new Lo3Datum(15330103),
                new Lo3GemeenteCode("0518"),
                Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement(),
                null,
                null,
                new Lo3Integer(1),
                new Lo3Datumtijdstempel(12345678901234567L),
                Lo3IndicatiePKVolledigGeconverteerdCodeEnum.VOLLEDIG_GECONVERTEERD.asElement()), new Lo3InschrijvingInhoud(
                new Lo3Datum(20140506),
                new Lo3Datum(21000304),
                Lo3RedenOpschortingBijhoudingCodeEnum.ONBEKEND.asElement(),
                new Lo3Datum(19780507),
                new Lo3GemeenteCode(STRING_1234),
                Lo3IndicatieGeheimCodeEnum.NIET_AAN_KERKEN.asElement(),
                null,
                null,
                new Lo3Integer(2),
                new Lo3Datumtijdstempel(76543210987654321L),
                null));
    }

    @Test
    public void testLo3NationaliteitInhoud() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
                new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(STRING_1234), new Lo3RedenNederlandschapCode("020"), null, null, null),
                new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(STRING_1234), new Lo3RedenNederlandschapCode("020"), null, null, null),
                new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(STRING_4321), new Lo3RedenNederlandschapCode("020"), null, null, null));
    }

    @Test
    public void testLo3OverlijdenInhoud() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
                new Lo3OverlijdenInhoud(new Lo3Datum(20120101), new Lo3GemeenteCode(STRING_1234), new Lo3LandCode(STRING_1234)),
                new Lo3OverlijdenInhoud(new Lo3Datum(20120101), new Lo3GemeenteCode(STRING_1234), new Lo3LandCode(STRING_1234)),
                new Lo3OverlijdenInhoud(new Lo3Datum(20100101), new Lo3GemeenteCode(STRING_4321), new Lo3LandCode(STRING_4321)));
    }

    @Test
    public void testLo3PersoonInhoud() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
                new Lo3PersoonInhoud(
                        Lo3String.wrap("1234567890"),
                        Lo3String.wrap("123456789"),
                        Lo3String.wrap("voor namen"),
                        new Lo3AdellijkeTitelPredikaatCode("B"),
                        Lo3String.wrap("voorv."),
                        Lo3String.wrap(GESLACHTSNAAM),
                        new Lo3Datum(19900101),
                        new Lo3GemeenteCode(STRING_1234),
                        new Lo3LandCode(STRING_1234),
                        Lo3GeslachtsaanduidingEnum.MAN.asElement(),
                        Lo3String.wrap("9876543210"),
                        Lo3String.wrap("5678901234"),
                        Lo3AanduidingNaamgebruikCodeEnum.EIGEN_GESLACHTSNAAM.asElement()),
                new Lo3PersoonInhoud(
                        Lo3String.wrap("1234567890"),
                        Lo3String.wrap("123456789"),
                        Lo3String.wrap("voor namen"),
                        new Lo3AdellijkeTitelPredikaatCode("B"),
                        Lo3String.wrap("voorv."),
                        Lo3String.wrap(GESLACHTSNAAM),
                        new Lo3Datum(19900101),
                        new Lo3GemeenteCode(STRING_1234),
                        new Lo3LandCode(STRING_1234),
                        Lo3GeslachtsaanduidingEnum.MAN.asElement(),
                        Lo3String.wrap("9876543210"),
                        Lo3String.wrap("5678901234"),
                        Lo3AanduidingNaamgebruikCodeEnum.EIGEN_GESLACHTSNAAM.asElement()),
                new Lo3PersoonInhoud(
                        Lo3String.wrap("2345678901"),
                        Lo3String.wrap("234567890"),
                        Lo3String.wrap("andere namen"),
                        new Lo3AdellijkeTitelPredikaatCode("J"),
                        Lo3String.wrap("ander vv."),
                        Lo3String.wrap("andere geslachtsnaam"),
                        new Lo3Datum(19950101),
                        new Lo3GemeenteCode(STRING_4321),
                        new Lo3LandCode(STRING_4321),
                        Lo3GeslachtsaanduidingEnum.VROUW.asElement(),
                        Lo3String.wrap("9876543211"),
                        Lo3String.wrap("5678901231"),
                        Lo3AanduidingNaamgebruikCodeEnum.GESLACHTNAAM_PARTER_VOOR_EIGEN_GESLACHTSNAAM.asElement()));
    }

    @Test
    public void testLo3ReisdocumentInhoud() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
                new Lo3ReisdocumentInhoud(
                        new Lo3SoortNederlandsReisdocument("P"),
                        Lo3String.wrap("P12345678"),
                        new Lo3Datum(20010101),
                        new Lo3AutoriteitVanAfgifteNederlandsReisdocument(STRING_1234),
                        new Lo3Datum(20100101),
                        new Lo3Datum(20080101),
                        Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum.INGEHOUDEN.asElement(),
                        null),
                new Lo3ReisdocumentInhoud(
                        new Lo3SoortNederlandsReisdocument("P"),
                        Lo3String.wrap("P12345678"),
                        new Lo3Datum(20010101),
                        new Lo3AutoriteitVanAfgifteNederlandsReisdocument(STRING_1234),
                        new Lo3Datum(20100101),
                        new Lo3Datum(20080101),
                        Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum.INGEHOUDEN.asElement(),
                        null),
                new Lo3ReisdocumentInhoud(
                        new Lo3SoortNederlandsReisdocument("R"),
                        Lo3String.wrap("R12345678"),
                        new Lo3Datum(20050101),
                        new Lo3AutoriteitVanAfgifteNederlandsReisdocument(STRING_4321),
                        new Lo3Datum(20110101),
                        new Lo3Datum(20090101),
                        Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum.VERMIST.asElement(),
                        null));
    }

    @Test
    public void testSignaleringLo3ReisdocumentInhoud() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new Lo3ReisdocumentInhoud(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null),
                new Lo3ReisdocumentInhoud(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null),
                new Lo3ReisdocumentInhoud(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        Lo3SignaleringEnum.SIGNALERING.asElement()));
    }

    @Test
    public void testLo3VerblijfstitelInhoud() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new Lo3VerblijfstitelInhoud(new Lo3AanduidingVerblijfstitelCode(STRING_1234), new Lo3Datum(
                20120101), new Lo3Datum(20120101)), new Lo3VerblijfstitelInhoud(
                new Lo3AanduidingVerblijfstitelCode(STRING_1234),
                new Lo3Datum(20120101),
                new Lo3Datum(20120101)), new Lo3VerblijfstitelInhoud(new Lo3AanduidingVerblijfstitelCode(STRING_4321), new Lo3Datum(20100101), new Lo3Datum(
                20100101)));
    }

    @Test
    public void testLo3HuwelijkOfGpInhoud() throws NoSuchMethodException, IllegalAccessException {
        final Lo3HuwelijkOfGpInhoud huwelijkInhoud =
                new Lo3HuwelijkOfGpInhoud(
                        Lo3String.wrap("1234567890"),
                        Lo3String.wrap("123456789"),
                        Lo3String.wrap(VOORNAMEN),
                        new Lo3AdellijkeTitelPredikaatCode("B"),
                        Lo3String.wrap("voorv"),
                        Lo3String.wrap(GESLACHTSNAAM),
                        new Lo3Datum(20120101),
                        new Lo3GemeenteCode(STRING_1234),
                        new Lo3LandCode(STRING_1234),
                        Lo3GeslachtsaanduidingEnum.MAN.asElement(),
                        new Lo3Datum(20120101),
                        new Lo3GemeenteCode(STRING_1234),
                        new Lo3LandCode(STRING_1234),
                        null,
                        null,
                        null,
                        null,
                        Lo3SoortVerbintenisEnum.HUWELIJK.asElement());
        final Lo3HuwelijkOfGpInhoud zelfdeHuwelijkInhoud =
                new Lo3HuwelijkOfGpInhoud(
                        Lo3String.wrap("1234567890"),
                        Lo3String.wrap("123456789"),
                        Lo3String.wrap(VOORNAMEN),
                        new Lo3AdellijkeTitelPredikaatCode("B"),
                        Lo3String.wrap("voorv"),
                        Lo3String.wrap(GESLACHTSNAAM),
                        new Lo3Datum(20120101),
                        new Lo3GemeenteCode(STRING_1234),
                        new Lo3LandCode(STRING_1234),
                        Lo3GeslachtsaanduidingEnum.MAN.asElement(),
                        new Lo3Datum(20120101),
                        new Lo3GemeenteCode(STRING_1234),
                        new Lo3LandCode(STRING_1234),
                        null,
                        null,
                        null,
                        null,
                        Lo3SoortVerbintenisEnum.HUWELIJK.asElement());
        final Lo3HuwelijkOfGpInhoud partnerschapInhoud =
                new Lo3HuwelijkOfGpInhoud(
                        Lo3String.wrap("9876543210"),
                        Lo3String.wrap("123456389"),
                        Lo3String.wrap(ANDERS),
                        new Lo3AdellijkeTitelPredikaatCode("J"),
                        Lo3String.wrap("vv"),
                        Lo3String.wrap(ANDERS),
                        new Lo3Datum(20110101),
                        new Lo3GemeenteCode(STRING_4321),
                        new Lo3LandCode(STRING_4321),
                        Lo3GeslachtsaanduidingEnum.MAN.asElement(),
                        new Lo3Datum(20120101),
                        new Lo3GemeenteCode(STRING_4321),
                        new Lo3LandCode(STRING_4321),
                        null,
                        null,
                        null,
                        null,
                        Lo3SoortVerbintenisEnum.GEREGISTREERD_PARTNERSCHAP.asElement());

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(huwelijkInhoud, zelfdeHuwelijkInhoud, partnerschapInhoud);
    }

    @Test
    public void testLo3OuderInhoud() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
                VerplichteStapel.createOuderInhoud("1234567890"),
                VerplichteStapel.createOuderInhoud("1234567890"),
                new Lo3OuderInhoud(
                        Lo3String.wrap("9876543210"),
                        Lo3String.wrap("333456789"),
                        Lo3String.wrap(ANDERS),
                        null,
                        null,
                        Lo3String.wrap(ANDERS),
                        new Lo3Datum(19700101),
                        new Lo3GemeenteCode("8088"),
                        new Lo3LandCode("7373"),
                        Lo3GeslachtsaanduidingEnum.ONBEKEND.asElement(),
                        new Lo3Datum(19990101)));
    }

    @Test
    public void testLo3VerblijfplaatsInhoud() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
                VerplichteStapel.createVerblijfplaatsInhoud(),
                VerplichteStapel.createVerblijfplaatsInhoud(),
                new Lo3VerblijfplaatsInhoud(
                        new Lo3GemeenteCode(STRING_4321),
                        new Lo3Datum(20110101),
                        Lo3FunctieAdresEnum.BRIEFADRES.asElement(),
                        null,
                        new Lo3Datum(20110101),
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
                        Lo3String.wrap("locatieBeschrijving2"),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        Lo3AangifteAdreshoudingEnum.AMBSTHALVE.asElement(),
                        null));
    }

    @Test
    public void testLo3KindInhoud() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
                new Lo3KindInhoud(
                        Lo3String.wrap("1234567890"),
                        Lo3String.wrap("123456789"),
                        Lo3String.wrap(VOORNAMEN),
                        new Lo3AdellijkeTitelPredikaatCode("B"),
                        Lo3String.wrap("van"),
                        Lo3String.wrap(GESLACHTSNAAM),
                        new Lo3Datum(20120101),
                        new Lo3GemeenteCode(STRING_1234),
                        new Lo3LandCode(STRING_1234)),
                new Lo3KindInhoud(
                        Lo3String.wrap("1234567890"),
                        Lo3String.wrap("123456789"),
                        Lo3String.wrap(VOORNAMEN),
                        new Lo3AdellijkeTitelPredikaatCode("B"),
                        Lo3String.wrap("van"),
                        Lo3String.wrap(GESLACHTSNAAM),
                        new Lo3Datum(20120101),
                        new Lo3GemeenteCode(STRING_1234),
                        new Lo3LandCode(STRING_1234)),
                new Lo3KindInhoud(
                        Lo3String.wrap("1234567890"),
                        Lo3String.wrap("123456789"),
                        Lo3String.wrap("andere voornamen"),
                        new Lo3AdellijkeTitelPredikaatCode("J"),
                        Lo3String.wrap(ANDERS),
                        Lo3String.wrap("andere geslachtsnaam"),
                        new Lo3Datum(20100101),
                        new Lo3GemeenteCode(STRING_4321),
                        new Lo3LandCode(STRING_4321)));
    }

    @Test
    public void testLo3GezagsverhoudingInhoud() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
                new Lo3GezagsverhoudingInhoud(null, Lo3IndicatieCurateleregisterEnum.CURATOR_AANGESTELD.asElement()),
                new Lo3GezagsverhoudingInhoud(null, Lo3IndicatieCurateleregisterEnum.CURATOR_AANGESTELD.asElement()),
                new Lo3GezagsverhoudingInhoud(Lo3IndicatieGezagMinderjarigeEnum.OUDER_2.asElement(), null));
    }

    @Test
    public void testLo3KiesrechtInhoud() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new Lo3KiesrechtInhoud(
                Lo3AanduidingEuropeesKiesrechtEnum.UITGESLOTEN.asElement(),
                new Lo3Datum(20040101),
                new Lo3Datum(20040102),
                Lo3AanduidingUitgeslotenKiesrechtEnum.UITGESLOTEN_KIESRECHT.asElement(),
                new Lo3Datum(20040103)), new Lo3KiesrechtInhoud(
                Lo3AanduidingEuropeesKiesrechtEnum.UITGESLOTEN.asElement(),
                new Lo3Datum(20040101),
                new Lo3Datum(20040102),
                Lo3AanduidingUitgeslotenKiesrechtEnum.UITGESLOTEN_KIESRECHT.asElement(),
                new Lo3Datum(20040103)), new Lo3KiesrechtInhoud(
                Lo3AanduidingEuropeesKiesrechtEnum.ONTVANGT_OPROEP.asElement(),
                new Lo3Datum(19990101),
                null,
                null,
                null));
    }
}
