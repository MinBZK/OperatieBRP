/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.HashMap;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
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
        ValidatieUtils.valideer(errors, false, "", "fout3");
        Assert.assertEquals(3, errors.getErrorCount());
        Assert.assertEquals(1, errors.getFieldErrorCount());
        Assert.assertEquals(2, errors.getGlobalErrorCount());
        Assert.assertEquals("fout3", errors.getGlobalErrors().get(1).getCode());
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
    public void valideerDatumLigtNaDatum() {
        final Errors errors = new MapBindingResult(new HashMap<>(), "test");
        ValidatieUtils.valideerDatumLigtNaDatum(errors, (Integer) null, (Integer) null, "veld1", "veld2");
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        ValidatieUtils.valideerDatumLigtNaDatum(errors, Integer.valueOf(19000101), null, "veld1", "veld2");
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        ValidatieUtils.valideerDatumLigtNaDatum(errors, null, Integer.valueOf(19000102), "veld1", "veld2");
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        ValidatieUtils.valideerDatumLigtNaDatum(errors, Integer.valueOf(19000101), Integer.valueOf(19000102), "veld1", "veld2");
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());

        ValidatieUtils.valideerDatumLigtNaDatum(errors, Integer.valueOf(0), Integer.valueOf(19000102), "veld1", "veld2");
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());

        ValidatieUtils.valideerDatumLigtNaDatum(errors, Integer.valueOf(20150000), Integer.valueOf(20150701), "veld1", "veld2");
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());

        ValidatieUtils.valideerDatumLigtNaDatum(errors, Integer.valueOf(20150715), Integer.valueOf(20150700), "veld1", "veld2");
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());

        ValidatieUtils.valideerDatumLigtNaDatum(errors, Integer.valueOf(20150715), Integer.valueOf(20150000), "veld1", "veld2");
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());

        ValidatieUtils.valideerDatumLigtNaDatum(errors, Integer.valueOf(19000101), Integer.valueOf(19000101), "veld1", "veld2");
        Assert.assertEquals(1, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        Assert.assertEquals("veld.moetlaterzijn", errors.getFieldErrors().get(0).getCode());
        Assert.assertEquals("veld2", errors.getFieldErrors().get(0).getField());

    }

    @Test
    public void valideerDeelsOnbekendeDatums() {
        final Errors errors = new MapBindingResult(new HashMap<>(), "test");
        ValidatieUtils.valideerGeenDeelsOnbekendeDelen(errors, (Integer) null, "datumIngang");
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        ValidatieUtils.valideerGeenDeelsOnbekendeDelen(errors, Integer.valueOf(19000001), "datumIngang");
        Assert.assertEquals(1, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        ValidatieUtils.valideerGeenDeelsOnbekendeDelen(errors, Integer.valueOf(20990101), "datumIngang");
        Assert.assertEquals(1, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        ValidatieUtils.valideerGeenDeelsOnbekendeDelen(errors, Integer.valueOf(DatumUtil.vanDatumNaarInteger(LocalDate.now())), "datumIngang");
        Assert.assertEquals(1, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        ValidatieUtils.valideerGeenDeelsOnbekendeDelen(errors, Integer.valueOf(0), "datumIngang");
        Assert.assertEquals(2, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        ValidatieUtils.valideerGeenDeelsOnbekendeDelen(errors, Integer.valueOf(00000), "datumIngang");
        Assert.assertEquals(3, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
    }

    @Test
    public void valideerDatumGroterGelijkAanVandaag() {
        final Errors errors = new MapBindingResult(new HashMap<>(), "test");
        ValidatieUtils.valideerDatumLigtNaHuidigeDatum(errors, (Integer) null, "datumIngang");
        Assert.assertEquals(0, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        ValidatieUtils.valideerDatumLigtNaHuidigeDatum(errors, Integer.valueOf(19000101), "datumIngang");
        Assert.assertEquals(1, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        ValidatieUtils.valideerDatumLigtNaHuidigeDatum(errors, Integer.valueOf(20990101), "datumIngang");
        Assert.assertEquals(1, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        ValidatieUtils.valideerDatumLigtNaHuidigeDatum(errors, Integer.valueOf(DatumUtil.vanDatumNaarInteger(LocalDate.now())), "datumIngang");
        Assert.assertEquals(2, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
        ValidatieUtils.valideerDatumLigtOpOfNaHuidigeDatum(errors, Integer.valueOf(DatumUtil.vanDatumNaarInteger(LocalDate.now())), "datumIngang");
        Assert.assertEquals(2, errors.getFieldErrorCount());
        Assert.assertEquals(0, errors.getGlobalErrorCount());
    }

    @Test
    public void eenPrivateConstructorTest() throws Exception {
        Constructor<ValidatieUtils> c = ValidatieUtils.class.getDeclaredConstructor();
        c.setAccessible(true);
        ValidatieUtils validatieUtils = c.newInstance();
        Assert.assertNotNull(validatieUtils);
    }
}
