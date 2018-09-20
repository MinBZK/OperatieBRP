/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.levering.algemeen.service.PartijService;
import nl.bzk.brp.levering.business.toegang.populatie.LeveringinformatieService;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;

public class HaalLeveringsautorisatieEnPartijGegevensOpStapTest extends AbstractStappenTest {

    @Mock
    private LeveringinformatieService leveringinformatieService;

    @Mock
    private PartijService partijService;

    @InjectMocks
    private HaalLeveringsautorisatieEnPartijGegevensOpStap haalLeveringsautorisatieEnPartijGegevensOpStap =
        new HaalLeveringsautorisatieEnPartijGegevensOpStap();

    private int partijCode = 123;


    private Leveringinformatie leveringsautorisatie = mock(Leveringinformatie.class);

    private Partij partij = TestPartijBuilder.maker().metCode(partijCode).maak();

    @Before
    public final void setup() {
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metId(123).maak();
        maakBericht(123550394, leveringsautorisatie, partijCode, "AGV");

        when(leveringinformatieService.geefLeveringinformatie(Mockito.anyInt(),
            Mockito.anyInt(),
            Mockito.any(SoortDienst.class)))
            .thenReturn(this.leveringsautorisatie);

        when(partijService.vindPartijOpCode(partijCode)).thenReturn(this.partij);
    }

    @Test
    @Ignore
    public final void testVoerStapUitLeveringsautorisatieWordtOpgehaaldMetDiensten() {
        getBerichtContext().setLeveringsautorisatie(null);

        final boolean stapResultaat =
            haalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        assertTrue(stapResultaat);
        assertEquals(leveringsautorisatie, getBerichtContext().getLeveringsautorisatie());
    }

    @Test
    public final void testVoerStapUitPartijOngeldig() {
        getBerichtContext().setLeveringsautorisatie(null);
        when(partijService.vindPartijOpCode(partijCode))
            .thenThrow(NumberFormatException.class);

        final boolean stapResultaat =
            haalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        Assert.assertFalse(stapResultaat);
        Assert.assertEquals(1, getResultaat().getMeldingen().size());
        Assert.assertEquals(Regel.BRAL0220, getResultaat().getMeldingen().get(0).getRegel());
    }

    @Test
    public final void testVoerStapUitPartijNietBestaand() {
        getBerichtContext().setLeveringsautorisatie(null);
        when(partijService.vindPartijOpCode(partijCode))
            .thenThrow(EmptyResultDataAccessException.class);

        final boolean stapResultaat =
            haalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        Assert.assertFalse(stapResultaat);
        Assert.assertEquals(1, getResultaat().getMeldingen().size());
        Assert.assertEquals(Regel.BRAL0220, getResultaat().getMeldingen().get(0).getRegel());
    }

    @Test
    public final void testVoerStapUitLeveringinformatieIsNullStoptStap() {
        Mockito.when(leveringinformatieService
            .geefLeveringinformatie(Mockito.anyInt(),
                Mockito.anyInt(),
                Mockito.any(SoortDienst.class)))
            .thenReturn(new Leveringinformatie(null, null));

        final boolean stapResultaat =
            haalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        Assert.assertFalse(stapResultaat);
        Assert.assertEquals(1, getResultaat().getMeldingen().size());
        Assert.assertEquals(Regel.BRLV0029, getResultaat().getMeldingen().get(0).getRegel());
    }

    @Test
    public final void testVoerStapUitLeveringinformatieServiceGeeftExceptie() {
        Mockito.when(leveringinformatieService
            .geefLeveringinformatie(Mockito.anyInt(),
                Mockito.anyInt(),
                Mockito.any(SoortDienst.class)))
            .thenThrow(OnbekendeReferentieExceptie.class);

        final boolean stapResultaat =
            haalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        Assert.assertFalse(stapResultaat);
        Assert.assertEquals(1, getResultaat().getMeldingen().size());
        Assert.assertEquals(Regel.BRLV0007, getResultaat().getMeldingen().get(0).getRegel());
    }

    @Test
    @Ignore
    public final void testVoerStapUitZendendePartijWordtOpgehaald() {
        getOnderwerp().getStuurgegevens().setZendendePartij(null);

        final boolean stapResultaat =
            haalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        assertTrue(stapResultaat);
        assertEquals(partij, getOnderwerp().getStuurgegevens().getZendendePartij().getWaarde());
    }

}
