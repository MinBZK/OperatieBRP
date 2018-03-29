/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Test;

/**
 * Unittest voor {@link Persoon}.
 */
public class PersoonTest {
    private static final Timestamp TIJDSTIP = new Timestamp(System.currentTimeMillis());
    private final Partij partij = new Partij("naam1", "000001");
    private final AdministratieveHandeling handeling = new AdministratieveHandeling(partij, SoortAdministratieveHandeling.GBA_INITIELE_VULLING, TIJDSTIP);
    private final BRPActie brpActie = new BRPActie(SoortActie.CONVERSIE_GBA, handeling, partij, TIJDSTIP);

    @Test
    public void testGetRelaties() {
        final Relatie huwelijk = new Relatie(SoortRelatie.HUWELIJK);
        final Relatie geregistreerdPartnerschap = new Relatie(SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        final Relatie familierechtelijkeBetrekking = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        final Betrokkenheid partnerHuwelijk = new Betrokkenheid(SoortBetrokkenheid.PARTNER, huwelijk);
        final Betrokkenheid partnerGP = new Betrokkenheid(SoortBetrokkenheid.PARTNER, geregistreerdPartnerschap);
        final Betrokkenheid ouder = new Betrokkenheid(SoortBetrokkenheid.OUDER, familierechtelijkeBetrekking);

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        assertEquals(0, persoon.getRelaties().size());

        persoon.addBetrokkenheid(partnerGP);
        persoon.addBetrokkenheid(partnerHuwelijk);
        persoon.addBetrokkenheid(ouder);
        assertEquals(3, persoon.getRelaties().size());

        assertEquals(2, persoon.getRelaties(SoortBetrokkenheid.PARTNER).size());
        assertEquals(1, persoon.getRelaties(SoortBetrokkenheid.OUDER).size());
        assertEquals(0, persoon.getRelaties(SoortBetrokkenheid.KIND).size());
    }

    @Test
    public void testLeidAf() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        assertTrue(persoon.isPersoonIngeschrevene());
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = maakPersoonSamengesteldeNaamHistorie(persoon, true);
        persoon.addPersoonSamengesteldeNaamHistorie(samengesteldeNaamHistorie);

        PersoonGeslachtsnaamcomponent persoonGeslachtsnaamComponenent = new PersoonGeslachtsnaamcomponent(persoon, 1);
        persoonGeslachtsnaamComponenent.setActueelEnGeldig(true);
        persoonGeslachtsnaamComponenent
                .addPersoonGeslachtsnaamcomponentHistorie(new PersoonGeslachtsnaamcomponentHistorie(persoonGeslachtsnaamComponenent, "stam"));
        persoon.addPersoonGeslachtsnaamcomponent(persoonGeslachtsnaamComponenent);

        final Timestamp now1 = new Timestamp(Timestamp.from(Instant.now()).getTime() - 5 * 60 * 1000);

        final BRPActie actie = new BRPActie();
        actie.setDatumTijdRegistratie(now1);
        assertEquals(1, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
        persoon.leidAf(actie, DatumUtil.vandaag(), false, Boolean.TRUE);

        assertEquals(3, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
    }

    @Test
    public void testAfleidingNaamgebruikEigen() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        assertTrue(persoon.isPersoonIngeschrevene());
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = maakPersoonSamengesteldeNaamHistorie(persoon, true);
        persoon.addPersoonSamengesteldeNaamHistorie(samengesteldeNaamHistorie);

        final Timestamp now1 = new Timestamp(Timestamp.from(Instant.now()).getTime() - 5 * 60 * 1000);
        final Timestamp now2 = new Timestamp(Timestamp.from(Instant.now()).getTime() - 3 * 60 * 1000);
        final Timestamp now3 = new Timestamp(Timestamp.from(Instant.now()).getTime() - 1 * 60 * 1000);

        final BRPActie actie = new BRPActie();
        actie.setDatumTijdRegistratie(now1);
        assertEquals(0, persoon.getPersoonNaamgebruikHistorieSet().size());
        final Naamgebruik naamgebruik = Naamgebruik.EIGEN;
        persoon.leidtNaamgebruikAf(naamgebruik, actie, false);
        assertEquals(1, persoon.getPersoonNaamgebruikHistorieSet().size());
        final PersoonNaamgebruikHistorie naamgebruikHistorie = persoon.getPersoonNaamgebruikHistorieSet().iterator().next();

        assertEquals(naamgebruik, naamgebruikHistorie.getNaamgebruik());
        assertTrue(naamgebruikHistorie.getIndicatieNaamgebruikAfgeleid());
        assertEquals(actie, naamgebruikHistorie.getActieInhoud());

        assertEquals(samengesteldeNaamHistorie.getAdellijkeTitel(), naamgebruikHistorie.getAdellijkeTitel());
        assertEquals(samengesteldeNaamHistorie.getGeslachtsnaamstam(), naamgebruikHistorie.getGeslachtsnaamstamNaamgebruik());
        assertEquals(samengesteldeNaamHistorie.getPredicaat(), naamgebruikHistorie.getPredicaat());
        assertEquals(samengesteldeNaamHistorie.getScheidingsteken(), naamgebruikHistorie.getScheidingstekenNaamgebruik());
        assertEquals(samengesteldeNaamHistorie.getVoornamen(), naamgebruikHistorie.getVoornamenNaamgebruik());
        assertEquals(samengesteldeNaamHistorie.getVoorvoegsel(), naamgebruikHistorie.getVoorvoegselNaamgebruik());

        final BRPActie actie1 = new BRPActie();
        actie1.setDatumTijdRegistratie(now2);
        persoon.leidtNaamgebruikAf(actie1, false);
        assertEquals(1, persoon.getPersoonNaamgebruikHistorieSet().size());

        assertEquals(samengesteldeNaamHistorie.getAdellijkeTitel(), naamgebruikHistorie.getAdellijkeTitel());
        assertEquals(samengesteldeNaamHistorie.getGeslachtsnaamstam(), naamgebruikHistorie.getGeslachtsnaamstamNaamgebruik());
        assertEquals(samengesteldeNaamHistorie.getPredicaat(), naamgebruikHistorie.getPredicaat());
        assertEquals(samengesteldeNaamHistorie.getScheidingsteken(), naamgebruikHistorie.getScheidingstekenNaamgebruik());
        assertEquals(samengesteldeNaamHistorie.getVoornamen(), naamgebruikHistorie.getVoornamenNaamgebruik());
        assertEquals(samengesteldeNaamHistorie.getVoorvoegsel(), naamgebruikHistorie.getVoorvoegselNaamgebruik());

        // nu expliciet (als actie wordt deze meegegeven in de bijhouding
        final BRPActie actie2 = new BRPActie();
        actie2.setDatumTijdRegistratie(now3);
        persoon.leidtNaamgebruikAf(actie2, true);
        assertEquals(2, persoon.getPersoonNaamgebruikHistorieSet().size());

    }

    @Test
    public void testAfleidingNaamgebruikPartner() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = maakPersoonSamengesteldeNaamHistorie(persoon, true);
        persoon.addPersoonSamengesteldeNaamHistorie(samengesteldeNaamHistorie);

        final Persoon partner = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonSamengesteldeNaamHistorie partnerSamengesteldeNaamHistorie = maakPartnerSamengesteldeNaamHistorie(partner, true);
        partner.addPersoonSamengesteldeNaamHistorie(partnerSamengesteldeNaamHistorie);

        maakHuwelijk(persoon, partner);

        final BRPActie actie = new BRPActie();
        actie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        assertEquals(0, persoon.getPersoonNaamgebruikHistorieSet().size());
        final Naamgebruik naamgebruik = Naamgebruik.PARTNER;
        persoon.leidtNaamgebruikAf(naamgebruik, actie, false);
        assertEquals(1, persoon.getPersoonNaamgebruikHistorieSet().size());
        final PersoonNaamgebruikHistorie naamgebruikHistorie = persoon.getPersoonNaamgebruikHistorieSet().iterator().next();

        assertEquals(naamgebruik, naamgebruikHistorie.getNaamgebruik());
        assertTrue(naamgebruikHistorie.getIndicatieNaamgebruikAfgeleid());
        assertEquals(actie, naamgebruikHistorie.getActieInhoud());

        assertEquals(samengesteldeNaamHistorie.getVoornamen(), naamgebruikHistorie.getVoornamenNaamgebruik());
        assertEquals(samengesteldeNaamHistorie.getAdellijkeTitel(), naamgebruikHistorie.getAdellijkeTitel());
        assertEquals(samengesteldeNaamHistorie.getPredicaat(), naamgebruikHistorie.getPredicaat());

        assertEquals(partnerSamengesteldeNaamHistorie.getGeslachtsnaamstam(), naamgebruikHistorie.getGeslachtsnaamstamNaamgebruik());
        assertEquals(partnerSamengesteldeNaamHistorie.getScheidingsteken(), naamgebruikHistorie.getScheidingstekenNaamgebruik());
        assertEquals(partnerSamengesteldeNaamHistorie.getVoorvoegsel(), naamgebruikHistorie.getVoorvoegselNaamgebruik());
    }

