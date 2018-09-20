/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.lang.reflect.InvocationTargetException;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class ValidatorUtilTest {

    @Test
    public void testHaalOpUitDirecteBean() throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException
    {
        Datum datum = new Datum(20100101);
        Plaats plaats = StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM;
        ReflectionTestUtils.setField(plaats, "datumAanvangGeldigheid", datum);

        PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();
        geboorte.setWoonplaatsGeboorte(plaats);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setCommunicatieID("abc");
        persoon.setGeboorte(geboorte);

        Assert.assertEquals("abc", ValidatorUtil.haalWaardeOp(persoon, "communicatieID"));
        Assert.assertEquals(geboorte, ValidatorUtil.haalWaardeOp(persoon, "geboorte"));
        Assert.assertEquals(plaats, ValidatorUtil.haalWaardeOp(persoon, "geboorte.woonplaatsGeboorte"));
        Assert.assertEquals("Amsterdam", ValidatorUtil.haalWaardeOp(persoon, "geboorte.woonplaatsGeboorte.naam"));
        Assert.assertEquals("Amsterdam", ValidatorUtil.haalWaardeOp(persoon, "geboorte.woonplaatsGeboorte.naam.waarde"));
        Assert.assertEquals(datum, ValidatorUtil.haalWaardeOp(persoon, "geboorte.woonplaatsGeboorte.datumAanvangGeldigheid"));
        Assert.assertEquals(20100101,
                ValidatorUtil.haalWaardeOp(persoon, "geboorte.woonplaatsGeboorte.datumAanvangGeldigheid.waarde"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nietBestaandeVeld() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        PersoonBericht persoon = new PersoonBericht();
        ValidatorUtil.haalWaardeOp(persoon, "abc");
    }
}
