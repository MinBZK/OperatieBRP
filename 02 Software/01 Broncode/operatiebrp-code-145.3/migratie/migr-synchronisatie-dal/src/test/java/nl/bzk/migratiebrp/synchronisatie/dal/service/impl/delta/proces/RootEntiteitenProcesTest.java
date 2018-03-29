/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNummerverwijzingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocumentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.AbstractAdresDeltaTest;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaRootEntiteitMatch;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;

import org.junit.Before;
import org.junit.Test;

public class RootEntiteitenProcesTest extends AbstractAdresDeltaTest {

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

        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(nieuwePersoon.getPersoonAfgeleidAdministratiefHistorieSet())
                .getAdministratieveHandeling()
                .setDatumTijdRegistratie(new Timestamp(System.currentTimeMillis()));
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
        bestaandePersoon.addPersoonBijhoudingHistorie(maakBijhoudingHistorie(bestaandePersoon, NadereBijhoudingsaard.ACTUEEL));
        nieuwePersoon.addPersoonBijhoudingHistorie(maakBijhoudingHistorie(nieuwePersoon, NadereBijhoudingsaard.FOUT));

        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er moet maar 1 AH zijn", 1, nieuweAdministratieveHandelingen.size());
        final AdministratieveHandeling administratieveHandeling = nieuweAdministratieveHandelingen.get(0);
        assertEquals("AH moet van type GBA_AFVOEREN_PL zijn", SoortAdministratieveHandeling.GBA_AFVOEREN_PL, administratieveHandeling.getSoort());
    }

    private PersoonBijhoudingHistorie maakBijhoudingHistorie(final Persoon persoon, final NadereBijhoudingsaard nadereBijhoudingsaard) {
        final Timestamp timestamp = Timestamp.from(Instant.now());
        final Partij partij = maakPartij();
        final PersoonBijhoudingHistorie bestaandeBijhoudingHistorie =
                new PersoonBijhoudingHistorie(persoon, partij, Bijhoudingsaard.INGEZETENE, nadereBijhoudingsaard);
        final BRPActie actieInhoud = maakBrpActie(maakAdministratieveHandeling(timestamp), timestamp);
        actieInhoud.setLo3Voorkomen(new Lo3Voorkomen(new Lo3Bericht("ref", Lo3BerichtenBron.INITIELE_VULLING, timestamp, "data", true), "07"));
        bestaandeBijhoudingHistorie.setActieInhoud(actieInhoud);
        return bestaandeBijhoudingHistorie;
    }

    @Test
    public void testNietAfgevoerdBijEmigratie() throws ReflectiveOperationException {
        bestaandePersoon.addPersoonBijhoudingHistorie(maakBijhoudingHistorie(bestaandePersoon, NadereBijhoudingsaard.ACTUEEL));
        nieuwePersoon.addPersoonBijhoudingHistorie(maakBijhoudingHistorie(nieuwePersoon, NadereBijhoudingsaard.EMIGRATIE));
        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er moet maar 1 AH zijn", 1, nieuweAdministratieveHandelingen.size());
        final AdministratieveHandeling administratieveHandeling = nieuweAdministratieveHandelingen.get(0);
        assertEquals(SoortAdministratieveHandeling.GBA_BIJHOUDING_OVERIG, administratieveHandeling.getSoort());
    }

    @Test
    public void testNietAfgevoerdBijWasAlAfgevoerd() throws ReflectiveOperationException {
        final PersoonBijhoudingHistorie foutBestaandeBijhoudingHistorie = maakBijhoudingHistorie(bestaandePersoon, NadereBijhoudingsaard.FOUT);
        final PersoonBijhoudingHistorie foutNieuweBijhoudingHistorie = new PersoonBijhoudingHistorie(foutBestaandeBijhoudingHistorie);
        foutNieuweBijhoudingHistorie.setPersoon(nieuwePersoon);
        bestaandePersoon.addPersoonBijhoudingHistorie(foutBestaandeBijhoudingHistorie);
        nieuwePersoon.addPersoonBijhoudingHistorie(foutNieuweBijhoudingHistorie);
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
        final PersoonReisdocument reisdocument = new PersoonReisdocument(nieuwePersoon, new SoortNederlandsReisdocument("1", "ID"));
        final PersoonReisdocumentHistorie reisdocumentHistorie =
                new PersoonReisdocumentHistorie(20010101, 20010101, 20100101, "123456", "BURGEMEESTER", reisdocument);
        reisdocument.addPersoonReisdocumentHistorieSet(reisdocumentHistorie);
        nieuwePersoon.getPersoonReisdocumentSet().add(reisdocument);

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

        final AdministratieveHandeling administratieveHandeling =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(nieuwePersoon.getPersoonAfgeleidAdministratiefHistorieSet())
                        .getAdministratieveHandeling();
        final Timestamp datumtijdstempel =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(nieuwePersoon.getPersoonInschrijvingHistorieSet()).getDatumtijdstempel();

        nieuwePersoon.addPersoonNationaliteit(maakPersoonNationaliteit(nieuwePersoon, administratieveHandeling, datumtijdstempel));

        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        System.out.println("AH: " + nieuweAdministratieveHandelingen);

        assertEquals("Er zou 1 nieuwe administratieve handelingen moeten zijn", 1, nieuweAdministratieveHandelingen.size());

        for (final AdministratieveHandeling ah : nieuweAdministratieveHandelingen) {
            assertEquals("Het ah type zou sync moeten zijn", SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL, ah.getSoort());
        }
    }

    @Test
    public void testSyncEnInfra() {
        vulPersoonGroepen(bestaandePersoon, true);
        vulPersoonGroepen(nieuwePersoon, true);

        final AdministratieveHandeling administratieveHandeling =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(nieuwePersoon.getPersoonAfgeleidAdministratiefHistorieSet())
                        .getAdministratieveHandeling();
        final Timestamp datumtijdstempel =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(nieuwePersoon.getPersoonInschrijvingHistorieSet()).getDatumtijdstempel();

        nieuwePersoon.addPersoonNationaliteit(maakPersoonNationaliteit(nieuwePersoon, administratieveHandeling, datumtijdstempel));

        voegAdresToeAanPersoon(bestaandePersoon, true);
        voegAdresToeAanPersoon(nieuwePersoon, false);

        maakPersoonAdresHistorie(nieuwePersoon.getPersoonAdresSet().iterator().next(), false, REDEN_WIJZIGING_INFRASTRUCTUREEL, administratieveHandeling);

        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals("Er zou 1 nieuwe administratieve handeling moeten zijn", 1, nieuweAdministratieveHandelingen.size());
    }

    @Test
    public void testAnummerWijziging() {
        isAnummerWijziging = true;
        final PersoonNummerverwijzingHistorie historie = new PersoonNummerverwijzingHistorie(nieuwePersoon);
        historie.setVorigeAdministratienummer("9876543210");
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
        historie.setVorigeAdministratienummer("9876543210");
        nieuwePersoon.addPersoonNummerverwijzingHistorie(historie);

        vulPersoonGroepen(bestaandePersoon, true);
        vulPersoonGroepen(nieuwePersoon, true);

        final PersoonAdres persoonAdres = new PersoonAdres(bestaandePersoon);
        persoonAdres.addPersoonAdresHistorie(new PersoonAdresHistorie(persoonAdres, SoortAdres.WOONADRES, maakLandOfGebied(), REDEN_WIJZIGING_AMBTSHALVE));
        bestaandePersoon.addPersoonAdres(persoonAdres);

        final List<AdministratieveHandeling> nieuweAdministratieveHandelingen = testService();

        assertEquals(2, nieuweAdministratieveHandelingen.size());
    }

    @Test
    public void testAnummerWijzigingWijzigingOokInRelatie() {
        isAnummerWijziging = true;
        final PersoonNummerverwijzingHistorie historie = new PersoonNummerverwijzingHistorie(nieuwePersoon);
        historie.setVorigeAdministratienummer("9876543210");
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

        bestaandePersoon.addPersoonBijhoudingHistorie(maakBijhoudingHistorie(bestaandePersoon, NadereBijhoudingsaard.ACTUEEL));
        nieuwePersoon.addPersoonBijhoudingHistorie(maakBijhoudingHistorie(nieuwePersoon, NadereBijhoudingsaard.FOUT));

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
