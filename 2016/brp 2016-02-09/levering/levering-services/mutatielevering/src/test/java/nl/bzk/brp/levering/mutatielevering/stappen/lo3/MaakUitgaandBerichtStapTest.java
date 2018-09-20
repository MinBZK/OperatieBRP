/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.lo3;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.business.stappen.AbstractStap;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.lo3.bericht.Bericht;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * Test voor {@link nl.bzk.brp.levering.mutatielevering.stappen.lo3.MaakUitgaandBerichtStap}
 */
@RunWith(MockitoJUnitRunner.class)
public class MaakUitgaandBerichtStapTest {

    @InjectMocks
    private MaakUitgaandBerichtStap subject;

    @Mock
    private LeveringsautorisatieVerwerkingContext  context;
    @Mock
    private LeveringautorisatieStappenOnderwerp    onderwerp;
    @Mock
    private LeveringautorisatieVerwerkingResultaat resultaat;

    @Mock
    private Bericht bericht1;
    @Mock
    private Bericht bericht2;

    @Captor
    private ArgumentCaptor<List<String>> uitgaandeBerichtenCaptor;

    @Before
    public final void setup() {
        final List<SynchronisatieBericht> leveringBerichten = new ArrayList<>();
        leveringBerichten.add(bericht1);
        leveringBerichten.add(bericht2);
        Mockito.when(context.getLeveringBerichten()).thenReturn(leveringBerichten);
    }

    @Test
    public final void test() {
        final String bericht11 = "bericht1";
        final String bericht21 = "bericht2";
        Mockito.when(bericht1.maakUitgaandBericht()).thenReturn(bericht11);
        Mockito.when(bericht2.maakUitgaandBericht()).thenReturn(bericht21);

        final boolean result = subject.voerStapUit(onderwerp, context, resultaat);
        Assert.assertEquals(AbstractStap.DOORGAAN, result);

        Mockito.verify(context).setUitgaandePlatteTekstBerichten(uitgaandeBerichtenCaptor.capture());
        Mockito.verifyNoMoreInteractions(resultaat);

        final List<String> berichten = uitgaandeBerichtenCaptor.getValue();
        Assert.assertEquals(2, berichten.size());
        Assert.assertEquals(bericht11, berichten.get(0));
        Assert.assertEquals(bericht21, berichten.get(1));
    }

    @Test(expected = IllegalStateException.class)
    public final void testException() {
        Mockito.when(bericht1.maakUitgaandBericht()).thenThrow(new IllegalStateException("Test"));

        subject.voerStapUit(onderwerp, context, resultaat);
    }

}