    @Test
    public void testAfleidingNaamgebruikPartnerNaEigen() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = maakPersoonSamengesteldeNaamHistorie(persoon, true);
        persoon.addPersoonSamengesteldeNaamHistorie(samengesteldeNaamHistorie);

        final Persoon partner = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonSamengesteldeNaamHistorie partnerSamengesteldeNaamHistorie = maakPartnerSamengesteldeNaamHistorie(partner, true);
        partner.addPersoonSamengesteldeNaamHistorie(partnerSamengesteldeNaamHistorie);

        maakHuwelijk(persoon, partner);

        final BRPActie actie = new BRPActie();
        actie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        assertEquals(0, persoon.getPersoonNaamgebruikHistorieSet().size());
        final Naamgebruik naamgebruik = Naamgebruik.PARTNER_NA_EIGEN;
        persoon.leidtNaamgebruikAf(naamgebruik, actie, false);
        assertEquals(1, persoon.getPersoonNaamgebruikHistorieSet().size());
        final PersoonNaamgebruikHistorie naamgebruikHistorie = persoon.getPersoonNaamgebruikHistorieSet().iterator().next();

        assertEquals(naamgebruik, naamgebruikHistorie.getNaamgebruik());
        assertTrue(naamgebruikHistorie.getIndicatieNaamgebruikAfgeleid());
        assertEquals(actie, naamgebruikHistorie.getActieInhoud());

        assertEquals(samengesteldeNaamHistorie.getVoornamen(), naamgebruikHistorie.getVoornamenNaamgebruik());
        assertEquals(samengesteldeNaamHistorie.getAdellijkeTitel(), naamgebruikHistorie.getAdellijkeTitel());
        assertEquals(samengesteldeNaamHistorie.getPredicaat(), naamgebruikHistorie.getPredicaat());
        assertEquals(samengesteldeNaamHistorie.getScheidingsteken(), naamgebruikHistorie.getScheidingstekenNaamgebruik());
        assertEquals(samengesteldeNaamHistorie.getVoorvoegsel(), naamgebruikHistorie.getVoorvoegselNaamgebruik());

