/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.util.HashMap;
import java.util.Map;
import javax.validation.ConstraintValidatorContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestLandGebiedBuilder;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.validatie.constraint.GeldigheidBestaansPeriodeStamgegeven;
import org.hibernate.validator.util.annotationfactory.AnnotationDescriptor;
import org.hibernate.validator.util.annotationfactory.AnnotationFactory;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


public class GeldigheidBestaansPeriodeStamgegevenValidatorTest {

    private final GeldigheidBestaansPeriodeStamgegevenValidator validator =
            new GeldigheidBestaansPeriodeStamgegevenValidator();

    private Map<String, Object> annotatieAttributen;


    public class TestBean<A, B> {

        private final A veldA;
        private final B veldB;

        public TestBean(final A veldA, final B veldB) {
            this.veldA = veldA;
            this.veldB = veldB;
        }

        public A getVeldA() {
            return this.veldA;
        }

        public B getVeldB() {
            return this.veldB;
        }
    }

    @Test
    public void testMetLand() {
        initialiseerValidatorMetWaarden("veldA", "veldB");

        ConstraintValidatorContext.ConstraintViolationBuilder builder =
                Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
        Mockito.when(context.buildConstraintViolationWithTemplate(Mockito.anyString())).thenReturn(builder);

        LandGebied landGebied = TestLandGebiedBuilder.maker().metCode(10).metNaam("naam").metAlpha2Naam("naam")
            .metAanvangGeldigheid(20130101).metEindeGeldigheid(20130701).maak();

        TestBean<DatumEvtDeelsOnbekendAttribuut, LandGebied> testBean =
                new TestBean<>(new DatumEvtDeelsOnbekendAttribuut(20130611), landGebied);
        Assert.assertTrue(this.validator.isValid(testBean, context));

        testBean = new TestBean<>(new DatumEvtDeelsOnbekendAttribuut(20120611), landGebied);
        Assert.assertFalse(this.validator.isValid(testBean, context));

        Mockito.verify(context, Mockito.times(1)).buildConstraintViolationWithTemplate(Mockito.anyString());
        Mockito.verify(builder, Mockito.times(1)).addConstraintViolation();

        testBean = new TestBean<>(new DatumEvtDeelsOnbekendAttribuut(20140611), landGebied);
        Assert.assertFalse(this.validator.isValid(testBean, context));
    }

    @Test
    public void testGeenPeilDatum() {
        initialiseerValidatorMetWaarden("veldA", "veldB");

        LandGebied landGebied = TestLandGebiedBuilder.maker().metCode(10).metNaam("naam").metAlpha2Naam("naam")
            .metAanvangGeldigheid(20130101).metEindeGeldigheid(20130701).maak();

        TestBean<DatumEvtDeelsOnbekendAttribuut, LandGebied> testBean = new TestBean<>(null, landGebied);

        Assert.assertTrue(this.validator.isValid(testBean, null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVerkeerdeObjectTypenStamgegevens() {
        initialiseerValidatorMetWaarden("veldA", "veldB");
        TestBean<DatumEvtDeelsOnbekendAttribuut, PersoonAdresBericht> testBean =
                new TestBean<>(new DatumEvtDeelsOnbekendAttribuut(20130611), new PersoonAdresBericht());
        this.validator.isValid(testBean, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVerkeerdeObjectTypenDatum() {
        initialiseerValidatorMetWaarden("veldA", "veldB");
        LandGebied landGebied = TestLandGebiedBuilder.maker().metCode(10).metNaam("naam").metAlpha2Naam("naam")
            .metAanvangGeldigheid(20130101).metEindeGeldigheid(20130701).maak();

        TestBean<Integer, LandGebied> testBean = new TestBean<Integer, LandGebied>(1, landGebied);

        this.validator.isValid(testBean, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNietBestaandeVelden() {
        initialiseerValidatorMetWaarden("abcdedf", "veldB");

        TestBean<DatumEvtDeelsOnbekendAttribuut, LandGebied> testBean = new TestBean<>(null, null);

        this.validator.isValid(testBean, null);
    }

    private void initialiseerValidatorMetWaarden(final String veldA, final String veldB) {
        // Voorbeeld:
        // @GeldigheidBestaansPeriodeStamgegeven(
        // peilDatumVeld = "datumAanvangAdreshouding",
        // bestaansPeriodeStamgegevenVeld = "land",
        // code = Regel.BRBY0172
        // ))
        this.annotatieAttributen = new HashMap<String, Object>();
        this.annotatieAttributen.put("peilDatumVeld", veldA);
        this.annotatieAttributen.put("bestaansPeriodeStamgegevenVeld", veldB);
        this.annotatieAttributen.put("code", Regel.ALG0001);
        GeldigheidBestaansPeriodeStamgegeven annotation =
                AnnotationFactory.create(AnnotationDescriptor.getInstance(GeldigheidBestaansPeriodeStamgegeven.class,
                        this.annotatieAttributen));
        this.validator.initialize(annotation);
    }

}
