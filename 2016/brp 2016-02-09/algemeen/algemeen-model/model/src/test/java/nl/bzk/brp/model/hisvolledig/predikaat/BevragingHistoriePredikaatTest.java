/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Historievorm;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeleHistorieModel;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;

import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class BevragingHistoriePredikaatTest {

    private static final String DEVENTER  = "Deventer";
    private static final String UTRECHT   = "Utrecht";
    private static final String HILVERSUM = "Hilversum";
    private static final String ZEIST     = "Zeist";

    private Set<HisPersoonAdresModel> adresHistorie;

    /*
     * In 1980 geboren in Deventer.
     * In 2000 verhuist naar Utrecht om te studeren.
     * In 2010 verhuist naar Hilversum om te settelen.
     * In 2012 correctie: van 2000 tot 2005 gewoont in Zeist.
     *
     * Grafisch ziet dat er als volgt uit:
     *
     * 2014 | D | | |
     * 2013 | E | ZEIST | UTRECHT | HIL
     * 2012 | V |-------------------| VER
     * 2011 | E | UTRECHT | SUM
     * 2010 | N |----------------------------
     * 2009 | T |
     * .... | E | UTRECHT
     * 2001 | R |
     * 2000 |-------------------------------------
     * 1999 |
     * .... | DEVENTER
     * 1981 |
     * 1980 |-------------------------------------
     * 1 1 . 1 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2
     * 9 9 . 9 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
     * 8 8 . 9 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1
     * 0 1 . 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4
     */
    @Before
    @SuppressWarnings("unchecked")
    public void bouwAdresHistorie() {
        PersoonHisVolledigImpl persoon =
                new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        PersoonAdresHisVolledigImpl adres =
                new PersoonAdresHisVolledigImplBuilder(persoon).nieuwStandaardRecord(19800101, null, 19800101)
                        .woonplaatsnaam(DEVENTER).eindeRecord().nieuwStandaardRecord(20000101, null, 20000101)
                        .woonplaatsnaam(UTRECHT).eindeRecord().nieuwStandaardRecord(20100101, null, 20100101)
                        .woonplaatsnaam(HILVERSUM).eindeRecord().nieuwStandaardRecord(20000101, 20050101, 20120101)
                        .woonplaatsnaam(ZEIST).eindeRecord().build();
        persoon.getAdressen().add(adres);

        adresHistorie =
                (Set<HisPersoonAdresModel>) ReflectionTestUtils.getField(persoon.getAdressen().iterator().next()
                        .getPersoonAdresHistorie(), "interneSet");
    }

    @Test
    // Alleen de actuele waarde.
    public void testMaterieel2014Formeel2014HistorievormGeen()
    {
        // given
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(20140101);
        final DatumTijdAttribuut formeelPeilmoment = DatumTijdAttribuut.bouwDatumTijd(2014, 1, 1);
        final Predicate predikaat =
                new BevragingHistoriePredikaat(materieelPeilmoment, formeelPeilmoment, Historievorm.GEEN);

        // when
        CollectionUtils.filter(adresHistorie, predikaat);

        // then
        Assert.assertEquals(1, adresHistorie.size());
        assertBevatEnkel(HILVERSUM);
    }

    @Test
    // Alleen de materiele, niet vervallen historie.
    public void testMaterieel2014Formeel2014HistorievormMaterieel()
    {
        // given
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(20140101);
        final DatumTijdAttribuut formeelPeilmoment = DatumTijdAttribuut.bouwDatumTijd(2014, 1, 1);
        final Predicate predikaat =
                new BevragingHistoriePredikaat(materieelPeilmoment, formeelPeilmoment, Historievorm.MATERIEEL);

        // when
        CollectionUtils.filter(adresHistorie, predikaat);

        // then
        Assert.assertEquals(4, adresHistorie.size());
        assertBevatEnkel(DEVENTER, ZEIST, UTRECHT, HILVERSUM);
    }

    @Test
    // Alle historie.
    public void testMaterieel2014Formeel2014HistorievormMaterieelFormeel()
    {
        // given
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(20140101);
        final DatumTijdAttribuut formeelPeilmoment = DatumTijdAttribuut.bouwDatumTijd(2014, 1, 1);
        final Predicate predikaat =
                new BevragingHistoriePredikaat(materieelPeilmoment, formeelPeilmoment, Historievorm.MATERIEEL_FORMEEL);

        // when
        CollectionUtils.filter(adresHistorie, predikaat);

        // then
        Assert.assertEquals(7, adresHistorie.size());
        assertBevatEnkel(DEVENTER, ZEIST, UTRECHT, HILVERSUM);
    }

    @Test
    // Punt in het verleden, kennis van toen.
    public void testMaterieel2003Formeel2003HistorievormGeen()
    {
        // given
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(20030101);
        final DatumTijdAttribuut formeelPeilmoment = DatumTijdAttribuut.bouwDatumTijd(2003, 1, 1);
        final Predicate predikaat =
                new BevragingHistoriePredikaat(materieelPeilmoment, formeelPeilmoment, Historievorm.GEEN);

        // when
        CollectionUtils.filter(adresHistorie, predikaat);

        // then
        Assert.assertEquals(1, adresHistorie.size());
        assertBevatEnkel(UTRECHT);
    }

    @Test
    // Punt in het verleden, kennis van nu.
    public void testMaterieel2003Formeel2014HistorievormGeen()
    {
        // given
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(20030101);
        final DatumTijdAttribuut formeelPeilmoment = DatumTijdAttribuut.bouwDatumTijd(2014, 1, 1);
        final Predicate predikaat =
                new BevragingHistoriePredikaat(materieelPeilmoment, formeelPeilmoment, Historievorm.GEEN);

        // when
        CollectionUtils.filter(adresHistorie, predikaat);

        // then
        Assert.assertEquals(1, adresHistorie.size());
        assertBevatEnkel(ZEIST);
    }

    @Test
    // Materiele lijn uit het verleden, kennis van nu.
    public void testMaterieel2003Formeel2014HistorievormMaterieel()
    {
        // given
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(20030101);
        final DatumTijdAttribuut formeelPeilmoment = DatumTijdAttribuut.bouwDatumTijd(2014, 1, 1);
        final Predicate predikaat =
                new BevragingHistoriePredikaat(materieelPeilmoment, formeelPeilmoment, Historievorm.MATERIEEL);

        // when
        CollectionUtils.filter(adresHistorie, predikaat);

        // then
        Assert.assertEquals(2, adresHistorie.size());
        assertBevatEnkel(DEVENTER, ZEIST);
    }

    @Test
    // Materieel en formeel vlak uit het verleden, kennis van nu.
    public void testMaterieel2003Formeel2014HistorievormMaterieelFormeel()
    {
        // given
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(20030101);
        final DatumTijdAttribuut formeelPeilmoment = DatumTijdAttribuut.bouwDatumTijd(2014, 1, 1);
        final Predicate predikaat =
                new BevragingHistoriePredikaat(materieelPeilmoment, formeelPeilmoment, Historievorm.MATERIEEL_FORMEEL);

        // when
        CollectionUtils.filter(adresHistorie, predikaat);

        // then
        Assert.assertEquals(5, adresHistorie.size());
        assertBevatEnkel(DEVENTER, UTRECHT, ZEIST);
    }

    @Test
    // Grensgeval test
    public void testMaterieel2000Formeel2000HistorievormMaterieelFormeel()
    {
        // given
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(20000101);
        final DatumTijdAttribuut formeelPeilmoment = DatumTijdAttribuut.bouwDatumTijd(2000, 1, 1);
        final Predicate predikaat =
                new BevragingHistoriePredikaat(materieelPeilmoment, formeelPeilmoment, Historievorm.GEEN);

        // when
        CollectionUtils.filter(adresHistorie, predikaat);

        // then
        Assert.assertEquals(1, adresHistorie.size());
        assertBevatEnkel(UTRECHT);
    }

    @Test
    public void testEvaluateWaarFormeelHistorisch() throws Exception {
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(20000101);
        final DatumTijdAttribuut formeelPeilmoment = DatumTijdAttribuut.bouwDatumTijd(2000, 1, 1);
        final Predicate predikaat =
            new BevragingHistoriePredikaat(materieelPeilmoment, formeelPeilmoment, Historievorm.GEEN);
        FormeelHistorisch object = mock(FormeelHistorisch.class);
        final FormeleHistorieModel formeleHistorieModel = mock(FormeleHistorieModel.class);

        when(formeleHistorieModel.getTijdstipRegistratie()).thenReturn(DatumTijdAttribuut.bouwDatumTijd(1999, 12, 31));
        when(object.getFormeleHistorie()).thenReturn(formeleHistorieModel);
        assertTrue(predikaat.evaluate(object));

        when(formeleHistorieModel.getTijdstipRegistratie()).thenReturn(DatumTijdAttribuut.bouwDatumTijd(2000, 1, 1));
        when(object.getFormeleHistorie()).thenReturn(formeleHistorieModel);
        assertFalse(predikaat.evaluate(object));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEvaluateWaarIllegaalType() {
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(20000101);
        final DatumTijdAttribuut formeelPeilmoment = DatumTijdAttribuut.bouwDatumTijd(2000, 1, 1);
        final Predicate predikaat =
            new BevragingHistoriePredikaat(materieelPeilmoment, formeelPeilmoment, Historievorm.GEEN);
        predikaat.evaluate("Objecten die niet Formeel- of MaterieelHistorisch zijn, zijn illegaal");
    }

    private void assertBevatEnkel(final String... woonplaatsnamen) {

        for (String woonplaatsnaam : woonplaatsnamen) {
            boolean bevat = false;
            for (HisPersoonAdresModel hisAdres : adresHistorie) {
                if (hisAdres.getWoonplaatsnaam().getWaarde().equals(woonplaatsnaam)) {
                    bevat = true;
                }
            }
            assertTrue(bevat);
        }
        for (HisPersoonAdresModel hisAdres : adresHistorie) {
            assertTrue(Arrays.asList(woonplaatsnamen).contains(hisAdres.getWoonplaatsnaam().getWaarde()));
        }
    }

}
