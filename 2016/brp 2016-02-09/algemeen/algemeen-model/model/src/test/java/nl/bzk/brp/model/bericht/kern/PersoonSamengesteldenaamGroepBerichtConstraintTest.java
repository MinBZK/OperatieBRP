/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;

import org.junit.Assert;
import org.junit.Test;


@SuppressWarnings("rawtypes")
public class PersoonSamengesteldenaamGroepBerichtConstraintTest {

    @Test
    public void testHelemaalLeeg() {
        // niet gezet ==> geenError
        Assert.assertEquals(0, validate(bouwGroep(null, null, null, null, null)).size());
    }

    @Test
    public void testHelemaalLeegNamenreeksJa() {
        // niet gezet ==> geenError
        Assert.assertEquals(0, validate(bouwGroep(null, null, null, null, JaNeeAttribuut.JA)).size());
    }

    @Test
    public void testHelemaalLeegNamenreeksNee() {
        // namenreeks NEE ==> dont care
        Assert.assertEquals(0, validate(bouwGroep(null, null, null, null, JaNeeAttribuut.NEE)).size());
    }

    @Test
    public void testPredikaatEnAdelijkeTitelLeeg() {
        Assert.assertEquals(0, validate(bouwGroep("-", "abc", null, null, null)).size());
    }

    @Test
    public void testPredikaatEnAdelijkeTitelLeegnamenReeksNee() {
        // namenreeks NEE ==> dont care
        Assert.assertEquals(0, validate(bouwGroep("-", "abc", null, null, JaNeeAttribuut.NEE)).size());
    }

    @Test
    public void testVoorxxxLeeg() {
        Assert.assertEquals(0, validate(bouwGroep(null, null, null, null, null)).size());
    }

    @Test
    public void testVoorvoegselNietLeeg() {
        Set<ConstraintViolation> overtredingen = validate(bouwGroep(null, "voor", null, null, null));
        Assert.assertEquals(1, overtredingen.size());
        Assert.assertEquals(Regel.BRAL0212.getCode(), overtredingen.iterator().next().getMessage());
    }

    @Test
    public void testScheidingstekenNietLeeg() {
        Set<ConstraintViolation> overtredingen = validate(bouwGroep("s", null, null, null, null));
        Assert.assertEquals(1, overtredingen.size());
    }

    @Test
    public void testScheidingstekenNietLeegNamenReeksJa() {
        Set<ConstraintViolation> overtredingen =
                validate(bouwGroep("s", null, StatischeObjecttypeBuilder.ADEL_TITEL_BARON, null, JaNeeAttribuut.JA));
        Assert.assertEquals(1, overtredingen.size());
        Assert.assertEquals(Regel.BRAL0212.getCode(), overtredingen.iterator().next().getMessage());
    }

    @Test
    public void testVoorvoegselNietLeegNamenReeksJa() {
        Set<ConstraintViolation> overtredingen = validate(bouwGroep(null, "voor", null, null, JaNeeAttribuut.JA));
        Assert.assertEquals(2, overtredingen.size());
        // Assert.assertEquals(Regel.BRAL0505.getCode(), overtredingen.iterator().next().getMessage());
    }

    @Test
    public void testAllesGevuld() {
        // titel en predikaat is exclusief, beide gevuld => ERROR
        Set<ConstraintViolation> overtredingen =
                validate(bouwGroep("-", "abc", StatischeObjecttypeBuilder.ADEL_TITEL_BARON,
                        StatischeObjecttypeBuilder.PREDICAAT_HOOGHEID, null));
        Assert.assertEquals(1, overtredingen.size());
        Assert.assertEquals(Regel.BRAL0213.getCode(), overtredingen.iterator().next().getMessage());
    }

    @Test
    public void testPredikaatLeeg() {
        // tiel en predikaat is exclusief, de een is leeg => OK
        Set<ConstraintViolation> overtredingen =
                validate(bouwGroep("-", "abc", StatischeObjecttypeBuilder.ADEL_TITEL_BARON, null, null));
        Assert.assertEquals(0, overtredingen.size());
    }

    @Test
    public void testTitelLeeg() {
        // tiel en predikaat is exclusief, de een is leeg => OK
        Set<ConstraintViolation> overtredingen =
                validate(bouwGroep("-", "abc", null, StatischeObjecttypeBuilder.PREDICAAT_HOOGHEID, null));
        Assert.assertEquals(0, overtredingen.size());
    }

    private PersoonSamengesteldeNaamGroepBericht bouwGroep(final String scheidingsTeken, final String voorvoegsel,
            final AdellijkeTitelAttribuut titel, final PredicaatAttribuut predicaat, final JaNeeAttribuut namenreeks)
    {
        PersoonSamengesteldeNaamGroepBericht groep = new PersoonSamengesteldeNaamGroepBericht();
        if (null != scheidingsTeken) {
            groep.setScheidingsteken(new ScheidingstekenAttribuut(scheidingsTeken));
        }
        if (null != voorvoegsel) {
            groep.setVoorvoegsel(new VoorvoegselAttribuut(voorvoegsel));
        }
        if (null != titel) {
            groep.setAdellijkeTitel(titel);
        }
        if (null != predicaat) {
            groep.setPredicaat(predicaat);
        }
        if (null != namenreeks) {
            groep.setIndicatieNamenreeks(namenreeks);
        }
        return groep;
    }

    private Set<ConstraintViolation> validate(final Object groep) {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation> overtredingen = new HashSet<ConstraintViolation>();
        overtredingen.addAll(validator.validate(groep, Default.class));
        return overtredingen;
    }

}
