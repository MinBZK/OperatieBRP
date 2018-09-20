/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
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

    private Map<String, Object> annotatieAttributen;


    public class TestBean<V, A> {

        private static final String NAAM_VELD_AFHANKELIJKVELD = "afhankelijkVeld";
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
            return this.verplichtVeld;
        }

        public A getAfhankelijkVeld() {
            return this.afhankelijkVeld;
        }

        public boolean getVerplichtNull() {
            return this.verplichtNull;
        }
    }

    @Test
    public void testStringAttribuutTypeVeldVerplicht() {
        // Test String veld met String afhankelijke veld
        final String bevatWaarde = "waardeA";
        initialiseerValidatorMetWaarden(TestBean.NAAM_VELD_AFHANKELIJKVELD, bevatWaarde);

        TestBean<NaamEnumeratiewaardeAttribuut, NaamEnumeratiewaardeAttribuut> testString =
                new TestBean<NaamEnumeratiewaardeAttribuut, NaamEnumeratiewaardeAttribuut>(
                        new NaamEnumeratiewaardeAttribuut(""), new NaamEnumeratiewaardeAttribuut(bevatWaarde));
        Assert.assertFalse(this.validator.isValid(testString, null));
        testString =
                new TestBean<NaamEnumeratiewaardeAttribuut, NaamEnumeratiewaardeAttribuut>(null,
                        new NaamEnumeratiewaardeAttribuut("waardeA"));
        Assert.assertFalse(this.validator.isValid(testString, null));
        testString =
                new TestBean<NaamEnumeratiewaardeAttribuut, NaamEnumeratiewaardeAttribuut>(
                        new NaamEnumeratiewaardeAttribuut("a"), new NaamEnumeratiewaardeAttribuut(bevatWaarde));
        Assert.assertTrue(this.validator.isValid(testString, null));
        testString =
                new TestBean<NaamEnumeratiewaardeAttribuut, NaamEnumeratiewaardeAttribuut>(
                        new NaamEnumeratiewaardeAttribuut(""), new NaamEnumeratiewaardeAttribuut("wa"));
        Assert.assertTrue(this.validator.isValid(testString, null));

        // Test String veld met Integer afhankelijke veld
        initialiseerValidatorMetWaarden(TestBean.NAAM_VELD_AFHANKELIJKVELD, "1");

        TestBean<NaamEnumeratiewaardeAttribuut, VolgnummerAttribuut> testInteger =
                new TestBean<NaamEnumeratiewaardeAttribuut, VolgnummerAttribuut>(new NaamEnumeratiewaardeAttribuut(""),
                        new VolgnummerAttribuut(1));
        Assert.assertFalse(this.validator.isValid(testInteger, null));
        testInteger =
                new TestBean<NaamEnumeratiewaardeAttribuut, VolgnummerAttribuut>(new NaamEnumeratiewaardeAttribuut(""),
                        new VolgnummerAttribuut(2));
        Assert.assertTrue(this.validator.isValid(testInteger, null));
        testInteger =
                new TestBean<NaamEnumeratiewaardeAttribuut, VolgnummerAttribuut>(new NaamEnumeratiewaardeAttribuut(""),
                        null);
        Assert.assertTrue(this.validator.isValid(testInteger, null));
    }

    @Test
    public void testIntegerAttribuutTypeVeldVerplicht() {
        initialiseerValidatorMetWaarden(TestBean.NAAM_VELD_AFHANKELIJKVELD, "waardeD");

        // Test dat er geen Integer is ingevoerd
        TestBean<VolgnummerAttribuut, NaamEnumeratiewaardeAttribuut> testString =
                new TestBean<VolgnummerAttribuut, NaamEnumeratiewaardeAttribuut>(null,
                        new NaamEnumeratiewaardeAttribuut(
                                "waardeD"));
        Assert.assertFalse(this.validator.isValid(testString, null));

        // Test dat er wel een Integer is ingevoerd
        testString =
                new TestBean<VolgnummerAttribuut, NaamEnumeratiewaardeAttribuut>(new VolgnummerAttribuut(1),
                        new NaamEnumeratiewaardeAttribuut("waardeD"));
        Assert.assertTrue(this.validator.isValid(testString, null));
    }

    @Test
    public void testEnumVeldVerplicht() {
        initialiseerValidatorMetWaarden(TestBean.NAAM_VELD_AFHANKELIJKVELD, "waardeG");

        // Test dat er geen Integer is ingevoerd
        TestBean<Geslachtsaanduiding, NaamEnumeratiewaardeAttribuut> testString =
                new TestBean<Geslachtsaanduiding, NaamEnumeratiewaardeAttribuut>(null,
                        new NaamEnumeratiewaardeAttribuut(
                                "waardeG"));
        Assert.assertFalse(this.validator.isValid(testString, null));

        // Test dat er wel een Integer is ingevoerd
        testString =
                new TestBean<Geslachtsaanduiding, NaamEnumeratiewaardeAttribuut>(Geslachtsaanduiding.MAN,
                        new NaamEnumeratiewaardeAttribuut("waardeG"));
        Assert.assertTrue(this.validator.isValid(testString, null));
    }

    @Test
    public void testStringVeldVerplicht() {
        initialiseerValidatorMetWaarden(TestBean.NAAM_VELD_AFHANKELIJKVELD, "waardeJ");

        // Test dat er geen Integer is ingevoerd
        TestBean<String, String> testString = new TestBean<String, String>(null, "waardeJ");
        Assert.assertFalse(this.validator.isValid(testString, null));

        // Test dat er wel een Integer is ingevoerd
        testString = new TestBean<String, String>("abc", "waardeL");
        Assert.assertTrue(this.validator.isValid(testString, null));
    }

    @Test
    public void tesVeldVerplichtNull() {
        initialiseerValidatorMetWaarden(TestBean.NAAM_VELD_AFHANKELIJKVELD, "waardeM", true);

        // Test dat 'verplichtveld' is null MOET zijn als 'afhankelijkVeld' de inhoud "waarde" heeft
        TestBean<String, String> testBean = new TestBean<String, String>(null, "waardeM", true);
        Assert.assertTrue(this.validator.isValid(testBean, null));

        // Test dat 'verplichtveld' is null MOET zijn als 'afhankelijkVeld' de inhoud "waarde" heeft
        testBean = new TestBean<String, String>("abcd", "waardeM");
        Assert.assertFalse(this.validator.isValid(testBean, null));

        // Test dat 'verplichtveld' willekeurig kan zijn als 'afhankelijkVeld' NIET de inhoud "waarde" heeft
        testBean = new TestBean<String, String>("abc", "waardex");
        Assert.assertTrue(this.validator.isValid(testBean, null));
        testBean = new TestBean<String, String>(null, "waardex");
        Assert.assertTrue(this.validator.isValid(testBean, null));

        // Test dat 'verplichtveld' willekeurig kan zijn als 'afhankelijkVeld' NIET de inhoud "waarde" (== null) heeft
        testBean = new TestBean<String, String>("abcdef", null);
        Assert.assertTrue(this.validator.isValid(testBean, null));
        testBean = new TestBean<String, String>(null, null);
        Assert.assertTrue(this.validator.isValid(testBean, null));

    }

    @Test
    public void tesVeldVerplichtNullConditioneleVeldWillekeurigeWaarde() {
        // afhankelijkveld heeft de waarde null ==> will zeggen dat alles is goed genoeg om te triggeren.
        // nl. als afhankelijk waarde is niet leeg, dan MOET verplicht veld null zijn.
        initialiseerValidatorMetWaarden(TestBean.NAAM_VELD_AFHANKELIJKVELD, ConditioneelVerplichtVeld.WILDCARD_WAARDE_CODE, true);

        // Test dat 'verplichtveld' is null MOET zijn als 'afhankelijkVeld' de waarde "waarde" heeft
        TestBean<String, String> testStringNull = new TestBean<String, String>(null, "waardeU", true);
        Assert.assertTrue(this.validator.isValid(testStringNull, null));

        // Test dat 'verplichtveld' is null MOET zijn als 'afhankelijkVeld' de waarde "waarde" heeft
        testStringNull = new TestBean<String, String>("abcdefg", "waardeV");
        Assert.assertFalse(this.validator.isValid(testStringNull, null));

        // Test dat 'verplichtveld' willekeurig kan zijn als 'afhankelijkVeld' NIET waarde "waarde" heeft
        // is nu False, omdat willekeurig "waarde" voldoet.
        testStringNull = new TestBean<String, String>("abcdefgh", "waardeW");
        Assert.assertFalse(this.validator.isValid(testStringNull, null));
        // afhankelijkVeld is null ==> regel wordt helemaal niet getriggerd
        testStringNull = new TestBean<String, String>(null, "waardex");
        Assert.assertTrue(this.validator.isValid(testStringNull, null));
    }

    @Test
    public void tesVeldVerplichtNietNullConditioneleVeldWillekeurigeWaarde() {
        // afhankelijkveld heeft de waarde null ==> will zeggen dat alles is goed genoeg om te triggeren.
        // nl. als afhankelijk waarde is niet leeg, dan MOET verplicht veld null zijn.
        TestBean<String, String> testStringNull = null;
        initialiseerValidatorMetWaarden(TestBean.NAAM_VELD_AFHANKELIJKVELD, ConditioneelVerplichtVeld.WILDCARD_WAARDE_CODE, false);

        // aangezien afhankelijk veld niet null is (heeft waarde 'waarde'), mag die ander NIET null zijn..
        testStringNull = new TestBean<String, String>(null, "waardeX");
        Assert.assertFalse(this.validator.isValid(testStringNull, null));

        // aangezien afhankelijk een willekeurige waarde heeft, moet verplicht veld null zijn.
        testStringNull = new TestBean<String, String>("abc", "waardeY");
        Assert.assertTrue(this.validator.isValid(testStringNull, null));

        // aangezien afhankelijk heeft null waarde, DOET ER NIET toe wat verplicht veld heeft.
        testStringNull = new TestBean<String, String>(null, null);
        Assert.assertTrue(this.validator.isValid(testStringNull, null));

        // aangezien afhankelijk heeft null waarde, DOET ER NIET toe wat verplicht veld heeft.
        testStringNull = new TestBean<String, String>("any", null);
        Assert.assertTrue(this.validator.isValid(testStringNull, null));
    }

    @Test
    public void testGenestAfhankelijkAttribuut() {
        initialiseerValidatorMetWaarden("afhankelijkVeld.verplichtVeld", "waarde1");

        // Test dat de afhankelijke veld dezelfde waarde heeft als in de annotatie en dat de verplichte veld niet is
        // ingevuld
        TestBean<NaamEnumeratiewaardeAttribuut, NaamEnumeratiewaardeAttribuut> genesteBean =
                new TestBean<NaamEnumeratiewaardeAttribuut, NaamEnumeratiewaardeAttribuut>(
                        new NaamEnumeratiewaardeAttribuut("waarde1"), new NaamEnumeratiewaardeAttribuut("waarde2"));
        TestBean<NaamEnumeratiewaardeAttribuut, TestBean<NaamEnumeratiewaardeAttribuut, NaamEnumeratiewaardeAttribuut>>
                testString =
                new TestBean<NaamEnumeratiewaardeAttribuut, TestBean<NaamEnumeratiewaardeAttribuut, NaamEnumeratiewaardeAttribuut>>(
                        null, genesteBean);
        Assert.assertFalse(this.validator.isValid(testString, null));

        // Test dat de afhankelijke veld dezelfde waarde heeft als in de annotatie en dat de verplichte veld wel is
        // ingevuld
        testString =
                new TestBean<NaamEnumeratiewaardeAttribuut, TestBean<NaamEnumeratiewaardeAttribuut, NaamEnumeratiewaardeAttribuut>>(
                        new NaamEnumeratiewaardeAttribuut("abcd"), genesteBean);
        Assert.assertTrue(this.validator.isValid(testString, null));

        // Test dat de afhankelijke veld een andere waarde heeft dan als in de annotatie en dat de verpluchte veld niet
        // is ingevuld
        genesteBean =
                new TestBean<NaamEnumeratiewaardeAttribuut, NaamEnumeratiewaardeAttribuut>(
                        new NaamEnumeratiewaardeAttribuut("waarde2"), new NaamEnumeratiewaardeAttribuut("waarde2"));
        testString =
                new TestBean<NaamEnumeratiewaardeAttribuut, TestBean<NaamEnumeratiewaardeAttribuut, NaamEnumeratiewaardeAttribuut>>(
                        null, genesteBean);
        Assert.assertTrue(this.validator.isValid(testString, null));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testFoutInAttribuutNaam() {
        initialiseerValidatorMetWaarden("ongeldigVeld", "waarde1");

        this.validator.isValid(new TestBean<String, String>(null, null), null);
    }

    private void initialiseerValidatorMetWaarden(final String naamAfhankelijkVeld, final String bevatWaarde) {
        // @ConditioneelVerplichtVeld(naamVerplichtVeld = "verplichtVeld", naamAfhankelijkVeld = "afhankelijkVeld",
        // waardeAfhankelijkVeld =
        // x, message = "fout") })
        this.annotatieAttributen = new HashMap<String, Object>();
        this.annotatieAttributen.put("naamVerplichtVeld", "verplichtVeld");
        this.annotatieAttributen.put("naamAfhankelijkVeld", naamAfhankelijkVeld);
        this.annotatieAttributen.put("waardeAfhankelijkVeld", bevatWaarde);
        this.annotatieAttributen.put("code", Regel.ALG0001);
        ConditioneelVerplichtVeld annotation =
                AnnotationFactory.create(AnnotationDescriptor.getInstance(ConditioneelVerplichtVeld.class,
                        this.annotatieAttributen));
        this.validator.initialize(annotation);
    }

    private void initialiseerValidatorMetWaarden(final String naamAfhankelijkVeld, final String bevatWaarde,
            final boolean verplichtNull)
    {
        this.annotatieAttributen = new HashMap<String, Object>();
        this.annotatieAttributen.put("naamVerplichtVeld", "verplichtVeld");
        this.annotatieAttributen.put("verplichtNull", verplichtNull);
        this.annotatieAttributen.put("naamAfhankelijkVeld", naamAfhankelijkVeld);
        this.annotatieAttributen.put("waardeAfhankelijkVeld", bevatWaarde);
        this.annotatieAttributen.put("code", Regel.ALG0001);
        ConditioneelVerplichtVeld annotation =
                AnnotationFactory.create(AnnotationDescriptor.getInstance(ConditioneelVerplichtVeld.class,
                        this.annotatieAttributen));
        this.validator.initialize(annotation);
    }

}
