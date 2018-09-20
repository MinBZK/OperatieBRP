/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.brp.model.attribuuttype.Scheidingsteken;
import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.groep.bericht.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
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
        RelatieBericht relatie = maakRelatie();

        List<Melding> meldingen = brby0106.executeer(null, relatie, null);

        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, meldingen.get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0002, meldingen.get(0).getCode());
        Assert.assertEquals("Er is geen ouder meegegeven in het bericht.", meldingen.get(0).getOmschrijving());
    }

    @Test
    public void testOuder1ZonderIdentificatienummers() {
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 =
            maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("123456789"));
        ouder1.getBetrokkene().setIdentificatienummers(null);
        relatie.getBetrokkenheden().add(ouder1);

        List<Melding> meldingen = brby0106.executeer(null, relatie, null);

        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, meldingen.get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0002, meldingen.get(0).getCode());
        Assert.assertEquals("Er is een ouder meegegeven zonder identificatienummers in het bericht.", meldingen.get(0)
                .getOmschrijving());
    }

    @Test
    public void testOuder2ZonderIdentificatienummers() {
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 =
            maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("123456789"));
        BetrokkenheidBericht ouder2 =
            maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("11111111"));
        ouder2.getBetrokkene().setIdentificatienummers(null);
        relatie.getBetrokkenheden().add(ouder1);
        relatie.getBetrokkenheden().add(ouder2);

        List<Melding> melding = brby0106.executeer(null, relatie, null);

        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0002, melding.get(0).getCode());
        Assert.assertEquals("Er is een ouder meegegeven zonder identificatienummers in het bericht.", melding.get(0)
                .getOmschrijving());
    }

    @Test
    public void testKindZonderGeslachtsnaamcomponenten() {
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 =
            maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("123456789"));
        BetrokkenheidBericht ouder2 =
            maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("11111111"));
        relatie.getBetrokkenheden().add(ouder1);
        relatie.getBetrokkenheden().add(ouder2);
        BetrokkenheidBericht kind =
            maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("66666666"));
        relatie.getBetrokkenheden().add(kind);

        List<Melding> melding = brby0106.executeer(null, relatie, null);

        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, melding.get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0002, melding.get(0).getCode());
        Assert.assertEquals("Er zijn geen geslachtsnaamcomponenten voor het kind meegegeven in het bericht.", melding
                .get(0).getOmschrijving());
    }

    @Test
    public void testKindMet1OuderEnDezelfdeGeslachtsnamen() {
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 =
            maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("123456789"));
        relatie.getBetrokkenheden().add(ouder1);
        BetrokkenheidBericht kind =
            maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("66666666"));
        relatie.getBetrokkenheden().add(kind);

        PersoonGeslachtsnaamcomponentBericht comp = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroep =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroep.setScheidingsteken(new Scheidingsteken("-"));
        standaardGroep.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Piet"));
        standaardGroep.setVoorvoegsel(new Voorvoegsel("der"));
        comp.setGegevens(standaardGroep);
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(comp);

        PersoonBericht pOuder = new PersoonBericht();
        PersoonModel pOuderModel = new PersoonModel(pOuder);
        PersoonGeslachtsnaamcomponentBericht compOuder = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroepOuder =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroepOuder.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Piet"));
        standaardGroepOuder.setVoorvoegsel(new Voorvoegsel("der"));
        standaardGroepOuder.setScheidingsteken(new Scheidingsteken("-"));
        compOuder.setGegevens(standaardGroepOuder);
        pOuderModel.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamcomponentModel(compOuder, pOuderModel));

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456789"))).thenReturn(
                pOuderModel);

        List<Melding> melding = brby0106.executeer(null, relatie, null);
        Assert.assertTrue(melding.size() == 0);
    }

    @Test
    public void testKindMet1OuderEnVerschillendeGeslachtsnamen() {
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 =
            maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("123456789"));
        relatie.getBetrokkenheden().add(ouder1);
        BetrokkenheidBericht kind =
            maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("66666666"));
        relatie.getBetrokkenheden().add(kind);

        PersoonGeslachtsnaamcomponentBericht comp = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroep =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroep.setScheidingsteken(new Scheidingsteken("-"));
        standaardGroep.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Jan"));
        standaardGroep.setVoorvoegsel(new Voorvoegsel("der"));
        comp.setGegevens(standaardGroep);
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(comp);

        PersoonBericht pOuder = new PersoonBericht();
        PersoonModel pOuderModel = new PersoonModel(pOuder);
        PersoonGeslachtsnaamcomponentBericht compOuder = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroepOuder =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroepOuder.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Piet"));
        standaardGroepOuder.setVoorvoegsel(new Voorvoegsel("der"));
        standaardGroepOuder.setScheidingsteken(new Scheidingsteken("-"));
        compOuder.setGegevens(standaardGroepOuder);
        pOuderModel.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamcomponentModel(compOuder, pOuderModel));

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456789"))).thenReturn(
                pOuderModel);

        List<Melding> melding = brby0106.executeer(null, relatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.get(0).getSoort());
        Assert.assertEquals(MeldingCode.BRBY0106, melding.get(0).getCode());
        Assert.assertEquals("De opgegeven geslachtsnaam is niet toegestaan. De geslachtsnaam moet gelijk zijn aan die "
            + "van een van de ouders en aan die van eerder geboren kinderen van deze ouders.", melding.get(0)
                .getOmschrijving());

        // Reset
        standaardGroep.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Piet"));
        melding = brby0106.executeer(null, relatie, null);
        Assert.assertTrue(melding.size() == 0);
        // Veschillende voorvoegsels
        standaardGroepOuder.setVoorvoegsel(new Voorvoegsel("van"));
        pOuderModel.getGeslachtsnaamcomponenten().clear();
        pOuderModel.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamcomponentModel(compOuder, pOuderModel));
        melding = brby0106.executeer(null, relatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.get(0).getSoort());
        Assert.assertEquals(MeldingCode.BRBY0106, melding.get(0).getCode());
        Assert.assertEquals("De opgegeven geslachtsnaam is niet toegestaan. De geslachtsnaam moet gelijk zijn aan die "
            + "van een van de ouders en aan die van eerder geboren kinderen van deze ouders.", melding.get(0)
                .getOmschrijving());

        // Reset
        standaardGroepOuder.setVoorvoegsel(new Voorvoegsel("der"));
        pOuderModel.getGeslachtsnaamcomponenten().clear();
        pOuderModel.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamcomponentModel(compOuder, pOuderModel));
        melding = brby0106.executeer(null, relatie, null);
        Assert.assertTrue(melding.size() == 0);
        // Verschillende scheidingstekens
        standaardGroep.setScheidingsteken(new Scheidingsteken("/"));
        melding = brby0106.executeer(null, relatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.get(0).getSoort());
        Assert.assertEquals(MeldingCode.BRBY0106, melding.get(0).getCode());
        Assert.assertEquals("De opgegeven geslachtsnaam is niet toegestaan. De geslachtsnaam moet gelijk zijn aan die "
            + "van een van de ouders en aan die van eerder geboren kinderen van deze ouders.", melding.get(0)
                .getOmschrijving());

        // Reset
        standaardGroep.setScheidingsteken(new Scheidingsteken("-"));
        melding = brby0106.executeer(null, relatie, null);
        Assert.assertTrue(melding.size() == 0);
        // Verschillende volgnummers (Geen effect)
        comp.setVolgnummer(new Volgnummer(6));
        melding = brby0106.executeer(null, relatie, null);
        Assert.assertTrue(melding.size() == 0);
    }

    @Test
    public void testKindMet1OuderEnDezelfdeGeslachtsnamenMaarKindGeslNaamIsLegeString() {
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 =
            maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("123456789"));
        relatie.getBetrokkenheden().add(ouder1);
        BetrokkenheidBericht kind =
            maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("66666666"));
        relatie.getBetrokkenheden().add(kind);

        PersoonGeslachtsnaamcomponentBericht comp = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroep =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroep.setScheidingsteken(new Scheidingsteken("-"));
        standaardGroep.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Piet"));
        standaardGroep.setVoorvoegsel(new Voorvoegsel(""));
        comp.setGegevens(standaardGroep);
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(comp);

        PersoonBericht pOuder = new PersoonBericht();
        PersoonModel pOuderModel = new PersoonModel(pOuder);
        PersoonGeslachtsnaamcomponentBericht compOuder = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroepOuder =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroepOuder.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Piet"));
        standaardGroepOuder.setVoorvoegsel(null);
        standaardGroepOuder.setScheidingsteken(new Scheidingsteken("-"));
        compOuder.setGegevens(standaardGroepOuder);
        pOuderModel.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamcomponentModel(compOuder, pOuderModel));

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456789"))).thenReturn(
                pOuderModel);

        List<Melding> melding = brby0106.executeer(null, relatie, null);
        Assert.assertTrue(melding.size() == 0);
    }

    @Test
    public void test2OudersGeslachtsNaamKindGelijkAanOuder2EnGeenEerdereKinderen() {
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 =
            maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("111111111"));
        relatie.getBetrokkenheden().add(ouder1);
        BetrokkenheidBericht ouder2 =
            maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("22222222"));
        relatie.getBetrokkenheden().add(ouder2);
        BetrokkenheidBericht kind =
            maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("66666666"));
        relatie.getBetrokkenheden().add(kind);

        PersoonGeslachtsnaamcomponentBericht comp = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroep =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroep.setScheidingsteken(new Scheidingsteken("-"));
        standaardGroep.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Piet"));
        standaardGroep.setVoorvoegsel(new Voorvoegsel("der"));
        comp.setGegevens(standaardGroep);
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(comp);

        PersoonBericht pOuder1 = new PersoonBericht();
        PersoonModel pOuderModel1 = new PersoonModel(pOuder1);
        PersoonGeslachtsnaamcomponentBericht compOuder1 = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroepOuder1 =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroepOuder1.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Janneke"));
        standaardGroepOuder1.setVoorvoegsel(new Voorvoegsel("van"));
        standaardGroepOuder1.setScheidingsteken(new Scheidingsteken("/"));
        compOuder1.setGegevens(standaardGroepOuder1);
        pOuderModel1.getGeslachtsnaamcomponenten()
                .add(new PersoonGeslachtsnaamcomponentModel(compOuder1, pOuderModel1));

        PersoonBericht pOuder2 = new PersoonBericht();
        PersoonModel pOuderModel2 = new PersoonModel(pOuder2);
        PersoonGeslachtsnaamcomponentBericht compOuder2 = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroepOuder2 =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroepOuder2.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Piet"));
        standaardGroepOuder2.setVoorvoegsel(new Voorvoegsel("der"));
        standaardGroepOuder2.setScheidingsteken(new Scheidingsteken("-"));
        compOuder2.setGegevens(standaardGroepOuder2);
        pOuderModel2.getGeslachtsnaamcomponenten()
                .add(new PersoonGeslachtsnaamcomponentModel(compOuder2, pOuderModel2));

        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder1.getBetrokkene().getIdentificatienummers()
                        .getBurgerservicenummer())).thenReturn(pOuderModel1);
        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder2.getBetrokkene().getIdentificatienummers()
                        .getBurgerservicenummer())).thenReturn(pOuderModel2);

        List<Melding> melding = brby0106.executeer(null, relatie, null);
        Assert.assertTrue(melding.size() == 0);
    }

    @Test
    public void test2OudersGeslachtsNaamKindGelijkAanGeenVanOuders() {
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 =
            maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("111111111"));
        relatie.getBetrokkenheden().add(ouder1);
        BetrokkenheidBericht ouder2 =
            maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("22222222"));
        relatie.getBetrokkenheden().add(ouder2);
        BetrokkenheidBericht kind =
            maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("66666666"));
        relatie.getBetrokkenheden().add(kind);

        PersoonGeslachtsnaamcomponentBericht compKind = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroepKind =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroepKind.setScheidingsteken(new Scheidingsteken("-"));
        standaardGroepKind.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Jan"));
        standaardGroepKind.setVoorvoegsel(new Voorvoegsel("van der"));
        compKind.setGegevens(standaardGroepKind);
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(compKind);

        PersoonBericht pOuder1 = new PersoonBericht();
        PersoonModel pOuderModel1 = new PersoonModel(pOuder1);
        PersoonGeslachtsnaamcomponentBericht compOuder1 = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroepOuder1 =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroepOuder1.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Janneke"));
        standaardGroepOuder1.setVoorvoegsel(new Voorvoegsel("van"));
        standaardGroepOuder1.setScheidingsteken(new Scheidingsteken("/"));
        compOuder1.setGegevens(standaardGroepOuder1);
        pOuderModel1.getGeslachtsnaamcomponenten()
                .add(new PersoonGeslachtsnaamcomponentModel(compOuder1, pOuderModel1));

        PersoonBericht pOuder2 = new PersoonBericht();
        PersoonModel pOuderModel2 = new PersoonModel(pOuder2);
        PersoonGeslachtsnaamcomponentBericht compOuder2 = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroepOuder2 =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroepOuder2.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Piet"));
        standaardGroepOuder2.setVoorvoegsel(new Voorvoegsel("der"));
        standaardGroepOuder2.setScheidingsteken(new Scheidingsteken("-"));
        compOuder2.setGegevens(standaardGroepOuder2);
        pOuderModel2.getGeslachtsnaamcomponenten()
                .add(new PersoonGeslachtsnaamcomponentModel(compOuder2, pOuderModel2));

        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder1.getBetrokkene().getIdentificatienummers()
                        .getBurgerservicenummer())).thenReturn(pOuderModel1);
        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder2.getBetrokkene().getIdentificatienummers()
                        .getBurgerservicenummer())).thenReturn(pOuderModel2);

        List<Melding> melding = brby0106.executeer(null, relatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.get(0).getSoort());
        Assert.assertEquals(MeldingCode.BRBY0106, melding.get(0).getCode());
        Assert.assertEquals("De opgegeven geslachtsnaam is niet toegestaan. De geslachtsnaam moet gelijk zijn aan die "
            + "van een van de ouders en aan die van eerder geboren kinderen van deze ouders.", melding.get(0)
                .getOmschrijving());
    }

    @Test
    public void test2NietGehuwdeOudersGeslachtsNaamKindGelijkAanOuder2MaarNietAanEerderKindUitHuwelijk() {
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 =
            maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("111111111"));
        relatie.getBetrokkenheden().add(ouder1);
        BetrokkenheidBericht ouder2 =
            maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("22222222"));
        relatie.getBetrokkenheden().add(ouder2);
        BetrokkenheidBericht kind =
            maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("66666666"));
        relatie.getBetrokkenheden().add(kind);

        PersoonGeslachtsnaamcomponentBericht compKind = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroepKind =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroepKind.setScheidingsteken(new Scheidingsteken("-"));
        standaardGroepKind.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Jan"));
        standaardGroepKind.setVoorvoegsel(new Voorvoegsel("van der"));
        compKind.setGegevens(standaardGroepKind);
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(compKind);

        PersoonBericht pOuder1 = new PersoonBericht();
        PersoonModel pOuderModel1 = new PersoonModel(pOuder1);
        PersoonGeslachtsnaamcomponentBericht compOuder1 = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroepOuder1 =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroepOuder1.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Janneke"));
        standaardGroepOuder1.setVoorvoegsel(new Voorvoegsel("van"));
        standaardGroepOuder1.setScheidingsteken(new Scheidingsteken("/"));
        compOuder1.setGegevens(standaardGroepOuder1);
        pOuderModel1.getGeslachtsnaamcomponenten()
                .add(new PersoonGeslachtsnaamcomponentModel(compOuder1, pOuderModel1));

        PersoonBericht pOuder2 = new PersoonBericht();
        PersoonModel pOuderModel2 = new PersoonModel(pOuder2);
        PersoonGeslachtsnaamcomponentBericht compOuder2 = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroepOuder2 =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroepOuder2.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Jan"));
        standaardGroepOuder2.setVoorvoegsel(new Voorvoegsel("van der"));
        standaardGroepOuder2.setScheidingsteken(new Scheidingsteken("-"));
        compOuder2.setGegevens(standaardGroepOuder2);
        pOuderModel2.getGeslachtsnaamcomponenten()
                .add(new PersoonGeslachtsnaamcomponentModel(compOuder2, pOuderModel2));

        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder1.getBetrokkene().getIdentificatienummers()
                        .getBurgerservicenummer())).thenReturn(pOuderModel1);
        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder2.getBetrokkene().getIdentificatienummers()
                        .getBurgerservicenummer())).thenReturn(pOuderModel2);

        // Familie rechtelijke betrekking
        RelatieBericht familieRechtelijkeBetr = new RelatieBericht();
        familieRechtelijkeBetr.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        familieRechtelijkeBetr.setGegevens(new RelatieStandaardGroepBericht());
        RelatieModel familieRechtelijkeBetrModel = new RelatieModel(familieRechtelijkeBetr);

        BetrokkenheidBericht pBetrEerderVerkregenKind = new BetrokkenheidBericht();
        pBetrEerderVerkregenKind.setRol(SoortBetrokkenheid.KIND);

        PersoonBericht eerderVerkregenKind = new PersoonBericht();
        PersoonGeslachtsnaamcomponentBericht compEerderVerkregenKind = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroepEerderVerkregenKind =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroepEerderVerkregenKind.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Liesje"));
        standaardGroepEerderVerkregenKind.setVoorvoegsel(new Voorvoegsel("van"));
        compEerderVerkregenKind.setGegevens(standaardGroepEerderVerkregenKind);
        pBetrEerderVerkregenKind.setBetrokkene(eerderVerkregenKind);

        PersoonModel eerderVerkregenKindModel = new PersoonModel(eerderVerkregenKind);
        eerderVerkregenKindModel.getGeslachtsnaamcomponenten().add(
                new PersoonGeslachtsnaamcomponentModel(compEerderVerkregenKind, eerderVerkregenKindModel));

        familieRechtelijkeBetrModel.getBetrokkenheden()
                .add(new BetrokkenheidModel(pBetrEerderVerkregenKind, eerderVerkregenKindModel,
                        familieRechtelijkeBetrModel));

        List<RelatieModel> gevondenFamilieRechtelijkeBetrekkingen = new ArrayList<RelatieModel>();
        gevondenFamilieRechtelijkeBetrekkingen.add(familieRechtelijkeBetrModel);

        Mockito.when(
                relatieRepository.vindSoortRelatiesMetPersonenInRol(Matchers.any(PersoonModel.class),
                        Matchers.any(PersoonModel.class), Matchers.any(SoortBetrokkenheid.class),
                        Matchers.any(Datum.class), Matchers.any(SoortRelatie.class))).thenReturn(
                gevondenFamilieRechtelijkeBetrekkingen);

        List<Melding> melding = brby0106.executeer(null, relatie, null);
        Assert.assertTrue(melding.size() == 0);
    }

    @Test
    public void test2GehuwdeOudersGeslachtsNaamKindGelijkAanOuder2MaarNietAanEerderKindUitHuwelijk() {
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 =
            maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("111111111"));
        relatie.getBetrokkenheden().add(ouder1);
        BetrokkenheidBericht ouder2 =
            maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("22222222"));
        relatie.getBetrokkenheden().add(ouder2);
        BetrokkenheidBericht kind =
            maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("66666666"));
        relatie.getBetrokkenheden().add(kind);

        PersoonGeslachtsnaamcomponentBericht compKind = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroepKind =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroepKind.setScheidingsteken(new Scheidingsteken("-"));
        standaardGroepKind.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Jan"));
        standaardGroepKind.setVoorvoegsel(new Voorvoegsel("van der"));
        compKind.setGegevens(standaardGroepKind);
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(compKind);

        PersoonBericht pOuder1 = new PersoonBericht();
        PersoonModel pOuderModel1 = new PersoonModel(pOuder1);
        PersoonGeslachtsnaamcomponentBericht compOuder1 = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroepOuder1 =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroepOuder1.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Janneke"));
        standaardGroepOuder1.setVoorvoegsel(new Voorvoegsel("van"));
        standaardGroepOuder1.setScheidingsteken(new Scheidingsteken("/"));
        compOuder1.setGegevens(standaardGroepOuder1);
        pOuderModel1.getGeslachtsnaamcomponenten()
                .add(new PersoonGeslachtsnaamcomponentModel(compOuder1, pOuderModel1));

        PersoonBericht pOuder2 = new PersoonBericht();
        PersoonModel pOuderModel2 = new PersoonModel(pOuder2);
        PersoonGeslachtsnaamcomponentBericht compOuder2 = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroepOuder2 =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroepOuder2.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Jan"));
        standaardGroepOuder2.setVoorvoegsel(new Voorvoegsel("van der"));
        standaardGroepOuder2.setScheidingsteken(new Scheidingsteken("-"));
        compOuder2.setGegevens(standaardGroepOuder2);
        pOuderModel2.getGeslachtsnaamcomponenten()
                .add(new PersoonGeslachtsnaamcomponentModel(compOuder2, pOuderModel2));

        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder1.getBetrokkene().getIdentificatienummers()
                        .getBurgerservicenummer())).thenReturn(pOuderModel1);
        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder2.getBetrokkene().getIdentificatienummers()
                        .getBurgerservicenummer())).thenReturn(pOuderModel2);

        // Huwelijk
        RelatieBericht huwelijk = new RelatieBericht();
        huwelijk.setGegevens(new RelatieStandaardGroepBericht());
        RelatieModel huwelijkModel = new RelatieModel(huwelijk);

        BetrokkenheidBericht pBetrOuder1 = new BetrokkenheidBericht();
        pBetrOuder1.setBetrokkene(pOuder1);

        BetrokkenheidBericht pBetrOuder2 = new BetrokkenheidBericht();
        pBetrOuder2.setBetrokkene(pOuder2);

        huwelijkModel.getBetrokkenheden().add(new BetrokkenheidModel(pBetrOuder1, pOuderModel1, huwelijkModel));
        huwelijkModel.getBetrokkenheden().add(new BetrokkenheidModel(pBetrOuder2, pOuderModel2, huwelijkModel));

        List<RelatieModel> gevondenHuwelijken = new ArrayList<RelatieModel>();
        gevondenHuwelijken.add(huwelijkModel);
        Mockito.when(
                relatieRepository.vindSoortRelatiesMetPersonenInRol(Matchers.any(PersoonModel.class),
                        Matchers.any(PersoonModel.class), Matchers.any(SoortBetrokkenheid.class),
                        Matchers.any(Datum.class), Matchers.any(SoortRelatie.class), Matchers.any(SoortRelatie.class)))
                .thenReturn(gevondenHuwelijken);

        // Familie rechtelijke betrekking
        RelatieBericht familieRechtelijkeBetr = new RelatieBericht();
        familieRechtelijkeBetr.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        familieRechtelijkeBetr.setGegevens(new RelatieStandaardGroepBericht());
        RelatieModel familieRechtelijkeBetrModel = new RelatieModel(familieRechtelijkeBetr);

        BetrokkenheidBericht pBetrEerderVerkregenKind = new BetrokkenheidBericht();
        pBetrEerderVerkregenKind.setRol(SoortBetrokkenheid.KIND);
        PersoonBericht eerderVerkregenKind = new PersoonBericht();
        PersoonGeslachtsnaamcomponentBericht compEerderVerkregenKind = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht standaardGroepEerderVerkregenKind =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroepEerderVerkregenKind.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("Liesje"));
        standaardGroepEerderVerkregenKind.setVoorvoegsel(new Voorvoegsel("van"));
        compEerderVerkregenKind.setGegevens(standaardGroepEerderVerkregenKind);
        pBetrEerderVerkregenKind.setBetrokkene(eerderVerkregenKind);

        PersoonModel eerderVerkregenKindModel = new PersoonModel(eerderVerkregenKind);
        eerderVerkregenKindModel.getGeslachtsnaamcomponenten().add(
                new PersoonGeslachtsnaamcomponentModel(compEerderVerkregenKind, eerderVerkregenKindModel));

        familieRechtelijkeBetrModel.getBetrokkenheden()
                .add(new BetrokkenheidModel(pBetrEerderVerkregenKind, eerderVerkregenKindModel,
                        familieRechtelijkeBetrModel));

        List<RelatieModel> gevondenFamilieRechtelijkeBetrekkingen = new ArrayList<RelatieModel>();
        gevondenFamilieRechtelijkeBetrekkingen.add(familieRechtelijkeBetrModel);

        Mockito.when(
                relatieRepository.vindSoortRelatiesMetPersonenInRol(Matchers.any(PersoonModel.class),
                        Matchers.any(PersoonModel.class), Matchers.any(SoortBetrokkenheid.class),
                        Matchers.any(Datum.class), Matchers.any(SoortRelatie.class))).thenReturn(
                gevondenFamilieRechtelijkeBetrekkingen);

        List<Melding> melding = brby0106.executeer(null, relatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.get(0).getSoort());
        Assert.assertEquals(MeldingCode.BRBY0106, melding.get(0).getCode());
        Assert.assertEquals("De opgegeven geslachtsnaam is niet toegestaan. De geslachtsnaam moet gelijk zijn aan die "
            + "van een van de ouders en aan die van eerder geboren kinderen van deze ouders.", melding.get(0)
                .getOmschrijving());
    }

    @Test
    public void testOuder1NietGevondenMetBsn() {
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 =
            maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("123456789"));
        relatie.getBetrokkenheden().add(ouder1);
        BetrokkenheidBericht kind =
            maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("66666666"));
        kind.getBetrokkene().setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamcomponentBericht());
        relatie.getBetrokkenheden().add(kind);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456789")))
                .thenReturn(null);

        List<Melding> melding = brby0106.executeer(null, relatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(MeldingCode.REF0001, melding.get(0).getCode());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, melding.get(0).getSoort());
    }

    @Test
    public void testOuder2NietGevondenMetBsn() {
        RelatieBericht relatie = maakRelatie();
        relatie.getBetrokkenheden().add(
                maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("123456789")));
        relatie.getBetrokkenheden().add(
                maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("123456700")));

        BetrokkenheidBericht kind =
            maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("66666666"));
        kind.getBetrokkene().setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamcomponentBericht());
        relatie.getBetrokkenheden().add(kind);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456789"))).thenReturn(
                new PersoonModel(new PersoonBericht()));
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456700")))
                .thenReturn(null);

        List<Melding> melding = brby0106.executeer(null, relatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(MeldingCode.REF0001, melding.get(0).getCode());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, melding.get(0).getSoort());
    }

    private RelatieBericht maakRelatie() {
        RelatieBericht relatie = new RelatieBericht();
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        relatie.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        return relatie;
    }

    private BetrokkenheidBericht maakBetrokkenheid(final SoortBetrokkenheid soort, final RelatieBericht relatie,
            final Burgerservicenummer bsn)
    {
        BetrokkenheidBericht betr = new BetrokkenheidBericht();
        betr.setRol(soort);
        betr.setRelatie(relatie);
        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(bsn);
        persoon.setGeboorte(new PersoonGeboorteGroepBericht());
        persoon.getGeboorte().setDatumGeboorte(new Datum(20120101));
        persoon.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());
        betr.setBetrokkene(persoon);
        return betr;
    }
}