        String verwachteNaam = samengesteldeNaamHistorie.getGeslachtsnaamstam() + "-" + partnerSamengesteldeNaamHistorie.getVoorvoegsel() +
                partnerSamengesteldeNaamHistorie.getScheidingsteken() + partnerSamengesteldeNaamHistorie.getGeslachtsnaamstam();
        assertEquals(verwachteNaam, naamgebruikHistorie.getGeslachtsnaamstamNaamgebruik());
    }

    @Test
    public void testAfleidingNaamgebruikPartnerNaEigenGeenScheidingstekenEnVoorvoegsel() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = maakPersoonSamengesteldeNaamHistorie(persoon, false);
        persoon.addPersoonSamengesteldeNaamHistorie(samengesteldeNaamHistorie);

        final Persoon partner = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonSamengesteldeNaamHistorie partnerSamengesteldeNaamHistorie = maakPartnerSamengesteldeNaamHistorie(partner, false);
        partner.addPersoonSamengesteldeNaamHistorie(partnerSamengesteldeNaamHistorie);

        maakHuwelijk(persoon, partner);

        final BRPActie actie = new BRPActie();
        actie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        assertEquals(0, persoon.getPersoonNaamgebruikHistorieSet().size());
        final Naamgebruik naamgebruik = Naamgebruik.PARTNER_NA_EIGEN;
        persoon.leidtNaamgebruikAf(naamgebruik, actie, false);
        assertEquals(1, persoon.getPersoonNaamgebruikHistorieSet().size());
        final PersoonNaamgebruikHistorie naamgebruikHistorie = persoon.getPersoonNaamgebruikHistorieSet().iterator().next();

        assertEquals(naamgebruik, naamgebruikHistorie.getNaamgebruik());
        assertTrue(naamgebruikHistorie.getIndicatieNaamgebruikAfgeleid());
        assertEquals(actie, naamgebruikHistorie.getActieInhoud());

        assertEquals(samengesteldeNaamHistorie.getVoornamen(), naamgebruikHistorie.getVoornamenNaamgebruik());
        assertEquals(samengesteldeNaamHistorie.getAdellijkeTitel(), naamgebruikHistorie.getAdellijkeTitel());
        assertEquals(samengesteldeNaamHistorie.getPredicaat(), naamgebruikHistorie.getPredicaat());
        assertEquals(samengesteldeNaamHistorie.getScheidingsteken(), naamgebruikHistorie.getScheidingstekenNaamgebruik());
        assertEquals(samengesteldeNaamHistorie.getVoorvoegsel(), naamgebruikHistorie.getVoorvoegselNaamgebruik());

        String verwachteNaam = samengesteldeNaamHistorie.getGeslachtsnaamstam() + "-" + partnerSamengesteldeNaamHistorie.getGeslachtsnaamstam();
        assertEquals(verwachteNaam, naamgebruikHistorie.getGeslachtsnaamstamNaamgebruik());
    }

    @Test
    public void testAfleidingNaamgebruikPartnerVoorEigen() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = maakPersoonSamengesteldeNaamHistorie(persoon, true);
        persoon.addPersoonSamengesteldeNaamHistorie(samengesteldeNaamHistorie);

        final Persoon partner = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonSamengesteldeNaamHistorie partnerSamengesteldeNaamHistorie = maakPartnerSamengesteldeNaamHistorie(partner, true);
        partner.addPersoonSamengesteldeNaamHistorie(partnerSamengesteldeNaamHistorie);

        maakHuwelijk(persoon, partner);

        final BRPActie actie = new BRPActie();
        actie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        assertEquals(0, persoon.getPersoonNaamgebruikHistorieSet().size());
        final Naamgebruik naamgebruik = Naamgebruik.PARTNER_VOOR_EIGEN;
        persoon.leidtNaamgebruikAf(naamgebruik, actie, false);
        assertEquals(1, persoon.getPersoonNaamgebruikHistorieSet().size());
        final PersoonNaamgebruikHistorie naamgebruikHistorie = persoon.getPersoonNaamgebruikHistorieSet().iterator().next();

        assertEquals(naamgebruik, naamgebruikHistorie.getNaamgebruik());
        assertTrue(naamgebruikHistorie.getIndicatieNaamgebruikAfgeleid());
        assertEquals(actie, naamgebruikHistorie.getActieInhoud());

        assertEquals(samengesteldeNaamHistorie.getVoornamen(), naamgebruikHistorie.getVoornamenNaamgebruik());
        assertEquals(samengesteldeNaamHistorie.getAdellijkeTitel(), naamgebruikHistorie.getAdellijkeTitel());
        assertEquals(samengesteldeNaamHistorie.getPredicaat(), naamgebruikHistorie.getPredicaat());
        assertEquals(partnerSamengesteldeNaamHistorie.getScheidingsteken(), naamgebruikHistorie.getScheidingstekenNaamgebruik());
        assertEquals(partnerSamengesteldeNaamHistorie.getVoorvoegsel(), naamgebruikHistorie.getVoorvoegselNaamgebruik());

        String verwachteNaam = partnerSamengesteldeNaamHistorie.getGeslachtsnaamstam() + "-" + samengesteldeNaamHistorie.getVoorvoegsel() +
                samengesteldeNaamHistorie.getScheidingsteken() + samengesteldeNaamHistorie.getGeslachtsnaamstam();
        assertEquals(verwachteNaam, naamgebruikHistorie.getGeslachtsnaamstamNaamgebruik());
    }

    @Test
    public void testAfleidingNaamgebruikPartnerVoorEigenGeenVoorvoegselEnScheidingsteken() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = maakPersoonSamengesteldeNaamHistorie(persoon, false);
        persoon.addPersoonSamengesteldeNaamHistorie(samengesteldeNaamHistorie);

        final Persoon partner = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonSamengesteldeNaamHistorie partnerSamengesteldeNaamHistorie = maakPartnerSamengesteldeNaamHistorie(partner, false);
        partner.addPersoonSamengesteldeNaamHistorie(partnerSamengesteldeNaamHistorie);

        maakHuwelijk(persoon, partner);

        final BRPActie actie = new BRPActie();
        actie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        assertEquals(0, persoon.getPersoonNaamgebruikHistorieSet().size());
        final Naamgebruik naamgebruik = Naamgebruik.PARTNER_VOOR_EIGEN;
        persoon.leidtNaamgebruikAf(naamgebruik, actie, false);
        assertEquals(1, persoon.getPersoonNaamgebruikHistorieSet().size());
        final PersoonNaamgebruikHistorie naamgebruikHistorie = persoon.getPersoonNaamgebruikHistorieSet().iterator().next();

        assertEquals(naamgebruik, naamgebruikHistorie.getNaamgebruik());
        assertTrue(naamgebruikHistorie.getIndicatieNaamgebruikAfgeleid());
        assertEquals(actie, naamgebruikHistorie.getActieInhoud());

        assertEquals(samengesteldeNaamHistorie.getVoornamen(), naamgebruikHistorie.getVoornamenNaamgebruik());
        assertEquals(samengesteldeNaamHistorie.getAdellijkeTitel(), naamgebruikHistorie.getAdellijkeTitel());
        assertEquals(samengesteldeNaamHistorie.getPredicaat(), naamgebruikHistorie.getPredicaat());
        assertEquals(partnerSamengesteldeNaamHistorie.getScheidingsteken(), naamgebruikHistorie.getScheidingstekenNaamgebruik());
        assertEquals(partnerSamengesteldeNaamHistorie.getVoorvoegsel(), naamgebruikHistorie.getVoorvoegselNaamgebruik());

        String verwachteNaam = partnerSamengesteldeNaamHistorie.getGeslachtsnaamstam() + "-" + samengesteldeNaamHistorie.getGeslachtsnaamstam();
        assertEquals(verwachteNaam, naamgebruikHistorie.getGeslachtsnaamstamNaamgebruik());
    }

    @Test
    public void testIsPersoonVerwant() {
        final Persoon piet = new Persoon(SoortPersoon.INGESCHREVENE);
        piet.setId(1L);
        final Persoon kees = addKind(piet);
        kees.setId(2L);
        final Persoon truus = addKind(piet);
        truus.setId(3L);
        final Persoon klaas = addKind(truus);
        klaas.setId(4L);
        final Persoon miep = addKind(truus);
        miep.setId(5L);

        assertTrue("Kind met moeder", Persoon.bestaatVerwantschap(klaas, truus));
        assertTrue("Moeder met kind", Persoon.bestaatVerwantschap(truus, klaas));

        assertTrue("Kind met opa", Persoon.bestaatVerwantschap(miep, piet));
        assertTrue("opa met kleinkind", Persoon.bestaatVerwantschap(piet, miep));

        assertTrue("Broer met zus", Persoon.bestaatVerwantschap(klaas, miep));
        assertTrue("Zus met broer", Persoon.bestaatVerwantschap(miep, klaas));

        assertFalse("Miep met niet verwante", Persoon.bestaatVerwantschap(miep, kees));
        assertFalse("Miep met null", Persoon.bestaatVerwantschap(miep, null));
        assertFalse("null met Miep", Persoon.bestaatVerwantschap(null, miep));
        assertFalse("Miep met zichzelf", Persoon.bestaatVerwantschap(miep, miep));

    }

    private Persoon addKind(Persoon ouder) {
        Persoon kind = new Persoon(SoortPersoon.INGESCHREVENE);
        Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        Betrokkenheid kindBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, relatie);
        Betrokkenheid ouderBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);
        relatie.addBetrokkenheid(kindBetrokkenheid);
        relatie.addBetrokkenheid(ouderBetrokkenheid);
        ouder.addBetrokkenheid(ouderBetrokkenheid);
        kind.addBetrokkenheid(kindBetrokkenheid);

        voegActueleBetrokkenheidHistorieToe(kindBetrokkenheid);
        voegActueleBetrokkenheidHistorieToe(ouderBetrokkenheid);

        return kind;
    }

    @Test
    public void testGetBetrokkenheidSetVoorSoort() {
        //setup
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final Relatie kind1 = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Relatie kind2 = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Relatie ouders = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Relatie huwelijk1 = new Relatie(SoortRelatie.HUWELIJK);
        final Relatie huwelijk2 = new Relatie(SoortRelatie.HUWELIJK);
        final Betrokkenheid mijnPartner1 = new Betrokkenheid(SoortBetrokkenheid.PARTNER, huwelijk1);
        final Betrokkenheid mijnPartner2 = new Betrokkenheid(SoortBetrokkenheid.PARTNER, huwelijk1);
        final Betrokkenheid mijnKind1 = new Betrokkenheid(SoortBetrokkenheid.KIND, kind1);
        final Betrokkenheid mijnKind2 = new Betrokkenheid(SoortBetrokkenheid.KIND, kind2);
        final Betrokkenheid mijnVader = new Betrokkenheid(SoortBetrokkenheid.OUDER, ouders);
        final Betrokkenheid mijnMoeder = new Betrokkenheid(SoortBetrokkenheid.OUDER, ouders);
        final Betrokkenheid ikAlsOuder1 = new Betrokkenheid(SoortBetrokkenheid.OUDER, kind1);
        final Betrokkenheid ikAlsOuder2 = new Betrokkenheid(SoortBetrokkenheid.OUDER, kind2);
        persoon.addBetrokkenheid(ikAlsOuder1);
        persoon.addBetrokkenheid(ikAlsOuder2);
        final Betrokkenheid ikAlsKind = new Betrokkenheid(SoortBetrokkenheid.KIND, ouders);
        persoon.addBetrokkenheid(ikAlsKind);
        final Betrokkenheid ikAlsPartner1 = new Betrokkenheid(SoortBetrokkenheid.PARTNER, huwelijk1);
        final Betrokkenheid ikAlsPartner2 = new Betrokkenheid(SoortBetrokkenheid.PARTNER, huwelijk2);
        persoon.addBetrokkenheid(ikAlsPartner1);
        persoon.addBetrokkenheid(ikAlsPartner2);
        //koppel betrokkenheden aan relatie
        huwelijk1.addBetrokkenheid(mijnPartner1);
        huwelijk1.addBetrokkenheid(ikAlsPartner1);
        huwelijk2.addBetrokkenheid(mijnPartner2);
        huwelijk2.addBetrokkenheid(ikAlsPartner2);
        kind1.addBetrokkenheid(mijnKind1);
        kind2.addBetrokkenheid(mijnKind2);
        kind1.addBetrokkenheid(ikAlsOuder1);
        kind2.addBetrokkenheid(ikAlsOuder2);
        ouders.addBetrokkenheid(mijnVader);
        ouders.addBetrokkenheid(mijnMoeder);
        ouders.addBetrokkenheid(ikAlsKind);
        //voeg historie toe aan betrokkenheden
        voegActueleBetrokkenheidHistorieToe(mijnPartner1);
        voegActueleBetrokkenheidHistorieToe(mijnPartner2);
        voegActueleBetrokkenheidHistorieToe(mijnKind1);
        voegActueleBetrokkenheidHistorieToe(mijnKind2);
        voegActueleBetrokkenheidHistorieToe(mijnVader);
        voegActueleBetrokkenheidHistorieToe(mijnMoeder);
        voegActueleBetrokkenheidHistorieToe(ikAlsOuder1);
        voegActueleBetrokkenheidHistorieToe(ikAlsOuder2);
        voegActueleBetrokkenheidHistorieToe(ikAlsKind);
        voegActueleBetrokkenheidHistorieToe(ikAlsPartner1);
        voegActueleBetrokkenheidHistorieToe(ikAlsPartner2);
        //laat betrokkenheid ikAlsPartner2 vervallen
        ikAlsPartner2.getBetrokkenheidHistorieSet().iterator().next().setDatumTijdVerval(new Timestamp(System.currentTimeMillis()));
        //execute + verify
        assertEquals(5, persoon.getBetrokkenheidSet().size());
        assertEquals(2, persoon.getBetrokkenheidSet(SoortBetrokkenheid.OUDER).size());
        assertEquals(1, persoon.getBetrokkenheidSet(SoortBetrokkenheid.KIND).size());
        assertEquals(2, persoon.getBetrokkenheidSet(SoortBetrokkenheid.PARTNER).size());

        assertEquals(4, persoon.getActueleBetrokkenheidSet(null).size());
        assertEquals(2, persoon.getActueleBetrokkenheidSet(SoortBetrokkenheid.OUDER).size());
        assertEquals(1, persoon.getActueleBetrokkenheidSet(SoortBetrokkenheid.KIND).size());
        assertEquals(1, persoon.getActueleBetrokkenheidSet(SoortBetrokkenheid.PARTNER).size());

        assertEquals(2, persoon.getActueleOuders().size());
        assertEquals(2, persoon.getActueleKinderen().size());
        assertEquals(1, persoon.getActuelePartners().size());

        assertEquals(mijnKind1, new ArrayList<>(persoon.getActueleKinderen()).get(0));
        assertEquals(mijnKind2, new ArrayList<>(persoon.getActueleKinderen()).get(1));
        assertEquals(mijnVader, new ArrayList<>(persoon.getActueleOuders()).get(0));
        assertEquals(mijnMoeder, new ArrayList<>(persoon.getActueleOuders()).get(1));
    }

    private void voegActueleBetrokkenheidHistorieToe(final Betrokkenheid betrokkenheid) {
        final BetrokkenheidHistorie actueelVoorkomen = new BetrokkenheidHistorie(betrokkenheid);
        actueelVoorkomen.setDatumTijdRegistratie(new Timestamp(System.currentTimeMillis()));
        betrokkenheid.addBetrokkenheidHistorie(actueelVoorkomen);
    }

    private void maakHuwelijk(final Persoon persoon, final Persoon partner) {
        final Relatie huwelijk = new Relatie(SoortRelatie.HUWELIJK);
        final RelatieHistorie huwelijkHistorie = new RelatieHistorie(huwelijk);
        huwelijk.addRelatieHistorie(huwelijkHistorie);

        final Betrokkenheid ikBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, huwelijk);
        final Betrokkenheid partnerBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, huwelijk);
        huwelijk.addBetrokkenheid(ikBetrokkenheid);
        huwelijk.addBetrokkenheid(partnerBetrokkenheid);
        persoon.addBetrokkenheid(ikBetrokkenheid);
        partner.addBetrokkenheid(partnerBetrokkenheid);
    }

    private PersoonSamengesteldeNaamHistorie maakPersoonSamengesteldeNaamHistorie(final Persoon persoon, final boolean gebruikVoorvoegselEnScheidingsteken) {
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = new PersoonSamengesteldeNaamHistorie(persoon, "Puk", true, true);
        samengesteldeNaamHistorie.setAdellijkeTitel(AdellijkeTitel.B);
        samengesteldeNaamHistorie.setPredicaat(Predicaat.H);
        samengesteldeNaamHistorie.setVoornamen("Pietje");
        if (gebruikVoorvoegselEnScheidingsteken) {
            samengesteldeNaamHistorie.setScheidingsteken(' ');
            samengesteldeNaamHistorie.setVoorvoegsel("van");
        }
        return samengesteldeNaamHistorie;
    }

    private PersoonSamengesteldeNaamHistorie maakPartnerSamengesteldeNaamHistorie(final Persoon persoon, final boolean gebruikVoorvoegselEnScheidingsteken) {
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = new PersoonSamengesteldeNaamHistorie(persoon, "eau", true, true);
        samengesteldeNaamHistorie.setAdellijkeTitel(AdellijkeTitel.G);
        samengesteldeNaamHistorie.setPredicaat(Predicaat.J);
        samengesteldeNaamHistorie.setVoornamen("Francien");
        if (gebruikVoorvoegselEnScheidingsteken) {
            samengesteldeNaamHistorie.setScheidingsteken('\'');
            samengesteldeNaamHistorie.setVoorvoegsel("de l");
        }
        return samengesteldeNaamHistorie;
    }

    @Test
    public void TestMaterieleHistoriePersoonAdres() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonAdres adres = new PersoonAdres(persoon);
        final PersoonAdresHistorie
                historie =
                new PersoonAdresHistorie(adres, SoortAdres.WOONADRES, new LandOfGebied(LandOfGebied.CODE_NEDERLAND, "NL"),
                        new RedenWijzigingVerblijf('P', "P"));
        historie.setDatumTijdVerval(TIJDSTIP);
        historie.setActieVerval(brpActie);
        adres.getPersoonAdresHistorieSet().add(historie);

        final PersoonAdresHistorie
                historie2 =
                new PersoonAdresHistorie(adres, SoortAdres.BRIEFADRES, new LandOfGebied(LandOfGebied.CODE_NEDERLAND, "NL"),
                        new RedenWijzigingVerblijf('P', "P"));
        adres.getPersoonAdresHistorieSet().add(historie2);
        persoon.getPersoonAdresSet().add(adres);

        final Set<MaterieleHistorie> actueleMaterieleGroepen = persoon.getNietVervallenMaterieleGroepen();
        assertEquals(1, actueleMaterieleGroepen.size());
        assertNull(actueleMaterieleGroepen.iterator().next().getDatumTijdVerval());
    }

    @Test
    public void TestMaterieleHistoriePersoonGeslachtsNaam() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);

        final PersoonGeslachtsnaamcomponent geslacht1 = new PersoonGeslachtsnaamcomponent(persoon, 1);
        final PersoonGeslachtsnaamcomponentHistorie historie1 = new PersoonGeslachtsnaamcomponentHistorie(geslacht1, "stam1-1");
        historie1.setDatumTijdVerval(TIJDSTIP);
        historie1.setActieVerval(brpActie);
        geslacht1.getPersoonGeslachtsnaamcomponentHistorieSet().add(historie1);

        final PersoonGeslachtsnaamcomponentHistorie historie2 = new PersoonGeslachtsnaamcomponentHistorie(geslacht1, "stam1-2");
        geslacht1.getPersoonGeslachtsnaamcomponentHistorieSet().add(historie2);

        final PersoonGeslachtsnaamcomponent geslacht2 = new PersoonGeslachtsnaamcomponent(persoon, 2);
        final PersoonGeslachtsnaamcomponentHistorie historie3 = new PersoonGeslachtsnaamcomponentHistorie(geslacht2, "stam1-1");
        historie3.setDatumTijdVerval(TIJDSTIP);
        historie3.setActieVerval(brpActie);
        geslacht2.getPersoonGeslachtsnaamcomponentHistorieSet().add(historie3);

        final PersoonGeslachtsnaamcomponentHistorie historie4 = new PersoonGeslachtsnaamcomponentHistorie(geslacht2, "stam1-2");
        geslacht2.getPersoonGeslachtsnaamcomponentHistorieSet().add(historie4);

        persoon.getPersoonGeslachtsnaamcomponentSet().add(geslacht1);
        persoon.getPersoonGeslachtsnaamcomponentSet().add(geslacht2);
        final Set<MaterieleHistorie> actueleMaterieleGroepen = persoon.getNietVervallenMaterieleGroepen();
        assertEquals(2, actueleMaterieleGroepen.size());
        assertNull(actueleMaterieleGroepen.iterator().next().getDatumTijdVerval());
    }

    @Test
    public void TestMaterieleHistoriePersoonIndicatie() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        PersoonIndicatie indicatie = new PersoonIndicatie(persoon, SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING);
        PersoonIndicatieHistorie his = new PersoonIndicatieHistorie(indicatie, true);
        indicatie.getPersoonIndicatieHistorieSet().add(his);
        PersoonIndicatie indicatie2 = new PersoonIndicatie(persoon, SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER);
        PersoonIndicatieHistorie his2 = new PersoonIndicatieHistorie(indicatie2, true);
        indicatie2.getPersoonIndicatieHistorieSet().add(his2);
        persoon.getPersoonIndicatieSet().add(indicatie);
        persoon.getPersoonIndicatieSet().add(indicatie2);
        assertEquals(1, persoon.getNietVervallenMaterieleGroepen().size());
    }

    @Test
    public void TestMaterieleHistorieNationaliteit() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        PersoonNationaliteit nat = new PersoonNationaliteit(persoon, new Nationaliteit("NL", Nationaliteit.NEDERLANDSE));
        PersoonNationaliteitHistorie his = new PersoonNationaliteitHistorie(nat);
        nat.addPersoonNationaliteitHistorie(his);

        PersoonNationaliteit nat2 = new PersoonNationaliteit(persoon, new Nationaliteit("NL", "0002"));
        PersoonNationaliteitHistorie his2 = new PersoonNationaliteitHistorie(nat2);
        his2.setActieVerval(brpActie);
        his2.setDatumTijdVerval(TIJDSTIP);
        nat.addPersoonNationaliteitHistorie(his2);
        persoon.getPersoonNationaliteitSet().add(nat);
        persoon.getPersoonNationaliteitSet().add(nat2);
        assertEquals(2, persoon.getPersoonNationaliteitSet().size());
        assertEquals(1, persoon.getNietVervallenMaterieleGroepen().size());
    }

    @Test
    public void TestMaterieleHistorieBetrokkenheidOuderlijkGezag() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        Betrokkenheid ouder1 = new Betrokkenheid(SoortBetrokkenheid.OUDER, new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING));
        BetrokkenheidHistorie ouder1Historie = new BetrokkenheidHistorie(ouder1);
        BetrokkenheidOuderlijkGezagHistorie ouder1OuderlijkGezag = new BetrokkenheidOuderlijkGezagHistorie(ouder1, true);
        ouder1.addBetrokkenheidHistorie(ouder1Historie);
        ouder1.addBetrokkenheidOuderlijkGezagHistorie(ouder1OuderlijkGezag);

        Betrokkenheid ouder2 = new Betrokkenheid(SoortBetrokkenheid.OUDER, new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING));
        BetrokkenheidHistorie ouder2Historie = new BetrokkenheidHistorie(ouder1);
        BetrokkenheidOuderlijkGezagHistorie ouder2OuderlijkGezag = new BetrokkenheidOuderlijkGezagHistorie(ouder2, true);
        ouder2OuderlijkGezag.setActieVerval(brpActie);
        ouder2OuderlijkGezag.setDatumTijdVerval(TIJDSTIP);
        ouder2.addBetrokkenheidHistorie(ouder2Historie);
        ouder2.addBetrokkenheidOuderlijkGezagHistorie(ouder2OuderlijkGezag);
        persoon.addBetrokkenheid(ouder1);
        persoon.addBetrokkenheid(ouder2);
        assertEquals(2, persoon.getBetrokkenheidSet().size());
        assertEquals(1, persoon.getNietVervallenMaterieleGroepen().size());
    }

    @Test
    public void TestMaterieleHistorieBetrokkenheidOuder() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        Betrokkenheid ouder1 = new Betrokkenheid(SoortBetrokkenheid.OUDER, new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING));
        BetrokkenheidHistorie ouder1Historie = new BetrokkenheidHistorie(ouder1);
        BetrokkenheidOuderHistorie ouder1OuderlijkGezag = new BetrokkenheidOuderHistorie(ouder1);
        ouder1.addBetrokkenheidHistorie(ouder1Historie);
        ouder1.addBetrokkenheidOuderHistorie(ouder1OuderlijkGezag);

        Betrokkenheid ouder2 = new Betrokkenheid(SoortBetrokkenheid.OUDER, new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING));
        BetrokkenheidHistorie ouder2Historie = new BetrokkenheidHistorie(ouder1);
        BetrokkenheidOuderHistorie ouder2OuderlijkGezag = new BetrokkenheidOuderHistorie(ouder2);
        ouder2OuderlijkGezag.setActieVerval(brpActie);
        ouder2OuderlijkGezag.setDatumTijdVerval(TIJDSTIP);
        ouder2.addBetrokkenheidHistorie(ouder2Historie);
        ouder2.addBetrokkenheidOuderHistorie(ouder2OuderlijkGezag);
        persoon.addBetrokkenheid(ouder1);
        persoon.addBetrokkenheid(ouder2);
        assertEquals(2, persoon.getBetrokkenheidSet().size());
        assertEquals(1, persoon.getNietVervallenMaterieleGroepen().size());
    }

    @Test
    public void TestMaterieleHistorieVoornaam() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        PersoonVoornaam vn = new PersoonVoornaam(persoon, 1);
        PersoonVoornaamHistorie vnh = new PersoonVoornaamHistorie(vn, "voor1");
        vn.addPersoonVoornaamHistorie(vnh);

        PersoonVoornaam vn1 = new PersoonVoornaam(persoon, 1);
        PersoonVoornaamHistorie vnh1 = new PersoonVoornaamHistorie(vn1, "voor1");
        vnh1.setActieVerval(brpActie);
        vnh1.setDatumTijdVerval(TIJDSTIP);
        vn1.addPersoonVoornaamHistorie(vnh1);

        persoon.getPersoonVoornaamSet().add(vn);
        persoon.getPersoonVoornaamSet().add(vn1);
        assertEquals(2, persoon.getPersoonVoornaamSet().size());
        assertEquals(1, persoon.getNietVervallenMaterieleGroepen().size());
    }

    @Test
    public void TestMaterieleHistorieBijhouding() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        PersoonBijhoudingHistorie his = new PersoonBijhoudingHistorie(persoon, partij, Bijhoudingsaard.ONBEKEND, NadereBijhoudingsaard.ACTUEEL);
        PersoonBijhoudingHistorie his2 = new PersoonBijhoudingHistorie(persoon, partij, Bijhoudingsaard.ONBEKEND, NadereBijhoudingsaard.EMIGRATIE);
        his2.setActieVerval(brpActie);
        his2.setDatumTijdVerval(TIJDSTIP);
        persoon.getPersoonBijhoudingHistorieSet().add(his);
        persoon.getPersoonBijhoudingHistorieSet().add(his2);
        assertEquals(1, persoon.getNietVervallenMaterieleGroepen().size());
    }

    @Test
    public void TestMaterieleHistorieGeslachtsNaam() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        PersoonGeslachtsaanduidingHistorie his = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.MAN);
        PersoonGeslachtsaanduidingHistorie his2 = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.VROUW);
        his2.setActieVerval(brpActie);
        his2.setDatumTijdVerval(TIJDSTIP);
        persoon.getPersoonGeslachtsaanduidingHistorieSet().add(his);
        persoon.getPersoonGeslachtsaanduidingHistorieSet().add(his2);
        assertEquals(1, persoon.getNietVervallenMaterieleGroepen().size());
    }

    @Test
    public void TestMaterieleHistorieID() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        PersoonIDHistorie his = new PersoonIDHistorie(persoon);
        PersoonIDHistorie his2 = new PersoonIDHistorie(persoon);
        his2.setActieVerval(brpActie);
        his2.setDatumTijdVerval(TIJDSTIP);
        persoon.getPersoonIDHistorieSet().add(his);
        persoon.getPersoonIDHistorieSet().add(his2);
        assertEquals(1, persoon.getNietVervallenMaterieleGroepen().size());
    }

    @Test
    public void TestMaterieleHistorieMigratie() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        PersoonMigratieHistorie his = new PersoonMigratieHistorie(persoon, SoortMigratie.EMIGRATIE);
        PersoonMigratieHistorie his2 = new PersoonMigratieHistorie(persoon, SoortMigratie.EMIGRATIE);
        his2.setDatumEindeGeldigheid(20170101);
        PersoonMigratieHistorie his3 = new PersoonMigratieHistorie(persoon, SoortMigratie.IMMIGRATIE);
        his3.setActieVerval(brpActie);
        his3.setDatumTijdVerval(TIJDSTIP);
        persoon.getPersoonMigratieHistorieSet().add(his);
        persoon.getPersoonMigratieHistorieSet().add(his2);
        persoon.getPersoonMigratieHistorieSet().add(his3);
        assertEquals(2, persoon.getNietVervallenMaterieleGroepen().size());
    }

    @Test
    public void TestMaterieleHistorieNummerVerwijzing() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        PersoonNummerverwijzingHistorie his = new PersoonNummerverwijzingHistorie(persoon);
        PersoonNummerverwijzingHistorie his2 = new PersoonNummerverwijzingHistorie(persoon);
        his2.setActieVerval(brpActie);
        his2.setDatumTijdVerval(TIJDSTIP);
        persoon.getPersoonNummerverwijzingHistorieSet().add(his);
        persoon.getPersoonNummerverwijzingHistorieSet().add(his2);
        assertEquals(1, persoon.getNietVervallenMaterieleGroepen().size());
    }

    @Test
    public void TestMaterieleHistorieSamenGesteldeNaam() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        PersoonSamengesteldeNaamHistorie his = new PersoonSamengesteldeNaamHistorie(persoon, "stam", true, true);
        PersoonSamengesteldeNaamHistorie his2 = new PersoonSamengesteldeNaamHistorie(persoon, "stam1", true, true);
        his2.setActieVerval(brpActie);
        his2.setDatumTijdVerval(TIJDSTIP);
        persoon.getPersoonSamengesteldeNaamHistorieSet().add(his);
        persoon.getPersoonSamengesteldeNaamHistorieSet().add(his2);
        assertEquals(1, persoon.getNietVervallenMaterieleGroepen().size());
    }

    @Test
    public void TestMaterieleHistorie() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        PersoonSamengesteldeNaamHistorie his = new PersoonSamengesteldeNaamHistorie(persoon, "stam", true, true);
        persoon.getPersoonSamengesteldeNaamHistorieSet().add(his);
        PersoonNummerverwijzingHistorie his2 = new PersoonNummerverwijzingHistorie(persoon);
        persoon.getPersoonNummerverwijzingHistorieSet().add(his2);
        PersoonMigratieHistorie his3 = new PersoonMigratieHistorie(persoon, SoortMigratie.EMIGRATIE);
        persoon.getPersoonMigratieHistorieSet().add(his3);
        PersoonIDHistorie his4 = new PersoonIDHistorie(persoon);
        persoon.getPersoonIDHistorieSet().add(his4);
        PersoonGeslachtsaanduidingHistorie his5 = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.MAN);
        persoon.getPersoonGeslachtsaanduidingHistorieSet().add(his5);
        PersoonBijhoudingHistorie his6 = new PersoonBijhoudingHistorie(persoon, partij, Bijhoudingsaard.ONBEKEND, NadereBijhoudingsaard.ACTUEEL);
        persoon.getPersoonBijhoudingHistorieSet().add(his6);

        PersoonNationaliteit nat = new PersoonNationaliteit(persoon, new Nationaliteit("NL", Nationaliteit.NEDERLANDSE));
        PersoonNationaliteitHistorie his7 = new PersoonNationaliteitHistorie(nat);
        nat.addPersoonNationaliteitHistorie(his7);
        persoon.getPersoonNationaliteitSet().add(nat);

        PersoonIndicatie indicatie = new PersoonIndicatie(persoon, SoortIndicatie.ONDER_CURATELE);
        PersoonIndicatieHistorie his8 = new PersoonIndicatieHistorie(indicatie, true);
        indicatie.getPersoonIndicatieHistorieSet().add(his8);
        persoon.getPersoonIndicatieSet().add(indicatie);

        PersoonVoornaam vn = new PersoonVoornaam(persoon, 1);
        PersoonVoornaamHistorie his9 = new PersoonVoornaamHistorie(vn, "voor1");
        vn.addPersoonVoornaamHistorie(his9);
        persoon.getPersoonVoornaamSet().add(vn);

        final PersoonGeslachtsnaamcomponent geslacht1 = new PersoonGeslachtsnaamcomponent(persoon, 1);
        final PersoonGeslachtsnaamcomponentHistorie his10 = new PersoonGeslachtsnaamcomponentHistorie(geslacht1, "stam1-1");
        geslacht1.getPersoonGeslachtsnaamcomponentHistorieSet().add(his10);
        persoon.getPersoonGeslachtsnaamcomponentSet().add(geslacht1);

        final PersoonAdres adres = new PersoonAdres(persoon);
        final PersoonAdresHistorie
                his11 =
                new PersoonAdresHistorie(adres, SoortAdres.WOONADRES, new LandOfGebied(LandOfGebied.CODE_NEDERLAND, "NL"),
                        new RedenWijzigingVerblijf('P', "P"));
        adres.getPersoonAdresHistorieSet().add(his11);
        persoon.getPersoonAdresSet().add(adres);

        Betrokkenheid betrokkenheid1 = new Betrokkenheid(SoortBetrokkenheid.OUDER, new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING));
        BetrokkenheidHistorie betrokkenheid1Historie = new BetrokkenheidHistorie(betrokkenheid1);
        BetrokkenheidOuderHistorie his12 = new BetrokkenheidOuderHistorie(betrokkenheid1);
        betrokkenheid1.addBetrokkenheidHistorie(betrokkenheid1Historie);
        betrokkenheid1.addBetrokkenheidOuderHistorie(his12);
        persoon.getBetrokkenheidSet().add(betrokkenheid1);

        Betrokkenheid betrokkenheid2 = new Betrokkenheid(SoortBetrokkenheid.OUDER, new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING));
        BetrokkenheidHistorie betrokkenheid2Historie = new BetrokkenheidHistorie(betrokkenheid2);
        BetrokkenheidOuderlijkGezagHistorie his13 = new BetrokkenheidOuderlijkGezagHistorie(betrokkenheid2, true);
        betrokkenheid2.addBetrokkenheidHistorie(betrokkenheid2Historie);
        betrokkenheid2.addBetrokkenheidOuderlijkGezagHistorie(his13);
        persoon.getBetrokkenheidSet().add(betrokkenheid2);

        //Formeel veld
        final AutoriteitAfgifteBuitenlandsPersoonsnummer autoriteit = new AutoriteitAfgifteBuitenlandsPersoonsnummer("0002");
        final PersoonBuitenlandsPersoonsnummer pbp = new PersoonBuitenlandsPersoonsnummer(persoon, autoriteit, "123456789");
        persoon.addPersoonBuitenlandsPersoonsnummer(pbp);

        assertEquals(13, persoon.getNietVervallenMaterieleGroepen().size());
    }

    @Test
    public void testBepaalLeeftijd() {
        final int peildatum = 20170202;
        final int achtienJaar = peildatum - 180000;
        final int achtienJaarDeelsOnbekend = 19990000;

        final Persoon persoonVanAchtienJaarOud = maakPersoonMetGeboorteDatum(achtienJaar, achtienJaar - 20000);
        final Persoon persoonVanAchtienJaarOudDeelsOnbekend = maakPersoonMetGeboorteDatum(achtienJaarDeelsOnbekend, achtienJaarDeelsOnbekend - 20000);
        assertEquals(18, persoonVanAchtienJaarOud.bepaalLeeftijd(peildatum));
        assertEquals(18, persoonVanAchtienJaarOudDeelsOnbekend.bepaalLeeftijd(peildatum));
    }

    @Test(expected = IllegalStateException.class)
    public void testBepaalLeeftijdFout() {
        final int peildatum = 20170202;
        final int achtienJaar = peildatum - 180000;

        final Persoon persoonVanAchtienJaarOud = maakPersoonMetGeboorteDatum(achtienJaar, achtienJaar - 20000);
        persoonVanAchtienJaarOud.getPersoonGeboorteHistorieSet().clear();
        persoonVanAchtienJaarOud.bepaalLeeftijd(peildatum);
    }

    private static Persoon maakPersoonMetGeboorteDatum(final int geboortedatum, final int vervallenGeboortedatum) {
        final Timestamp tijdstip1 = new Timestamp(System.currentTimeMillis());
        final Timestamp tijdstip2 = new Timestamp(tijdstip1.getTime() + 1000);
        final AdministratieveHandeling
                administratieveHandeling =
                new AdministratieveHandeling(new Partij("test", "000000"), SoortAdministratieveHandeling.GBA_INITIELE_VULLING,
                        tijdstip1);
        final BRPActie moment1 = new BRPActie(SoortActie.CONVERSIE_GBA, administratieveHandeling, administratieveHandeling.getPartij(), tijdstip1);
        final BRPActie moment2 = new BRPActie(SoortActie.CONVERSIE_GBA, administratieveHandeling, administratieveHandeling.getPartij(), tijdstip2);

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonGeboorteHistorie
                vervallenGeboorteHistorie =
                new PersoonGeboorteHistorie(persoon, vervallenGeboortedatum, new LandOfGebied("0000", "test"));
        vervallenGeboorteHistorie.setActieInhoud(moment1);
        vervallenGeboorteHistorie.setDatumTijdRegistratie(moment1.getDatumTijdRegistratie());

        final PersoonGeboorteHistorie
                actueleGeboorteHistorie =
                new PersoonGeboorteHistorie(persoon, geboortedatum, vervallenGeboorteHistorie.getLandOfGebied());
        actueleGeboorteHistorie.setActieInhoud(moment2);
        actueleGeboorteHistorie.setDatumTijdRegistratie(moment2.getDatumTijdRegistratie());

        FormeleHistorie.voegToe(vervallenGeboorteHistorie, persoon.getPersoonGeboorteHistorieSet());
        FormeleHistorie.voegToe(actueleGeboorteHistorie, persoon.getPersoonGeboorteHistorieSet());
        return persoon;
    }
}
