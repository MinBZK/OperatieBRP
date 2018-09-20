/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortMigratieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.testutils.EqualsAndHashcodeTester;
import org.junit.Test;

public class BrpGroepInhoudTest {

    private static final String STRING_1234 = "1234";
    private static final String WOONPLAATSNAAM_GEBOORTE = "woonplaatsnaamGeboorte";
    private static final String BUITENLANDSE_GEBOORTEPLAATS = "buitenlandseGeboorteplaats";
    private static final String BUITENLANDSE_REGIO_GEBOORTE = "buitenlandseRegioGeboorte";
    private static final String OMSCHRIJVING_GEBOORTELOCATIE = "omschrijvingGeboortelocatie";
    private static final String STRING_5678 = "5678";
    private static final String ANDERE_BUITENLANDSE_GEBOORTEPLAATS = "andereBuitenlandseGeboorteplaats";
    private static final String ANDERE_BUITENLANDSE_REGIO_GEBOORTE = "andereBuitenlandseRegioGeboorte";
    private static final String ANDERE_OMSCHRIJVING_GEBOORTELOCATIE = "andereOmschrijvingGeboortelocatie";
    private static final String STRING_4321 = "4321";
    private static final String STRING_6030 = "6030";

    @Test
    public void testEqualsHashcodeAndToString() throws NoSuchMethodException, IllegalAccessException {

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpNaamgebruikInhoud(BrpNaamgebruikCode.E, null, null, null, null, null, null, null),
            new BrpNaamgebruikInhoud(BrpNaamgebruikCode.E, null, null, null, null, null, null, null),
            new BrpNaamgebruikInhoud(BrpNaamgebruikCode.P, null, null, null, null, null, null, null));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpBehandeldAlsNederlanderIndicatieInhoud(new BrpBoolean(true), null, null),
            new BrpBehandeldAlsNederlanderIndicatieInhoud(new BrpBoolean(true), null, null),
            null);

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud(new BrpBoolean(true), null, null),
            new BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud(new BrpBoolean(true), null, null),
            null);

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpGeboorteInhoud(
                new BrpDatum(20120101, null),
                new BrpGemeenteCode(Short.parseShort(STRING_1234)),
                new BrpString(WOONPLAATSNAAM_GEBOORTE),
                new BrpString(BUITENLANDSE_GEBOORTEPLAATS),
                new BrpString(BUITENLANDSE_REGIO_GEBOORTE),
                new BrpLandOfGebiedCode(Short.parseShort(STRING_1234)),
                new BrpString(OMSCHRIJVING_GEBOORTELOCATIE)),
            new BrpGeboorteInhoud(
                new BrpDatum(20120101, null),
                new BrpGemeenteCode(Short.parseShort(STRING_1234)),
                new BrpString(WOONPLAATSNAAM_GEBOORTE),
                new BrpString(BUITENLANDSE_GEBOORTEPLAATS),
                new BrpString(BUITENLANDSE_REGIO_GEBOORTE),
                new BrpLandOfGebiedCode(Short.parseShort(STRING_1234)),
                new BrpString(OMSCHRIJVING_GEBOORTELOCATIE)),
            new BrpGeboorteInhoud(
                new BrpDatum(20110101, null),
                new BrpGemeenteCode(Short.parseShort(STRING_5678)),
                new BrpString(WOONPLAATSNAAM_GEBOORTE),
                new BrpString(ANDERE_BUITENLANDSE_GEBOORTEPLAATS),
                new BrpString(ANDERE_BUITENLANDSE_REGIO_GEBOORTE),
                new BrpLandOfGebiedCode(Short.parseShort(STRING_5678)),
                new BrpString(ANDERE_OMSCHRIJVING_GEBOORTELOCATIE)));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN),
            new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN),
            new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.VROUW));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpGeslachtsnaamcomponentInhoud(
                new BrpString("voorvoegsel"),
                new BrpCharacter(' '),
                new BrpString("stam"),
                new BrpPredicaatCode("B"),
                new BrpAdellijkeTitelCode("B"),
                new BrpInteger(1)),
            new BrpGeslachtsnaamcomponentInhoud(
                new BrpString("voorvoegsel"),
                new BrpCharacter(' '),
                new BrpString("stam"),
                new BrpPredicaatCode("B"),
                new BrpAdellijkeTitelCode("B"),
                new BrpInteger(1)),
            new BrpGeslachtsnaamcomponentInhoud(
                new BrpString("anderVoorvoegsel"),
                new BrpCharacter(' '),
                new BrpString("andereStam"),
                null,
                null,
                new BrpInteger(2)));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpIdentificatienummersInhoud(new BrpLong(1234567890L), new BrpInteger(123456789)),
            new BrpIdentificatienummersInhoud(new BrpLong(1234567890L), new BrpInteger(123456789)),
            new BrpIdentificatienummersInhoud(new BrpLong(2345678901L), new BrpInteger(234567890)));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpInschrijvingInhoud(new BrpDatum(20120101, null), new BrpLong(1L), BrpDatumTijd.fromDatum(20010101, null)),
            new BrpInschrijvingInhoud(new BrpDatum(20120101, null), new BrpLong(1L), BrpDatumTijd.fromDatum(20010101, null)),
            new BrpInschrijvingInhoud(new BrpDatum(20120101, null), new BrpLong(2L), BrpDatumTijd.fromDatum(20020202, null)));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpNummerverwijzingInhoud(
            new BrpLong(1234567890L),
            new BrpLong(1234567891L),
            new BrpInteger(123456789),
            new BrpInteger(987654321)), new BrpNummerverwijzingInhoud(
            new BrpLong(1234567890L),
            new BrpLong(1234567891L),
            new BrpInteger(123456789),
            new BrpInteger(987654321)), new BrpNummerverwijzingInhoud(
            new BrpLong(1234567899L),
            new BrpLong(1234567892L),
            new BrpInteger(123456788),
            new BrpInteger(987654322)));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToStringBeperkt(
            new BrpNationaliteitInhoud(
                new BrpNationaliteitCode(Short.parseShort(STRING_1234)),
                new BrpRedenVerkrijgingNederlandschapCode(Short.parseShort(STRING_1234)),
                new BrpRedenVerliesNederlandschapCode(Short.parseShort(STRING_5678)),
                null,
                null,
                null,
                null),
            new BrpNationaliteitInhoud(
                new BrpNationaliteitCode(Short.parseShort(STRING_1234)),
                new BrpRedenVerkrijgingNederlandschapCode(Short.parseShort(STRING_1234)),
                new BrpRedenVerliesNederlandschapCode(Short.parseShort(STRING_5678)),
                null,
                null,
                null,
                null),
            new BrpNationaliteitInhoud(

                new BrpNationaliteitCode(Short.parseShort(STRING_1234)),
                new BrpRedenVerkrijgingNederlandschapCode(Short.parseShort(STRING_4321)),
                new BrpRedenVerliesNederlandschapCode(Short.parseShort("8765")),
                null,
                null,
                null,
                null));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpOverlijdenInhoud(
                new BrpDatum(20120101, null),
                new BrpGemeenteCode(Short.parseShort(STRING_1234)),
                new BrpString(WOONPLAATSNAAM_GEBOORTE),
                new BrpString("buitenlandsePlaats"),
                new BrpString("buitenlandseRegio"),
                new BrpLandOfGebiedCode(Short.parseShort(STRING_1234)),
                new BrpString("omschrijvingLocatie")),
            new BrpOverlijdenInhoud(
                new BrpDatum(20120101, null),
                new BrpGemeenteCode(Short.parseShort(STRING_1234)),
                new BrpString(WOONPLAATSNAAM_GEBOORTE),
                new BrpString("buitenlandsePlaats"),
                new BrpString("buitenlandseRegio"),
                new BrpLandOfGebiedCode(Short.parseShort(STRING_1234)),
                new BrpString("omschrijvingLocatie")),
            new BrpOverlijdenInhoud(
                new BrpDatum(20130101, null),
                new BrpGemeenteCode(Short.parseShort(STRING_4321)),
                new BrpString("geboorteWoonplaatsnaam"),
                new BrpString("andereBuitenlandsePlaats"),
                new BrpString("andereBuitenlandseRegio"),
                new BrpLandOfGebiedCode(Short.parseShort(STRING_4321)),
                new BrpString("andereOmschrijvingLocatie")));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpReisdocumentInhoud(
            new BrpSoortNederlandsReisdocumentCode("P"),
            new BrpString("p12345678"),
            new BrpDatum(20110101, null),
            new BrpDatum(20050101, null),
            new BrpReisdocumentAutoriteitVanAfgifteCode(STRING_1234),
            new BrpDatum(20100101, null),
            new BrpDatum(20080101, null),
                new BrpAanduidingInhoudingOfVermissingReisdocumentCode('I')),
            new BrpReisdocumentInhoud(
                new BrpSoortNederlandsReisdocumentCode("P"),
                new BrpString("p12345678"),
                new BrpDatum(
            20110101,
            null), new BrpDatum(20050101, null), new BrpReisdocumentAutoriteitVanAfgifteCode(STRING_1234), new BrpDatum(20100101, null), new BrpDatum(
            20080101,
 null),
                new BrpAanduidingInhoudingOfVermissingReisdocumentCode('I')),
            new BrpReisdocumentInhoud(
            new BrpSoortNederlandsReisdocumentCode("R"),
            new BrpString("R12345678"),
            new BrpDatum(20110101, null),
            new BrpDatum(20050101, null),
            new BrpReisdocumentAutoriteitVanAfgifteCode(STRING_1234),
            new BrpDatum(20100101, null),
            null,
                null));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpVastgesteldNietNederlanderIndicatieInhoud(new BrpBoolean(true), null, null),
            new BrpVastgesteldNietNederlanderIndicatieInhoud(new BrpBoolean(true), null, null),
            null);

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpVerblijfsrechtInhoud(new BrpVerblijfsrechtCode((short) 1), new BrpDatum(20100101, null), new BrpDatum(20150101, null),new BrpDatum(20150102, null)),
            new BrpVerblijfsrechtInhoud(new BrpVerblijfsrechtCode((short) 1), new BrpDatum(20100101, null), new BrpDatum(20150101, null),new BrpDatum(20150102, null)),
            new BrpVerblijfsrechtInhoud(new BrpVerblijfsrechtCode((short) 2), new BrpDatum(20110101, null), new BrpDatum(20160101, null),new BrpDatum(20150102, null)));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpVoornaamInhoud(new BrpString("voornaam"), new BrpInteger(1)),
            new BrpVoornaamInhoud(new BrpString("voornaam"), new BrpInteger(1)),
            new BrpVoornaamInhoud(new BrpString("andereVoornaam"), new BrpInteger(2)));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpHistorie(
                new BrpDatum(20120203, null),
                new BrpDatum(20130203, null),
                BrpDatumTijd.fromDatum(20120203, null),
                BrpDatumTijd.fromDatum(20120203, null),
                null),
            new BrpHistorie(
                new BrpDatum(20120203, null),
                new BrpDatum(20130203, null),
                BrpDatumTijd.fromDatum(20120203, null),
                BrpDatumTijd.fromDatum(20120203, null),
                null),
            new BrpHistorie(
                new BrpDatum(20020203, null),
                new BrpDatum(20030203, null),
                BrpDatumTijd.fromDatum(20020203, null),
                BrpDatumTijd.fromDatum(20020203, null),
                null));
    }

    @Test
    public void testAttributen() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpAanduidingBijHuisnummerCode("TO"),
            new BrpAanduidingBijHuisnummerCode("TO"),
            new BrpAanduidingBijHuisnummerCode("BY"));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpAangeverCode('A'), new BrpAangeverCode('A'), new BrpAangeverCode('S'));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpAdellijkeTitelCode("B"),
            new BrpAdellijkeTitelCode("B"),
            new BrpAdellijkeTitelCode("P"));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpDatum(20120101, null), new BrpDatum(20120101, null), new BrpDatum(20120102, null));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            BrpDatumTijd.fromDatum(20120101, null),
            BrpDatumTijd.fromDatum(20120101, null),
            BrpDatumTijd.fromDatum(20120102, null));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpGemeenteCode(Short.parseShort(STRING_1234)),
            new BrpGemeenteCode(Short.parseShort(STRING_1234)),
            new BrpGemeenteCode(Short.parseShort(STRING_4321)));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpLandOfGebiedCode(Short.parseShort(STRING_6030)),
            new BrpLandOfGebiedCode(Short.parseShort(STRING_6030)),
            new BrpLandOfGebiedCode(Short.parseShort("6031")));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpNationaliteitCode(Short.parseShort(STRING_6030)),
            new BrpNationaliteitCode(Short.parseShort(STRING_6030)),
            new BrpNationaliteitCode(Short.parseShort("6031")));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpPredicaatCode("J"), new BrpPredicaatCode("J"), new BrpPredicaatCode("JV"));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpRedenEindeRelatieCode('1'),
            new BrpRedenEindeRelatieCode('1'),
            new BrpRedenEindeRelatieCode('2'));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpRedenVerkrijgingNederlandschapCode(Short.parseShort(

                "1")),
            new BrpRedenVerkrijgingNederlandschapCode(Short.parseShort("1")),
            new BrpRedenVerkrijgingNederlandschapCode(Short

        .parseShort(

            "2")));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpRedenVerliesNederlandschapCode(Short.parseShort("1")),
            new BrpRedenVerliesNederlandschapCode(Short.parseShort("1")),
            new BrpRedenVerliesNederlandschapCode(Short.parseShort("2")));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpRedenWijzigingVerblijfCode('1'),
            new BrpRedenWijzigingVerblijfCode('1'),
            new BrpRedenWijzigingVerblijfCode('2'));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpReisdocumentAutoriteitVanAfgifteCode("1"),
            new BrpReisdocumentAutoriteitVanAfgifteCode("1"),
            new BrpReisdocumentAutoriteitVanAfgifteCode("2"));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpAanduidingInhoudingOfVermissingReisdocumentCode('1'),
            new BrpAanduidingInhoudingOfVermissingReisdocumentCode('1'),
            new BrpAanduidingInhoudingOfVermissingReisdocumentCode('2'));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpSoortNederlandsReisdocumentCode("P"),
            new BrpSoortNederlandsReisdocumentCode("P"),
            new BrpSoortNederlandsReisdocumentCode("I"));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpVerblijfsrechtCode((short) 1),
            new BrpVerblijfsrechtCode((short) 1),
            new BrpVerblijfsrechtCode((short) 2));

        assertEquals(BrpGeslachtsaanduidingCode.MAN, new BrpGeslachtsaanduidingCode("M"));
        final String verkeerdeGeslachtsaanduidingCode = "X";
        assertEquals(verkeerdeGeslachtsaanduidingCode, new BrpGeslachtsaanduidingCode(verkeerdeGeslachtsaanduidingCode).getWaarde());

        assertEquals("P", BrpSoortBetrokkenheidCode.PARTNER.getWaarde());
        assertEquals("O", BrpSoortBetrokkenheidCode.OUDER.getWaarde());
        assertEquals("K", BrpSoortBetrokkenheidCode.KIND.getWaarde());

        assertEquals("H", BrpSoortRelatieCode.HUWELIJK.getWaarde());
        assertEquals("G", BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP.getWaarde());
        assertEquals("F", BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING.getWaarde());
    }

    @Test
    public void testIsLeegMethoden() {
        testIsNietLeeg(new BrpNaamgebruikInhoud(BrpNaamgebruikCode.E, null, null, null, null, null, null, null));

        testIsNietLeeg(new BrpBehandeldAlsNederlanderIndicatieInhoud(new BrpBoolean(true), null, null));

        testIsNietLeeg(new BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud(new BrpBoolean(true), null, null));

        testIsNietLeeg(
            new BrpGeboorteInhoud(
                new BrpDatum(20120101, null),
                new BrpGemeenteCode(Short.parseShort(STRING_1234)),
                new BrpString(WOONPLAATSNAAM_GEBOORTE),
                new BrpString(BUITENLANDSE_GEBOORTEPLAATS),
                new BrpString(BUITENLANDSE_REGIO_GEBOORTE),
                new BrpLandOfGebiedCode(Short.parseShort(STRING_1234)),
                new BrpString(OMSCHRIJVING_GEBOORTELOCATIE)));

        testIsNietLeeg(new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN));

        testIsNietLeeg(
            new BrpGeslachtsnaamcomponentInhoud(
                new BrpString("voorvoegsel"),
                new BrpCharacter(' '),
                new BrpString("naam"),
                new BrpPredicaatCode("B"),
                new BrpAdellijkeTitelCode("B"),
                new BrpInteger(1)));

        testIsNietLeeg(new BrpIdentificatienummersInhoud(new BrpLong(1234567890L), new BrpInteger(123456789)));

        testIsNietLeeg(new BrpInschrijvingInhoud(new BrpDatum(20120101, null), new BrpLong(1L), BrpDatumTijd.fromDatum(20010101, null)));

        testIsNietLeeg(
            new BrpNummerverwijzingInhoud(new BrpLong(1234567890L), new BrpLong(1234567891L), new BrpInteger(123456789), new BrpInteger(987654321)));

        testIsNietLeeg(
            new BrpNationaliteitInhoud(
                new BrpNationaliteitCode(Short.parseShort(STRING_1234)),
                new BrpRedenVerkrijgingNederlandschapCode(Short.parseShort(STRING_1234)),
                new BrpRedenVerliesNederlandschapCode(Short.parseShort(STRING_5678)),
                null,
                null,
                null,
                null));

        testIsNietLeeg(
            new BrpOverlijdenInhoud(
                new BrpDatum(20120101, null),
                new BrpGemeenteCode(Short.parseShort(STRING_1234)),
                new BrpString(WOONPLAATSNAAM_GEBOORTE),
                new BrpString("buitenlandsePlaats"),
                new BrpString("buitenlandseRegio"),
                new BrpLandOfGebiedCode(Short.parseShort(STRING_1234)),
                new BrpString("omschrijvingLocatie")));

        testIsNietLeeg(new BrpReisdocumentInhoud(
            new BrpSoortNederlandsReisdocumentCode("P"),
            new BrpString("p12345678"),
            new BrpDatum(20110101, null),
            new BrpDatum(20050101, null),
            new BrpReisdocumentAutoriteitVanAfgifteCode(STRING_1234),
            new BrpDatum(20100101, null),
            new BrpDatum(20080101, null),
                new BrpAanduidingInhoudingOfVermissingReisdocumentCode('I')));

        testIsNietLeeg(new BrpVastgesteldNietNederlanderIndicatieInhoud(new BrpBoolean(true), null, null));

        testIsNietLeeg(new BrpVerblijfsrechtInhoud(new BrpVerblijfsrechtCode((short) 1), new BrpDatum(20100101, null), new BrpDatum(20150101, null),new BrpDatum(20150102, null)));

        testIsNietLeeg(new BrpVoornaamInhoud(new BrpString("voornaam"), new BrpInteger(1)));
    }

    private void testIsNietLeeg(final BrpGroepInhoud inhoud) {
        assertFalse(inhoud.isLeeg());
    }

    private void testIsLeeg(final BrpGroepInhoud inhoud) {
        assertTrue(inhoud.isLeeg());
    }

    @Test
    public void testBrpAdres() throws NoSuchMethodException, IllegalAccessException {

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpAdresInhoud(
                BrpSoortAdresCode.W,
                new BrpRedenWijzigingVerblijfCode('V'),
                new BrpAangeverCode('A'),
                new BrpDatum(20100101, null),
                new BrpString("1312312"),
                new BrpString("123123213"),
                new BrpGemeenteCode(Short.parseShort("0518")),
                new BrpString("naam"),
                new BrpString("afk"),
                new BrpString("gemeentedeel"),
                new BrpInteger(123),
                new BrpCharacter('A'),
                new BrpString("-123"),
                new BrpString("1234AB"),
                new BrpString("woonplaatsnaam"),
                new BrpAanduidingBijHuisnummerCode("TO"),
                new BrpString("woonboot"),
                new BrpString("regel 1"),
                new BrpString("regel 2"),
                new BrpString("regel 3"),
                new BrpString("regel 4"),
                new BrpString("regel 5"),
                new BrpString("regel 6"),
                new BrpLandOfGebiedCode(Short.parseShort(STRING_6030)),
                null),
            new BrpAdresInhoud(
                BrpSoortAdresCode.W,
                new BrpRedenWijzigingVerblijfCode('V'),
                new BrpAangeverCode('A'),
                new BrpDatum(20100101, null),
                new BrpString("1312312"),
                new BrpString("123123213"),
                new BrpGemeenteCode(Short.parseShort("0518")),
                new BrpString("naam"),
                new BrpString("afk"),
                new BrpString("gemeentedeel"),
                new BrpInteger(123),
                new BrpCharacter('A'),
                new BrpString("-123"),
                new BrpString("1234AB"),
                new BrpString("woonplaatsnaam"),
                new BrpAanduidingBijHuisnummerCode("TO"),
                new BrpString("woonboot"),
                new BrpString("regel 1"),
                new BrpString("regel 2"),
                new BrpString("regel 3"),
                new BrpString("regel 4"),
                new BrpString("regel 5"),
                new BrpString("regel 6"),
                new BrpLandOfGebiedCode(Short.parseShort(STRING_6030)),
                null),
            new BrpAdresInhoud(
                BrpSoortAdresCode.B,
                new BrpRedenWijzigingVerblijfCode('E'),
                new BrpAangeverCode('S'),
                new BrpDatum(20030101, null),
                new BrpString("3453"),
                new BrpString("2345334"),
                new BrpGemeenteCode(Short.parseShort(STRING_1234)),
                new BrpString("asdsd"),
                new BrpString("vcc"),
                new BrpString("sfsd"),
                new BrpInteger(56765),
                new BrpCharacter('Z'),
                new BrpString("-E"),
                new BrpString("2444YY"),
                new BrpString("anderewoonplaatsnaam"),
                new BrpAanduidingBijHuisnummerCode("BY"),
                new BrpString("standplaats 23"),
                new BrpString("anders 1"),
                new BrpString("anders 2"),
                new BrpString("anders 3"),
                new BrpString("anders 4"),
                new BrpString("anders 5"),
                new BrpString("anders 6"),
                new BrpLandOfGebiedCode(Short.parseShort("3002")),
                null));

        testIsNietLeeg(
            new BrpAdresInhoud(
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
                null,
                null,
                null,
                new BrpLandOfGebiedCode(Short.parseShort(STRING_6030)),
                null));
        testIsLeeg(
            new BrpAdresInhoud(
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
                null,
                null,
                null,
                null,
                null));
    }

    @Test
    public void testBrpMigratie() throws NoSuchMethodException, IllegalAccessException {
        final BrpMigratieInhoud migratieInhoud2 =
                new BrpMigratieInhoud.Builder(BrpSoortMigratieCode.EMIGRATIE, new BrpLandOfGebiedCode((short) 2)).build();
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpMigratieInhoud.Builder(BrpSoortMigratieCode.IMMIGRATIE, new BrpLandOfGebiedCode((short) 1)).build(),
            new BrpMigratieInhoud.Builder(BrpSoortMigratieCode.IMMIGRATIE, new BrpLandOfGebiedCode((short) 1)).build(),
            migratieInhoud2);
        testIsNietLeeg(new BrpMigratieInhoud.Builder(BrpSoortMigratieCode.IMMIGRATIE, new BrpLandOfGebiedCode((short) 1)).build());
        testIsLeeg(new BrpMigratieInhoud.Builder(null, null).build());
    }

    @Test
    public void testBrpBijhouding() throws NoSuchMethodException, IllegalAccessException {

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpBijhoudingInhoud(new BrpPartijCode(Integer.parseInt("501801")), BrpBijhoudingsaardCode.INGEZETENE, null, new BrpBoolean(Boolean.FALSE)),
            new BrpBijhoudingInhoud(new BrpPartijCode(Integer.parseInt("501801")), BrpBijhoudingsaardCode.INGEZETENE, null, new BrpBoolean(Boolean.FALSE)),
            new BrpBijhoudingInhoud(new BrpPartijCode(Integer.parseInt("765401")), BrpBijhoudingsaardCode.INGEZETENE, null, new BrpBoolean(Boolean.TRUE)));

        testIsNietLeeg(
            new BrpBijhoudingInhoud(
                new BrpPartijCode(Integer.parseInt("501801")),
                BrpBijhoudingsaardCode.INGEZETENE,
                null,
                new BrpBoolean(Boolean.FALSE)));
    }

    @Test
    public void testBrpUitsluitingKiesrecht() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpUitsluitingKiesrechtInhoud(new BrpBoolean(Boolean.TRUE), new BrpDatum(20030205, null)),
            new BrpUitsluitingKiesrechtInhoud(new BrpBoolean(Boolean.TRUE), new BrpDatum(20030205, null)),
            new BrpUitsluitingKiesrechtInhoud(new BrpBoolean(Boolean.FALSE), null));

        testIsNietLeeg(new BrpUitsluitingKiesrechtInhoud(new BrpBoolean(Boolean.TRUE), null));
        testIsLeeg(new BrpUitsluitingKiesrechtInhoud(null, null));
    }

    @Test
    public void testBrpDeelnameEuVerkiezingen() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpDeelnameEuVerkiezingenInhoud(new BrpBoolean(Boolean.TRUE), new BrpDatum(20030205, null), new BrpDatum(20030206, null)),
            new BrpDeelnameEuVerkiezingenInhoud(new BrpBoolean(Boolean.TRUE), new BrpDatum(20030205, null), new BrpDatum(20030206, null)),
            new BrpDeelnameEuVerkiezingenInhoud(new BrpBoolean(Boolean.FALSE), null, null));

        testIsNietLeeg(new BrpDeelnameEuVerkiezingenInhoud(new BrpBoolean(Boolean.TRUE), new BrpDatum(20030205, null), new BrpDatum(20030206, null)));
        testIsLeeg(new BrpDeelnameEuVerkiezingenInhoud(null, null, null));
    }

    @Test
    public void testBrpPersoonskaart() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpPersoonskaartInhoud(new BrpPartijCode(Integer.parseInt("051801")), new BrpBoolean(true)),
            new BrpPersoonskaartInhoud(new BrpPartijCode(Integer.parseInt("051801")), new BrpBoolean(true)),
            new BrpPersoonskaartInhoud(new BrpPartijCode(Integer.parseInt("123401")), new BrpBoolean(false)));

        testIsNietLeeg(new BrpPersoonskaartInhoud(new BrpPartijCode(Integer.parseInt("051801")), new BrpBoolean(true)));
        testIsLeeg(new BrpPersoonskaartInhoud(null, null));
    }

    @Test
    public void testBrpVerstrekkingsbeperking() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new BrpVerstrekkingsbeperkingIndicatieInhoud(new BrpBoolean(true), null, null),
            new BrpVerstrekkingsbeperkingIndicatieInhoud(new BrpBoolean(true), null, null),
            new BrpVerstrekkingsbeperkingIndicatieInhoud(new BrpBoolean(false), null, null));
        testIsNietLeeg(new BrpVerstrekkingsbeperkingIndicatieInhoud(new BrpBoolean(true), null, null));
        testIsLeeg(new BrpVerstrekkingsbeperkingIndicatieInhoud(new BrpBoolean(false), null, null));
    }
}
