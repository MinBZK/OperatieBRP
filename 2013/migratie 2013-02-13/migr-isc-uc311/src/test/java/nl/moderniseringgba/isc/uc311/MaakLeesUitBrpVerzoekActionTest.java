/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc311;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.impl.WijzigingANummerSignaalBericht;
import nl.moderniseringgba.isc.esb.message.sync.generated.AntwoordFormaatType;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpVerzoekBericht;

import org.junit.Assert;
import org.junit.Test;

public class MaakLeesUitBrpVerzoekActionTest {
    private final MaakLeesUitBrpVerzoekAction subject = new MaakLeesUitBrpVerzoekAction();

    @Test
    public void test() {
        final Map<String, Object> parameters = new HashMap<String, Object>();

        final WijzigingANummerSignaalBericht input = new WijzigingANummerSignaalBericht();
        input.setNieuwANummer(1231231234L);
        parameters.put("input", input);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht =
                (LeesUitBrpVerzoekBericht) result.get("leesUitBrpVerzoekBericht");
        Assert.assertNotNull(leesUitBrpVerzoekBericht);
        Assert.assertEquals(Long.valueOf(1231231234L), leesUitBrpVerzoekBericht.getANummer());
        Assert.assertEquals(AntwoordFormaatType.LO_3, leesUitBrpVerzoekBericht.getAntwoordFormaat());
    }
}
