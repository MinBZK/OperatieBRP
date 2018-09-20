/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVeld;

import org.hibernate.validator.util.annotationfactory.AnnotationDescriptor;
import org.hibernate.validator.util.annotationfactory.AnnotationFactory;
import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de {@link ConditioneelVeldValidator} class.
 */
public class ConditioneelVeldValidatorTest {

    private final ConditioneelVeldValidator validator = new ConditioneelVeldValidator();


    public class TestBean<V, A> {

        private static final String VELDNAAM_CONDITIEVELD = "conditieVeld";
        private final V                              verplichtVeld;
        private final A                              conditieVeld;
        private       ConditioneelVeld.ConditieRegel mode;

        public TestBean(final V verplichtVeld, final A afhankelijkVeld) {
            this.verplichtVeld = verplichtVeld;
            this.conditieVeld = afhankelijkVeld;
        }

        public TestBean(final V verplichtVeld, final A afhankelijkVeld, final ConditioneelVeld.ConditieRegel mode) {
            this.verplichtVeld = verplichtVeld;
            this.conditieVeld = afhankelijkVeld;
            this.mode = mode;
        }

        public V getVerplichtVeld() {
            return this.verplichtVeld;
        }

        public A getConditieVeld() {
            return this.conditieVeld;
        }

        public ConditioneelVeld.ConditieRegel getMode() {
            return this.mode;
        }
    }

    @Test
    public void testIntegerAttribuutTypeVeldVerplicht() {
        initialiseerValidatorMetWaarden(TestBean.VELDNAAM_CONDITIEVELD, "waarde", ConditioneelVeld.ConditieRegel.SYNCHROON);

        // Test dat er geen Integer is ingevoerd
        TestBean<VolgnummerAttribuut, NaamEnumeratiewaardeAttribuut> testString =
                new TestBean<>(null,
                        new NaamEnumeratiewaardeAttribuut(
                                "waarde"));
        Assert.assertFalse(validate(testString));

        // Test dat er wel een Integer is ingevoerd
        testString =
                new TestBean<>(new VolgnummerAttribuut(1),
                        new NaamEnumeratiewaardeAttribuut("waarde"));
        Assert.assertTrue(validate(testString));
    }

    @Test
    public void testEnumVeldVerplicht() {
        // als conditieVeld bevat 'XX', dan moet verplicht veld ... anders ...
        initialiseerValidatorMetWaarden(TestBean.VELDNAAM_CONDITIEVELD, "XX", ConditioneelVeld.ConditieRegel.SYNCHROON);

        // Testbeean: verplichtveld is van type Geslachtsaanduiding, conditieveld is van type NaamEnumeratiewaarde
        // inhoud van: verplichtVeld = MAN, conditieveld = 'XX'
        // ==> voldoet aan conditie, synchroon => verplichtVeld MAG NIET null zijn.
        TestBean<Geslachtsaanduiding, NaamEnumeratiewaardeAttribuut> testString =
                new TestBean<>(null,
                        new NaamEnumeratiewaardeAttribuut(
                                "XX"));
        Assert.assertFalse(validate(testString));
        testString =
                new TestBean<>(Geslachtsaanduiding.MAN,
                        new NaamEnumeratiewaardeAttribuut("XX"));
        Assert.assertTrue(validate(testString));

        // inhoud van: conditieveld = 'yy' ==> voldoet niet ==> verplichtveld MOET null zijn
        testString =
                new TestBean<>(Geslachtsaanduiding.MAN,
                        new NaamEnumeratiewaardeAttribuut("yy"));
        Assert.assertFalse(validate(testString));
        testString =
                new TestBean<>(null,
                        new NaamEnumeratiewaardeAttribuut(
                                "yy"));
        Assert.assertTrue(validate(testString));

        // inhoud van: conditieveld = null ==> verplichtveld moet ook null zijn
        testString = new TestBean<>(Geslachtsaanduiding.MAN, null);
        Assert.assertFalse(validate(testString));
        testString = new TestBean<>(null, null);
        Assert.assertTrue(validate(testString));
    }

