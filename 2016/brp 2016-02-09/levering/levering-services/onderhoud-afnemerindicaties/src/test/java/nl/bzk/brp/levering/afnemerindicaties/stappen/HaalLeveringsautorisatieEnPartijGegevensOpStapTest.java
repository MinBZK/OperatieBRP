/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen;

import java.util.ArrayList;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.levering.algemeen.service.PartijService;
import nl.bzk.brp.levering.business.toegang.populatie.LeveringinformatieService;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.bericht.autaut.PersoonAfnemerindicatieBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;


public class HaalLeveringsautorisatieEnPartijGegevensOpStapTest extends AbstractStappenTest {

    @InjectMocks
    private HaalLeveringsautorisatieEnPartijGegevensOpStap haalLeveringsautorisatieEnPartijGegevensOpStap =
        new HaalLeveringsautorisatieEnPartijGegevensOpStap();

    @Mock
    private PartijService partijService;

    @Mock
    private LeveringinformatieService leveringinformatieService;

    private int    partijCode = 123;
    private Partij partij     = TestPartijBuilder.maker().metCode(partijCode).maak();
    private Leveringinformatie leveringinformatie;

    @Before
    public final void setup() {
        Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metNaam("TestLeveringsautorisatie").metPopulatiebeperking
            ("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau
                .GEEN_BEPERKINGEN).metDatumIngang(DatumAttribuut.gisteren()).maak();

        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
            (leveringsautorisatie).maak();

        leveringinformatie = new Leveringinformatie(toegangLeveringsautorisatie, null);

        maakBericht(123550394, leveringinformatie, partijCode, "AGV",
            SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
            new DatumEvtDeelsOnbekendAttribuut(20130101));

        Mockito.when(partijService.vindPartijOpCode(Mockito.anyInt())).thenReturn(partij);
        Mockito.when(leveringinformatieService
            .geefLeveringinformatie(Mockito.anyInt(),
                Mockito.anyInt(),
                Mockito.any(SoortDienst.class))).thenReturn(leveringinformatie);
    }

    @Test
    public final void testVoerStapUitLeveringsautorisatieWordtOpgehaaldMetDiensten() {
        final boolean stapResultaat =
            haalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        Assert.assertTrue(stapResultaat);
        Assert.assertEquals(leveringinformatie.getToegangLeveringsautorisatie().getLeveringsautorisatie(), getBerichtContext().getLeveringautorisatie());
    }


    @Test
    public final void testVoerStapUitLeveringinformatieServiceGeeftExceptieVoorLeveringsautorisatieNaam() {
        Mockito.when(leveringinformatieService
            .geefLeveringinformatie(Mockito.anyInt(),
                Mockito.anyInt(),
                Mockito.any(SoortDienst.class)))
            .thenThrow(new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie
                .ReferentieVeld.LEVERINGSAUTORISATIEID, "test1",
                new EmptyResultDataAccessException("melding1!", 1)));

