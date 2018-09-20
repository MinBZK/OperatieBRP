/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import static java.util.Collections.singletonList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link IsInOnderzoekPredikaat}
 */
@RunWith(MockitoJUnitRunner.class)
public class IsInOnderzoekPredikaatTest {

    private final IsInOnderzoekPredikaat predikaat = new IsInOnderzoekPredikaat();

    @Mock
    private Groep groep;

    @Mock
    private Attribuut attribuut;

    @Before
    public void init() {
        when(attribuut.isMagGeleverdWorden()).thenReturn(true);
    }

    @Test
    public void evaluateRetourneertTrueBijAttribuutInOnderzoek() {
        when(groep.getAttributen()).thenReturn(singletonList(attribuut));
        when(attribuut.isInOnderzoek()).thenReturn(true);

        final boolean result = predikaat.evaluate(groep);

        assertThat(result, is(true));
    }

    @Test
    public void evaluateRetourneertFalseBijGeenAttribuutInOnderzoek() {
        when(groep.getAttributen()).thenReturn(singletonList(attribuut));
        when(attribuut.isInOnderzoek()).thenReturn(false);

        final boolean result = predikaat.evaluate(groep);

        assertThat(result, is(false));
    }

    @Test
    public void evaluateRetourneertFalseBijAttribuutInOnderzoekMaarMagNietLeveren() {
        when(groep.getAttributen()).thenReturn(singletonList(attribuut));
        when(attribuut.isInOnderzoek()).thenReturn(true);
        when(attribuut.isMagGeleverdWorden()).thenReturn(false);

        final boolean result = predikaat.evaluate(groep);

        assertThat(result, is(false));
    }

    @Test
    public void evaluateRetourneertFalseBijGeenAbstractAttribuutInGroep() {
        when(groep.getAttributen()).thenReturn(singletonList(mock(Attribuut.class)));

        final boolean result = predikaat.evaluate(groep);

        assertThat(result, is(false));
    }

    @Test
    public void evaluateRetourneertFalseBijGeenGroepAlsInput() {
        final boolean result = predikaat.evaluate(null);

        assertThat(result, is(false));
    }
}