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

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;

import org.junit.Assert;
import org.junit.Test;


public class ActieConstraintTest {

    @Test
    public void testDatumDAGnaDEG() {
        ActieCorrectieAdresBericht actie = new ActieCorrectieAdresBericht();
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20130101));
        actie.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20120101));

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ActieCorrectieAdresBericht>> overtredingen = validator.validate(actie, Default.class);
        Assert.assertEquals(1, overtredingen.size());
        Assert.assertEquals(Regel.BRBY0032, haalRegelCodeOp(overtredingen.iterator().next()));
    }

    private Regel haalRegelCodeOp(final ConstraintViolation cv) {
        return (Regel) cv.getConstraintDescriptor().getAttributes().get("code");
    }
}
