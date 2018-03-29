/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc311;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AntwoordFormaatType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AnummerWijzigingNotificatie;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Test;

public class MaakLeesUitBrpVerzoekActionTest {

    private BerichtenDao berichtenDao = new InMemoryBerichtenDao();
    private MaakLeesUitBrpVerzoekAction subject = new MaakLeesUitBrpVerzoekAction(berichtenDao);

    @Test
    public void test() {
        final Map<String, Object> parameters = new HashMap<>();

        final AnummerWijzigingNotificatie input = new AnummerWijzigingNotificatie();
        input.setNieuwAnummer("1231231234");
        parameters.put("input", berichtenDao.bewaarBericht(input));

        final Map<String, Object> result = subject.execute(parameters);
        assertNotNull(result);
        assertEquals(1, result.size());
        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht =
                (LeesUitBrpVerzoekBericht) berichtenDao.leesBericht((Long) result.get("leesUitBrpVerzoekBericht"));
        assertNotNull(leesUitBrpVerzoekBericht);
        assertEquals("1231231234", leesUitBrpVerzoekBericht.getANummer());
        assertEquals(AntwoordFormaatType.LO_3, leesUitBrpVerzoekBericht.getAntwoordFormaat());
    }
}
