/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.hisvolledig.predikaat.HistorieVanafPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.IsInOnderzoekPredikaat;
import org.apache.commons.collections.Predicate;
import org.junit.Test;

/**
 * Unit test voor {@link AbstractHisVolledigPredikaatView}.
 */
public class AbstractHisVolledigPredikaatViewTest {

    private final HistorieVanafPredikaat           predikaat = new HistorieVanafPredikaat(new DatumAttribuut(new Date()));
    private final AbstractHisVolledigPredikaatView view      = new TestAbstractHisVolledigPredikaatView(predikaat);

    @Test
    public void geeftPredikaatUitAllPredikaatAlsTypeMatcht() throws Exception {
        final HistorieVanafPredikaat predikaat = view
            .getPredikaatVanType(HistorieVanafPredikaat.class);

        assertThat(predikaat, is(predikaat));
    }

    @Test
    public void geeftGeenPredikaatUitAllPredikaatAlsTypeNietMatcht() throws Exception {
        final IsInOnderzoekPredikaat predikaat = view
            .getPredikaatVanType(IsInOnderzoekPredikaat.class);

        assertThat(predikaat, is(nullValue()));
    }

    private static class TestAbstractHisVolledigPredikaatView extends AbstractHisVolledigPredikaatView {

        /**
         * Instantieert een nieuwe abstract his volledig predikaat view met een predikaat.
         *
         * @param predikaat het predikaat
         */
        protected TestAbstractHisVolledigPredikaatView(final Predicate predikaat) {
            super(predikaat);
        }
    }
}