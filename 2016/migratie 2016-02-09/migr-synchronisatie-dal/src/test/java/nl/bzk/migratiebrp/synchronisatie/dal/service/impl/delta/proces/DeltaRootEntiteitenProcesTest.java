/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Betrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FunctieAdres;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.NadereBijhoudingsaard;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAdres;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAdresHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNummerverwijzingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortBetrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortMigratie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stapel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.AbstractAdresDeltaTest;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaRootEntiteitMatch;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;

import org.junit.Before;
import org.junit.Test;

public class DeltaRootEntiteitenProcesTest extends AbstractAdresDeltaTest {

    private DeltaProces deltaService;
    private Persoon bestaandePersoon;
    private Persoon nieuwePersoon;
    private boolean isAnummerWijziging;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        SynchronisatieLogging.init();
        deltaService = new DeltaRootEntiteitenProces();
        bestaandePersoon = getDbPersoon();
        nieuwePersoon = getKluizenaar();

        nieuwePersoon.getAdministratieveHandeling().setDatumTijdRegistratie(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    public void testANummerWijziging() throws ReflectiveOperationException {
        vulPersoonGroepen(bestaandePersoon, true);
        vulPersoonGroepen(nieuwePersoon, false);

        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er zou 1 nieuwe administratieve handeling moeten zijn", 1, nieuweAdministratieveHandelingen.size());

        final boolean anummerWijzigingGevonden =
                nieuweAdministratieveHandelingen.iterator().next().getSoort().equals(SoortAdministratieveHandeling.GBA_BIJHOUDING_OVERIG);
        assertTrue("Het type van de gevonden administratieve handelingen moet kloppen", anummerWijzigingGevonden);
    }

    @Test
    public void testAfgevoerd() throws ReflectiveOperationException {
        bestaandePersoon.setNadereBijhoudingsaard(NadereBijhoudingsaard.ACTUEEL);
        nieuwePersoon.setNadereBijhoudingsaard(NadereBijhoudingsaard.FOUT);
        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er moet maar 1 AH zijn", 1, nieuweAdministratieveHandelingen.size());
        final AdministratieveHandeling administratieveHandeling = nieuweAdministratieveHandelingen.get(0);
        assertEquals("AH moet van type GBA_AFVOEREN_PL zijn", SoortAdministratieveHandeling.GBA_AFVOEREN_PL, administratieveHandeling.getSoort());
    }

    @Test
    public void testNietAfgevoerdElementNieuw() throws ReflectiveOperationException {
        nieuwePersoon.setNadereBijhoudingsaard(NadereBijhoudingsaard.FOUT);
        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er moet maar 1 AH zijn", 1, nieuweAdministratieveHandelingen.size());
        final AdministratieveHandeling administratieveHandeling = nieuweAdministratieveHandelingen.get(0);
        assertEquals(SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL, administratieveHandeling.getSoort());
    }

    @Test
    public void testNietAfgevoerdBijEmigratie() throws ReflectiveOperationException {
        bestaandePersoon.setNadereBijhoudingsaard(NadereBijhoudingsaard.ACTUEEL);
        nieuwePersoon.setNadereBijhoudingsaard(NadereBijhoudingsaard.EMIGRATIE);
        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er moet maar 1 AH zijn", 1, nieuweAdministratieveHandelingen.size());
        final AdministratieveHandeling administratieveHandeling = nieuweAdministratieveHandelingen.get(0);
        assertEquals(SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL, administratieveHandeling.getSoort());
    }

    @Test
    public void testNietAfgevoerdBijWasAlAfgevoerd() throws ReflectiveOperationException {
        bestaandePersoon.setNadereBijhoudingsaard(NadereBijhoudingsaard.FOUT);
        nieuwePersoon.setNadereBijhoudingsaard(NadereBijhoudingsaard.FOUT);
        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er moet maar 1 AH zijn", 1, nieuweAdministratieveHandelingen.size());
        final AdministratieveHandeling administratieveHandeling = nieuweAdministratieveHandelingen.get(0);
        assertEquals(SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL, administratieveHandeling.getSoort());
    }

    @Test
    public void testInfrastructureleWijzigingRedenInfrastructureel() {
        vulPersoonGroepen(bestaandePersoon, true);
        vulPersoonGroepen(nieuwePersoon, true);

        voegAdresToeAanPersoon(bestaandePersoon, true);
        voegAdresToeAanPersoon(nieuwePersoon, true);

        final PersoonAdresHistorie nieuwAdresVoorkomen = voerActueleBijhoudingUitOpAdres(nieuwePersoon);

        // inhoudelijke wijziging adres
        nieuwAdresVoorkomen.setRedenWijziging(REDEN_WIJZIGING_INFRASTRUCTUREEL);
        nieuwAdresVoorkomen.setHuisletter('B');

        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er zou 1 nieuwe administratieve handelingen moeten zijn", 1, nieuweAdministratieveHandelingen.size());

        assertEquals(
            "Het ah type zou infra moeten zijn",
            SoortAdministratieveHandeling.GBA_INFRASTRUCTURELE_WIJZIGING,
            nieuweAdministratieveHandelingen.get(0).getSoort());
    }

    @Test
    public void testInfrastructureleWijzigingRedenBAG() {
        vulPersoonGroepen(bestaandePersoon, true);
        vulPersoonGroepen(nieuwePersoon, true);

        voegAdresToeAanPersoon(bestaandePersoon, true);
        voegAdresToeAanPersoon(nieuwePersoon, true);

        final PersoonAdresHistorie nieuwAdresVoorkomen = voerActueleBijhoudingUitOpAdres(nieuwePersoon);

        // inhoudelijke wijziging adres
        nieuwAdresVoorkomen.setRedenWijziging(REDEN_WIJZIGING_BAG);
        nieuwAdresVoorkomen.setHuisletter('B');

        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er zou 1 nieuwe administratieve handelingen moeten zijn", 1, nieuweAdministratieveHandelingen.size());

        assertEquals(
            "Het ah type zou infra moeten zijn",
            SoortAdministratieveHandeling.GBA_INFRASTRUCTURELE_WIJZIGING,
            nieuweAdministratieveHandelingen.get(0).getSoort());
    }

    @Test
    public void testInfrastructureleWijzigingRedenBAGOokAndereWijzigingen() {
        vulPersoonGroepen(bestaandePersoon, true);
        vulPersoonGroepen(nieuwePersoon, true);

        voegAdresToeAanPersoon(bestaandePersoon, true);
        voegAdresToeAanPersoon(nieuwePersoon, true);

        nieuwePersoon.setNadereBijhoudingsaard(NadereBijhoudingsaard.EMIGRATIE);

        final PersoonAdresHistorie nieuwAdresVoorkomen = voerActueleBijhoudingUitOpAdres(nieuwePersoon);

        // inhoudelijke wijziging adres
        nieuwAdresVoorkomen.setRedenWijziging(REDEN_WIJZIGING_BAG);
        nieuwAdresVoorkomen.setHuisletter('B');

        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er zou 1 nieuwe administratieve handelingen moeten zijn", 1, nieuweAdministratieveHandelingen.size());

        assertEquals(
            "Het ah type zou infra moeten zijn",
            SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL,
            nieuweAdministratieveHandelingen.get(0).getSoort());
    }

    @Test
    public void testInfrastructureleWijziginGeenNieuwAdres() {
        vulPersoonGroepen(bestaandePersoon, true);
        vulPersoonGroepen(nieuwePersoon, true);

        voegAdresToeAanPersoon(bestaandePersoon, true);
        voegAdresToeAanPersoon(nieuwePersoon, true);

        final PersoonAdresHistorie historieBestaand = bestaandePersoon.getPersoonAdresSet().iterator().next().getPersoonAdresHistorieSet().iterator().next();
        final PersoonAdresHistorie historieNieuw = nieuwePersoon.getPersoonAdresSet().iterator().next().getPersoonAdresHistorieSet().iterator().next();
        // inhoudelijke wijziging adres
        historieBestaand.setRedenWijziging(REDEN_WIJZIGING_BAG);
        historieBestaand.setHuisletter('B');
        historieNieuw.setRedenWijziging(REDEN_WIJZIGING_BAG);
        historieNieuw.setHuisletter('B');

        nieuwePersoon.setLandOfGebiedMigratie(maakLandOfGebied());
        nieuwePersoon.setSoortMigratie(SoortMigratie.EMIGRATIE);
        nieuwePersoon.setBuitenlandsAdresRegel1Migratie("1");
        nieuwePersoon.setBuitenlandsAdresRegel2Migratie("1");
        nieuwePersoon.setBuitenlandsAdresRegel3Migratie("1");

        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er zou 1 nieuwe administratieve handelingen moeten zijn", 1, nieuweAdministratieveHandelingen.size());

        assertEquals(
            "Het ah type zou infra moeten zijn",
            SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL,
            nieuweAdministratieveHandelingen.get(0).getSoort());
    }

    @Test
    public void testInfrastructureleWijzigingMigratie() {
        vulPersoonGroepen(bestaandePersoon, true);
        vulPersoonGroepen(nieuwePersoon, true);

        voegAdresToeAanPersoon(bestaandePersoon, true);
        voegAdresToeAanPersoon(nieuwePersoon, true);

        final PersoonAdresHistorie nieuwAdresVoorkomen = voerActueleBijhoudingUitOpAdres(nieuwePersoon);

        // inhoudelijke wijziging adres
        nieuwAdresVoorkomen.setRedenWijziging(REDEN_WIJZIGING_INFRASTRUCTUREEL);
        nieuwePersoon.getPersoonAdresSet().iterator().next().setRedenWijziging(REDEN_WIJZIGING_INFRASTRUCTUREEL);

        // aanpassing migratie velden
        nieuwePersoon.setLandOfGebiedMigratie(maakLandOfGebied());
        nieuwePersoon.setSoortMigratie(SoortMigratie.EMIGRATIE);
        nieuwePersoon.setBuitenlandsAdresRegel1Migratie("1");
        nieuwePersoon.setBuitenlandsAdresRegel2Migratie("1");
        nieuwePersoon.setBuitenlandsAdresRegel3Migratie("1");
        nieuwePersoon.setAangeverMigratie(AANGEVER_INGESCHREVENE);
        nieuwePersoon.setRedenWijzigingMigratie(REDEN_WIJZIGING_INFRASTRUCTUREEL);

        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er zou 1 nieuwe administratieve handelingen moeten zijn", 1, nieuweAdministratieveHandelingen.size());

        for (final AdministratieveHandeling ah : nieuweAdministratieveHandelingen) {
            assertEquals("Het ah type zou infra moeten zijn", SoortAdministratieveHandeling.GBA_INFRASTRUCTURELE_WIJZIGING, ah.getSoort());
        }
    }

    @Test
    public void testInfrastructureleWijzigingNaastAndereWijziging() {
        vulPersoonGroepen(bestaandePersoon, true);
        vulPersoonGroepen(nieuwePersoon, true);

        voegAdresToeAanPersoon(bestaandePersoon, true);
        voegAdresToeAanPersoon(nieuwePersoon, true);
        nieuwePersoon.getPersoonAdresSet().iterator().next().setRedenWijziging(REDEN_WIJZIGING_INFRASTRUCTUREEL);
        PersoonReisdocument reisDocument = new PersoonReisdocument(nieuwePersoon, new SoortNederlandsReisdocument("1", "ID"));
        reisDocument.setDatumUitgifte(20010101);
        reisDocument.setDatumIngangDocument(20010101);
        reisDocument.setAutoriteitVanAfgifte("BURGEMEESTER");
        nieuwePersoon.getPersoonReisdocumentSet().add(reisDocument);

        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er zou 1 nieuwe administratieve handelingen moeten zijn", 1, nieuweAdministratieveHandelingen.size());

        for (final AdministratieveHandeling ah : nieuweAdministratieveHandelingen) {
            assertEquals("Het ah type zou actueel moeten zijn", SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL, ah.getSoort());
        }
    }

    @Test
    public void testGeenInfrastructureleWijziging() {
        vulPersoonGroepen(bestaandePersoon, true);
        vulPersoonGroepen(nieuwePersoon, true);

        voegAdresToeAanPersoon(bestaandePersoon, true);
        voegAdresToeAanPersoon(nieuwePersoon, true);

        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er zou 1 nieuwe administratieve handelingen moeten zijn", 1, nieuweAdministratieveHandelingen.size());

        for (final AdministratieveHandeling ah : nieuweAdministratieveHandelingen) {
            assertEquals("Het ah type zou actueel moeten zijn", SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL, ah.getSoort());
        }
    }

    @Test
    public void testInfrastructureleWijzigingGeenAddressen() {
        vulPersoonGroepen(bestaandePersoon, true);
        vulPersoonGroepen(nieuwePersoon, true);
        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er zou 1 nieuwe administratieve handelingen moeten zijn", 1, nieuweAdministratieveHandelingen.size());

        for (final AdministratieveHandeling ah : nieuweAdministratieveHandelingen) {
            assertEquals("Het ah type zou actueel moeten zijn", SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL, ah.getSoort());
        }
    }

    @Test
    public void testResync() {
        vulPersoonGroepen(bestaandePersoon, true);
        vulPersoonGroepen(nieuwePersoon, false);

        final PersoonNummerverwijzingHistorie pnHis = maakPersoonNummerverwijzingHistorie(nieuwePersoon, false);
        pnHis.setVorigeAdministratienummer(null);
        pnHis.setVolgendeAdministratienummer(BESTAAND_ADMIN_NUMMER);

        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er zou 1 nieuwe administratieve handelingen moeten zijn", 1, nieuweAdministratieveHandelingen.size());

        for (final AdministratieveHandeling ah : nieuweAdministratieveHandelingen) {
            assertEquals("Het ah type zou resync moeten zijn", SoortAdministratieveHandeling.GBA_BIJHOUDING_OVERIG, ah.getSoort());
        }
    }

    @Test
    public void testSync() {
        vulPersoonGroepen(bestaandePersoon, true);
        vulPersoonGroepen(nieuwePersoon, true);

        nieuwePersoon.addPersoonNationaliteit(maakPersoonNationaliteit(
            nieuwePersoon,
            nieuwePersoon.getAdministratieveHandeling(),
            nieuwePersoon.getDatumtijdstempel()));

        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er zou 1 nieuwe administratieve handelingen moeten zijn", 1, nieuweAdministratieveHandelingen.size());

        for (final AdministratieveHandeling ah : nieuweAdministratieveHandelingen) {
            assertEquals("Het ah type zou sync moeten zijn", SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL, ah.getSoort());
        }
    }

    @Test
    public void testSyncEnInfra() {
        vulPersoonGroepen(bestaandePersoon, true);
        vulPersoonGroepen(nieuwePersoon, true);

        nieuwePersoon.addPersoonNationaliteit(maakPersoonNationaliteit(
            nieuwePersoon,
            nieuwePersoon.getAdministratieveHandeling(),
            nieuwePersoon.getDatumtijdstempel()));

        voegAdresToeAanPersoon(bestaandePersoon, true);
        voegAdresToeAanPersoon(nieuwePersoon, false);
        maakPersoonAdresHistorie(
            nieuwePersoon.getPersoonAdresSet().iterator().next(),
            false,
            REDEN_WIJZIGING_INFRASTRUCTUREEL,
            nieuwePersoon.getAdministratieveHandeling());

        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er zou 1 nieuwe administratieve handeling moeten zijn", 1, nieuweAdministratieveHandelingen.size());
    }

    @Test
    public void testAnummerWijziging() {
        isAnummerWijziging = true;
        final PersoonNummerverwijzingHistorie historie = new PersoonNummerverwijzingHistorie(nieuwePersoon);
        historie.setVorigeAdministratienummer(9876543210L);
        nieuwePersoon.addPersoonNummerverwijzingHistorie(historie);

        vulPersoonGroepen(bestaandePersoon, true);
        vulPersoonGroepen(nieuwePersoon, true);

        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er zou 1 nieuwe administratieve handelingen moeten zijn", 1, nieuweAdministratieveHandelingen.size());

        for (final AdministratieveHandeling ah : nieuweAdministratieveHandelingen) {
            assertEquals("Het ah type zou actueel moeten zijn", SoortAdministratieveHandeling.GBA_A_NUMMER_WIJZIGING, ah.getSoort());
        }
    }

    @Test
    public void testAnummerWijzigingBijhoudingIsAlOverig() {
        isAnummerWijziging = true;
        final PersoonNummerverwijzingHistorie historie = new PersoonNummerverwijzingHistorie(nieuwePersoon);
        historie.setVorigeAdministratienummer(9876543210L);
        nieuwePersoon.addPersoonNummerverwijzingHistorie(historie);

        vulPersoonGroepen(bestaandePersoon, true);
        vulPersoonGroepen(nieuwePersoon, true);

        final PersoonAdres persoonAdres = new PersoonAdres(bestaandePersoon);
        persoonAdres.addPersoonAdresHistorie(new PersoonAdresHistorie(persoonAdres, FunctieAdres.WOONADRES, maakLandOfGebied(), REDEN_WIJZIGING_AMBTSHALVE));
        bestaandePersoon.addPersoonAdres(persoonAdres);

        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals(2, nieuweAdministratieveHandelingen.size());
    }

    @Test
    public void testAnummerWijzigingWijzigingOokInRelatie() {
        isAnummerWijziging = true;
        final PersoonNummerverwijzingHistorie historie = new PersoonNummerverwijzingHistorie(nieuwePersoon);
        historie.setVorigeAdministratienummer(9876543210L);
        nieuwePersoon.addPersoonNummerverwijzingHistorie(historie);

        vulPersoonGroepen(bestaandePersoon, true);
        vulPersoonGroepen(nieuwePersoon, true);

        voegJuridischGeenOuderToe(bestaandePersoon);

        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals(2, nieuweAdministratieveHandelingen.size());
    }

    @Test
    public void testAnummerWijzigingAfgevoerd() {
        isAnummerWijziging = true;
        vulPersoonGroepen(bestaandePersoon, true);
        vulPersoonGroepen(nieuwePersoon, true);

        bestaandePersoon.setNadereBijhoudingsaard(NadereBijhoudingsaard.ACTUEEL);
        nieuwePersoon.setNadereBijhoudingsaard(NadereBijhoudingsaard.FOUT);

        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er zou 1 nieuwe administratieve handelingen moeten zijn", 2, nieuweAdministratieveHandelingen.size());
        boolean bevatAnrWijziging = false;
        boolean bevatAfgevoerd = false;
        for (final AdministratieveHandeling ah : nieuweAdministratieveHandelingen) {
            if (ah.getSoort().equals(SoortAdministratieveHandeling.GBA_AFVOEREN_PL)) {
                bevatAfgevoerd = true;
            } else if (ah.getSoort().equals(SoortAdministratieveHandeling.GBA_A_NUMMER_WIJZIGING)) {
                bevatAnrWijziging = true;
            }
        }

        assertTrue(bevatAfgevoerd);
        assertTrue(bevatAnrWijziging);
    }

    @Test
    public void testAnummerWijzigingOverig() {
        isAnummerWijziging = true;
        vulPersoonGroepen(bestaandePersoon, true);
        vulPersoonGroepen(nieuwePersoon, true);

        bestaandePersoon.setNadereBijhoudingsaard(NadereBijhoudingsaard.ACTUEEL);
        nieuwePersoon.setNadereBijhoudingsaard(NadereBijhoudingsaard.EMIGRATIE);

        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er zou 1 nieuwe administratieve handelingen moeten zijn", 2, nieuweAdministratieveHandelingen.size());
        boolean bevatAnrWijziging = false;
        boolean bevatOverig = false;
        for (final AdministratieveHandeling ah : nieuweAdministratieveHandelingen) {
            if (ah.getSoort().equals(SoortAdministratieveHandeling.GBA_BIJHOUDING_OVERIG)) {
                bevatOverig = true;
            } else if (ah.getSoort().equals(SoortAdministratieveHandeling.GBA_A_NUMMER_WIJZIGING)) {
                bevatAnrWijziging = true;
            }
        }

        assertTrue(bevatOverig);
        assertTrue(bevatAnrWijziging);
    }

    @Test
    public void testGeenVerschilResultaat() {
        final DeltaBepalingContext context = new DeltaBepalingContext(bestaandePersoon, bestaandePersoon, null, isAnummerWijziging);
        context.setDeltaRootEntiteitMatches(Collections.singleton(new DeltaRootEntiteitMatch(bestaandePersoon, bestaandePersoon, null, null)));
        deltaService.verwerkVerschillen(context);
        assertEquals(SoortAdministratieveHandeling.GBA_INITIELE_VULLING, context.getAdministratieveHandelingGekoppeldAanActies().getSoort());

        deltaService.bepaalVerschillen(context);
        deltaService.verwerkVerschillen(context);
        assertEquals(SoortAdministratieveHandeling.GBA_INITIELE_VULLING, context.getAdministratieveHandelingGekoppeldAanActies().getSoort());
    }

    @Test
    public void testEntiteitNieuw() {
        voegJuridischGeenOuderToe(nieuwePersoon);

        final List<AdministratieveHandeling> ahs = testService();
        assertTrue(ahs.size() == 1);
        assertEquals(SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL, ahs.get(0).getSoort());
    }

    private void voegJuridischGeenOuderToe(final Persoon persoon) {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Stapel stapel = new Stapel(persoon, "02", 0);
        final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);
        stapel.addRelatie(relatie);
        relatie.addStapel(stapel);
        relatie.addBetrokkenheid(betrokkenheid);
        persoon.addBetrokkenheid(betrokkenheid);
        persoon.addStapel(stapel);
    }

    private List<AdministratieveHandeling> testService() {
        final DeltaBepalingContext context = new DeltaBepalingContext(nieuwePersoon, bestaandePersoon, null, isAnummerWijziging);
        deltaService.bepaalVerschillen(context);
        deltaService.verwerkVerschillen(context);
        return (List<AdministratieveHandeling>) context.getAdministratieveHandelingen();
    }
}