        final boolean stapResultaat =
            haalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        Assert.assertFalse(stapResultaat);
        Assert.assertEquals(1, getResultaat().getMeldingen().size());
        Assert.assertEquals(Regel.BRLV0007, getResultaat().getMeldingen().get(0).getRegel());
    }

    @Test
    public final void testVoerStapUitLeveringinformatieServiceGeeftExceptieVoorPartijCode() {
        Mockito.when(leveringinformatieService
            .geefLeveringinformatie(Mockito.anyInt(),
                Mockito.anyInt(),
                Mockito.any(SoortDienst.class)))
            .thenThrow(new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie
                .ReferentieVeld.PARTIJCODE, "test2",
                new EmptyResultDataAccessException("melding2!", 1)));

        final boolean stapResultaat =
            haalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        Assert.assertFalse(stapResultaat);
        Assert.assertEquals(1, getResultaat().getMeldingen().size());
        Assert.assertEquals(Regel.BRAL0220, getResultaat().getMeldingen().get(0).getRegel());
    }

    @Test
    public final void testVoerStapUitLeveringinformatieServiceGeeftExceptieVoorNietOndersteundObject() {
        Mockito.when(leveringinformatieService
            .geefLeveringinformatie(Mockito.anyInt(),
                Mockito.anyInt(),
                Mockito.any(SoortDienst.class)))
            .thenThrow(new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie
                .ReferentieVeld.AANGEVERADRESHOUDING, "test3",
                new EmptyResultDataAccessException("melding3!", 1)));

        final boolean stapResultaat =
            haalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        Assert.assertFalse(stapResultaat);
        Assert.assertEquals(0, getResultaat().getMeldingen().size());
    }

    @Test
    public final void testVoerStapUitRootobjectIsLeeg() {
        final ActieBericht actieBericht = getOnderwerp().getAdministratieveHandeling().getHoofdActie();
        actieBericht.setRootObject(null);

        final boolean stapResultaat =
            haalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        Assert.assertFalse(stapResultaat);
    }

    @Test
    public final void testVoerStapUitRootobjectIsFoutief() {
        final ActieBericht actieBericht = getOnderwerp().getAdministratieveHandeling().getHoofdActie();
        actieBericht.setRootObject(new FamilierechtelijkeBetrekkingBericht());

        final boolean stapResultaat =
            haalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        Assert.assertFalse(stapResultaat);
    }

    @Test
    public final void testVoerStapUitLeveringsautorisatieCacheWordtOpgezocht() {
        getBerichtContext().setLeveringinformatie(null);

        final boolean stapResultaat =
            haalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        Assert.assertTrue(stapResultaat);
    }

    @Test
    public final void testVoerStapUitZonderActies() {
        getOnderwerp().getAdministratieveHandeling().setActies(null);

        final boolean stapResultaat =
            haalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        Assert.assertFalse(stapResultaat);
    }

    @Test
    public final void testVoerStapUitLegeLijstActies() {
        getOnderwerp().getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());

        final boolean stapResultaat =
            haalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        Assert.assertFalse(stapResultaat);
    }

    @Test
    public final void testVoerStapUitZendendePartijWordtOpgehaald() {
        getOnderwerp().getStuurgegevens().setZendendePartij(null);

        final boolean stapResultaat =
            haalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        Assert.assertTrue(stapResultaat);
        Assert.assertEquals(partij, getOnderwerp().getStuurgegevens().getZendendePartij().getWaarde());
    }

    @Test
    public final void testVoerStapUitLegeLijstAfnemerindicaties() {
        maakBericht(12348945, leveringinformatie, 31223, "TestSysteem1",
            SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
            new DatumEvtDeelsOnbekendAttribuut(20130101));
        final RegistreerAfnemerindicatieBericht onderwerp = getOnderwerp();
        final PersoonBericht persoon =
            (PersoonBericht) onderwerp.getAdministratieveHandeling().getHoofdActie().getRootObject();
        persoon.setAfnemerindicaties(new ArrayList<PersoonAfnemerindicatieBericht>());

        final boolean stapResultaat =
            haalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());
        Assert.assertFalse(stapResultaat);
    }

    @Test
    public final void testVoerStapUitGeenAfnemerindicatie() {
        maakBericht(12348945, leveringinformatie, 31223, "TestSysteem2",
            SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
            new DatumEvtDeelsOnbekendAttribuut(20130101));
        final RegistreerAfnemerindicatieBericht onderwerp = getOnderwerp();
        final PersoonBericht persoon =
            (PersoonBericht) onderwerp.getAdministratieveHandeling().getHoofdActie().getRootObject();
        persoon.setAfnemerindicaties(null);

        final boolean stapResultaat =
            haalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());
        Assert.assertFalse(stapResultaat);
    }

    @Test
    public final void testVoerStapUitMetDiensten() {
        maakBericht(12348945, leveringinformatie, 31223, "TestSysteem3",
            SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
            new DatumEvtDeelsOnbekendAttribuut(20130101));

        final boolean stapResultaat =
            haalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());
        Assert.assertTrue(stapResultaat);
    }
}
