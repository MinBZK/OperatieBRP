/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.constraint.NietGroterDan;
import org.hibernate.validator.util.annotationfactory.AnnotationDescriptor;
import org.hibernate.validator.util.annotationfactory.AnnotationFactory;
import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de {@link ConditioneelVerplichtVeldValidator} class.
 */
public class NietGroterDanValidatorTest {

    private final NietGroterDanValidator validator = new NietGroterDanValidator();

    private Map<String, Object>          annotatieAttributen;

    public class TestBean<V, A> {

        private final V veld;
        private final A nietGroterDan;

        public TestBean(final V veld, final A nietGroterDan) {
            this.veld = veld;
            this.nietGroterDan = nietGroterDan;
        }

        public V getVeld() {
            return veld;
        }

        public A getNietGroterDan() {
            return nietGroterDan;
        }
    }

    @Test
    public void testDatumTijdAttribuutTypeVeld() {
        // Test String veld met String afhankelijke veld
        initialiseerValidatorMetWaarden("veld", "nietGroterDan");

        Calendar datumVeld = Calendar.getInstance();
        Calendar datumNietGroterDan = Calendar.getInstance();

        // Gelijk
        TestBean<DatumTijd, DatumTijd> testDatumTijd =
            new TestBean<DatumTijd, DatumTijd>(new DatumTijd(datumVeld.getTime()), new DatumTijd(
                    datumNietGroterDan.getTime()));
        Assert.assertTrue(validator.isValid(testDatumTijd, null));

        // veld na groterdan
        datumVeld.add(Calendar.MINUTE, 1);
        testDatumTijd =
            new TestBean<DatumTijd, DatumTijd>(new DatumTijd(datumVeld.getTime()), new DatumTijd(
                    datumNietGroterDan.getTime()));
        Assert.assertFalse(validator.isValid(testDatumTijd, null));

        // veld kleiner groterdan
        datumVeld.add(Calendar.MINUTE, -2);
        testDatumTijd =
            new TestBean<DatumTijd, DatumTijd>(new DatumTijd(datumVeld.getTime()), new DatumTijd(
                    datumNietGroterDan.getTime()));
        Assert.assertTrue(validator.isValid(testDatumTijd, null));

        testDatumTijd = new TestBean<DatumTijd, DatumTijd>(null, null);
        Assert.assertTrue(validator.isValid(testDatumTijd, null));

        testDatumTijd = new TestBean<DatumTijd, DatumTijd>(null, new DatumTijd(datumNietGroterDan.getTime()));
        Assert.assertFalse(validator.isValid(testDatumTijd, null));

        testDatumTijd = new TestBean<DatumTijd, DatumTijd>(new DatumTijd(datumVeld.getTime()), null);
        Assert.assertFalse(validator.isValid(testDatumTijd, null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGeenDatumTijdVeldValidator() {
        // Test String veld met String afhankelijke veld
        initialiseerValidatorMetWaarden("veld", "nietGroterDan");

        Calendar datumNietGroterDan = Calendar.getInstance();

        // Gelijk
        TestBean<Datum, DatumTijd> testDatumTijd =
            new TestBean<Datum, DatumTijd>(new Datum(20120101), new DatumTijd(datumNietGroterDan.getTime()));
        validator.isValid(testDatumTijd, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGeenDatumTijdNietGroterDanValidator() {
        // Test String veld met String afhankelijke veld
        initialiseerValidatorMetWaarden("veld", "nietGroterDan");

        Calendar datumVeld = Calendar.getInstance();

        // Gelijk
        TestBean<DatumTijd, Datum> testDatumTijd =
            new TestBean<DatumTijd, Datum>(new DatumTijd(datumVeld.getTime()), new Datum(20120101));
        validator.isValid(testDatumTijd, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFoutInAttribuutNaam() {
        initialiseerValidatorMetWaarden("ongeldigVeld", "waarde1");

        validator.isValid(new TestBean<String, String>(null, null), null);
    }

    private void initialiseerValidatorMetWaarden(final String veldNaam, final String nietGroterDanNaam) {
        // @NietGroterDan(veld = veldNaam, nietGroterDan = nietGroterDanNaam, code = MeldingCode.ALG0001, message =
        // "fout") })
        annotatieAttributen = new HashMap<String, Object>();
        annotatieAttributen.put("veld", veldNaam);
        annotatieAttributen.put("nietGroterDanVeld", nietGroterDanNaam);
        annotatieAttributen.put("code", MeldingCode.ALG0001);
        NietGroterDan annotation =
            AnnotationFactory.create(AnnotationDescriptor.getInstance(NietGroterDan.class, annotatieAttributen));
        validator.initialize(annotation);
    }

}
