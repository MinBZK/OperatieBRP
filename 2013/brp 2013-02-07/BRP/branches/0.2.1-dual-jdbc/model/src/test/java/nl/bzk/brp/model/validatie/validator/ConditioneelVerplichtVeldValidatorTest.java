/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
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

    private Map<String, Object>                      annotatieAttributen;

    public class TestBean<V, A> {

        private final V verplichtVeld;
        private final A afhankelijkVeld;
        private boolean verplichtNull = false;

        public TestBean(final V verplichtVeld, final A afhankelijkVeld) {
            this.verplichtVeld = verplichtVeld;
            this.afhankelijkVeld = afhankelijkVeld;
        }

        public TestBean(final V verplichtVeld, final A afhankelijkVeld, final boolean verplichtNull) {
            this.verplichtVeld = verplichtVeld;
            this.afhankelijkVeld = afhankelijkVeld;
            this.verplichtNull = verplichtNull;
        }

        public V getVerplichtVeld() {
            return verplichtVeld;
        }

        public A getAfhankelijkVeld() {
            return afhankelijkVeld;
        }

        public boolean getVerplichtNull() {
            return verplichtNull;
        }
    }


    @Test
    public void testStringAttribuutTypeVeldVerplicht() {
        // Test String veld met String afhankelijke veld
        initialiseerValidatorMetWaarden("afhankelijkVeld", "waarde");

        TestBean<Naam, Naam> testString = new TestBean<Naam, Naam>(new Naam(""), new Naam("waarde"));
        Assert.assertFalse(validator.isValid(testString, null));
        testString = new TestBean<Naam, Naam>(null, new Naam("waarde"));
        Assert.assertFalse(validator.isValid(testString, null));
        testString = new TestBean<Naam, Naam>(new Naam("a"), new Naam("waarde"));
        Assert.assertTrue(validator.isValid(testString, null));
        testString = new TestBean<Naam, Naam>(new Naam(""), new Naam("wa"));
        Assert.assertTrue(validator.isValid(testString, null));

        // Test String veld met Integer afhankelijke veld
        initialiseerValidatorMetWaarden("afhankelijkVeld", "1");

        TestBean<Naam, Volgnummer> testInteger = new TestBean<Naam, Volgnummer>(new Naam(""), new Volgnummer(1));
        Assert.assertFalse(validator.isValid(testInteger, null));
        testInteger = new TestBean<Naam, Volgnummer>(new Naam(""), new Volgnummer(2));
        Assert.assertTrue(validator.isValid(testInteger, null));
        testInteger = new TestBean<Naam, Volgnummer>(new Naam(""), null);
        Assert.assertTrue(validator.isValid(testInteger, null));
    }

    @Test
    public void testIntegerAttribuutTypeVeldVerplicht() {
        initialiseerValidatorMetWaarden("afhankelijkVeld", "waarde");

        // Test dat er geen Integer is ingevoerd
        TestBean<Volgnummer, Naam> testString = new TestBean<Volgnummer, Naam>(null, new Naam("waarde"));
        Assert.assertFalse(validator.isValid(testString, null));

        // Test dat er wel een Integer is ingevoerd
        testString = new TestBean<Volgnummer, Naam>(new Volgnummer(1), new Naam("waarde"));
        Assert.assertTrue(validator.isValid(testString, null));
    }

    @Test
    public void testEnumVeldVerplicht() {
        initialiseerValidatorMetWaarden("afhankelijkVeld", "waarde");

        // Test dat er geen Integer is ingevoerd
        TestBean<Geslachtsaanduiding, Naam> testString = new TestBean<Geslachtsaanduiding, Naam>(null, new Naam("waarde"));
        Assert.assertFalse(validator.isValid(testString, null));

        // Test dat er wel een Integer is ingevoerd
        testString = new TestBean<Geslachtsaanduiding, Naam>(Geslachtsaanduiding.MAN, new Naam("waarde"));
        Assert.assertTrue(validator.isValid(testString, null));
    }

    @Test
    public void testStringVeldVerplicht() {
        initialiseerValidatorMetWaarden("afhankelijkVeld", "waarde");

        // Test dat er geen Integer is ingevoerd
        TestBean<String, String> testString = new TestBean<String, String>(null, "waarde");
        Assert.assertFalse(validator.isValid(testString, null));

        // Test dat er wel een Integer is ingevoerd
        testString = new TestBean<String, String>("abc", "waarde");
        Assert.assertTrue(validator.isValid(testString, null));
    }

    @Test
    public void tesVeldVerplichtNull() {
        initialiseerValidatorMetWaarden("afhankelijkVeld", "waarde", true);

        // Test dat 'verplichtveld' is null MOET zijn als 'afhankelijkVeld' de waarde "waarde" heeft
        TestBean<String, String> testStringNull = new TestBean<String, String>(null, "waarde", true);
        Assert.assertTrue(validator.isValid(testStringNull, null));

        // Test dat 'verplichtveld' is null MOET zijn als 'afhankelijkVeld' de waarde "waarde" heeft
        testStringNull = new TestBean<String, String>("abc", "waarde");
        Assert.assertFalse(validator.isValid(testStringNull, null));

        // Test dat 'verplichtveld' willekeurig kan zijn als 'afhankelijkVeld' NIET waarde "waarde" heeft
        testStringNull = new TestBean<String, String>("abc", "waardex");
        Assert.assertTrue(validator.isValid(testStringNull, null));
        testStringNull = new TestBean<String, String>(null, "waardex");
        Assert.assertTrue(validator.isValid(testStringNull, null));
    }

    @Test
    public void testGenestAfhankelijkAttribuut() {
        initialiseerValidatorMetWaarden("afhankelijkVeld.verplichtVeld", "waarde1");

        // Test dat de afhankelijke veld dezelfde waarde heeft als in de annotatie en dat de verplichte veld niet is
        // ingevuld
        TestBean<Naam, Naam> genesteBean = new TestBean<Naam, Naam>(new Naam("waarde1"), new Naam("waarde2"));
        TestBean<Naam, TestBean<Naam, Naam>> testString = new TestBean<Naam, TestBean<Naam, Naam>>(null, genesteBean);
        Assert.assertFalse(validator.isValid(testString, null));

        // Test dat de afhankelijke veld dezelfde waarde heeft als in de annotatie en dat de verplichte veld wel is
        // ingevuld
        testString = new TestBean<Naam, TestBean<Naam, Naam>>(new Naam("abcd"), genesteBean);
        Assert.assertTrue(validator.isValid(testString, null));

        // Test dat de afhankelijke veld een andere waarde heeft dan als in de annotatie en dat de verpluchte veld niet
        // is ingevuld
        genesteBean = new TestBean<Naam, Naam>(new Naam("waarde2"), new Naam("waarde2"));
        testString = new TestBean<Naam, TestBean<Naam, Naam>>(null, genesteBean);
        Assert.assertTrue(validator.isValid(testString, null));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testFoutInAttribuutNaam() {
        initialiseerValidatorMetWaarden("ongeldigVeld", "waarde1");

        validator.isValid(new TestBean<String, String>(null, null), null);
    }

    private void initialiseerValidatorMetWaarden(final String naamAfhankelijkVeld, final String bevatWaarde) {
        // @ConditioneelVerplichtVeld(naamVerplichtVeld = "verplichtVeld", naamAfhankelijkVeld = "afhankelijkVeld",
        // waardeAfhankelijkVeld =
        // x, message = "fout") })
        annotatieAttributen = new HashMap<String, Object>();
        annotatieAttributen.put("naamVerplichtVeld", "verplichtVeld");
        annotatieAttributen.put("naamAfhankelijkVeld", naamAfhankelijkVeld);
        annotatieAttributen.put("waardeAfhankelijkVeld", bevatWaarde);
        annotatieAttributen.put("code", MeldingCode.ALG0001);
        ConditioneelVerplichtVeld annotation =
            AnnotationFactory.create(AnnotationDescriptor.getInstance(ConditioneelVerplichtVeld.class,
                    annotatieAttributen));
        validator.initialize(annotation);
    }

    private void initialiseerValidatorMetWaarden(final String naamAfhankelijkVeld, final String bevatWaarde,
            final boolean verplichtNull) {
        annotatieAttributen = new HashMap<String, Object>();
        annotatieAttributen.put("naamVerplichtVeld", "verplichtVeld");
        annotatieAttributen.put("verplichtNull", verplichtNull);
        annotatieAttributen.put("naamAfhankelijkVeld", naamAfhankelijkVeld);
        annotatieAttributen.put("waardeAfhankelijkVeld", bevatWaarde);
        annotatieAttributen.put("code", MeldingCode.ALG0001);
        ConditioneelVerplichtVeld annotation =
            AnnotationFactory.create(AnnotationDescriptor.getInstance(ConditioneelVerplichtVeld.class,
                    annotatieAttributen));
        validator.initialize(annotation);
    }

}
