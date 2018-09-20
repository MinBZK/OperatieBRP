/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.business.actie.validatie.HuwelijkPartnerschapActieValidator;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.handlers.AbstractStapTest;
import nl.bzk.brp.dataaccess.repository.ActieRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class HuwelijkPartnerschapUitvoerderTest extends AbstractStapTest {

    @Mock
    private PersoonRepository persoonRepository;

    @Mock
    private RelatieRepository relatieRepository;

    @Mock
    private HuwelijkPartnerschapActieValidator huwelijkPartnerschapActieValidator;

    @Mock
    private ActieRepository                    actieRepository;

    @InjectMocks
    private AbstractActieUitvoerder uitvoerder = new HuwelijkPartnerschapUitvoerder();


    @Before
    public void init() {
        initMocks(this);

        when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("234567891"))).thenReturn(
                bouwPersoon(1, "234567891"));
        when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("345678912"))).thenReturn(
                bouwPersoon(2, "345678912"));

        when(actieRepository.opslaanNieuwActie(Matchers.any(ActieModel.class))).thenReturn(
                new ActieModel(new ActieBericht()));
    }

    @Test
    public void testNormaleActie() {
        BerichtContext bc = bouwBerichtContext();
        List<Melding> meldingen = uitvoerder.voerUit(getHuwelijkActie(), bc);
        Assert.assertNull(meldingen);
        // Verifieer opslag actie
        verify(actieRepository, times(1)).opslaanNieuwActie(Matchers.any(ActieModel.class));
        // verifieer opslag persoon
        verify(persoonRepository, times(2)).findByBurgerservicenummer(
                Matchers.any(Burgerservicenummer.class));

        ArgumentCaptor<RelatieModel> argument = ArgumentCaptor.forClass(RelatieModel.class);

        verify(relatieRepository, times(1)).opslaanNieuweRelatie(argument.capture(),
                Matchers.any(ActieModel.class), Matchers.eq(new Datum(20120325)));

        Assert.assertEquals(2, argument.getValue().getBetrokkenheden().size());

        Assert.assertEquals(2, bc.getHoofdPersonen().size());
        Assert.assertEquals(0, bc.getBijPersonen().size());

        List<String> bsnNrs = Arrays.asList("234567891", "345678912");
        Assert.assertTrue(bsnNrs.contains(bc.getHoofdPersonen().get(0).getIdentificatienummers()
                .getBurgerservicenummer().getWaarde()));
        Assert.assertTrue(bsnNrs.contains(bc.getHoofdPersonen().get(1).getIdentificatienummers()
                .getBurgerservicenummer().getWaarde()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUitvoerZonderActie() {
        uitvoerder.voerUit(null, bouwBerichtContext());
    }

    @Test
    public void testUitvoerMetActieMetNullVoorRootObjecten() {
        ActieBericht actie = new ActieBericht();
        actie.setRootObjecten(null);

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertEquals(1, meldingen.size());
        Assert.assertSame(MeldingCode.ALG0002, meldingen.get(0).getCode());
    }

    @Test
    public void testUitvoerMetActieZonderRootObjecten() {
        ActieBericht actie = new ActieBericht();
        actie.setRootObjecten(new ArrayList<RootObject>());

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertEquals(1, meldingen.size());
        Assert.assertSame(MeldingCode.ALG0002, meldingen.get(0).getCode());
    }

    @Test
    public void testActieZonderBetrokkenheden() {
        ActieBericht actie = getHuwelijkActie();
        ((RelatieBericht) actie.getRootObjecten().get(0)).setBetrokkenheden(null);

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
    }

    @Test
    public void testActieMetOnbekendePartner() {
        ActieBericht actie = getHuwelijkActie();
        ((RelatieBericht) actie.getRootObjecten().get(0)).getBetrokkenheden().add(
                bouwPartnerBetrokkenheid("456789123"));

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
    }

    // Tijdelijke test, zonder bsn is out of scope op dit moment
    @Test
    public void testActieMetPartnerZonderBsn() {
        ActieBericht actie = getHuwelijkActie();
        ((RelatieBericht) actie.getRootObjecten().get(0)).getBetrokkenheden().add(bouwPartnerBetrokkenheid(null));

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals("Personen zonder BSN is op het moment nog niet ondersteund", meldingen.get(0)
                .getOmschrijving());
    }

    @Test
    public void testUitvoerMetActieMetAndereRootObjecten() {
        List<RootObject> rootObjecten = new ArrayList<RootObject>();
        rootObjecten.add(mock(RootObject.class));
        rootObjecten.add(mock(RootObject.class));

        ActieBericht actie = new ActieBericht();
        actie.setRootObjecten(rootObjecten);

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertEquals(1, meldingen.size());
        Assert.assertSame(MeldingCode.ALG0002, meldingen.get(0).getCode());
    }

    @Test
    public void testUitvoerMetActieMetPersoonEnAndereRootObjecten() {
        ActieBericht actie = getHuwelijkActie();
        actie.getRootObjecten().add(0, mock(RootObject.class));
        actie.getRootObjecten().add(mock(RootObject.class));

        Assert.assertNull(uitvoerder.voerUit(actie, bouwBerichtContext()));
    }

    /**
     * Retourneert een standaard 'huwelijk' actie met een leeg persoon als root object.
     *
     * @return een standaard 'huwelijk' actie met een leeg persoon als root object.
     */
    private ActieBericht getHuwelijkActie() {
        ActieBericht actie = new ActieBericht();
        actie.setSoort(SoortActie.HUWELIJK);
        actie.setDatumAanvangGeldigheid(new Datum(20120325));

        RelatieStandaardGroepBericht gegevens = new RelatieStandaardGroepBericht();

        RelatieBericht relatie = new RelatieBericht();
        relatie.setGegevens(gegevens);
        relatie.setSoort(SoortRelatie.HUWELIJK);

        List<RootObject> rootObjecten = new ArrayList<RootObject>();
        rootObjecten.add(relatie);
        actie.setRootObjecten(rootObjecten);

        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        relatie.getBetrokkenheden().add(bouwPartnerBetrokkenheid("234567891"));
        relatie.getBetrokkenheden().add(bouwPartnerBetrokkenheid("345678912"));
        return actie;
    }

    /**
     * Bouwt en retourneert een {@link BetrokkenheidBericht} instantie met daarin de minimale velden ingevuld voor een
     * ouder zoals benodigd voor een eerste inschrijving.
     */
    private BetrokkenheidBericht bouwPartnerBetrokkenheid(final String bsn) {
        BetrokkenheidBericht betrokkenheid = new BetrokkenheidBericht();
        betrokkenheid.setRol(SoortBetrokkenheid.PARTNER);
        betrokkenheid.setBetrokkene(bouwPersoonBericht(bsn));

        return betrokkenheid;
    }

    private PersoonBericht bouwPersoonBericht(final String bsn) {
        PersoonBericht persoon = new PersoonBericht();

        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(bsn));
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
        ReflectionTestUtils.setField(persoon, "id", id);

        return persoon;
    }
}
