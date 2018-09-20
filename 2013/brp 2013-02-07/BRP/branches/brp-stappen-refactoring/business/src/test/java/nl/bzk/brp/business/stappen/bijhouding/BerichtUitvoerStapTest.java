/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bijhouding;

import java.util.ArrayList;
import java.util.Arrays;

import nl.bzk.brp.business.actie.AbstractActieUitvoerder;
import nl.bzk.brp.business.actie.ActieFactory;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.stappen.AbstractStapTest;
import nl.bzk.brp.dataaccess.repository.AdministratieveHandelingRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingErkenningOngeborenVruchtBericht;
import nl.bzk.brp.model.bericht.kern.HandelingRegistratieIntergemeentelijkeVerhuizingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BerichtUitvoerStapTest extends AbstractStapTest {

    private static final String VALIDE_BSN = "123456782";

    @Mock
    private ActieFactory actieFactory;

    @Mock
    private AbstractActieUitvoerder actieUitvoerder;

    @Mock
    private AdministratieveHandelingRepository administratieveHandelingRepository;

    @Mock
    private AdministratieveHandelingModel administratieveHandelingModel;

    @InjectMocks
    private BerichtUitvoerStap berichtUitvoerStap = new BerichtUitvoerStap();


    @Before
    public void init() {
        when(administratieveHandelingRepository.opslaanNieuwAdministratieveHandeling(isA(AdministratieveHandelingBericht.class))).thenReturn(administratieveHandelingModel);
        when(administratieveHandelingModel.getTechnischeSleutel()).thenReturn("1");
    }

    @Test
    public void testBerichtZonderActies() {
        AbstractBijhoudingsBericht bericht = maakNieuwBericht();
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        boolean stapResultaat =
                berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBerichtMetNullAlsActies() {
        AbstractBijhoudingsBericht bericht = maakNieuwBericht(new ActieBericht[]{});
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(new ArrayList<Melding>());
        boolean stapResultaat =
                berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBerichtMetActieZonderMeldingen() {
        AdministratieveHandelingModel administratieveHandelingModel = new AdministratieveHandelingModel(
                new HandelingRegistratieIntergemeentelijkeVerhuizingBericht());
        ActieBericht actie = new ActieRegistratieAdresBericht();
        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(VALIDE_BSN));
        PersoonAdresBericht nieuwAdres = new PersoonAdresBericht();
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(nieuwAdres);
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));

        AbstractBijhoudingsBericht bericht = maakNieuwBericht(actie);

        when(actieFactory.getActieUitvoerder(actie)).thenReturn(actieUitvoerder);
        when(actieUitvoerder.voerUit(actie, bouwBerichtContext(), administratieveHandelingModel))
                .thenReturn(null);

        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        boolean stapResultaat =
                berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBerichtMetMeerdereActiesEnMeldingen() {
        AdministratieveHandelingModel administratieveHandelingModel = new AdministratieveHandelingModel(
            new HandelingRegistratieIntergemeentelijkeVerhuizingBericht());
        PersoonBericht persoon = new PersoonBericht();
        PersoonAdresBericht nieuwAdres = new PersoonAdresBericht();
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(nieuwAdres);
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(VALIDE_BSN));
        ActieBericht actie1 = new ActieRegistratieAdresBericht();
        actie1.setRootObjecten(Arrays.asList((RootObject) persoon));
        ActieBericht actie2 = new ActieRegistratieAdresBericht();
        actie2.setRootObjecten(Arrays.asList((RootObject) persoon));
        AbstractBijhoudingsBericht bericht = maakNieuwBericht(actie1, actie2);

        BerichtContext berichtContext = bouwBerichtContext();
        when(administratieveHandelingRepository.opslaanNieuwAdministratieveHandeling(isA(AdministratieveHandelingBericht.class))).thenReturn(administratieveHandelingModel);
        when(actieFactory.getActieUitvoerder(actie1)).thenReturn(actieUitvoerder);
        when(actieFactory.getActieUitvoerder(actie2)).thenReturn(actieUitvoerder);
        when(actieUitvoerder.voerUit(actie1, berichtContext, administratieveHandelingModel)).thenReturn(
                Arrays.asList(new Melding(SoortMelding.INFORMATIE, MeldingCode.ALG0001)));
        when(actieUitvoerder.voerUit(actie2, berichtContext, administratieveHandelingModel)).thenReturn(
                Arrays.asList(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001), new Melding(
                        SoortMelding.FOUT, MeldingCode.ALG0001)));

        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(new ArrayList<Melding>());
        boolean stapResultaat =
                berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, berichtContext, resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertEquals(3, resultaat.getMeldingen().size());
    }

    @Test
    public void testBerichtZonderBekendeActieUitvoerder() {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(VALIDE_BSN));
        ActieBericht actie = new ActieRegistratieAdresBericht();
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        AbstractBijhoudingsBericht bericht = maakNieuwBericht(actie);

        when(actieFactory.getActieUitvoerder(actie)).thenReturn(null);

        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        boolean stapResultaat =
                berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
    }

    @Test(expected = IllegalStateException.class)
    public void testBerichtMetExceptieInUitvoering() {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(VALIDE_BSN));
        ActieBericht actie = new ActieRegistratieAdresBericht();
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        AbstractBijhoudingsBericht bericht = maakNieuwBericht(actie);

        when(actieFactory.getActieUitvoerder(actie)).thenThrow(new IllegalStateException("Test"));

        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);
    }

    /**
     * Instantieert een bericht met de opgegeven acties.
     *
     * @param acties de acties waaruit het bericht dient te bestaan.
     * @return een nieuw bericht met opgegeven acties.
     */
    private AbstractBijhoudingsBericht maakNieuwBericht(final ActieBericht... acties) {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht(null) {
        };
        if (acties == null) {
            //            bericht.setBrpActies(null);
        } else {
            bericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
            bericht.getAdministratieveHandeling().setActies(Arrays.asList(acties));
        }
        return bericht;
    }

}
