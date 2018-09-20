/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.lang.reflect.InvocationTargetException;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.groep.bericht.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import org.junit.Assert;
import org.junit.Test;


public class ValidatorUtilTest {

    @Test
    public void testHaalOpUitDirecteBean() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Plaats plaats = new Plaats();
        plaats.setNaam(new Naam("plaats"));
        Datum datum = new Datum(20100101);
        plaats.setDatumAanvang(datum);

        PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();
        geboorte.setWoonplaatsGeboorte(plaats);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setVerzendendId("abc");
        persoon.setGeboorte(geboorte);

        Assert.assertEquals("abc", ValidatorUtil.haalWaardeOp(persoon, "verzendendId"));
        Assert.assertEquals(geboorte, ValidatorUtil.haalWaardeOp(persoon, "geboorte"));
        Assert.assertEquals(plaats, ValidatorUtil.haalWaardeOp(persoon, "geboorte.woonplaatsGeboorte"));
        Assert.assertEquals("plaats", ValidatorUtil.haalWaardeOp(persoon, "geboorte.woonplaatsGeboorte.naam"));
        Assert.assertEquals("plaats", ValidatorUtil.haalWaardeOp(persoon, "geboorte.woonplaatsGeboorte.naam.waarde"));
        Assert.assertEquals(datum, ValidatorUtil.haalWaardeOp(persoon, "geboorte.woonplaatsGeboorte.datumAanvang"));
        Assert.assertEquals(20100101, ValidatorUtil.haalWaardeOp(persoon, "geboorte.woonplaatsGeboorte.datumAanvang.waarde"));
    }


    @Test(expected = IllegalArgumentException.class)
    public void nietBestaandeVeld() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        PersoonBericht persoon = new PersoonBericht();
        ValidatorUtil.haalWaardeOp(persoon, "abc");
    }
}
