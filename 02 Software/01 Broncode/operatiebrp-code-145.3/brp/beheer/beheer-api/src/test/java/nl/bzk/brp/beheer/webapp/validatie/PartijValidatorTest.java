/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

public class PartijValidatorTest {

    private final PartijValidator subject = new PartijValidator();

    @Test
    public void supports() {
        Assert.assertTrue(subject.supports(Partij.class));
        Assert.assertFalse(subject.supports(PersoonAdres.class));
    }

    @Test
    public void validateNok() {
        final Partij partij = new Partij("testPartij", "001234");
        ReflectionTestUtils.setField(partij, "naam", null);
        partij.setDatumIngang(19000101);
        partij.setDatumEinde(19000101);

        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "partij");
        subject.validate(partij, errors);

        Assert.assertEquals(2, errors.getErrorCount());
    }

    @Test
    public void validateOk() {
        final Partij partij = new Partij("testPartij", "001234");
        partij.setCode("999901");
        partij.setNaam("Naam");
        partij.setDatumIngang(20160101);
        partij.setDatumEinde(null);

        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, null);
        subject.validate(partij, errors);

        Assert.assertEquals(0, errors.getErrorCount());
    }

}
