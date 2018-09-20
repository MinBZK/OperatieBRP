/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
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


    public class TestBean<V, A> {

        private final V veld;
        private final A nietGroterDan;

        public TestBean(final V veld, final A nietGroterDan) {
            this.veld = veld;
            this.nietGroterDan = nietGroterDan;
        }

        public V getVeld() {
            return this.veld;
        }

        public A getNietGroterDan() {
            return this.nietGroterDan;
        }
    }

    @Test
    public void testDatumTijdAttribuutTypeVeld() {
        // Test String veld met String afhankelijke veld
        initialiseerValidatorMetWaarden("nietGroterDan");

        Calendar datumVeld = Calendar.getInstance();
        Calendar datumNietGroterDan = Calendar.getInstance();

        // Gelijk
        TestBean<DatumTijdAttribuut, DatumTijdAttribuut> testDatumTijd =
                new TestBean<>(new DatumTijdAttribuut(datumVeld.getTime()), new DatumTijdAttribuut(
                        datumNietGroterDan.getTime()));
        Assert.assertTrue(this.validator.isValid(testDatumTijd, null));

        // veld na groterdan
        datumVeld.add(Calendar.MINUTE, 1);
        testDatumTijd =
                new TestBean<>(new DatumTijdAttribuut(datumVeld.getTime()), new DatumTijdAttribuut(
                        datumNietGroterDan.getTime()));
        Assert.assertFalse(this.validator.isValid(testDatumTijd, null));

        // veld kleiner groterdan
        datumVeld.add(Calendar.MINUTE, -2);
        testDatumTijd =
                new TestBean<>(new DatumTijdAttribuut(datumVeld.getTime()), new DatumTijdAttribuut(
                        datumNietGroterDan.getTime()));
        Assert.assertTrue(this.validator.isValid(testDatumTijd, null));

        testDatumTijd = new TestBean<>(null, null);
        Assert.assertTrue(this.validator.isValid(testDatumTijd, null));

        testDatumTijd = new TestBean<>(null, new DatumTijdAttribuut(datumNietGroterDan.getTime()));
        Assert.assertFalse(this.validator.isValid(testDatumTijd, null));

        testDatumTijd = new TestBean<>(new DatumTijdAttribuut(datumVeld.getTime()), null);
        Assert.assertFalse(this.validator.isValid(testDatumTijd, null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGeenDatumTijdVeldValidator() {
        // Test String veld met String afhankelijke veld
        initialiseerValidatorMetWaarden("nietGroterDan");

        Calendar datumNietGroterDan = Calendar.getInstance();

        // Gelijk
        TestBean<DatumAttribuut, DatumTijdAttribuut> testDatumTijd =
                new TestBean<>(new DatumAttribuut(20120101), new DatumTijdAttribuut(datumNietGroterDan.getTime()));
        this.validator.isValid(testDatumTijd, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGeenDatumTijdNietGroterDanValidator() {
        // Test String veld met String afhankelijke veld
        initialiseerValidatorMetWaarden("nietGroterDan");

        Calendar datumVeld = Calendar.getInstance();

        // Gelijk
        TestBean<DatumTijdAttribuut, DatumAttribuut> testDatumTijd =
                new TestBean<>(new DatumTijdAttribuut(datumVeld.getTime()), new DatumAttribuut(20120101));
        this.validator.isValid(testDatumTijd, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFoutInAttribuutNaam() {
        initialiseerValidatorMetWaarden("ongeldigVeld", "waarde1");

        this.validator.isValid(new TestBean<String, String>(null, null), null);
    }

    private void initialiseerValidatorMetWaarden(final String nietGroterDanNaam) {
        initialiseerValidatorMetWaarden("veld", nietGroterDanNaam);
    }

    private void initialiseerValidatorMetWaarden(final String veldNaam, final String nietGroterDanNaam) {
        // @NietGroterDan(veld = veldNaam, nietGroterDan = nietGroterDanNaam, code = MeldingCode.ALG0001, message =
        // "fout") })
        final Map<String, Object> annotatieAttributen = new HashMap<>();
        annotatieAttributen.put("veld", veldNaam);
        annotatieAttributen.put("nietGroterDanVeld", nietGroterDanNaam);
        annotatieAttributen.put("code", Regel.ALG0001);
        NietGroterDan annotation =
                AnnotationFactory
                        .create(AnnotationDescriptor.getInstance(NietGroterDan.class, annotatieAttributen));
        this.validator.initialize(annotation);
    }

}
