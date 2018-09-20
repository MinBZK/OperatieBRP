/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredicaatCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NaamgebruikAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predicaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.AbstractBerichtEntiteitGroep;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;

import org.junit.Assert;
import org.junit.Test;


public class PersoonNaamgebruikGroepConstraintTest {

    @Test(expected = IllegalArgumentException.class)
    public void testBRAL1512ZonderAanschrijvingGroep() {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<AbstractBerichtEntiteitGroep>> overtredingen = validator.validate(null, Default.class);
        Assert.assertEquals(0, overtredingen.size());
    }

    @Test
    public void testBRAL1512MetIndicatieOpJaRestNull() {
        PersoonNaamgebruikGroepBericht groep = new PersoonNaamgebruikGroepBericht();
        groep.setIndicatieNaamgebruikAfgeleid(JaNeeAttribuut.JA);
        // en de rest is null
        AbstractBerichtEntiteitGroep ag = groep;
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<AbstractBerichtEntiteitGroep>> overtredingen = validator.validate(ag, Default.class);
        Assert.assertEquals(0, overtredingen.size());
    }

    @Test
    public void testBRAL1512MetIndicatieOpNull() {
        PersoonNaamgebruikGroepBericht groep = new PersoonNaamgebruikGroepBericht();
        // en de rest is null
        AbstractBerichtEntiteitGroep ag = groep;
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<AbstractBerichtEntiteitGroep>> overtredingen = validator.validate(ag, Default.class);
        Assert.assertEquals(0, overtredingen.size());
    }

    @Test
    public void testBRAL1512MetIndicatieOpJaEnSommigeNietNull() {
        PersoonNaamgebruikGroepBericht groep = new PersoonNaamgebruikGroepBericht();
        groep.setIndicatieNaamgebruikAfgeleid(JaNeeAttribuut.JA);
        groep.setPredicaatNaamgebruik(new PredicaatAttribuut(new Predicaat(new PredicaatCodeAttribuut("P"),
                new NaamEnumeratiewaardeAttribuut("P-m"), new NaamEnumeratiewaardeAttribuut("P-v"))));
        // en de rest is null
        AbstractBerichtEntiteitGroep ag = groep;
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<AbstractBerichtEntiteitGroep>> overtredingen = validator.validate(ag, Default.class);
        Assert.assertEquals(1, overtredingen.size());
        @SuppressWarnings("rawtypes")
        ConstraintViolation cv = overtredingen.iterator().next();
        Assert.assertEquals("BRAL0512_1", cv.getMessage());
    }

    @Test
    public void testBRAL1512MetIndicatieOpJaEnAlleNietNull() {
        PersoonNaamgebruikGroepBericht groep = new PersoonNaamgebruikGroepBericht();
        groep.setIndicatieNaamgebruikAfgeleid(JaNeeAttribuut.JA);
        groep.setPredicaatNaamgebruik(new PredicaatAttribuut(new Predicaat(new PredicaatCodeAttribuut("P"),
                new NaamEnumeratiewaardeAttribuut("P-m"), new NaamEnumeratiewaardeAttribuut("P-v"))));
        groep.setAdellijkeTitelNaamgebruik(new AdellijkeTitelAttribuut(new AdellijkeTitel(
                new AdellijkeTitelCodeAttribuut("A"), new NaamEnumeratiewaardeAttribuut("A-m"),
                new NaamEnumeratiewaardeAttribuut("A-v"))));
        groep.setGeslachtsnaamstamNaamgebruik(new GeslachtsnaamstamAttribuut("Gslnaam"));
        groep.setNaamgebruik(new NaamgebruikAttribuut(Naamgebruik.EIGEN_PARTNER));
        groep.setScheidingstekenNaamgebruik(new ScheidingstekenAttribuut("/"));
        groep.setVoornamenNaamgebruik(new VoornamenAttribuut("Vnm1"));
        groep.setVoorvoegselNaamgebruik(new VoorvoegselAttribuut("van"));
        // en de rest is null
        AbstractBerichtEntiteitGroep ag = groep;
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<AbstractBerichtEntiteitGroep>> overtredingen = validator.validate(ag, Default.class);
        // in totaal is het 7 (Ja => 6 velden fout) + predikaat/adellijkeTitel combinatie.
        Assert.assertEquals(6 + 1, overtredingen.size());
        @SuppressWarnings("rawtypes")
        // elke keer een ander BRAL0512_x melding, zelfde als template.
                List<String> brals =
                Arrays.asList("BRAL0213", "BRAL0512_1", "BRAL0512_4", "BRAL0512_2", "BRAL0512_3", "BRAL0512_5",
                        "BRAL0512_6");
        for (ConstraintViolation cv : overtredingen) {
            Assert.assertTrue(brals.contains(cv.getMessage()));
        }
    }

