/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeleHistorieModel;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieleHistorieModel;
import nl.bzk.brp.model.hisvolledig.GlazenbolException;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

public class AltijdTonenGroepPredikaatTest {

    private static final DatumTijdAttribuut             VANDAAG        = new DatumTijdAttribuut(DateUtils.truncate(new Date(), Calendar.DATE));
    private static final DatumTijdAttribuut             OVERMORGEN     = new DatumTijdAttribuut(
        DateUtils.truncate(DateUtils.addDays(new Date(), 2), Calendar.DATE));
    private static final DatumTijdAttribuut             GISTEREN       = new DatumTijdAttribuut(
        DateUtils.truncate(DateUtils.addDays(new Date(), -1), Calendar.DATE));
    private static final DatumEvtDeelsOnbekendAttribuut GISTEREN_DEELS = new DatumEvtDeelsOnbekendAttribuut(GISTEREN.naarDatum());
    private static final DatumEvtDeelsOnbekendAttribuut VANDAAG_DEELS = new DatumEvtDeelsOnbekendAttribuut(VANDAAG.naarDatum());

    @Test
    public void testBekendOp() throws Exception {
        assertNotNull(AltijdTonenGroepPredikaat.bekendOp(VANDAAG));
    }

    @Test(expected = GlazenbolException.class)
    public void testCreateWhereOvermorgen() throws Exception {
        assertNotNull(AltijdTonenGroepPredikaat.bekendOp(OVERMORGEN));
    }

    @Test
    public void testEvaluateWaarFormeelHistorisch() throws Exception {
        AltijdTonenGroepPredikaat predikaat = AltijdTonenGroepPredikaat.bekendOp(VANDAAG);
        assertTrue(predikaat.evaluate(maakFormeelHistorisch(GISTEREN)));
        assertTrue(predikaat.evaluate(maakFormeelHistorisch(VANDAAG)));
    }

    @Test
    public void testEvaluateWaarMaterieelHistorisch() throws Exception {
        AltijdTonenGroepPredikaat predikaat = AltijdTonenGroepPredikaat.bekendOp(GISTEREN);
        assertFalse(predikaat.evaluate(maakMaterieleHistorie(VANDAAG, null)));
        assertTrue(predikaat.evaluate(maakMaterieleHistorie(GISTEREN, GISTEREN_DEELS)));
        assertFalse(predikaat.evaluate(maakMaterieleHistorie(GISTEREN, VANDAAG_DEELS)));
    }

    private FormeelHistorisch maakFormeelHistorisch(final DatumTijdAttribuut registratie) {
        FormeelHistorisch resultaat = mock(FormeelHistorisch.class);
        FormeleHistorieModel formeleHistorieModel = mock(FormeleHistorieModel.class);
        when(formeleHistorieModel.getTijdstipRegistratie()).thenReturn(registratie);
        when(resultaat.getFormeleHistorie()).thenReturn(formeleHistorieModel);
        return resultaat;
    }

    private MaterieelHistorisch maakMaterieleHistorie(final DatumTijdAttribuut registratie, final DatumEvtDeelsOnbekendAttribuut aanvang) {
        MaterieelHistorisch resultaat = mock(MaterieelHistorisch.class);
        MaterieleHistorieModel materieleHistorieModel = mock(MaterieleHistorieModel.class);
        when(materieleHistorieModel.getTijdstipRegistratie()).thenReturn(registratie);
        when(materieleHistorieModel.getDatumAanvangGeldigheid()).thenReturn(aanvang);
        when(resultaat.getMaterieleHistorie()).thenReturn(materieleHistorieModel);
        return resultaat;
    }

}
