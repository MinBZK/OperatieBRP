/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BrpStapelSorterTest {

    private final FoutMelder foutMelder = new FoutMelder();

    @Test
    public void testSorteerPL() {
        final BrpPersoonslijst pl = GgoBrpStapelSorter.sorteerPersoonslijst(createValidBrpPersoonslijst(new BrpGemeenteCode((short) 1904)), foutMelder);
        assertNotNull(pl);
    }

    @Test
    public void testSorteerLegePL() {
        final BrpPersoonslijst pl = GgoBrpStapelSorter.sorteerPersoonslijst(null, foutMelder);
        assertNull(pl);
    }

    @Test
    public void testSorteerPLLegeStapel() {
        final BrpPersoonslijst pl = GgoBrpStapelSorter.sorteerPersoonslijst(createBrpPersoonslijstMissing(new BrpGemeenteCode((short) 1904)), foutMelder);
        assertNotNull(pl);
    }

    @Test
    public void testSorteerPLVolgorde() {
        final BrpPersoonslijst origPl = createBrpPersoonslijstWrongOrder(new BrpGemeenteCode((short) 1904));

        // check order origPl Voornaameen is de eerst toegevoegde en komt vooraan te staan.
        for (final BrpStapel<BrpVoornaamInhoud> stapel : origPl.getVoornaamStapels()) {
            assertEquals("Voornaameen", BrpString.unwrap(stapel.get(0).getInhoud().getVoornaam()));
        }
        final BrpPersoonslijst pl = GgoBrpStapelSorter.sorteerPersoonslijst(origPl, foutMelder);
        // check order pl Voornaamtwee zou nu vooraan moeten staan.
        for (final BrpStapel<BrpVoornaamInhoud> stapel : pl.getVoornaamStapels()) {
            assertEquals("Voornaamtwee", BrpString.unwrap(stapel.get(0).getInhoud().getVoornaam()));
        }

        assertNotNull(pl);
    }

    private BrpPersoonslijst createBrpPersoonslijstWrongOrder(final BrpGemeenteCode gemeenteCode) {
        final BrpPartijCode brpGemeenteCode;
        if (gemeenteCode == null) {
            brpGemeenteCode = new BrpPartijCode(59901);
        } else {
            brpGemeenteCode = new BrpPartijCode(gemeenteCode.getWaarde() * 100 + 1);
        }
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final List<BrpGroep<BrpBijhoudingInhoud>> groepen = new ArrayList<>();

        groepen.add(
            new BrpGroep<>(
                new BrpBijhoudingInhoud(brpGemeenteCode, null, null, new BrpBoolean(false, null)),
                new BrpHistorie(
                    new BrpDatum(20000101, null),
                    new BrpDatum(20110101, null),
                    new BrpDatumTijd(new Date()),
                    new BrpDatumTijd(new Date()),
                    null),
                null,
                null,
                null));
        builder.bijhoudingStapel(new BrpStapel<>(groepen));

        // Relatie 1
        createRelatie(builder);

        // Relatie 2
        createRelatie(builder);

        // Geslachtsnaam 1
        createGeslachtsnaam(builder);

        // Geslachtsnaam 2
        createGeslachtsnaam(builder);

        // Voornaam 1
        createVoornaamMultiple(builder);

        // Reisdoc 1
        createReisdoc(builder);

        // Reisdoc 2
        createReisdoc(builder);

        // Nationaliteit 1
        createNationaliteit(builder);

        // Nationaliteit 2
        createNationaliteit(builder);

        return builder.build();
    }

    private BrpPersoonslijst createBrpPersoonslijstMissing(final BrpGemeenteCode gemeenteCode) {
        final BrpPartijCode brpGemeenteCode;
        if (gemeenteCode == null) {
            brpGemeenteCode = new BrpPartijCode(59901);
        } else {
            brpGemeenteCode = new BrpPartijCode(gemeenteCode.getWaarde() * 100 + 1);
        }
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final List<BrpGroep<BrpBijhoudingInhoud>> groepen = new ArrayList<>();

        groepen.add(
            new BrpGroep<>(
                new BrpBijhoudingInhoud(brpGemeenteCode, null, null, new BrpBoolean(false, null)),
                new BrpHistorie(
                    new BrpDatum(20000101, null),
                    new BrpDatum(20110101, null),
                    new BrpDatumTijd(new Date()),
                    new BrpDatumTijd(new Date()),
                    null),
                null,
                null,
                null));
        builder.bijhoudingStapel(new BrpStapel<>(groepen));

        return builder.build();
    }

    private BrpPersoonslijst createValidBrpPersoonslijst(final BrpGemeenteCode gemeenteCode) {
        final BrpPartijCode brpGemeenteCode;
        if (gemeenteCode == null) {
            brpGemeenteCode = new BrpPartijCode(59901);
        } else {
            brpGemeenteCode = new BrpPartijCode(gemeenteCode.getWaarde() * 100 + 1);
        }
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final List<BrpGroep<BrpBijhoudingInhoud>> groepen = new ArrayList<>();

        groepen.add(
            new BrpGroep<>(
                new BrpBijhoudingInhoud(brpGemeenteCode, null, null, new BrpBoolean(false, null)),
                new BrpHistorie(
                    new BrpDatum(20000101, null),
                    new BrpDatum(20110101, null),
                    new BrpDatumTijd(new Date()),
                    new BrpDatumTijd(new Date()),
                    null),
                null,
                null,
                null));
        builder.bijhoudingStapel(new BrpStapel<>(groepen));

        // Relatie 1
        createRelatie(builder);

        // Relatie 2
        createRelatie(builder);

        // Geslachtsnaam 1
        createGeslachtsnaam(builder);

        // Geslachtsnaam 2
        createGeslachtsnaam(builder);

        // Voornaam 1
        createVoornaam(builder);

        // Voornaam 2
        createVoornaam(builder);

        // Reisdoc 1
        createReisdoc(builder);

        // Reisdoc 2
        createReisdoc(builder);

        // Nationaliteit 1
        createNationaliteit(builder);

        // Nationaliteit 2
        createNationaliteit(builder);

        return builder.build();
    }

    private void createRelatie(final BrpPersoonslijstBuilder builder) {
        final List<BrpBetrokkenheid> betrokkenheid = new ArrayList<>();
        final BrpIdentificatienummersInhoud inhoud = new BrpIdentificatienummersInhoud(new BrpLong(43L, null), new BrpInteger(43, null));
        final List<BrpGroep<BrpIdentificatienummersInhoud>> idBrpGroep = new ArrayList<>();
        final BrpHistorie historie =
                new BrpHistorie(
                    new BrpDatum(20000101, null),
                    new BrpDatum(20110101, null),
                    new BrpDatumTijd(new Date()),
                    new BrpDatumTijd(new Date()),
                    null);
        final BrpGroep<BrpIdentificatienummersInhoud> brpIdGroep = new BrpGroep<>(inhoud, historie, null, null, null);
        idBrpGroep.add(brpIdGroep);
        final List<BrpGroep<BrpSamengesteldeNaamInhoud>> samengesteldeNaamInhoudGroepen = new ArrayList<>();
        final BrpHistorie historie1 =
                new BrpHistorie(
                    new BrpDatum(20000101, null),
                    new BrpDatum(20110101, null),
                    new BrpDatumTijd(new Date()),
                    new BrpDatumTijd(new Date()),
                    null);
        samengesteldeNaamInhoudGroepen.add(
            new BrpGroep<>(
                new BrpSamengesteldeNaamInhoud(
                    new BrpPredicaatCode("P"),
                    new BrpString("voornamen", null),
                    new BrpString("voorv", null),
                    new BrpCharacter(' ', null),
                    new BrpAdellijkeTitelCode("B"),
                    new BrpString("naam", null),
                    new BrpBoolean(false, null),
                    new BrpBoolean(false, null)),
                historie1,
                null,
                null,
                null));
        betrokkenheid.add(
            new BrpBetrokkenheid(null, new BrpStapel<>(idBrpGroep), null, null, null, new BrpStapel<>(samengesteldeNaamInhoudGroepen), null, null));
        final BrpRelatie.Builder relatieBuilder =
                new BrpRelatie.Builder(
                    BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING,
                    BrpSoortBetrokkenheidCode.OUDER,
                    new LinkedHashMap<Long, BrpActie>());
        relatieBuilder.betrokkenheden(betrokkenheid);
        builder.relatie(relatieBuilder.build());
    }

    private void createGeslachtsnaam(final BrpPersoonslijstBuilder builder) {
        final BrpHistorie historie =
                new BrpHistorie(
                    new BrpDatum(20000101, null),
                    new BrpDatum(20110101, null),
                    new BrpDatumTijd(new Date()),
                    new BrpDatumTijd(new Date()),
                    null);
        final BrpGeslachtsnaamcomponentInhoud brpGeslachtsnaamcomponentInhoud =
                new BrpGeslachtsnaamcomponentInhoud(
                    new BrpString("voor", null),
                    new BrpCharacter(' ', null),
                    new BrpString("naam", null),
                    new BrpPredicaatCode("P"),
                    new BrpAdellijkeTitelCode("B"),
                    new BrpInteger(0, null));
        final BrpGroep<BrpGeslachtsnaamcomponentInhoud> brpGeslachtGroep = new BrpGroep<>(brpGeslachtsnaamcomponentInhoud, historie, null, null, null);
        final List<BrpGroep<BrpGeslachtsnaamcomponentInhoud>> brpGeslachtsnaamGroep = new ArrayList<>();
        brpGeslachtsnaamGroep.add(brpGeslachtGroep);
        final BrpStapel<BrpGeslachtsnaamcomponentInhoud> geslachtsnaamcomponentStapel = new BrpStapel<>(brpGeslachtsnaamGroep);
        builder.geslachtsnaamcomponentStapel(geslachtsnaamcomponentStapel);
    }

    private void createVoornaam(final BrpPersoonslijstBuilder builder) {
        final List<BrpGroep<BrpVoornaamInhoud>> brpVoornaamGroep1 = new ArrayList<>();
        brpVoornaamGroep1.add(
            new BrpGroep<>(
                new BrpVoornaamInhoud(new BrpString("Vorrnaam", null), new BrpInteger(0, null)),
                new BrpHistorie(
                    new BrpDatum(20000101, null),
                    new BrpDatum(20110101, null),
                    new BrpDatumTijd(new Date(), null),
                    new BrpDatumTijd(new Date(), null),
                    null),
                null,
                null,
                null));
        final BrpStapel<BrpVoornaamInhoud> voornaamStapel1 = new BrpStapel<>(brpVoornaamGroep1);

        builder.voornaamStapel(voornaamStapel1);
    }

    private void createVoornaamMultiple(final BrpPersoonslijstBuilder builder) {
        final List<BrpGroep<BrpVoornaamInhoud>> brpVoornaamGroep1 = new ArrayList<>();

        brpVoornaamGroep1.add(
            new BrpGroep<>(
                new BrpVoornaamInhoud(new BrpString("Voornaameen", null), new BrpInteger(0, null)),
                new BrpHistorie(new BrpDatum(20130101, null), new BrpDatum(20130101, null), new BrpDatumTijd(new Date(), null), null, null),
                null,
                null,
                null));
        brpVoornaamGroep1.add(
            new BrpGroep<>(
                new BrpVoornaamInhoud(new BrpString("Voornaamtwee", null), new BrpInteger(0, null)),
                new BrpHistorie(
                    new BrpDatum(20140101, null),
                    new BrpDatum(20140101, null),
                    new BrpDatumTijd(new Date(), null),
                    new BrpDatumTijd(new Date(), null),
                    null),
                null,
                null,
                null));
        final BrpStapel<BrpVoornaamInhoud> voornaamStapel1 = new BrpStapel<>(brpVoornaamGroep1);

        builder.voornaamStapel(voornaamStapel1);
    }

    private void createReisdoc(final BrpPersoonslijstBuilder builder) {
        final BrpReisdocumentInhoud brpReisdocumentInhoud =
                new BrpReisdocumentInhoud(
                    new BrpSoortNederlandsReisdocumentCode("P"),
                    new BrpString("1"),
                    new BrpDatum(20110101, null),
                    new BrpDatum(20110101, null),
                    new BrpReisdocumentAutoriteitVanAfgifteCode("D"),
                    new BrpDatum(20110101, null),
                    new BrpDatum(20110101, null),
                    new BrpAanduidingInhoudingOfVermissingReisdocumentCode('I'));
        final List<BrpGroep<BrpReisdocumentInhoud>> reisdocumentInhoudGroep = new ArrayList<>();
        reisdocumentInhoudGroep.add(
            new BrpGroep<>(
                brpReisdocumentInhoud,
                new BrpHistorie(
                    new BrpDatum(20000101, null),
                    new BrpDatum(20110101, null),
                    new BrpDatumTijd(new Date()),
                    new BrpDatumTijd(new Date()),
                    null),
                null,
                null,
                null));

        builder.reisdocumentStapel(new BrpStapel<>(reisdocumentInhoudGroep));
    }

    private void createNationaliteit(final BrpPersoonslijstBuilder builder) {
        final List<BrpGroep<BrpNationaliteitInhoud>> nationaliteitInhoudGroep = new ArrayList<>();
        final BrpNationaliteitInhoud brpNationaliteitInhoud =
                new BrpNationaliteitInhoud(
                    new BrpNationaliteitCode(Short.parseShort("1")),
                    new BrpRedenVerkrijgingNederlandschapCode(Short.parseShort("1")),
                    new BrpRedenVerliesNederlandschapCode(Short.parseShort("1")),
                    null,
                    null,
                    null,
                    null);
        nationaliteitInhoudGroep.add(
            new BrpGroep<>(
                brpNationaliteitInhoud,
                new BrpHistorie(
                    new BrpDatum(20000101, null),
                    new BrpDatum(20110101, null),
                    new BrpDatumTijd(new Date()),
                    new BrpDatumTijd(new Date()),
                    null),
                null,
                null,
                null));
        builder.nationaliteitStapel(new BrpStapel<>(nationaliteitInhoudGroep));
    }

}