    @Test
    public void testVeldAlsCondietNietVoldoet() {
        // constraint: Als conditioneelVeld == "XX" of null en mode = Synchroon, dan veplichtVeld= .. anders ...
        initialiseerValidatorMetWaarden(TestBean.VELDNAAM_CONDITIEVELD, "XX", ConditioneelVeld.ConditieRegel.SYNCHROON);

        // testbean: conditieVeld="yy" ==> conditieveld voldoet niet =>> verplichtveld MOET null
        TestBean<String, String> testString = new TestBean<>(null, "yy");
        Assert.assertTrue(validate(testString));
        testString = new TestBean<>("dfbch", "yy");
        Assert.assertFalse(validate(testString));
    }

    @Test
    public void testVeldAlsCondietWelVoldoet() {
        // constraint: Als conditioneelVeld == "XX" of null en mode = Synchroon, dan veplichtVeld= .. anders ...
        initialiseerValidatorMetWaarden(TestBean.VELDNAAM_CONDITIEVELD, "XX", ConditioneelVeld.ConditieRegel.SYNCHROON);

        // testbean: conditieVeld="XX" ==> conditieveld voldoet wel =>> verplichtveld MAG NIET null
        TestBean<String, String> testString = new TestBean<>(null, "XX");
        Assert.assertFalse(validate(testString));
        testString = new TestBean<>("erewr", "XX");
        Assert.assertTrue(validate(testString));

        // testbean: conditieVeld=null ==> conditieveld voldoet wel =>> verplichtveld MOET null (omdat conditieVeld is
        // null)
        testString = new TestBean<>("dfgsdter", null);
        Assert.assertFalse(validate(testString));
        testString = new TestBean<>(null, null);
        Assert.assertTrue(validate(testString));
    }

    @Test
    public void testConditieVoldoetNietExclusief() {
        // constraint: Als conditioneelVeld == "XX" of null en mode = Exclusief, dan veplichtVeld= .. anders ...
        initialiseerValidatorMetWaarden(TestBean.VELDNAAM_CONDITIEVELD, "XX", ConditioneelVeld.ConditieRegel.EXCLUSIEF);

        // testbean: conditieVeld="yy" ==> conditieveld voldoet niet =>> verplichtveld MAG NIET null
        TestBean<String, String> testString = new TestBean<>(null, "yy");
        Assert.assertFalse(validate(testString));
        testString = new TestBean<>("gbv", "yy");
        Assert.assertTrue(validate(testString));
    }

    @Test
    public void testConditieVoldoetWelExclusief() {
        // constraint: Als conditioneelVeld == "XX" of null en mode = Exclusief, dan veplichtVeld= .. anders ...
        initialiseerValidatorMetWaarden(TestBean.VELDNAAM_CONDITIEVELD, "XX", ConditioneelVeld.ConditieRegel.EXCLUSIEF);

        // testbean: conditieVeld="XX" ==> conditieveld voldoet niet =>> verplichtveld MAG NIET null
        TestBean<String, String> testString = new TestBean<>(null, "XX");
        Assert.assertTrue(validate(testString));
        testString = new TestBean<>("dfgg", "XX");
        Assert.assertFalse(validate(testString));

        // testbean: conditieveld=null ==> voldoet wel ==> verplicht veld MAG NIET null (tegen overgesteld)
        testString = new TestBean<>("xcvxgv", null);
        Assert.assertTrue(validate(testString));
        testString = new TestBean<>(null, null);
        Assert.assertFalse(validate(testString));
    }

    @Test
    public void testConditieWildcard() {
        // constraint: Als conditioneelVeld == willekeurig of null en mode = Exclusief, dan veplichtVeld= .. anders ...
        initialiseerValidatorMetWaarden(TestBean.VELDNAAM_CONDITIEVELD, ConditioneelVeld.WILDCARD_WAARDE_CODE,
                ConditioneelVeld.ConditieRegel.SYNCHROON);

        // testbean: conditieVeld="yy" ==> conditieveld voldoet wel =>> verplichtveld MAG NIET null
        TestBean<String, String> testString = new TestBean<>(null, "yy");
        Assert.assertFalse(validate(testString));
        testString = new TestBean<>("dfgdf", "yy");
        Assert.assertTrue(validate(testString));
        // testbean: conditieVeld="XX" ==> conditieveld voldoet wel =>> verplichtveld MAG NIET null
        testString = new TestBean<>(null, "XX");
        Assert.assertFalse(validate(testString));
        testString = new TestBean<>("qweqe", "XX");
        Assert.assertTrue(validate(testString));
        // testbean: conditieVeld=null ==> conditieveld voldoet wel =>> verplichtveld MOET null (synchroon)
        testString = new TestBean<>(null, null);
        Assert.assertTrue(validate(testString));
        testString = new TestBean<>("iopiop", null);
        Assert.assertFalse(validate(testString));
    }

