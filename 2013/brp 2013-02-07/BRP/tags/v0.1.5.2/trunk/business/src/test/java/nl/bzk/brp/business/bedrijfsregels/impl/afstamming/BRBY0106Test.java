/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.operationeel.kern.PersistentBetrokkenheid;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.operationeel.kern.PersistentRelatie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class BRBY0106Test {

    @Mock
    private RelatieRepository relatieRepository;

    @Mock
    private PersoonRepository persoonRepository;

    private BRBY0106          brby0106;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        brby0106 = new BRBY0106();
        ReflectionTestUtils.setField(brby0106, "persoonRepository", persoonRepository);
        ReflectionTestUtils.setField(brby0106, "relatieRepository", relatieRepository);
    }

    @Test
    public void testGeenOudersInBericht() {
        Relatie relatie = maakRelatie();

        Melding melding = brby0106.executeer(null, relatie, null);

        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals(MeldingCode.ALG0002, melding.getCode());
        Assert.assertEquals("Er is geen ouder meegegeven in het bericht.", melding.getOmschrijving());
    }

    @Test
    public void testOuder1ZonderIdentificatieNummers() {
        Relatie relatie = maakRelatie();
        Betrokkenheid ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, "123456789");
        ouder1.getBetrokkene().setIdentificatienummers(null);
        relatie.getBetrokkenheden().add(ouder1);

        Melding melding = brby0106.executeer(null, relatie, null);

        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals(MeldingCode.ALG0002, melding.getCode());
        Assert.assertEquals("Er is een ouder meegegeven zonder identificatienummers in het bericht.",
                melding.getOmschrijving());
    }

    @Test
    public void testOuder2ZonderIdentificatieNummers() {
        Relatie relatie = maakRelatie();
        Betrokkenheid ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, "123456789");
        Betrokkenheid ouder2 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, "11111111");
        ouder2.getBetrokkene().setIdentificatienummers(null);
        relatie.getBetrokkenheden().add(ouder1);
        relatie.getBetrokkenheden().add(ouder2);

        Melding melding = brby0106.executeer(null, relatie, null);

        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals(MeldingCode.ALG0002, melding.getCode());
        Assert.assertEquals("Er is een ouder meegegeven zonder identificatienummers in het bericht.",
                melding.getOmschrijving());
    }

    @Test
    public void testKindZonderGeslachtsNaamComponenten() {
        Relatie relatie = maakRelatie();
        Betrokkenheid ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, "123456789");
        Betrokkenheid ouder2 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, "11111111");
        relatie.getBetrokkenheden().add(ouder1);
        relatie.getBetrokkenheden().add(ouder2);
        Betrokkenheid kind = maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, "66666666");
        relatie.getBetrokkenheden().add(kind);

        Melding melding = brby0106.executeer(null, relatie, null);

        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals(MeldingCode.ALG0002, melding.getCode());
        Assert.assertEquals("Er zijn geen geslachtsnaamcomponenten voor het kind meegegeven in het bericht.",
                melding.getOmschrijving());
    }

    @Test
    public void testKindMet1OuderEnDezelfdeGeslachtsnamen() {
        Relatie relatie = maakRelatie();
        Betrokkenheid ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, "123456789");
        relatie.getBetrokkenheden().add(ouder1);
        Betrokkenheid kind = maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, "66666666");
        relatie.getBetrokkenheden().add(kind);
        PersoonGeslachtsnaamcomponent comp = new PersoonGeslachtsnaamcomponent();
        comp.setScheidingsTeken("-");
        comp.setNaam("Piet");
        comp.setVoorvoegsel("der");
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(comp);

        PersistentPersoon pOuder = new PersistentPersoon();
        PersistentPersoonGeslachtsnaamcomponent compOuder = new PersistentPersoonGeslachtsnaamcomponent();
        compOuder.setNaam("Piet");
        compOuder.setVoorvoegsel("der");
        compOuder.setScheidingsteken("-");
        pOuder.getPersoonGeslachtsnaamcomponenten().add(compOuder);

        Mockito.when(persoonRepository.findByBurgerservicenummer("123456789")).thenReturn(pOuder);

        Melding melding = brby0106.executeer(null, relatie, null);
        Assert.assertNull(melding);
    }

    @Test
    public void testKindMet1OuderEnVerschillendeGeslachtsnamen() {
        Relatie relatie = maakRelatie();
        Betrokkenheid ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, "123456789");
        relatie.getBetrokkenheden().add(ouder1);
        Betrokkenheid kind = maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, "66666666");
        relatie.getBetrokkenheden().add(kind);
        PersoonGeslachtsnaamcomponent comp = new PersoonGeslachtsnaamcomponent();
        comp.setScheidingsTeken("-");
        comp.setNaam("Jan");
        comp.setVoorvoegsel("der");
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(comp);

        PersistentPersoon pOuder = new PersistentPersoon();
        PersistentPersoonGeslachtsnaamcomponent compOuder = new PersistentPersoonGeslachtsnaamcomponent();
        compOuder.setNaam("Piet");
        compOuder.setVoorvoegsel("der");
        compOuder.setScheidingsteken("-");
        pOuder.getPersoonGeslachtsnaamcomponenten().add(compOuder);

        Mockito.when(persoonRepository.findByBurgerservicenummer("123456789")).thenReturn(pOuder);

        Melding melding = brby0106.executeer(null, relatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals(MeldingCode.BRBY0106, melding.getCode());
        Assert.assertEquals("De opgegeven geslachtsnaam is niet toegestaan. De geslachtsnaam moet gelijk zijn aan die "
                + "van een van de ouders en aan die van eerder geboren kinderen van deze ouders.",
                melding.getOmschrijving());

        // Reset
        comp.setNaam("Piet");
        melding = brby0106.executeer(null, relatie, null);
        Assert.assertNull(melding);
        // Veschillende voorvoegsels
        comp.setVoorvoegsel("van");
        melding = brby0106.executeer(null, relatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals(MeldingCode.BRBY0106, melding.getCode());
        Assert.assertEquals("De opgegeven geslachtsnaam is niet toegestaan. De geslachtsnaam moet gelijk zijn aan die "
                + "van een van de ouders en aan die van eerder geboren kinderen van deze ouders.",
                melding.getOmschrijving());

        // Reset
        comp.setVoorvoegsel("der");
        melding = brby0106.executeer(null, relatie, null);
        Assert.assertNull(melding);
        // Verschillende scheidingstekens
        comp.setScheidingsTeken("/");
        melding = brby0106.executeer(null, relatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals(MeldingCode.BRBY0106, melding.getCode());
        Assert.assertEquals("De opgegeven geslachtsnaam is niet toegestaan. De geslachtsnaam moet gelijk zijn aan die "
                + "van een van de ouders en aan die van eerder geboren kinderen van deze ouders.",
                melding.getOmschrijving());

        // Reset
        comp.setScheidingsTeken("-");
        melding = brby0106.executeer(null, relatie, null);
        Assert.assertNull(melding);
        // Verschillende volgnummers (Geen effect)
        comp.setVolgnummer(6);
        melding = brby0106.executeer(null, relatie, null);
        Assert.assertNull(melding);
    }

    @Test
    public void testKindMet1OuderEnDezelfdeGeslachtsnamenMaarKindGeslNaamIsLegeString() {
        Relatie relatie = maakRelatie();
        Betrokkenheid ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, "123456789");
        relatie.getBetrokkenheden().add(ouder1);
        Betrokkenheid kind = maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, "66666666");
        relatie.getBetrokkenheden().add(kind);
        PersoonGeslachtsnaamcomponent comp = new PersoonGeslachtsnaamcomponent();
        comp.setScheidingsTeken("-");
        comp.setNaam("Piet");
        comp.setVoorvoegsel("");
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(comp);

        PersistentPersoon pOuder = new PersistentPersoon();
        PersistentPersoonGeslachtsnaamcomponent compOuder = new PersistentPersoonGeslachtsnaamcomponent();
        compOuder.setNaam("Piet");
        compOuder.setVoorvoegsel(null);
        compOuder.setScheidingsteken("-");
        pOuder.getPersoonGeslachtsnaamcomponenten().add(compOuder);

        Mockito.when(persoonRepository.findByBurgerservicenummer("123456789")).thenReturn(pOuder);

        Melding melding = brby0106.executeer(null, relatie, null);
        Assert.assertNull(melding);
    }

    @Test
    public void test2OudersGeslachtsNaamKindGelijkAanOuder2EnGeenEerdereKinderen() {
        Relatie relatie = maakRelatie();
        Betrokkenheid ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, "111111111");
        relatie.getBetrokkenheden().add(ouder1);
        Betrokkenheid ouder2 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, "22222222");
        relatie.getBetrokkenheden().add(ouder2);
        Betrokkenheid kind = maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, "66666666");
        relatie.getBetrokkenheden().add(kind);

        PersoonGeslachtsnaamcomponent compKind = new PersoonGeslachtsnaamcomponent();
        compKind.setScheidingsTeken("-");
        compKind.setNaam("Piet");
        compKind.setVoorvoegsel("der");
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(compKind);

        PersistentPersoon pOuder1 = new PersistentPersoon();
        PersistentPersoonGeslachtsnaamcomponent compOuder1 = new PersistentPersoonGeslachtsnaamcomponent();
        compOuder1.setNaam("Janneke");
        compOuder1.setVoorvoegsel("van");
        compOuder1.setScheidingsteken("/");
        pOuder1.getPersoonGeslachtsnaamcomponenten().add(compOuder1);

        PersistentPersoon pOuder2 = new PersistentPersoon();
        PersistentPersoonGeslachtsnaamcomponent compOuder2 = new PersistentPersoonGeslachtsnaamcomponent();
        compOuder2.setNaam("Piet");
        compOuder2.setVoorvoegsel("der");
        compOuder2.setScheidingsteken("-");
        pOuder2.getPersoonGeslachtsnaamcomponenten().add(compOuder2);

        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder1.getBetrokkene().getIdentificatienummers()
                        .getBurgerservicenummer())).thenReturn(pOuder1);
        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder2.getBetrokkene().getIdentificatienummers()
                        .getBurgerservicenummer())).thenReturn(pOuder2);

        Melding melding = brby0106.executeer(null, relatie, null);
        Assert.assertNull(melding);
    }

    @Test
    public void test2OudersGeslachtsNaamKindGelijkAanGeenVanOuders() {
        Relatie relatie = maakRelatie();
        Betrokkenheid ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, "111111111");
        relatie.getBetrokkenheden().add(ouder1);
        Betrokkenheid ouder2 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, "22222222");
        relatie.getBetrokkenheden().add(ouder2);
        Betrokkenheid kind = maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, "66666666");
        relatie.getBetrokkenheden().add(kind);

        PersoonGeslachtsnaamcomponent compKind = new PersoonGeslachtsnaamcomponent();
        compKind.setScheidingsTeken("-");
        compKind.setNaam("Jan");
        compKind.setVoorvoegsel("van der");
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(compKind);

        PersistentPersoon pOuder1 = new PersistentPersoon();
        PersistentPersoonGeslachtsnaamcomponent compOuder1 = new PersistentPersoonGeslachtsnaamcomponent();
        compOuder1.setNaam("Janneke");
        compOuder1.setVoorvoegsel("van");
        compOuder1.setScheidingsteken("/");
        pOuder1.getPersoonGeslachtsnaamcomponenten().add(compOuder1);

        PersistentPersoon pOuder2 = new PersistentPersoon();
        PersistentPersoonGeslachtsnaamcomponent compOuder2 = new PersistentPersoonGeslachtsnaamcomponent();
        compOuder2.setNaam("Piet");
        compOuder2.setVoorvoegsel("der");
        compOuder2.setScheidingsteken("-");
        pOuder2.getPersoonGeslachtsnaamcomponenten().add(compOuder2);

        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder1.getBetrokkene().getIdentificatienummers()
                        .getBurgerservicenummer())).thenReturn(pOuder1);
        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder2.getBetrokkene().getIdentificatienummers()
                        .getBurgerservicenummer())).thenReturn(pOuder2);

        Melding melding = brby0106.executeer(null, relatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals(MeldingCode.BRBY0106, melding.getCode());
        Assert.assertEquals("De opgegeven geslachtsnaam is niet toegestaan. De geslachtsnaam moet gelijk zijn aan die "
                + "van een van de ouders en aan die van eerder geboren kinderen van deze ouders.",
                melding.getOmschrijving());
    }

    @Test
    public void test2NietGehuwdeOudersGeslachtsNaamKindGelijkAanOuder2MaarNietAanEerderKindUitHuwelijk() {
        Relatie relatie = maakRelatie();
        Betrokkenheid ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, "111111111");
        relatie.getBetrokkenheden().add(ouder1);
        Betrokkenheid ouder2 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, "22222222");
        relatie.getBetrokkenheden().add(ouder2);
        Betrokkenheid kind = maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, "66666666");
        relatie.getBetrokkenheden().add(kind);

        PersoonGeslachtsnaamcomponent compKind = new PersoonGeslachtsnaamcomponent();
        compKind.setScheidingsTeken("-");
        compKind.setNaam("Jan");
        compKind.setVoorvoegsel("van der");
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(compKind);

        // Ouder 1
        PersistentPersoon pOuder1 = new PersistentPersoon();
        PersistentPersoonGeslachtsnaamcomponent compOuder1 = new PersistentPersoonGeslachtsnaamcomponent();
        compOuder1.setNaam("Janneke");
        compOuder1.setVoorvoegsel("van");
        compOuder1.setScheidingsteken("/");
        pOuder1.getPersoonGeslachtsnaamcomponenten().add(compOuder1);

        // Ouder 2
        PersistentPersoon pOuder2 = new PersistentPersoon();
        PersistentPersoonGeslachtsnaamcomponent compOuder2 = new PersistentPersoonGeslachtsnaamcomponent();
        compOuder2.setNaam("Jan");
        compOuder2.setVoorvoegsel("van der");
        compOuder2.setScheidingsteken("-");
        pOuder2.getPersoonGeslachtsnaamcomponenten().add(compOuder2);

        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder1.getBetrokkene().getIdentificatienummers()
                        .getBurgerservicenummer())).thenReturn(pOuder1);
        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder2.getBetrokkene().getIdentificatienummers()
                        .getBurgerservicenummer())).thenReturn(pOuder2);

        // Familie rechtelijke betrekking
        PersistentRelatie familieRechtelijkeBetr = new PersistentRelatie();
        familieRechtelijkeBetr.setSoortRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        familieRechtelijkeBetr.setBetrokkenheden(new HashSet<PersistentBetrokkenheid>());
        PersistentBetrokkenheid pBetrEerderVerkregenKind = new PersistentBetrokkenheid();
        pBetrEerderVerkregenKind.setSoortBetrokkenheid(SoortBetrokkenheid.KIND);
        PersistentPersoon eerderVerkregenKind = new PersistentPersoon();
        eerderVerkregenKind.setPersoonGeslachtsnaamcomponenten(new HashSet<PersistentPersoonGeslachtsnaamcomponent>());
        PersistentPersoonGeslachtsnaamcomponent compEerderVerkregenKind = new PersistentPersoonGeslachtsnaamcomponent();
        compEerderVerkregenKind.setNaam("Liesje");
        compEerderVerkregenKind.setVoorvoegsel("van");
        pBetrEerderVerkregenKind.setBetrokkene(eerderVerkregenKind);
        familieRechtelijkeBetr.getBetrokkenheden().add(pBetrEerderVerkregenKind);
        List<PersistentRelatie> gevondenFamilieRechtelijkeBetrekkingen = new ArrayList<PersistentRelatie>();
        gevondenFamilieRechtelijkeBetrekkingen.add(familieRechtelijkeBetr);

        Mockito.when(
                relatieRepository.vindSoortRelatiesMetPersonenInRol(
                        Matchers.any(PersistentPersoon.class),
                        Matchers.any(PersistentPersoon.class),
                        Matchers.any(SoortBetrokkenheid.class),
                        Matchers.anyInt(),
                        Matchers.any(SoortRelatie.class))).thenReturn(
                gevondenFamilieRechtelijkeBetrekkingen);

        Melding melding = brby0106.executeer(null, relatie, null);
        Assert.assertNull(melding);
    }

    @Test
    public void test2GehuwdeOudersGeslachtsNaamKindGelijkAanOuder2MaarNietAanEerderKindUitHuwelijk() {
        Relatie relatie = maakRelatie();
        Betrokkenheid ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, "111111111");
        relatie.getBetrokkenheden().add(ouder1);
        Betrokkenheid ouder2 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, "22222222");
        relatie.getBetrokkenheden().add(ouder2);
        Betrokkenheid kind = maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, "66666666");
        relatie.getBetrokkenheden().add(kind);

        PersoonGeslachtsnaamcomponent compKind = new PersoonGeslachtsnaamcomponent();
        compKind.setScheidingsTeken("-");
        compKind.setNaam("Jan");
        compKind.setVoorvoegsel("van der");
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(compKind);

        // Ouder 1
        PersistentPersoon pOuder1 = new PersistentPersoon();
        PersistentPersoonGeslachtsnaamcomponent compOuder1 = new PersistentPersoonGeslachtsnaamcomponent();
        compOuder1.setNaam("Janneke");
        compOuder1.setVoorvoegsel("van");
        compOuder1.setScheidingsteken("/");
        pOuder1.getPersoonGeslachtsnaamcomponenten().add(compOuder1);

        // Ouder 2
        PersistentPersoon pOuder2 = new PersistentPersoon();
        PersistentPersoonGeslachtsnaamcomponent compOuder2 = new PersistentPersoonGeslachtsnaamcomponent();
        compOuder2.setNaam("Jan");
        compOuder2.setVoorvoegsel("van der");
        compOuder2.setScheidingsteken("-");
        pOuder2.getPersoonGeslachtsnaamcomponenten().add(compOuder2);

        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder1.getBetrokkene().getIdentificatienummers()
                        .getBurgerservicenummer())).thenReturn(pOuder1);
        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder2.getBetrokkene().getIdentificatienummers()
                        .getBurgerservicenummer())).thenReturn(pOuder2);

        // Huwelijk
        PersistentRelatie huwelijk = new PersistentRelatie();
        huwelijk.setBetrokkenheden(new HashSet<PersistentBetrokkenheid>());
        PersistentBetrokkenheid pBetrOuder1 = new PersistentBetrokkenheid();
        pBetrOuder1.setBetrokkene(pOuder1);
        huwelijk.getBetrokkenheden().add(pBetrOuder1);
        PersistentBetrokkenheid pBetrOuder2 = new PersistentBetrokkenheid();
        pBetrOuder2.setBetrokkene(pOuder2);
        huwelijk.getBetrokkenheden().add(pBetrOuder2);
        List<PersistentRelatie> gevondenHuwelijken = new ArrayList<PersistentRelatie>();
        gevondenHuwelijken.add(huwelijk);
        Mockito.when(
                relatieRepository.vindSoortRelatiesMetPersonenInRol(
                        Matchers.any(PersistentPersoon.class),
                        Matchers.any(PersistentPersoon.class),
                        Matchers.any(SoortBetrokkenheid.class),
                        Matchers.anyInt(),
                        Matchers.any(SoortRelatie.class),
                        Matchers.any(SoortRelatie.class))).thenReturn(gevondenHuwelijken);

        // Familie rechtelijke betrekking
        PersistentRelatie familieRechtelijkeBetr = new PersistentRelatie();
        familieRechtelijkeBetr.setSoortRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        familieRechtelijkeBetr.setBetrokkenheden(new HashSet<PersistentBetrokkenheid>());
        PersistentBetrokkenheid pBetrEerderVerkregenKind = new PersistentBetrokkenheid();
        pBetrEerderVerkregenKind.setSoortBetrokkenheid(SoortBetrokkenheid.KIND);
        PersistentPersoon eerderVerkregenKind = new PersistentPersoon();
        eerderVerkregenKind.setPersoonGeslachtsnaamcomponenten(new HashSet<PersistentPersoonGeslachtsnaamcomponent>());
        PersistentPersoonGeslachtsnaamcomponent compEerderVerkregenKind = new PersistentPersoonGeslachtsnaamcomponent();
        compEerderVerkregenKind.setNaam("Liesje");
        compEerderVerkregenKind.setVoorvoegsel("van");
        pBetrEerderVerkregenKind.setBetrokkene(eerderVerkregenKind);
        familieRechtelijkeBetr.getBetrokkenheden().add(pBetrEerderVerkregenKind);
        List<PersistentRelatie> gevondenFamilieRechtelijkeBetrekkingen = new ArrayList<PersistentRelatie>();
        gevondenFamilieRechtelijkeBetrekkingen.add(familieRechtelijkeBetr);

        Mockito.when(
                relatieRepository.vindSoortRelatiesMetPersonenInRol(
                        Matchers.any(PersistentPersoon.class),
                        Matchers.any(PersistentPersoon.class),
                        Matchers.any(SoortBetrokkenheid.class),
                        Matchers.anyInt(),
                        Matchers.any(SoortRelatie.class))).thenReturn(
                gevondenFamilieRechtelijkeBetrekkingen);

        Melding melding = brby0106.executeer(null, relatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals(MeldingCode.BRBY0106, melding.getCode());
        Assert.assertEquals("De opgegeven geslachtsnaam is niet toegestaan. De geslachtsnaam moet gelijk zijn aan die "
                + "van een van de ouders en aan die van eerder geboren kinderen van deze ouders.",
                melding.getOmschrijving());
    }

    @Test
    public void testOuder1NietGevondenMetBsn() {
        Relatie relatie = maakRelatie();
        Betrokkenheid ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, "123456789");
        relatie.getBetrokkenheden().add(ouder1);
        Betrokkenheid kind = maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, "66666666");
        relatie.getBetrokkenheden().add(kind);
        PersoonGeslachtsnaamcomponent comp = new PersoonGeslachtsnaamcomponent();
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(comp);

        Mockito.when(persoonRepository.findByBurgerservicenummer("123456789")).thenReturn(null);

        Melding melding = brby0106.executeer(null, relatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(MeldingCode.REF0001, melding.getCode());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, melding.getSoort());
    }

    @Test
    public void testOuder2NietGevondenMetBsn() {
        Relatie relatie = maakRelatie();
        relatie.getBetrokkenheden().add(maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, "123456789"));
        relatie.getBetrokkenheden().add(maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, "123456700"));

        Betrokkenheid kind = maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, "66666666");
        relatie.getBetrokkenheden().add(kind);
        PersoonGeslachtsnaamcomponent comp = new PersoonGeslachtsnaamcomponent();
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(comp);

        Mockito.when(persoonRepository.findByBurgerservicenummer("123456789")).thenReturn(new PersistentPersoon());
        Mockito.when(persoonRepository.findByBurgerservicenummer("123456700")).thenReturn(null);

        Melding melding = brby0106.executeer(null, relatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(MeldingCode.REF0001, melding.getCode());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, melding.getSoort());
    }

    private Relatie maakRelatie() {
        Relatie relatie = new Relatie();
        relatie.setBetrokkenheden(new HashSet<Betrokkenheid>());
        relatie.setSoortRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        return relatie;
    }

    private Betrokkenheid maakBetrokkenheid(final SoortBetrokkenheid soort, final Relatie relatie, final String bsn) {
        Betrokkenheid betr = new Betrokkenheid();
        betr.setSoortBetrokkenheid(soort);
        betr.setRelatie(relatie);
        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer(bsn);
        persoon.setGeboorte(new PersoonGeboorte());
        persoon.getGeboorte().setDatumGeboorte(20120101);
        betr.setBetrokkene(persoon);
        return betr;
    }
}
