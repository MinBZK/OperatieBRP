/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import java.util.HashMap;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

public class ValidatieUtilsTest {

    @Test
    public void valideer() {
        final Errors errors = new MapBindingResult(new HashMap<>(), "test");
        ValidatieUtils.valideer(errors, true, "veld", "fout");
        Assert.assertEquals(0, errors.getErrorCount());
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        ValidatieUtils.valideer(errors, true, null, "fout");
        Assert.assertEquals(0, errors.getErrorCount());
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        ValidatieUtils.valideer(errors, false, "veld", "fout1");
        Assert.assertEquals(1, errors.getErrorCount());
        Assert.assertEquals(1, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        Assert.assertEquals("veld", errors.getFieldErrors().get(0).getField());
        Assert.assertEquals("fout1", errors.getFieldErrors().get(0).getCode());
        ValidatieUtils.valideer(errors, false, null, "fout2");
        Assert.assertEquals(2, errors.getErrorCount());
        Assert.assertEquals(1, errors.getFieldErrorCount());
        Assert.assertEquals(1, errors.getGlobalErrorCount());
        Assert.assertEquals("fout2", errors.getGlobalErrors().get(0).getCode());
    }

    @Test
    public void valideerVerplichtVeld() {
        final Errors errors = new MapBindingResult(new HashMap<>(), "test");
        ValidatieUtils.valideerVerplichtVeld(errors, new Object(), "veld");
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        ValidatieUtils.valideerVerplichtVeld(errors, "", "veld");
        Assert.assertEquals(1, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        Assert.assertEquals("veld.verplicht", errors.getFieldErrors().get(0).getCode());
        ValidatieUtils.valideerVerplichtVeld(errors, null, "veld");
        Assert.assertEquals(2, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
    }

    @Test
    public void valideerVerplichtVeldAttribuut() {
        final Errors errors = new MapBindingResult(new HashMap<>(), "test");
        ValidatieUtils.valideerVerplichtVeldAttribuut(errors, new NaamEnumeratiewaardeAttribuut("waarde"), "veld");
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        ValidatieUtils.valideerVerplichtVeldAttribuut(errors, new NaamEnumeratiewaardeAttribuut(""), "veld");
        Assert.assertEquals(1, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        Assert.assertEquals("veld.verplicht", errors.getFieldErrors().get(0).getCode());
        ValidatieUtils.valideerVerplichtVeldAttribuut(errors, null, "veld");
        Assert.assertEquals(2, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
    }

    @Test
    public void valideerDatumLigtNaDatum() {
        final Errors errors = new MapBindingResult(new HashMap<>(), "test");
        ValidatieUtils.valideerDatumLigtNaDatum(errors, (DatumAttribuut) null, (DatumAttribuut) null, "veld1", "veld2");
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        ValidatieUtils.valideerDatumLigtNaDatum(errors, new DatumEvtDeelsOnbekendAttribuut(19000101), null, "veld1", "veld2");
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        ValidatieUtils.valideerDatumLigtNaDatum(errors, null, new DatumEvtDeelsOnbekendAttribuut(19000102), "veld1", "veld2");
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        ValidatieUtils.valideerDatumLigtNaDatum(
            errors,
            new DatumEvtDeelsOnbekendAttribuut(19000101),
            new DatumEvtDeelsOnbekendAttribuut(19000102),
            "veld1",
                "veld2");
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());

        ValidatieUtils.valideerDatumLigtNaDatum(
            errors,
            new DatumEvtDeelsOnbekendAttribuut(0),
            new DatumEvtDeelsOnbekendAttribuut(19000102),
            "veld1",
                "veld2");
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());

        ValidatieUtils.valideerDatumLigtNaDatum(
            errors,
            new DatumEvtDeelsOnbekendAttribuut(20150000),
            new DatumEvtDeelsOnbekendAttribuut(20150701),
            "veld1",
                "veld2");
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());

        ValidatieUtils.valideerDatumLigtNaDatum(
            errors,
            new DatumEvtDeelsOnbekendAttribuut(20150715),
            new DatumEvtDeelsOnbekendAttribuut(20150700),
            "veld1",
                "veld2");
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());

        ValidatieUtils.valideerDatumLigtNaDatum(
            errors,
            new DatumEvtDeelsOnbekendAttribuut(20150715),
            new DatumEvtDeelsOnbekendAttribuut(20150000),
            "veld1",
                "veld2");
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());

        ValidatieUtils.valideerDatumLigtNaDatum(
            errors,
            new DatumEvtDeelsOnbekendAttribuut(19000101),
            new DatumEvtDeelsOnbekendAttribuut(19000101),
            "veld1",
                "veld2");
        Assert.assertEquals(1, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        Assert.assertEquals("veld.moetlaterzijn", errors.getFieldErrors().get(0).getCode());
        Assert.assertEquals("veld2", errors.getFieldErrors().get(0).getField());

    }
}