    @Test
    public void testConditieWildcardExclusief() {
        // constraint: Als conditioneelVeld == willekeurig of null en mode = Exclusief, dan veplichtVeld= .. anders ...
        initialiseerValidatorMetWaarden("conditieVeld", ConditioneelVeld.WILDCARD_WAARDE_CODE,
                ConditioneelVeld.ConditieRegel.EXCLUSIEF);

        // testbean: conditieVeld="yy" ==> conditieveld voldoet wel =>> verplichtveld MOET null
        TestBean<String, String> testString = new TestBean<>(null, "yy");
        Assert.assertTrue(validate(testString));
        testString = new TestBean<>("jklj", "yy");
        Assert.assertFalse(validate(testString));
        // testbean: conditieVeld="XX" ==> conditieveld voldoet wel =>> verplichtveld MOET null
        testString = new TestBean<>(null, "XX");
        Assert.assertTrue(validate(testString));
        testString = new TestBean<>("asdas", "XX");
        Assert.assertFalse(validate(testString));
        //
        // testbean: conditieVeld=null ==> conditieveld voldoet wel =>> verplichtveld MAG NIET (exclusief)
        testString = new TestBean<>(null, null);
        Assert.assertFalse(validate(testString));
        testString = new TestBean<>("dfgrt", null);
        Assert.assertTrue(validate(testString));
    }

