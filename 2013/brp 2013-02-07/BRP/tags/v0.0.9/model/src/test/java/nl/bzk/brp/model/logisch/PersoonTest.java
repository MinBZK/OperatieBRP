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
import nl.bzk.brp.model.gedeeld.SoortIndicatie;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.validatie.MeldingCode;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test class voor de {@link PersoonTest} class.
 */
public class PersoonTest {

    @Test
    public void testGetterEnSetterId() {
        Persoon persoon = new Persoon();
        persoon.setIdentiteit(null);
        Assert.assertNull(persoon.getId());
        Assert.assertNull(persoon.getIdentiteit());

        persoon.setId(Long.valueOf(2));
        Assert.assertEquals(Long.valueOf(2), persoon.getId());
        Assert.assertNotNull(persoon.getIdentiteit());

        persoon.setId(Long.valueOf(3));
        Assert.assertEquals(Long.valueOf(3), persoon.getId());
    }

    @Test
    public void testZettenEnOphalenIndicaties() {
        Persoon persoon = new Persoon();
        Assert.assertNotNull(persoon.getIndicaties());
        Assert.assertEquals(0, persoon.getIndicaties().size());
        Assert.assertNull(persoon.getVerstrekkingsBeperking());

        persoon.setVerstrekkingsBeperking(Boolean.TRUE);
        Assert.assertEquals(1, persoon.getIndicaties().size());
        Assert.assertTrue(persoon.getVerstrekkingsBeperking());

        persoon.setVerstrekkingsBeperking(Boolean.FALSE);
        Assert.assertEquals(1, persoon.getIndicaties().size());
        Assert.assertFalse(persoon.getVerstrekkingsBeperking());

        persoon = new Persoon();
        PersoonIndicatie indicatie = new PersoonIndicatie();
        indicatie.setPersoon(persoon);
        indicatie.setSoort(SoortIndicatie.BEZIT_BUITENLANDS_REISDOCUMENT);
        indicatie.setWaarde(Boolean.FALSE);
        persoon.voegToePersoonIndicatie(indicatie);
        Assert.assertEquals(1, persoon.getIndicaties().size());
        persoon.verwijderPersoonIndicatie(indicatie);
        Assert.assertEquals(0, persoon.getIndicaties().size());
    }

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
        Assert.assertTrue(violationsBevatCode(MeldingCode.BRAL0001, violations));
        Assert.assertTrue(violationsBevatCode(MeldingCode.BRAL0012, violations));
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
        Assert.assertTrue(violationsBevatCode(MeldingCode.BRAL2032, violations));
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
        Assert.assertTrue(violationsBevatCode(MeldingCode.BRAL0102, violations));

        persoonAdres.setDatumAanvangAdreshouding(20121231);
        violations = beanValidator.validate(persoon, Default.class);
        Assert.assertEquals(0, violations.size());
    }

    @Test
    public void testAddGeslachtsnaamcomponent() {
        Persoon persoon = new Persoon();
        persoon.voegToeGeslachtsnaamcomponent("Santos", "dos");
        persoon.voegToeGeslachtsnaamcomponent("Victória", "da");
        Assert.assertEquals("dos Santos da Victória", persoon.getGeslachtsNaam());
    }

    @Test
    public void testSetVerstrekkingsbeperking() {
        Persoon persoon = new Persoon();
        Assert.assertEquals(0, persoon.getIndicaties().size());
        persoon.setVerstrekkingsBeperking(true);
        Assert.assertTrue(persoon.getVerstrekkingsBeperking());
        Assert.assertEquals(1, persoon.getIndicaties().size());
        persoon.setVerstrekkingsBeperking(false);
        Assert.assertFalse(persoon.getVerstrekkingsBeperking());
        Assert.assertEquals(1, persoon.getIndicaties().size());
    }

    @Test
    public void testAddVoornaam() {
        Persoon persoon = new Persoon();
        persoon.voegToeVoornaam("Françoise");
        persoon.voegToeVoornaam("Isabella");
        Assert.assertEquals("Françoise Isabella", persoon.getVoornamen());
    }

    private boolean violationsBevatCode(final MeldingCode code, final Set<ConstraintViolation<Persoon>> violations) {
        boolean resultaat = false;
        for (ConstraintViolation<Persoon> v : violations) {
            if (code == v.getConstraintDescriptor().getAttributes().get("code")) {
                resultaat = true;
                break;
            }
        }

        return resultaat;
    }
}
