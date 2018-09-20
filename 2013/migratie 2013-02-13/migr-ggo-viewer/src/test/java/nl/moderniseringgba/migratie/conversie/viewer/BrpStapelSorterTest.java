/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifte;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentRedenOntbreken;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentSoort;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsgemeenteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.moderniseringgba.migratie.conversie.viewer.log.FoutMelder;

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
        try {
            final BrpPersoonslijst pl =
                    BrpStapelSorter.sorteerPersoonslijst(createValidBrpPersoonslijst(new BrpGemeenteCode(
                            new BigDecimal(1904))), foutMelder);
            assertNotNull(pl);
        } catch (final Exception e) {
            fail("Er mag geen fout optreden bij het sorteren!");
        }
    }

    @Test
    public void testSorteerLegePL() {
        try {
            final BrpPersoonslijst pl = BrpStapelSorter.sorteerPersoonslijst(null, foutMelder);
            assertNull(pl);
        } catch (final Exception e) {
            fail("Er mag geen fout optreden bij het sorteren!");
        }
    }

    @Test
    public void testSorteerPLLegeStapel() {
        try {
            final BrpPersoonslijst pl =
                    BrpStapelSorter.sorteerPersoonslijst(createBrpPersoonslijstMissing(new BrpGemeenteCode(
                            new BigDecimal(1904))), foutMelder);
            assertNotNull(pl);
        } catch (final Exception e) {
            e.printStackTrace();
            fail("Er mag geen fout optreden bij het sorteren!");
        }
    }

    @Test
    public void testSorteerPLVolgorde() {
        try {
            final BrpPersoonslijst origPl =
                    createBrpPersoonslijstWrongOrder(new BrpGemeenteCode(new BigDecimal(1904)));

            // check order origPl Voornaameen is de eerst toegevoegde en komt vooraan te staan.
            for (final BrpStapel<BrpVoornaamInhoud> stapel : origPl.getVoornaamStapels()) {
                assertEquals("Voornaameen", stapel.get(0).getInhoud().getVoornaam());
            }
            final BrpPersoonslijst pl = BrpStapelSorter.sorteerPersoonslijst(origPl, foutMelder);
            // check order pl Voornaamtwee zou nu vooraan moeten staan.
            for (final BrpStapel<BrpVoornaamInhoud> stapel : pl.getVoornaamStapels()) {
                assertEquals("Voornaamtwee", stapel.get(0).getInhoud().getVoornaam());
            }

            assertNotNull(pl);
        } catch (final Exception e) {
            e.printStackTrace();
            fail("Er mag geen fout optreden bij het sorteren!");
        }
    }

    private BrpPersoonslijst createBrpPersoonslijstWrongOrder(final BrpGemeenteCode gemeenteCode) {
        final BrpGemeenteCode brpGemeenteCode = gemeenteCode;
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final List<BrpGroep<BrpBijhoudingsgemeenteInhoud>> groepen =
                new ArrayList<BrpGroep<BrpBijhoudingsgemeenteInhoud>>();

        groepen.add(new BrpGroep<BrpBijhoudingsgemeenteInhoud>(new BrpBijhoudingsgemeenteInhoud(brpGemeenteCode,
                BrpDatum.ONBEKEND, false), new BrpHistorie(new BrpDatum(20000101), new BrpDatum(20110101),
                new BrpDatumTijd(new Date()), new BrpDatumTijd(new Date())), null, null, null));
        builder.bijhoudingsgemeenteStapel(new BrpStapel<BrpBijhoudingsgemeenteInhoud>(groepen));

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
        final BrpGemeenteCode brpGemeenteCode = gemeenteCode;
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final List<BrpGroep<BrpBijhoudingsgemeenteInhoud>> groepen =
                new ArrayList<BrpGroep<BrpBijhoudingsgemeenteInhoud>>();

        groepen.add(new BrpGroep<BrpBijhoudingsgemeenteInhoud>(new BrpBijhoudingsgemeenteInhoud(brpGemeenteCode,
                BrpDatum.ONBEKEND, false), new BrpHistorie(new BrpDatum(20000101), new BrpDatum(20110101),
                new BrpDatumTijd(new Date()), new BrpDatumTijd(new Date())), null, null, null));
        builder.bijhoudingsgemeenteStapel(new BrpStapel<BrpBijhoudingsgemeenteInhoud>(groepen));

        return builder.build();
    }

    private BrpPersoonslijst createValidBrpPersoonslijst(final BrpGemeenteCode gemeenteCode) {
        final BrpGemeenteCode brpGemeenteCode = gemeenteCode;
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final List<BrpGroep<BrpBijhoudingsgemeenteInhoud>> groepen =
                new ArrayList<BrpGroep<BrpBijhoudingsgemeenteInhoud>>();

        groepen.add(new BrpGroep<BrpBijhoudingsgemeenteInhoud>(new BrpBijhoudingsgemeenteInhoud(brpGemeenteCode,
                BrpDatum.ONBEKEND, false), new BrpHistorie(new BrpDatum(20000101), new BrpDatum(20110101),
                new BrpDatumTijd(new Date()), new BrpDatumTijd(new Date())), null, null, null));
        builder.bijhoudingsgemeenteStapel(new BrpStapel<BrpBijhoudingsgemeenteInhoud>(groepen));

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
        final List<BrpBetrokkenheid> betrokkenheid = new ArrayList<BrpBetrokkenheid>();
        final BrpIdentificatienummersInhoud inhoud =
                new BrpIdentificatienummersInhoud(Long.valueOf(43), Long.valueOf(43));
        final List<BrpGroep<BrpIdentificatienummersInhoud>> idBrpGroep =
                new ArrayList<BrpGroep<BrpIdentificatienummersInhoud>>();
        final BrpHistorie historie =
                new BrpHistorie(new BrpDatum(20000101), new BrpDatum(20110101), new BrpDatumTijd(new Date()),
                        new BrpDatumTijd(new Date()));
        final BrpGroep<BrpIdentificatienummersInhoud> brpIdGroep =
                new BrpGroep<BrpIdentificatienummersInhoud>(inhoud, historie, null, null, null);
        idBrpGroep.add(brpIdGroep);
        final List<BrpGroep<BrpSamengesteldeNaamInhoud>> samengesteldeNaamInhoudGroepen =
                new ArrayList<BrpGroep<BrpSamengesteldeNaamInhoud>>();
        final BrpHistorie historie1 =
                new BrpHistorie(new BrpDatum(20000101), new BrpDatum(20110101), new BrpDatumTijd(new Date()),
                        new BrpDatumTijd(new Date()));
        samengesteldeNaamInhoudGroepen.add(new BrpGroep<BrpSamengesteldeNaamInhoud>(new BrpSamengesteldeNaamInhoud(
                new BrpPredikaatCode("P"), "voornamen", "voorv", Character.valueOf(' '), new BrpAdellijkeTitelCode(
                        "B"), "naam", false, false), historie1, null, null, null));
        betrokkenheid.add(new BrpBetrokkenheid(null, new BrpStapel<BrpIdentificatienummersInhoud>(idBrpGroep), null,
                null, null, new BrpStapel<BrpSamengesteldeNaamInhoud>(samengesteldeNaamInhoudGroepen), null));
        final BrpRelatie relatie =
                new BrpRelatie(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, BrpSoortBetrokkenheidCode.OUDER,
                        betrokkenheid, null);
        builder.relatie(relatie);
    }

    private void createGeslachtsnaam(final BrpPersoonslijstBuilder builder) {
        final BrpHistorie historie =
                new BrpHistorie(new BrpDatum(20000101), new BrpDatum(20110101), new BrpDatumTijd(new Date()),
                        new BrpDatumTijd(new Date()));
        final BrpGeslachtsnaamcomponentInhoud brpGeslachtsnaamcomponentInhoud =
                new BrpGeslachtsnaamcomponentInhoud("voor", Character.valueOf(' '), "naam",
                        new BrpPredikaatCode("P"), new BrpAdellijkeTitelCode("B"), 0);
        final BrpGroep<BrpGeslachtsnaamcomponentInhoud> brpGeslachtGroep =
                new BrpGroep<BrpGeslachtsnaamcomponentInhoud>(brpGeslachtsnaamcomponentInhoud, historie, null, null,
                        null);
        final List<BrpGroep<BrpGeslachtsnaamcomponentInhoud>> brpGeslachtsnaamGroep =
                new ArrayList<BrpGroep<BrpGeslachtsnaamcomponentInhoud>>();
        brpGeslachtsnaamGroep.add(brpGeslachtGroep);
        final BrpStapel<BrpGeslachtsnaamcomponentInhoud> geslachtsnaamcomponentStapel =
                new BrpStapel<BrpGeslachtsnaamcomponentInhoud>(brpGeslachtsnaamGroep);
        builder.geslachtsnaamcomponentStapel(geslachtsnaamcomponentStapel);
    }

    private void createVoornaam(final BrpPersoonslijstBuilder builder) {
        final List<BrpGroep<BrpVoornaamInhoud>> brpVoornaamGroep1 = new ArrayList<BrpGroep<BrpVoornaamInhoud>>();
        brpVoornaamGroep1.add(new BrpGroep<BrpVoornaamInhoud>(new BrpVoornaamInhoud("Vorrnaam", 0), new BrpHistorie(
                new BrpDatum(20000101), new BrpDatum(20110101), new BrpDatumTijd(new Date()), new BrpDatumTijd(
                        new Date())), null, null, null));
        final BrpStapel<BrpVoornaamInhoud> voornaamStapel1 = new BrpStapel<BrpVoornaamInhoud>(brpVoornaamGroep1);

        builder.voornaamStapel(voornaamStapel1);
    }

    private void createVoornaamMultiple(final BrpPersoonslijstBuilder builder) {
        final List<BrpGroep<BrpVoornaamInhoud>> brpVoornaamGroep1 = new ArrayList<BrpGroep<BrpVoornaamInhoud>>();

        brpVoornaamGroep1.add(new BrpGroep<BrpVoornaamInhoud>(new BrpVoornaamInhoud("Voornaameen", 0),
                new BrpHistorie(new BrpDatum(20130101), new BrpDatum(20130101), new BrpDatumTijd(new Date()), null),
                null, null, null));
        brpVoornaamGroep1.add(new BrpGroep<BrpVoornaamInhoud>(new BrpVoornaamInhoud("Voornaamtwee", 0),
                new BrpHistorie(new BrpDatum(20100101), new BrpDatum(20100101), new BrpDatumTijd(new Date()),
                        new BrpDatumTijd(new Date())), null, null, null));
        final BrpStapel<BrpVoornaamInhoud> voornaamStapel1 = new BrpStapel<BrpVoornaamInhoud>(brpVoornaamGroep1);

        builder.voornaamStapel(voornaamStapel1);
    }

    private void createReisdoc(final BrpPersoonslijstBuilder builder) {
        final BrpReisdocumentInhoud brpReisdocumentInhoud =
                new BrpReisdocumentInhoud(new BrpReisdocumentSoort("P"), "1", new BrpDatum(20110101), new BrpDatum(
                        20110101), new BrpReisdocumentAutoriteitVanAfgifte("D"), new BrpDatum(20110101),
                        new BrpDatum(20110101), new BrpReisdocumentRedenOntbreken("O"), Integer.valueOf(1));
        final List<BrpGroep<BrpReisdocumentInhoud>> reisdocumentInhoudGroep =
                new ArrayList<BrpGroep<BrpReisdocumentInhoud>>();
        reisdocumentInhoudGroep.add(new BrpGroep<BrpReisdocumentInhoud>(brpReisdocumentInhoud, new BrpHistorie(
                new BrpDatum(20000101), new BrpDatum(20110101), new BrpDatumTijd(new Date()), new BrpDatumTijd(
                        new Date())), null, null, null));

        builder.reisdocumentStapel(new BrpStapel<BrpReisdocumentInhoud>(reisdocumentInhoudGroep));
    }

    private void createNationaliteit(final BrpPersoonslijstBuilder builder) {
        final List<BrpGroep<BrpNationaliteitInhoud>> nationaliteitInhoudGroep =
                new ArrayList<BrpGroep<BrpNationaliteitInhoud>>();
        final BrpNationaliteitInhoud brpNationaliteitInhoud =
                new BrpNationaliteitInhoud(new BrpNationaliteitCode(Integer.valueOf(1)),
                        new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal(1)),
                        new BrpRedenVerliesNederlandschapCode(new BigDecimal(1)));
        nationaliteitInhoudGroep.add(new BrpGroep<BrpNationaliteitInhoud>(brpNationaliteitInhoud, new BrpHistorie(
                new BrpDatum(20000101), new BrpDatum(20110101), new BrpDatumTijd(new Date()), new BrpDatumTijd(
                        new Date())), null, null, null));
        builder.nationaliteitStapel(new BrpStapel<BrpNationaliteitInhoud>(nationaliteitInhoudGroep));
    }

}
