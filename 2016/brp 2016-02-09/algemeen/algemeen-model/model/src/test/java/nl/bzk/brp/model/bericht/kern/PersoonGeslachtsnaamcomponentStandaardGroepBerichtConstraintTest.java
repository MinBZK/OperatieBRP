/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;

import org.junit.Assert;
import org.junit.Test;


public class PersoonGeslachtsnaamcomponentStandaardGroepBerichtConstraintTest {

    @Test
    public void testBRAL0212NietGeldig() {
        PersoonGeslachtsnaamcomponentStandaardGroepBericht groep =
                new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        groep.setScheidingsteken(null);
        groep.setVoorvoegsel(new VoorvoegselAttribuut("abc"));

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersoonGeslachtsnaamcomponentStandaardGroepBericht>> overtredingen =
                validator.validate(groep, Default.class);

        Assert.assertEquals(1, overtredingen.size());
        @SuppressWarnings("rawtypes")
        ConstraintViolation cv = overtredingen.iterator().next();
        Assert.assertEquals("BRAL0212", cv.getMessage());
    }

    @Test
    public void testBRAL0212Geldig() {
        PersoonGeslachtsnaamcomponentStandaardGroepBericht groep =
                new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        groep.setScheidingsteken(new ScheidingstekenAttribuut("-"));
        groep.setVoorvoegsel(new VoorvoegselAttribuut("abc"));

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersoonGeslachtsnaamcomponentStandaardGroepBericht>> overtredingen =
                validator.validate(groep, Default.class);

        Assert.assertEquals(0, overtredingen.size());
    }

    @Test
    public void testPredikaatEnAdelijkeTitelLeeg() {
        PersoonGeslachtsnaamcomponentStandaardGroepBericht groep =
                new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        groep.setScheidingsteken(new ScheidingstekenAttribuut("-"));
        groep.setVoorvoegsel(new VoorvoegselAttribuut("abc"));
        groep.setAdellijkeTitel(null);
        groep.setPredicaat(null);

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersoonGeslachtsnaamcomponentStandaardGroepBericht>> overtredingen =
                validator.validate(groep, Default.class);

        Assert.assertEquals(0, overtredingen.size());
    }

    @Test
    public void testPredikaatEnAdelijkeTitelNietLeeg() {
        PersoonGeslachtsnaamcomponentStandaardGroepBericht groep =
                new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        groep.setScheidingsteken(new ScheidingstekenAttribuut("-"));
        groep.setVoorvoegsel(new VoorvoegselAttribuut("abc"));
        groep.setAdellijkeTitel(StatischeObjecttypeBuilder.ADEL_TITEL_BARON);
        groep.setPredicaat(StatischeObjecttypeBuilder.PREDICAAT_HOOGHEID);

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersoonGeslachtsnaamcomponentStandaardGroepBericht>> overtredingen =
                validator.validate(groep, Default.class);

        Assert.assertEquals(1, overtredingen.size());
        Assert.assertEquals(Regel.BRAL0213.getCode(), overtredingen.iterator().next().getMessage());
    }

}
