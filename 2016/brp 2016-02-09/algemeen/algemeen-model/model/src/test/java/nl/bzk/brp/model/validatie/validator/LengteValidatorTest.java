/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.validatie.constraint.Lengte;

import org.hibernate.validator.util.annotationfactory.AnnotationDescriptor;
import org.hibernate.validator.util.annotationfactory.AnnotationFactory;
import org.junit.Assert;
import org.junit.Test;


public class LengteValidatorTest {

    private final LengteValidator validator = new LengteValidator();

    @Test
    public void testLengteVanStringTeVoldoetNiet() {
        initialiseerValidatorMetWaarden(1, 2);

        Assert.assertFalse(this.validator.isValid(new LocatieomschrijvingAttribuut("123"), null));
        Assert.assertFalse(this.validator.isValid(new LocatieomschrijvingAttribuut(""), null));
    }

    @Test
    public void testLengteVanStringVoldoet() {
        initialiseerValidatorMetWaarden(1, 2);

        Assert.assertTrue(this.validator.isValid(new LocatieomschrijvingAttribuut("12"), null));
        Assert.assertTrue(this.validator.isValid(new LocatieomschrijvingAttribuut(null), null));
        Assert.assertTrue(this.validator.isValid(null, null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOngeldigAttribuutType() {
        initialiseerValidatorMetWaarden(1, 2);

        // Niet van het type AbstractAttribuutType met een waarde veld.
        this.validator.isValid(new PersoonAdresBericht(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOngeldigeAbstractAttribuutType() {
        initialiseerValidatorMetWaarden(1, 2);

        // Niet van het type AbstractAttribuutType met een waarde veld.
        this.validator.isValid(new HuisnummerAttribuut(1), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOngeldigeParameterMin() {
        initialiseerValidatorMetWaarden(-1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOngeldigeParameterMax() {
        initialiseerValidatorMetWaarden(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOngeldigeParametersNegatief() {
        initialiseerValidatorMetWaarden(2, 1);
    }

    private void initialiseerValidatorMetWaarden(final Integer min, final Integer max) {
        final Map<String, Object> annotatieAttributen = new HashMap<String, Object>();
        annotatieAttributen.put("min", min);
        annotatieAttributen.put("max", max);
        annotatieAttributen.put("code", Regel.ALG0001);
        annotatieAttributen.put("dbObject", DatabaseObjectKern.PERSOON);
        Lengte annotation =
                AnnotationFactory.create(AnnotationDescriptor.getInstance(Lengte.class, annotatieAttributen));
        this.validator.initialize(annotation);
    }
}
