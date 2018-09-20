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
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.business.stappen.AbstractStapTest;
import nl.bzk.brp.dataaccess.repository.ActieRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieHuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.HandelingSluitingHuwelijkNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import org.springframework.test.util.ReflectionTestUtils;


public class HuwelijkPartnerschapUitvoerderTest extends AbstractStapTest {

    @Mock
    private PersoonRepository persoonRepository;

    @Mock
    private RelatieRepository relatieRepository;

    @Mock
    private HuwelijkPartnerschapActieValidator huwelijkPartnerschapActieValidator;

    @Mock
    private ActieRepository actieRepository;

    @InjectMocks
    private final AbstractActieUitvoerder uitvoerder = new HuwelijkPartnerschapUitvoerder();

    private AdministratieveHandelingModel administratieveHandelingModel;


    @Before
    public void init() {
        initMocks(this);

        administratieveHandelingModel =
            new AdministratieveHandelingModel(new HandelingSluitingHuwelijkNederlandBericht());
        when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("234567891"))).thenReturn(
            bouwPersoon(1, "234567891"));
        when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("345678912"))).thenReturn(
            bouwPersoon(2, "345678912"));

        when(actieRepository.opslaanNieuwActie(Matchers.any(ActieModel.class))).thenReturn(
            new ActieModel(new ActieRegistratieHuwelijkBericht(), null));
    }

    @Test
    public void testNormaleActie() {
        BerichtContext bc = bouwBerichtContext();
        List<Melding> meldingen = uitvoerder.voerUit(getHuwelijkActie(), bc, administratieveHandelingModel);
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

        List<String> bsnNrs = Arrays.asList("234567891", "345678912");
        Assert.assertTrue(bsnNrs.contains(bc.getHoofdPersonen().get(0).getIdentificatienummers()
                                            .getBurgerservicenummer().toString()));
        Assert.assertTrue(bsnNrs.contains(bc.getHoofdPersonen().get(1).getIdentificatienummers()
                                            .getBurgerservicenummer().toString()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUitvoerZonderActie() {
        uitvoerder.voerUit(null, bouwBerichtContext(), administratieveHandelingModel);
    }

    @Test
    public void testUitvoerMetActieMetNullVoorRootObjecten() {
        ActieBericht actie = new ActieRegistratieHuwelijkBericht();
        actie.setRootObjecten(null);

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext(), administratieveHandelingModel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertSame(MeldingCode.ALG0002, meldingen.get(0).getCode());
    }

    @Test
    public void testUitvoerMetActieZonderRootObjecten() {
        ActieBericht actie = new ActieRegistratieHuwelijkBericht();
        actie.setRootObjecten(new ArrayList<RootObject>());

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext(), administratieveHandelingModel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertSame(MeldingCode.ALG0002, meldingen.get(0).getCode());
    }

    @Test
    public void testActieZonderBetrokkenheden() {
        ActieBericht actie = getHuwelijkActie();
        ((RelatieBericht) actie.getRootObjecten().get(0)).setBetrokkenheden(null);

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext(), administratieveHandelingModel);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
    }

    @Test
    public void testActieMetOnbekendePartner() {
        ActieBericht actie = getHuwelijkActie();
        ((RelatieBericht) actie.getRootObjecten().get(0)).getBetrokkenheden().add(
            bouwPartnerBetrokkenheid("456789123"));

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext(), administratieveHandelingModel);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
    }

    // Tijdelijke test, zonder bsn is out of scope op dit moment
    @Test
    public void testActieMetPartnerZonderBsn() {
        ActieBericht actie = getHuwelijkActie();
        ((RelatieBericht) actie.getRootObjecten().get(0)).getBetrokkenheden().add(bouwPartnerBetrokkenheid(null));

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext(), administratieveHandelingModel);
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

        ActieBericht actie = new ActieRegistratieHuwelijkBericht();
        actie.setRootObjecten(rootObjecten);

        List<Melding> meldingen = uitvoerder.voerUit(actie, bouwBerichtContext(), administratieveHandelingModel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertSame(MeldingCode.ALG0002, meldingen.get(0).getCode());
    }

    @Test
    public void testUitvoerMetActieMetPersoonEnAndereRootObjecten() {
        ActieBericht actie = getHuwelijkActie();
        actie.getRootObjecten().add(0, mock(RootObject.class));
        actie.getRootObjecten().add(mock(RootObject.class));

        Assert.assertNull(uitvoerder.voerUit(actie, bouwBerichtContext(), administratieveHandelingModel));
    }

    /**
     * Retourneert een standaard 'huwelijk' actie met een leeg persoon als root object.
     *
     * @return een standaard 'huwelijk' actie met een leeg persoon als root object.
     */
    private ActieBericht getHuwelijkActie() {
        ActieBericht actie = new ActieRegistratieHuwelijkBericht();
        actie.setDatumAanvangGeldigheid(new Datum(20120325));


        HuwelijkBericht relatie = new HuwelijkBericht();

        List<RootObject> rootObjecten = new ArrayList<RootObject>();
        rootObjecten.add(relatie);
        actie.setRootObjecten(rootObjecten);

        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        relatie.getBetrokkenheden().add(bouwPartnerBetrokkenheid("234567891"));
        relatie.getBetrokkenheden().add(bouwPartnerBetrokkenheid("345678912"));

        AdministratieveHandelingBericht admin = new HandelingSluitingHuwelijkNederlandBericht();
        admin.setActies(Arrays.asList(actie));

        actie.setAdministratieveHandeling(admin);

        return actie;
    }

    /**
     * Bouwt en retourneert een {@link BetrokkenheidBericht} instantie met daarin de minimale velden ingevuld voor een
     * ouder zoals benodigd voor een eerste inschrijving.
     */
    private PartnerBericht bouwPartnerBetrokkenheid(final String bsn) {
        PartnerBericht betrokkenheid = new PartnerBericht();
        betrokkenheid.setPersoon(bouwPersoonBericht(bsn));

        return betrokkenheid;
    }

    private PersoonBericht bouwPersoonBericht(final String bsn) {
        PersoonBericht persoon = new PersoonBericht();

        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        if (StringUtils.isNotBlank(bsn)) {
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
