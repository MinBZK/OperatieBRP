/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.lo3;

import nl.bzk.brp.business.stappen.AbstractStap;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * Test voor {@link nl.bzk.brp.levering.mutatielevering.stappen.lo3.MaakBerichtenStap}
 */
@RunWith(MockitoJUnitRunner.class)
public class MaakBerichtenStapTest {

    @Mock
    private BerichtFactory berichtFactory;

    @InjectMocks
    private MaakBerichtenStap subject;

    @Mock(answer = Answers.RETURNS_MOCKS)
    private LeveringsautorisatieVerwerkingContext  context;
    @Mock(answer = Answers.RETURNS_MOCKS)
    private LeveringautorisatieStappenOnderwerp    onderwerp;
    @Mock
    private LeveringautorisatieVerwerkingResultaat resultaat;

    @Test
    public final void test() throws ReflectiveOperationException {
        final boolean result = subject.voerStapUit(onderwerp, context, resultaat);

        Assert.assertEquals(AbstractStap.DOORGAAN, result);

        Mockito.verify(berichtFactory).maakBerichten(Mockito.anyListOf(PersoonHisVolledig.class),
            Mockito.<Leveringinformatie>any(), Mockito.anyMapOf(Integer.class, Populatie.class),
            Mockito.<AdministratieveHandelingModel>any());
        Mockito.verify(context).setLeveringBerichten(Mockito.anyListOf(SynchronisatieBericht.class));
        Mockito.verifyNoMoreInteractions(berichtFactory, resultaat);
    }

    @Test(expected = IllegalStateException.class)
    public final void testException() throws ReflectiveOperationException {
        Mockito.when(
            berichtFactory.maakBerichten(Mockito.anyListOf(PersoonHisVolledig.class),
                Mockito.<Leveringinformatie>any(), Mockito.anyMapOf(Integer.class, Populatie.class),
                Mockito.<AdministratieveHandelingModel>any())).thenThrow(new IllegalStateException("Test"));

        subject.voerStapUit(onderwerp, context, resultaat);
    }

}
