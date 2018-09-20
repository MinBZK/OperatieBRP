/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.lo3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.business.stappen.Stap;
import nl.bzk.brp.gba.dataaccess.Lo3FilterRubriekRepository;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.lo3.bericht.Bericht;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienstbundel;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test voor {@link nl.bzk.brp.levering.mutatielevering.stappen.lo3.ConverteerNaarLo3Stap}
 */
@RunWith(MockitoJUnitRunner.class)
public class FilterRubriekenStapTest {

    @Mock
    private Lo3FilterRubriekRepository lo3FilterRubriekRepository;

    @InjectMocks
    private FilterRubriekenStap subject;

    @Mock(answer = Answers.RETURNS_MOCKS)
    private LeveringsautorisatieVerwerkingContext  context;
    @Mock(answer = Answers.RETURNS_MOCKS)
    private LeveringautorisatieStappenOnderwerp    onderwerp;
    @Mock
    private LeveringautorisatieVerwerkingResultaat resultaat;

    @Mock
    private Bericht bericht1;
    @Mock
    private Bericht bericht2;

    private List<String> rubrieken;

    @Before
    public final void setup() {
        final List<SynchronisatieBericht> leveringBerichten = new ArrayList<>();
        leveringBerichten.add(bericht1);
        leveringBerichten.add(bericht2);
        Mockito.when(context.getLeveringBerichten()).thenReturn(leveringBerichten);
        rubrieken = Arrays.asList("01.01.10", "01.01.20");
    }

    @Test
    public final void test() {

        Mockito.when(lo3FilterRubriekRepository.haalLo3FilterRubriekenVoorDienstbundel(Matchers.<Dienstbundel>any())).thenReturn(rubrieken);

        Mockito.when(bericht1.filterRubrieken(rubrieken)).thenReturn(Boolean.TRUE);
        Mockito.when(bericht2.filterRubrieken(rubrieken)).thenReturn(Boolean.FALSE);

        final boolean result = subject.voerStapUit(onderwerp, context, resultaat);
        Assert.assertEquals(Stap.DOORGAAN, result);

        Mockito.verify(bericht1).filterRubrieken(rubrieken);
        Mockito.verify(bericht2).filterRubrieken(rubrieken);
        Mockito.verifyNoMoreInteractions(resultaat);
    }

    @Test
    public final void testEmpty() {
        Mockito.when(lo3FilterRubriekRepository.haalLo3FilterRubriekenVoorDienstbundel(Matchers.<Dienstbundel>any())).thenReturn(rubrieken);

        Mockito.when(bericht1.filterRubrieken(rubrieken)).thenReturn(Boolean.FALSE);
        Mockito.when(bericht2.filterRubrieken(rubrieken)).thenReturn(Boolean.FALSE);

        final boolean result = subject.voerStapUit(onderwerp, context, resultaat);
        Assert.assertEquals(Stap.STOPPEN, result);

        Mockito.verify(bericht1).filterRubrieken(rubrieken);
        Mockito.verify(bericht2).filterRubrieken(rubrieken);
        Mockito.verifyNoMoreInteractions(resultaat);
    }

    @Test(expected = IllegalStateException.class)
    public final void testException() {
        Mockito.when(lo3FilterRubriekRepository.haalLo3FilterRubriekenVoorDienstbundel(Matchers.<Dienstbundel>any())).thenReturn(rubrieken);

        Mockito.when(bericht1.filterRubrieken(rubrieken)).thenThrow(new IllegalStateException("Test"));

        subject.voerStapUit(onderwerp, context, resultaat);
    }
}
