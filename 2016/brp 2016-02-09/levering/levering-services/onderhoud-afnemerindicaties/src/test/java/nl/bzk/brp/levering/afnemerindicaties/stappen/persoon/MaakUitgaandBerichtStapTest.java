/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import nl.bzk.brp.levering.afnemerindicaties.stappen.AbstractStappenTest;
import nl.bzk.brp.levering.business.bericht.MarshallService;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.levering.VolledigBericht;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MaakUitgaandBerichtStapTest extends AbstractStappenTest {

    @InjectMocks
    private MaakUitgaandBerichtStap maakUitgaandBerichtStap = new MaakUitgaandBerichtStap();

    @Mock
    private MarshallService marshallService;

    @Test
    public final void testVoerStapUitSuccesvol() throws JiBXException {
        maakBericht(123550394, maakDummyLeveringinformatie(), 123, "AGV1", SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
            new DatumEvtDeelsOnbekendAttribuut(20130101));
        final String verwachtBericht = "een bericht";
        final VolledigBericht volledigBericht = mock(VolledigBericht.class);
        getBerichtContext().setVolledigBericht(volledigBericht);
        when(marshallService.maakBericht(any(VolledigBericht.class))).thenReturn(verwachtBericht);

        final boolean stapResultaat =
            maakUitgaandBerichtStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        verify(marshallService).maakBericht(volledigBericht);
        Assert.assertEquals(verwachtBericht, getBerichtContext().getXmlBericht());
        Assert.assertTrue(stapResultaat);
    }

    @Test
    public final void testVoerStapUitMetExceptie() throws JiBXException {
        maakBericht(123550394, maakDummyLeveringinformatie(), 123, "AGV2", SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
                new DatumEvtDeelsOnbekendAttribuut(20130101));
        Mockito.when(marshallService.maakBericht(any(VolledigBericht.class))).thenThrow(JiBXException.class);

        final boolean stapResultaat =
            maakUitgaandBerichtStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        Assert.assertFalse(stapResultaat);
    }
}
