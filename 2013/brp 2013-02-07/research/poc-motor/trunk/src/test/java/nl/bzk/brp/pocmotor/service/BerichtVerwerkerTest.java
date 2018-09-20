/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Date;

import javax.inject.Inject;

import nl.bzk.brp.pocmotor.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.pocmotor.bedrijfsregels.BedrijfsRegelFout;
import nl.bzk.brp.pocmotor.bedrijfsregels.OpaOmaArtikel;
import nl.bzk.brp.pocmotor.bedrijfsregels.VoornaamGewenst;
import nl.bzk.brp.pocmotor.dal.logisch.PersoonLGMRepository;
import nl.bzk.brp.pocmotor.model.Bericht;
import nl.bzk.brp.pocmotor.model.RootObject;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.NaamEnumeratiewaarde;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PartijID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Voornamen;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Geslachtsaanduiding;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortActie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortBetrokkenheid;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortPersoon;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortRelatie;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.BRPActieIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.BetrokkenheidIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.LandIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.NationaliteitIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PartijIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonGeboorte;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonGeslachtsaanduiding;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonIdentificatienummers;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonNationaliteitIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonSamengesteldeNaam;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.RelatieIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.BRPActie;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Betrokkenheid;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Land;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Nationaliteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Partij;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.PersoonNationaliteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Relatie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor de bericht verwerker.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-config.xml")
public class BerichtVerwerkerTest {

    @Inject
    private BerichtVerwerker         berichtVerwerker;
    @Inject
    private BedrijfsRegelManagerImpl bedrijfsRegelManager;
    @Mock
    private PersoonLGMRepository persoonLGMRepositoryMock;

    @Test
    @Ignore
    public void testBerichtZonderActies() {
        Bericht bericht = new Bericht();
        List<BedrijfsRegelFout> fouten = berichtVerwerker.verwerkBericht(bericht);
        Assert.assertTrue(fouten.isEmpty());
    }

    @Test
    @Ignore
    public void testBerichtMetActieZonderBedrijfsRegels() {
        ReflectionTestUtils.setField(bedrijfsRegelManager, "regelsMap", new HashMap());

        Bericht bericht = new Bericht();
        bericht.setBerichtId("TEST-Bericht");
        bericht.setTijdstipVerzonden(new Date());
        bericht.setBijhoudingen(bouwEersteInschrijvingActie(SoortActie.DUMMY));

        List<BedrijfsRegelFout> fouten = berichtVerwerker.verwerkBericht(bericht);
        Assert.assertTrue(fouten.isEmpty());
    }

    @Test
    @Ignore
    public void testBerichtMetActieMetFalendeBedrijfsRegel() {
        Bericht bericht = new Bericht();
        bericht.setBerichtId("TEST-Bericht");
        bericht.setTijdstipVerzonden(new Date());
        bericht.setBijhoudingen(bouwEersteInschrijvingActie(SoortActie.DUMMY));

        List<BedrijfsRegelFout> fouten = berichtVerwerker.verwerkBericht(bericht);
        Assert.assertEquals(1, fouten.size());
        Assert.assertEquals("VOORNAAM-GEWENST", fouten.get(0).getBedrijfsRegelCode());
    }

    @Test
    @Ignore
    public void testBerichtMetActieMetSuccessvolleBedrijfsRegel() {
        Bericht bericht = new Bericht();
        bericht.setBerichtId("TEST-Bericht");
        bericht.setTijdstipVerzonden(new Date());
        bericht.setBijhoudingen(bouwEersteInschrijvingActie(SoortActie.DUMMY_WAARDE));

        List<BedrijfsRegelFout> fouten = berichtVerwerker.verwerkBericht(bericht);
        Assert.assertTrue(fouten.isEmpty());
    }

