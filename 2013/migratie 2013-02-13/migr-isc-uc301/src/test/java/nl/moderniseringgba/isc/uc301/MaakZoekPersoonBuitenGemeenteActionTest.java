/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc301;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.impl.ZoekPersoonVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ii01Bericht;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;

import org.junit.Assert;
import org.junit.Test;

public class MaakZoekPersoonBuitenGemeenteActionTest {
    private final MaakZoekPersoonBuitenGemeenteAction subject = new MaakZoekPersoonBuitenGemeenteAction();

    @Test
    public void test() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.BURGERSERVICENUMMER, "1234567891");

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", ii01Bericht);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final ZoekPersoonVerzoekBericht verzoek =
                (ZoekPersoonVerzoekBericht) result.get("zoekPersoonBuitenGemeenteBericht");
        Assert.assertNotNull(verzoek);
        Assert.assertEquals("1234567891", verzoek.getBsn());
        Assert.assertNull(verzoek.getBijhoudingsGemeente());
    }
}
