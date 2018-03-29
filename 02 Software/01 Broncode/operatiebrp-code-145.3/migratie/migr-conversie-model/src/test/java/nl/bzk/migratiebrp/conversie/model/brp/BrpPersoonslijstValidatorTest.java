/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.exceptions.PreconditieException;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import org.junit.Test;

public class BrpPersoonslijstValidatorTest {

    private static final BrpSamengesteldeNaamInhoud SAMENGESTELDE_NAAM_INHOUD = new BrpSamengesteldeNaamInhoud(
            null,
            new BrpString("Pietje"),
            null,
            null,
            null,
            new BrpString("Jansen"),
            new BrpBoolean(false),
            new BrpBoolean(false));

    @Test(expected = AssertionError.class)
    public void testPrivateConstructor() throws Throwable {
        try {
            Constructor<BrpPersoonslijstValidator> c = BrpPersoonslijstValidator.class.getDeclaredConstructor();
            c.setAccessible(true);
            BrpPersoonslijstValidator u = c.newInstance();
        } catch (Exception e) {
            throw e.getCause();
        }
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE058)
    public void testValideerBehandeldAlsNederlanderVastgesteldNietNederlanderGelijk() {
        final BrpHistorie historie = BrpStapelHelper.his(20010101, 20010102000000L);
        final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel = maakBehandeldAlsNederlanderStapel(historie);

        final BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel =
                maakVastgesteldNietNederlanderStapel(true, historie);
        try {
            BrpPersoonslijstValidator.valideerBehandeldAlsNederlanderVastgesteldNietNederlander(
                    behandeldAlsNederlanderIndicatieStapel,
                    vastgesteldNietNederlanderIndicatieStapel);
            fail("Exceptie verwacht omdat behandeld en vastgesteld beiden tegelijk geldig zijn.");
        } catch (final PreconditieException e) {
            assertTrue(e.getMessage().contains(SoortMeldingCode.PRE058.name()));
        }
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE058)
    public void testAlleenVastgesteldNietNederlanderIndicatie() {
        final BrpHistorie historie = BrpStapelHelper.his(20010101, 20010102000000L);
        final BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel =
                maakVastgesteldNietNederlanderStapel(true, historie);
        BrpPersoonslijstValidator.valideerBehandeldAlsNederlanderVastgesteldNietNederlander(null, vastgesteldNietNederlanderIndicatieStapel);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE058)
    public void testAlleenBehandeldAlsNederlanderIndicatie() {
        final BrpHistorie historie = BrpStapelHelper.his(20010101, 20010102000000L);
        final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel = maakBehandeldAlsNederlanderStapel(historie);
        BrpPersoonslijstValidator.valideerBehandeldAlsNederlanderVastgesteldNietNederlander(behandeldAlsNederlanderIndicatieStapel, null);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE058)
    public void testBeideIndicatieNull() {
        BrpPersoonslijstValidator.valideerBehandeldAlsNederlanderVastgesteldNietNederlander(null, null);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE058)
    public void testBeideIndicatiesGevuldEenFalseEenTrue() {
        final BrpHistorie historie = BrpStapelHelper.his(20010101, 20010102000000L);
        final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel = maakBehandeldAlsNederlanderStapel(historie);

        final BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel =
                maakVastgesteldNietNederlanderStapel(false, historie);
        BrpPersoonslijstValidator.valideerBehandeldAlsNederlanderVastgesteldNietNederlander(
                behandeldAlsNederlanderIndicatieStapel,
                vastgesteldNietNederlanderIndicatieStapel);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE058)
    public void testBehandeldisActueelVastgesteldIsNietActueel() {
        final BrpHistorie historieActueel = BrpHistorieTest.createdefaultInhoud();
        final BrpHistorie historieNietActueel = BrpHistorieTest.createNietActueleInhoud();

        final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel =
                maakBehandeldAlsNederlanderStapel(historieActueel);

        final BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel =
                maakVastgesteldNietNederlanderStapel(true, historieNietActueel);
        BrpPersoonslijstValidator.valideerBehandeldAlsNederlanderVastgesteldNietNederlander(
                behandeldAlsNederlanderIndicatieStapel,
                vastgesteldNietNederlanderIndicatieStapel);
        // Dit zou gewoon goed moeten gaan
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE043)
    public void testSamengesteldeNaamNullStapel() {
        try {
            BrpPersoonslijstValidator.valideerActueleGeslachtsnaam(null);
            fail("PreconditieException met PRE043 verwacht");
        } catch (final PreconditieException pe) {
            assertEquals(SoortMeldingCode.PRE043.name(), pe.getPreconditieNaam());
        }
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE043)
    public void testSamengesteldeNaamActueelStapel() {
        final BrpHistorie historie = new BrpHistorie(BrpDatumTijd.fromDatum(20120101, null), null, null);
        final BrpGroep<BrpSamengesteldeNaamInhoud> groep = new BrpGroep<>(SAMENGESTELDE_NAAM_INHOUD, historie, null, null, null);
        final BrpStapel<BrpSamengesteldeNaamInhoud> stapel = new BrpStapel<>(Arrays.asList(groep));

        try {
            BrpPersoonslijstValidator.valideerActueleGeslachtsnaam(stapel);
        } catch (final PreconditieException pe) {
            fail("Geen preconditieException met PRE043 verwacht");
        }
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE043)
    public void testSamengesteldeNaamVervallenStapel() {
        final BrpHistorie historie = new BrpHistorie(BrpDatumTijd.fromDatum(20120101, null), BrpDatumTijd.fromDatum(20120102, null), null);
        final BrpGroep<BrpSamengesteldeNaamInhoud> groep = new BrpGroep<>(SAMENGESTELDE_NAAM_INHOUD, historie, null, null, null);
        final BrpStapel<BrpSamengesteldeNaamInhoud> stapel = new BrpStapel<>(Arrays.asList(groep));

        try {
            BrpPersoonslijstValidator.valideerActueleGeslachtsnaam(stapel);
            fail("PreconditieException met PRE043 verwacht");
        } catch (final PreconditieException pe) {
            assertEquals(SoortMeldingCode.PRE043.name(), pe.getPreconditieNaam());
        }
    }