    private List<BRPActie> bouwEersteInschrijvingActie(final SoortActie soortActie) {
        BRPActie actie = new BRPActie();
        actie.setIdentiteit(new BRPActieIdentiteit());
        actie.getIdentiteit().setSoort(soortActie);
        actie.setDatumAanvangGeldigheid(new Datum());
        actie.getDatumAanvangGeldigheid().setWaarde(20120203);
        actie.getIdentiteit().setPartij(new Partij());
        actie.getIdentiteit().getPartij().setIdentiteit(new PartijIdentiteit());
        actie.getIdentiteit().getPartij().getIdentiteit().setID(new PartijID());
        actie.getIdentiteit().getPartij().getIdentiteit().getID().setWaarde(6030);

        Relatie relatie = new Relatie();
        relatie.setIdentiteit(new RelatieIdentiteit());
        relatie.getIdentiteit().setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        List<RootObject> rootObjecten = new ArrayList<RootObject>();
        rootObjecten.add(relatie);
        actie.setRootObjecten(rootObjecten);

        Persoon kind = new Persoon();
        kind.setIdentiteit(new PersoonIdentiteit());
        kind.getIdentiteit().setSoort(SoortPersoon.INGESCHREVENE);
        kind.setSamengesteldeNaam(new PersoonSamengesteldeNaam());
        kind.getSamengesteldeNaam().setVoornamen(new Voornamen());

        Betrokkenheid betrokkenheid = new Betrokkenheid();
        betrokkenheid.setIdentiteit(new BetrokkenheidIdentiteit());
        betrokkenheid.getIdentiteit().setRol(SoortBetrokkenheid.KIND);
        betrokkenheid.getIdentiteit().setBetrokkene(kind);
        relatie.voegBetrokkenHeidToe(betrokkenheid);

        Persoon ouder1 = new Persoon();
        ouder1.setIdentiteit(new PersoonIdentiteit());
        ouder1.getIdentiteit().setSoort(SoortPersoon.INGESCHREVENE);
        ouder1.setIdentificatienummers(new PersoonIdentificatienummers());
        ouder1.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer());
        ouder1.getIdentificatienummers().getBurgerservicenummer().setWaarde("123456789");
        ouder1.setGeslachtsaanduiding(new PersoonGeslachtsaanduiding());
        ouder1.getGeslachtsaanduiding().setGeslachtsaanduiding(Geslachtsaanduiding.MAN);

        Nationaliteit nationaliteit = new Nationaliteit();
        nationaliteit.setIdentiteit(new NationaliteitIdentiteit());
        nationaliteit.getIdentiteit().setNaam(new NaamEnumeratiewaarde());
        nationaliteit.getIdentiteit().getNaam().setWaarde("Nederlandse");
        Land nederland = new Land();
        nederland.setIdentiteit(new LandIdentiteit());
        nederland.getIdentiteit().setNaam(new NaamEnumeratiewaarde());
        nederland.getIdentiteit().getNaam().setWaarde("Nederland");

        PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit();
        persoonNationaliteit.setIdentiteit(new PersoonNationaliteitIdentiteit());
        persoonNationaliteit.getIdentiteit().setPersoon(ouder1);
        persoonNationaliteit.getIdentiteit().setNationaliteit(nationaliteit);
        Set<PersoonNationaliteit> nationaliteiten = new HashSet<PersoonNationaliteit>();        
        nationaliteiten.add(persoonNationaliteit);
        ouder1.setNationaliteiten(nationaliteiten);

        ouder1.setGeboorte(new PersoonGeboorte());
        ouder1.getGeboorte().setLandGeboorte(nederland);
        
        Betrokkenheid betrokkenheid1 = new Betrokkenheid();
        betrokkenheid1.setIdentiteit(new BetrokkenheidIdentiteit());
        betrokkenheid1.getIdentiteit().setRol(SoortBetrokkenheid.OUDER);
        betrokkenheid1.getIdentiteit().setBetrokkene(ouder1);
        relatie.voegBetrokkenHeidToe(betrokkenheid1);

