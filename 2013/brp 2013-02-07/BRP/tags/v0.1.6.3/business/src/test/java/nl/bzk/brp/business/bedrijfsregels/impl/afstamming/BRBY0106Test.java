/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.repository.PersoonMdlRepository;
import nl.bzk.brp.dataaccess.repository.RelatieMdlRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.GeslachtsnaamComponent;
import nl.bzk.brp.model.attribuuttype.ScheidingsTeken;
import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.groep.bericht.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsnaamCompStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatieNummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonGeslachtsnaamComponentBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamComponentModel;
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
    private RelatieMdlRepository relatieRepository;

    @Mock
    private PersoonMdlRepository persoonRepository;

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

        Melding melding = brby0106.executeer(null, relatie, null);

        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals(MeldingCode.ALG0002, melding.getCode());
        Assert.assertEquals("Er is geen ouder meegegeven in het bericht.", melding.getOmschrijving());
    }

    @Test
    public void testOuder1ZonderIdentificatieNummers() {
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie,
                new Burgerservicenummer("123456789"));
        ouder1.getBetrokkene().setIdentificatieNummers(null);
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
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("123456789"));
        BetrokkenheidBericht ouder2 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("11111111"));
        ouder2.getBetrokkene().setIdentificatieNummers(null);
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
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("123456789"));
        BetrokkenheidBericht ouder2 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("11111111"));
        relatie.getBetrokkenheden().add(ouder1);
        relatie.getBetrokkenheden().add(ouder2);
        BetrokkenheidBericht kind = maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("66666666"));
        relatie.getBetrokkenheden().add(kind);

        Melding melding = brby0106.executeer(null, relatie, null);

        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, melding.getSoort());
        Assert.assertEquals(MeldingCode.ALG0002, melding.getCode());
        Assert.assertEquals("Er zijn geen geslachtsnaamcomponenten voor het kind meegegeven in het bericht.",
                melding.getOmschrijving());
    }

    @Test
    public void testKindMet1OuderEnDezelfdeGeslachtsnamen() {
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("123456789"));
        relatie.getBetrokkenheden().add(ouder1);
        BetrokkenheidBericht kind = maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("66666666"));
        relatie.getBetrokkenheden().add(kind);

        PersoonGeslachtsnaamComponentBericht comp = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroep = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroep.setScheidingsteken(new ScheidingsTeken("-"));
        standaardGroep.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Piet"));
        standaardGroep.setVoorvoegsel(new Voorvoegsel("der"));
        comp.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroep);
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(comp);

        PersoonBericht pOuder = new PersoonBericht();
        PersoonModel pOuderModel = new PersoonModel(pOuder);
        PersoonGeslachtsnaamComponentBericht compOuder = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroepOuder = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroepOuder.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Piet"));
        standaardGroepOuder.setVoorvoegsel(new Voorvoegsel("der"));
        standaardGroepOuder.setScheidingsteken(new ScheidingsTeken("-"));
        compOuder.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroepOuder);
        pOuderModel.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamComponentModel(compOuder, pOuderModel));

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456789")))
                .thenReturn(pOuderModel);

        Melding melding = brby0106.executeer(null, relatie, null);
        Assert.assertNull(melding);
    }

    @Test
    public void testKindMet1OuderEnVerschillendeGeslachtsnamen() {
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("123456789"));
        relatie.getBetrokkenheden().add(ouder1);
        BetrokkenheidBericht kind = maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("66666666"));
        relatie.getBetrokkenheden().add(kind);

        PersoonGeslachtsnaamComponentBericht comp = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroep = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroep.setScheidingsteken(new ScheidingsTeken("-"));
        standaardGroep.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Jan"));
        standaardGroep.setVoorvoegsel(new Voorvoegsel("der"));
        comp.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroep);
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(comp);

        PersoonBericht pOuder = new PersoonBericht();
        PersoonModel pOuderModel = new PersoonModel(pOuder);
        PersoonGeslachtsnaamComponentBericht compOuder = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroepOuder = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroepOuder.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Piet"));
        standaardGroepOuder.setVoorvoegsel(new Voorvoegsel("der"));
        standaardGroepOuder.setScheidingsteken(new ScheidingsTeken("-"));
        compOuder.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroepOuder);
        pOuderModel.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamComponentModel(compOuder, pOuderModel));

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456789"))).thenReturn(pOuderModel);

        Melding melding = brby0106.executeer(null, relatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals(MeldingCode.BRBY0106, melding.getCode());
        Assert.assertEquals("De opgegeven geslachtsnaam is niet toegestaan. De geslachtsnaam moet gelijk zijn aan die "
                + "van een van de ouders en aan die van eerder geboren kinderen van deze ouders.",
                melding.getOmschrijving());

        // Reset
        standaardGroep.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Piet"));
        melding = brby0106.executeer(null, relatie, null);
        Assert.assertNull(melding);
        // Veschillende voorvoegsels
        standaardGroepOuder.setVoorvoegsel(new Voorvoegsel("van"));
        pOuderModel.getGeslachtsnaamcomponenten().clear();
        pOuderModel.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamComponentModel(compOuder, pOuderModel));
        melding = brby0106.executeer(null, relatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals(MeldingCode.BRBY0106, melding.getCode());
        Assert.assertEquals("De opgegeven geslachtsnaam is niet toegestaan. De geslachtsnaam moet gelijk zijn aan die "
                + "van een van de ouders en aan die van eerder geboren kinderen van deze ouders.",
                melding.getOmschrijving());

        // Reset
        standaardGroepOuder.setVoorvoegsel(new Voorvoegsel("der"));
        pOuderModel.getGeslachtsnaamcomponenten().clear();
        pOuderModel.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamComponentModel(compOuder, pOuderModel));
        melding = brby0106.executeer(null, relatie, null);
        Assert.assertNull(melding);
        // Verschillende scheidingstekens
        standaardGroep.setScheidingsteken(new ScheidingsTeken("/"));
        melding = brby0106.executeer(null, relatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals(MeldingCode.BRBY0106, melding.getCode());
        Assert.assertEquals("De opgegeven geslachtsnaam is niet toegestaan. De geslachtsnaam moet gelijk zijn aan die "
                + "van een van de ouders en aan die van eerder geboren kinderen van deze ouders.",
                melding.getOmschrijving());

        // Reset
        standaardGroep.setScheidingsteken(new ScheidingsTeken("-"));
        melding = brby0106.executeer(null, relatie, null);
        Assert.assertNull(melding);
        // Verschillende volgnummers (Geen effect)
        comp.setVolgnummer(new Volgnummer(6));
        melding = brby0106.executeer(null, relatie, null);
        Assert.assertNull(melding);
    }

    @Test
    public void testKindMet1OuderEnDezelfdeGeslachtsnamenMaarKindGeslNaamIsLegeString() {
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("123456789"));
        relatie.getBetrokkenheden().add(ouder1);
        BetrokkenheidBericht kind = maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("66666666"));
        relatie.getBetrokkenheden().add(kind);

        PersoonGeslachtsnaamComponentBericht comp = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroep = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroep.setScheidingsteken(new ScheidingsTeken("-"));
        standaardGroep.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Piet"));
        standaardGroep.setVoorvoegsel(new Voorvoegsel(""));
        comp.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroep);
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(comp);

        PersoonBericht pOuder = new PersoonBericht();
        PersoonModel pOuderModel = new PersoonModel(pOuder);
        PersoonGeslachtsnaamComponentBericht compOuder = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroepOuder = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroepOuder.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Piet"));
        standaardGroepOuder.setVoorvoegsel(null);
        standaardGroepOuder.setScheidingsteken(new ScheidingsTeken("-"));
        compOuder.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroepOuder);
        pOuderModel.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamComponentModel(compOuder, pOuderModel));

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456789"))).thenReturn(pOuderModel);

        Melding melding = brby0106.executeer(null, relatie, null);
        Assert.assertNull(melding);
    }

    @Test
    public void test2OudersGeslachtsNaamKindGelijkAanOuder2EnGeenEerdereKinderen() {
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("111111111"));
        relatie.getBetrokkenheden().add(ouder1);
        BetrokkenheidBericht ouder2 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("22222222"));
        relatie.getBetrokkenheden().add(ouder2);
        BetrokkenheidBericht kind = maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("66666666"));
        relatie.getBetrokkenheden().add(kind);

        PersoonGeslachtsnaamComponentBericht comp = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroep = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroep.setScheidingsteken(new ScheidingsTeken("-"));
        standaardGroep.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Piet"));
        standaardGroep.setVoorvoegsel(new Voorvoegsel("der"));
        comp.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroep);
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(comp);

        PersoonBericht pOuder1 = new PersoonBericht();
        PersoonModel pOuderModel1 = new PersoonModel(pOuder1);
        PersoonGeslachtsnaamComponentBericht compOuder1 = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroepOuder1 = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroepOuder1.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Janneke"));
        standaardGroepOuder1.setVoorvoegsel(new Voorvoegsel("van"));
        standaardGroepOuder1.setScheidingsteken(new ScheidingsTeken("/"));
        compOuder1.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroepOuder1);
        pOuderModel1.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamComponentModel(compOuder1, pOuderModel1));

        PersoonBericht pOuder2 = new PersoonBericht();
        PersoonModel pOuderModel2 = new PersoonModel(pOuder2);
        PersoonGeslachtsnaamComponentBericht compOuder2 = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroepOuder2 = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroepOuder2.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Piet"));
        standaardGroepOuder2.setVoorvoegsel(new Voorvoegsel("der"));
        standaardGroepOuder2.setScheidingsteken(new ScheidingsTeken("-"));
        compOuder2.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroepOuder2);
        pOuderModel2.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamComponentModel(compOuder2, pOuderModel2));


        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder1.getBetrokkene().getIdentificatieNummers()
                        .getBurgerServiceNummer())).thenReturn(pOuderModel1);
        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder2.getBetrokkene().getIdentificatieNummers()
                        .getBurgerServiceNummer())).thenReturn(pOuderModel2);

        Melding melding = brby0106.executeer(null, relatie, null);
        Assert.assertNull(melding);
    }

    @Test
    public void test2OudersGeslachtsNaamKindGelijkAanGeenVanOuders() {
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("111111111"));
        relatie.getBetrokkenheden().add(ouder1);
        BetrokkenheidBericht ouder2 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("22222222"));
        relatie.getBetrokkenheden().add(ouder2);
        BetrokkenheidBericht kind = maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("66666666"));
        relatie.getBetrokkenheden().add(kind);

        PersoonGeslachtsnaamComponentBericht compKind = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroepKind = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroepKind.setScheidingsteken(new ScheidingsTeken("-"));
        standaardGroepKind.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Jan"));
        standaardGroepKind.setVoorvoegsel(new Voorvoegsel("van der"));
        compKind.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroepKind);
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(compKind);

        PersoonBericht pOuder1 = new PersoonBericht();
        PersoonModel pOuderModel1 = new PersoonModel(pOuder1);
        PersoonGeslachtsnaamComponentBericht compOuder1 = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroepOuder1 = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroepOuder1.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Janneke"));
        standaardGroepOuder1.setVoorvoegsel(new Voorvoegsel("van"));
        standaardGroepOuder1.setScheidingsteken(new ScheidingsTeken("/"));
        compOuder1.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroepOuder1);
        pOuderModel1.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamComponentModel(compOuder1, pOuderModel1));

        PersoonBericht pOuder2 = new PersoonBericht();
        PersoonModel pOuderModel2 = new PersoonModel(pOuder2);
        PersoonGeslachtsnaamComponentBericht compOuder2 = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroepOuder2 = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroepOuder2.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Piet"));
        standaardGroepOuder2.setVoorvoegsel(new Voorvoegsel("der"));
        standaardGroepOuder2.setScheidingsteken(new ScheidingsTeken("-"));
        compOuder2.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroepOuder2);
        pOuderModel2.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamComponentModel(compOuder2, pOuderModel2));

        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder1.getBetrokkene().getIdentificatieNummers()
                        .getBurgerServiceNummer())).thenReturn(pOuderModel1);
        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder2.getBetrokkene().getIdentificatieNummers()
                        .getBurgerServiceNummer())).thenReturn(pOuderModel2);

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
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("111111111"));
        relatie.getBetrokkenheden().add(ouder1);
        BetrokkenheidBericht ouder2 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("22222222"));
        relatie.getBetrokkenheden().add(ouder2);
        BetrokkenheidBericht kind = maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("66666666"));
        relatie.getBetrokkenheden().add(kind);

        PersoonGeslachtsnaamComponentBericht compKind = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroepKind = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroepKind.setScheidingsteken(new ScheidingsTeken("-"));
        standaardGroepKind.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Jan"));
        standaardGroepKind.setVoorvoegsel(new Voorvoegsel("van der"));
        compKind.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroepKind);
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(compKind);

        PersoonBericht pOuder1 = new PersoonBericht();
        PersoonModel pOuderModel1 = new PersoonModel(pOuder1);
        PersoonGeslachtsnaamComponentBericht compOuder1 = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroepOuder1 = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroepOuder1.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Janneke"));
        standaardGroepOuder1.setVoorvoegsel(new Voorvoegsel("van"));
        standaardGroepOuder1.setScheidingsteken(new ScheidingsTeken("/"));
        compOuder1.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroepOuder1);
        pOuderModel1.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamComponentModel(compOuder1, pOuderModel1));

        PersoonBericht pOuder2 = new PersoonBericht();
        PersoonModel pOuderModel2 = new PersoonModel(pOuder2);
        PersoonGeslachtsnaamComponentBericht compOuder2 = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroepOuder2 = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroepOuder2.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Jan"));
        standaardGroepOuder2.setVoorvoegsel(new Voorvoegsel("van der"));
        standaardGroepOuder2.setScheidingsteken(new ScheidingsTeken("-"));
        compOuder2.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroepOuder2);
        pOuderModel2.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamComponentModel(compOuder2, pOuderModel2));

        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder1.getBetrokkene().getIdentificatieNummers()
                        .getBurgerServiceNummer())).thenReturn(pOuderModel1);
        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder2.getBetrokkene().getIdentificatieNummers()
                        .getBurgerServiceNummer())).thenReturn(pOuderModel2);

        // Familie rechtelijke betrekking
        RelatieBericht familieRechtelijkeBetr = new RelatieBericht();
        familieRechtelijkeBetr.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        familieRechtelijkeBetr.setGegevens(new RelatieStandaardGroepBericht());
        RelatieModel familieRechtelijkeBetrModel = new RelatieModel(familieRechtelijkeBetr);

        BetrokkenheidBericht pBetrEerderVerkregenKind = new BetrokkenheidBericht();
        pBetrEerderVerkregenKind.setRol(SoortBetrokkenheid.KIND);

        PersoonBericht eerderVerkregenKind = new PersoonBericht();
        PersoonGeslachtsnaamComponentBericht compEerderVerkregenKind = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroepEerderVerkregenKind = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroepEerderVerkregenKind.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Liesje"));
        standaardGroepEerderVerkregenKind.setVoorvoegsel(new Voorvoegsel("van"));
        compEerderVerkregenKind.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroepEerderVerkregenKind);
        pBetrEerderVerkregenKind.setBetrokkene(eerderVerkregenKind);

        PersoonModel eerderVerkregenKindModel = new PersoonModel(eerderVerkregenKind);
        eerderVerkregenKindModel.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamComponentModel(compEerderVerkregenKind, eerderVerkregenKindModel));

        familieRechtelijkeBetrModel.getBetrokkenheden().add(new BetrokkenheidModel(
                pBetrEerderVerkregenKind, eerderVerkregenKindModel, familieRechtelijkeBetrModel));

        List<RelatieModel> gevondenFamilieRechtelijkeBetrekkingen = new ArrayList<RelatieModel>();
        gevondenFamilieRechtelijkeBetrekkingen.add(familieRechtelijkeBetrModel);

        Mockito.when(
                relatieRepository.vindSoortRelatiesMetPersonenInRol(
                        Matchers.any(PersoonModel.class),
                        Matchers.any(PersoonModel.class),
                        Matchers.any(SoortBetrokkenheid.class),
                        Matchers.any(Datum.class),
                        Matchers.any(SoortRelatie.class))).thenReturn(
                gevondenFamilieRechtelijkeBetrekkingen);

        Melding melding = brby0106.executeer(null, relatie, null);
        Assert.assertNull(melding);
    }

    @Test
    public void test2GehuwdeOudersGeslachtsNaamKindGelijkAanOuder2MaarNietAanEerderKindUitHuwelijk() {
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("111111111"));
        relatie.getBetrokkenheden().add(ouder1);
        BetrokkenheidBericht ouder2 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("22222222"));
        relatie.getBetrokkenheden().add(ouder2);
        BetrokkenheidBericht kind = maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("66666666"));
        relatie.getBetrokkenheden().add(kind);

        PersoonGeslachtsnaamComponentBericht compKind = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroepKind = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroepKind.setScheidingsteken(new ScheidingsTeken("-"));
        standaardGroepKind.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Jan"));
        standaardGroepKind.setVoorvoegsel(new Voorvoegsel("van der"));
        compKind.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroepKind);
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(compKind);

        PersoonBericht pOuder1 = new PersoonBericht();
        PersoonModel pOuderModel1 = new PersoonModel(pOuder1);
        PersoonGeslachtsnaamComponentBericht compOuder1 = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroepOuder1 = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroepOuder1.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Janneke"));
        standaardGroepOuder1.setVoorvoegsel(new Voorvoegsel("van"));
        standaardGroepOuder1.setScheidingsteken(new ScheidingsTeken("/"));
        compOuder1.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroepOuder1);
        pOuderModel1.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamComponentModel(compOuder1, pOuderModel1));

        PersoonBericht pOuder2 = new PersoonBericht();
        PersoonModel pOuderModel2 = new PersoonModel(pOuder2);
        PersoonGeslachtsnaamComponentBericht compOuder2 = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroepOuder2 = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroepOuder2.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Jan"));
        standaardGroepOuder2.setVoorvoegsel(new Voorvoegsel("van der"));
        standaardGroepOuder2.setScheidingsteken(new ScheidingsTeken("-"));
        compOuder2.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroepOuder2);
        pOuderModel2.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamComponentModel(compOuder2, pOuderModel2));

        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder1.getBetrokkene().getIdentificatieNummers()
                        .getBurgerServiceNummer())).thenReturn(pOuderModel1);
        Mockito.when(
                persoonRepository.findByBurgerservicenummer(ouder2.getBetrokkene().getIdentificatieNummers()
                        .getBurgerServiceNummer())).thenReturn(pOuderModel2);

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
                relatieRepository.vindSoortRelatiesMetPersonenInRol(
                        Matchers.any(PersoonModel.class),
                        Matchers.any(PersoonModel.class),
                        Matchers.any(SoortBetrokkenheid.class),
                        Matchers.any(Datum.class),
                        Matchers.any(SoortRelatie.class),
                        Matchers.any(SoortRelatie.class))).thenReturn(gevondenHuwelijken);

        // Familie rechtelijke betrekking
        RelatieBericht familieRechtelijkeBetr = new RelatieBericht();
        familieRechtelijkeBetr.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        familieRechtelijkeBetr.setGegevens(new RelatieStandaardGroepBericht());
        RelatieModel familieRechtelijkeBetrModel = new RelatieModel(familieRechtelijkeBetr);

        BetrokkenheidBericht pBetrEerderVerkregenKind = new BetrokkenheidBericht();
        pBetrEerderVerkregenKind.setRol(SoortBetrokkenheid.KIND);
        PersoonBericht eerderVerkregenKind = new PersoonBericht();
        PersoonGeslachtsnaamComponentBericht compEerderVerkregenKind = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroepEerderVerkregenKind = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroepEerderVerkregenKind.setGeslachtsnaamComponent(new GeslachtsnaamComponent("Liesje"));
        standaardGroepEerderVerkregenKind.setVoorvoegsel(new Voorvoegsel("van"));
        compEerderVerkregenKind.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroepEerderVerkregenKind);
        pBetrEerderVerkregenKind.setBetrokkene(eerderVerkregenKind);

        PersoonModel eerderVerkregenKindModel = new PersoonModel(eerderVerkregenKind);
        eerderVerkregenKindModel.getGeslachtsnaamcomponenten().add(
                new PersoonGeslachtsnaamComponentModel(compEerderVerkregenKind, eerderVerkregenKindModel));

        familieRechtelijkeBetrModel.getBetrokkenheden().add(
                new BetrokkenheidModel(pBetrEerderVerkregenKind, eerderVerkregenKindModel, familieRechtelijkeBetrModel));

        List<RelatieModel> gevondenFamilieRechtelijkeBetrekkingen = new ArrayList<RelatieModel>();
        gevondenFamilieRechtelijkeBetrekkingen.add(familieRechtelijkeBetrModel);

        Mockito.when(
                relatieRepository.vindSoortRelatiesMetPersonenInRol(
                        Matchers.any(PersoonModel.class),
                        Matchers.any(PersoonModel.class),
                        Matchers.any(SoortBetrokkenheid.class),
                        Matchers.any(Datum.class),
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
        RelatieBericht relatie = maakRelatie();
        BetrokkenheidBericht ouder1 = maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("123456789"));
        relatie.getBetrokkenheden().add(ouder1);
        BetrokkenheidBericht kind = maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("66666666"));
        kind.getBetrokkene().setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamComponentBericht>());
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamComponentBericht());
        relatie.getBetrokkenheden().add(kind);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456789"))).thenReturn(null);

        Melding melding = brby0106.executeer(null, relatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(MeldingCode.REF0001, melding.getCode());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, melding.getSoort());
    }

    @Test
    public void testOuder2NietGevondenMetBsn() {
        RelatieBericht relatie = maakRelatie();
        relatie.getBetrokkenheden().add(maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("123456789")));
        relatie.getBetrokkenheden().add(maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("123456700")));

        BetrokkenheidBericht kind = maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("66666666"));
        kind.getBetrokkene().setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamComponentBericht>());
        kind.getBetrokkene().getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamComponentBericht());
        relatie.getBetrokkenheden().add(kind);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456789"))).thenReturn(new PersoonModel(new PersoonBericht()));
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456700"))).thenReturn(null);

        Melding melding = brby0106.executeer(null, relatie, null);
        Assert.assertNotNull(melding);
        Assert.assertEquals(MeldingCode.REF0001, melding.getCode());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, melding.getSoort());
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
        persoon.setIdentificatieNummers(new PersoonIdentificatieNummersGroepBericht());
        persoon.getIdentificatieNummers().setBurgerServiceNummer(bsn);
        persoon.setGeboorte(new PersoonGeboorteGroepBericht());
        persoon.getGeboorte().setDatumGeboorte(new Datum(20120101));
        persoon.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamComponentBericht>());
        betr.setBetrokkene(persoon);
        return betr;
    }
}
