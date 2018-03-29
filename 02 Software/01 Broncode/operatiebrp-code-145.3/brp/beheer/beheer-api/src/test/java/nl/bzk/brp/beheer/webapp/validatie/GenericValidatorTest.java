/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Afleidbaar;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

public class GenericValidatorTest {

    @Test
    public void testAfleidbaarEntiteitZonderDatumIngangMethode() {
        GenericValidator<Persoon> persoonValidator = new GenericValidator<>(Persoon.class);
        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "afleidbareEntiteit");
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoonValidator.validate(persoon, errors);

        Assert.assertEquals(0, errorCount(persoonValidator, persoon));
    }

    @Test
    public void testDatumIngangVoorVandaagEnDatumEindeInDeToekomst() {
        GenericValidator<Partij> partijValidator = new GenericValidator<>(Partij.class);
        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "partij");
        final Partij partij = new Partij("testPartij", "012345");
        partij.setDatumIngang(19700101);
        partij.setDatumEinde(20991231);
        partijValidator.validate(partij, errors);

        Assert.assertEquals(0, errorCount(partijValidator, partij));
    }

    @Test
    public void testDatumIngangVandaagEnDatumEindeInDeToekomst() {
        GenericValidator<Partij> partijValidator = new GenericValidator<>(Partij.class);
        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "partij");
        final Partij partij = new Partij("testPartij", "012345");
        partij.setDatumIngang(DatumUtil.vanDatumNaarInteger(LocalDate.now()));
        partij.setDatumEinde(20991231);
        partijValidator.validate(partij, errors);

        Assert.assertEquals(0, errorCount(partijValidator, partij));
    }

    @Test
    public void testIllegalAccesException() {
        GenericValidator<Partij> partijValidator = new GenericValidator<>(Partij.class);
        Assert.assertTrue(partijValidator.supports(Partij.class));
        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "partij");
        final Partij partij = Mockito.mock(Partij.class);
        partij.setDatumIngang(19700101);
        partij.setDatumEinde(20991231);
        Mockito.when(partij.getDatumIngang()).thenThrow(IllegalAccessException.class);
        partijValidator.validate(partij, errors);

        Assert.assertEquals(2, errorCount(partijValidator, partij));
    }

    @Test
    public void testInvocationTargetException() {
        GenericValidator<Partij> partijValidator = new GenericValidator<>(Partij.class);
        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "partij");
        final Partij partij = Mockito.mock(Partij.class);
        partij.setDatumIngang(19700101);
        partij.setDatumEinde(20991231);
        Mockito.when(partij.getDatumIngang()).thenThrow(InvocationTargetException.class);
        partijValidator.validate(partij, errors);

        Assert.assertEquals(2, errorCount(partijValidator, partij));
    }

    private int errorCount(final GenericValidator<? extends Afleidbaar> subject, final Afleidbaar afleidbareEntiteit) {
        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "afleidbareEntiteit");
        subject.validate(afleidbareEntiteit, errors);
        return errors.getErrorCount();
    }
}
