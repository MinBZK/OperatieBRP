/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.binding.BRPBericht;
import nl.bzk.brp.binding.BerichtResultaat;
import nl.bzk.brp.binding.Melding;
import nl.bzk.brp.binding.MeldingCode;
import nl.bzk.brp.binding.ResultaatCode;
import nl.bzk.brp.binding.SoortMelding;
import nl.bzk.brp.business.actie.ActieFactory;
import nl.bzk.brp.business.actie.ActieUitvoerder;
import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegelManager;
import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegelManagerImpl;
import nl.bzk.brp.business.bedrijfsregels.impl.MR0502;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.SoortActie;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de bericht verwerker.
 */
@RunWith(MockitoJUnitRunner.class)
public class BerichtVerwerkerTest {

    private BerichtVerwerker     berichtVerwerker;

    @Mock
    private ActieFactory         actieFactory;

    @Mock
    private ActieUitvoerder      actieUitvoerder;

    @Mock
    private PersoonRepository    persoonRepository;

    @Mock
    private PersoonAdresRepository persoonAdresRepository;

    @Mock
    private BedrijfsRegelManager bedrijfsRegelManager;

    private PersistentPersoon    huidigeSituatie;

    @Before
    public void init() {
        berichtVerwerker = new BerichtVerwerkerImpl();
        HashMap<SoortActie, List<? extends BedrijfsRegel>> bedrijfsRegelsPerSoortActie =
                new HashMap<SoortActie, List<? extends BedrijfsRegel>>();
        List<BedrijfsRegel> regelsVerhuizing = new ArrayList<BedrijfsRegel>();
        MR0502 mr0502 = new MR0502();
        ReflectionTestUtils.setField(mr0502, "persoonAdresRepository", persoonAdresRepository);
        regelsVerhuizing.add(mr0502);
        bedrijfsRegelsPerSoortActie.put(SoortActie.VERHUIZING, regelsVerhuizing);
        bedrijfsRegelManager = new BedrijfsRegelManagerImpl(bedrijfsRegelsPerSoortActie);
        huidigeSituatie = new PersistentPersoon();

        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.anyString())).thenReturn(huidigeSituatie);
        Mockito.when(persoonAdresRepository.isIemandIngeschrevenOpAdres(Matchers.any(PersoonAdres.class)))
                .thenReturn(false);

        ReflectionTestUtils.setField(berichtVerwerker, "actieFactory", actieFactory);
        ReflectionTestUtils.setField(berichtVerwerker, "persoonRepository", persoonRepository);
        ReflectionTestUtils.setField(berichtVerwerker, "bedrijfsRegelManager", bedrijfsRegelManager);
    }

    @Test
    public void testBerichtZonderActies() {
        BRPBericht bericht = maakNieuwBericht();
        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(bericht);

        Assert.assertEquals(ResultaatCode.GOED, resultaat.getResultaat());
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBerichtMetNullAlsActies() {
        BRPBericht bericht = maakNieuwBericht();
        bericht.setBrpActies(null);
        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(bericht);

        Assert.assertEquals(ResultaatCode.GOED, resultaat.getResultaat());
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBerichtMetActieZonderMeldingen() {
        BRPActie actie = new BRPActie();
        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer("123456782");
        PersoonAdres nieuwAdres = new PersoonAdres();
        persoon.setAdressen(new HashSet<PersoonAdres>());
        persoon.getAdressen().add(nieuwAdres);
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        actie.setSoort(SoortActie.VERHUIZING);

        BRPBericht bericht = maakNieuwBericht(actie);

        Mockito.when(actieFactory.getActieUitvoerder(actie)).thenReturn(actieUitvoerder);
        Mockito.when(actieUitvoerder.voerUit(actie)).thenReturn(null);

        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(bericht);
        Assert.assertEquals(ResultaatCode.GOED, resultaat.getResultaat());
        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(0, resultaat.getMeldingen().size());
    }

    @Test
    public void testBerichtMetMeerdereActiesEnMeldingen() {
        Persoon persoon = new Persoon();
        PersoonAdres nieuwAdres = new PersoonAdres();
        persoon.setAdressen(new HashSet<PersoonAdres>());
        persoon.getAdressen().add(nieuwAdres);
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer("123456782");
        BRPActie actie1 = new BRPActie();
        actie1.setRootObjecten(Arrays.asList((RootObject) persoon));
        actie1.setSoort(SoortActie.VERHUIZING);
        BRPActie actie2 = new BRPActie();
        actie2.setRootObjecten(Arrays.asList((RootObject) persoon));
        actie2.setSoort(SoortActie.VERHUIZING);
        BRPBericht bericht = maakNieuwBericht(actie1, actie2);

        Mockito.when(actieFactory.getActieUitvoerder(actie1)).thenReturn(actieUitvoerder);
        Mockito.when(actieFactory.getActieUitvoerder(actie2)).thenReturn(actieUitvoerder);
        Mockito.when(actieUitvoerder.voerUit(actie1)).thenReturn(
                Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.ALG0001)));
        Mockito.when(actieUitvoerder.voerUit(actie2)).thenReturn(
                Arrays.asList(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001), new Melding(
                        SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001)));

        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(bericht);
        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaat());
        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(3, resultaat.getMeldingen().size());
    }

    @Test
    public void testBerichtZonderBekendeActieUitvoerder() {
        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer("123456782");
        BRPActie actie = new BRPActie();
        actie.setSoort(SoortActie.VERHUIZING);
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        BRPBericht bericht = maakNieuwBericht(actie);

        Mockito.when(actieFactory.getActieUitvoerder(actie)).thenReturn(null);

        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(bericht);
        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaat());
        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
    }

    @Test
    public void testBerichtMetExceptieInUitvoering() {
        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer("123456782");
        BRPActie actie = new BRPActie();
        actie.setSoort(SoortActie.VERHUIZING);
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        BRPBericht bericht = maakNieuwBericht(actie);

        Mockito.when(actieFactory.getActieUitvoerder(actie)).thenThrow(new RuntimeException("Test"));

        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(bericht);
        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaat());
        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }


    /**
     * Tot nu toe gevalideerde velden: BSN, SoortAdres, Datum
     */
    @Test
    public void testBerichtMetOngeldigeBericht() {
        Land land = new Land();
        land.setLandcode("6030");

        PersoonIdentificatienummers pin = new PersoonIdentificatienummers();
        pin.setBurgerservicenummer("123456789");

        PersoonAdres persoonAdres = new PersoonAdres();
        persoonAdres.setDatumAanvangAdreshouding(10);
        persoonAdres.setLand(land);
        Set<PersoonAdres> adressen = new HashSet<PersoonAdres>();
        adressen.add(persoonAdres);

        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(pin);
        persoon.setAdressen(adressen);

        BRPActie actie = new BRPActie();
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        BRPBericht bericht = maakNieuwBericht(actie);

        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(bericht);
        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaat());
        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(3, resultaat.getMeldingen().size());

        Mockito.verify(actieFactory, Mockito.times(0)).getActieUitvoerder((BRPActie) Matchers.anyObject());

        Melding melding = zoekMeldingInResultaat(MeldingCode.BRAL0012, resultaat);
        Assert.assertNotNull(melding);
        Assert.assertEquals("Bsn nummer is ongeldig", melding.getOmschrijving());

        melding = zoekMeldingInResultaat(MeldingCode.BRAL2032, resultaat);
        Assert.assertNotNull(melding);
        Assert.assertEquals("Soort adres verplicht voor Nederlandse adressen", melding.getOmschrijving());

        melding = zoekMeldingInResultaat(MeldingCode.BRAL0102, resultaat);
        Assert.assertNotNull(melding);
        Assert.assertEquals("Datum ongeldig formaat", melding.getOmschrijving());
    }

    private Melding zoekMeldingInResultaat(final MeldingCode meldingCode, final BerichtResultaat resultaat) {
        Melding r = null;
        for (Melding m : resultaat.getMeldingen()) {
            if (meldingCode.equals(m.getCode())) {
                r =  m;
            }
        }

        return r;
    }

    /**
     * Instantieert een bericht met de opgegeven acties.
     *
     * @param acties de acties waaruit het bericht dient te bestaan.
     * @return een nieuw bericht met opgegeven acties.
     */
    private BRPBericht maakNieuwBericht(final BRPActie... acties) {
        BRPBericht bericht = new BRPBericht();
        bericht.setBrpActies(Arrays.asList(acties));
        return bericht;
    }
}
