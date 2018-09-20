/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Voorvoegsel;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentStandaardGroepBericht;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de {@link nl.bzk.brp.model.validatie.validator.ConditioneelVerplichtVeldValidator} class.
 */
public class BRAL0212ValidatorTest {

    private final BRAL0212Validator validator = new BRAL0212Validator();

    @Test
    public void testBeideVeldenGevuld() {
        PersoonGeslachtsnaamcomponentStandaardGroepBericht geslComp =
                new PersoonGeslachtsnaamcomponentStandaardGroepBericht();

        geslComp.setScheidingsteken(new ScheidingstekenAttribuut("-"));
        geslComp.setVoorvoegsel(new VoorvoegselAttribuut("de"));

        // initialize doet niks
        validator.initialize(null);

        Assert.assertTrue(validator.isValid(geslComp, null));

        geslComp.setScheidingsteken(new ScheidingstekenAttribuut(" "));
        geslComp.setVoorvoegsel(new VoorvoegselAttribuut("de"));
        Assert.assertTrue(validator.isValid(geslComp, null));
    }

    @Test
    public void testBeidNietGevuld() {
        PersoonGeslachtsnaamcomponentStandaardGroepBericht geslComp =
                new PersoonGeslachtsnaamcomponentStandaardGroepBericht();

        Assert.assertTrue(validator.isValid(geslComp, null));
    }

    @Test
    public void testScheidingsTekenNietGevuld() {
        PersoonGeslachtsnaamcomponentStandaardGroepBericht geslComp =
                new PersoonGeslachtsnaamcomponentStandaardGroepBericht();

        geslComp.setScheidingsteken(null);
        geslComp.setVoorvoegsel(new VoorvoegselAttribuut("de"));
        Assert.assertFalse(validator.isValid(geslComp, null));

        geslComp.setScheidingsteken(new ScheidingstekenAttribuut(null));
        geslComp.setVoorvoegsel(new VoorvoegselAttribuut("de"));
        Assert.assertFalse(validator.isValid(geslComp, null));

        geslComp.setScheidingsteken(new ScheidingstekenAttribuut(""));
        geslComp.setVoorvoegsel(new VoorvoegselAttribuut("de"));
        Assert.assertFalse(validator.isValid(geslComp, null));
    }

    @Test
    public void testVoorvoegselNietGevuld() {
        PersoonGeslachtsnaamcomponentStandaardGroepBericht geslComp =
                new PersoonGeslachtsnaamcomponentStandaardGroepBericht();

        geslComp.setVoorvoegsel(null);
        geslComp.setScheidingsteken(new ScheidingstekenAttribuut("-"));
        Assert.assertFalse(validator.isValid(geslComp, null));

        geslComp.setVoorvoegsel(new VoorvoegselAttribuut(null));
        geslComp.setScheidingsteken(new ScheidingstekenAttribuut("-"));
        Assert.assertFalse(validator.isValid(geslComp, null));

        geslComp.setVoorvoegsel(new VoorvoegselAttribuut(""));
        geslComp.setScheidingsteken(new ScheidingstekenAttribuut("-"));
        Assert.assertFalse(validator.isValid(geslComp, null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testObjectZonderVoorvoegselVeldInObject() {
        @SuppressWarnings("unused") class TestBean {

            private ScheidingstekenAttribuut scheidingsteken;
            private VoorvoegselAttribuut     geenVoorvoegsel;

            ScheidingstekenAttribuut getScheidingsteken() {
                return scheidingsteken;
            }

            VoorvoegselAttribuut getGeenVoorvoegsel() {
                return geenVoorvoegsel;
            }
        }

        TestBean testBean = new TestBean();

        validator.isValid(testBean, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testObjectZonderScheidingstekenVeldInObject() {
        @SuppressWarnings("unused") class TestBean {

            private ScheidingstekenAttribuut geenScheidingsteken;
            private Voorvoegsel              voorvoegsel;

            ScheidingstekenAttribuut getGeenScheidingsteken() {
                return geenScheidingsteken;
            }

            Voorvoegsel getVoorvoegsel() {
                return voorvoegsel;
            }
        }

        TestBean testBean = new TestBean();

        validator.isValid(testBean, null);
    }
}
