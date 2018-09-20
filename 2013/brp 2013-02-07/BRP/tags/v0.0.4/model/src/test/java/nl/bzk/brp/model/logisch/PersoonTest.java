/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test class voor de {@link PersoonTest} class.
 */
public class PersoonTest {

    @Test
    public void testBsnAnnotatieOpPersoonIdentificatienummers() {
        // Correcte bsn
        PersoonIdentificatienummers pin = new PersoonIdentificatienummers();
        pin.setBurgerservicenummer("111222333");
        pin.setAdministratienummer("7934628529");

        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(pin);

        Validator beanValidator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Persoon>> violations = beanValidator.validate(persoon, Default.class);
        Assert.assertEquals(0, violations.size());

        // Verkeerde bsn
        pin.setBurgerservicenummer("1");
        pin.setAdministratienummer("1");
        violations = beanValidator.validate(persoon, Default.class);
        Assert.assertEquals(2, violations.size());
        Assert.assertTrue(violationsBevatCode("{BRAL0001}", violations));
        Assert.assertTrue(violationsBevatCode("{BRAL0012}", violations));
    }

    @Test
    public void testValidatieAnnotatieSoortAdresVerplicht() {
        // Soort adres niet verplicht
        Land land = new Land();
        land.setLandcode("6031");

        PersoonAdres persoonAdres = new PersoonAdres();
        persoonAdres.setLand(land);
        Set<PersoonAdres> adressen = new HashSet<PersoonAdres>();
        adressen.add(persoonAdres);

        Persoon persoon = new Persoon();
        persoon.setAdressen(adressen);

        Validator beanValidator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Persoon>> violations = beanValidator.validate(persoon, Default.class);

        // Soort adres verplicht
        land.setLandcode("6030");
        violations = beanValidator.validate(persoon, Default.class);
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("{BRAL2032}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testValidatieAnnotatieDatumFormaat() {
        PersoonAdres persoonAdres = new PersoonAdres();
        persoonAdres.setDatumAanvangAdreshouding(2012);

        Set<PersoonAdres> adressen = new HashSet<PersoonAdres>();
        adressen.add(persoonAdres);

        Persoon persoon = new Persoon();
        persoon.setAdressen(adressen);

        Validator beanValidator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Persoon>> violations = beanValidator.validate(persoon, Default.class);

        // Soort adres verplicht
        violations = beanValidator.validate(persoon, Default.class);
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("{BRAL0102}", violations.iterator().next().getMessageTemplate());


        persoonAdres.setDatumAanvangAdreshouding(20121231);
        violations = beanValidator.validate(persoon, Default.class);
        Assert.assertEquals(0, violations.size());
    }

    private boolean violationsBevatCode(final String code, final Set<ConstraintViolation<Persoon>> violations) {
        boolean resultaat = false;
        for (ConstraintViolation<Persoon> v : violations) {
            if (code.equals(v.getMessageTemplate())) {
                resultaat = true;
                break;
            }
        }

        return resultaat;
    }
}
