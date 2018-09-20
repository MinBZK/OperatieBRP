/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen.persoon;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import nl.bzk.brp.levering.business.bericht.MarshallService;
import nl.bzk.brp.levering.synchronisatie.stappen.AbstractStappenTest;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.levering.VolledigBericht;
import org.jibx.runtime.JiBXException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MaakUitgaandBerichtStapTest extends AbstractStappenTest {

    private static final Leveringsautorisatie LEVERINGSAUTORISATIE_NAAM = TestLeveringsautorisatieBuilder.maker().maak();
    private static final String               AGV                       = "AGV";

    @InjectMocks
    private MaakUitgaandBerichtStap maakUitgaandBerichtStap = new MaakUitgaandBerichtStap();

    @Mock
    private MarshallService marshallService;

    @Test
    public final void testVoerStapUitSuccesvol() throws JiBXException {
        maakBericht(123550394, LEVERINGSAUTORISATIE_NAAM, 123, AGV);
        final String verwachtBericht = "een bericht";
        final VolledigBericht volledigBericht = mock(VolledigBericht.class);
        getBerichtContext().setVolledigBericht(volledigBericht);
        when(marshallService.maakBericht(any(VolledigBericht.class))).thenReturn(verwachtBericht);

        final boolean stapResultaat =
            maakUitgaandBerichtStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        verify(marshallService).maakBericht(volledigBericht);
        assertEquals(verwachtBericht, getBerichtContext().getXmlBericht());
        assertTrue(stapResultaat);
    }

    @Test
    public final void testVoerStapUitMetExceptie() throws JiBXException {
        maakBericht(123550394, LEVERINGSAUTORISATIE_NAAM, 123, AGV);
        when(marshallService.maakBericht(any(VolledigBericht.class))).thenThrow(JiBXException.class);

        final boolean stapResultaat =
            maakUitgaandBerichtStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        assertFalse(stapResultaat);
    }

}