    @Test
    public void testGenestAfhankelijkAttribuut() {
        initialiseerValidatorMetWaarden("conditieVeld.verplichtVeld", "waarde1",
                ConditioneelVeld.ConditieRegel.SYNCHROON);

        // Test dat de afhankelijke veld dezelfde waarde heeft als in de annotatie en dat de verplichte veld niet is
        // ingevuld
        TestBean<NaamEnumeratiewaardeAttribuut, NaamEnumeratiewaardeAttribuut> innerBean =
                new TestBean<>(
                        new NaamEnumeratiewaardeAttribuut("waarde1"), new NaamEnumeratiewaardeAttribuut("waarde2"));
        TestBean<NaamEnumeratiewaardeAttribuut, TestBean<NaamEnumeratiewaardeAttribuut, NaamEnumeratiewaardeAttribuut>>
                testString =
                new TestBean<>(
                        null, innerBean);
        Assert.assertFalse(validate(testString));

        // Test dat de afhankelijke veld dezelfde waarde heeft als in de annotatie en dat de verplichte veld wel is
        // ingevuld
        testString =
                new TestBean<>(
                        new NaamEnumeratiewaardeAttribuut("abcd"), innerBean);
        Assert.assertTrue(validate(testString));

        // Test dat de afhankelijke veld een andere waarde heeft dan als in de annotatie en dat de verpluchte veld niet
        // is ingevuld
        innerBean =
                new TestBean<>(
                        new NaamEnumeratiewaardeAttribuut("waarde2"), new NaamEnumeratiewaardeAttribuut("waarde2"));
        testString =
                new TestBean<>(
                        null, innerBean);
        Assert.assertTrue(validate(testString));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testFoutInAttribuutNaam() {
        initialiseerValidatorMetWaarden("ongeldigVeld", "waarde1", ConditioneelVeld.ConditieRegel.SYNCHROON);

        this.validator.isValid(new TestBean<String, String>(null, null), null);
    }

    @Test
    public void testJaNeeExclusiefInNullDontCare() {
        // constraint: Als conditioneelVeld == "true" of null en mode = Synchroon, dan veplichtVeld= .. anders ...
        initialiseerValidatorMetWaarden(TestBean.VELDNAAM_CONDITIEVELD, "true",
                ConditioneelVeld.ConditieRegel.EXCLUSIEF_IF_NULL_DONT_CARE);

        // testbean: conditieVeld="Ja" ==> conditieveld voldoet wel =>> verplichtveld MOET null
        TestBean<String, JaNeeAttribuut> testJaNee = new TestBean<>(null, JaNeeAttribuut.JA);
        Assert.assertTrue(validate(testJaNee));
        testJaNee = new TestBean<>("zas", JaNeeAttribuut.JA);
        Assert.assertFalse(validate(testJaNee));
        testJaNee = new TestBean<>(null, null);
        Assert.assertTrue(validate(testJaNee));
        testJaNee = new TestBean<>("qwe", null);
        Assert.assertTrue(validate(testJaNee));
    }

    @Test
    public void testNietWaardeExclusiefInNullDontCare() {
        initialiseerValidatorMetWaarden(TestBean.VELDNAAM_CONDITIEVELD, ConditioneelVeld.OPERATOR_NOT + "abc",
                ConditioneelVeld.ConditieRegel.EXCLUSIEF_IF_NULL_DONT_CARE);

        // Voldoet aan !abc dan moet de verplichtVeld null zijn, dus NOK
        TestBean<String, String> testWaarde = new TestBean<>("zzz", "aaa");
        Assert.assertFalse(validate(testWaarde));
        // Voldoet aan !abc dan moet de verplichtVeld null zijn, dus OK
        testWaarde = new TestBean<>(null, "aaa");
        Assert.assertTrue(validate(testWaarde));
        // Voldoet niet aan !abc dan moet de verplichtVeld don't care, dus OK
        testWaarde = new TestBean<>("zzz", "abc");
        Assert.assertTrue(validate(testWaarde));
        // Voldoet niet aan !abc dan moet de verplichtVeld don't care, dus OK
        testWaarde = new TestBean<>(null, "abc");
        Assert.assertTrue(validate(testWaarde));
    }

    @Test
    public void testJaNeeSynchroonIfNullDontCare() {
        // constraint: Als conditioneelVeld == "true" of null en mode = Synchroon, dan veplichtVeld= .. anders ...
        initialiseerValidatorMetWaarden(TestBean.VELDNAAM_CONDITIEVELD, "true",
                ConditioneelVeld.ConditieRegel.SYNCHROON_IF_NULL_DONT_CARE);

        // testbean: conditieVeld="Ja" ==> conditieveld voldoet wel =>> verplichtveld MAG NIET null
        TestBean<String, JaNeeAttribuut> testJaNee = new TestBean<>(null, JaNeeAttribuut.JA);
        Assert.assertFalse(validate(testJaNee));
        testJaNee = new TestBean<>("sdgf", JaNeeAttribuut.JA);
        Assert.assertTrue(validate(testJaNee));
        testJaNee = new TestBean<>(null, null);
        Assert.assertTrue(validate(testJaNee));
        testJaNee = new TestBean<>("andgdrgfy", null);
        Assert.assertTrue(validate(testJaNee));
    }

    private void initialiseerValidatorMetWaarden(final String naamAfhankelijkVeld, final String bevatWaarde,
            final ConditioneelVeld.ConditieRegel mode)
    {
        final Map<String, Object> annotatieAttributen = new HashMap<>();
        annotatieAttributen.put("danVoldoetRegelInInhoudVanVeld", "verplichtVeld");
        annotatieAttributen.put("aanConditieRegel", mode);
        annotatieAttributen.put("wanneerInhoudVanVeld", naamAfhankelijkVeld);
        annotatieAttributen.put("isGelijkAan", bevatWaarde);
        annotatieAttributen.put("code", Regel.ALG0001);
        annotatieAttributen.put("dbObject", DatabaseObjectKern.PERSOON);
        ConditioneelVeld annotation =
                AnnotationFactory
                        .create(AnnotationDescriptor.getInstance(ConditioneelVeld.class, annotatieAttributen));
        this.validator.initialize(annotation);
    }

    private boolean validate(final Object object) {
        return this.validator.isValid(object, null);
    }
}