        Persoon ouder2 = new Persoon();
        ouder2.setIdentiteit(new PersoonIdentiteit());
        ouder2.getIdentiteit().setSoort(SoortPersoon.INGESCHREVENE);
        ouder2.setIdentificatienummers(new PersoonIdentificatienummers());
        ouder2.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer());
        ouder2.getIdentificatienummers().getBurgerservicenummer().setWaarde("234567890");
        ouder2.setGeslachtsaanduiding(new PersoonGeslachtsaanduiding());
        ouder2.getGeslachtsaanduiding().setGeslachtsaanduiding(Geslachtsaanduiding.VROUW);

        nationaliteit = new Nationaliteit();
        nationaliteit.setIdentiteit(new NationaliteitIdentiteit());
        nationaliteit.getIdentiteit().setNaam(new NaamEnumeratiewaarde());
        nationaliteit.getIdentiteit().getNaam().setWaarde("Nederlandse");

        nationaliteiten = new HashSet<PersoonNationaliteit>();
        persoonNationaliteit = new PersoonNationaliteit();
        persoonNationaliteit.setIdentiteit(new PersoonNationaliteitIdentiteit());
        persoonNationaliteit.getIdentiteit().setPersoon(ouder2);
        persoonNationaliteit.getIdentiteit().setNationaliteit(nationaliteit);
        nationaliteiten = new HashSet<PersoonNationaliteit>();
        nationaliteiten.add(persoonNationaliteit);
        ouder2.setNationaliteiten(nationaliteiten);

        ouder2.setGeboorte(new PersoonGeboorte());
        ouder2.getGeboorte().setLandGeboorte(nederland);

        Betrokkenheid betrokkenheid2 = new Betrokkenheid();
        betrokkenheid2.setIdentiteit(new BetrokkenheidIdentiteit());
        betrokkenheid2.getIdentiteit().setRol(SoortBetrokkenheid.OUDER);
        betrokkenheid2.getIdentiteit().setBetrokkene(ouder2);
        relatie.voegBetrokkenHeidToe(betrokkenheid2);

        return Arrays.asList(actie);
    }

    @Before
    public void init() {
        Map<SoortActie, List<? extends BedrijfsRegel>> regelsMap = new HashMap<SoortActie, List<? extends BedrijfsRegel>>();
        regelsMap.put(SoortActie.DUMMY, Arrays.asList(new VoornaamGewenst()));
        regelsMap.put(SoortActie.DUMMY_WAARDE, Arrays.asList(new OpaOmaArtikel()));
        ReflectionTestUtils.setField(bedrijfsRegelManager, "regelsMap", regelsMap);

        Nationaliteit nationaliteit = new Nationaliteit();
        nationaliteit.setIdentiteit(new NationaliteitIdentiteit());
        nationaliteit.getIdentiteit().setNaam(new NaamEnumeratiewaarde());
        nationaliteit.getIdentiteit().getNaam().setWaarde("Nederlandse");

        Persoon persoon1 = new Persoon();
        persoon1.setIdentiteit(new PersoonIdentiteit());
        persoon1.getIdentiteit().setSoort(SoortPersoon.INGESCHREVENE);
        persoon1.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon1.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer());
        persoon1.getIdentificatienummers().getBurgerservicenummer().setWaarde("123456789");
        persoon1.setGeboorte(new PersoonGeboorte());
        persoon1.getGeboorte().setLandGeboorte(new Land());
        persoon1.getGeboorte().getLandGeboorte().setIdentiteit(new LandIdentiteit());
        persoon1.getGeboorte().getLandGeboorte().getIdentiteit().setNaam(new NaamEnumeratiewaarde());
        persoon1.getGeboorte().getLandGeboorte().getIdentiteit().getNaam().setWaarde("Nederland");

        PersoonNationaliteit persoonNationaliteit1 = new PersoonNationaliteit();
        persoonNationaliteit1.setIdentiteit(new PersoonNationaliteitIdentiteit());
        persoonNationaliteit1.getIdentiteit().setNationaliteit(nationaliteit);
        persoonNationaliteit1.getIdentiteit().setPersoon(persoon1);

        Set<PersoonNationaliteit> nationaliteiten1 = new HashSet<PersoonNationaliteit>();
        nationaliteiten1.add(persoonNationaliteit1);
        persoon1.setNationaliteiten(nationaliteiten1);

        Persoon persoon2 = new Persoon();
        persoon2.setIdentiteit(new PersoonIdentiteit());
        persoon2.getIdentiteit().setSoort(SoortPersoon.INGESCHREVENE);
        persoon2.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon2.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer());
        persoon2.getIdentificatienummers().getBurgerservicenummer().setWaarde("234567890");
        persoon2.setGeboorte(new PersoonGeboorte());
        persoon2.getGeboorte().setLandGeboorte(new Land());
        persoon2.getGeboorte().getLandGeboorte().setIdentiteit(new LandIdentiteit());
        persoon2.getGeboorte().getLandGeboorte().getIdentiteit().setNaam(new NaamEnumeratiewaarde());
        persoon2.getGeboorte().getLandGeboorte().getIdentiteit().getNaam().setWaarde("Nederland");

        PersoonNationaliteit persoonNationaliteit2 = new PersoonNationaliteit();
        persoonNationaliteit2.setIdentiteit(new PersoonNationaliteitIdentiteit());
        persoonNationaliteit2.getIdentiteit().setNationaliteit(nationaliteit);
        persoonNationaliteit2.getIdentiteit().setPersoon(persoon2);

        Set<PersoonNationaliteit> nationaliteiten2 = new HashSet<PersoonNationaliteit>();
        nationaliteiten2.add(persoonNationaliteit2);
        persoon2.setNationaliteiten(nationaliteiten2);

        MockitoAnnotations.initMocks(this);
        //Mockito.when(persoonDaoMock.haalPersoonOpBasisVanBsn("123456789")).thenReturn(persoon1);
        //Mockito.when(persoonDaoMock.haalPersoonOpBasisVanBsn("234567890")).thenReturn(persoon2);

        ReflectionTestUtils.setField(berichtVerwerker, "persoonLGMRepositoryMock", persoonLGMRepositoryMock);
    }

}
