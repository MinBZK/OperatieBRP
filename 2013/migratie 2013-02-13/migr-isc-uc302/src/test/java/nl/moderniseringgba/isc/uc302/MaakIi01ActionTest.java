/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc302;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ii01Bericht;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.junit.Test;

public class MaakIi01ActionTest {

    @Test
    public void test() {
        final VerhuizingVerzoekBericht verhuisBericht = new VerhuizingVerzoekBericht();
        verhuisBericht.setBsn("123456789");
        verhuisBericht.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal("0399")));
        verhuisBericht.setBrpGemeente(new BrpGemeenteCode(new BigDecimal("0499")));

        final Map<String, Object> input = new HashMap<String, Object>();
        input.put("input", verhuisBericht);

        final MaakIi01Action task = new MaakIi01Action();

        final Map<String, Object> output = task.execute(input);

        Assert.assertEquals(1, output.size());
        Assert.assertTrue(output.containsKey("ii01Bericht"));

        final Ii01Bericht ii01Bericht = (Ii01Bericht) output.get("ii01Bericht");

        final String kop = "00000000" + "Ii01" + "0";
        final String bl = "00021";
        final String can = "01";
        final String cal = "016";
        final String eln = "0120";
        final String ell = "009";
        final String eli = "123456789"; // bsn
        final String verwacht = kop + bl + can + cal + eln + ell + eli;
        Assert.assertEquals(verwacht, ii01Bericht.format());

    }
}
