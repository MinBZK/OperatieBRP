/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import static nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut.datumTijd;
import static nl.bzk.brp.util.hisvolledig.kern.FormeleHistorieBuilder.bouwFormeleHistorie;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.FormeleHistorieModel;
import nl.bzk.brp.model.hisvolledig.GlazenbolException;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.junit.Before;
import org.junit.Test;


public class FormeleHistoriePredikaatTest {

    private HisPersoonGeboorteModel      hisPersoonGeboorteModel1998Tot2001  = mock(HisPersoonGeboorteModel.class);
    private HisPersoonGeboorteModel      hisPersoonGeboorteModel2001Tot2011  = mock(HisPersoonGeboorteModel.class);
    private HisPersoonGeboorteModel      hisPersoonGeboorteModel2011TotHeden = mock(HisPersoonGeboorteModel.class);
    private Set<HisPersoonGeboorteModel> hisPersoonGeboorteModellen          =
            new HashSet<HisPersoonGeboorteModel>(
                    Arrays.asList(new HisPersoonGeboorteModel[] {
                            hisPersoonGeboorteModel2001Tot2011, hisPersoonGeboorteModel1998Tot2001,
                            hisPersoonGeboorteModel2011TotHeden }));

    @Before
    public void setup() {
        final FormeleHistorieModel formeleHistorie1998Tot2001 = bouwFormeleHistorie(datumTijd(1998), datumTijd(2001));
        when(hisPersoonGeboorteModel1998Tot2001.getFormeleHistorie()).thenReturn(formeleHistorie1998Tot2001);

        final FormeleHistorieModel formeleHistorie2001Tot2011 = bouwFormeleHistorie(datumTijd(2001), datumTijd(2011));
        when(hisPersoonGeboorteModel2001Tot2011.getFormeleHistorie()).thenReturn(formeleHistorie2001Tot2011);

        final FormeleHistorieModel formeleHistorie2011TotHeden = bouwFormeleHistorie(datumTijd(2011), null);
        when(hisPersoonGeboorteModel2011TotHeden.getFormeleHistorie()).thenReturn(formeleHistorie2011TotHeden);

    }

    @Test
    public void watWetenWeIn2013() {
        // given
        final DatumTijdAttribuut formeelPeilmoment = datumTijd(2013, 1, 1);

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
        final DatumTijdAttribuut formeelPeilmoment = datumTijd(2005, 1, 2, 0, 22, 33);

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
        final DatumTijdAttribuut formeelPeilmoment = datumTijd(1997);

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
        final DatumTijdAttribuut formeelPeilmoment = datumTijd(2005, 1, 2, 0, 22, 33);

        final Predicate geldigOp = FormeleHistoriePredikaat.bekendOp(formeelPeilmoment);

        // when
        final HisPersoonGeboorteModel geboorte =
                (HisPersoonGeboorteModel) CollectionUtils.find(hisPersoonGeboorteModellen, geldigOp);

        // then
        assertEquals(hisPersoonGeboorteModel2001Tot2011, geboorte);
    }

    @Test(expected = GlazenbolException.class)
    public void kijkenNaarDeToekomstKanNiet() {
        // given
        final DatumTijdAttribuut formeelPeilmoment = datumTijd(2050, 1, 1);

        // when
        final Predicate bekendOp = FormeleHistoriePredikaat.bekendOp(formeelPeilmoment);
    }

    @Test
    public void testGeenActueleRecordsAanwezig() {
        Set<HisPersoonGeboorteModel> hisPersoonGeboorteModellenZonderActueelRecord =
                new HashSet<HisPersoonGeboorteModel>(
                        Arrays.asList(new HisPersoonGeboorteModel[] { hisPersoonGeboorteModel1998Tot2001 }));
        final DatumTijdAttribuut formeelPeilmoment = datumTijd(2013, 1, 1);
        final Predicate geldigOp = FormeleHistoriePredikaat.bekendOp(formeelPeilmoment);

        final HisPersoonGeboorteModel geboorte =
                (HisPersoonGeboorteModel) CollectionUtils.find(hisPersoonGeboorteModellenZonderActueelRecord, geldigOp);

        assertNull(geboorte);
    }

}