    @Test
    public void testBRAL1512MetIndicatieOpNeeEnAlleNietNull() {
        // I don't care if its Nee. ==> geen fouten.
        PersoonNaamgebruikGroepBericht groep = new PersoonNaamgebruikGroepBericht();
        groep.setIndicatieNaamgebruikAfgeleid(JaNeeAttribuut.NEE);
        groep.setPredicaatNaamgebruik(new PredicaatAttribuut(new Predicaat(new PredicaatCodeAttribuut("P"),
                new NaamEnumeratiewaardeAttribuut("P-m"), new NaamEnumeratiewaardeAttribuut("P-v"))));
        groep.setAdellijkeTitelNaamgebruik(new AdellijkeTitelAttribuut(new AdellijkeTitel(
                new AdellijkeTitelCodeAttribuut("A"), new NaamEnumeratiewaardeAttribuut("A-m"),
                new NaamEnumeratiewaardeAttribuut("A-v"))));
        groep.setGeslachtsnaamstamNaamgebruik(new GeslachtsnaamstamAttribuut("Gslnaam"));
        groep.setNaamgebruik(new NaamgebruikAttribuut(Naamgebruik.EIGEN_PARTNER));
        groep.setScheidingstekenNaamgebruik(new ScheidingstekenAttribuut("/"));
        groep.setVoornamenNaamgebruik(new VoornamenAttribuut("Vnm1"));
        groep.setVoorvoegselNaamgebruik(new VoorvoegselAttribuut("van"));
        // en de rest is null
        AbstractBerichtEntiteitGroep ag = groep;
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<AbstractBerichtEntiteitGroep>> overtredingen = validator.validate(ag, Default.class);

        // omdat predikaat EN adellijke titel ingevul => Error
        Assert.assertEquals(1, overtredingen.size());
        Assert.assertEquals(Regel.BRAL0213.getCode(), overtredingen.iterator().next().getMessage());
    }

    @Test
    public void testBRAL0516MetIndicatieOpNeeEnAlleNull() {
        // I don't care if its Nee. ==> 1 fout.
        PersoonNaamgebruikGroepBericht groep = new PersoonNaamgebruikGroepBericht();
        groep.setIndicatieNaamgebruikAfgeleid(JaNeeAttribuut.NEE);
        // en de rest is null
        AbstractBerichtEntiteitGroep ag = groep;
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<AbstractBerichtEntiteitGroep>> overtredingen = validator.validate(ag, Default.class);
        // geslachtsnaam is verplicht
        Assert.assertEquals(1, overtredingen.size());
        @SuppressWarnings("rawtypes")
        ConstraintViolation cv = overtredingen.iterator().next();
        Assert.assertEquals("BRAL0516", cv.getMessage().substring(0, "BRAL0516".length()));
    }

    @Test
    public void testBRAL1512MetIndicatieOpNeeEnAlleenGeslachtsnaamIngevuld() {
        PersoonNaamgebruikGroepBericht groep = new PersoonNaamgebruikGroepBericht();
        groep.setIndicatieNaamgebruikAfgeleid(JaNeeAttribuut.NEE);
        groep.setGeslachtsnaamstamNaamgebruik(new GeslachtsnaamstamAttribuut("Gslnaam"));
        // en de rest is null
        AbstractBerichtEntiteitGroep ag = groep;
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<AbstractBerichtEntiteitGroep>> overtredingen = validator.validate(ag, Default.class);
        // geslachtsnaam is verplicht
        Assert.assertEquals(0, overtredingen.size());
    }

    @Test
    public void testPredikaatEnAdelijkeTitelLeeg() {
        PersoonNaamgebruikGroepBericht groep = new PersoonNaamgebruikGroepBericht();
        groep.setAdellijkeTitelNaamgebruik(null);
        groep.setPredicaatNaamgebruik(null);
        // en de rest is null
        AbstractBerichtEntiteitGroep ag = groep;
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<AbstractBerichtEntiteitGroep>> overtredingen = validator.validate(ag, Default.class);
        // geslachtsnaam is verplicht
        Assert.assertEquals(0, overtredingen.size());
    }

    @Test
    public void testPredikaatNietNullEnAdelijkeTitelLeeg() {
        PersoonNaamgebruikGroepBericht groep = new PersoonNaamgebruikGroepBericht();
        groep.setAdellijkeTitelNaamgebruik(null);
        groep.setPredicaatNaamgebruik(StatischeObjecttypeBuilder.PREDICAAT_HOOGHEID);
        // en de rest is null
        AbstractBerichtEntiteitGroep ag = groep;
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<AbstractBerichtEntiteitGroep>> overtredingen = validator.validate(ag, Default.class);
        // geslachtsnaam is verplicht
        Assert.assertEquals(0, overtredingen.size());
    }

    @Test
    public void testPredikaatLeegEnAdelijkeTitelNietNull() {
        PersoonNaamgebruikGroepBericht groep = new PersoonNaamgebruikGroepBericht();
        groep.setAdellijkeTitelNaamgebruik(StatischeObjecttypeBuilder.ADEL_TITEL_BARON);
        groep.setPredicaatNaamgebruik(null);
        // en de rest is null
        AbstractBerichtEntiteitGroep ag = groep;
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<AbstractBerichtEntiteitGroep>> overtredingen = validator.validate(ag, Default.class);
        // geslachtsnaam is verplicht
        Assert.assertEquals(0, overtredingen.size());
    }

    @Test
    public void testPredikaatEnAdelijkeTitelNietLeeg() {
        PersoonNaamgebruikGroepBericht groep = new PersoonNaamgebruikGroepBericht();
        groep.setAdellijkeTitelNaamgebruik(StatischeObjecttypeBuilder.ADEL_TITEL_BARON);
        groep.setPredicaatNaamgebruik(StatischeObjecttypeBuilder.PREDICAAT_HOOGHEID);
        // en de rest is null
        AbstractBerichtEntiteitGroep ag = groep;
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<AbstractBerichtEntiteitGroep>> overtredingen = validator.validate(ag, Default.class);
        // geslachtsnaam is verplicht
        Assert.assertEquals(1, overtredingen.size());
    }

}
