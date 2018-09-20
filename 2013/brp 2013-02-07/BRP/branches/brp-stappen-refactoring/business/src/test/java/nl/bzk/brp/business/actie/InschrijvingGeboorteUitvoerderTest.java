/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.business.actie.validatie.GeboorteActieValidator;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.stappen.AbstractStapTest;
import nl.bzk.brp.dataaccess.exceptie.ObjectReedsBestaandExceptie;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ActieRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingInschrijvingDoorGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderlijkGezagGroepBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderschapGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


/** Unit test voor de {@link InschrijvingGeboorteUitvoerder} class. */
@RunWith(MockitoJUnitRunner.class)
public class InschrijvingGeboorteUitvoerderTest extends AbstractStapTest {

    @Mock
    private PersoonRepository      persoonRepository;
    @Mock
    private RelatieRepository      relatieRepository;
    @Mock
    private GeboorteActieValidator geboorteActieValidator;
    @Mock
    private ActieRepository        actieRepository;

    @InjectMocks
    private AbstractActieUitvoerder uitvoerder = new InschrijvingGeboorteUitvoerder();

    private AdministratieveHandelingModel administratieveHandelingModel;

    @Before
    public void init() {
        administratieveHandelingModel =
            new AdministratieveHandelingModel(new HandelingInschrijvingDoorGeboorteBericht());

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("234567891")))
               .thenReturn(bouwPersoon(1, "234567891"));
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("345678912")))
               .thenReturn(bouwPersoon(2, "345678912"));
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("456789123")))
               .thenReturn(null);

        Mockito.when(actieRepository.opslaanNieuwActie(Matchers.any(ActieModel.class))).thenReturn(
            new ActieModel(new ActieRegistratieGeboorteBericht(), null));

        PersoonModel pPersoon = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(pPersoon, "iD", 15);

        Mockito.when(
            persoonRepository.opslaanNieuwPersoon(Matchers.any(PersoonModel.class), Matchers.any(ActieModel.class),
                Matchers.any(Datum.class))).thenReturn(pPersoon);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testUitvoerZonderActie() {
        uitvoerder.voerUit(null, bouwBerichtContext(), administratieveHandelingModel);
    }

    @Test
    public void testUitvoerMetActieMetNullVoorRootObjecten() {
        ActieBericht actie = new ActieRegistratieGeboorteBericht();
        actie.setRootObjecten(null);

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext(), administratieveHandelingModel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertSame(MeldingCode.ALG0002, meldingen.get(0).getCode());
    }

    @Test
    public void testUitvoerMetActieZonderRootObjecten() {
        ActieBericht actie = new ActieRegistratieGeboorteBericht();
        actie.setRootObjecten(new ArrayList<RootObject>());

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext(), administratieveHandelingModel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertSame(MeldingCode.ALG0002, meldingen.get(0).getCode());
    }

    @Test
    public void testNormaleActie() {
        BerichtContext bc = bouwBerichtContext();
        List<Melding> meldingen = uitvoerder.voerUit(getInschrijvingGeboorteActie(), bc, administratieveHandelingModel);
        Assert.assertNull(meldingen);
        // Verifieer opslag actie
        Mockito.verify(actieRepository, Mockito.times(1)).opslaanNieuwActie(Matchers.any(ActieModel.class));
        // verifieer opslag persoon
        Mockito.verify(persoonRepository).opslaanNieuwPersoon(Matchers.any(PersoonModel.class),
            Matchers.any(ActieModel.class), Matchers.eq(new Datum(20120325)));
        // Opslag historie is verplaats naar Repository
        // verifieer opslag historie
        // Mockito.verify(persoonRepository).werkHistorieBij(Matchers.any(PersoonModel.class),
        // Matchers.any(PersoonModel.class), Matchers.any(ActieModel.class),
        // Matchers.eq(Integer.valueOf(20120325)));
        // Verifieer opslag relatie
        Mockito.verify(relatieRepository).opslaanNieuweRelatie(Matchers.any(RelatieModel.class),
            Matchers.any(ActieModel.class), Matchers.eq(new Datum(20120325)));
        // Opslag historie is verplaats naar Repository
        // Verifieer opslag historie voor relatie
        // Mockito.verify(relatieRepository).werkHistorieBij(Matchers.any(PersistentRelatie.class),
        // Matchers.any(ActieModel.class), Matchers.eq(Integer.valueOf(20120325)));
        // 1 hoofd persoon bijgehouden. 2 ouders
        Assert.assertEquals(1, bc.getHoofdPersonen().size());
        Assert.assertEquals(2, bc.getBijPersonen().size());

        Assert.assertEquals(Integer.valueOf("123456789"), bc.getHoofdPersonen().get(0).getIdentificatienummers()
                                                            .getBurgerservicenummer().getWaarde());
        Assert.assertTrue(Arrays.asList(Integer.valueOf("345678912"), Integer.valueOf("234567891")).contains(
            bc.getBijPersonen().get(0).getIdentificatienummers().getBurgerservicenummer().getWaarde()));
        Assert.assertTrue(Arrays.asList(Integer.valueOf("345678912"), Integer.valueOf("234567891")).contains(
            bc.getBijPersonen().get(1).getIdentificatienummers().getBurgerservicenummer().getWaarde()));

        Assert.assertEquals(Integer.valueOf(20120325), bc.getHoofdPersonen().get(0).getInschrijving()
                                                         .getDatumInschrijving().getWaarde());
        Assert.assertEquals(new Long(1L), bc.getHoofdPersonen().get(0).getInschrijving().getVersienummer()
                                            .getWaarde());
    }

    @Test
    public void testActieZonderBetrokkenheden() {
        ActieBericht actie = getInschrijvingGeboorteActie();
        ((RelatieBericht) actie.getRootObjecten().get(0)).setBetrokkenheden(null);

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext(), administratieveHandelingModel);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
    }

    @Test
    public void testActieMetOnbekendeOuder() {
        ActieBericht actie = getInschrijvingGeboorteActie();
        ((RelatieBericht) actie.getRootObjecten().get(0)).getBetrokkenheden().add(
            bouwOuderBetrokkenheid("456789123"));

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext(), administratieveHandelingModel);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
    }

    @Test
    public void testActieMetOuderZonderBsn() {
        ActieBericht actie = getInschrijvingGeboorteActie();
        ((RelatieBericht) actie.getRootObjecten().get(0)).getBetrokkenheden().add(bouwOuderBetrokkenheid(null));

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext(), administratieveHandelingModel);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
    }

    @Test
    public void testUitvoerMetActieMetAndereRootObjecten() {
        List<RootObject> rootObjecten = new ArrayList<RootObject>();
        rootObjecten.add(Mockito.mock(RootObject.class));
        rootObjecten.add(Mockito.mock(RootObject.class));

        ActieBericht actie = new ActieRegistratieGeboorteBericht();
        actie.setRootObjecten(rootObjecten);

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext(), administratieveHandelingModel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertSame(MeldingCode.ALG0002, meldingen.get(0).getCode());
    }

    @Test
    public void testUitvoerMetActieMetPersoonEnAndereRootObjecten() {
        ActieBericht actie = getInschrijvingGeboorteActie();
        actie.getRootObjecten().add(0, Mockito.mock(RootObject.class));
        actie.getRootObjecten().add(Mockito.mock(RootObject.class));

        Assert.assertNull(uitvoerder.voerUit(actie, bouwBerichtContext(), administratieveHandelingModel));
    }

    @Test
    public void testUitvoerMetBestaandBsn() {
        ActieBericht actie = getInschrijvingGeboorteActie();
        Mockito.doThrow(new ObjectReedsBestaandExceptie(ObjectReedsBestaandExceptie.ReferentieVeld.BSN, null, null))
               .when(persoonRepository)
               .opslaanNieuwPersoon(Matchers.any(PersoonModel.class), Matchers.any(ActieModel.class),
                   Matchers.any(Datum.class));

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext(), administratieveHandelingModel);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBER00121, meldingen.get(0).getCode());
    }

    @Test
    public void testUitvoerMetOnbekendeReferentie() {
        ActieBericht actie = getInschrijvingGeboorteActie();
        Mockito.doThrow(
            new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.LANDCODE, "abc", null))
               .when(persoonRepository)
               .opslaanNieuwPersoon(Matchers.any(PersoonModel.class), Matchers.any(ActieModel.class),
                   Matchers.any(Datum.class));

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext(), administratieveHandelingModel);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.REF0001, meldingen.get(0).getCode());
    }

    /**
     * Retourneert een standaard 'aangifte geboorte' actie met een leeg persoon als root object.
     *
     * @return een standaard 'aangifte geboorte' actie met een leeg persoon als root object.
     */
    private ActieBericht getInschrijvingGeboorteActie() {
        ActieBericht actie = new ActieRegistratieGeboorteBericht();
        actie.setDatumAanvangGeldigheid(new Datum(20120325));
        actie.setAdministratieveHandeling(new HandelingInschrijvingDoorGeboorteBericht());

        FamilierechtelijkeBetrekkingBericht relatie = new FamilierechtelijkeBetrekkingBericht();

        List<RootObject> rootObjecten = new ArrayList<RootObject>();
        rootObjecten.add(relatie);
        actie.setRootObjecten(rootObjecten);

        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        relatie.getBetrokkenheden().add(bouwKindBetrokkenheid("123456789"));
        relatie.getBetrokkenheden().add(bouwOuderBetrokkenheid("234567891"));
        relatie.getBetrokkenheden().add(bouwOuderBetrokkenheid("345678912"));

        AdministratieveHandelingBericht admin = new HandelingInschrijvingDoorGeboorteBericht();
        admin.setActies(Arrays.asList(actie));

        actie.setAdministratieveHandeling(admin);

        return actie;
    }


    /**
     * Bouwt en retourneert een {@link BetrokkenheidBericht} instantie met daarin de minimale velden ingevuld voor een
     * kind zoals benodigd voor een eerste inschrijving.
     */
    private KindBericht bouwKindBetrokkenheid(final String bsn) {
        KindBericht betrokkenheid = new KindBericht();
        betrokkenheid.setPersoon(bouwPersoonBericht(bsn));
        return betrokkenheid;
    }

    /**
     * Bouwt en retourneert een {@link BetrokkenheidBericht} instantie met daarin de minimale velden ingevuld voor een
     * ouder zoals benodigd voor een eerste inschrijving.
     */
    private BetrokkenheidBericht bouwOuderBetrokkenheid(final String bsn) {
        OuderOuderlijkGezagGroepBericht betrokkenheidOuderlijkGezag =
            new OuderOuderlijkGezagGroepBericht();
        OuderOuderschapGroepBericht betrokkenheidOuderschap = new OuderOuderschapGroepBericht();

        OuderBericht betrokkenheid = new OuderBericht();
        betrokkenheid.setOuderschap(betrokkenheidOuderschap);
        betrokkenheid.setOuderlijkGezag(betrokkenheidOuderlijkGezag);

        betrokkenheid.setPersoon(bouwPersoonBericht(bsn));
        return betrokkenheid;
    }

    private PersoonBericht bouwPersoonBericht(final String bsn) {
        PersoonBericht persoon = new PersoonBericht();

        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        if (bsn != null) {
            persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(bsn));
        }
        return persoon;
    }

    /**
     * Bouwt en retourneert een instantie van {@link PersoonModel} waarbij de id en bsn is gezet naar de opgegeven
     * waardes.
     */
    private PersoonModel bouwPersoon(final Integer id, final String bsn) {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoonBericht.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(bsn));

        PersoonModel persoon = new PersoonModel(persoonBericht);
        ReflectionTestUtils.setField(persoon, "iD", id);

        return persoon;
    }
}
