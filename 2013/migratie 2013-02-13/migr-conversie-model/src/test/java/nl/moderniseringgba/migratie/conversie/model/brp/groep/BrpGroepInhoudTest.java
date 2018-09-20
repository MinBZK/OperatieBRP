/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAangeverAdreshoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpFunctieAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenWijzigingAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifte;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentRedenOntbreken;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentSoort;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpWijzeGebruikGeslachtsnaamCode;
import nl.moderniseringgba.migratie.testutils.EqualsAndHashcodeTester;

import org.junit.Test;

public class BrpGroepInhoudTest {

    @Test
    public void testEqualsHashcodeAndToString() throws Exception {

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpAanschrijvingInhoud(
                BrpWijzeGebruikGeslachtsnaamCode.E, null, null, null, null, null, null, null, null),
                new BrpAanschrijvingInhoud(BrpWijzeGebruikGeslachtsnaamCode.E, null, null, null, null, null, null,
                        null, null), new BrpAanschrijvingInhoud(BrpWijzeGebruikGeslachtsnaamCode.P, null, null, null,
                        null, null, null, null, null));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpBehandeldAlsNederlanderIndicatieInhoud(true),
                new BrpBehandeldAlsNederlanderIndicatieInhoud(true), null);

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
                new BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud(true),
                new BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud(true), null);

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
                new BrpBezitBuitenlandsReisdocumentIndicatieInhoud(true),
                new BrpBezitBuitenlandsReisdocumentIndicatieInhoud(true), null);

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
                new BrpGeboorteInhoud(new BrpDatum(20120101), new BrpGemeenteCode(new BigDecimal("1234")),
                        new BrpPlaatsCode("1234"), "buitenlandseGeboorteplaats", "buitenlandseRegioGeboorte",
                        new BrpLandCode(Integer.valueOf("1234")), "omschrijvingGeboortelocatie"),
                new BrpGeboorteInhoud(new BrpDatum(20120101), new BrpGemeenteCode(new BigDecimal("1234")),
                        new BrpPlaatsCode("1234"), "buitenlandseGeboorteplaats", "buitenlandseRegioGeboorte",
                        new BrpLandCode(Integer.valueOf("1234")), "omschrijvingGeboortelocatie"),
                new BrpGeboorteInhoud(new BrpDatum(20110101), new BrpGemeenteCode(new BigDecimal("5678")),
                        new BrpPlaatsCode("5678"), "andereBuitenlandseGeboorteplaats",
                        "andereBuitenlandseRegioGeboorte", new BrpLandCode(Integer.valueOf("5678")),
                        "andereOmschrijvingGeboortelocatie"));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpGeslachtsaanduidingInhoud(
                BrpGeslachtsaanduidingCode.MAN), new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN),
                new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.VROUW));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpGeslachtsnaamcomponentInhoud("voorvoegsel", ' ',
                "naam", new BrpPredikaatCode("B"), new BrpAdellijkeTitelCode("B"), 1),
                new BrpGeslachtsnaamcomponentInhoud("voorvoegsel", ' ', "naam", new BrpPredikaatCode("B"),
                        new BrpAdellijkeTitelCode("B"), 1), new BrpGeslachtsnaamcomponentInhoud("anderVoorvoegsel",
                        ' ', "andereNaam", null, null, 2));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpIdentificatienummersInhoud(1234567890L,
                123456789L), new BrpIdentificatienummersInhoud(1234567890L, new Long(123456789L)),
                new BrpIdentificatienummersInhoud(2345678901L, 234567890L));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpInschrijvingInhoud(1234567890L, 1234567891L,
                new BrpDatum(20120101), 1), new BrpInschrijvingInhoud(1234567890L, 1234567891L,
                new BrpDatum(20120101), 1), new BrpInschrijvingInhoud(1234567891L, 1234567892L,
                new BrpDatum(20120101), 2));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToStringBeperkt(new BrpNationaliteitInhoud(
                new BrpNationaliteitCode(Integer.valueOf("1234")), new BrpRedenVerkrijgingNederlandschapCode(
                        new BigDecimal("1234")), new BrpRedenVerliesNederlandschapCode(new BigDecimal("5678"))),
                new BrpNationaliteitInhoud(new BrpNationaliteitCode(Integer.valueOf("1234")),
                        new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal("1234")),
                        new BrpRedenVerliesNederlandschapCode(new BigDecimal("5678"))), new BrpNationaliteitInhoud(
                        new BrpNationaliteitCode(Integer.valueOf("1234")), new BrpRedenVerkrijgingNederlandschapCode(
                                new BigDecimal("4321")),
                        new BrpRedenVerliesNederlandschapCode(new BigDecimal("8765"))));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
                new BrpOverlijdenInhoud(new BrpDatum(20120101), new BrpGemeenteCode(new BigDecimal("1234")),
                        new BrpPlaatsCode("1234"), "buitenlandsePlaats", "buitenlandseRegio", new BrpLandCode(Integer
                                .valueOf("1234")), "omschrijvingLocatie"),
                new BrpOverlijdenInhoud(new BrpDatum(20120101), new BrpGemeenteCode(new BigDecimal("1234")),
                        new BrpPlaatsCode("1234"), "buitenlandsePlaats", "buitenlandseRegio", new BrpLandCode(Integer
                                .valueOf("1234")), "omschrijvingLocatie"),
                new BrpOverlijdenInhoud(new BrpDatum(20130101), new BrpGemeenteCode(new BigDecimal("4321")),
                        new BrpPlaatsCode("4321"), "andereBuitenlandsePlaats", "andereBuitenlandseRegio",
                        new BrpLandCode(Integer.valueOf("4321")), "andereOmschrijvingLocatie"));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpReisdocumentInhoud(
                new BrpReisdocumentSoort("P"), "p12345678", new BrpDatum(20110101), new BrpDatum(20050101),
                new BrpReisdocumentAutoriteitVanAfgifte("1234"), new BrpDatum(20100101), new BrpDatum(20080101),
                new BrpReisdocumentRedenOntbreken("1"), 180), new BrpReisdocumentInhoud(
                new BrpReisdocumentSoort("P"), "p12345678", new BrpDatum(20110101), new BrpDatum(20050101),
                new BrpReisdocumentAutoriteitVanAfgifte("1234"), new BrpDatum(20100101), new BrpDatum(20080101),
                new BrpReisdocumentRedenOntbreken("1"), 180), new BrpReisdocumentInhoud(
                new BrpReisdocumentSoort("R"), "R12345678", new BrpDatum(20110101), new BrpDatum(20050101),
                new BrpReisdocumentAutoriteitVanAfgifte("1234"), new BrpDatum(20100101), null, null, 180));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpVastgesteldNietNederlanderIndicatieInhoud(true),
                new BrpVastgesteldNietNederlanderIndicatieInhoud(true), null);

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpVerblijfsrechtInhoud(new BrpVerblijfsrechtCode(
                "1"), new BrpDatum(20100101), new BrpDatum(20150101), new BrpDatum(20080101)),
                new BrpVerblijfsrechtInhoud(new BrpVerblijfsrechtCode("1"), new BrpDatum(20100101), new BrpDatum(
                        20150101), new BrpDatum(20080101)), new BrpVerblijfsrechtInhoud(
                        new BrpVerblijfsrechtCode("2"), new BrpDatum(20110101), new BrpDatum(20160101), new BrpDatum(
                                20090101)));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpVoornaamInhoud("voornaam", 1),
                new BrpVoornaamInhoud("voornaam", 1), new BrpVoornaamInhoud("andereVoornaam", 2));
        //
        // final BrpStapel<BrpIdentificatienummers> identificatienummersStapel =
        // new BrpStapel<BrpIdentificatienummers>(Arrays.asList(new BrpIdentificatienummers(1234567890L,
        // 123456789L, HISTORIE, null, null, null)));
        // final BrpStapel<BrpGeslachtsaanduiding> geslachtsaanduidingStapel =
        // new BrpStapel<BrpGeslachtsaanduiding>(Arrays.asList(new BrpGeslachtsaanduiding(
        // BrpGeslachtsaanduidingCode.MAN, HISTORIE, null, null, null)));
        // final BrpStapel<BrpGeboorte> geboorteStapel =
        // new BrpStapel<BrpGeboorte>(Arrays.asList(new BrpGeboorte(new BrpDatum(20120203), new BrpGemeenteCode(
        // "1234"), null, null, null, new BrpLandCode("6030"), null, HISTORIE, null, null, null)));
        // final BrpStapel<BrpOuderlijkGezag> ouderlijkgezagStapel =
        // new BrpStapel<BrpOuderlijkGezag>(
        // Arrays.asList(new BrpOuderlijkGezag(true, HISTORIE, null, null, null)));
        // final List<BrpBetrokkenheid> groepen = Arrays.asList(new BrpBetrokkenheid(HISTORIE, null, null, null));
        // final BrpBetrokkenheidStapel betrokkenheidStapel =
        // new BrpBetrokkenheidStapel(BrpSoortBetrokkenheidCode.PARTNER, identificatienummersStapel,
        // geslachtsaanduidingStapel, geboorteStapel, ouderlijkgezagStapel, groepen);
        // EqualsAndHashcodeTester.testEqualsHashcodeAndToString(betrokkenheidStapel, new BrpBetrokkenheidStapel(
        // BrpSoortBetrokkenheidCode.PARTNER, identificatienummersStapel, geslachtsaanduidingStapel,
        // geboorteStapel, ouderlijkgezagStapel, groepen), new BrpBetrokkenheidStapel(
        // BrpSoortBetrokkenheidCode.KIND, identificatienummersStapel, geslachtsaanduidingStapel,
        // geboorteStapel, ouderlijkgezagStapel, groepen));
        //
        // EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpIkBetrokkenheidStapel(
        // BrpSoortBetrokkenheidCode.PARTNER, groepen), new BrpIkBetrokkenheidStapel(
        // BrpSoortBetrokkenheidCode.PARTNER, groepen), new BrpIkBetrokkenheidStapel(
        // BrpSoortBetrokkenheidCode.KIND, groepen));
        //
        // final BrpIkBetrokkenheidStapel ikBetrokkenheidStapel =
        // new BrpIkBetrokkenheidStapel(BrpSoortBetrokkenheidCode.KIND, groepen);
        // final List<BrpBetrokkenheidStapel> betrokkenheidStapels = Arrays.asList(betrokkenheidStapel);
        // final List<BrpRelatie> ikGroepen =
        // Arrays.asList(new BrpRelatie(new BrpDatum(20120203), new BrpGemeenteCode("1234"), null, null, null,
        // new BrpLandCode("6030"), null, null, null, null, null, null, null, null, null, false,
        // HISTORIE, null, null, null));
        // EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpRelatieStapel(BrpSoortRelatieCode.HUWELIJK,
        // ikBetrokkenheidStapel, betrokkenheidStapels, ikGroepen), new BrpRelatieStapel(
        // BrpSoortRelatieCode.HUWELIJK, ikBetrokkenheidStapel, betrokkenheidStapels, ikGroepen),
        // new BrpRelatieStapel(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, ikBetrokkenheidStapel,
        // betrokkenheidStapels, ikGroepen));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpHistorie(new BrpDatum(20120203), new BrpDatum(
                20130203), BrpDatumTijd.fromDatum(20120203), BrpDatumTijd.fromDatum(20120203)),
                new BrpHistorie(new BrpDatum(20120203), new BrpDatum(20130203), BrpDatumTijd.fromDatum(20120203),
                        BrpDatumTijd.fromDatum(20120203)), new BrpHistorie(new BrpDatum(20020203), new BrpDatum(
                        20030203), BrpDatumTijd.fromDatum(20020203), BrpDatumTijd.fromDatum(20020203)));
    }

    @Test
    public void testAttributen() throws Exception {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpAanduidingBijHuisnummerCode("TO"),
                new BrpAanduidingBijHuisnummerCode("TO"), new BrpAanduidingBijHuisnummerCode("BY"));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpAangeverAdreshoudingCode("A"),
                new BrpAangeverAdreshoudingCode("A"), new BrpAangeverAdreshoudingCode("S"));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpAdellijkeTitelCode("B"),
                new BrpAdellijkeTitelCode("B"), new BrpAdellijkeTitelCode("P"));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpDatum(20120101), new BrpDatum(20120101),
                new BrpDatum(20120102));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(BrpDatumTijd.fromDatum(20120101),
                BrpDatumTijd.fromDatum(20120101), BrpDatumTijd.fromDatum(20120102));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpGemeenteCode(new BigDecimal("1234")),
                new BrpGemeenteCode(new BigDecimal("1234")), new BrpGemeenteCode(new BigDecimal("4321")));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpLandCode(Integer.valueOf("6030")),
                new BrpLandCode(Integer.valueOf("6030")), new BrpLandCode(Integer.valueOf("6031")));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpNationaliteitCode(Integer.valueOf("6030")),
                new BrpNationaliteitCode(Integer.valueOf("6030")), new BrpNationaliteitCode(Integer.valueOf("6031")));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpPlaatsCode("1234"), new BrpPlaatsCode("1234"),
                new BrpPlaatsCode("4321"));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpPredikaatCode("J"), new BrpPredikaatCode("J"),
                new BrpPredikaatCode("JV"));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpRedenEindeRelatieCode("1"),
                new BrpRedenEindeRelatieCode("1"), new BrpRedenEindeRelatieCode("2"));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpRedenVerkrijgingNederlandschapCode(
                new BigDecimal("1")), new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal("1")),
                new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal("2")));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpRedenVerliesNederlandschapCode(new BigDecimal(
                "1")), new BrpRedenVerliesNederlandschapCode(new BigDecimal("1")),
                new BrpRedenVerliesNederlandschapCode(new BigDecimal("2")));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpRedenWijzigingAdresCode("1"),
                new BrpRedenWijzigingAdresCode("1"), new BrpRedenWijzigingAdresCode("2"));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpReisdocumentAutoriteitVanAfgifte("1"),
                new BrpReisdocumentAutoriteitVanAfgifte("1"), new BrpReisdocumentAutoriteitVanAfgifte("2"));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpReisdocumentRedenOntbreken("1"),
                new BrpReisdocumentRedenOntbreken("1"), new BrpReisdocumentRedenOntbreken("2"));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpReisdocumentSoort("P"),
                new BrpReisdocumentSoort("P"), new BrpReisdocumentSoort("I"));

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpVerblijfsrechtCode("1"),
                new BrpVerblijfsrechtCode("1"), new BrpVerblijfsrechtCode("2"));

        assertEquals(BrpGeslachtsaanduidingCode.MAN, BrpGeslachtsaanduidingCode.valueOfBrpCode("M"));
        IllegalArgumentException exception = null;
        try {
            BrpGeslachtsaanduidingCode.valueOfBrpCode("X");
        } catch (final IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);

        assertEquals("P", BrpSoortBetrokkenheidCode.PARTNER.getCode());
        assertEquals("O", BrpSoortBetrokkenheidCode.OUDER.getCode());
        assertEquals("K", BrpSoortBetrokkenheidCode.KIND.getCode());

        assertEquals("H", BrpSoortRelatieCode.HUWELIJK.getCode());
        assertEquals("G", BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP.getCode());
        assertEquals("F", BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING.getCode());
    }

    @Test
    public void testIsLeegMethoden() {
        testIsNietLeeg(new BrpAanschrijvingInhoud(BrpWijzeGebruikGeslachtsnaamCode.E, null, null, null, null, null,
                null, null, null));

        testIsNietLeeg(new BrpBehandeldAlsNederlanderIndicatieInhoud(true));

        testIsNietLeeg(new BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud(true));

        testIsNietLeeg(new BrpBezitBuitenlandsReisdocumentIndicatieInhoud(true));

        testIsNietLeeg(new BrpGeboorteInhoud(new BrpDatum(20120101), new BrpGemeenteCode(new BigDecimal("1234")),
                new BrpPlaatsCode("1234"), "buitenlandseGeboorteplaats", "buitenlandseRegioGeboorte",
                new BrpLandCode(Integer.valueOf("1234")), "omschrijvingGeboortelocatie"));

        testIsNietLeeg(new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN));

        testIsNietLeeg(new BrpGeslachtsnaamcomponentInhoud("voorvoegsel", ' ', "naam", new BrpPredikaatCode("B"),
                new BrpAdellijkeTitelCode("B"), 1));

        testIsNietLeeg(new BrpIdentificatienummersInhoud(1234567890L, 123456789L));

        testIsNietLeeg(new BrpInschrijvingInhoud(1234567890L, 1234567891L, new BrpDatum(20120101), 1));

        testIsNietLeeg(new BrpNationaliteitInhoud(new BrpNationaliteitCode(Integer.valueOf("1234")),
                new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal("1234")),
                new BrpRedenVerliesNederlandschapCode(new BigDecimal("5678"))));

        testIsNietLeeg(new BrpOverlijdenInhoud(new BrpDatum(20120101), new BrpGemeenteCode(new BigDecimal("1234")),
                new BrpPlaatsCode("1234"), "buitenlandsePlaats", "buitenlandseRegio", new BrpLandCode(
                        Integer.valueOf("1234")), "omschrijvingLocatie"));

        testIsNietLeeg(new BrpReisdocumentInhoud(new BrpReisdocumentSoort("P"), "p12345678", new BrpDatum(20110101),
                new BrpDatum(20050101), new BrpReisdocumentAutoriteitVanAfgifte("1234"), new BrpDatum(20100101),
                new BrpDatum(20080101), new BrpReisdocumentRedenOntbreken("1"), 180));

        testIsNietLeeg(new BrpVastgesteldNietNederlanderIndicatieInhoud(true));

        testIsNietLeeg(new BrpVerblijfsrechtInhoud(new BrpVerblijfsrechtCode("1"), new BrpDatum(20100101),
                new BrpDatum(20150101), new BrpDatum(20080101)));

        testIsNietLeeg(new BrpVoornaamInhoud("voornaam", 1));
    }

    private void testIsNietLeeg(final BrpGroepInhoud inhoud) {
        assertFalse(inhoud.isLeeg());
    }

    private void testIsLeeg(final BrpGroepInhoud inhoud) {
        assertTrue(inhoud.isLeeg());
    }

    @Test
    public void testBrpAdres() throws Exception {

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
                new BrpAdresInhoud(BrpFunctieAdresCode.W, new BrpRedenWijzigingAdresCode("V"),
                        new BrpAangeverAdreshoudingCode("A"), new BrpDatum(20100101), "1312312", "123123213",
                        new BrpGemeenteCode(new BigDecimal("0518")), "naam", "afk", "gemeentedeel", 123, 'A', "-123",
                        "1234AB", new BrpPlaatsCode("0518"), new BrpAanduidingBijHuisnummerCode("TO"), "woonboot",
                        "regel 1", "regel 2", "regel 3", "regel 4", "regel 5", "regel 6", new BrpLandCode(Integer
                                .valueOf("6030")), new BrpDatum(20030406)), new BrpAdresInhoud(BrpFunctieAdresCode.W,
                        new BrpRedenWijzigingAdresCode("V"), new BrpAangeverAdreshoudingCode("A"), new BrpDatum(
                                20100101), "1312312", "123123213", new BrpGemeenteCode(new BigDecimal("0518")),
                        "naam", "afk", "gemeentedeel", 123, 'A', "-123", "1234AB", new BrpPlaatsCode("0518"),
                        new BrpAanduidingBijHuisnummerCode("TO"), "woonboot", "regel 1", "regel 2", "regel 3",
                        "regel 4", "regel 5", "regel 6", new BrpLandCode(Integer.valueOf("6030")), new BrpDatum(
                                20030406)), new BrpAdresInhoud(BrpFunctieAdresCode.B, new BrpRedenWijzigingAdresCode(
                        "E"), new BrpAangeverAdreshoudingCode("S"), new BrpDatum(20030101), "3453", "2345334",
                        new BrpGemeenteCode(new BigDecimal("1234")), "asdsd", "vcc", "sfsd", 56765, 'Z', "-E",
                        "2444YY", new BrpPlaatsCode("3454"), new BrpAanduidingBijHuisnummerCode("BY"),
                        "standplaats 23", "anders 1", "anders 2", "anders 3", "anders 4", "anders 5", "anders 6",
                        new BrpLandCode(Integer.valueOf("3002")), new BrpDatum(20050102)));

        testIsNietLeeg(new BrpAdresInhoud(null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, new BrpLandCode(
                        Integer.valueOf("6030")), null));
        testIsLeeg(new BrpAdresInhoud(null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null));
    }

    @Test
    public void testBrpImmigratie() throws Exception {

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
                new BrpImmigratieInhoud(new BrpLandCode(Integer.valueOf("1233")), new BrpDatum(20000102)),
                new BrpImmigratieInhoud(new BrpLandCode(Integer.valueOf("1233")), new BrpDatum(20000102)),
                new BrpImmigratieInhoud(new BrpLandCode(Integer.valueOf("5433")), new BrpDatum(19990404)));
        testIsNietLeeg(new BrpImmigratieInhoud(new BrpLandCode(Integer.valueOf("1233")), new BrpDatum(20000102)));
        testIsLeeg(new BrpImmigratieInhoud(null, null));
    }

    @Test
    public void testBrpBijhoudingsGemeente() throws Exception {

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpBijhoudingsgemeenteInhoud(new BrpGemeenteCode(
                new BigDecimal("5018")), new BrpDatum(20030405), Boolean.FALSE), new BrpBijhoudingsgemeenteInhoud(
                new BrpGemeenteCode(new BigDecimal("5018")), new BrpDatum(20030405), Boolean.FALSE),
                new BrpBijhoudingsgemeenteInhoud(new BrpGemeenteCode(new BigDecimal("7654")), new BrpDatum(19880607),
                        Boolean.TRUE));

        testIsNietLeeg(new BrpBijhoudingsgemeenteInhoud(new BrpGemeenteCode(new BigDecimal("5018")), new BrpDatum(
                20030405), Boolean.FALSE));
        testIsLeeg(new BrpBijhoudingsgemeenteInhoud(null, null, null));
    }

    @Test
    public void testBrpUitsluitingNederlandsKiesrecht() throws Exception {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpUitsluitingNederlandsKiesrechtInhoud(
                Boolean.TRUE, new BrpDatum(20030205)), new BrpUitsluitingNederlandsKiesrechtInhoud(Boolean.TRUE,
                new BrpDatum(20030205)), new BrpUitsluitingNederlandsKiesrechtInhoud(Boolean.FALSE, null));

        testIsNietLeeg(new BrpUitsluitingNederlandsKiesrechtInhoud(Boolean.TRUE, null));
        testIsLeeg(new BrpUitsluitingNederlandsKiesrechtInhoud(null, null));
    }

    @Test
    public void testBrpEuropeseVerkiezingen() throws Exception {

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpEuropeseVerkiezingenInhoud(Boolean.TRUE,
                new BrpDatum(20030205), new BrpDatum(20030206)), new BrpEuropeseVerkiezingenInhoud(Boolean.TRUE,
                new BrpDatum(20030205), new BrpDatum(20030206)), new BrpEuropeseVerkiezingenInhoud(Boolean.FALSE,
                null, null));

        testIsNietLeeg(new BrpEuropeseVerkiezingenInhoud(Boolean.TRUE, new BrpDatum(20030205), new BrpDatum(20030206)));
        testIsLeeg(new BrpEuropeseVerkiezingenInhoud(null, null, null));
    }

    @Test
    public void testBrpOpschorting() throws Exception {

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpOpschortingInhoud(new BrpDatum(20030203),
                BrpRedenOpschortingBijhoudingCode.MINISTERIEEL_BESLUIT), new BrpOpschortingInhoud(new BrpDatum(
                20030203), BrpRedenOpschortingBijhoudingCode.MINISTERIEEL_BESLUIT), new BrpOpschortingInhoud(
                new BrpDatum(20120405), BrpRedenOpschortingBijhoudingCode.ONBEKEND));
        testIsNietLeeg(new BrpOpschortingInhoud(new BrpDatum(20030203),
                BrpRedenOpschortingBijhoudingCode.MINISTERIEEL_BESLUIT));
        testIsLeeg(new BrpOpschortingInhoud(null, null));
    }

    @Test
    public void testBrpPersoonskaart() throws Exception {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpPersoonskaartInhoud(new BrpGemeenteCode(
                new BigDecimal("0518")), Boolean.TRUE), new BrpPersoonskaartInhoud(new BrpGemeenteCode(
                new BigDecimal("0518")), Boolean.TRUE), new BrpPersoonskaartInhoud(new BrpGemeenteCode(
                new BigDecimal("1234")), Boolean.FALSE));

        testIsNietLeeg(new BrpPersoonskaartInhoud(new BrpGemeenteCode(new BigDecimal("0518")), Boolean.TRUE));
        testIsLeeg(new BrpPersoonskaartInhoud(null, null));
    }

    @Test
    public void testBrpVerstrekkingsbeperking() throws Exception {

        EqualsAndHashcodeTester
                .testEqualsHashcodeAndToString(new BrpVerstrekkingsbeperkingInhoud(Boolean.TRUE),
                        new BrpVerstrekkingsbeperkingInhoud(Boolean.TRUE), new BrpVerstrekkingsbeperkingInhoud(
                                Boolean.FALSE));
        testIsNietLeeg(new BrpVerstrekkingsbeperkingInhoud(Boolean.TRUE));
        testIsLeeg(new BrpVerstrekkingsbeperkingInhoud(null));
    }
}