    @Test
    public void testValideerIstInhoudMetNull() {
        BrpPersoonslijstValidator.valideerIstInhoud(null);
        // er zou niks moeten gebeuren!
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE017)
    public void testValideerGeprivilegieerde() {
        try {
            BrpPersoonslijstValidator.valideerGeprivilegieerde(null, BrpNationaliteitInhoudTest.createList(true));
            BrpPersoonslijstValidator.valideerGeprivilegieerde(
                    BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoudTest.createStapel(),
                    BrpNationaliteitInhoudTest.createList(false));
        } catch (final PreconditieException pe) {
            assertEquals(SoortMeldingCode.PRE017.name(), pe.getPreconditieNaam());
        }
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE043)
    public void testSamengesteldeNaamEindeGeldigheidStapel() {
        final BrpHistorie historie = new BrpHistorie(BrpDatum.ONBEKEND, BrpDatum.ONBEKEND, BrpDatumTijd.fromDatum(20120101, null), null, null);
        final BrpGroep<BrpSamengesteldeNaamInhoud> groep = new BrpGroep<>(SAMENGESTELDE_NAAM_INHOUD, historie, null, null, null);
        final BrpStapel<BrpSamengesteldeNaamInhoud> stapel = new BrpStapel<>(Arrays.asList(groep));

        try {
            BrpPersoonslijstValidator.valideerActueleGeslachtsnaam(stapel);
            fail("PreconditieException met PRE043 verwacht");
        } catch (final PreconditieException pe) {
            assertEquals(SoortMeldingCode.PRE043.name(), pe.getPreconditieNaam());
        }
    }

    private BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> maakVastgesteldNietNederlanderStapel(
            final boolean indicatie,
            final BrpHistorie... histories) {
        final List<BrpGroep<BrpVastgesteldNietNederlanderIndicatieInhoud>> groepen = new ArrayList<>();
        final BrpVastgesteldNietNederlanderIndicatieInhoud inhoud =
                new BrpVastgesteldNietNederlanderIndicatieInhoud(new BrpBoolean(indicatie), null, null);
        for (final BrpHistorie historie : histories) {
            final BrpGroep<BrpVastgesteldNietNederlanderIndicatieInhoud> groep = new BrpGroep<>(inhoud, historie, null, null, null);
            groepen.add(groep);
        }
        return new BrpStapel<>(groepen);
    }

    private BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> maakBehandeldAlsNederlanderStapel(final BrpHistorie... histories) {
        final List<BrpGroep<BrpBehandeldAlsNederlanderIndicatieInhoud>> groepen = new ArrayList<>();
        final BrpBehandeldAlsNederlanderIndicatieInhoud inhoud = new BrpBehandeldAlsNederlanderIndicatieInhoud(new BrpBoolean(true), null, null);
        for (final BrpHistorie historie : histories) {
            final BrpGroep<BrpBehandeldAlsNederlanderIndicatieInhoud> groep = new BrpGroep<>(inhoud, historie, null, null, null);
            groepen.add(groep);
        }
        return new BrpStapel<>(groepen);
    }
}
