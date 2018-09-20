/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import org.junit.Assert;
import org.junit.Test;

public class PersoonIdPredikaatTest {

    @Test
    public final void predikaatMatchtPersoon() {
        final PersoonIdPredikaat predikaat = new PersoonIdPredikaat(Arrays.asList(1, 3));

        final PersoonHisVolledig persoon = mock(PersoonHisVolledig.class);
        when(persoon.getID()).thenReturn(1);

        final boolean resultaat = predikaat.evaluate(persoon);

        Assert.assertThat(resultaat, is(true));
    }

    @Test
    public final void predikaatMatchtPersoonNiet() {
        final PersoonIdPredikaat predikaat = new PersoonIdPredikaat(Arrays.asList(1, 3));

        final PersoonHisVolledig persoon = mock(PersoonHisVolledig.class);
        when(persoon.getID()).thenReturn(2);

        final boolean resultaat = predikaat.evaluate(persoon);

        Assert.assertThat(resultaat, is(false));
    }

    @Test
    public final void predikaatHeeftLegeLijst() {
        final PersoonIdPredikaat predikaat = new PersoonIdPredikaat(Collections.<Integer>emptyList());

        final PersoonHisVolledig persoon = mock(PersoonHisVolledig.class);
        when(persoon.getID()).thenReturn(2);

        final boolean resultaat = predikaat.evaluate(persoon);

        Assert.assertThat(resultaat, is(false));
    }

}
