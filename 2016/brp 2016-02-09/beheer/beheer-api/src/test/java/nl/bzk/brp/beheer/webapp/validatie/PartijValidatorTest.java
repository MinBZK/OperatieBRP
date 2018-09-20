/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartijAttribuut;
import nl.bzk.brp.model.beheer.kern.Partij;
import nl.bzk.brp.model.logisch.kern.Persoon;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

public class PartijValidatorTest {

    private final PartijValidator subject = new PartijValidator();

    @Test
    public void supports() {
        Assert.assertTrue(subject.supports(Partij.class));
        Assert.assertFalse(subject.supports(Persoon.class));
    }

    @Test
    public void validateNok() {
        final Partij partij = new Partij();
        partij.setDatumIngang(new DatumEvtDeelsOnbekendAttribuut(19000101));
        partij.setDatumEinde(new DatumEvtDeelsOnbekendAttribuut(19000101));

        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "partij");
        subject.validate(partij, errors);

        Assert.assertEquals(3, errors.getErrorCount());
    }

    @Test
    public void validateOk() {
        final Partij partij = new Partij();
        partij.setCode(new PartijCodeAttribuut(99999901));
        partij.setNaam(new NaamEnumeratiewaardeAttribuut("Naam"));
        partij.setDatumIngang(new DatumEvtDeelsOnbekendAttribuut(19000101));
        partij.setDatumEinde(null);

        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, null);
        subject.validate(partij, errors);

        Assert.assertEquals(0, errors.getErrorCount());
    }

}
