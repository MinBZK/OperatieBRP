/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.categorie;

import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingBezitBuitenlandsReisdocumentEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingEuropeesKiesrechtEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingNaamgebruikCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingUitgeslotenKiesrechtEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AangifteAdreshoudingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3FunctieAdresEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieCurateleregisterEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieGezagMinderjarigeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatiePKVolledigGeconverteerdCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3SignaleringEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LengteHouder;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.testutils.EqualsAndHashcodeTester;
import nl.moderniseringgba.migratie.testutils.VerplichteStapel;

import org.junit.Test;

//CHECKSTYLE:OFF
public final class Lo3CategorieTest {
    @Test
    public void testLo3InschrijvingInhoud() throws Exception {

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new Lo3InschrijvingInhoud(new Lo3Datum(20120101),
                new Lo3Datum(20120101), Lo3RedenOpschortingBijhoudingCodeEnum.EMIGRATIE.asElement(), new Lo3Datum(
                        15330103), new Lo3GemeenteCode("0518"),
                Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement(), 1,
                new Lo3Datumtijdstempel(12345678901234567l),
                Lo3IndicatiePKVolledigGeconverteerdCodeEnum.VOLLEDIG_GECONVERTEERD.asElement()),
                new Lo3InschrijvingInhoud(new Lo3Datum(20120101), new Lo3Datum(20120101),
                        Lo3RedenOpschortingBijhoudingCodeEnum.EMIGRATIE.asElement(), new Lo3Datum(15330103),
                        new Lo3GemeenteCode("0518"), Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement(), 1,
                        new Lo3Datumtijdstempel(12345678901234567l),
                        Lo3IndicatiePKVolledigGeconverteerdCodeEnum.VOLLEDIG_GECONVERTEERD.asElement()),
                new Lo3InschrijvingInhoud(new Lo3Datum(20140506), new Lo3Datum(21000304),
                        Lo3RedenOpschortingBijhoudingCodeEnum.ONBEKEND.asElement(), new Lo3Datum(19780507),
                        new Lo3GemeenteCode("1234"), Lo3IndicatieGeheimCodeEnum.NIET_AAN_KERKEN.asElement(), 2,
                        new Lo3Datumtijdstempel(76543210987654321l), null));
    }

    @Test
    public void testLo3NationaliteitInhoud() throws Exception {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(
                "1234"), new Lo3RedenNederlandschapCode("020"), null, null), new Lo3NationaliteitInhoud(
                new Lo3NationaliteitCode("1234"), new Lo3RedenNederlandschapCode("020"), null, null),
                new Lo3NationaliteitInhoud(new Lo3NationaliteitCode("4321"), new Lo3RedenNederlandschapCode("020"),
                        null, null));
    }

    @Test
    public void testLo3OverlijdenInhoud() throws Exception {
        EqualsAndHashcodeTester
                .testEqualsHashcodeAndToString(new Lo3OverlijdenInhoud(new Lo3Datum(20120101), new Lo3GemeenteCode(
                        "1234"), new Lo3LandCode("1234")), new Lo3OverlijdenInhoud(new Lo3Datum(20120101),
                        new Lo3GemeenteCode("1234"), new Lo3LandCode("1234")), new Lo3OverlijdenInhoud(new Lo3Datum(
                        20100101), new Lo3GemeenteCode("4321"), new Lo3LandCode("4321")));
    }

    @Test
    public void testLo3PersoonInhoud() throws Exception {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
                new Lo3PersoonInhoud(1234567890L, 123456789L, "voor namen", new Lo3AdellijkeTitelPredikaatCode("B"),
                        "voorv.", "geslachtsnaam", new Lo3Datum(19900101), new Lo3GemeenteCode("1234"),
                        new Lo3LandCode("1234"), Lo3GeslachtsaanduidingEnum.MAN.asElement(),
                        Lo3AanduidingNaamgebruikCodeEnum.EIGEN_GESLACHTSNAAM.asElement(), 9876543210L, 5678901234L),
                new Lo3PersoonInhoud(1234567890L, 123456789L, "voor namen", new Lo3AdellijkeTitelPredikaatCode("B"),
                        "voorv.", "geslachtsnaam", new Lo3Datum(19900101), new Lo3GemeenteCode("1234"),
                        new Lo3LandCode("1234"), Lo3GeslachtsaanduidingEnum.MAN.asElement(),
                        Lo3AanduidingNaamgebruikCodeEnum.EIGEN_GESLACHTSNAAM.asElement(), 9876543210L, 5678901234L),
                new Lo3PersoonInhoud(2345678901L, 234567890L, "andere namen",
                        new Lo3AdellijkeTitelPredikaatCode("J"), "ander vv.", "andere geslachtsnaam", new Lo3Datum(
                                19950101), new Lo3GemeenteCode("4321"), new Lo3LandCode("4321"),
                        Lo3GeslachtsaanduidingEnum.VROUW.asElement(),
                        Lo3AanduidingNaamgebruikCodeEnum.GESLACHTNAAM_PARTER_VOOR_EIGEN_GESLACHTSNAAM.asElement(),
                        9876543211L, 5678901231L));
    }

    @Test
    public void testLo3ReisdocumentInhoud() throws Exception {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new Lo3ReisdocumentInhoud(
                new Lo3SoortNederlandsReisdocument("P"), "P12345678", new Lo3Datum(20010101),
                new Lo3AutoriteitVanAfgifteNederlandsReisdocument("1234"), new Lo3Datum(20100101), new Lo3Datum(
                        20080101), Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum.INGEHOUDEN.asElement(),
                new Lo3LengteHouder(180), null, null), new Lo3ReisdocumentInhoud(new Lo3SoortNederlandsReisdocument(
                "P"), "P12345678", new Lo3Datum(20010101), new Lo3AutoriteitVanAfgifteNederlandsReisdocument("1234"),
                new Lo3Datum(20100101), new Lo3Datum(20080101),
                Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum.INGEHOUDEN.asElement(),
                new Lo3LengteHouder(180), null, null), new Lo3ReisdocumentInhoud(new Lo3SoortNederlandsReisdocument(
                "R"), "R12345678", new Lo3Datum(20050101), new Lo3AutoriteitVanAfgifteNederlandsReisdocument("4321"),
                new Lo3Datum(20110101), new Lo3Datum(20090101),
                Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum.VERMIST.asElement(), new Lo3LengteHouder(
                        190), null, null));
    }

    @Test
    public void testSignaleringLo3ReisdocumentInhoud() throws Exception {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new Lo3ReisdocumentInhoud(null, null, null, null, null,
                null, null, null, null, Lo3AanduidingBezitBuitenlandsReisdocumentEnum.AANDUIDING.asElement()),
                new Lo3ReisdocumentInhoud(null, null, null, null, null, null, null, null, null,
                        Lo3AanduidingBezitBuitenlandsReisdocumentEnum.AANDUIDING.asElement()),
                new Lo3ReisdocumentInhoud(null, null, null, null, null, null, null, null,
                        Lo3SignaleringEnum.SIGNALERING.asElement(), null));
    }

    @Test
    public void testLo3VerblijfstitelInhoud() throws Exception {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new Lo3VerblijfstitelInhoud(
                new Lo3AanduidingVerblijfstitelCode("1234"), new Lo3Datum(20120101), new Lo3Datum(20120101)),
                new Lo3VerblijfstitelInhoud(new Lo3AanduidingVerblijfstitelCode("1234"), new Lo3Datum(20120101),
                        new Lo3Datum(20120101)), new Lo3VerblijfstitelInhoud(new Lo3AanduidingVerblijfstitelCode(
                        "4321"), new Lo3Datum(20100101), new Lo3Datum(20100101)));
    }

    @Test
    public void testLo3HuwelijkOfGpInhoud() throws Exception {
        final Lo3HuwelijkOfGpInhoud huwelijkInhoud =
                new Lo3HuwelijkOfGpInhoud(1234567890L, 123456789L, "voornamen", new Lo3AdellijkeTitelPredikaatCode(
                        "B"), "voorv", "geslachtsnaam", new Lo3Datum(20120101), new Lo3GemeenteCode("1234"),
                        new Lo3LandCode("1234"), Lo3GeslachtsaanduidingEnum.MAN.asElement(), new Lo3Datum(20120101),
                        new Lo3GemeenteCode("1234"), new Lo3LandCode("1234"), null, null, null, null,
                        Lo3SoortVerbintenisEnum.HUWELIJK.asElement());
        final Lo3HuwelijkOfGpInhoud zelfdeHuwelijkInhoud =
                new Lo3HuwelijkOfGpInhoud(1234567890L, 123456789L, "voornamen", new Lo3AdellijkeTitelPredikaatCode(
                        "B"), "voorv", "geslachtsnaam", new Lo3Datum(20120101), new Lo3GemeenteCode("1234"),
                        new Lo3LandCode("1234"), Lo3GeslachtsaanduidingEnum.MAN.asElement(), new Lo3Datum(20120101),
                        new Lo3GemeenteCode("1234"), new Lo3LandCode("1234"), null, null, null, null,
                        Lo3SoortVerbintenisEnum.HUWELIJK.asElement());
        final Lo3HuwelijkOfGpInhoud partnerschapInhoud =
                new Lo3HuwelijkOfGpInhoud(9876543210L, 123456389L, "anders", new Lo3AdellijkeTitelPredikaatCode("J"),
                        "vv", "anders", new Lo3Datum(20110101), new Lo3GemeenteCode("4321"), new Lo3LandCode("4321"),
                        Lo3GeslachtsaanduidingEnum.MAN.asElement(), new Lo3Datum(20120101), new Lo3GemeenteCode(
                                "4321"), new Lo3LandCode("4321"), null, null, null, null,
                        Lo3SoortVerbintenisEnum.GEREGISTREERD_PARTNERSCHAP.asElement());

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(huwelijkInhoud, zelfdeHuwelijkInhoud,
                partnerschapInhoud);
    }

    @Test
    public void testLo3OuderInhoud() throws Exception {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(VerplichteStapel.createOuderInhoud(1234567890L),
                VerplichteStapel.createOuderInhoud(1234567890L), new Lo3OuderInhoud(9876543210L, 333456789L,
                        "anders", null, null, "anders", new Lo3Datum(19700101), new Lo3GemeenteCode("8088"),
                        new Lo3LandCode("7373"), Lo3GeslachtsaanduidingEnum.ONBEKEND.asElement(), new Lo3Datum(
                                19990101)));
    }

    @Test
    public void testLo3VerblijfplaatsInhoud() throws Exception {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(VerplichteStapel.createVerblijfplaatsInhoud(),
                VerplichteStapel.createVerblijfplaatsInhoud(),
                new Lo3VerblijfplaatsInhoud(new Lo3GemeenteCode("4321"), new Lo3Datum(20110101),
                        Lo3FunctieAdresEnum.BRIEFADRES.asElement(), null, new Lo3Datum(20110101), null, null, null,
                        null, null, null, null, null, null, null, "locatieBeschrijving2", null, null, null, null,
                        null, null, null, Lo3AangifteAdreshoudingEnum.AMBSTHALVE.asElement(), null));
    }

    @Test
    public void testLo3KindInhoud() throws Exception {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new Lo3KindInhoud(1234567890L, 123456789L, "voornamen",
                new Lo3AdellijkeTitelPredikaatCode("B"), "van", "geslachtsnaam", new Lo3Datum(20120101),
                new Lo3GemeenteCode("1234"), new Lo3LandCode("1234"), true), new Lo3KindInhoud(1234567890L,
                123456789L, "voornamen", new Lo3AdellijkeTitelPredikaatCode("B"), "van", "geslachtsnaam",
                new Lo3Datum(20120101), new Lo3GemeenteCode("1234"), new Lo3LandCode("1234"), true),
                new Lo3KindInhoud(1234567890L, 123456789L, "andere voornamen",
                        new Lo3AdellijkeTitelPredikaatCode("J"), "anders", "andere geslachtsnaam", new Lo3Datum(
                                20100101), new Lo3GemeenteCode("4321"), new Lo3LandCode("4321"), true));
    }

    @Test
    public void testLo3GezagsverhoudingInhoud() throws Exception {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new Lo3GezagsverhoudingInhoud(null,
                Lo3IndicatieCurateleregisterEnum.CURATOR_AANGESTELD.asElement()), new Lo3GezagsverhoudingInhoud(null,
                Lo3IndicatieCurateleregisterEnum.CURATOR_AANGESTELD.asElement()), new Lo3GezagsverhoudingInhoud(
                Lo3IndicatieGezagMinderjarigeEnum.OUDER_2.asElement(), null));
    }

    @Test
    public void testLo3KiesrechtInhoud() throws Exception {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new Lo3KiesrechtInhoud(
                Lo3AanduidingEuropeesKiesrechtEnum.UITGESLOTEN.asElement(), new Lo3Datum(20040101), new Lo3Datum(
                        20040102), Lo3AanduidingUitgeslotenKiesrechtEnum.UITGESLOTEN_KIESRECHT.asElement(),
                new Lo3Datum(20040103)),
                new Lo3KiesrechtInhoud(Lo3AanduidingEuropeesKiesrechtEnum.UITGESLOTEN.asElement(), new Lo3Datum(
                        20040101), new Lo3Datum(20040102),
                        Lo3AanduidingUitgeslotenKiesrechtEnum.UITGESLOTEN_KIESRECHT.asElement(), new Lo3Datum(
                                20040103)),
                new Lo3KiesrechtInhoud(Lo3AanduidingEuropeesKiesrechtEnum.ONTVANGT_OPROEP.asElement(), new Lo3Datum(
                        19990101), null, null, null));
    }
}
