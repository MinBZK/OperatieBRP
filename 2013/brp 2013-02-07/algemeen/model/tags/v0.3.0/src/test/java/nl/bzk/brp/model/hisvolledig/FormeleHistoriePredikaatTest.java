/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig;

import static nl.bzk.brp.model.hisvolledig.util.DatumTijdBuilder.bouwDatumTijd;
import static nl.bzk.brp.model.hisvolledig.util.FormeleHistorieBuilder.bouwFormeleHistorie;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.basis.FormeleHistorie;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.junit.Before;
import org.junit.Test;

public class FormeleHistoriePredikaatTest {

    private HisPersoonGeboorteModel hisPersoonGeboorteModel1998Tot2001  = mock(HisPersoonGeboorteModel.class);
    private HisPersoonGeboorteModel hisPersoonGeboorteModel2001Tot2011  = mock(HisPersoonGeboorteModel.class);
    private HisPersoonGeboorteModel hisPersoonGeboorteModel2011TotHeden = mock(HisPersoonGeboorteModel.class);

    private Set<HisPersoonGeboorteModel> hisPersoonGeboorteModellen = new HashSet<HisPersoonGeboorteModel>(
            Arrays.asList(new HisPersoonGeboorteModel[]{
                hisPersoonGeboorteModel2001Tot2011,
                hisPersoonGeboorteModel1998Tot2001,
                hisPersoonGeboorteModel2011TotHeden}));

    @Before
    public void setup() {
        final FormeleHistorie formeleHistorie1998Tot2001 = bouwFormeleHistorie(
                bouwDatumTijd(1998, 0, 0, 0, 0, 0),
                bouwDatumTijd(2001, 0, 0, 0, 0, 0));
        when(hisPersoonGeboorteModel1998Tot2001.getFormeleHistorie()).thenReturn(
                formeleHistorie1998Tot2001);

        final FormeleHistorie formeleHistorie2001Tot2011 = bouwFormeleHistorie(
                bouwDatumTijd(2001, 0, 0, 0, 0, 0),
                bouwDatumTijd(2011, 0, 0, 0, 0, 0));
        when(hisPersoonGeboorteModel2001Tot2011.getFormeleHistorie()).thenReturn(
                formeleHistorie2001Tot2011);

        final FormeleHistorie formeleHistorie2011TotHeden = bouwFormeleHistorie(
                bouwDatumTijd(2011, 0, 0, 0, 0, 0),
                null);
        when(hisPersoonGeboorteModel2011TotHeden.getFormeleHistorie()).thenReturn(formeleHistorie2011TotHeden);

    }

    @Test
    public void watWetenWeIn2013() {
        // given
        final DatumTijd formeelPeilmoment = bouwDatumTijd(2013, 1, 1, 0, 0, 0);

        final Predicate geldigOp = FormeleHistoriePredikaat.bekendOp(formeelPeilmoment);

        // when
        final HisPersoonGeboorteModel geboorte =
                (HisPersoonGeboorteModel) CollectionUtils.find(hisPersoonGeboorteModellen, geldigOp);

        // then
        assertEquals(hisPersoonGeboorteModel2011TotHeden, geboorte);
    }

    @Test
    public void watWetenWeIn2005() {
        // given
        final DatumTijd formeelPeilmoment = bouwDatumTijd(2005, 1, 2, 0, 22, 33);

        final Predicate geldigOp = FormeleHistoriePredikaat.bekendOp(formeelPeilmoment);

        // when
        final HisPersoonGeboorteModel geboorte =
                (HisPersoonGeboorteModel) CollectionUtils.find(hisPersoonGeboorteModellen, geldigOp);

        // then
        assertEquals(hisPersoonGeboorteModel2001Tot2011, geboorte);
    }

    @Test
    public void watWetenWeIn1997() {
        // given
        final DatumTijd formeelPeilmoment = bouwDatumTijd(1997, 0, 0, 0, 0, 0);

        final Predicate geldigOp = FormeleHistoriePredikaat.bekendOp(formeelPeilmoment);

        // when
        final HisPersoonGeboorteModel geboorte =
                (HisPersoonGeboorteModel) CollectionUtils.find(hisPersoonGeboorteModellen, geldigOp);

        // then
        assertNull(geboorte);
    }

    @Test
    public void geboorteVervalOpDatumTijdFormelePeildatum() {
        // given
        final DatumTijd formeelPeilmoment = bouwDatumTijd(2005, 1, 2, 0, 22, 33);

        final Predicate geldigOp = FormeleHistoriePredikaat.bekendOp(formeelPeilmoment);

        // when
        final HisPersoonGeboorteModel geboorte =
                (HisPersoonGeboorteModel) CollectionUtils.find(hisPersoonGeboorteModellen, geldigOp);

        // then
        assertEquals(hisPersoonGeboorteModel2001Tot2011, geboorte);
    }

}
