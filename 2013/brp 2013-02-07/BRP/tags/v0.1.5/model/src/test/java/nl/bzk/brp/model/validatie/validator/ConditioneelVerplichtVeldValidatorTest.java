/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVerplichtVeld;
import org.hibernate.validator.util.annotationfactory.AnnotationDescriptor;
import org.hibernate.validator.util.annotationfactory.AnnotationFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor de {@link ConditioneelVerplichtVeldValidator} class.
 */
public class ConditioneelVerplichtVeldValidatorTest {

    private final ConditioneelVerplichtVeldValidator validator = new ConditioneelVerplichtVeldValidator();

    private Map<String, Object>             annotatieAttributen;

    public class TestBean<V, A> {

        private V verplichtVeld;
        private A afhankelijkVeld;

        public TestBean(final V verplichtVeld, final A afhankelijkVeld) {
            this.verplichtVeld = verplichtVeld;
            this.afhankelijkVeld = afhankelijkVeld;
        }

        public V getVerplichtVeld() {
            return verplichtVeld;
        }

        public A getAfhankelijkVeld() {
            return afhankelijkVeld;
        }
    }

    @Test
    public void testStringVeldVerplicht() {
        // Test String veld met String afhankelijke veld
        initialiseerValidatorMetWaarden("afhankelijkVeld", "waarde");

        TestBean<String, String> testString = new TestBean<String, String>("", "waarde");
        Assert.assertFalse(validator.isValid(testString, null));
        testString = new TestBean<String, String>(null, "waarde");
        Assert.assertFalse(validator.isValid(testString, null));
        testString = new TestBean<String, String>("a", "waarde");
        Assert.assertTrue(validator.isValid(testString, null));
        testString = new TestBean<String, String>("", "wa");
        Assert.assertTrue(validator.isValid(testString, null));

        // Test String veld met Integer afhankelijke veld
        initialiseerValidatorMetWaarden("afhankelijkVeld", "1");

        TestBean<String, Integer> testInteger = new TestBean<String, Integer>("", 1);
        Assert.assertFalse(validator.isValid(testInteger, null));
        testInteger = new TestBean<String, Integer>("", 2);
        Assert.assertTrue(validator.isValid(testInteger, null));
        testInteger = new TestBean<String, Integer>("", null);
        Assert.assertTrue(validator.isValid(testInteger, null));
    }

    @Test
    public void testIntegerVeldVerplicht() {
        initialiseerValidatorMetWaarden("afhankelijkVeld", "waarde");

        // Test dat er geen Integer is ingevoerd
        TestBean<Integer, String> testString = new TestBean<Integer, String>(null, "waarde");
        Assert.assertFalse(validator.isValid(testString, null));

        // Test dat er wel een Integer is ingevoerd
        testString = new TestBean<Integer, String>(1, "waarde");
        Assert.assertTrue(validator.isValid(testString, null));
    }

    @Test
    public void testGenestAfhankelijkAttribuut() {
        initialiseerValidatorMetWaarden("afhankelijkVeld.verplichtVeld", "waarde1");

        // Test dat de afhankelijke veld dezelfde waarde heeft als in de annotatie en dat de verplichte veld niet is
        // ingevuld
        TestBean<String, String> genesteBean = new TestBean<String, String>("waarde1", "waarde2");
        TestBean<String, TestBean<String, String>> testString =
            new TestBean<String, TestBean<String, String>>(null, genesteBean);
        Assert.assertFalse(validator.isValid(testString, null));

        // Test dat de afhankelijke veld dezelfde waarde heeft als in de annotatie en dat de verplichte veld wel is
        // ingevuld
        testString = new TestBean<String, TestBean<String, String>>("abcd", genesteBean);
        Assert.assertTrue(validator.isValid(testString, null));

        // Test dat de afhankelijke veld een andere waarde heeft dan als in de annotatie en dat de verpluchte veld niet
        // is ingevuld
        genesteBean = new TestBean<String, String>("waarde2", "waarde2");
        testString = new TestBean<String, TestBean<String, String>>(null, genesteBean);
        Assert.assertTrue(validator.isValid(testString, null));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testFoutInAttribuutNaam() {
        initialiseerValidatorMetWaarden("ongeldigVeld", "waarde1");

        validator.isValid(new TestBean<String, String>(null, null), null);
    }

    private void initialiseerValidatorMetWaarden(final String naamAfhankelijkVeld, final String bevatWaarde) {
        // @ConditioneelVerplichtVeld(naamVerplichtVeld = "verplichtVeld", naamAfhankelijkVeld = "afhankelijkVeld", waardeAfhankelijkVeld =
        // x, message = "fout") })
        annotatieAttributen = new HashMap<String, Object>();
        annotatieAttributen.put("naamVerplichtVeld", "verplichtVeld");
        annotatieAttributen.put("naamAfhankelijkVeld", naamAfhankelijkVeld);
        annotatieAttributen.put("waardeAfhankelijkVeld", bevatWaarde);
        annotatieAttributen.put("code", MeldingCode.ALG0001);
        ConditioneelVerplichtVeld annotation =
            AnnotationFactory.create(AnnotationDescriptor.getInstance(ConditioneelVerplichtVeld.class, annotatieAttributen));
        validator.initialize(annotation);
    }
}
